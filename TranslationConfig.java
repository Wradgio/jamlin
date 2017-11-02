package sk.cw.jamlin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Marcel Zúbrik on 12.10.17.
 */
public class TranslationConfig {
    String source = "";
    Date timestamp = new Date();
    String destination = "";
    List<ConfigSourceFilterSelector> selectors = new ArrayList<ConfigSourceFilterSelector>();
    Language language = new Language();

    public TranslationConfig(String source, String destination) {
        this.source = source;
        this.destination = destination;
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
}
