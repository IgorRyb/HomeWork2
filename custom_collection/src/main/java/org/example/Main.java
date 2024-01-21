package org.example;

public class Main {
    public static void main(String[] args) {
        MedianListImpl<Integer> medianList = new MedianListImpl<>();
        for (int i = 0; i <= 10000000; i++) {
            medianList.add(i);
        }
        System.out.println(medianList.getMedian());
    }
}