package ua.yandex.shad.tries;

import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

import static org.mockito.Mockito.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ua.yandex.shad.collections.DynStringArray;
import static ua.yandex.shad.tries.RWayTrie.Node;

@RunWith(MockitoJUnitRunner.class)
public class RWayTrieTest {
    
    
    @Mock private Tuple parrotMock;
    @Mock private Tuple fishMock;
    @Mock private Tuple finishMock;
    @Mock private Tuple fishkaMock;
    @Mock private Tuple hereMock;
    @Mock private Tuple hondaMock;
    
    private RWayTrie trie;
    private Node root;
    
    
    private Node getNode(String str) {
        Node temp = root;
        for (char c : str.toCharArray()) {
            if (temp.getNext(c) == null) {
                return null;
            }
            temp = temp.getNext(c);
        }
        return temp;
    }
    
    @Before
    public void setUp() {
        trie = new RWayTrie();
        root = trie.getRoot();
        
        mockInitialization();
        
        root.setNext('f', new Node());
        getNode("f").setValue(1);
        getNode("f").setNext('i', new Node());
        getNode("fi").setValue(2);
        getNode("fi").setNext('s', new Node());
        getNode("fis").setNext('h', new Node());
        getNode("fish").setValue(4);
        getNode("fish").setNext('k', new Node());
        getNode("fishk").setNext('a', new Node());
        getNode("fishka").setValue(6);
        getNode("fi").setNext('n', new Node());
        getNode("fin").setNext('i', new Node());
        getNode("fini").setNext('s', new Node());
        getNode("finis").setNext('h', new Node());
        getNode("finish").setValue(6);
        
    }
    
    public void mockInitialization() {
        when(parrotMock.getTerm()).thenReturn("parrot");
        when(parrotMock.getWeight()).thenReturn(6);
        when(fishMock.getTerm()).thenReturn("fish");
        when(fishMock.getWeight()).thenReturn(4);
        when(finishMock.getTerm()).thenReturn("finish");
        when(finishMock.getWeight()).thenReturn(6);
        when(fishkaMock.getTerm()).thenReturn("fishka");
        when(fishkaMock.getWeight()).thenReturn(6);
        when(hereMock.getTerm()).thenReturn("here");
        when(hereMock.getWeight()).thenReturn(4);
        when(hondaMock.getTerm()).thenReturn("honda");
        when(hondaMock.getWeight()).thenReturn(5);
    }
    
    
    @Test
    public void testRWAyTrieConstructor() {
        RWayTrie trie = new RWayTrie();
    } 
    
    @Test
    public void testAddCheckValue() {
        trie.add(parrotMock);
        int res = (int)getNode("parrot").getValue();
        int expectRes = 6;
        
        assertEquals(res, expectRes);
    }
    
    @Test
    public void testAddCheckForValueAtWrongPlace() {
        trie.add(parrotMock);
        Object res = getNode("parro").getValue();
        Object expectRes = null;
        
        assertEquals(res, expectRes);
    }
    
    @Test
    public void testAddCheckForExitingWord() {
        trie.add(finishMock);
        int res = (int)getNode("finish").getValue();
        int expectRes = 6;
        
        assertEquals(res, expectRes);
    }
    
    @Test
    public void testContainsCheckForTrueResult() {
        String checkWord = "finish";
        boolean res = trie.contains(checkWord);
        boolean expectRes = true;
        
        assertEquals(res, expectRes);
    }
    
    @Test
    public void testContainsCheckForFalseResult() {
        String checkWord = "parrot";
        boolean res = trie.contains(checkWord);
        boolean expectRes = false;
        
        assertEquals(res, expectRes);
    }
    
    @Test
    public void testDeleteCheckForExitingWord() {
        String deleteWord = "fish";
        boolean res = trie.delete(deleteWord);
        boolean expectRes = true;
        
        assertEquals(res, expectRes);
    }
    
    @Test
    public void testDeleteCheckForNotExitingWord() {
        String deleteWord = "fishakk";
        boolean res = trie.delete(deleteWord);
        boolean expectRes = false;
        
        assertEquals(res, expectRes);
    }
    
