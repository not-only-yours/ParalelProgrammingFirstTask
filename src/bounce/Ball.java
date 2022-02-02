package bounce;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class Ball {
    private Component canvas;
    private static final int XSIZE = 20;
    private static final int YSIZE = 20;
    private int x = 0;
    private int y = 0;
    private int dx = 2;
    private int dy = 2;
    private int p = 0;
    public int getPriorityOfBall() {
        return p;
    }

    public Ball (Component c) {
        this.canvas = c;
        if (Math.random() < 0.5) {
            x = new Random().nextInt(this.canvas.getWidth());
            y = 0;
        }
        else {
            x = 0;
            y = new Random().nextInt(this.canvas.getHeight());
        }
    }

    public Ball (Component c, int priority) {
        this.canvas = c;
        if (Math.random() < 0.5) {
            x = new Random().nextInt(this.canvas.getWidth());
            y = 0;
        }
        else {
            x = 0;
            y = new Random().nextInt(this.canvas.getHeight());
        }
        p = priority;
    }

    public int getXsize() {
        return XSIZE;
    }

    public int getYsize() {
        return YSIZE;
    }

    public int getWidthCanvas() {
        return this.canvas.getWidth();
    }

    public int getHeightCanvas() {
        return this.canvas.getHeight();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.darkGray);
        g2.fill(new Ellipse2D.Double(x, y, XSIZE, YSIZE));
    }

    public void drawRed(Graphics2D g2) {
        g2.setColor(Color.red);
        g2.fill(new Ellipse2D.Double(x, y, XSIZE, YSIZE));
    }

    public void drawBlue(Graphics2D g2) {
        g2.setColor(Color.blue);
        g2.fill(new Ellipse2D.Double(x, y, XSIZE, YSIZE));
    }

    public void move() {
        x+=dx;
        y+=dy;
        if (x < 0) {
            x = 0;
            dx = -dx;
        }
        if (y < 0) {
            y = 0;
            dy = -dy;
        }
        if(x + XSIZE >= this.canvas.getWidth()) {
            x = this.canvas.getWidth() - XSIZE;
            dx = -dx;
        }
        if(y + YSIZE >= this.canvas.getHeight()) {
            y = this.canvas.getHeight() - YSIZE;
            dy = - dy;
        }
        this.canvas.repaint();
    }
}
