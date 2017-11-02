package sk.cw.jamlin;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;

import java.util.Map;

/**
 * Created by marthol on 02.10.17.
 */
public class Config {

    private String type = "file";
    private String input = "";
    private ConfigTarget target;
    private ConfigSource sources;
    private Language language = null;

    /* CONSTRUCTORS */
    public Config(String type, String input) {
        this.type = type;
        this.input = input;
        this.sources = new ConfigSource();
        System.out.println("construct lang NULL");
        parseConfigInput();
    }

    public Config(String type, String input, String langCode) {
        this.type = type;
        this.input = input;
        this.sources = new ConfigSource();
        System.out.println("construct lang: "+langCode);
        this.language = new Language(langCode);
        parseConfigInput();
    }


    /* METHODS */
    private void parseConfigInput() {
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(this.input);

        // target
        Boolean targetReplaceFile = JsonPath.read(document, "$.target.replace_file");
        String targetReplacePattern = JsonPath.read(document, "$.target.replace_pattern");
        this.target = new ConfigTarget(targetReplaceFile, targetReplacePattern);

        // source Dirs
        int sourceDirectoriesCount = JsonPath.read(document, "$.sources.directories.length()");
        for (int i=0; i<sourceDirectoriesCount; i++) {
            String dirPath = JsonPath.read(document, "$.sources.directories["+i+"].path");
            Boolean dirTraverse = JsonPath.read(document, "$.sources.directories["+i+"].traverse");
            int lastIndex = this.sources.addConfigSource("directory", dirPath, dirTraverse);
        }
        // source Files
        int sourceFilesCount = JsonPath.read(document, "$.sources.files.length()");
        for (int i=0; i<sourceFilesCount; i++) {
            String filePath = JsonPath.read(document, "$.sources.files["+i+"].path");
            int lastIndex = this.sources.addConfigSource("file", filePath);
            Map<String, String> selectors = JsonPath.read(document, "$.sources.files["+i+"].selectors");
            for (Map.Entry<String,String> entry : selectors.entrySet()) {
                Map<String, String> selectorData = JsonPath.read(document, "$.sources.files[" + i + "].selectors." + entry.getKey());
                String type = "";
                String selector = "";
                String attrName = "";
                for (Map.Entry<String, String> dataEntry : selectorData.entrySet()) {
                    if (dataEntry.getKey().equals("type")) {
                        type = dataEntry.getValue();
                    }
                    if (dataEntry.getKey().equals("selector")) {
                        selector = dataEntry.getValue();
                    }
                    if (dataEntry.getKey().equals("attrName")) {
                        attrName = dataEntry.getValue();
                    }
                }
                if ( !selector.trim().equals("") && !type.trim().equals("") ) {
                    if (type.equals(TranslationBlock.types.ATTRIBUTE.toString().toLowerCase()) && !type.trim().equals("")) {
                        this.sources.getFiles().get(lastIndex).addSelector(entry.getKey(), selector, type, attrName);
                    } else {
                        this.sources.getFiles().get(lastIndex).addSelector(entry.getKey(), selector, type);
                    }
                }
            }
//            System.out.println( this.sources.getFiles().get(lastIndex).getSelectors().get(1).getName() );
//            System.out.println( this.sources.getFiles().get(lastIndex).getSelectors().get(1).getSelector() );
        }
    }


    public TranslationConfig makeTranslationConfig(String source, String target) {
        TranslationConfig translationConfig = new TranslationConfig(source, target, this.target);
        System.out.println("language c.mtc: "+this.language);
        translationConfig.setLanguage(this.language);
        for (int i = 0; i<this.sources.getFiles().size(); i++ ) {
            translationConfig.selectors.addAll(this.sources.getFiles().get(i).getSelectors());
        }
        // TODO - update after directory listing is ready
        /*for (int i = 0; i<this.sources.getDirectories().size(); i++ ) {
            translationConfig.selectors.addAll(this.sources.getDirectories().get(i).getSelectors());
        }*/

        return translationConfig;
    }


    /* THE GETTERS & THE SETTERS */

    public String getType() {
        return type;
    }

    public String getInput() {
        return input;
    }
}
