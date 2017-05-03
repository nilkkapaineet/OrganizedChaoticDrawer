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

public class Chaotic extends Applet implements MouseListener {

    int p1x = 200;
    int p1y = 1;
    int p2x = 400;
    int p2y = 400;
    int p3x = 1;
    int p3y = 400;

    Random rand = new Random();

    int startX = rand.nextInt(400) + 1;
    int startY = rand.nextInt(400) + 1;

    public void init() {
        addMouseListener(this);
    }

    public int[] chaos(Random rand, int startX, int startY, Graphics g) {

        int n = rand.nextInt(3) + 1;
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
        }

        // get coordinates between random end point and start point

        int finX = (startX + endX)/2;
        int finY = (startY + endY)/2;

        g.drawOval(finX, finY, 2, 2);
        System.out.println(startX + ", " + startY);

        int[] finXY = {finX, finY};

        return finXY;

        // repaint();
    }


    public void paint(Graphics g) {

        g.drawOval(p1x, p1y, 2, 2);
        g.drawOval(p2x, p2y, 2, 2);
        g.drawOval(p3x, p3y, 2, 2);

        for (int i=0;i<10000;i++) {
            int[] resultXY = chaos(rand, startX, startY, g);
            startX = resultXY[0];
            startY = resultXY[1];
        }
    }

    public void ppl (Graphics2D g2, int x1, int y1, int x2, int y2) {
        // draw perpendicular lines
        // take input of segment end points of triangle and their midpoints
        int mx = Math.abs((x1-x2)/2);
        int my = Math.abs((y1-y2)/2);
        // write original equation of line segment
        // find slope and turn 90 degrees ie. find perpendicular slope
        int k = (x2-x1)/(y2-y1);
        int b = y1-k*x1;
        // extend line by 30 pixels
        // y = kx+b
        int xe1 = x1-10;
        int ye1 = k*xe1+b;
        int xe2 = x2+10;
        int ye2 = k*xe2+b;
        g2.setColor(Color.green);
        g2.drawLine(xe1, ye1, xe2, ye2);
        // write new equation for a perpendicular line
        int kp = -1/k;
        int bp = my-kp*mx;
        // find new start and endpoits and draw the line
        int xa = 1;
        int ya = kp*xa+bp;
        int xl = 499;
        int yl = kp*xl+bp;
        g2.setColor(Color.DARK_GRAY);
        g2.drawLine(xa,ya,xl,yl);
        // extend
        int x1a = 1;
        int y1a = k*x1a+b;
        int x1l = 499;
        int y1l = k*x1l+b;
        g2.setColor(Color.red);
        g2.drawLine(x1a,y1a,x1l,y1l);

        g2.drawOval(20, 20, 15, 15);
        g2.drawOval(mx, my, 5, 5);
    }

    public void extendLines (Graphics2D g2, int x1, int y1, int x2, int y2) {
        double X1 = (double) x1;
        double Y1 = (double) y1;
        double X2 = (double) x2;
        double Y2 = (double) y2;
        double mpx = (X1+X2)/2;
        double mpy = (Y1+Y2)/2;
        int Mpx = (int) mpx;
        int Mpy = (int) mpy;
        //slope
        double m = (Y2-Y1)/(X2-X1);
        double b = Y1-m*X1;
        // here we have y=m*x+b

        // find extended points, valid if 0 < x,y < 250
        // case 3: (1, yo)
        double y3 = m*1+b;
        double x3 = 1;
        // case 4: (x0, 250)
        double x4 = (250-b)/m;
        double y4 = 250;
        // case 5: (250, y0)
        double y5 = m*250+b;
        double x5 = 250;
        // case 6: (x0, 1)
        double x6 = (1-b)/m;
        double y6 = 1;

        // start and end points
        double x0a = 0;
        double y0a = 0;
        double x0b = 200;
        double y0b = 200;

        // conditions for start and endpoints
        // slopes must match
        // case 3--> 4
        double m34 = (y4-y3)/(x4-x3);
        if (m34 == m) {
            x0a = x3;
            y0a = y3;
            x0b = x4;
            y0b = y4;
        }
        // case 3--> 5
        double m35 = (y5-y3)/(x5-x3);
        if (m35 == m) {
            x0a = x3;
            y0a = y3;
            x0b = x5;
            y0b = y5;
        }
        // case 3--> 6
        double m36 = (y6-y3)/(x6-x3);
        if (m36 == m) {
            x0a = x3;
            y0a = y3;
            x0b = x6;
            y0b = y6;
        }
        // case 4--> 5
        double m45 = (y5-y4)/(x5-x4);
        if (m45 == m) {
            x0a = x4;
            y0a = y4;
            x0b = x5;
            y0b = y5;
        }
        // case 4--> 6
        double m46 = (y6-y4)/(x6-x4);
        if (m46 == m) {
            x0a = x4;
            y0a = y4;
            x0b = x6;
            y0b = y6;
        }
        // case 5--> 6
        double m56 = (y6-y5)/(x6-x5);
        if (m56 == m) {
            x0a = x5;
            y0a = y5;
            x0b = x6;
            y0b = y6;
        }

        // double to int conversions
        int X0a = (int) x0a;
        int Y0a = (int) y0a;
        int X0b = (int) x0b;
        int Y0b = (int) y0b;
        // draw line
        g2.drawLine(X0a, Y0a, X0b, Y0b);

        g2.setColor(Color.cyan);
        g2.rotate(Math.toRadians(90), (X0b-X0a)/2, (Y0b-Y0a)/2);
        g2.drawLine(X0a, Y0a, X0b, Y0b); // right!
    }

    public void plines(Graphics2D pg, int x1, int y1, int x2, int y2) {
        // find perpendicular bisector of a given line segment
        double X1 = (double) x1;
        double Y1 = (double) y1;
        double X2 = (double) x2;
        double Y2 = (double) y2;
        double mpx = (X1+X2)/2;
        double mpy = (Y1+Y2)/2;
        int Mpx = (int) mpx;
        int Mpy = (int) mpy;

        // slope of the segment
        double m12 = (Y2-Y1)/(X2-X1);

        // slope of the bisector
        double mb = -1/m12;

        // find y if x=0
        double dY = mb*(-X1)+Y1;
        int dy = (int) dY;

        pg.setColor(Color.cyan);
        pg.drawLine(Mpx, Mpy, 0, dy);

        // find x if y=0;
        double dX = -Y1/mb+X1;
        int dx = (int) dX;

        pg.setColor(Color.green);
        pg.drawLine(Mpx, Mpy, dx, 0);
    }
