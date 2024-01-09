package ru.igorRyb.hw;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Runner implements Runnable{

    private final Lock lock = new ReentrantLock();

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            for (int i = 1; i <= 10; i++) {
                lock.lock();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
                System.out.println(Thread.currentThread().getName() + ": " + i);
            }
            for (int i = 9; i > 0; i--) {
                lock.lock();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
                System.out.println(Thread.currentThread().getName() + ": " + i);
            }
            Thread.currentThread().interrupt();
        }
    }
}
