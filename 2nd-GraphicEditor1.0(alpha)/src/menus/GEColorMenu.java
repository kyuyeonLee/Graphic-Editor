package menus;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JColorChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import main.GEConstants;
import main.GEConstants.EColorMenuItems;
import frames.GEDrawingPanel;


public class GEColorMenu extends JMenu {
	
	private Vector<JMenuItem> menuItems;
	private ActionHandler handler;
	
	private GEDrawingPanel panel;
	
	public GEColorMenu(String string) {
		super(string);
		
		menuItems = new Vector<JMenuItem>();
		handler = new ActionHandler();
		
		for(EColorMenuItems eMenuItem: EColorMenuItems.values()) {
			JMenuItem menuItem = new JMenuItem(eMenuItem.getName());
			menuItem.setActionCommand(eMenuItem.name());
			menuItem.addActionListener(handler);
			menuItems.add(menuItem);
			this.add(menuItem);
		}
	}
	
	public void initialize(GEDrawingPanel panel) {
		this.panel = panel;		
	}
	
	public void setPanel(GEDrawingPanel panel) {
		this.panel = panel;
	}
	
	public void changeLineColor() {
		Color color = JColorChooser.showDialog(null, EColorMenuItems.lineColor.name(), null);
		if(color != null) {
			panel.setLineColor(color);
		}
	}
	
	public void changeFillColor() {
		Color color = JColorChooser.showDialog(null, GEConstants.EColorMenuItems.fillColor.name(), null);
		if(color != null) {
			panel.setFillColor(color);
		}
	}
	
	private void clearLineColor() {
		panel.setLineColor(getForeground());
	}
	

	private void clearFillColor() {
		panel.setFillColor(getBackground());
	}
	
	private class ActionHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals(GEConstants.EColorMenuItems.lineColor.name())) {
				changeLineColor();
			} else if(e.getActionCommand().equals(GEConstants.EColorMenuItems.fillColor.name())) {
				changeFillColor();
			} else if(e.getActionCommand().equals(GEConstants.EColorMenuItems.clearLineColor.name())) {
				clearLineColor();
			} else if(e.getActionCommand().equals(GEConstants.EColorMenuItems.clearFillColor.name())) {
				clearFillColor();
			}
		}
	}
}
