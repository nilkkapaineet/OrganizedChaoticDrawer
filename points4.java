/**
 * Created by pekka on 16.11.2016.
 */

import java.applet.Applet;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.GeneralPath;
import java.util.Random;

public class points4 extends Applet implements MouseListener {

    int p1x = 200;
    int p1y = 1;
    int p2x = 400;
    int p2y = 150;
    int p3x = 300;
    int p3y = 400;
    int p4x = 100;
    int p4y = 400;
    int p5x = 1;
    int p5y = 150;

    Random rand = new Random();

    int startX = rand.nextInt(400) + 1;
    int startY = rand.nextInt(400) + 1;

    public void init() {
        addMouseListener(this);
    }

    public int[] chaos(Random rand, int startX, int startY, Graphics g) {

        int n = rand.nextInt(5) + 1;
        int endX = 0;
        int endY = 0;

        switch(n) {
            case 1:
                endX = p1x;
                endY = p1y;
                break;
            case 2:
                endX = p2x;
                endY = p2y;
                break;
            case 3:
                endX = p3x;
                endY = p3y;
                break;
            case 4:
                endX = p4x;
                endY = p4y;
                break;
            case 5:
                endX = p5x;
                endY = p5y;
                break;
        }

        // get coordinates between random end point and start point

        int finX = (startX + endX)/2;
        int finY = (startY + endY)/2;

        g.drawOval(finX, finY, 1, 1);
        //System.out.println("n: " + n + "(" + startX + ", " + startY + ")");

        int[] finXY = {finX, finY};

        return finXY;

        // repaint();
    }


    public void paint(Graphics g) {

        g.drawOval(p1x, p1y, 1, 1);
        g.drawOval(p2x, p2y, 1, 1);
        g.drawOval(p3x, p3y, 1, 1);
        g.drawOval(p4x, p4y, 1, 1);
        g.drawOval(p5x, p5y, 1, 1);


        for (int i=0;i<100000;i++) {
            int[] resultXY = chaos(rand, startX, startY, g);
            startX = resultXY[0];
            startY = resultXY[1];
        }
    }

    public void stop() {
    }

    public void destroy() {
    }

    public void mouseEntered(MouseEvent event) {
    }
    public void mouseExited(MouseEvent event) {
    }
    public void mousePressed(MouseEvent event) {

        repaint();
    }
    public void mouseReleased(MouseEvent event) {
    }
    public void mouseClicked(MouseEvent event) {
        //addItem("mouse clicked! ");
    }

}
