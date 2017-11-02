package sk.cw.jamlin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marthol on 05.10.17.
 */
public class TranslationBlock {

    String name;
    String cssSelector;
    List<TranslationString> translationStrings = new ArrayList<TranslationString>();

    public TranslationBlock(String name, String cssSelector) {
        this.name = name;
        this.cssSelector = cssSelector;
    }


    public int addTranslationString(String name, String selector) {
        TranslationString translationString = new TranslationString(name, selector);
        translationStrings.add(translationString);
        return (translationStrings.size()-1);
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
}
