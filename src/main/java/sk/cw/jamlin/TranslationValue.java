package sk.cw.jamlin;

/**
 * Created by Marcel Zúbrik on 26.10.2017.
 */
public class TranslationValue {
    private String langCode = null;
    private String translation = null;

    public TranslationValue(String langCode, String translation) {
        this.langCode = langCode;
        this.translation = translation;
    }

    public String getLangCode() {
        return langCode;
    }

    public String getTranslation() {
        return translation;
    }


    public boolean equals(TranslationValue other) {
        if ( this.langCode.equals(other.getLangCode()) && this.translation.equals(other.getTranslation()) ) {
            return true;
        }
        return false;
    }
}
