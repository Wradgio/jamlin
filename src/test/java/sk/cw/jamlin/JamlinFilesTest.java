package sk.cw.jamlin;

import org.junit.Test;

import java.io.File;
import java.util.Date;

/**
 * Created by Marcel Zúbrik on 12.1.2018.
 */
public class JamlinFilesTest {
    @Test
    public void makeHistory() throws Exception {
        Date testDateTime = new Date();
        File testFile = new File(System.getProperty("user.dir") +File.separator+ "testdata" +File.separator+ "jamlin_demo-en.html");
        System.out.println(testDateTime.toString());
        System.out.println(testFile.toString());
        System.out.println( JamlinFiles.makeHistory(testDateTime, testFile) );
    }

}