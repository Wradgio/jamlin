package sk.cw.jamlin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marthol on 11.10.17.
 */
public abstract class ConfigSourceFilter implements IConfigSourceFilter {
    String path;
    private List<ConfigSourceFilterSelector> selectors = new ArrayList<ConfigSourceFilterSelector>();


    ConfigSourceFilter(String path, List<ConfigSourceFilterSelector> selectors) {
        this.path = path;
        this.selectors = selectors;
    }

    ConfigSourceFilter(String path) {
        this.path = path;
    }

    /**
     * @param name String
     * @param selector String
     * @param type String
     * @return Boolean
     */
    public Boolean addSelector(String name, String selector, String type) {
        if (selectors==null) {
            selectors = new ArrayList<ConfigSourceFilterSelector>();
        }
        if (selectors.size() > 0) {
            for (int i = 0; i < selectors.size(); i++) {
                if (selectors.get(i).getName().equals(name)) {
                    return false;
                }
            }
        }
        selectors.add(new ConfigSourceFilterSelector(name, selector, type));

        return true;
    }

    /**
     * @param name String
     * @param selector String
     * @param type String
     * @param attrName String
     * @return Boolean
     */
    public Boolean addSelector(String name, String selector, String type, String attrName) {
        if (selectors.size() > 0) {
            for (int i = 0; i < selectors.size(); i++) {
                if (selectors.get(i).getName().equals(name)) {
                    return false;
                }
            }
        }
        selectors.add(new ConfigSourceFilterSelector(name, selector, type, attrName));

        return true;
    }

    /**
     * @param name String
     * @param selector String
     * @param type String
     * @param attrName String
     * @param filter String
     * @return Boolean
     */
    public Boolean addSelector(String name, String selector, String type, String attrName, String filter) {
        if (selectors.size() > 0) {
            for (int i = 0; i < selectors.size(); i++) {
                if (selectors.get(i).getName().equals(name)) {
                    return false;
                }
            }
        }
        selectors.add(new ConfigSourceFilterSelector(name, selector, type, attrName, filter));

        return true;
    }


    public String getPath() {
        return path;
    }

    public List<ConfigSourceFilterSelector> getSelectors() {
        return selectors;
    }
}
