package threads;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static void main(String[] args) throws InterruptedException {
//        Runnable r = new Runnable1();
//        Thread t = new Thread(r);
//        Runnable r2 = new Runnable2();
//        Thread t2 = new Thread(r2);
//        t.start();
//        t2.start();
        final BlockingQueue<Response> responses = new ArrayBlockingQueue<>(1);
        final Worker[] workers = new Worker[2];
        for (int i = 0; i < 2; i++) {
            final Worker w = new Worker(i, responses);
            workers[i] = w;
            new Thread(w, "Thread #" + i).start();
        }
        for (int i = 0; i < 100; i++) {
            final int workerId = i % 2;
            workers[workerId].send(new Request(i));
            System.out.println(responses.take().getContent());
        }
        System.exit(0);

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


final class Request {

    private final int id;

    public Request(final int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

final class Response {

    private final String content;

    public Response(final String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}

final class Worker implements Runnable {

    private final int id;
    private final BlockingQueue<Request> requests;
    private final BlockingQueue<Response> responses;

    public Worker(
            final int id,
            final BlockingQueue<Response> responses
    ) {
        this.id = id;
        this.requests = new ArrayBlockingQueue<>(1);
        this.responses = responses;
    }

    public void send(final Request r) throws InterruptedException {
        requests.put(r);
    }

    @Override
    public void run() {
        try {
            while (true) {
                final Request r = requests.take();
                final String msg = String.format("Worker #%d message %s", id, getMessage(id));
                responses.put(new Response(msg));
            }
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
    }

    public String getMessage(int id) {
        if (id == 0){
            return "/";
        } else {
            return "-";
        }
    }
}


