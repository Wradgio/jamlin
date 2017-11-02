package sk.cw.jamlin;

import java.io.File;

/**
 * Created by Marcel Zúbrik on 29.10.2017.
 */
public class Files {

    // files and dirs
    public static void visitAllDirsAndFiles(File dir) {
        // spracovat(dir);

        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                visitAllDirsAndFiles(new File(dir, children[i]));
            }
        }
    }

    // only dirs
    public static void visitAllDirs(File dir) {
        if (dir.isDirectory()) {
            // spracovat(dir);
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                visitAllDirs(new File(dir, children[i]));
            }
        }
    }

    // only files
    public static void visitAllFiles(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                visitAllFiles(new File(dir, children[i]));
            }
        } else {
            // spracovat(dir);
        }
    }
}
