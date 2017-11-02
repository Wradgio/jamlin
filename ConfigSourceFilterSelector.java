package sk.cw.jamlin;

/**
 * Created by marthol on 10.10.17.
 */
public class ConfigSourceFilterSelector {
    private String name;
    private String selector;

    public ConfigSourceFilterSelector(String name, String selector) {
        this.name = name;
        this.selector = selector;
    }

    public String getName() {
        return name;
    }

    public String getSelector() {
        return selector;
    }
}
