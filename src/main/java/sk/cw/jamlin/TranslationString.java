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

    TranslationString(String stringOrig, String selector) {
        this.stringOrig = stringOrig;
        this.selector = selector;
    }

    /**
     *
     * @param other TranslationString
     * @return boolean
     */
    boolean equals(TranslationString other) {
        if ( this.selector.equals(other.getSelector()) ) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param langCode String
     * @return int
     */
    public int getTranslationValueByLang(String langCode) {
        if ( getTranslations().size()>0 ) {
            for (int i=0; i<getTranslations().size(); i++) {
                if (getTranslations().get(i).getLangCode().equals(langCode)) {
                    return i;
                }
            }
        }
        return -1;
    }


    int addTranslationValue(String langCode, String translation) {
        TranslationValue translationValue = new TranslationValue(langCode, translation);
        translations.add(translationValue);
        return (translations.size()-1);
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

    public void setStringOrig(String stringOrig) {
        this.stringOrig = stringOrig;
    }
}
