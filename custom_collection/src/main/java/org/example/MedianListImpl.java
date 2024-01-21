package org.example;


import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class MedianListImpl<T extends Number> {

    private static int size = 0;

    private final Queue<T> queue = new PriorityQueue<>();

    private final Queue<T> duplicates = new ConcurrentLinkedQueue<>();


    public int size() {
        return size;
    }

    public void add(T item) {
        if (item == null) {
            throw new NullPointerException("Item is null");
        } else {
            queue.add(item);
            duplicates.add(item);
            size++;
        }
    }

    public void remove(T item) {
        if (item == null) {
            throw new NullPointerException("Cannot remove because item is null");
        }
        if (duplicates.remove(item)) {
            queue.remove(item);
            size--;
        }
    };

    public double getMedian() {
        if (size == 0) {
            return Double.NaN;
        }
        int avg = size / 2;
        boolean isEven = size % 2 == 0;
        Queue<T> temp = new PriorityQueue<>(queue);
        T median = null;
        for (int i = 0; i <= avg; i++) {
            median = temp.poll();
        }
        if (isEven) {
            median = (T) Double.valueOf((median.doubleValue() + temp.peek().doubleValue()) / 2);
        }
        return median.doubleValue();
    }

}
