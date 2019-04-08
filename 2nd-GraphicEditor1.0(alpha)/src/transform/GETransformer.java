package transform;

import java.awt.geom.AffineTransform;
import java.util.Vector;

import shapes.GEShape;

public abstract class GETransformer {

	protected GEShape shape;
	protected Vector<GEShape> shapelist;
	protected int oldX;
	protected int oldY;
	protected AffineTransform af;
	
	public GETransformer(GEShape shape) {
		this.shape = shape;
	}
	public abstract void init(int x, int y);
	public abstract void Transform(int x, int y);

}