package sk.cw.jamlin;

/**
 * Created by marthol on 21.09.17.
 */
public class TranslationString {

    private String stringOrig;
    private String stringNew;
    private String selector;

    public TranslationString(String stringOrig, String selector) {
        this.stringOrig = stringOrig;
        this.selector = selector;
    }

    public String getStringOrig() {
        return stringOrig;
    }

    public String getStringNew() {
        return stringNew;
    }

    public String getSelector() {
        return selector;
    }
}
