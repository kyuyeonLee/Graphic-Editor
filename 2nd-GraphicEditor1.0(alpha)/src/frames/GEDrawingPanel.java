package frames;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JPanel;

import main.GEConstants.EPointerState;
import main.GEConstants.EShapeType;
import shapes.GEGroup;
import shapes.GESelect;
import shapes.GEShape;
import transform.GEDrawer;
import transform.GEMover;
import transform.GEResizer;
import transform.GERotater;
import transform.GETransformer;


public class GEDrawingPanel extends JPanel {
	
	// Attribute
	private enum EDrawingState {idle, TPDrawing, NPDrawing, moving, rotating, resizing};
	private EDrawingState drawingState;
	private Color lineColor, fillColor;
	
	// Components
	private MouseHandler handler;
	private Vector<GEShape> shapes;
	private GEClipBoard clipboard;
	private GEStack stack;
	private GEGroup group;
	
	// Working Variables
	private GEShape drawingTool, currentShape, selectedShape;
	private GETransformer trans;
	private Stroke dashedStroke;
	private EPointerState pointerState;
	
	public GEDrawingPanel() {
		super();
		// Attribute
		drawingState = EDrawingState.idle;
		// Components
		handler = new MouseHandler();
		this.addMouseListener(handler);
		this.addMouseMotionListener(handler);
		shapes = new Vector<GEShape>();
		clipboard = new GEClipBoard();
		stack = new GEStack();
		group = new GEGroup();
		// Working Variables
		float dashs[] = {4};
		dashedStroke = new BasicStroke(
				1, 
				BasicStroke.CAP_ROUND, 
				BasicStroke.JOIN_ROUND, 
				10, 
				dashs, 
				0);
		lineColor = this.getForeground();
		fillColor = this.getBackground();
		//lineColor = Color.BLACK;
		//fillColor = Color.WHITE;
	}
	
	public void initialize() {	}
	
