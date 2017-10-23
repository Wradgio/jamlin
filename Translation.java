package sk.cw.jamlin;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created by marthol on 21.09.17.
 */
public class Translation {

    private TranslationConfig config;
    private String translateAction;
    private enum translateActions {
        EXTRACT, REPLACE
    }
    private Language language;

    /*public Translation(Config config, String translateAction) {
        this.config = config;
        if ( validAction(translateAction) ) {
            this.translateAction = translateAction;
        }
    }*/

    public Translation(TranslationConfig config) {
        this.config = config;
    }

    public boolean validAction(String action) {
        for (translateActions c : translateActions.values()) {
            if (c.name().toLowerCase().equals(action)) {
                return true;
            }
        }
        return false;
    }


    public String extractStrings(String source) {
        this.translateAction = translateActions.EXTRACT.toString().toLowerCase();

        Document doc = null;
        try {
            doc = Jsoup.parse(source, "UTF-8");
            TranslationExtractResult translationExtractResult = new TranslationExtractResult(config);

            for (int i=0; i<config.getSelectors().size(); i++) {
                String selector = config.getSelectors().get(i).getSelector();
                String selectorName = config.getSelectors().get(i).getName();
                Elements selectorResult = doc.select(selector);

                TranslationBlock translationBlock = new TranslationBlock(selectorName, selector);

                for (int j=0; j<selectorResult.size(); j++) {
                    translationBlock.addTranslationString(selectorResult.get(j).text(), selectorResult.get(j).cssSelector());
                }
                translationExtractResult.addTranslationBlock(translationBlock);
            }

            // now we have result with all translates - time to export them to json output

            return translationExtractResult.resultToJson();
        } catch (Exception $e) {
            System.out.println($e.getMessage());
            $e.printStackTrace();
        }

        return "{}";
    }

    public String replaceStrings(String extractedJson, String target) {
        this.translateAction = translateActions.REPLACE.toString().toLowerCase();
//        extractedJson = extractedJson.trim();
//        Gson gson = new Gson();
//        TranslationExtractResult extractResult = gson.fromJson(extractedJson, TranslationExtractResult.class);
        return "<html></html>";
    }
}
