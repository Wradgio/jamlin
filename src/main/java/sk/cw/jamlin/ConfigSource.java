package sk.cw.jamlin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marthol on 10.10.17.
 */
public class ConfigSource {
    private List<IConfigSourceFilter> directories = new ArrayList<IConfigSourceFilter>();
    private List<IConfigSourceFilter> files = new ArrayList<IConfigSourceFilter>();

    public ConfigSource(ArrayList<IConfigSourceFilter> directories, ArrayList<IConfigSourceFilter> files) {
        this.directories = directories;
        this.files = files;
    }

    public ConfigSource() {
    }


    public int addConfigSource(String type, String path, Boolean traverse) {
        ConfigSourceFilter sourceFilter = null;
        int lastItemIndex = -1;

        if ( type.equals("directory") || type.equals("dir") ) {
            sourceFilter = new ConfigSourceFilterDirectory(path, traverse);
            this.directories.add(sourceFilter);
            lastItemIndex = this.directories.size()-1;
        } else {
            sourceFilter = new ConfigSourceFilterFile(path);
            files.add(sourceFilter);
            lastItemIndex = files.size()-1;
        }

        return lastItemIndex;
    }

    public int addConfigSource(String type, String path) {
        return this.addConfigSource(type, path, false);
    }

    /* THE GETTERS & THE SETTERS */
    public List<IConfigSourceFilter> getDirectories() {
        return directories;
    }

    public List<IConfigSourceFilter> getFiles() {
        return files;
    }
}
