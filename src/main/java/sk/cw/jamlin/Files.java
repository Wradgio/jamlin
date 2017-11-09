package sk.cw.jamlin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by Marcel Zúbrik on 29.10.2017.
 */
public class Files {
    // browse files
    public static List<String> listValidFiles(File dir, List<String> extensions) {
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
     * @param input
     * @param source
     */
    static void outputExtractResultFile(String input, File source, Translation translation) {
        if ( !translation.getLanguage().toString().isEmpty() ) {
            //translation.getLanguage().toString();
        }
        // check if lang set
        // check if related json file does exist
        // is does - parse json and add this language - if unknown, use xx(1, 2, 3, ...) - and output it
        // TODO - compare with existing result file !!!
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

        fileName = fileName+"-extract";
        if ( !fileName.contains(".json") ) {
            fileName = fileName+".json";
        }

        writeResultFile(source.getParentFile(), fileName, input);
    }


    /**
     *
     * @param results
     * @param destination
     */
    static void outputReplaceResultFiles(TranslationReplaceResult results, String destination) {
        Map<String, String> resultFileNames = new HashMap<>();

        resultFileNames = getReplaceOutputFileName(results, destination);

        File destinationDirectory = new File(destination);
        if (!destinationDirectory.isDirectory()) {
            destinationDirectory = destinationDirectory.getParentFile();
        }

        if (results.getLangCodes().size()>0) {
            for (int j=0; j<results.getLangCodes().size(); j++) {
                String langCode = results.getLangCodes().get(j);
                writeResultFile(destinationDirectory, resultFileNames.get(langCode), results.get(langCode));
            }
        }
    }


    /**
     *
     * @param results
     * @param destination
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
                String newName = fileNameFromPattern(results.getTargetPattern(), fileName, fileExtension, results.getLangCodes().get(j));
                resultFileNames.put(results.getLangCodes().get(j), newName);
            }
        }

        return resultFileNames;
    }


    /**
     *
     * @param pattern
     * @param fileName
     * @param fileExtension
     * @param langCode
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
                System.out.println("Exported: "+destination.toString()+ File.separator+ fileName);
            } catch(IOException e) {
                System.out.println("Close error for: "+fileName);
                e.printStackTrace();
            }
        }
    }


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
}
