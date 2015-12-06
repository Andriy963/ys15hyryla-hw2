package ua.yandex.shad.autocomplete;

import java.util.Iterator;
import java.util.NoSuchElementException;
import ua.yandex.shad.collections.DynStringArray;
import ua.yandex.shad.tries.RWayTrie;
import ua.yandex.shad.tries.Tuple;
import ua.yandex.shad.tries.Trie;

/**
 *
 * @author Andrii Hyryla
 */
public class PrefixMatches {

    private static final int MIN_SIZE = 3;
    private static final int DEFAULT_K = 3;

    private Trie trie = new RWayTrie();

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

        /*Iterable<String> strings = trie.wordsWithPrefix(pref);
         Iterator<String> iterator = strings.iterator();

         int maxSize = pref.length() + k;
         DynStringArray q = new DynStringArray();
         String current;

         while (iterator.hasNext()) {
         current = iterator.next();
         if (current.length() < maxSize) {
         q.add(current);
         }
         }
        
         return q;*/
        Iterator<String> iterator = trie.wordsWithPrefix(pref).iterator();
        PartIterable words = new PartIterable();
        PartIterator iter = new PartIterator(iterator, k);
        words.setIterator(iter);

        return words;
        //return new PartIterable(trie.wordsWithPrefix(pref), k);
    }

    private static class PartIterable implements Iterable<String> {

        private PartIterator iterator = new PartIterator(null, 0);

        @Override
        public Iterator<String> iterator() {
            return iterator;
        }

        public void setIterator(PartIterator iterator) {
            this.iterator = iterator;
        }
    }

    private static class PartIterator implements Iterator<String> {

        private Iterator<String> iterator;
        private String nextWord;
        private int prevLen;
        private int k;

        public PartIterator(Iterator<String> iterator, int k) {
            this.iterator = iterator;
            this.k = k;
            if (iterator != null && iterator.hasNext()) {
                this.nextWord = iterator.next();
            }
        }

        @Override
        public boolean hasNext() {
            return !(iterator == null || nextWord == null
                    || (nextWord.length() - prevLen) >= k && k == 0);
        }

        @Override
        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            String returnValue = nextWord;

            if (!iterator.hasNext()) {
                nextWord = null;
            } 
            else {
                nextWord = iterator.next();
            }
            if (returnValue.length() != prevLen) {
                k--;
                prevLen = returnValue.length();
            }

            return returnValue;
        }
    }

    public int size() {
        return trie.size();
    }

    public Trie getTrie() {
        return trie;
    }
}
