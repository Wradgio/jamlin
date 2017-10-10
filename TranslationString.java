package sk.cw.jamlin;

/**
 * Created by marthol on 21.09.17.
 */
public abstract class TranslationString {

    private String stringOrig;
    private String stringNew;
    private String selector;

    public TranslationString(String stringToTranslate) {
        this.stringOrig = stringToTranslate;
    }
}
