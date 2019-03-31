package shape;

import java.awt.Graphics;
import java.util.Vector;

public abstract class Shape{
	protected int x1, y1, x2, y2, xpoint[], ypoint[];
	protected Vector<Integer> xvect = new Vector<Integer>();
	protected Vector<Integer> yvect = new Vector<Integer>();

	public void setOrigin(int x,int y) {
		this.x1 = x;
		this.y1 = y;
		this.x2 = x;
		this.y2 = y;
		xvect.add(x1);
		yvect.add(y1);
		xvect.add(x2);
		yvect.add(y2);
	}
	public void setPoint(int x, int y) {
		this.x2 = x;
		this.y2 = y;
	}
	public void addPoint(int x, int y) {
		this.x1 = x;
		this.y1 = y;
		xvect.add(x);
		yvect.add(y);
	}
	abstract public void draw(Graphics g);
	
}
