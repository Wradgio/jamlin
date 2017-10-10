package sk.cw.jamlin;

import java.util.ArrayList;

/**
 * Created by marthol on 10.10.17.
 */
public class ConfigSourceFilter {
    private String path = "";
    private ArrayList<String> extensions = null;
    private Boolean traverse = false;
    private ArrayList<ConfigSourceFilterSelector> selectors = null;

    /**
     *
      * @param name
     * @param selector
     * @return Boolean
     */
    public Boolean addSelector(String name, String selector) {
        if (selectors.size()>0) {
            for (int i = 0; i < selectors.size(); i++) {
                if (selectors.get(i).getName()==name) {
                    return false;
                }
            }
        }
        selectors.add(new ConfigSourceFilterSelector(name, selector));

        return true;
    }
}
