package ua.yandex.shad.tries;

import ua.yandex.shad.collections.DynStringArray;
import ua.yandex.shad.collections.MyQueue;

/**
 *
 * @author Andrii Hyryla
 */

public class RWayTrie implements Trie {

    private static final int ALPHABET = 26;
    private static final char FIRSTCHAR = 'a';
    private Node root = new Node();
    
    private static class Node {
        private Object val;
        private Node[] next = new Node[ALPHABET];
    }
    
    @Override
    public void add(Tuple t) {
        root = add(root, t, 0);
    }
    
    private Node add(Node xx, Tuple t, int d) {
        Node x = xx;
        if (x == null) { 
            x = new Node();
        }
        if (d == t.getWeight()) {
            x.val = t.getWeight();
            return x;
        }
        char c = t.getTerm().charAt(d);
        c = (char) (c - FIRSTCHAR);
        x.next[c] = add(x.next[c], t, d + 1);
        return x;
    }

    @Override
    public boolean contains(String word) {
        Node res = contains(root, word, 0);
        if (res == null) {
            return false;
        }
        else {
            return res.val != null;
        }
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
        if (!contains(word)) {
            return false;
        }
        int sizeTo = size();
        root = delete(root, word, 0);
        return size() == sizeTo - 1;
    }
    
    private Node delete(Node x, String word, int d) {
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
        DynStringArray dyn = new DynStringArray();
        bfs(contains(root, s, 0), s, dyn);
        return dyn;
    }

    private void bfs(Node x, String s, DynStringArray dyn) {
        
        MyQueue<Node> q = new MyQueue<>();
        MyQueue<String> strings = new MyQueue<>();
        q.add(x);
        strings.add(s);
        
        Node curNode;
        String curString;
        
        while (!q.isEmpty()) {
            curNode = q.remove();
            curString = strings.remove();
            if (curNode == null) {
                continue;
            }
            if (curNode.val != null) {
                dyn.add(curString);
            }
            for (char c = 0; c < ALPHABET; c++) {
                q.add(curNode.next[c]);
                char ch = (char) (c + FIRSTCHAR);
                strings.add(curString + ch);
            }
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
