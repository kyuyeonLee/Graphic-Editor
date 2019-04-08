package shape;

import java.awt.Polygon;

public class GPolygon extends GShape {
	private Polygon polygon;

	public GPolygon() {
		super();
		this.polygon = new Polygon();
		this.shape = this.polygon;

	}

	public void setOrigin(int x, int y) {
		this.polygon.addPoint(x, y);
		this.polygon.addPoint(x, y);
	}

	public void setPoint(int x, int y) {
		this.polygon.xpoints[this.polygon.npoints - 1] = x;
		this.polygon.ypoints[this.polygon.npoints - 1] = y;
	}

	public void addPoint(int x, int y) {
		this.polygon.addPoint(x, y);
	}

	public void keepMoving(int x, int y) {
		int dw = x - px;
		int dh = y - py;

		for (int i = 0; i < this.polygon.npoints; i++) {
			this.polygon.xpoints[i] += dw;
			this.polygon.ypoints[i] += dh;
		}

		this.px = x;
		this.py = y;
	}
	public void finishMoving(int x, int y) {
		Polygon newPolygon = new Polygon();
		for (int i = 0; i < this.polygon.npoints; i++) {
			newPolygon.addPoint(this.polygon.xpoints[i], this.polygon.ypoints[i]);
		}
		
		this.polygon = new Polygon();
		this.shape = this.polygon;
	}

}
