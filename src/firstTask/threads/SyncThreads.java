package firstTask.threads;

public class SyncThreads {
    static SyncThreads threads = new SyncThreads();

    public static void SyncThreadsCall() {

        Thread thread1 = threadCreator("/", threads);
        Thread thread2 = threadCreator("-", threads);
        thread1.start();
        thread2.start();
    }


    public static Thread threadCreator(String str, SyncThreads threads) {
        return new Thread(() -> {
            synchronized (threads) {
                for (int i = 0; i < 100; i++) {
                    System.out.print(str);
                    threads.notify();
                    try {
                        threads.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    threads.notify();
                }
            }
        });
    }
}
