package menu;

import javax.swing.JMenuBar;

import drawingPanel.GDrawingPanel;
import global.GConstants.EMenu;

@SuppressWarnings("serial")
public class GMenuBar extends JMenuBar {

	// components
	private GFileMenu fileMenu;

	// associate
	private GDrawingPanel drawingPanel;

	public void associate(GDrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}

	public GMenuBar() {
		this.fileMenu = new GFileMenu(EMenu.fileMenu.getText());
		this.add(this.fileMenu);
	}

	public void initialize() {
		this.fileMenu.initialize();
	}

}
