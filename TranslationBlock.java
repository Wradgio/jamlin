package sk.cw.jamlin;

import java.util.ArrayList;

/**
 * Created by marthol on 05.10.17.
 */
public class TranslationBlock {

    String name;
    String cssSelector;
    ArrayList<TranslationString> translationStrings;

    public TranslationBlock(String name, String cssSelector) {
        this.name = name;
        this.cssSelector = cssSelector;
    }
}
