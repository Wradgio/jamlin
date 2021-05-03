package sk.cw.jamlin;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * Created by Marcel Zúbrik on 22.11.2017.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MainTest {

    /*@Test
    public void main() throws Exception {
    }*/


    /*
     * Extract specific - equivalent to
     * java -jar jamlin-jar-with-dependencies.jar --action "extract" --source "jamlin_demo.html" --language "sk"
     */
    @Test
    public void getFileTranslation_01_extract_specific() throws Exception {
        Main.exportedFilesCount = 1;
        Main.workingDirectory = System.getProperty("user.dir") + File.separator + "testdata";
        if (Main.config==null) {
            Main.config = Main.getConfig(Main.workingDirectory + File.separator + "jamlin_config.json");
        }
        Main.action = null;
        Main.source = "jamlin_demo.html";
        Main.language = "sk";
        Main.config.setLanguage( new Language(Main.language) );
        if (Main.config!=null) {
            Main.handleFileTranslations();
        }
        assertEquals("Extract specific - Expected to export " +Main.expectedFilesCount+ " files, exported " +Main.exportedFilesCount, Main.expectedFilesCount, Main.exportedFilesCount);
    }


    /*
     * Extract semiautomatic - equivalent to
     * java -jar jamlin-jar-with-dependencies.jar --action "extract" --source "jamlin_demo.html"
     */
    /*@Test
    public void getFileTranslation_02_extract_semiautomatic() throws Exception {
        Main.exportedFilesCount = 1;
        Main.workingDirectory = System.getProperty("user.dir") + File.separator + "testdata";
        if (Main.config==null) {
            Main.config = Main.getConfig(Main.workingDirectory + File.separator + "jamlin_config.json");
        }
        Main.action = null;
        Main.source = "jamlin_demo.html";
        Main.language = null;
        Main.config.setLanguage(null);
        if (Main.config!=null) {
            Main.handleFileTranslations();
        }
        assertEquals("Extract semiautomatic - Expected to export " +Main.expectedFilesCount+ " files, exported " +Main.exportedFilesCount, Main.expectedFilesCount, Main.exportedFilesCount);
    }*/


    /*
     * Extract automatic - equivalent to
     * java -jar jamlin-jar-with-dependencies.jar
     */
    /*@Test
    public void getFileTranslation_03_extract_automatic() throws Exception {
        Main.dictionary = false;
        Main.exportedFilesCount = 1;
        Main.workingDirectory = System.getProperty("user.dir") + File.separator + "testdata";
        if (Main.config==null) {
            Main.config = Main.getConfig(Main.workingDirectory + File.separator + "jamlin_config.json");
        }
        Main.action = null;
        Main.source = null;
        Main.language = null;
        Main.config.setLanguage(null);
        if (Main.config!=null) {
            Main.handleFileTranslations();
        }
        assertEquals("Extract automatic - Expected to export " +Main.expectedFilesCount+ " files, exported " +Main.exportedFilesCount, Main.expectedFilesCount, Main.exportedFilesCount);
    }*/



    /*
     * Replace specific - equivalent to
     * java -jar jamlin-jar-with-dependencies.jar --action "replace" --source "jamlin_demo-extract.json" --target "jamlin_demo.html" --language "en"
     */
    @Test
    public void getFileTranslation_04_replace_specific() throws Exception {
        Main.exportedFilesCount = 0;
        Main.workingDirectory = System.getProperty("user.dir") + File.separator + "testdata";
        if (Main.config==null) {
            Main.config = Main.getConfig(Main.workingDirectory + File.separator + "jamlin_config.json");
        }
        Main.action = "replace";
        Main.source = "jamlin_demo-extract.json";
        Main.target = "jamlin_demo.html";
        Main.language = "sk";
        Main.config.setLanguage( new Language(Main.language) );
        if (Main.config!=null) {
            Main.handleFileTranslations();
        }
        assertEquals("Replace specific - Expected to export " +Main.expectedFilesCount+ " files, exported " +Main.exportedFilesCount, Main.expectedFilesCount, Main.exportedFilesCount);
    }


    /*
     * Replace semiautomatic - equivalent to
     * java -jar jamlin-jar-with-dependencies.jar --action "replace" --source "jamlin_demo-extract.json" --target "jamlin_demo.html"
     */
    @Test
    public void getFileTranslation_05_replace_semiautomatic() throws Exception {
        Main.exportedFilesCount = 0;
        Main.workingDirectory = System.getProperty("user.dir") + File.separator + "testdata";
        if (Main.config==null) {
            Main.config = Main.getConfig(Main.workingDirectory + File.separator + "jamlin_config.json");
        }
        Main.action = "replace";
        Main.source = "jamlin_demo-extract.json";
        Main.target = "jamlin_demo.html";
        Main.language = null;
        Main.config.setLanguage(null);
        if (Main.config!=null) {
            Main.handleFileTranslations();
        }
        assertEquals("Replace semiautomatic - Expected to export " +Main.expectedFilesCount+ " files, exported " +Main.exportedFilesCount, Main.expectedFilesCount, Main.exportedFilesCount);
    }


    /*
     * Extract single file - equivalent to
     * java -jar jamlin-jar-with-dependencies.jar --action "extract" -source "/Users/marthol/Development/vue-js/jamlin-front-vue/src/components/global/Header.vue"
     */
    /*@Test
    public void getFileTranslation_07_extract_file() throws Exception {
        Main.exportedFilesCount = 0;
        Main.workingDirectory = "/Users/marthol/Development/vue-js/jamlin-front-vue/";
        if (Main.config==null) {
            Main.config = Main.getConfig(Main.workingDirectory + "jamlin_config.json");
        }
        Main.action = "extract";
        Main.source = "/Users/marthol/Development/vue-js/jamlin-front-vue/src/components/global/Header.vue";
        Main.target = null;
        Main.language = "en";
        Language lang = new Language(Main.language);
        if (Main.config!=null) {
            Main.config.setLanguage(lang);
            Main.handleFileTranslations();
        }
        assertEquals("extract automatic - extracted to translate " +Main.expectedFilesCount+ " files, exported " +Main.exportedFilesCount, Main.expectedFilesCount, Main.exportedFilesCount);
    }*/

    /*
     * Replace automatic - equivalent to
     * java -jar jamlin-jar-with-dependencies.jar --action "replace"
     */
    /*@Test
    public void getFileTranslation_06_replace_automatic() throws Exception {
        Main.exportedFilesCount = 0;
        Main.workingDirectory = System.getProperty("user.dir") + File.separator + "testdata";
        if (Main.config==null) {
            Main.config = Main.getConfig(Main.workingDirectory + File.separator + "jamlin_config.json");
        }
        Main.action = "replace";
        Main.source = null;
        Main.target = null;
        Main.language = null;
        Main.config.setLanguage(null);
        if (Main.config!=null) {
            Main.handleFileTranslations();
        }
        assertEquals("Replace automatic - Expected to export " +Main.expectedFilesCount+ " files, exported " +Main.exportedFilesCount, Main.expectedFilesCount, Main.exportedFilesCount);
    }*/


    // TODO - change to regular test
    /*
     * Replace automatic - equivalent to
     * java -jar jamlin-jar-with-dependencies.jar --action "replace"
     */
    /*@Test
    public void StretchTest() throws Exception {
        Main.exportedFilesCount = 1;
        Main.workingDirectory = "/Users/marthol/Development/vue-js/stretchshop-front-vue/";
        if (Main.config==null) {
            Main.config = Main.getConfig(Main.workingDirectory + "jamlin_config.json");
        }
        Main.action = "extract";
        Main.source = "";
        Main.target = null;
        Main.language = "en";
        Language lang = new Language(Main.language);
        if (Main.config!=null) {
            Main.config.setLanguage(lang);
            Main.handleFileTranslations();
        }
        assertEquals("Replace automatic - Expected to export " +Main.expectedFilesCount+ " files, exported " +Main.exportedFilesCount, Main.expectedFilesCount, Main.exportedFilesCount);
    }*/




    /*
     * Extract automatic - equivalent to
     * java -jar jamlin-jar-with-dependencies.jar --dictionary true
     */
    /*@Test
    public void getProjectTranslation_01_extract_automatic() throws Exception {
        Main.exportedFilesCount = 0;
        Main.workingDirectory = System.getProperty("user.dir") + File.separator + "testdata";
        if (Main.config==null) {
            Main.config = Main.getConfig(Main.workingDirectory + File.separator + "jamlin_config.json");
        }
        Main.action = null;
        Main.source = null;
        Main.language = null;
        Main.config.setLanguage(null);
        Main.dictionary = true;
        if (Main.config!=null) {
            Main.handleFileTranslations();
        }
        assertEquals("Project Extract automatic - Expected to export " +Main.expectedFilesCount+ " +1 files, exported " +Main.exportedFilesCount, Main.expectedFilesCount, Main.exportedFilesCount);
    }*/

}