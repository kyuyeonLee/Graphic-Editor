package shape;

import java.awt.Graphics;

public class Ellipse extends Shape{
	public void draw(Graphics g) {
		g.drawOval(x1, y1, y1, x2-x1);
	}
}
