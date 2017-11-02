package sk.cw.jamlin;

import java.util.Locale;

/**
 * Created by marthol on 09.10.17.
 */
public class Language {

    private String code = "";
    private Locale lang = null;

    public Language() {
    }

    public Language(String code) {
        this.setCode(code);
    }


    public String getCode() {
        return code;
    }

    public Locale getLang() {
        return lang;
    }

    public void setCode(String code) {
        this.code = code;
        if ( !code.trim().isEmpty() ) {
            String[] codes = code.split("_");
            if (codes.length>=3) {
                this.lang = new Locale(codes[0], codes[1], codes[2]);
            } else if (codes.length>=2) {
                this.lang = new Locale(codes[0], codes[1]);
            } else {
                this.lang = new Locale(code);
            }
        }
    }
}
