package ua.yandex.shad.autocomplete;

import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

import static org.mockito.Mockito.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.yandex.shad.collections.DynStringArray;

import ua.yandex.shad.tries.Tuple;

@RunWith(MockitoJUnitRunner.class)
public class PrefixMatchesTest {
    
    private PrefixMatches prefMatch;
    @Mock private Tuple parrotMock;
    @Mock private Tuple fishMock;
    @Mock private Tuple finishMock;
    @Mock private Tuple fishkaMock;
    @Mock private Tuple hereMock;
    
    @Before
    public void setUp() {
        prefMatch = new PrefixMatches();
        
        mockInitialization();
        prefMatch.getTrie().add(fishMock);
        prefMatch.getTrie().add(fishkaMock);
        prefMatch.getTrie().add(finishMock);
        prefMatch.getTrie().add(parrotMock);
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
    }
    
    
    @Test
    public void testPrefixMatchesTest_Constructor() {
        PrefixMatches pref = new PrefixMatches();
    } 

    @Test
    public void testLoadEmptyArray() {
        String[] str = {};
        
        PrefixMatches pref = new PrefixMatches();
        int result = pref.load(str);
        int expectRes = 0;
        
        assertEquals(result, expectRes);
    }
    
    @Test
    public void testLoadSingleElement() {
        String str = "fish";
        
        PrefixMatches pref = new PrefixMatches();
        int result = pref.load(str);
        int expectRes = 1;
        
        assertEquals(result, expectRes);
    }
    
    @Test
    public void testLoadArrayOfElements() {
        String[] str = {"fish", "fishka", "finish"};
        
        PrefixMatches pref = new PrefixMatches();
        int result = pref.load(str);
        int expectRes = 3;
        
        assertEquals(result, expectRes);
    }
    
    @Test
    public void testLoadElementsToExisting() {
        String[] str = {"fis", "fishkas"};
        
        
        int result = prefMatch.load(str);
        int expectRes = 6;
        
        assertEquals(result, expectRes);
    }
    
    @Test
    public void testLoadElementsWithSmallLenght() {
        String[] str = {"fi", "g", "pa"};
        
        
        int result = prefMatch.load(str);
        int expectRes = 4;
        
        assertEquals(result, expectRes);
    }
    
    @Test
    public void testLoadElementsWithDifferentLenght() {
        String[] str = {"fis", "g", "par", "f", "here"};
        
        
        int result = prefMatch.load(str);
        int expectRes = 7;
        
        assertEquals(result, expectRes);
    }
    
    @Test
    public void testLoadExitingElement() {
        String str = "fish";
        
        
        int result = prefMatch.load(str);
        int expectRes = 4;
        
        assertEquals(result, expectRes);
    }
    
    @Test
    public void testLoadExitingAndNotExitingElements() {
        String[] str = {"fish", "fin", "finish", "gf", "parrot", "h", "here"};
        
        
        int result = prefMatch.load(str);
        int expectRes = 6;
        
        assertEquals(result, expectRes);
    }
    
    @Test
    public void testContainsExitingWord() {
        String str = "fish";
        
        boolean result = prefMatch.contains(str);
        boolean expectRes = true;
        
        assertEquals(result, expectRes);
    }
    
    @Test
    public void testContainsNoExitingWord() {
        String str = "here";
        
        boolean result = prefMatch.contains(str);
        boolean expectRes = false;
        
        assertEquals(result, expectRes);
    }
    
    @Test
    public void testContainsPartOfExitingWord() {
        String str = "fis";
        
        boolean result = prefMatch.contains(str);
        boolean expectRes = false;
        
        assertEquals(result, expectRes);
    }
    
    @Test
    public void testContainsTwoOperations() {
        String str = "fish";
        
        prefMatch.contains(str);
        boolean result = prefMatch.contains(str);
        boolean expectRes = true;
        
        assertEquals(result, expectRes);
    }
    
    @Test
    public void testDeleteExitingElement() {
        String str = "fish";
        
        boolean result = prefMatch.delete(str);
        boolean expectRes = true;
        
        assertEquals(result, expectRes);
    }
    
    @Test
    public void testDeleteNoExitingElement() {
        String str = "fishk";
        
        boolean result = prefMatch.delete(str);
        boolean expectRes = false;
        
        assertEquals(result, expectRes);
    }
    
    @Test
    public void testDeleteOneElementTwice() {
        String str = "fish";
        
        prefMatch.delete(str);
        boolean result = prefMatch.delete(str);
        boolean expectRes = false;
        
        assertEquals(result, expectRes);
    }
    
    @Test
    public void testDeletePartOfTheWord() {
        String str = "fis";
        
        prefMatch.delete(str);
        boolean result = prefMatch.contains("fish");
        boolean expectRes = true;
        
        assertEquals(result, expectRes);
    }
    
    @Test
    public void testDeleteAllWords() {
        
        prefMatch.delete("fish");
        prefMatch.delete("fishka");
        prefMatch.delete("finish");
        prefMatch.delete("parrot");
        
        int size = prefMatch.size();
        int expectRes = 0;
        
        assertEquals(size, expectRes);
    }
    
    @Test
    public void testSizeNewObject() {
        
        PrefixMatches pref = new PrefixMatches();
        
        int result = pref.size();
        int expectRes = 0;
        
        assertEquals(result, expectRes);
    }
    
    @Test
    public void testSizeObjectWithElements() {
        
        int result = prefMatch.size();
        int expectRes = 4;
        
        assertEquals(result, expectRes);
    }
    
    @Test
    public void testSizeAfterLoading() {
        
        prefMatch.load("here");
        int result = prefMatch.size();
        int expectRes = 5;
        
        assertEquals(result, expectRes);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testWordsWithPrefixExeptionSmallSize() {
        String word = "f";
        prefMatch.wordsWithPrefix(word);
    }
    
    @Test
    public void testWordsWithPrefixOneWord() {
        String word = "fis";
        int k = 2;
        DynStringArray d = prefMatch.wordsWithPrefix(word, k);
        String[] result = d.getArrayWords();
        String[] expectRes = {"fish"};
        
        Assert.assertArrayEquals(result, expectRes);
    }
    
    @Test
    public void testWordsWithPrefixDefaultK() {
        String word = "fish";
        
        DynStringArray d = prefMatch.wordsWithPrefix(word);
        String[] result = d.getArrayWords();
        String[] expectRes = {"fish", "fishka"};
        
        Assert.assertArrayEquals(result, expectRes);
    }
    
    @Test
    public void testWordsWithPrefixWithLoadDefaulK() {
        prefMatch.load("fisha");
        prefMatch.load("fishakjd");
        prefMatch.load("fishaka");
        prefMatch.load("fishechka");
        String word = "fish";
        
        DynStringArray d = prefMatch.wordsWithPrefix(word);
        String[] result = d.getArrayWords();
        String[] expectRes = {"fish", "fisha", "fishka"};
        
        Assert.assertArrayEquals(result, expectRes);
    }
    
    @Test
    public void testWordsWithPrefixWithLoadSimpleK() {
        prefMatch.load("fisha");
        prefMatch.load("fishakjd");
        prefMatch.load("fishaka");
        prefMatch.load("fishechka");
        String word = "fish";
        int k = 7;
        
        DynStringArray d = prefMatch.wordsWithPrefix(word, k);
        String[] result = d.getArrayWords();
        String[] expectRes = {"fish", "fisha", "fishka", "fishaka", "fishakjd", "fishechka"};
        
        Assert.assertArrayEquals(result, expectRes);
    }
}