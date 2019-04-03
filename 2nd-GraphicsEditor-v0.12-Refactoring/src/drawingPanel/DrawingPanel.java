package drawingPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JPanel;

import global.Constants.EToolBar;
import shape.GShape;

public class DrawingPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private enum EActionState {eReady,  e2PDrawing, eNPDrawing};
	private EActionState eActionState;
	MouseHandler mouseHandler;
	
	private Vector<GShape> shapeVector;
	private GShape currentShape;
	private GShape currentTool;
	public void setCurrentTool(EToolBar CurrenTool) {
		this.currentTool = CurrenTool.getShape();
	}

	public DrawingPanel() {
		this.eActionState = EActionState.eReady;

		this.setBackground(Color.WHITE);
		// ���콺 Ŭ���̶� �����̴� ���� ���� ���� �޾ƾ��ϴµ� �ѹ��� ����
		mouseHandler = new MouseHandler();
		this.addMouseListener(this.mouseHandler);
		this.addMouseMotionListener(this.mouseHandler);
		
		this.shapeVector = new Vector<GShape>();
	}
	public void initialize() {
		// TODO Auto-generated method stub
		
	}
	
	public void paint(Graphics graphics) {
		super.paint(graphics);
		for (GShape shape: this.shapeVector) {
			shape.draw(graphics);
		}
	}
	public void drawShape() {
		Graphics graphics = this.getGraphics();
		graphics.setXORMode(getBackground());
		this.currentShape.draw(graphics);
	}
	private boolean onShape(int x, int y) {
		currentShape = null;
		for(GShape shape: this.shapeVector) {
			if(shape.contains(x, y)) {
				this.currentShape = shape;
				return true;
			}
		}
		return false;
	}
	public void initDrawing(int x, int y) {
		// �׸��� �׸� �غ� �ϴ� �� initDraw
		this.currentShape = this.currentTool.clone();
		this.currentShape.setOrigin(x, y); 
		this.drawShape();
	}
	public void keepDrawing(int x, int y) {
		this.drawShape();
		this.currentShape.setPoint(x, y);
		this.drawShape();
	}
	private void continuedrawing(int x, int y) {
		this.currentShape.addPoint(x, y);
	}
	private void finishDrawing(int x, int y) {
		this.shapeVector.add(this.currentShape);
	}

	private class MouseHandler implements MouseListener, MouseMotionListener {
		// Click-Move-Click Drawing
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 1) {
				mouse1Clicked(e);
			} else if (e.getClickCount() == 2) {
				mouse2Clicked(e);
			}
		}

		public void mouse1Clicked(MouseEvent e) {
			if (eActionState == EActionState.eReady) {
				initDrawing(e.getX(), e.getY());
				eActionState = EActionState.eNPDrawing;
			}else if (eActionState == EActionState.eNPDrawing){
				continuedrawing(e.getX(), e.getY());
			}
		}

		public void mouse2Clicked(MouseEvent e) {
			if (eActionState == EActionState.eNPDrawing) {
					finishDrawing(e.getX(), e.getY());
					eActionState = EActionState.eReady;
				}
		}

		public void mouseMoved(MouseEvent e) {
			 if(eActionState == EActionState.eNPDrawing) {
					keepDrawing(e.getX(), e.getY());
				}
		}

		
		
		// Press-Drag-Release Drawing
		public void mousePressed(MouseEvent e) {
			
			if (eActionState == EActionState.eReady) {
				if(onshape(e.getX(), e.getY())) {
					initMoving(e.getX(), e.getY());
					eActionState = EActionState.eMoving;
				}else {
					initDrawing(e.getX(), e.getY());
					eActionState = EActionState.e2PDrawing;
				}
			}
		}

		public void mouseDragged(MouseEvent e) {
			if (eActionState == EActionState.e2PDrawing) {
				keepDrawing(e.getX(), e.getY());
			}else if(eActionState == EActionState.){
				
			}
		}

		public void mouseReleased(MouseEvent e) {
			if (eActionState == EActionState.e2PDrawing) {
				finishDrawing(e.getX(), e.getY());
				eActionState = EActionState.eReady;
			}
		}

		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
	}

}
