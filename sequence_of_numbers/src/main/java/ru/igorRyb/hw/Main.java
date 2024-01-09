package ru.igorRyb.hw;

public class Main {

    public static void main(String[] args) {
        start(2);
    }

    public static void start(int threadCount) {
        for (int i = 0; i < threadCount; i++) {
            new Thread(new Runner()).start();
        }
    }

}