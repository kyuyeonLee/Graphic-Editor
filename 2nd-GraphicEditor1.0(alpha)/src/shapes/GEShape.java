package shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.Serializable;

import main.GEConstants;
import main.GEConstants.EPointerState;
import main.GEConstants.EShapeType;



public abstract class GEShape implements Serializable{
	private static final long serialVersionUID = -5117757752902339464L;
	
	// attributes
	protected Color lineColor, fillColor;
	protected GEConstants.EShapeType shapeType;
	// components
	protected Shape shape;
	protected GEAnchor anchor;
	// associations
	
	// working variables
	protected boolean selected;
	protected int oldX, oldY, oldWidth, oldHeight;
	protected AffineTransform af;
	protected Point2D.Double ROrigin;
	protected double theta;
	
	public GEShape() {
		// graphics attribute initialization is need
		shapeType = GEConstants.EShapeType.TP;
		selected = false;
		af = new AffineTransform();
	}

	// sets and gets
	public Shape getShape() { return shape; }
	public void setShape(Shape shape) { this.shape = shape; }
	public GEAnchor getAnchor() { return anchor; }
	public void setAnchor(GEAnchor anchor) { this.anchor = anchor; }
	public Color getLineColor() {return lineColor;}
	public void setLineColor(Color lineColor) {this.lineColor = lineColor;}
	public Color getFillColor() {return fillColor;}
	public void setFillColor(Color fillColor) {this.fillColor = fillColor;}
	
	public void setGraphicAttribute(GEShape shape) {
		setLineColor(shape.getLineColor());
		setFillColor(shape.getFillColor());
		setAnchor(shape.getAnchor());
		setSelected(shape.isSelected());
	}
	
	public boolean isSelected() {return selected;}
	public void setSelected(boolean selected) {
		this.selected = selected;
		if(this.selected) {
			//if(anchor == null) {
				anchor = new GEAnchor(shape.getBounds());
			//} 
		}
	}
	public EShapeType getShapeType() { return shapeType; }
	public void setShapeType(GEConstants.EShapeType shapeType) { this.shapeType = shapeType; }

	// member functions / methods
	public void draw(Graphics2D g) {
		Color c = g.getColor();
		g.setColor(this.fillColor);
		g.fill(shape);
		g.setColor(this.lineColor);
		g.draw(shape);
		g.setColor(c);
		if(selected) {
			anchor.draw(g);
		}
	}

	public void drawXOR(Graphics2D g, Color background) {
		g.setXORMode(background);
		draw(g);
	}
	
	public EPointerState includes(int x, int y) {
		if(selected) {
			EPointerState state = anchor.includes(x, y);
			if(state != null) {
				return state;
			}
		}
		if(shape.contains(x, y)) {
			return EPointerState.MM;
		}
		return null;
	}
	
	/* abstract class */
	public abstract Point getOrigin();
	public abstract void setOrigin(int x, int y);
	public abstract void addPoint(int x, int y);
	public abstract void setLastPoint(int x, int y);
	public abstract GEShape clone(); // shape에 대해서 clone을 쓸수가 없어서 코드적으로 deepcopy를 구현
	public abstract GEShape deepcopy(); // shape에 대해서 clone을 쓸수가 없어서 코드적으로 deepcopy를 구현
	
}
