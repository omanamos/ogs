package com.ogs.datastore;
import static com.ogs.datastore.Utils.COMMON_WORDS;
import static com.ogs.grounder.Utils.cleanString;

public class ChunkedString {

    private Map<String, int[]> chunks;

    private ChunkedString() {}

    public ChunkedString(String str) {
        String cleanedStr = cleanString(str);
        String[] words = cleanedStr.split(" ");
        
        boolean prevWasNum = false;
    }
}
