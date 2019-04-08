package menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import main.GEConstants;
import main.GEConstants.EEditMenuItems;
import frames.GEDrawingPanel;


public class GEEditMenu extends JMenu {
	private GEDrawingPanel panel;
	private Vector<JMenuItem> menuItems;
	private ActionHandler handler;
	
	public GEEditMenu(String string) {
		super(string);
		
		menuItems = new Vector<JMenuItem>();
		handler = new ActionHandler();
		
		for(GEConstants.EEditMenuItems eMenuItem: GEConstants.EEditMenuItems.values()) {
			JMenuItem menuItem = new JMenuItem(eMenuItem.getName());
			menuItem.addActionListener(handler);
			menuItems.add(menuItem);
			this.add(menuItem);
		}
	}
	
	public void initialize(GEDrawingPanel panel) {
		this.panel = panel;		
	}
	
	private class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String action = e.getActionCommand();
			if(action.equals(EEditMenuItems.redo.getName())) {
				panel.redo();
			} else if(action.equals(EEditMenuItems.undo.getName())) {
				panel.undo();
			} else if(action.equals(EEditMenuItems.cut.getName())) {
				panel.cut();
			} else if(action.equals(EEditMenuItems.copy.getName())) {
				panel.copy();
			} else if(action.equals(EEditMenuItems.delete.getName())) {
				panel.delete();
			} else if(action.equals(EEditMenuItems.paste.getName())) {
				panel.paste();
			} else if(action.equals(EEditMenuItems.group.getName())) {
				panel.group();
			} else if(action.equals(EEditMenuItems.ungroup.getName())) {
				panel.ungroup();
			}
		}
	}
}
