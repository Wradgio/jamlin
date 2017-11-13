package sk.cw.jamlin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marthol on 21.09.17.
 */
public class TranslationString {

    private String stringOrig;
    private String selector;
    private List<TranslationValue> translations = new ArrayList<TranslationValue>();

    public TranslationString(String stringOrig, String selector) {
        this.stringOrig = stringOrig;
        this.selector = selector;
    }

    public String getStringOrig() {
        return stringOrig;
    }

    public String getSelector() {
        return selector;
    }

    public List<TranslationValue> getTranslations() {
        return translations;
    }


    public boolean equals(TranslationString other) {
        if ( this.selector.equals(other.getSelector()) ) {
            return true;
        }
        return false;
    }
}
