package menu;

import javax.swing.JMenuBar;

import drawingPanel.DrawingPanel;
import global.Constants.EMenu;

@SuppressWarnings("serial")
public class MenuBar extends JMenuBar{

	// components
		private FileMenu fileMenu;

		// associate
		private DrawingPanel drawingPanel;
		public void associate(DrawingPanel drawingPanel) {
			this.drawingPanel = drawingPanel;
		}
		
		public MenuBar() {
			this.fileMenu = new FileMenu(EMenu.fileMenu.getText());
			this.add(this.fileMenu);
	}

}
