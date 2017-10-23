package sk.cw.jamlin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marthol on 11.10.17.
 */
public abstract class ConfigSourceFilter implements IConfigSourceFilter {
    String path = "";
    List<ConfigSourceFilterSelector> selectors = new ArrayList<ConfigSourceFilterSelector>();

    public ConfigSourceFilter(String path, List<ConfigSourceFilterSelector> selectors) {
        this.path = path;
        this.selectors = selectors;
    }

    public ConfigSourceFilter(String path) {
        this.path = path;
    }

    /**
     * @param name
     * @param selector
     * @return
     */
    public Boolean addSelector(String name, String selector) {
        if (selectors.size() > 0) {
            for (int i = 0; i < selectors.size(); i++) {
                if (selectors.get(i).getName() == name) {
                    return false;
                }
            }
        }
        selectors.add(new ConfigSourceFilterSelector(name, selector));

        return true;
    }


    public String getPath() {
        return path;
    }

    public List<ConfigSourceFilterSelector> getSelectors() {
        return selectors;
    }
}
