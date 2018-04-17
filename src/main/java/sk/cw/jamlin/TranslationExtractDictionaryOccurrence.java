package sk.cw.jamlin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcel Zúbrik on 27.3.2018.
 */
public class TranslationExtractDictionaryOccurrence {

    private String path;
    private String selector;
    private String type;
    private String blockName;
    private String attrName;
    private List<TranslationString> translationStrings;

    TranslationExtractDictionaryOccurrence(String phrase, String path, TranslationBlock block) {
        this.path = path;
        this.selector = block.getCssSelector();
        this.type = block.getType();
        this.blockName = block.getName();
        if ( !block.getAttrName().trim().isEmpty() ) {
            this.attrName = block.getAttrName();
        }
        this.translationStrings = new ArrayList<>();
        for ( TranslationString translationString: block.getTranslationStrings() ) {
            if ( translationString.getStringOrig().equals(phrase) ) {
                this.translationStrings.add(translationString);
            }
        }

    }

    public String getPath() {
        return path;
    }

    public String getSelector() {
        return selector;
    }

    public String getType() {
        return type;
    }

    public String getBlockName() {
        return blockName;
    }

    public String getAttrName() {
        return attrName;
    }

    public List<TranslationString> getTranslationStrings() {
        return translationStrings;
    }
}
