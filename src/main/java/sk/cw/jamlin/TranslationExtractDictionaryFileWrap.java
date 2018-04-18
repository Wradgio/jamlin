package sk.cw.jamlin;

/**
 * Created by Marcel Zúbrik on 18.4.2018.
 */
public class TranslationExtractDictionaryFileWrap {
    public String path;
    public TranslationExtractDictionary dictionary;

    public TranslationExtractDictionaryFileWrap(String path, TranslationExtractDictionary dictionary) {
        this.path = path;
        this.dictionary = dictionary;
    }
}
