package sk.cw.jamlin;

/**
 * Created by marthol on 21.09.17.
 */
public class Translation {

    private String translateAction;
    private enum translateActions {
        EXTRACT, REPLACE
    }
    private Language language;

    public Translation(String translateAction) {
        if ( validAction(translateAction) ) {
            this.translateAction = translateAction;
        }
    }

    public Translation() {
        this.translateAction = translateActions.EXTRACT.toString().toLowerCase();
    }

    private static boolean validAction(String action) {
        for (translateActions c : translateActions.values()) {
            if (c.name().equals(action)) {
                return true;
            }
        }

        return false;
    }


    public String ExtractStrings(String source) {
        return "{}";
    }

    public String ReplaceStrings(String target, String extractedJson) {
        return "<html></html>";
    }
}
