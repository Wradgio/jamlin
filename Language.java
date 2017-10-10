package sk.cw.jamlin;

import java.util.Locale;

/**
 * Created by marthol on 09.10.17.
 */
public class Language {

    private String code = "";
    private Locale lang = null;

    public Language(String code) {
        this.code = code;
        this.lang = new Locale(code);
    }

    public Language() {
    }

    public String getCode() {
        return code;
    }

    public Locale getLang() {
        return lang;
    }
}
