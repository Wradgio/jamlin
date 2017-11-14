package sk.cw.jamlin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcel Zúbrik on 13.10.17.
 */
public class TranslationExtractResult {
    private List<TranslationBlock> translationBlocks = new ArrayList<TranslationBlock>();
    private TranslationConfig config;

    public TranslationExtractResult() {
    }

    TranslationExtractResult(TranslationConfig config) {
        this.config = config;
    }

    int addTranslationBlock(TranslationBlock translationBlock) {
        if ( !checkIfBlockExists(translationBlock) ) {
            translationBlocks.add(translationBlock);
            return (translationBlocks.size()-1);
        }
        return -1;
    }


    /**
     *
     * @return String languages
     */
    public ArrayList<String> getExtractResultLanguages() {
        ArrayList<String> languages = new ArrayList<String>();
        if (getTranslationBlocks().size()>0) {
            for (int i=0; i<getTranslationBlocks().size(); i++) {
                for (int j=0; j<getTranslationBlocks().get(i).getTranslationStrings().size(); j++) {
                    for (int k=0; k<getTranslationBlocks().get(i).getTranslationStrings().get(j).getTranslations().size(); k++) {
                        String langCode = getTranslationBlocks().get(i).getTranslationStrings().get(j).getTranslations().get(k).getLangCode();
                        if (!languages.contains(langCode)) {
                            languages.add(langCode);
                        }
                    }
                }
            }
        }
        return languages;
    }

    /**
     *
     * @param selector String
     * @return TranslationBlock
     */
    private TranslationBlock getBlockBySelector(String selector) {
        if (getTranslationBlocks().size()>0) {
            for (int i=0; i<getTranslationBlocks().size(); i++) {
                if ( getTranslationBlocks().get(i).getCssSelector()==selector ) {
                    return getTranslationBlocks().get(i);
                }
            }
        }
        return null;
    }


    String resultToJson() {
        //Gson gson = new Gson();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }


    List<TranslationBlock> getTranslationBlocks() {
        return translationBlocks;
    }


    /**
     * Loop translations of new file and add, update blocks and translations with languages to old result
     * @param oldResult TranslationExtractResult
     * @param newResult TranslationExtractResult
     * @return String
     */
    static String mergeTwoResults(TranslationExtractResult oldResult, TranslationExtractResult newResult) {
        //ArrayList<String> oldLanguages = oldResult.getExtractResultLanguages();
        if (newResult.getTranslationBlocks().size()>0) {
            for (int i=0; i<newResult.getTranslationBlocks().size(); i++) {
                // get oldResult block selector and check if exists in newResult
                TranslationBlock newBlock = newResult.getTranslationBlocks().get(i);
                int sameOldBlockId = oldResult.getTranslationBlockBySameData(newBlock);
                if ( sameOldBlockId > -1 ) {

                    // old block - update translate strings
                    if ( newResult.getTranslationBlocks().get(i).getTranslationStrings().size()>0 ) {
                        for (int j=0; j<newResult.getTranslationBlocks().get(i).getTranslationStrings().size(); j++) {
                            TranslationString newString = newResult.getTranslationBlocks().get(i).getTranslationStrings().get(j);
                            int sameOldStringId = oldResult.getTranslationBlocks().get(sameOldBlockId).getTranslationStringBySameData(newString);
                            if ( sameOldStringId > -1 ) { // translationString exists - UPDATE
                                // update stringOrig in oldResult using same translationString of newResult
                                oldResult.getTranslationBlocks().get(sameOldBlockId).getTranslationStrings().get(sameOldStringId).setStringOrig(newString.getStringOrig());

                                // old translationString - update values
                                if ( newResult.getTranslationBlocks().get(i).getTranslationStrings().get(j).getTranslations().size()>0 ) {
                                    for (int k=0; k<newResult.getTranslationBlocks().get(i).getTranslationStrings().get(j).getTranslations().size(); k++) {
                                        TranslationValue newValue = newResult.getTranslationBlocks().get(i).getTranslationStrings().get(j).getTranslations().get(k);
                                        // translationValue with same langCode
                                        int sameOldValueId = oldResult.getTranslationBlocks().get(sameOldBlockId).getTranslationStrings().get(sameOldStringId).getTranslationValueByLang(newValue.getLangCode());
                                        if ( sameOldValueId > -1 ) { // translation exists - UPDATE
                                            // update translation of that lang in oldResult using same translation of newResult
                                            oldResult.getTranslationBlocks().get(sameOldBlockId).getTranslationStrings().get(sameOldStringId).getTranslations().get(sameOldValueId).setTranslation(newValue.getTranslation());
                                        } else {
                                            // NO translation - INSERT
                                            oldResult.getTranslationBlocks().get(sameOldBlockId).getTranslationStrings().get(sameOldStringId).addTranslationValue(newValue.getLangCode(), newValue.getTranslation());
                                        }
                                    }
                                }

                            } else {
                                // NO translationString - INSERT
                                oldResult.getTranslationBlocks().get(sameOldBlockId).addTranslationString(newString.getStringOrig(), newString.getSelector());
                            }
                        }
                    }
                    
                } else {
                    // NO translationBlock - INSERT
                    oldResult.addTranslationBlock(newBlock);
                }
            }
        }

        return oldResult.resultToJson();
    }


    /**
     *
     * @param block TranslationBlock
     * @return boolean
     */
    public boolean checkIfBlockExists(TranslationBlock block) {
        boolean blockExists = false;
        if ( getTranslationBlocks().size()>0 ) {
            for (int i=0; i<getTranslationBlocks().size(); i++) {
                if ( getTranslationBlocks().get(i).equals(block) ) {
                    return true;
                }
            }
        }
        return blockExists;
    }


    /**
     *
     * @param block TranslationBlock
     * @return boolean
     */
    public int getTranslationBlockBySameData(TranslationBlock block) {
        if ( getTranslationBlocks().size()>0 ) {
            for (int i=0; i<getTranslationBlocks().size(); i++) {
                if ( getTranslationBlocks().get(i).equals(block) ) {
                    return i;
                }
            }
        }
        return -1;
    }
}
