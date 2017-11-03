package sk.cw.jamlin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Marcel Zúbrik on 29.10.2017.
 */
public class Files {

    // files and dirs
    public static void visitAllDirsAndFiles(File dir) {
        // spracovat(dir);

        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                visitAllDirsAndFiles(new File(dir, children[i]));
            }
        }
    }

    // only dirs
    public static void visitAllDirs(File dir) {
        if (dir.isDirectory()) {
            // spracovat(dir);
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                visitAllDirs(new File(dir, children[i]));
            }
        }
    }

    // only files
    public static void visitAllFiles(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                visitAllFiles(new File(dir, children[i]));
            }
        } else {
            // spracovat(dir);
        }
    }


    public static boolean outputReplaceResultFiles(TranslationReplaceResult results, String destination) {
        Map<String, String> resultFileNames = new HashMap<>();

        resultFileNames = getReplaceOutputFileName(results, destination);

        File destinationDirectory = new File(destination);
        if (!destinationDirectory.isDirectory()) {
            destinationDirectory = destinationDirectory.getParentFile();
        }

        if (results.getLangCodes().size()>0) {
            for (int j=0; j<results.getLangCodes().size(); j++) {
                System.out.println(j);
                String langCode = results.getLangCodes().get(j);
                writeResultFile(destinationDirectory, resultFileNames.get(langCode), results.get(langCode));
            }
        }

        return true;
    }


    public static Map<String, String> getReplaceOutputFileName(TranslationReplaceResult results, String destination) {
        String fileName = "";
        String fileExtension = "";
        Map<String, String> resultFileNames = new HashMap<>();

        // get file name
        if ( !results.getTargetPattern().isEmpty() && !destination.isEmpty() ) {
            try {
                File f = new File(destination);
                fileName = f.getName();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        // get file extension
        int i = fileName.lastIndexOf(".");
        if (i>0) {
            fileExtension = fileName.substring(i+1);
            String[] name = fileName.split("\\.", i+1);
            String[] pureName = Arrays.copyOf(name, name.length-1);
            fileName = String.join(".", pureName);
        }

        if (results.getLangCodes().size()>0) {
            for (int j=0; j<results.getLangCodes().size(); j++) {
                String newName = fileNameFromPattern(results.getTargetPattern(), fileName, fileExtension, results.getLangCodes().get(j));
                resultFileNames.put(results.getLangCodes().get(j), newName);
            }
        }

        return resultFileNames;
    }


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
}
