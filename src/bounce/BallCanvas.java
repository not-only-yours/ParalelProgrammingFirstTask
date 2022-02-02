package bounce;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static bounce.BounceFrame.RED_PRIORITY;

public class BallCanvas extends JPanel {
    private static ArrayList<Ball> balls = new ArrayList<>();

    public void add(Ball b) {
        balls.add(b);
    }

    public static void removeFromBalls(Ball b) {
        balls.remove(b);
    }

    public static int ballsNum(){
        return balls.size();
    }

    @Override
    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        for (Ball b : balls) {
            if (b.getPriorityOfBall() > 0){
                if (b.getPriorityOfBall() == RED_PRIORITY) {
                    b.drawRed(g2);
                } else {
                    b.drawBlue(g2);
                }
            }else
            b.draw(g2);
        }
    }
}
