package menu;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import global.Constants.EFileMenu;

@SuppressWarnings("serial")
public class FileMenu extends JMenu {

	private JMenuItem newItem;
	
	public FileMenu(String text) {
		super(text);
		this.newItem = new JMenuItem(EFileMenu.newItem.getText());
		this.add(newItem);
	}
	
}
