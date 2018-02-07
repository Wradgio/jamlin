package sk.cw.jamlin;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Hello world!
 *
 */
public class Main
{
    @Parameter(names={"--action", "-a"})
    public static String action;
    @Parameter(names={"--source", "-s"})
    public static String source;
    @Parameter(names={"--target", "-t"})
    public static String target;
    @Parameter(names={"--language", "-l"})
    public static String language = "";

    public static int expectedFilesCount = 0;
    public static int exportedFilesCount = 0;
    public static String mode = "";

    static String workingDirectory = "";
    public static Config config;
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
            //getFileTranslation(config, action, source, target);
            handleFileTranslations();
        } else {
            System.out.println("Main Error: no config");
        }
    }


    private void run() {
//        System.out.printf("%s %s %s %s", action, source, target, language);
//        System.out.println("");
    }


    public static Config getConfig(String configFilePath) {
        try {
            String jsonConfig = new String ( Files.readAllBytes( Paths.get(configFilePath) ), Charset.forName("UTF-8") );
            if (language.trim().isEmpty()) {
                return new Config("file", jsonConfig);
            } else {
                return new Config("file", jsonConfig, language);
            }
        } catch (IOException e) {
            System.out.println("Main IOException: "+e.getMessage());
        } catch (Exception e) {
            System.out.println("Main Exception: "+e.getMessage());
        }

        return null;
    }



    public static void handleFileTranslations() {
        // get all files (their paths) that match config options
        List<String> extensions = new ArrayList<String>();
        for (int i=0; i<config.getSources().getDirectories().size(); i++) {
            ConfigSourceFilterDirectory dir = (ConfigSourceFilterDirectory) config.getSources().getDirectories().get(i);
            for (int j=0; j<((ConfigSourceFilterDirectory) config.getSources().getDirectories().get(i)).getExtensions().size(); j++) {
                String extension = ((ConfigSourceFilterDirectory) config.getSources().getDirectories().get(i)).getExtensions().get(j);
                extensions.add( extension );
            }
        }

        // get mode accoding to settings
        mode = getMode();

        List<String> resultFiles = new ArrayList<String>();
        if ( action!=null && action.equals(actions.REPLACE.toString().toLowerCase()) && target!=null && !target.isEmpty() ) {
            // use exact result in replace - replace with target = lang specific and semiautomatic modes
            resultFiles.add(target);
            expectedFilesCount = 1;
        } else if ( action!=null && action.equals(actions.EXTRACT.toString().toLowerCase()) && source!=null && !source.isEmpty() && (target==null || !target.isEmpty()) ) {
            // extract should not have target
            expectedFilesCount = JamlinFiles.getExpectedFilesCount(action, mode, resultFiles);
        } else {
            // all replace actions
            resultFiles = JamlinFiles.listValidFiles(new File(workingDirectory), extensions);
            expectedFilesCount = resultFiles.size();//sk.cw.jamlin.JamlinFiles.getExpectedFilesCount(action, resultFiles);
        }

        System.out.println("ACTION: "+ (action==null ? "null(extract)" : action) );
        System.out.println("MODE: "+mode);
        System.out.println("SOURCE: "+source);
        System.out.println("TARGET: "+target);
        System.out.println("LANGUAGE: "+language);

        System.out.println("resultFiles.size(): "+resultFiles.size());

        if (action!=null && action.equals(actions.REPLACE.toString().toLowerCase())) {
            if (resultFiles.size()>0) {
                for (int i = 0; i < resultFiles.size(); i++) {
                    try {
                        File parentDirectory = new File(resultFiles.get(i));
                        if (parentDirectory != null) {
                            String fileNameOrig = parentDirectory.getName();
                            String fileName = "";
                            String langCode = Language.getLangCodeFromFilePath(parentDirectory.getPath());
                            String jsonFilePath = "";
                            if (langCode != null && Language.checkLangCodeValid(langCode)) {
                                // if valid, remove lang from filename
                                fileName = fileNameOrig.replace("-" + langCode, "-extract");
                                String extension[] = fileName.split("\\.");
                                if (extension.length > 0) {
                                    fileName = fileName.replace("." + extension[extension.length - 1], ".json");
                                } else {
                                    fileName = null;
                                }
                                parentDirectory = parentDirectory.getParentFile();
                                jsonFilePath = parentDirectory.getPath() + File.separator + fileName;
                            } else {
                                if ( source!=null && !source.isEmpty() ) {
                                    jsonFilePath = source;
                                } else {
                                    String extension[] = fileNameOrig.split("\\.");
                                    if (extension.length > 0) {
                                        fileName = fileNameOrig.replace("." + extension[extension.length - 1], "");
                                    } else {
                                        fileName = null;
                                    }
                                    if (fileName.contains("-")) {
                                        String fileNameBlocks[] = fileName.split("\\-");
                                        fileNameBlocks = Arrays.copyOf(fileNameBlocks, fileNameBlocks.length - 1);
                                        fileName = String.join("-", fileNameBlocks);
                                    }
                                    fileName = fileName + "-extract.json";
                                }

                            }
                            // check if json exists
                            if ( (new File(jsonFilePath)).exists() ) {
                                if ( Language.checkLangCodeValid(langCode) ) {
                                    config.setLanguage(new Language(langCode));
                                }
                                System.out.println("Processing file: "+resultFiles.get(i));
                                getFileTranslation(config, action, jsonFilePath, resultFiles.get(i));
                            } else {
                                expectedFilesCount--;
                                System.out.println("Not exists: "+jsonFilePath);
                            }
                        } else {
                            System.out.println("Parent directory is null");
                        }
                    } catch (Exception e) {
                        System.out.println("Replace action file error: " + e.getMessage());
//                        System.out.print(e.getCause());
                    }
                }
            } else {
                System.out.println("No result files for replace");
            }
        } else {
            // extract action
            if (resultFiles.size()>0) {
                for (int i = 0; i < resultFiles.size(); i++) {
                    System.out.println(" -------------------------------------------- ");
                    System.out.println(resultFiles.get(i));
                    System.out.println(" -------------------------------------------- ");
                    getFileTranslation(config, action, resultFiles.get(i), null);
                }
            } else {
                // extract specific
                getFileTranslation(config, action, source, null);
            }
        }
    }


    /**
     *
     * @param config Config
     * @param action String
     * @param source String
     * @param target String
     */
    public static void getFileTranslation(Config config, String action, String source, String target) {
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
        if ( action.equals(actions.REPLACE.toString().toLowerCase()) ) {
            if (target != null && !target.trim().isEmpty()) {
                target = target.trim();
                if (!target.contains(File.separator)) {
                    target = workingDirectory + File.separator + target;
                }
            } else {
                variablesPassed = false;
                System.out.println("ERROR: No 'target' set.");
            }
        }

        if ( variablesPassed ) {
            if (action.equals(actions.EXTRACT.toString().toLowerCase())) {
                String fileLangCode = Language.getLangCodeFromFilePath(source);
                if (!fileLangCode.isEmpty() && Language.checkLangCodeValid(fileLangCode)) {
                    config.setLanguage(new Language(fileLangCode));
                }
            } else if (action.equals(actions.REPLACE.toString().toLowerCase())) {
                if ( source!=null && !source.trim().isEmpty() && target!=null && !target.trim().isEmpty() ) {
                    // nothing
                } else {
                    String fileLangCode = Language.getLangCodeFromFilePath(target);
                    if (!fileLangCode.isEmpty() && Language.checkLangCodeValid(fileLangCode)) {
                        config.setLanguage(new Language(fileLangCode));
                    }
                }
            }

            String input = "";
            try {
                input = new String(Files.readAllBytes(Paths.get(source)), Charset.forName("UTF-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            TranslationConfig translationConfig = config.makeTranslationConfig(source, target);

            Translation translation = new Translation(translationConfig);
            //System.out.println("LANG CODE: "+translation.getLanguage().getCode());
            String result = "";

            if (translation.validAction(action)) {
                if (action.equals(actions.REPLACE.toString().toLowerCase())) {
                    String targetString = "";
                    try {
                        targetString = new String(Files.readAllBytes(Paths.get(target)), Charset.forName("UTF-8"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    TranslationReplaceResult replaceResults = translation.replaceStrings(input, targetString);
                    result = "Please, set output language for result translation";
                    if (language!=null && !language.isEmpty() && replaceResults.getLangCodes().size()>0) {
                        // if language is set, return result of that language
                        result = replaceResults.get(language);
                    } else {
                        // else if any results, output files and return first
                        result = replaceResults.get(replaceResults.getLangCodes().get(0));
                    }
                    JamlinFiles.outputReplaceResultFiles(replaceResults, target);

                } else { // extract
                    result = translation.extractStrings(input);
                    File sourceFile = new File(source);
                    // write result
                    JamlinFiles.outputExtractResultFile(result, sourceFile, translation);
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
     * @param type String
     * @return boolean
     */
    private static boolean validAction(String type) {
        for (actions c : actions.values()) {
            if (c.name().toLowerCase().equals(type)) {
                return true;
            }
        }
        return false;
    }


    /**
     *
     * @return String
     */
    private static String getMode() {
        if ( action!=null && action.equals(actions.REPLACE.toString().toLowerCase()) ) {
            if ( source!=null && !source.isEmpty() && target!=null && !target.isEmpty() && language!=null && !language.isEmpty() ) {
                return "specific";
            } else if ( source!=null && !source.isEmpty() && target!=null && !target.isEmpty() ) {
                return "semiautomatic";
            }
        } else { // extract
            if ( source!=null && !source.isEmpty() && language!=null && !language.isEmpty() ) {
                return "specific";
            } else if ( source!=null && !source.isEmpty() ) {
                return "semiautomatic";
            }
        }
        return "automatic";
    }

}
