package sk.cw.jamlin;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by Marcel Zúbrik on 29.10.2017.
 */
public class JamlinFiles {

    // browse files
    static List<String> listValidFiles(File dir, List<String> extensions) {
        List<String> resultFiles = new ArrayList<String>();
        return listValidFiles(dir, 0, extensions, resultFiles);
    }

    private static List<String> listValidFiles(File dir, int level, List<String> extensions, List<String> resultFiles) { //
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                String fileExtension = getFileExtension(children[i]);
                File item = new File(dir, children[i]);
                if ( item.isDirectory() ) {
                    listValidFiles(item, level, extensions, resultFiles);
                } else if ( extensions.contains(fileExtension) ) {
                    resultFiles.add(item.toString());
                }
            }
            return resultFiles;
        } else {
            resultFiles.add(dir.toString());
            return resultFiles;
        }
    }


    /**
     *
     * @param input String
     * @param source File
     * @param translation Translation
     */
    static void outputExtractResultFile(String input, File source, Translation translation) {
        String fileName = "";
        String fileExtension = "";

        try {
            fileName = source.getName();
            // get name before extension
            if (fileName.contains(".")) {
                fileName = fileName.substring(0, fileName.lastIndexOf('.'));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        String langCode = null;
        if ( translation.getLanguage()!=null && !translation.getLanguage().toString().isEmpty() ) {
            langCode = translation.getLanguage().getCode();
        } else {
            // get lang code from filename
            langCode = Language.getLangCodeFromFilePath(fileName);
        }
        if (langCode!=null && Language.checkLangCodeValid(langCode) ) {
            // if valid, remove lang from filename
            fileName = fileName.replace("-"+langCode, "");
        }

        fileName = fileName+"-extract";
        if ( !fileName.contains(".json") ) {
            fileName = fileName+".json";
        }


        String oldResultFilePath = source.getParentFile().toString()+ File.separator +fileName;
        if ( (new File(oldResultFilePath)).exists() ) {
            // merge two results and create new result

            // get old result from file
            String oldResultInput = "";
            try {
                oldResultInput = new String( java.nio.file.Files.readAllBytes(Paths.get(oldResultFilePath)), Charset.forName("UTF-8") );
            } catch (IOException e) {
                e.printStackTrace();
            }
            TranslationExtractResult oldResult = null;
            if (!oldResultInput.isEmpty()) {
                Gson gsonOld = new Gson();
                try {
                    oldResult = gsonOld.fromJson(oldResultInput, TranslationExtractResult.class);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            // get new result from input
            Gson gsonNew = new Gson();
            TranslationExtractResult newResult = null;
            try {
                newResult = gsonNew.fromJson(input, TranslationExtractResult.class);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            if (oldResult!=null && newResult!=null) {
                input = TranslationExtractResult.mergeTwoResults(oldResult, newResult);
            }
        }

        // save history
        if ( Main.config.getTarget().getSaveHistory() ) {
            JamlinFiles.makeHistory(new Date(), source);
        }

        writeResultFile(source.getParentFile(), fileName, input);
    }


    /**
     *
     * @param results TranslationReplaceResult
     * @param destination String
     */
    static void outputReplaceResultFiles(TranslationReplaceResult results, String destination) {
        Map<String, String> resultFileNames = new HashMap<>();
        Date historyDate = new Date();

        resultFileNames = getReplaceOutputFileName(results, destination);

        File destinationDirectory = new File(destination);
        if (destinationDirectory!=null && !destinationDirectory.isDirectory()) {
            destinationDirectory = destinationDirectory.getParentFile();
        }

        if (results.getLangCodes().size()>0) {
            for (int j=0; j<results.getLangCodes().size(); j++) {
                String langCode = results.getLangCodes().get(j);
                // save history
                if ( Main.config.getTarget().getSaveHistory() ) {
                    JamlinFiles.makeHistory(historyDate, new File(destinationDirectory +File.separator+ resultFileNames.get(langCode)) );
                }
                writeResultFile(destinationDirectory, resultFileNames.get(langCode), results.get(langCode));
            }
        }
    }


    /**
     *
     * @param results TranslationReplaceResult
     * @param destination String
     * @return Map
     */
    private static Map<String, String> getReplaceOutputFileName(TranslationReplaceResult results, String destination) {
        String fileName = "";
        String fileExtension = "";
        Map<String, String> resultFileNames = new HashMap<>();

        // get file name
        if ( !results.getTargetPattern().isEmpty() && !destination.isEmpty() ) {
            try {
                File f = new File(destination);
                fileName = f.getName();
                // get file extension
                fileExtension = getFileExtension(fileName);
                // get name before extension
                if (fileName.contains(".")) {
                    fileName = fileName.substring(0, fileName.lastIndexOf('.'));
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        if (results.getLangCodes().size()>0) {
            for (int j=0; j<results.getLangCodes().size(); j++) {
                // remove language from fileName it is already included
                String foundLangExt = fileName.substring(fileName.length() -1 -results.getLangCodes().get(j).length(), fileName.length());
                if ( foundLangExt.equals("-"+results.getLangCodes().get(j)) ) {
                    fileName = fileName.substring(0, fileName.length() -1 -results.getLangCodes().get(j).length());
                }
                // set filename from config pattern
                String newName = fileNameFromPattern(results.getTargetPattern(), fileName, fileExtension, results.getLangCodes().get(j));
                resultFileNames.put(results.getLangCodes().get(j), newName);
            }
        }

        return resultFileNames;
    }


    /**
     *
     * @param pattern String
     * @param fileName String
     * @param fileExtension String
     * @param langCode String
     * @return String
     */
    private static String fileNameFromPattern(String pattern, String fileName, String fileExtension, String langCode) {
        String resultName = "";

        if ( !pattern.isEmpty() && pattern.contains("*") ) {
            resultName = pattern;
            int starIndex = resultName.indexOf("*");
            if (starIndex>-1) {
                resultName = resultName.substring(0, starIndex) + fileName + resultName.substring(starIndex + 1);
            }
            starIndex = resultName.lastIndexOf("*");
            if (starIndex>-1) {
                resultName = resultName.substring(0, starIndex) + fileExtension + resultName.substring(starIndex + 1);
            }
            resultName = resultName.replaceAll("\\*$",fileExtension);
            resultName = resultName.replace("$lang", langCode);
            if (resultName.contains("$datetime")) {
                Date now = new Date();
                LocalDateTime current = now.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                String datetime = current.format(DateTimeFormatter.ofPattern("u-mm-dd-HH-mm-ss"));
                resultName = resultName.replace("$datetime", datetime);
            }
        } else {
            resultName = fileName +"-"+ langCode +"."+ fileExtension;
        }

        return resultName;
    }


    /**
     *
     * @param destination File
     * @param fileName String
     * @param resultContent String
     */
    private static void writeResultFile(File destination, String fileName, String resultContent) {
        FileWriter locFile = null;
        try {
            locFile = new FileWriter(destination.toString()+ File.separator+ fileName);
            locFile.write(resultContent);
        } catch(IOException e) {
            System.out.println("Write error for: "+fileName);
            e.printStackTrace();
        } finally {
            try {
                if(locFile != null) {
                    locFile.close();
                }
                Main.exportedFilesCount ++;
                System.out.println("Exported #"+ Main.exportedFilesCount+ ": "+ destination.toString()+ File.separator+ fileName);
            } catch(IOException e) {
                System.out.println("Close error for: "+fileName);
                e.printStackTrace();
            }
        }
    }


    /**
     *
     * @param fileName String
     * @return String
     */
    private static String getFileExtension(String fileName) {
        String fileExtension = "";
        // get file extension
        int i = fileName.lastIndexOf(".");
        if (i>0) {
            fileExtension = fileName.substring(i+1);
            String[] name = fileName.split("\\.", i+1);
            String[] pureName = Arrays.copyOf(name, name.length-1);
            fileName = String.join(".", pureName);
        }

        return fileExtension;
    }


    /**
     *
     * @param action String
     * @param mode String
     * @param resultFiles List<String>
     * @return int
     */
    static int getExpectedFilesCount(String action, String mode, List<String> resultFiles) {
        List<String> usedNames = new ArrayList<String>();

        if (resultFiles!=null && resultFiles.size()>0) {
            for (int i = 0; i < resultFiles.size(); i++) {
                //mode replace automatic
                if ( action!=null && action.equals(Main.actions.REPLACE.toString().toLowerCase()) ) {
                    if ( mode!=null && mode.equals("automatic") ) {
                        /*if ( resultFiles.get(i).indexOf('-extract.json') ) {

                        }*/
                    }
                } else {

                }
            }
        }
        return 0;
    }


    /**
     *
     * @param startTimestamp Date
     * @param origFile File
     * @return File
     */
    static File makeHistory(Date startTimestamp, File origFile) {
        if ( startTimestamp!=null && origFile!=null ) {
            File baseDirectory = origFile.getParentFile();
            SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            String formatedDateString = dateFormater.format(startTimestamp);
            String[] dateStrings = null;
            if ( formatedDateString.contains(" ") ) {
                dateStrings = formatedDateString.split("\\s+");
            }
            if (dateStrings!=null && dateStrings.length>=2) {
                File destinationHistoryFolder = new File(origFile.getParent().toString()+ File.separator+ ".jamlin_history" +File.separator+ dateStrings[0]
                        +File.separator+ dateStrings[1]);
                if ( !destinationHistoryFolder.exists() ) {
                    try {
                        java.nio.file.Files.createDirectories(destinationHistoryFolder.toPath());
                    } catch (IOException exception) {
                        System.out.println("Cannot create directories: "+exception.getMessage());
                    }
                } else {
                    System.out.println("Folder "+destinationHistoryFolder.toString()+" doesn't exist.");
                }

                if ( destinationHistoryFolder.exists() ) {
                    File destinationFile = new File(destinationHistoryFolder.toString() +File.separator+ origFile.getName());
                    System.out.println(destinationFile.toString());
                    try {
                        java.nio.file.Files.copy(origFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException exception) {
                        System.out.println("Cannot copy file: "+exception.getMessage());
                    }
                }
            } else {
                System.out.println("No datestrings");
            }

        } else {
            System.out.println("Missing parameters.");
        }
        return null;
    }
}
