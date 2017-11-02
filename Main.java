package sk.cw.jamlin;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.jayway.jsonpath.JsonPath;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Hello world!
 *
 */
public class Main
{
    @Parameter(names={"--action", "-a"})
    private static String action;
    @Parameter(names={"--source", "-s"})
    private static String source;
    @Parameter(names={"--target", "-t"})
    private static String target;
    @Parameter(names={"--language", "-l"})
    private static String language = "";

    private static String workingDirectory = "";
    private static Config config;

    public static void main(String ... argv)
    {
        workingDirectory = System.getProperty("user.dir");
        System.out.println("Working Directory = " + workingDirectory);

        Main main = new Main();
        JCommander.Builder builder = new JCommander.Builder();
        builder.addObject(main).build().parse(argv);
        main.run();

        config = getConfig(workingDirectory+"/jamlin-config.json");

        if (config!=null) {
            getFileTranslation(config, action, source, target);
        }

//        jsonPathTest();
//        jsoutTest();
    }

    public void run() {
        System.out.printf("%s %s %s %s", action, source, target, language);
        System.out.println("");
    }


    private static Config getConfig(String configFilePath) {

        try {
            String jsonConfig = new String ( Files.readAllBytes( Paths.get(configFilePath) ) );
//            String jsonConfig = new Scanner(new File(configFilePath)).useDelimiter("\\Z").next();
            System.out.println("lang _"+language+"_");
            if (language.trim().equals("")) {
                return new Config("file", jsonConfig);
            } else {
                return new Config("file", jsonConfig, language);
            }
        } catch (IOException e) {
            System.out.println("main IOException: "+e.getMessage());
        } catch (Exception e) {
            System.out.println("main Exception: "+e.getMessage());
        }

        return null;
    }


    private static void jsonPathTest() {
        File jsonConfig = new File(workingDirectory+"/jamlin-config.json");
        try {
            int sourceDirectories = JsonPath.read(jsonConfig, "$.sources.directories.length()");
            System.out.println(sourceDirectories);
            /*for (int i=0; i<sourceDirectories.size(); i++) {
                System.out.println( sourceDirectories.get(i) );
                //Object sourceDirRow = sourceDirectories.get(i);
                //System.out.println(sourceDirRow);
                //this.sources.addConfigSource("dir", );
            }*/

            Boolean targetReplaceFile = JsonPath.read(jsonConfig, "$.target.replace_file");
            System.out.println( targetReplaceFile );
            List sourceDirs = JsonPath.read(jsonConfig, "$.sources.directories");
            System.out.println( sourceDirs.getClass().getName() );
            String targetReplacePattern = JsonPath.read(jsonConfig, "$.target.replace_pattern");
            System.out.println("target.replace_pattern: "+targetReplacePattern);
        } catch (IOException $e) {
            System.out.println($e.getMessage());
        }
    }


    private static void jsoutTest() {
        File input = new File(workingDirectory+"/jamlin-demo.html");
        Document doc = null;
        try {
            doc = Jsoup.parse(input, "UTF-8");
            Elements resultTexts = doc.select("p, a");
            Elements resultParams = doc.select("a[title], img[title]");

            System.out.println(resultTexts.first().text());
            System.out.println(resultParams.first().toString());
        } catch (IOException $e) {
            System.out.println($e.getMessage());
            $e.printStackTrace();
        }
        //System.out.println("Hello World");
    }


    private static void getFileTranslation(Config config, String action, String source, String target) {
        // get action
        if (action==null || action.isEmpty()) {
            action = "replace";//"extract";
        } else {
            action = action.trim().toLowerCase();
        }

        // use relative path if no separator
        if ( source==null || source.trim().isEmpty() ) {
            if (action == "extract") {
                source = workingDirectory + "/jamlin-demo.html";
            } else {
                source = workingDirectory + "/jamlin-extract.json";
            }
        } else {
            source = source.trim();
            if (!source.contains(File.separator)) {
                source = workingDirectory + File.separator + source;
            }
        }

        if ( target==null || target.trim().isEmpty() ) {
            target = workingDirectory+"/jamlin-demo-result.html";
        } else {
            target = target.trim();
            if ( !target.contains(File.separator) ) {
                target = workingDirectory+File.separator+target;
            }
        }

        String input = "";
        try {
            input = new String ( Files.readAllBytes( Paths.get(source) ) );
        } catch (IOException e) {
            e.printStackTrace();
        }

        TranslationConfig translationConfig = config.makeTranslationConfig(source, target);

        Translation translation = new Translation(translationConfig);
        String result = "";
        if ( translation.validAction(action) ) {
            if ( action.equals("replace") ) {
                result = translation.replaceStrings(source, target);
            } else {
                result = translation.extractStrings(input);
            }
        }
        System.out.println(result);

    }
}
