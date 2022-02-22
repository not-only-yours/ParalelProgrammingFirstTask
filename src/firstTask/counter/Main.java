package firstTask.counter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        //unsync();
        //syncMethod();
        //syncBlock();
        BlockLock();
    }
    private static void unsync() {
        Runnable r = new Runnable1();
        Thread t = new Thread(r);
        Runnable r2 = new Runnable2();
        Thread t2 = new Thread(r2);
        t.start();
        t2.start();
    }
    private static void syncMethod() {
        Runnable r = new Runnable3();
        Thread t = new Thread(r);
        Runnable r2 = new Runnable4();
        Thread t2 = new Thread(r2);
        t.start();
        t2.start();
    }

    private static void syncBlock() {
        Runnable r = new Runnable5();
        Thread t = new Thread(r);
        Runnable r2 = new Runnable6();
        Thread t2 = new Thread(r2);
        t.start();
        t2.start();
    }

    private static void BlockLock() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Runnable task1 =
                () -> {
                    for (int i = 0; i < 1000; i++) {
                        lock.lock();
                        Counter.decrement();
                        lock.unlock();
                    }
                };
        Thread taskThread1 = new Thread(task1);

        Runnable task2 =
                () -> {
                    for (int i = 0; i < 1000; i++) {
                        lock.lock();
                        Counter.decrement();
                        lock.unlock();
                    }
                };
        Thread taskThread2 = new Thread(task2);
        System.out.println(Counter.getCount());
        taskThread1.start();
        taskThread2.start();

        taskThread1.join();
        taskThread2.join();
    }
}


class Runnable2 implements Runnable{
    public void run(){
        for(int i = 0; i < 100000; i++) {
            Counter.increment();
            System.out.println(Counter.getCount());
        }
    }
}

class Runnable1 implements Runnable{
    public void run(){
        for(int i = 0;i < 100000;i++) {
            Counter.decrement();
            System.out.println(Counter.getCount());
        }
    }
}


class Runnable3 implements Runnable{
    public void run(){
        for(int i = 0; i < 100000; i++) {
            Counter.incrementSync();
            System.out.println(Counter.getCountAtomic());
        }
    }
}

class Runnable4 implements Runnable{
    public void run(){
        for(int i = 0;i < 100000;i++) {
            Counter.decrementSync();
            System.out.println(Counter.getCountAtomic());
        }
    }
}


class Runnable5 implements Runnable{
    public void run(){

            for (int i = 0; i < 100000; i++) {
                synchronized (this) {
                Counter.incrementSync();
                System.out.println(Counter.getCountAtomic());
            }
        }
    }
}

class Runnable6 implements Runnable{
    public void run(){

            for (int i = 0; i < 100000; i++) {
                synchronized (this) {
                Counter.decrementSync();
                System.out.println(Counter.getCountAtomic());
            }
        }
    }
}



