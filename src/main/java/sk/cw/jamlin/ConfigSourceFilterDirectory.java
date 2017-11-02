package sk.cw.jamlin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marthol on 10.10.17.
 */
public class ConfigSourceFilterDirectory extends ConfigSourceFilter {
    private ArrayList<String> extensions = new ArrayList<String>();;
    private Boolean traverse = false;
    private Boolean useChildConfigs = false;

    public ConfigSourceFilterDirectory(String path, Boolean traverse, List<ConfigSourceFilterSelector> selectors) {
        super(path, selectors);
        this.traverse = traverse;
    }

    public ConfigSourceFilterDirectory(String path, Boolean traverse) {
        super(path);
        this.traverse = traverse;
    }

    public ConfigSourceFilterDirectory(String path) {
        super(path);
    }


    /* THE GETTERS & THE SETTERS */
    public ArrayList<String> getExtensions() {
        return extensions;
    }

    public Boolean getTraverse() {
        return traverse;
    }

    public Boolean getUseChildConfigs() {
        return useChildConfigs;
    }
}
