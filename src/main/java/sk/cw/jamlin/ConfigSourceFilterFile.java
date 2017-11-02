package sk.cw.jamlin;

import java.util.List;

/**
 * Created by marthol on 10.10.17.
 */
public class ConfigSourceFilterFile extends ConfigSourceFilter {

    public ConfigSourceFilterFile(String path, List<ConfigSourceFilterSelector> selectors) {
        super(path, selectors);
    }

    public ConfigSourceFilterFile(String path) {
        super(path);
    }
}
