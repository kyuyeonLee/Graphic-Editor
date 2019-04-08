package menu;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import global.GConstants.EFileMenu;

@SuppressWarnings("serial")
public class GFileMenu extends JMenu {

	private JMenuItem newItem;
	
	public GFileMenu(String text) {
		super(text);
		this.newItem = new JMenuItem(EFileMenu.newItem.getText());
		this.add(newItem);
	}

	public void initialize() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
