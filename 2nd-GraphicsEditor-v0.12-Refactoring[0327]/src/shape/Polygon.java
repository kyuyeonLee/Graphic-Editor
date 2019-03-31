package shape;

import java.awt.Graphics;
import java.util.Vector;

import javax.swing.plaf.basic.BasicTabbedPaneUI.MouseHandler;

public class Polygon extends Shape {
	public void draw(Graphics g) {
		int[] xpoint = new int[xvect.size()];
		for (int i = 0; i < xvect.size(); i++) {
			xpoint[i] = xvect.get(i);
		}

		int[] ypoint = new int[yvect.size()];
		for (int i = 0; i < yvect.size(); i++) {
			ypoint[i] = yvect.get(i);
		}
		g.drawPolygon(xpoint, ypoint, xpoint.length);
		
		
		
		
	}
}
