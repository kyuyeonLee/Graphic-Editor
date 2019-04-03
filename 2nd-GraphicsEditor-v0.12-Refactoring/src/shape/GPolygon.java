package shape;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class GPolygon extends GShape{
	private Polygon polygon;
	
	public GPolygon() {
		super();
		this.polygon = new Polygon();
		this.polygon = (java.awt.Polygon)this.shape;

	}
	
	public void setOrigin(int x,int y) {
		this.polygon.addPoint(x, y);
		this.polygon.addPoint(x, y);
	}
	
	public void setPoint(int x, int y) {
		this.polygon.xpoints[this.polygon.npoints-1]=x;
		this.polygon.ypoints[this.polygon.npoints-1] = y;
	}
	
	public void addPoint(int x, int y) {
		this.polygon.addPoint(x, y);
	}
}
