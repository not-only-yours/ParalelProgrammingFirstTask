package counter;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter {
    private static int count = 0;
    private static AtomicInteger c = new AtomicInteger(0);

    public static void increment() {
        count++;
    }

    public static void decrement() {
        count--;
    }

    public static int getCount() {
        return count;
    }

    public static void incrementAtomic() {
        c.incrementAndGet();
    }

    public static void decrementAtomic() {
        c.decrementAndGet();
    }

    public static int getCountAtomic() {
        return c.get();
    }
}
