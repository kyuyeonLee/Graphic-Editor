package shape;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;

public abstract class GShape implements Cloneable{
	
	protected Shape shape;
	protected int px;
	protected int py;
	
	abstract public void setOrigin(int x,int y);
	abstract public void setPoint(int x, int y);
	abstract public void addPoint(int x, int y); 
	
	public abstract void keepMoving(int x, int y);
	public abstract void finishMoving(int x, int y);
	public void initMoving(int x, int y) {
		this.px = x;
		this.px = y;
	}
	
	public GShape clone() {
		try {
			return (GShape)this.getClass().newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.draw(this.shape);
	}
	
	public boolean contains(int x,int y) {
		return this.shape.contains(x,y);
	}
}
