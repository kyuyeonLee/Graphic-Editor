package shape;

import java.awt.Rectangle;

public class GRectangle extends GShape {
	private Rectangle rectangle;

	public GRectangle() {
		super();
		this.shape = new Rectangle();
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

	public void keepMoving(int x, int y) {
		int dw = x - px;
		int dh = y - py;
		
		this.rectangle.setLocation(this.rectangle.x+dw, this.rectangle.y+dh);
		
		this.px = x;
		this.py = y;
	}

	public void finishMoving(int x, int y) {
	}
	
}
