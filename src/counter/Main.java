package counter;

public class Main {
    public static int COUNTER = 0;
    public static void main(String[] args) {

        Runnable r = new Runnable1();
        Thread t = new Thread(r);
        Runnable r2 = new Runnable2();
        Thread t2 = new Thread(r2);
        t.start();
        t2.start();
        //System.out.println(COUNTER);


    }
}


class Runnable2 implements Runnable{
    public void run(){
        for(int i = 0; i < 100000; i++) {
            Main.COUNTER++;
            System.out.println(Main.COUNTER);
        }
    }
}

class Runnable1 implements Runnable{
    public void run(){
        for(int i = 0;i < 100000;i++) {
            Main.COUNTER--;
            System.out.println(Main.COUNTER);
        }
    }
}
