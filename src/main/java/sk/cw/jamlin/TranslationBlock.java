package sk.cw.jamlin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marthol on 05.10.17.
 */
public class TranslationBlock {

    private String name = "";
    private String cssSelector = "";
    enum types {
        ATTRIBUTE, TEXT, VALUE
    }
    private String type = "text";
    private String attrName = "";
    private List<TranslationString> translationStrings = new ArrayList<TranslationString>();

    TranslationBlock(String name, String cssSelector, String type) {
        this(name, cssSelector, type, "");
    }

    TranslationBlock(String name, String cssSelector, String type, String attrName) {
        this.name = name;
        this.cssSelector = cssSelector;
        if ( validType(type) ) {
            this.type = type.toLowerCase();
            this.attrName = attrName;
        }
    }


    int addTranslationString(String name, String selector) {
        TranslationString translationString = new TranslationString(name, selector);
        translationStrings.add(translationString);
        return (translationStrings.size()-1);
    }


    private boolean validType(String type) {
        for (types c : types.values()) {
            if (c.name().toLowerCase().equals(type)) {
                return true;
            }
        }
        return false;
    }


    public boolean equals(TranslationBlock other) {
        if ( this.name.equals(other.getName()) && this.cssSelector.equals(other.getCssSelector()) &&
                this.attrName.equals(other.getAttrName()) && this.type.equals(other.getType()) ) {
            return true;
        }
        return false;
    }


    /**
     *
     * @param translationString TranslationString
     * @return boolean
     */
    public TranslationString getTranslationStringBySameData(TranslationString translationString) {
        boolean blockExists = false;
        if ( getTranslationStrings().size()>0 ) {
            for (int i=0; i<getTranslationStrings().size(); i++) {
                if ( getTranslationStrings().get(i).equals(translationString) ) {
                    return getTranslationStrings().get(i);
                }
            }
        }
        return null;
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
