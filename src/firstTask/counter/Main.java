package counter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {

        //Runnable r = new Runnable1();
        Runnable r = new Runnable3();
        Thread t = new Thread(r);
        //Runnable r2 = new Runnable2();
        Runnable r2 = new Runnable4();
        Thread t2 = new Thread(r2);
        t.start();
        t2.start();
        //System.out.println(COUNTER);


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
            Counter.incrementAtomic();
            System.out.println(Counter.getCountAtomic());
        }
    }
}

class Runnable4 implements Runnable{
    public void run(){
        for(int i = 0;i < 100000;i++) {
            Counter.decrementAtomic();
            System.out.println(Counter.getCountAtomic());
        }
    }
}