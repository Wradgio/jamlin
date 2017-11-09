package sk.cw.jamlin;

import java.util.Locale;
import java.util.MissingResourceException;

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


    /**
     *
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
        if ( !code.trim().isEmpty() ) {
            String[] codes = code.split("_");
            Locale lang = null;
            switch (codes.length) {
                case 3:
                    lang = new Locale(codes[0], codes[1], codes[2]);
                    break;
                case 2:
                    lang = new Locale(codes[0], codes[1]);
                    break;
                case 1:
                    lang = new Locale(code);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid locale: " + code);
            }
            if (!lang.toString().isEmpty()) {
                if (isValid(lang)) {
                    this.lang = lang;
                }
            }
        }
    }

    private boolean isValid(Locale locale) {
        try {
            return locale.getISO3Language() != null && locale.getISO3Country() != null;
        } catch (MissingResourceException e) {
            return false;
        }
    }


    /**
     *
     * @param filePath
     * @return
     */
    public static String getLangCodeFromFilePath(String filePath) {
        String fileNameLang = "";
        String pathSplit[] = filePath.split("-");
        if ( pathSplit.length>0 ) {
            pathSplit = pathSplit[pathSplit.length-1].split("\\.");
            if (pathSplit.length>0) {
                fileNameLang = pathSplit[0];
            }
        }

        return fileNameLang;
    }


    public String getCode() {
        return code;
    }

    public Locale getLang() {
        return lang;
    }

}
