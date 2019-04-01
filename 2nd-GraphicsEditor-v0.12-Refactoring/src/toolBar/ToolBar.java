package toolBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JToolBar;

import drawingPanel.DrawingPanel;
import global.Constants.EToolBar;

@SuppressWarnings("serial")
public class ToolBar extends JToolBar{

	// components
		private Vector<JButton> buttons; 
		// association
		private DrawingPanel drawingPanel;

		public void associate(DrawingPanel drawingPanel) {
			this.drawingPanel = drawingPanel;
		}
		public ToolBar() {
			ActionHandler actionHandler = new ActionHandler();
			this.buttons = new Vector<JButton>();
			for (EToolBar eToolBar: EToolBar.values()) {
				JButton button = new JButton(eToolBar.getText());
				button.setActionCommand(eToolBar.name());
				button.addActionListener(actionHandler);
				this.buttons.add(button);
				this.add(button);
			}
		}
		private class ActionHandler implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				drawingPanel.setCurrentTool(EToolBar.valueOf(e.getActionCommand()));//EToolBar에서 'valueOf(e.getActionCommand())'라는 이름을 가진 객체를 찾아라
			}
	}
}

