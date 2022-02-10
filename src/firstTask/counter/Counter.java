package firstTask.counter;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter {
    private static int count = 0;

    public static void increment() {
        count++;
    }

    public static void decrement() {
        count--;
    }

    public static int getCount() {
        return count;
    }

    public static synchronized void incrementSync() {
        count++;
    }

    public static synchronized void decrementSync() {
        count--;
    }


    public static int getCountAtomic() {
        return count;
    }
}