/*
    public void extendLines(Graphics2D eg2, int x, int y) {
        //
        eg2.drawLine(x, y, ax, ay);
    }
*/
    public void perpLines(Graphics2D gdp, int A1, int A2, int B1, int B2, int C1, int C2) {
        int m1 = (A1+B1)/2;
        int m2 = (A2+B2)/2;
        double Ax = (double) A1;
        double Ay = (double) A2;
        double Bx = (double) B1;
        double By = (double) B2;
        double Cx = (double) C1;
        double Cy = (double) C2;
        double t=((Cx-Ax)*(Bx-Ax)+(Cy-Ay)*(By-Ay))/((Bx-Ax)*(Bx-Ax)+(By-Ay)*(By-Ay));
        double Dx=Ax+t*(Bx-Ax);
        double Dy=Ay+t*(By-Ay);
        int intDx = (int) Dx;
        int intDy = (int) Dy;
        gdp.setColor(Color.red);
        //gdp.drawLine(C1, C2, intDx, intDy);
        // works on certain angles
        int vx = m1-intDx;
        int vy = m2-intDy;
        //C1 += vx;
        //C2 += vy;
        vx += C1;
        vy += C2;
        gdp.drawLine(vx, vy, m1, m2);
        // other way round, C1 and C2 into opposite direction
        int dx = m1+intDx;
        int dy = m2+intDy;
        dx += C1;
        dy += C2;
        gdp.drawLine(dx, dy, m1, m2);
    }

    public void drawPerpendicularLines(Graphics2D pg2, int pax, int pay, int pbx, int pby, int pcx, int pcy, int pmidABx, int pmidABy, int pmidBCx, int pmidBCy, int pmidACx, int pmidACy) {
        // extend lines by finding out relative outstretched coordinates
        int epax = pax/pay;
        int epay = 1;
        int epbx = pbx/pby*500;
        int epby = 500;

        pg2.rotate(Math.toRadians(90), pmidABx, pmidABy);
        pg2.drawLine(pax, pay, pbx, pby); // right!
        pg2.rotate(Math.toRadians(-90), pmidABx, pmidABy);
        pg2.rotate(Math.toRadians(90), pmidBCx, pmidBCy);
        pg2.drawLine(pbx, pby, pcx, pcy);
        pg2.rotate(Math.toRadians(-90), pmidBCx, pmidBCy);
        pg2.rotate(Math.toRadians(90), pmidACx, pmidACy);
        pg2.drawLine(pax, pay, pcx, pcy);
        pg2.rotate(Math.toRadians(-90), pmidACx, pmidACy);
    }

    public void drawTriangle(Graphics2D tg2, int tax, int tay, int tbx, int tby, int tcx, int tcy) {
        tg2.drawLine(tax, tay, tbx, tby);
        tg2.drawLine(tbx, tby, tcx, tcy);
        tg2.drawLine(tax, tay, tcx, tcy);
    }

    // Returns 1 if the lines intersect, otherwise 0. In addition, if the lines
    // intersect the intersection point may be stored in the ints i_x and i_y.
     public boolean getLineIntersection(int p0_x, int p0_y, int p1_x, int p1_y,
                               int p2_x, int p2_y, int p3_x, int p3_y)
    {
        double s1_x, s1_y, s2_x, s2_y;
        s1_x = p1_x - p0_x;     s1_y = p1_y - p0_y;
        s2_x = p3_x - p2_x;     s2_y = p3_y - p2_y;

        double s, t;
        s = (-s1_y * (p0_x - p2_x) + s1_x * (p0_y - p2_y)) / (-s2_x * s1_y + s1_x * s2_y);
        t = ( s2_x * (p0_y - p2_y) - s2_y * (p0_x - p2_x)) / (-s2_x * s1_y + s1_x * s2_y);

        if (s >= 0 && s <= 1 && t >= 0 && t <= 1)
        {
            // Collision detected
            double rp0_x = (double) p0_x;
            double rp0_y = (double) p0_y;
            double ri_x = rp0_x + (t * s1_x);
            double ri_y = rp0_y + (t * s1_y);

            return true;
        }
        return false; // No collision
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
