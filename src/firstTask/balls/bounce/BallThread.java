package firstTask.balls.bounce;


public class BallThread extends Thread{
    private Ball b;
    BallThread ballThread;
    boolean isBallThread = false;
    public BallThread(Ball ball) {
        b = ball;
    }

    public BallThread(Ball ball,BallThread ballThread) {
        b = ball;
        this.ballThread = ballThread;
        this.ballThread.start();
        isBallThread = true;
    }
    @Override

    public void run (){
        try{
            for( int i = 1; i<10000; i++){
                b.move();
                if ((b.getX() < 5 && b.getY() < 5) || (b.getX() + b.getXsize() > b.getWidthCanvas() - 5 && b.getY() + b.getYsize() >= b.getHeightCanvas() - 5) || (b.getX() < 5 && b.getY() + b.getYsize() >= b.getHeightCanvas() - 5) || (b.getY() < 5 && b.getX() + b.getXsize() > b.getWidthCanvas() - 5)) {
                    //System.out.println("There");
                    BallCanvas.removeFromBalls(b);
                    BounceFrame.updateCounter();
                    break;
                } else {
                    Thread.sleep(5);
                }
                System.out.println("Thread name = " + Thread.currentThread().getName());

                if(isBallThread) {
                    ballThread.join();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } ;
        isBallThread = false;
        System.out.println("Stopped Thread name = " + Thread.currentThread().getName());
    }

}
