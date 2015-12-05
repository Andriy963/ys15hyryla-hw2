package ua.yandex.shad.tries;

public class Tuple {
    private final String term;
    private final int weight;

    public Tuple() {
        this.term = "";
        this.weight = 0;
    }
    public Tuple(String term, int weight) {
        this.term = term;
        this.weight = weight;
    }
    
    public String getTerm() {
        return term;
    }
    
    public int getWeight() {
        return weight;
    }
    
    @Override
    public boolean equals(Object equal) {
        if (equal == null) {
            return false;
        }
        if (this.getClass() != equal.getClass()) {
            return false;
        }
        
        Tuple testOb = (Tuple) equal;
        
        if (testOb.term == null) {
            return false;
        }
        return this.term.equals(testOb.term) && this.weight == testOb.weight;
    }
    
    @Override
    public int hashCode() {
        return 42;
    }
}
