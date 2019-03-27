package shape;

import java.awt.Graphics;

public class Select extends Shape {
	public void draw(Graphics g) {
		g.drawRect(x1, y1, y1, x2-x1);
	}
}
