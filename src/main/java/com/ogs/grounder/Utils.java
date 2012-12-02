package com.ogs.grounder;

import com.google.common.collect.Sets;

public class Utils {
    /**
     * @retuns given string, lower-cased, trimmed, and without any (,.$()&) characters
     */
    public static String cleanString(String s) {
        return s.toLowerCase().trim().replaceAll("(,|\\.|$|\\(|\\)|&)", "").replaceAll(" {2,}", " ");
    }

    /**
     * @returns set of indexes of given character
     */
    public static Set<Integer> indexOf(String s, char c) {
        Set<Integer> rtn = Sets.newHashSet();
        int index = 0;
        while (true) {
            index = s.indexOf(c, index);
            if (index == -1) {
                break;
            } else {
                rtn.add(index);
            }
        }
        return rtn;
    }
}
