package sk.cw.jamlin;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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

        Main main = new Main();
        JCommander.Builder builder = new JCommander.Builder();
        builder.addObject(main).build().parse(argv);
        main.run();

        config = getConfig(workingDirectory+File.separator+"jamlin_config.json");

        if (config!=null) {
            getFileTranslation(config, action, source, target);
        }
    }


    public void run() {
//        System.out.printf("%s %s %s %s", action, source, target, language);
//        System.out.println("");
    }


    private static Config getConfig(String configFilePath) {

        try {
            String jsonConfig = new String ( Files.readAllBytes( Paths.get(configFilePath) ) );
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


    private static void getFileTranslation(Config config, String action, String source, String target) {
        // get action
        if (action==null || action.isEmpty()) {
            action = "replace";// extract | replace;
        } else {
            action = action.trim().toLowerCase();
        }

        // use relative path if no separator
        if ( source==null || source.trim().isEmpty() ) {
            if (action == "extract") {
                source = workingDirectory + File.separator+"jamlin_demo.html";
            } else {
                source = workingDirectory + File.separator+"jamlin_demo-extract.json";
            }
        } else {
            source = source.trim();
            if (!source.contains(File.separator)) {
                source = workingDirectory + File.separator + source;
            }
        }

        if ( target==null || target.trim().isEmpty() ) {
            target = workingDirectory+File.separator+"jamlin_demo.html";
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
                String targetString = "";
                try {
                    targetString = new String ( Files.readAllBytes( Paths.get(target) ) );
                } catch (IOException e) {
                    e.printStackTrace();
                }

                TranslationReplaceResult replaceResults = translation.replaceStrings(input, targetString);
                result = "Please, set output language for result translation";
                if ( !language.isEmpty() && replaceResults.getLangCodes().size()>0 ) {
                    // if language is set, return result of that language
                    result = replaceResults.get(language);
                } else {
                    // else if any results, output files and return first
                    result = replaceResults.get(replaceResults.getLangCodes().get(0));
                }
                sk.cw.jamlin.Files.outputReplaceResultFiles(replaceResults, target);
            } else {
                result = translation.extractStrings(input);
                File sourceFile = new File(source);
                // write result
                sk.cw.jamlin.Files.outputExtractResultFile(result, sourceFile);
            }
        }
//        System.out.println(result);

    }
}
