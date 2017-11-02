package sk.cw.jamlin;

import org.jsoup.nodes.Document;

import java.io.File;
import java.util.*;

/**
 * Created by Marcel Zúbrik on 30.10.2017.
 */
public class TranslationReplaceResult {
    private Map<String, Document> results = new HashMap<>();
    private List<String> langCodes = new ArrayList<>();
    private List<String> fileNames = new ArrayList<>();
    private String targetPattern = "";

    public TranslationReplaceResult(Map<String, Document> results, List<String> langCodes) {
        this.results = results;
        this.langCodes = langCodes;
    }

    public void addResult(String name, Document result) {
        results.put(name, result);
    }

    public void addResult(String name, Document result, String targetPattern) {
        results.put(name, result);
        targetPattern = targetPattern;
    }

    public Document getDocument(String key) {
        if ( results.size()>0 ) {
            return results.get(key);
        }
        return null;
    }

    public String get(String key) {
        if ( results.size()>0 ) {
            return results.get(key).toString();
        }
        return null;
    }

    public int countResults() {
        return results.size();
    }

    public String getOutputFileName(String language, String destination) {
        String fileName = "";
        String fileExtension = "";
        if ( !this.targetPattern.isEmpty() && !destination.isEmpty() ) {
            try {
                File f = new File(destination);
                fileName = f.getName();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        int i = fileName.lastIndexOf(".");
        if (i>0) {
            fileExtension = fileName.substring(i+1);
            String[] name = fileName.split("\\.", i+1);
            String[] pureName = Arrays.copyOf(name, name.length-1);
            fileName = String.join(".", pureName);
        }

        System.out.println("fileName: "+fileName);
        System.out.println("fileExtension: "+fileExtension);

        return fileName;
    }


    public List<String> getLangCodes() {
        return langCodes;
    }


    public void setTargetPattern(String targetPattern) {
        this.targetPattern = targetPattern;
    }
}
