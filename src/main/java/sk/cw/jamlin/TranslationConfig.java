package sk.cw.jamlin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Marcel Zúbrik on 12.10.17.
 */
public class TranslationConfig {
    private String source;
    private Date timestamp = new Date();
    private String destination;
    List<ConfigSourceFilterSelector> selectors = new ArrayList<>();
    private Language language = new Language();
    private ConfigTarget target;

    TranslationConfig(String source, String destination, ConfigTarget target) {
        this.source = source;
        this.destination = destination;
        this.target = target;
    }

    public String getSource() {
        return source;
    }

    public String getTimestamp() {
        return timestamp.toString();
    }

    public String getDestination() {
        return destination;
    }

    public List<ConfigSourceFilterSelector> getSelectors() {
        return selectors;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public ConfigTarget getTarget() {
        return target;
    }
}
