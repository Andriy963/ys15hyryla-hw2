package ua.yandex.shad.tries;

import java.util.*;
import java.io.*;

public class RWayTrie implements Trie {

    private static final int ALPHABET = 26;
    private static final char FIRSTCHAR = 'a';
    private Node root;
    
    private static class Node {
        private Object val;
        private Node[] next = new Node[ALPHABET];
    }
    
    @Override
    public void add(Tuple t) {
        root = add(root, t, 0);
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private Node add(Node x, Tuple t, int d) {
        if (x == null) { x = new Node(); }
        if (d == t.term.length()) {
            x.val = t.weight;
            return x;
        }
        char c = t.term.charAt(d);
        c = (char) (c - FIRSTCHAR);
        x.next[c] = add(x.next[c], t, d + 1);
        return x;
    }

    @Override
    public boolean contains(String word) {
        root = contains(root, word, 0);
        if (root == null) { return false; }
        else { return true; }
    }
    
    private Node contains(Node x, String word, int d) {
        if (x == null) { return null; }
        if (d == word.length()) { return x; }
        char c = word.charAt(d);
        c = (char) (c - FIRSTCHAR);
        return contains(x.next[c], word, d+1);
    }

    @Override
    public boolean delete(String word) {
        root = delete(root, word, 0);
        if (root == null) { return false; }
        else { return true; }
    }
    
    private Node delete(Node x, String word, int d) {
        if (x == null) { return null; }
        if (d == word.length()) {
            x.val = null;
        }
        else {
            char c = word.charAt(d);
            c = (char) (c - FIRSTCHAR);
            x.next[c] = delete(x.next[c], word, d+1);
        }
        if (x.val != null) {
            return x;
        }
        for (char c = 0; c < ALPHABET; c++) {
            if (x.next[c] != null) {
                return x;
            }
        }
        return null;
    }

    
    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s) {
        Queue<String> q = new LinkedList<String>();
        collect(contains(root, s, 0), s, q);
        return q;
    }
    
    private void collect(Node x, String s, Queue<String> q) {
        if (x == null) { return; }
        if (x.val != null) {
            q.add(s);
        }
        for (char c = 0; c < ALPHABET; c++) {
            collect(x.next[c], s + c, q);
        }
    }

    @Override
    public int size() {
        return size(root);
    }
    
    private int size(Node x) {
        if (x == null) { return 0; }
        int cnt = 0;
        if (x.val != null) {
            cnt++;
        }
        for (char c = 0; c < ALPHABET; c++) {
            cnt += size(x.next[c]);
        }
        return cnt;
    }
}
