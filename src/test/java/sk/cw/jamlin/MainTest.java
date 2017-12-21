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
        Main.exportedFilesCount = 0;
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
    @Test
    public void getFileTranslation_02_extract_semiautomatic() throws Exception {
        Main.exportedFilesCount = 0;
        Main.workingDirectory = System.getProperty("user.dir") + File.separator + "testdata";;
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
    }


    /*
     * Extract automatic - equivalent to
     * java -jar jamlin-jar-with-dependencies.jar
     */
    @Test
    public void getFileTranslation_03_extract_automatic() throws Exception {
        Main.exportedFilesCount = 0;
        Main.workingDirectory = System.getProperty("user.dir") + File.separator + "testdata";;
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
    }



    /*
     * Replace specific - equivalent to
     * java -jar jamlin-jar-with-dependencies.jar --action "replace" --source "jamlin_demo-extract.json" --target "jamlin_demo.html" --language "en"
     */
    @Test
    public void getFileTranslation_04_replace_specific() throws Exception {
        Main.exportedFilesCount = 0;
        Main.workingDirectory = System.getProperty("user.dir") + File.separator + "testdata";;
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
        Main.workingDirectory = System.getProperty("user.dir") + File.separator + "testdata";;
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
     * Replace automatic - equivalent to
     * java -jar jamlin-jar-with-dependencies.jar --action "replace"
     */
    @Test
    public void getFileTranslation_06_replace_automatic() throws Exception {
        Main.exportedFilesCount = 0;
        Main.workingDirectory = System.getProperty("user.dir") + File.separator + "testdata";;
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
    }

}