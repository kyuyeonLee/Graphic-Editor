package drawingPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JPanel;

import global.GConstants.EToolBar;
import shape.GShape;
import shape.GPolygon;

public class GDrawingPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private enum EActionState {
		eReady, e2PDrawing, eNPDrawing, eMoving, eResizing, eRotating
	};

	private EActionState eActionState;
	MouseHandler mouseHandler;

	private Vector<GShape> shapeVector;
	private GShape currentShape;
	private GShape currentTool;

	public void setCurrentTool(EToolBar CurrenTool) {
		this.currentTool = CurrenTool.getShape();
	}

	public GDrawingPanel() {
		this.eActionState = EActionState.eReady;

		this.setForeground(Color.BLACK);
		this.setBackground(Color.WHITE);
		// 마우스 클릭이랑 움직이는 것을 따로 따로 달아야하는데 한번에 달음
		mouseHandler = new MouseHandler();
		this.addMouseListener(this.mouseHandler);
		this.addMouseMotionListener(this.mouseHandler);

		this.shapeVector = new Vector<GShape>();
	}
	public void initialize() {
	}
	public void paint(Graphics graphics) {
		super.paint(graphics);
		for (GShape shape : this.shapeVector) {
			shape.draw(graphics);
		}
	}
	public void drawShape() {
		Graphics2D graphics2d = (Graphics2D)this.getGraphics();
		graphics2d.setXORMode(getBackground());
		this.currentShape.draw(graphics2d);
	}

	private boolean onShape(int x, int y) {
		currentShape = null;
		for (GShape shape : this.shapeVector) {
			if (shape.contains(x, y)) {
				this.currentShape = shape;
				return true;
			}
		}
		return false;
	}

	public void initDrawing(int x, int y) {
		// 그림을 그릴 준비를 하는 것 initDraw
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

	
	
	public void initMoving(int x, int y) {
		this.currentShape.initMoving(x, y);
	}
	public void keepMoving(int x, int y) {
		this.drawShape();
		this.currentShape.keepMoving(x, y);
		this.drawShape();
	}
	private void finishMoving(int x, int y) {
		this.currentShape.finishMoving(x, y);
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
			} else if (eActionState == EActionState.eNPDrawing) {
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
			if (eActionState == EActionState.eNPDrawing) {
				keepDrawing(e.getX(), e.getY());
			}
		}

		// Press-Drag-Release Drawing
		public void mousePressed(MouseEvent e) {
			if (eActionState == EActionState.eReady) {
				if (onShape(e.getX(), e.getY())) {
					initMoving(e.getX(), e.getY());
					eActionState = EActionState.eMoving;
				} else {
					if (!(currentTool instanceof GPolygon)) {
						initDrawing(e.getX(), e.getY());
						eActionState = EActionState.e2PDrawing;
					}
				}
			}
		}
		public void mouseReleased(MouseEvent e) {
			if (eActionState == EActionState.e2PDrawing) {
				finishDrawing(e.getX(), e.getY());
				eActionState = EActionState.eReady;
			} else if (eActionState == EActionState.eMoving) {
				finishMoving(e.getX(), e.getY());
				eActionState = EActionState.eReady;
			}
		}
		public void mouseDragged(MouseEvent e) {
			if (eActionState == EActionState.e2PDrawing) {
				keepDrawing(e.getX(), e.getY());
			} else if (eActionState == EActionState.eMoving) {
				keepMoving(e.getX(), e.getY());
			}
		}

		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
	}
}
