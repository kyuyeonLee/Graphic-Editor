package shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.Vector;

import main.GEConstants.EPointerState;


public class GEAnchor implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final int aWidth = 8;
	private static final int aHeight = 8;
	private Vector<Ellipse2D.Double> anchors;
	private AffineTransform af;
	private double theta;
	
	public GEAnchor(Rectangle boundingRect) {
		anchors = new Vector<Ellipse2D.Double>();
		af = new AffineTransform();
		theta = 0;
		
		for(int i=0; i < EPointerState.values().length-1; i++) {
			Point p = getPosition(boundingRect, EPointerState.values()[i]);
			Ellipse2D.Double ellipse = new Ellipse2D.Double(p.x, p.y, aWidth, aHeight);
			anchors.add(ellipse);
		}
	}

	public Point getPosition(Rectangle bRect, EPointerState ePointerState) {
		int x = bRect.x;
		int y = bRect.y;
		int w = bRect.width;
		int h = bRect.height;
		
		Point p = null;
		switch (ePointerState) {
			case NW: p = new Point(x, y); break;
			case WW: p = new Point(x, y+h/2); break;
			case SW: p = new Point(x, y+h); break;
			case NN: p = new Point(x+w/2, y); break;
			case SS: p = new Point(x+w/2, y+h); break;
			case NE: p = new Point(x+w, y); break;
			case EE: p = new Point(x+w, y+h/2); break;
			case SE: p = new Point(x+w, y+h); break;
			case RR: p = new Point(x+w/2, y-h/4); break;
			default: break;
		}
		
		p.x = p.x-aWidth/2;
		p.y = p.y-aHeight/2;
		
		return p;
	}
	
	public void draw(Graphics2D g) {
		Color c = g.getColor();
		g.setColor(Color.darkGray);
		for(Ellipse2D.Double anchor: anchors) {
			g.draw(anchor);
			g.fill(anchor);
		}
		g.setColor(c);
	}
	
	public EPointerState includes(int x, int y) {
		
		for(int i=0; i<anchors.size(); i++) {
			if(anchors.get(i).contains(x, y)) {
				return EPointerState.values()[i];
			}
		}
		return null;
	}

	public void move(int dx, int dy) {
		for(Ellipse2D.Double anchor: anchors) {
			anchor.x = anchor.x + dx;
			anchor.y = anchor.y + dy;
		}
	}
	
	public void resize(Rectangle bRect) {
		for(int i=0; i < EPointerState.values().length-1; i++) {
			Point p = getPosition(bRect, EPointerState.values()[i]);
			anchors.get(i).setFrame(new Rectangle(p.x, p.y, aWidth, aHeight));
		}
	}
	
	
	public void rotate(double theta, Point2D.Double origin) {
		Shape shape;

		for(int i = 0; i < EPointerState.values().length - 1; i++) {
			shape = anchors.get(i);
			af.setToRotation(theta, origin.x, origin.y);
			shape = af.createTransformedShape(shape);
			anchors.get(i).setFrame(new Rectangle(shape.getBounds().x, shape.getBounds().y, aWidth, aHeight));			
		}
	}
	
}
