package sk.cw.jamlin;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
    enum actions {
        EXTRACT, REPLACE
    }

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
            handleFileTranslations();
        }
    }


    private void run() {
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



    private static void handleFileTranslations() {
        // get all files (their paths) that match config options
        List<String> extensions = new ArrayList<String>();
        for (int i=0; i<config.getSources().getDirectories().size(); i++) {
            ConfigSourceFilterDirectory dir = (ConfigSourceFilterDirectory) config.getSources().getDirectories().get(i);
            for (int j=0; j<((ConfigSourceFilterDirectory) config.getSources().getDirectories().get(i)).getExtensions().size(); j++) {
                String extension = ((ConfigSourceFilterDirectory) config.getSources().getDirectories().get(i)).getExtensions().get(j);
                extensions.add( extension );
            }
        }
        List<String> resultFiles = sk.cw.jamlin.Files.listValidFiles(new File(workingDirectory), extensions);
        for (int i=0; i<resultFiles.size(); i++) {
            System.out.println( resultFiles.get(i) );
        }
    }


    /**
     *
     * @param config
     * @param action
     * @param source
     * @param target
     */
    private static void getFileTranslation(Config config, String action, String source, String target) {
        boolean variablesPassed = true;

        // get action
        if ( action!=null && !action.isEmpty() && validAction(action) ) {
            action = action.trim().toLowerCase();
        } else {
            action = "extract";// extract | replace;
        }

        // get source - use relative path if no separator
        if ( source!=null && !source.trim().isEmpty() ) { // no source
            source = source.trim();
            if (!source.contains(File.separator)) {
                source = workingDirectory + File.separator + source;
            }
        } else {
            variablesPassed = false;
            System.out.println("ERROR: No 'source' set.");
        }

        // get target - REPLACE only
        if ( target!=null && !target.trim().isEmpty() ) {
            target = target.trim();
            if ( !target.contains(File.separator) ) {
                target = workingDirectory+File.separator+target;
            }
        } else {
            variablesPassed = false;
            System.out.println("ERROR: No 'target' set.");
        }


        if ( variablesPassed ) {
            String fileLangCode = Language.getLangCodeFromFilePath(source);
            if (!fileLangCode.isEmpty()) {
                config.setLanguage(new Language(fileLangCode));
            }

            String input = "";
            try {
                input = new String(Files.readAllBytes(Paths.get(source)));
            } catch (IOException e) {
                e.printStackTrace();
            }

            TranslationConfig translationConfig = config.makeTranslationConfig(source, target);

            Translation translation = new Translation(translationConfig);
            String result = "";

            if (translation.validAction(action)) {
                if (action.equals("replace")) {
                    String targetString = "";
                    try {
                        targetString = new String(Files.readAllBytes(Paths.get(target)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    TranslationReplaceResult replaceResults = translation.replaceStrings(input, targetString);
                    result = "Please, set output language for result translation";
                    if (!language.isEmpty() && replaceResults.getLangCodes().size() > 0) {
                        // if language is set, return result of that language
                        result = replaceResults.get(language);
                    } else {
                        // else if any results, output files and return first
                        result = replaceResults.get(replaceResults.getLangCodes().get(0));
                    }
                    sk.cw.jamlin.Files.outputReplaceResultFiles(replaceResults, target);

                } else { // extract
                    result = translation.extractStrings(input);
                    File sourceFile = new File(source);
                    // write result
                    sk.cw.jamlin.Files.outputExtractResultFile(result, sourceFile, translation);
                }
            }
        } else {
            System.out.println("No output - please check your inputs:");
            System.out.println("action: "+action);
            System.out.println("source: "+source);
            System.out.println("target: "+target);
        }
    }


    /**
     *
     * @param type
     * @return
     */
    private static boolean validAction(String type) {
        for (actions c : actions.values()) {
            if (c.name().toLowerCase().equals(type)) {
                return true;
            }
        }
        return false;
    }
}
