package ua.yandex.shad.tries;

import static org.junit.Assert.*;
import org.junit.Test;


public class TupleTest {
    
    @Test
    public void testTupleConstractorNoArguments() {
        Tuple t = new Tuple();
    }
    
    @Test
    public void testTupleConstractor() {
        String term = "a";
        int weight = 1;
        Tuple t = new Tuple(term, weight);
    }
    
    @Test
    public void testEqualsWithEqualArguments() {
        String term = "a";
        int weight = 1;
        Tuple t1 = new Tuple(term, weight);
        Tuple t2 = new Tuple(term, weight);
        
        boolean res = t1.equals(t2);
        boolean expectRes = true;
        
        assertEquals(res, expectRes);
    }
    
    @Test
    public void testEqualsWithDifferentTermArguments() {
        String term1 = "a";
        String term2 = "b";
        int weight = 1;
        Tuple t1 = new Tuple(term1, weight);
        Tuple t2 = new Tuple(term2, weight);
        
        boolean res = t1.equals(t2);
        boolean expectRes = false;
        
        assertEquals(res, expectRes);
    }
    
    @Test
    public void testEqualsWithDifferentWeightArguments() {
        String term = "a";
        int weight1 = 1;
        int weight2 = 2;
        Tuple t1 = new Tuple(term, weight1);
        Tuple t2 = new Tuple(term, weight2);
        
        boolean res = t1.equals(t2);
        boolean expectRes = false;
        
        assertEquals(res, expectRes);
    }
    
    @Test
    public void testEqualsWithDifferentArguments() {
        String term1 = "a";
        String term2 = "b";
        int weight1 = 1;
        int weight2 = 2;
        Tuple t1 = new Tuple(term1, weight1);
        Tuple t2 = new Tuple(term2, weight2);
        
        boolean res = t1.equals(t2);
        boolean expectRes = false;
        
        assertEquals(res, expectRes);
    }
    
    @Test
    public void testEqualsNullArgument() {
        String term = "a";
        int weight = 1;
        Tuple t1 = new Tuple(term, weight);
        Tuple t2 = null;
        
        boolean res = t1.equals(t2);
        boolean expectRes = false;
        
        assertEquals(res, expectRes);
    }
    
    @Test
    public void testEqualsStringArgument() {
        String term = "a";
        int weight = 1;
        Tuple t = new Tuple(term, weight);
        
        boolean res = t.equals(term);
        boolean expectRes = false;
        
        assertEquals(res, expectRes);
    }
    
    @Test
    public void testEqualsWithNullTerm() {
        String term = null;
        int weight = 1;
        Tuple t = new Tuple(term, weight);
        
        boolean res = t.equals(term);
        boolean expectRes = false;
        
        assertEquals(res, expectRes);
    }
    
    @Test
    public void testHashCode() {
        String term = "a";
        int weight = 1;
        Tuple t = new Tuple(term, weight);
        
        int res = t.hashCode();
        int expectRes = 42;
        
        assertEquals(res, expectRes);
    }
    
    @Test
    public void testGetTerm() {
        String term = "a";
        int weight = 1;
        Tuple t = new Tuple(term, weight);
        
        String res = t.getTerm();
        String expectRes = "a";
        
        assertEquals(res, expectRes);
    }
    
    @Test
    public void testGetWeight() {
        String term = "a";
        int weight = 1;
        Tuple t = new Tuple(term, weight);
        
        int res = t.getWeight();
        int expectRes = 1;
        
        assertEquals(res, expectRes);
    }
}
