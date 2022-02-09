package balls.bounce;


public class BallThread extends Thread{
    private Ball b;


    public BallThread(Ball ball) {
        b = ball;
    }

    @Override

    public void run (){
        try{
            for( int i = 1; i<10000; i++){
                b.move();
                if ((b.getX() < 5 && b.getY() < 5) || (b.getX() + b.getXsize() > b.getWidthCanvas() - 5 && b.getY() + b.getYsize() >= b.getHeightCanvas() - 5) || (b.getX() < 5 && b.getY() + b.getYsize() >= b.getHeightCanvas() - 5) || (b.getY() < 5 && b.getX() + b.getXsize() > b.getWidthCanvas() - 5)) {
                    //System.out.println("There");
                    BallCanvas.removeFromBalls(b);
                    Thread.currentThread().interrupt();
                    BounceFrame.updateCounter();
                } else {
                    Thread.sleep(5);
                }
                System.out.println("Thread name = " + Thread.currentThread().getName());

            }
        } catch (InterruptedException e) {
            System.out.println("Closed thread name = " + Thread.currentThread().getName());
        } ;
    }

}
