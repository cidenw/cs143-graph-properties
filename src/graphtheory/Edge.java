/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtheory;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author mk
 */
public class Edge {

    public Vertex vertex1;
    public Vertex vertex2;
    public boolean wasFocused;
    public boolean wasClicked;
    public boolean setWhite;
    public boolean setRed;
    public boolean setBlue;
    public boolean setGreen;
    public boolean setYellow;

    public Edge(Vertex v1, Vertex v2) {
        vertex1 = v1;
        vertex2 = v2;
    }

    public void draw(Graphics g) {
        if (wasClicked) {
            g.setColor(Color.red);
        } else if (wasFocused) {
            g.setColor(Color.blue);
        } else if(setWhite) {
            g.setColor(Color.white);       	
        } else if(setRed) {
            g.setColor(Color.red);
        } else if(setGreen) {
            g.setColor(Color.green);        	
        } else if(setBlue) {
            g.setColor(Color.blue);        	
        } else if(setYellow) {
            g.setColor(Color.yellow);
        } else {
            g.setColor(Color.black);
        }
        g.drawLine(vertex1.location.x, vertex1.location.y, vertex2.location.x, vertex2.location.y);

    }

    public boolean hasIntersection(int x, int y) {
        int x1, x2, y1, y2;
        x1 = vertex1.location.x;
        x2 = vertex2.location.x;
        y1 = vertex1.location.y;
        y2 = vertex2.location.y;
        float slope = 0;
        if (x2 != x1) {
            slope = (y2 - y1) / (x2 - x1);
        }

        float b = Math.abs(x1 * slope - y1);

        if (y + b <= Math.round(slope * x) + 10 && y + b >= Math.round(slope * x) - 10) {
            if (x1 > x2 && y1 > y2) {
                if (x <= x1 && x >= x2 && y <= y1 && y >= y2) {
                    return true;
                }
            } else if (x1 < x2 && y1 > y2) {
                if (x <= x2 && x >= x1 && y <= y1 && y >= y2) {
                    return true;
                }
            } else if (x1 < x2 && y1 < y2) {
                if (x <= x2 && x >= x1 && y <= y2 && y >= y1) {
                    return true;
                }
            } else if (x <= x1 && x >= x2 && y <= y2 && y >= y1) {
                return true;
            }
        }
        return false;

    }
}
