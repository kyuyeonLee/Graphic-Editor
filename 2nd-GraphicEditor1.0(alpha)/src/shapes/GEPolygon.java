package shapes;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import main.GEConstants;

public class GEPolygon extends GEShape {

	private Polygon polygon;
	
	public GEPolygon() {
		super();
		shapeType = GEConstants.EShapeType.NP;
		polygon = new Polygon();
		this.shape = polygon;
	}
	
	@Override
	public Point getOrigin() {
		return null;
	}

	@Override
	public void setOrigin(int x, int y) {
		polygon.addPoint(x, y);
	}

	@Override
	public void addPoint(int x, int y) {
		polygon.addPoint(x, y);
	}

	@Override
	public void setLastPoint(int x, int y) {
		polygon.xpoints[polygon.npoints-1] = x;
		polygon.ypoints[polygon.npoints-1] = y;
	}

	@Override
	public GEPolygon clone() {
		return new GEPolygon();
	}

	@Override
	public GEShape deepcopy() {
		AffineTransform af = new AffineTransform();
		Shape copyshape = af.createTransformedShape(this.shape);
		GEPolygon newshape = new GEPolygon();
		newshape.setShape(copyshape);
		newshape.setGraphicAttribute(this);
		return newshape;
	}

}
