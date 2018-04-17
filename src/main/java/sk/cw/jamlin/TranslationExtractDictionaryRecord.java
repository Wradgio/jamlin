package sk.cw.jamlin;

import java.util.ArrayList;

/**
 * Created by Marcel Zúbrik on 26.3.2018.
 */
public class TranslationExtractDictionaryRecord {

    private String phrase;
    private Language language;
    private ArrayList<TranslationExtractDictionaryOccurrence> occurrences;

    public TranslationExtractDictionaryRecord(Language language, String phrase, ArrayList<TranslationExtractDictionaryOccurrence> occurrences) {
        this.language = language;
        this.phrase = phrase;
        this.occurrences = occurrences;
    }

    TranslationExtractDictionaryRecord(Language language, String phrase, TranslationExtractDictionaryOccurrence occurrence) {
        this.language = language;
        this.phrase = phrase;
        if (this.occurrences==null) {
            this.occurrences = new ArrayList<>();
            this.occurrences.add(occurrence);
        } else {
            this.addOccurrence(occurrence);
        }
    }


    /**
     *
     * @param occurence TranslationExtractDictionaryOccurrence
     * @return int
     */
    public ArrayList<Integer> findOccurrences(TranslationExtractDictionaryOccurrence occurence) {
        ArrayList<Integer> foundOccurences = new ArrayList<>();

        if (occurrences.size()>0) {
            for (int i = 0; i < occurrences.size(); i++) {
                if (occurrences.get(i).getPath().equals(occurence.getPath()) &&
                        occurrences.get(i).getSelector().equals(occurence.getSelector()) &&
                        occurrences.get(i).getType().equals(occurence.getType()) &&
                        occurrences.get(i).getBlockName().equals(occurence.getBlockName()) &&
                        (
                            occurrences.get(i).getAttrName()==null ||
                            (occurrences.get(i).getAttrName()!=null && occurrences.get(i).getAttrName().equals(occurence.getAttrName()))
                        )
                    ) {
                    foundOccurences.add(i);
                }
            }
        }

        return foundOccurences;
    }


    /**
     *
     * @param occurrence TranslationExtractDictionaryOccurrence
     * @return boolean
     */
    public boolean addOccurrence(TranslationExtractDictionaryOccurrence occurrence) {
        // find if any occurrence for this path, selector and and language exists
        ArrayList<Integer> foundOccurrences = findOccurrences(occurrence);

        // if not, add this
        if (foundOccurrences.size()<=0) {
            this.occurrences.add(occurrence);
            return true;
        }

        return false;
    }



    public String getPhrase() {
        return phrase;
    }

    public ArrayList<TranslationExtractDictionaryOccurrence> getOccurrences() {
        return occurrences;
    }

    public Language getLanguage() {
        return language;
    }
}
