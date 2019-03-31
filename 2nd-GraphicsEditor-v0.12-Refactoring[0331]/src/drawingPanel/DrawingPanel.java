package drawingPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import global.Constants.EToolBar;
import shape.Shape;

public class DrawingPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private enum EActionState {
		eReady, eCMCDrawing, ePDRDrawing
	};

	private EActionState eActionState;

	public static final Enum<EToolBar> eToolBar = null;

	private static final Graphics Graphics = null;
	MouseHandler mouseHandler;
	private Shape currentTool;

	public void setCurrentTool(EToolBar CurrenTool) {
		this.currentTool = CurrenTool.getShape();
	}

	public DrawingPanel() {
		this.eActionState = EActionState.eReady;

		this.setBackground(Color.WHITE);
		// 마우스 클릭이랑 움직이는 것을 따로 따로 달아야하는데 한번에 달음
		mouseHandler = new MouseHandler();
		this.addMouseListener(this.mouseHandler);
		this.addMouseMotionListener(this.mouseHandler);
		currentTool = EToolBar.rect.getShape();
	}

	public void drawShape() {
		Graphics graphics = this.getGraphics();
		graphics.setXORMode(getBackground());
		this.currentTool.draw(graphics);
	}

	public void initDrawing(int x, int y) {
		this.currentTool.setOrigin(x, y); // 그림을 그릴 준비를 하는 것 initDraw
		this.drawShape();
	}

	public void keepDrawing(int x, int y) {
		this.drawShape();
		this.currentTool.setPoint(x, y);
		this.drawShape();
	}

	private void continueDrawing(int x, int y) {
		this.currentTool.addPoint(x, y);

	}

	private void finishDrawing(int x, int y) {
		this.drawShape();
		this.currentTool.addPoint(x, y);
		this.drawShape();

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
				eActionState = EActionState.eCMCDrawing;
			} else if (eActionState == EActionState.eCMCDrawing) {
				finishDrawing(e.getX(), e.getY());
				eActionState = EActionState.eReady;
			}
		}

		public void mouse2Clicked(MouseEvent e) {
			repaint();
			finishDrawing(e.getX(), e.getY());
			}

		public void mouseMoved(MouseEvent e) {// 그림을 그리는 경우에만 작동하게 만들어야한다.
			if (eActionState == EActionState.eCMCDrawing) {
				continueDrawing(e.getX(), e.getY());
				eActionState = EActionState.eReady;

			}
		}

		// Press-Drag-Release Drawing
		public void mousePressed(MouseEvent e) {
			if (eActionState == EActionState.eReady) {
				initDrawing(e.getX(), e.getY());
				eActionState = EActionState.ePDRDrawing;
			}
		}

		public void mouseDragged(MouseEvent e) {
			if (eActionState == EActionState.ePDRDrawing) {
				keepDrawing(e.getX(), e.getY());
			}
		}

		public void mouseReleased(MouseEvent e) {
			if (eActionState == EActionState.ePDRDrawing) {
				finishDrawing(e.getX(), e.getY());
				eActionState = EActionState.eReady;
			}
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}
	}
}
