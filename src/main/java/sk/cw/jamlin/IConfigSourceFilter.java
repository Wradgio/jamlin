package sk.cw.jamlin;

import java.util.List;

/**
 * Created by marthol on 11.10.17.
 */
public interface IConfigSourceFilter {
    String path = "";
    List<ConfigSourceFilterSelector> selectors = null;

    Boolean addSelector(String name, String selector, String type);

    Boolean addSelector(String name, String selector, String type, String attrName);

    Boolean addSelector(String name, String selector, String type, String attrName, String filter);

    String getPath();
    List<ConfigSourceFilterSelector> getSelectors();
}
