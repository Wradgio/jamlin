package sk.cw.jamlin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marthol on 05.10.17.
 */
public class TranslationBlock {

    private String name;
    private String cssSelector;
    enum types {
        ATTRIBUTE, TEXT, VALUE
    }
    private String type;
    private String attrName;
    private List<TranslationString> translationStrings = new ArrayList<TranslationString>();

    public TranslationBlock(String name, String cssSelector, String type) {
        this.name = name;
        this.cssSelector = cssSelector;
        if ( validType(type) ) {
            this.type = type.toLowerCase();
        }
    }

    public TranslationBlock(String name, String cssSelector, String type, String attrName) {
        this.name = name;
        this.cssSelector = cssSelector;
        if ( validType(type) ) {
            this.type = type.toLowerCase();
            this.attrName = attrName;
        }
    }


    public int addTranslationString(String name, String selector) {
        TranslationString translationString = new TranslationString(name, selector);
        translationStrings.add(translationString);
        return (translationStrings.size()-1);
    }



    public boolean validType(String type) {
        for (types c : types.values()) {
            if (c.name().toLowerCase().equals(type)) {
                return true;
            }
        }
        return false;
    }


    public String getName() {
        return name;
    }

    public String getCssSelector() {
        return cssSelector;
    }

    public List<TranslationString> getTranslationStrings() {
        return translationStrings;
    }

    public String getType() {
        return type;
    }

    public String getAttrName() {
        return attrName;
    }
}
