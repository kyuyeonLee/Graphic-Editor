package shapes;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.util.Vector;

import main.GEConstants;

public class GEGroup extends GERectangle {
	
	private Vector<GEShape> shapes;
	private float dashs[] = {4};
	private BasicStroke dashedStroke;
	
	public GEGroup() {
		super();
		shapeType = GEConstants.EShapeType.GR;
		shapes = new Vector<GEShape>();
		dashedStroke = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10, dashs, 0);
	}
	
	public Vector<GEShape> getSubShapes() {
		return shapes;
	}
	 
	public void draw(Graphics2D g) {
		for(GEShape shape: shapes) {
			shape.draw(g);
		}
		if(this.isSelected()) {
			Stroke s = g.getStroke();
			g.setStroke(dashedStroke);
			g.draw(shape);
			g.setStroke(s);
			if(selected) {
				anchor.draw(g);
			}
		}
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
		if(selected) {
			anchor = new GEAnchor(this.shape.getBounds());
		} else {
			anchor = null;
		}
	}
	
	public void addshape(GEShape subshape) {
		shapes.add(subshape);
		
		if(shapes.size() == 1) {
			this.shape = subshape.getShape().getBounds();
		} else {
			this.shape = this.shape.getBounds().createUnion(subshape.getShape().getBounds());
		}
		//System.out.println(this.shape.getBounds());
	}
	
	/* 임시함수 */
	public int getsize() {
		return shapes.size();
	}
	
	@Override
	public GEShape deepcopy() {
		AffineTransform af = new AffineTransform();
		Shape copyshape = af.createTransformedShape(this.shape);
		GEGroup newshape = new GEGroup();
		newshape.setShape(copyshape);
		newshape.setGraphicAttribute(this);
		return newshape;
	}
}
