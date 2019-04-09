package sk.cw.jamlin;

import java.util.ArrayList;

/**
 * Created by Marcel Zúbrik on 26.3.2018.
 */
public class TranslationExtractDictionary {

    private ArrayList<TranslationExtractDictionaryRecord> records;

    TranslationExtractDictionary(ArrayList<TranslationExtractDictionaryRecord> records) {
        this.records = records;
    }

    // METHODS

    /**
     *
     * @param phrase String
     * @return ArrayList<TranslationExtractDictionaryRecord>
     */
    private ArrayList<Integer> findRecordByPhrase(String phrase, Language language) {
        ArrayList<Integer> found = new ArrayList<>();

        for (int i = 0; i < records.size(); i++) {
            if (records.get(i)!=null && records.get(i).getPhrase()!=null &&
                    records.get(i).getPhrase().equals(phrase) && records.get(i).getLanguage()!=null &&
                    records.get(i).getLanguage().equals(language)) {
                found.add(i);
            }
        }

        return found;
    }

    /**
     *
     * @param language Language
     * @param path String
     * @param block TranslationBlock
     * @return boolean
     */
    private boolean addRecord(String phrase, String selector, Language language, String path, TranslationBlock block) {
        // find if any record for this phrase and language exists
        ArrayList<Integer> foundRecords = findRecordByPhrase(phrase, language);
        // create occurrence of path & related block
        TranslationExtractDictionaryOccurrence occurrence = new TranslationExtractDictionaryOccurrence(phrase, path, block);

        if (foundRecords.size()>0) {

            // loop found records and find if they have this occurrence
            for (Integer foundRecordIndex : foundRecords) {
                TranslationExtractDictionaryRecord record = this.records.get(foundRecordIndex);
                // if no occurrence found, add it
                if (record.findOccurrences(occurrence).size() < 1) {
                    boolean occurrenceAdded = record.addOccurrence(occurrence);
                    // update record
                    if (occurrenceAdded) {
                        this.records.set(foundRecordIndex, record);
                        return true;
                    }
                }
            }

        } else {
            TranslationExtractDictionaryRecord record = new TranslationExtractDictionaryRecord(language, phrase, occurrence);
            this.records.add(record);
            return true;
        }

        return false;
    }


    void addRecords(Language language, String path, TranslationExtractResult extractResult) {
        //Loop translation blocks from result, get its data and translation strings array, put in into TranslationExtractDictionaryOccurence
        if (extractResult.getTranslationBlocks().size()>0) {
            for (TranslationBlock block: extractResult.getTranslationBlocks()) {
                for (TranslationString phrase: block.getTranslationStrings()) {
                    boolean addRecordResult = this.addRecord(phrase.getStringOrig(), phrase.getSelector(), language, path, block);
                }
            }
        }
    }


    // GETTERS & SETTERS

    public ArrayList<TranslationExtractDictionaryRecord> getRecords() { return records; }

    public void setRecords(ArrayList<TranslationExtractDictionaryRecord> records) { this.records = records; }
}
