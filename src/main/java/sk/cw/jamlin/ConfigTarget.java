package sk.cw.jamlin;

/**
 * Created by marthol on 10.10.17.
 */
public class ConfigTarget {
    private Boolean saveHistory = true;
    private String replacePattern = "*.*";

    public ConfigTarget(Boolean saveHistory, String replacePattern) {
        this.saveHistory = saveHistory;
        this.replacePattern = replacePattern;
    }

    public ConfigTarget() {
    }

    public Boolean getSaveHistory() {
        return saveHistory;
    }

    public String getReplacePattern() {
        return replacePattern;
    }
}
