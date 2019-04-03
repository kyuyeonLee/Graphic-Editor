package shape;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class GRectangle extends GShape {
	private Rectangle rectangle;

	public GRectangle() {
		super();
		this.rectangle = new Rectangle();
		this.rectangle = (java.awt.Rectangle)this.shape;
	}
	
	public void setOrigin(int x, int y) {
		this.rectangle.setBounds(x, y, 0, 0);
	}

	public void setPoint(int x, int y) {
		this.rectangle.setSize(x-this.rectangle.x, y-this.rectangle.y); //위드스하&이트를 가져오는 함수이다.
	}

	public void addPoint(int x, int y) {

	}

	
}
