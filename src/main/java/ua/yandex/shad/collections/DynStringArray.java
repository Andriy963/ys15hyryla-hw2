package ua.yandex.shad.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DynStringArray implements Iterable<String> {

    private static final int MIN_SIZE = 5;
    
    private String[] words;
    private int actualSize;
    
    public DynStringArray() {
        this.words = new String[MIN_SIZE];
        this.actualSize = 0;
    }
    
    public DynStringArray(int size) {
        this.words = new String[size];
        this.actualSize = 0;
    }
    
    public DynStringArray(String ... strings) {
        this.words = strings.clone();
        this.actualSize = strings.length;
    }
    
    public void add(String ... strings) {
        if (strings.length == 0) {
            throw new IllegalArgumentException();
        }
        
        int freeSize = words.length - actualSize;
        
        if (freeSize < strings.length) {
            int newSizeOfArray = words.length;
            while (newSizeOfArray < actualSize + strings.length) {
                newSizeOfArray = newSizeOfArray * 2;
            }
            
            String[] tempArray = new String[newSizeOfArray];
            System.arraycopy(words, 0, tempArray, 0, actualSize);
            
            words = tempArray;
        }
        
        for (String str : strings) {
            words[actualSize] = str;
            actualSize++;
        }
    }
    
    public int getSize() {
        return actualSize;
    }
    
    public String[] getArrayWords() {
        String[] tempArray = new String[actualSize];
        
        System.arraycopy(words, 0, tempArray, 0, actualSize);
        return tempArray;
    }
    
    
    public Iterator<String> iterator() {
        return new DynStringArrayIterator();
    }
    
    private class DynStringArrayIterator implements Iterator<String> {
        
        private int size = 0;
        
        public boolean hasNext() {
            return size < actualSize;
        }
        
        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return words[size++];
        }
    }
}
