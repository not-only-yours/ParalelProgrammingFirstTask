package firstTask.threads;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        //firstPart();

        SyncThreads.SyncThreadsCall();
    }

    public static void firstPart() {
        Runnable r = new Runnable1();
        Thread t = new Thread(r);
        Runnable r2 = new Runnable2();
        Thread t2 = new Thread(r2);
        t.start();
        t2.start();
    }
}


class Runnable2 implements Runnable{
    public void run(){
        for(int i=0;i<100;i++) {
            System.out.print("-");
        }
    }
}

class Runnable1 implements Runnable{
    public void run(){
        for(int i=0;i<100;i++) {
            System.out.print("|");
        }
    }
}

