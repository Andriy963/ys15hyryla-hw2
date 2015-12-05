package ua.yandex.shad.tries;

import java.util.Iterator;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

import static org.mockito.Mockito.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RWayTrieTest {
    
    
    @Mock private Tuple parrotMock;
    @Mock private Tuple fishMock;
    @Mock private Tuple finishMock;
    @Mock private Tuple fishkaMock;
    @Mock private Tuple hereMock;
    @Mock private Tuple heMock;
    
    private RWayTrie trie;
    
    @Before
    public void setUp() {
        trie = new RWayTrie();
        
        mockInitialization();
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
        when(heMock.getTerm()).thenReturn("he");
        when(heMock.getWeight()).thenReturn(2);
    }
    
    @Test
    public void testAddOneWord() {
        trie.add(parrotMock);
        
        String checkedWord = "parrot";
        boolean res = trie.contains(checkedWord);
        boolean expectRes = true;
        
        assertEquals(res, expectRes);
    }
    
    @Test
    public void testAddCheckForValueAtWrongPlace() {
        trie.add(parrotMock);
        
        String checkedWord = "parro";
        boolean res = trie.contains(checkedWord);
        boolean expectRes = false;
        
        assertEquals(res, expectRes);
    }
    
    @Test
    public void testAddSeveralWords() {
        trie.add(finishMock);
        trie.add(fishMock);
        trie.add(fishkaMock);
        
        int res = trie.size();
        int expectRes = 3;
        
        assertEquals(res, expectRes);
    }
    
    @Test
    public void testAddSeveralEqualsWords() {
        trie.add(finishMock);
        trie.add(fishMock);
        trie.add(fishMock);
        trie.add(fishkaMock);
        trie.add(fishkaMock);
        
        int res = trie.size();
        int expectRes = 3;
        
        assertEquals(res, expectRes);
    }
    
    @Test
    public void testContainsCheckForPrefixOfSomeWord() {
        trie.add(fishMock);
        
        String checkedWord = "fis";
        boolean res = trie.contains(checkedWord);
        boolean expectRes = false;
        
        assertEquals(res, expectRes);
    }
    
    @Test
    public void testContainsCheckForWrongWord() {
        trie.add(finishMock);
        
        String checkedWord = "fisish";
        boolean res = trie.contains(checkedWord);
        boolean expectRes = false;
        
        assertEquals(res, expectRes);
    }
    
    @Test
    public void testContainsCheckForValueBetweenSeveralWords() {
        trie.add(fishMock);
        trie.add(fishkaMock);
        
        String checkedWord = "fishk";
        boolean res = trie.contains(checkedWord);
        boolean expectRes = false;
        
        assertEquals(res, expectRes);
    }
    
    @Test
    public void testDeleteCheckForExitingWord() {
        trie.add(fishkaMock);
        
        String deleteWord = "fishka";
        boolean res = trie.delete(deleteWord);
        boolean expectRes = true;
        
        assertEquals(res, expectRes);
    }
    
    @Test
    public void testDeleteCheckForNotExitingWord() {
        trie.add(fishMock);
        trie.add(fishkaMock);
        trie.add(finishMock);
        
        String deleteWord = "fishakk";
        boolean res = trie.delete(deleteWord);
        boolean expectRes = false;
        
        assertEquals(res, expectRes);
    }
    
    @Test
    public void testDeleteCheckForContainsErasedWord() {
        trie.add(fishMock);
        trie.add(fishkaMock);
        trie.add(finishMock);
        
        String deleteWord = "fishka";
        trie.delete(deleteWord);
        boolean res = trie.contains("fishka");
        boolean expectRes = false;
        
        assertEquals(res, expectRes);
    }
    
    @Test
    public void testDeleteCheckForPartOfWord() {
        trie.add(fishMock);
        trie.add(fishkaMock);
        trie.add(finishMock);
        
        String deleteWord = "fishka";
        String subWord = "fish";
        trie.delete(deleteWord);
        boolean res = trie.contains(subWord);
        boolean expectRes = true;
        
        assertEquals(res, expectRes);
    }
    
    @Test
    public void testDeleteTwoWords() {
        trie.add(fishMock);
        trie.add(fishkaMock);
        trie.add(finishMock);
        
        String word1 = "fishka";
        String word2 = "finish";
        trie.delete(word1);
        boolean res = trie.delete(word2);
        boolean expectRes = true;
        
        assertEquals(res, expectRes);
    }
    
    @Test
    public void testDeleteOneWordTwice() {
        trie.add(fishMock);
        trie.add(fishkaMock);
        trie.add(finishMock);
        
        String deleteWord = "fishka";
        trie.delete(deleteWord);
        boolean res = trie.delete(deleteWord);
        boolean expectRes = false;
        
        assertEquals(res, expectRes);
    }
    
    @Test
    public void testDeleteAllWords() {
        trie.add(fishMock);
        trie.add(fishkaMock);
        trie.add(finishMock);
        trie.add(heMock);
        trie.add(hereMock);
        trie.add(parrotMock);
        
        trie.delete("fishka");
        trie.delete("fish");
        trie.delete("finish");
        trie.delete("here");
        trie.delete("he");
        trie.delete("parrot");
        int res = trie.size();
        int expectRes = 0;
        
        assertEquals(res, expectRes);
    }
    
    @Test
    public void testSizeWithAddingWords() {
        trie.add(parrotMock);
        trie.add(fishMock);
        trie.add(hereMock);
        
        int res = trie.size();
        int expectRes = 3;
        
        assertEquals(res, expectRes);
    }
    
    @Test
    public void testSizeWithDeletingWords() {
        trie.add(parrotMock);
        trie.add(fishMock);
        trie.add(hereMock);
        
        trie.delete("fish");
        int res = trie.size();
        int expectRes = 2;
        
        assertEquals(res, expectRes);
    }
    
    @Test
    public void testWordsWithPrefix_prefixIsAlsoWord() {
        trie.add(fishMock);
        trie.add(fishkaMock);
        trie.add(finishMock);
        
        String str = "fish";
        Iterable<String> dynStr = trie.wordsWithPrefix(str);
        Iterator<String> iterator = dynStr.iterator();
        
        String[] expectRes = {"fish", "fishka"};
        int iter = 0;
        boolean equalArrays = true;
        while(iterator.hasNext()) {
            if (!expectRes[iter++].equals(iterator.next())) {
                equalArrays = false;
            }
        }   
        
        assertTrue(equalArrays && (iter == 2));
    }
    
    @Test
    public void testWordsWithPrefix_prefixIsNotWord() {
        trie.add(fishMock);
        trie.add(fishkaMock);
        trie.add(finishMock);
        
        String str = "fi";
        Iterable<String> dynStr = trie.wordsWithPrefix(str);
        Iterator<String> iterator = dynStr.iterator();
        
        String[] expectRes = {"fish", "finish", "fishka"};
        int iter = 0;
        boolean equalArrays = true;
        while(iterator.hasNext()) {
            if (!expectRes[iter++].equals(iterator.next())) {
                equalArrays = false;
            }
        }   
        
        assertTrue(equalArrays && (iter == 3));
    }
    
    @Test
    public void testWordsWithPrefix_noWordsWithSuchPrefix() {
        trie.add(fishMock);
        trie.add(fishkaMock);
        trie.add(finishMock);
        
        String str = "fik";
        Iterable<String> dynStr = trie.wordsWithPrefix(str);
        Iterator<String> iterator = dynStr.iterator();
        
        String[] expectRes = {};
        int iter = 0;
        boolean equalArrays = true;
        while(iterator.hasNext()) {
            if (!expectRes[iter++].equals(iterator.next())) {
                equalArrays = false;
            }
        }   
        
        assertTrue(equalArrays && (iter == 0));
    }
    
    @Test
    public void testWordsWithPrefix_noExitingPrefix() {
        trie.add(fishMock);
        trie.add(fishkaMock);
        trie.add(finishMock);
        
        String str = "parrot";
        Iterable<String> dynStr = trie.wordsWithPrefix(str);
        Iterator<String> iterator = dynStr.iterator();
        
        String[] expectRes = {};
        int iter = 0;
        boolean equalArrays = true;
        while(iterator.hasNext()) {
            if (!expectRes[iter++].equals(iterator.next())) {
                equalArrays = false;
            }
        }   
        
        assertTrue(equalArrays && (iter == 0));
    }
    
    @Test
    public void testWords() {
        trie.add(fishMock);
        trie.add(fishkaMock);
        trie.add(finishMock);
        trie.add(heMock);
        trie.add(hereMock);
        trie.add(parrotMock);
        
        Iterable<String> dynStr = trie.words();
        Iterator<String> iterator = dynStr.iterator();
        
        String[] expectRes = {"he", "fish", "here", "finish", "fishka", "parrot"};
        int iter = 0;
        boolean equalArrays = true;
        while(iterator.hasNext()) {
            if (!expectRes[iter++].equals(iterator.next())) {
                equalArrays = false;
            }
        }   
        
        assertTrue(equalArrays && (iter == 6));
    }
}