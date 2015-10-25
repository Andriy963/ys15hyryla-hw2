
package ua.yandex.shad.autocomplete;

import java.util.*;

import ua.yandex.shad.tries.Tuple;
import ua.yandex.shad.tries.Trie;


public class PrefixMatches {

    private static final int MIN_SIZE = 3;
    private static final int DEFAULT_K = 3;
    
    private Trie trie;

    public int load(String... strings) {
        for (String string : strings) {
            String[] temp = string.split("\\s+");
            for (String str : temp) {
                if (str.length() >= MIN_SIZE) {
                    trie.add(new Tuple(str, str.length()));
                }
            }
        }
        return size();
    }

    public boolean contains(String word) {
        return trie.contains(word);
    }

    public boolean delete(String word) {
        return trie.delete(word);
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        return wordsWithPrefix(pref, DEFAULT_K);
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        if (pref.length() < MIN_SIZE) {
            throw new IllegalArgumentException();
        }
        Iterable<String> strings = trie.wordsWithPrefix(pref);
        
        int maxSize = pref.length() + k;
        Queue<String> q = new LinkedList<String>();
        for (String str : strings) {
            if (str.length() <= maxSize) {
                q.add(str);
            }
        }
        
        return q;
    }

    public int size() {
        return trie.size();
    }
}
