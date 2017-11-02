package sk.cw.jamlin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcel Zúbrik on 13.10.17.
 */
public class TranslationExtractResult {
    List<TranslationBlock> translationBlocks = new ArrayList<TranslationBlock>();
    TranslationConfig config;

    public TranslationExtractResult() {
    }

    public TranslationExtractResult(TranslationConfig config) {
        this.config = config;
    }

    public int addTranslationBlock(TranslationBlock translationBlock) {
        translationBlocks.add(translationBlock);
        return (translationBlocks.size()-1);
    }

    public String resultToJson() {
        //Gson gson = new Gson();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }


    public List<TranslationBlock> getTranslationBlocks() {
        return translationBlocks;
    }
}
