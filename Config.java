package sk.cw.jamlin;

import java.util.ArrayList;

/**
 * Created by marthol on 02.10.17.
 */
public class Config {

    public String type = "file";
    public String path = "";

//    private ArrayList<>
    private ConfigTarget target;
    private ArrayList<ConfigSource> sources;

    public Config(String type, String path) {
        this.type = type;
        this.path = path;
    }

    public Config() {

    }
}
