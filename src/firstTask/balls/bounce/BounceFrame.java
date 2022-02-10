package firstTask.balls.bounce;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class BounceFrame extends JFrame {
    private BallCanvas canvas;
    public static final int WIDTH = 450;
    public static final int HEIGHT = 350;
    public static final int RED_PRIORITY = 100;
    public static final int BLUE_PRIORITY = 1;
    public static JTextField text = new JTextField(String.valueOf(BallCanvas.ballsNum()));

    public static void updateCounter() {
        text.setText(String.valueOf(BallCanvas.ballsNum()));
    }
    public BounceFrame () {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Bounce program");
        this.canvas = new BallCanvas();

        System.out.println("In frame Thread name = " + Thread.currentThread().getName());
        Container content = this.getContentPane();
        content.add(this.canvas, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.lightGray);
        JButton buttonStart = new JButton("Start");
        JButton buttonBlue = new JButton("Join");
        JButton buttonRed = new JButton("Priority");
        JButton buttonStop = new JButton("Stop");

        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ball b = new Ball(canvas);
                canvas.add(b);
                BallThread thread = new BallThread(b);
                thread.start();
                updateCounter();
                System.out.println("Thread name = " + thread.getName());
            }
        });

        buttonRed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 100; i++) {
                    Ball b = new Ball(canvas, BLUE_PRIORITY,50,50);
                    canvas.add(b);
                    BallThread thread = new BallThread(b);
                    thread.start();
                    updateCounter();
                    System.out.println("Thread name = " + thread.getName());
                }
                Ball b = new Ball(canvas, RED_PRIORITY, 50, 50);
                canvas.add(b);
                BallThread thread = new BallThread(b);
                thread.start();
                updateCounter();
                System.out.println("Thread name = " + thread.getName());
            }
        });

        buttonBlue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Ball> balls = Arrays.asList(new Ball(canvas, 1), new Ball(canvas, 10));
                for (Ball ball : balls) {
                    canvas.add(ball);
                }
                BallThread bs1 = new BallThread(balls.get(0));
                BallThread bs2 = new BallThread(balls.get(1), bs1);
                bs2.start();
            }
        });

        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        buttonPanel.add(buttonStart);
        buttonPanel.add(buttonRed);
        buttonPanel.add(buttonBlue);
        buttonPanel.add(buttonStop);
        buttonPanel.add(text);
        content.add(buttonPanel, BorderLayout.SOUTH);
    }
}
