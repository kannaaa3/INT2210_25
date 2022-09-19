import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int n;

    private Item[] q;

    // construct an empty randomized queue
    public RandomizedQueue() {
        n = 0;
        q = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (n == q.length) resize(q.length * 2);
        q[n++] = item;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) copy[i] = q[i];
        q = copy;
    }

    // remove and return a random item
    public Item dequeue() {
        if (n == 0) throw new NoSuchElementException();
        if (n == q.length / 4) resize(q.length / 2);

        int  ranId   = StdRandom.uniformInt(n);
        Item oldItem = q[ranId];

        if (ranId != n - 1) q[ranId] = q[n - 1]; //swap the last element and q[id]

        q[n - 1] = null;
        n--;

        return oldItem;
    }


    // return a random item (but do not remove it)
    public Item sample() {
        if (n == 0) throw new NoSuchElementException();

        int ranId = StdRandom.uniformInt(n);
        return q[ranId];
    }


    // return an independent iterator over items in random order

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int    current  = 0;
        private Item[] randomIt = (Item[]) new Object[n];

        public RandomizedQueueIterator() {
            for (int i = 0; i < n; i++) randomIt[i] = q[i];
            StdRandom.shuffle(randomIt);
            for (int i = 0; i < n; i++) {
                q[i] = randomIt[i];
            }

        }

        public boolean hasNext() {
            return current < n;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return q[current++];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

//    public String toString() {
//        String ans = "[";
//        for (int i = 0; i < n; i++) {
//            ans += q[i] + ", ";
//        }
//        ans += "]";
//        return ans;
//    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> m = new RandomizedQueue<>();
        m.enqueue(1);
        m.dequeue();
        m.enqueue(2);
        m.enqueue(3);
        System.out.println(m.dequeue());
        ;
        System.out.println(m);
        m.enqueue(4);
        m.enqueue(5);
        m.dequeue();
        System.out.println(m);
        m.enqueue(6);
        m.dequeue();
        m.enqueue(7);
        System.out.println(m);

        for (Integer s : m)
            System.out.print(s + ", ");
//        System.out.println();
//        for (Integer s : m)
//            System.out.print(s + ", ");
//        System.out.println();
//        for (Integer s : m)
//            System.out.print(s + ", ");
//        System.out.println();
//        for (Integer s : m)
//            System.out.print(s + ", ");
//        System.out.println();
//        for (Integer s : m)
//            System.out.print(s + ", ");
//        System.out.println();
//        for (Integer s : m)
//            System.out.print(s + ", ");

    }

}