    @Test
    public void testDeleteCheckForContainsErasedWord() {
        String deleteWord = "fishka";
        trie.delete(deleteWord);
        boolean res = trie.delete("fishka");
        boolean expectRes = false;
        
        assertEquals(res, expectRes);
    }
    
    @Test
    public void testDeleteCheckForPartOfWord() {
        String deleteWord = "fishka";
        String subWord = "fish";
        trie.delete(deleteWord);
        boolean res = trie.contains(subWord);
        boolean expectRes = true;
        
        assertEquals(res, expectRes);
    }
    
    @Test
    public void testDeleteTwoWords() {
        String word1 = "fishka";
        String word2 = "finish";
        trie.delete(word1);
        boolean res = trie.delete(word2);
        boolean expectRes = true;
        
        assertEquals(res, expectRes);
    }
    
    @Test
    public void testDeleteOneWordTwice() {
        String deleteWord = "fishka";
        trie.delete(deleteWord);
        boolean res = trie.delete(deleteWord);
        boolean expectRes = false;
        
        assertEquals(res, expectRes);
    }
    
    @Test
    public void testDeleteAllWords() {
        trie.delete("fishka");
        trie.delete("fish");
        trie.delete("finish");
        trie.delete("fi");
        trie.delete("f");
        int res = trie.size();
        int expectRes = 0;
        
        assertEquals(res, expectRes);
    }
    
    @Test
    public void testSize() {
        int res = trie.size();
        int expectRes = 5;
        
        assertEquals(res, expectRes);
    }
    
    @Test
    public void testSizeWithAddingWords() {
        trie.add(parrotMock);
        trie.add(fishMock);
        trie.add(hereMock);
        int res = trie.size();
        int expectRes = 7;
        
        assertEquals(res, expectRes);
    }
    
    @Test
    public void testSizeWithDeletingWords() {
        trie.delete("fish");
        trie.delete("finish");
        int res = trie.size();
        int expectRes = 3;
        
        assertEquals(res, expectRes);
    }
    
    @Test
    public void testWordsWithPrefix_prefixIsAlsoWord() {
        String str = "fi";
        
        DynStringArray dynStr = trie.wordsWithPrefix(str);
        String[] result = dynStr.getArrayWords();
        String[] expectRes = {"fi", "fish", "finish", "fishka"};
        
        Assert.assertArrayEquals(result, expectRes);
    }
    
    @Test
    public void testWordsWithPrefix_prefixIsNotWord() {
        String str = "fis";
        
        DynStringArray dynStr = trie.wordsWithPrefix(str);
        String[] result = dynStr.getArrayWords();
        String[] expectRes = {"fish", "fishka"};
        
        Assert.assertArrayEquals(result, expectRes);
    }
    
    @Test
    public void testWordsWithPrefix_noWordsWithSuchPrefix() {
        String str = "fik";
        
        DynStringArray dynStr = trie.wordsWithPrefix(str);
        String[] result = dynStr.getArrayWords();
        String[] expectRes = {};
        
        Assert.assertArrayEquals(result, expectRes);
    }
    
    @Test
    public void testWordsWithPrefix_allWords() {
        String str = "f";
        
        DynStringArray dynStr = trie.wordsWithPrefix(str);
        String[] result = dynStr.getArrayWords();
        String[] expectRes = {"f", "fi", "fish", "finish", "fishka"};
        
        Assert.assertArrayEquals(result, expectRes);
    }
    
    @Test
    public void testWordsWithPrefix_noExitingPrefix() {
        String str = "parrot";
        
        DynStringArray dynStr = trie.wordsWithPrefix(str);
        String[] result = dynStr.getArrayWords();
        String[] expectRes = {};
        
        Assert.assertArrayEquals(result, expectRes);
    }
    
    @Test
    public void testWords() {
        DynStringArray dynStr = trie.words();
        String[] result = dynStr.getArrayWords();
        String[] expectRes = {"f", "fi", "fish", "finish", "fishka"};
        
        Assert.assertArrayEquals(result, expectRes);
    }
}