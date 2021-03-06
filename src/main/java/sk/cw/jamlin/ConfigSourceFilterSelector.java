package sk.cw.jamlin;

/**
 * Created by marthol on 10.10.17.
 */
public class ConfigSourceFilterSelector {
    private String name;
    private String selector;
    private String type;
    private String attrName = "";
    private String filter = "";

    ConfigSourceFilterSelector(String name, String selector, String type) {
        this.name = name;
        this.selector = selector;
        this.type = type;
    }

    ConfigSourceFilterSelector(String name, String selector, String type, String attrName) {
        this.name = name;
        this.selector = selector;
        this.type = type;
        this.attrName = attrName;
    }

    ConfigSourceFilterSelector(String name, String selector, String type, String attrName, String filter) {
        this.name = name;
        this.selector = selector;
        this.type = type;
        this.attrName = attrName;
        this.filter = filter;
    }

    public String getName() {
        return name;
    }

    public String getSelector() {
        return selector;
    }

    public String getType() {
        return type;
    }

    public String getAttrName() {
        return attrName;
    }

    public String getFilter() {
        return filter;
    }
}
