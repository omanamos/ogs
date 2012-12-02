package com.ogs.datastore;

public class Utils {
    private static final Set<String> COMMON_WORDS = loadCommonWords("data/");

    // TODO: include cooking terms in this
    private static Set<String> loadCommonWords(String path) {
        Set<String> rtn = Sets.newHashSet();
        try {
            Scanner s = new Scanner(new File(path + "measurement-terms.txt"));
            while (s.hasNextLine()) {
                String line = s.nextLine();
                for (String word : line.split("=")) {
                    rtn.add(word.trim().toLowerCase());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rtn;
    }
}
