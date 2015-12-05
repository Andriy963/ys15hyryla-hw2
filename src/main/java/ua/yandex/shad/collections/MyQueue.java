package ua.yandex.shad.collections;

/**
 *
 * @author Andrii Hyryla
 * 
 * @param <Item>
 */
public class MyQueue<Item> {
    
    private Node first;
    private Node last;
    private int n;
    
    private class Node {
        private Item item;
        private Node next;
    }
    
    public boolean isEmpty() {
        return first == null;
    }
    
    public void add(Item item) {
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) {
            first = last;
        }
        else {
            oldLast.next = last;
        }
        n++;
    }
    
    public Item remove() {
        Item item = first.item;
        first = first.next;
        if (isEmpty()) {
            last = null;
        }
        n--;
        return item;
    }
}
