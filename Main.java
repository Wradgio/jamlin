package sk.cw.jamlin;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.jayway.jsonpath.JsonPath;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class Main
{
    public static String workingDirectory = "";
    @Parameter(names={"--length", "-l"})
    int length;
    @Parameter(names={"--pattern", "-p"})
    int pattern;

    public static void main(String ... argv)
    {
        workingDirectory = System.getProperty("user.dir");

        Main main = new Main();
        JCommander.Builder builder = new JCommander.Builder();
        builder.addObject(main).build().parse(argv);

        jsonPathTest();
        jsoutTest();

        System.out.println("Working Directory = " + workingDirectory);

        main.run();
    }

    public void run() {
        System.out.printf("%d %d", length, pattern);
        System.out.println("");
    }


    private static void jsonPathTest() {
        File jsonConfig = new File(workingDirectory+"/jamlin-config.json");
        try {
            String targetFilename = JsonPath.read(jsonConfig, "$.target.filename");
            System.out.println("target.filename: "+targetFilename);
        } catch (IOException $e) {
            System.out.println($e.getMessage());
        }
    }


    private static void jsoutTest() {
        File input = new File(workingDirectory+"/jamlin-demo.html");
        Document doc = null;
        try {
            doc = Jsoup.parse(input, "UTF-8");
            Elements resultTexts = doc.select("p, a");
            Elements resultParams = doc.select("a[title], img[title]");

            System.out.println(resultTexts.first().text());
            System.out.println(resultParams.first().toString());
        } catch (IOException $e) {
            System.out.println($e.getMessage());
            $e.printStackTrace();
        }
        //System.out.println("Hello World");
    }
}
