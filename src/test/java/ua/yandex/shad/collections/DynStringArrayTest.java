package ua.yandex.shad.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Test;


public class DynStringArrayTest {
    
    private static final int defaultSize = 1;
    
    private DynStringArray dynStr = new DynStringArray(defaultSize);
    
    
    @Test
    public void testDynStringArrayConstructorNoArguments() {
        DynStringArray dynAr = new DynStringArray();
    }
    
    @Test
    public void testDynStringArrayConstructorWithIntArgument() {
        int size = 6;
        DynStringArray dynAr = new DynStringArray(size);
    }
    
    @Test
    public void testDynStringArrayConstructorWithStringArgument() {
        String[] str = {"fge", "sdgs", "fvsfd", "safav"}; 
        DynStringArray dynAr = new DynStringArray(str);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testAddZeroStrings() {
        String[] str = { };
        dynStr.add(str);
    }
    
    @Test
    public void testAddOneString() {
        String[] str = {"sdfa"};
        dynStr.add(str);
        
        String[] result = dynStr.getArrayWords();
        
        Assert.assertArrayEquals(str, result);
    }
    
    @Test
    public void testAddFiveStringsToArrayWithFiveCapacity() {
        String[] str = {"sdfa", "svav", "vadssav", "adfv", "sdvfa"};
        int size = 5;
        
        dynStr = new DynStringArray(size);
        dynStr.add(str);
        
        String[] result = dynStr.getArrayWords();
        
        Assert.assertArrayEquals(str, result);
    }
    
    @Test
    public void testAddFiveStringsToArrayWithOneCapacity() {
        String[] str = {"sdfa", "svav", "vadssav", "adfv", "sdvfa"};
        
        dynStr.add(str);
        
        String[] result = dynStr.getArrayWords();
        
        Assert.assertArrayEquals(str, result);
    }
    
    @Test
    public void testAddTwoArrays() {
        String[] str1 = {"sdfa", "svav", "vadssav"};
        String[] str2 = {"adfv", "sdvfa"};
        
        dynStr.add(str1);
        dynStr.add(str2);
        
        String[] result = dynStr.getArrayWords();
        String[] expectRes = {"sdfa", "svav", "vadssav", "adfv", "sdvfa"};
        
        Assert.assertArrayEquals(expectRes, result);
    }
    
    @Test
    public void testGetSize() {
        String[] str = {"sdfa", "svav", "vadssav", "adfv", "sdvfa"};
        dynStr = new DynStringArray(str);
        
        int expectRes = 5;
        int result = dynStr.getSize();
        
        assertEquals(expectRes, result);
    }
    
    @Test
    public void testGetArrayWords() {
        String[] str = {"sdfa", "svav", "vadssav", "adfv", "sdvfa"};
        dynStr = new DynStringArray(str);
        
        String[] expectRes = {"sdfa", "svav", "vadssav", "adfv", "sdvfa"};
        String[] result = dynStr.getArrayWords();
        
        Assert.assertArrayEquals(expectRes, result);
    }
    
    @Test
    public void testIteratorHasNextFalseResult() {
        Iterator<String> iterator = dynStr.iterator();
        
        boolean result = iterator.hasNext();
        boolean expectRes = false;
        
        assertEquals(result, expectRes);
    }
    
    @Test
    public void testIteratorHasNextTrueResult() {
        String[] strings = {"dsfgdfa"};
        
        dynStr = new DynStringArray(strings);
        Iterator<String> iterator = dynStr.iterator();
        
        boolean result = iterator.hasNext();
        boolean expectRes = true;
        
        assertEquals(result, expectRes);
    }
    
    @Test(expected = NoSuchElementException.class)
    public void testIteratorNextNoNextElement() {
        Iterator<String> iterator = dynStr.iterator();
        
        iterator.next();
    }
    
    @Test(expected = NoSuchElementException.class)
    public void testIteratorNextNoSecondNextElement() {
        String[] strings = {"fish"};
        
        dynStr = new DynStringArray(strings);
        Iterator<String> iterator = dynStr.iterator();
        
        iterator.next();
        iterator.next();
    }
    
    @Test
    public void testIteratorNextOneWordNext() {
        String[] strings = {"dsfgdfa"};
        
        dynStr = new DynStringArray(strings);
        Iterator<String> iterator = dynStr.iterator();
        
        String result = iterator.next();
        String expectRes = "dsfgdfa";
        
        assertEquals(result, expectRes);
    }
    
    @Test
    public void testIteratorNextThirdWord() {
        String[] strings = {"fish", "fishka", "finish", "honda", "gys"};
        
        dynStr = new DynStringArray(strings);
        Iterator<String> iterator = dynStr.iterator();
        
        iterator.next();
        iterator.next();
        String result = iterator.next();
        String expectRes = "finish";
        
        assertEquals(result, expectRes);
    }
}