	// sets and gets
	public GEShape getDrawingTool() {return drawingTool;}
	public void setDrawingTool(GEShape drawingTool) {this.drawingTool = drawingTool;}
	public void setLineColor(Color lineColor) {this.lineColor = lineColor;}
	public void setFillColor(Color fillColor) {this.fillColor = fillColor;}
	public Object getShapes() {	return this.shapes; }

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		for(GEShape shape: shapes) {
			shape.draw((Graphics2D) g);
		}
	}
	
	public void init(int x, int y) {
		if(drawingState != EDrawingState.idle) {
			switch (drawingState) {
				case TPDrawing:
				case NPDrawing:
					try {
						currentShape = drawingTool.getClass().newInstance();
						currentShape.setLineColor(this.lineColor);
						currentShape.setFillColor(this.fillColor);
						
						trans = new GEDrawer(currentShape);
					} catch (InstantiationException e1) {
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						e1.printStackTrace();
					}
					break;
				case  moving:
					trans = new GEMover(selectedShape);
					break;
				case resizing:
					trans = new GEResizer(selectedShape);
					break;
				case rotating:
					trans = new GERotater(selectedShape);
					break;
			}
			
			trans.init(x, y);
			if(drawingState == EDrawingState.NPDrawing) {
				nPAddPoint(x, y); // n-1 점추가
			} else {
				trans.Transform(x, y);
			}
			Graphics g = this.getGraphics();
			currentShape.drawXOR((Graphics2D) g, this.getBackground());
		}
	}
	
	public void keep(int x, int y) {
		Graphics2D g = (Graphics2D)this.getGraphics();	
		
		if(drawingState == EDrawingState.TPDrawing || drawingState == EDrawingState.NPDrawing) {
			Stroke s = g.getStroke();
			g.setStroke(dashedStroke);
			currentShape.drawXOR(g, this.getBackground());
			trans.Transform(x, y);
			currentShape.drawXOR(g, this.getBackground());
			g.setStroke(s);
		} else {
			selectedShape.drawXOR(g, this.getBackground());
			if(drawingState == EDrawingState.resizing) {
				((GEResizer)trans).Transform(x, y, pointerState);
			} else {
				trans.Transform(x, y);
			}	
			selectedShape.drawXOR(g, this.getBackground());
		}
	}
	
	public void finish(int x, int y) {
		if(!(currentShape instanceof GESelect)) {
			Graphics2D g = (Graphics2D)this.getGraphics();	
			
			if(drawingState == EDrawingState.NPDrawing || drawingState == EDrawingState.TPDrawing) {
				Stroke s = g.getStroke();
				g.setStroke(dashedStroke);
				currentShape.drawXOR(g, this.getBackground());
				g.setStroke(s);
				currentShape.draw(g);
				shapes.add(currentShape);
			}
		} else {	
			//System.out.println("그룹영역 : " + currentShape.getShape().getBounds());
			int i=0;
			for(GEShape shape : shapes) {
				if(shape.getShape().intersects(currentShape.getShape().getBounds())) {
					shape.setSelected(true); 
					i++;
				}
			}
		}
		stack.push(shapes); // redo, undo support
		trans = null; // for garbage collector
		repaint();
	}
	
	public void nPAddPoint(int x, int y) {
		currentShape.addPoint(x, y);
	}
	
	public EPointerState includes(int x, int y) {
		EPointerState pointerState = null;
		for(GEShape shape: shapes) {
			pointerState = shape.includes(x, y);
			if(pointerState != null) {
				selectedShape = shape;
				return pointerState;
			}
		}
		return pointerState;
	}
	
	public void changePointer(EPointerState pointerState) {
		if(pointerState != null) {
			switch (pointerState) {
				case NW: setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR)); break;
				case WW: setCursor(new Cursor(Cursor.W_RESIZE_CURSOR)); break;
				case SW: setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR)); break;
				case NN: setCursor(new Cursor(Cursor.N_RESIZE_CURSOR)); break;
				case SS: setCursor(new Cursor(Cursor.S_RESIZE_CURSOR)); break;
				case NE: setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR)); break;
				case EE: setCursor(new Cursor(Cursor.E_RESIZE_CURSOR)); break;
				case SE: setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR)); break;
				case RR: setCursor(new Cursor(Cursor.HAND_CURSOR)); break;
				case MM: setCursor(new Cursor(Cursor.MOVE_CURSOR)); break;
			}
		} else {
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}
	
	public void clearSelection() {
		for(GEShape shape: shapes) {
			shape.setSelected(false);
		}
	}
	
	private class MouseHandler implements MouseListener, MouseMotionListener {
		
		@Override
		public void mousePressed(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON1) {
				button1Pressed(e);
			}
		}
		
		public void button1Pressed(MouseEvent e) {
			if(drawingState == EDrawingState.idle) {
				pointerState = includes(e.getX(), e.getY());
				clearSelection(); // 도형이 아니면 셀레트를 날린다
				
				// if mouse is on a shape
				if(pointerState != null) {
					selectedShape.setSelected(true);
					// Transform 선택
					if(pointerState ==  EPointerState.MM) {
						drawingState = EDrawingState.moving;
					} else if(pointerState ==  EPointerState.RR) {
						drawingState = EDrawingState.rotating;
					} else {
						drawingState = EDrawingState.resizing;			
					}
					init(e.getX(), e.getY());
				} else { // if mouse is not on a shape
					if(drawingTool.getShapeType() == EShapeType.TP) {
						drawingState = EDrawingState.TPDrawing;
						init(e.getX(), e.getY());
					}
				}
				repaint();
			}
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			if(drawingState != EDrawingState.idle && drawingState != EDrawingState.NPDrawing) {
				keep(e.getX(), e.getY());
			}
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			if(drawingState != EDrawingState.idle && drawingState != EDrawingState.NPDrawing) {
				finish(e.getX(), e.getY());
				drawingState = EDrawingState.idle;
			}
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON1) {
				if(e.getClickCount() == 1) {
					button1Clicked(e);
				} else if(e.getClickCount() == 2) {
					button1DblClicked(e);
				}
			}
		}
		
		public void button1Clicked(MouseEvent e) {
			if(drawingState == EDrawingState.idle) {
				pointerState = includes(e.getX(), e.getY());
				if(pointerState == null) {
					if(drawingTool.getShapeType() == EShapeType.NP) {
						drawingState = EDrawingState.NPDrawing;
						init(e.getX(), e.getY());
					}
				} else {
					selectedShape.setSelected(true);
				}
			} else if(drawingState == EDrawingState.NPDrawing) {
				nPAddPoint(e.getX(), e.getY());
			}
		}
		
		public void button1DblClicked(MouseEvent e) {
			if(drawingState == EDrawingState.NPDrawing) {
				finish(e.getX(), e.getY());
				drawingState = EDrawingState.idle;
			}
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {
			if(drawingState == EDrawingState.idle) {
				pointerState = includes(e.getX(), e.getY());
				changePointer(pointerState);
			} else if(drawingState == EDrawingState.NPDrawing) {
				keep(e.getX(), e.getY());
			}
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			
		}
	}

	public void setsetShapeList(Vector<GEShape> obj) {
		try {
			currentShape = drawingTool.getClass().newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.shapes = obj;
		repaint();		
	}
	
	/* edit function */
	public void redo() {
		shapes.clear();
		Vector<GEShape> temp = stack.pop(1);
		if(temp != null) {
			for(int i=0; i<temp.size(); i++) {
				shapes.add(temp.get(i).deepcopy());
			}
		}
		repaint();
	}
	
	public void undo() {
		shapes.clear();
		Vector<GEShape> temp = stack.pop(-1);
		if(temp != null) {
			for(int i=0; i<temp.size(); i++) {
				shapes.add(temp.get(i).deepcopy());
			}
		}
		repaint();
	}
	
	public void cut() {
		clipboard.cut(shapes);
		stack.push(shapes);
		repaint();
	}
	
	public void copy() {
		clipboard.copy(shapes);
		stack.push(shapes);
	}
	
	public void paste() {
		/*GEShape tempshape;
		for(GEShape shape : clipboard.paste()){ // 붙일려고 하는 도형중에서
			tempshape = shape.deepcopy();
			for(GEShape curshape : shapes) { // 현재 있는 도형중에서
				if(tempshape.getShape().getBounds().equals(curshape.getShape().getBounds())) { // 완전히 겹치면 
					trans = new GEMover(tempshape);
					trans.init((int)tempshape.getShape().getBounds().getX(), 
							   (int)tempshape.getShape().getBounds().getY());
					trans.Transform((int)tempshape.getShape().getBounds().getX() + 10, 
									(int)tempshape.getShape().getBounds().getY() + 10); // 시작점을 5,5씩이동
					break;
				}
			}
			shapes.add(tempshape);
		}*/
		for(GEShape shape : clipboard.paste()) {
			shapes.add(shape.deepcopy());
		}
		stack.push(shapes);
		repaint();
	}
	
	public void delete() {
		for(int i=shapes.size(); i>0; i--) {
			GEShape temp = shapes.get(i-1);
			if(temp.isSelected()) {
				temp.setSelected(false);
				shapes.remove(temp);
			}
		}
		stack.push(shapes);
		repaint();
	}
	
	public void group() {
		for(int i=shapes.size(); i>0; i--) {
			GEShape temp = shapes.get(i-1);
			if(temp.isSelected()) {
				temp.setSelected(false);
				group.addshape(temp.deepcopy());
				shapes.remove(temp);
			}
		}
		shapes.add(group);
		group.setSelected(true);
		stack.push(shapes);
		repaint();
	}
	
	public void ungroup() {
		Vector<GEShape> templist = new Vector<GEShape>();
		for(int i=shapes.size(); i>0; i--) {
			GEShape temp = shapes.get(i-1);
			if(temp instanceof GEGroup && temp.isSelected()) {
				for(GEShape subshape : ((GEGroup)temp).getSubShapes()) {
					subshape.setSelected(true);
					templist.add(subshape);
				}
				shapes.remove(temp);
			}
		}
		shapes.addAll(templist);
		stack.push(shapes);
		repaint();
	}
	
	public void nnew() {
		shapes = new Vector<GEShape>();
		stack = new GEStack();
		repaint();
	}
	 
}
