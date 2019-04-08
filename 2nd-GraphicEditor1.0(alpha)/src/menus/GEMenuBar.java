package menus;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import frames.GEDrawingPanel;

import main.GEConstants;
import main.GEConstants.EMenus;



public class GEMenuBar extends JMenuBar {

	private GEFileMenu fileMenu;
	private GEEditMenu editMenu;
	private GEColorMenu colorMenu;
	
	private GEDrawingPanel panel;
	
	public GEMenuBar() {
		super();
		
		fileMenu = new GEFileMenu(GEConstants.EMenus.File.getName());
		this.add(fileMenu);
		editMenu = new GEEditMenu(GEConstants.EMenus.Edit.getName());
		this.add(editMenu);
		colorMenu = new GEColorMenu(GEConstants.EMenus.Color.getName());
		this.add(colorMenu);
	}
	
	public void setPanel(GEDrawingPanel panel) {
		this.panel = panel;
		colorMenu.setPanel(this.panel);
	}

	public void initialize(GEDrawingPanel panel) {
		fileMenu.initialize(panel);
		editMenu.initialize(panel);
		colorMenu.initialize(panel);
	}
}
