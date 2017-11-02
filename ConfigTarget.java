package sk.cw.jamlin;

/**
 * Created by marthol on 10.10.17.
 */
public class ConfigTarget {
    private Boolean replaceFile = true;
    private String replacePattern = "*.*";

    public ConfigTarget(Boolean replaceFile, String replacePattern) {
        this.replaceFile = replaceFile;
        this.replacePattern = replacePattern;
    }

    public ConfigTarget() {
    }
}
