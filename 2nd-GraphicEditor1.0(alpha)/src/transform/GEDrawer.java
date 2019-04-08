package transform;

import shapes.GEShape;


public class GEDrawer extends GETransformer {

	public GEDrawer(GEShape shape) {
		super(shape);
	}

	@Override
	public void init(int x, int y) {
		shape.setOrigin(x, y);
	}
	
	@Override
	public void Transform(int x, int y) {
		shape.setLastPoint(x, y);
	}
	
}
