package sk.cw.jamlin;

import org.junit.Test;

import java.io.File;

/**
 * Created by Marcel Zúbrik on 22.11.2017.
 */
public class MainTest {
    
    @Test
    public void main() throws Exception {
    }


    /*
     * Extract specific - equivalent to
     * java -jar jamlin-jar-with-dependencies.jar --action "extract" --source "jamlin_demo.html" --language "sk"
     */
    @Test
    public void getFileTranslation_extract_specific() throws Exception {

    }


    /*
     * equivalent to
     * java -jar jamlin-jar-with-dependencies.jar --action "extract" --source "jamlin_demo.html"
     */
    @Test
    public void getFileTranslation_extract_semiautomatic() throws Exception {

    }


    /*
     * equivalent to
     * java -jar jamlin-jar-with-dependencies.jar
     */
    @Test
    public void getFileTranslation_extract_automatic() throws Exception {
        Main.workingDirectory = System.getProperty("user.dir");
        System.out.println(Main.workingDirectory+ File.separator+"jamlin_config.json");
        Main.config = Main.getConfig(Main.workingDirectory+ File.separator+"jamlin_config.json");
        System.out.println(Main.config.getSources().getDirectories().size());
        if (Main.config!=null) {
            Main.handleFileTranslations();
        }
    }



    /*
     * equivalent to
     * java -jar jamlin-jar-with-dependencies.jar --action "replace" --source "jamlin_demo-extract.json" --target "jamlin_demo.html" --language "en"
     */
    @Test
    public void getFileTranslation_replace_specific() throws Exception {

    }


    /*
     * equivalent to
     * java -jar jamlin-jar-with-dependencies.jar --action "replace" --source "jamlin_demo-extract.json" --target "jamlin_demo.html"
     */
    @Test
    public void getFileTranslation_replace_semiautomatic() throws Exception {
    }


    /*
     * equivalent to
     * java -jar jamlin-jar-with-dependencies.jar --action "replace"
     */
    @Test
    public void getFileTranslation_replace_automatic() throws Exception {

    }

}