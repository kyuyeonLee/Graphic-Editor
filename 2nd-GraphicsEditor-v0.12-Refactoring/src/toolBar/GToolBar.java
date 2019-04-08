package toolBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import drawingPanel.GDrawingPanel;
import global.GConstants.EToolBar;

public class GToolBar extends JToolBar {
	
	//attributes
	private static final long serialVersionUID = 1L;
	// components
	private Vector<JRadioButton> buttons;
	// association
	private GDrawingPanel drawingPanel;

	public void associate(GDrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}

	public GToolBar() {
		ButtonGroup buttonGroup = new ButtonGroup();
		this.buttons = new Vector<JRadioButton>();
		ActionHandler actionHandler = new ActionHandler();
		for (EToolBar eToolBar : EToolBar.values()) {
			JRadioButton button = new JRadioButton(eToolBar.getText());
			button.setActionCommand(eToolBar.name());
			button.addActionListener(actionHandler);
			this.buttons.add(button);
			this.add(button);
			buttonGroup.add(button);
		}
	}

	public void initialize() {
		this.buttons.get(EToolBar.rect.ordinal()).doClick();
	}

	private class ActionHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			drawingPanel.setCurrentTool(EToolBar.valueOf(e.getActionCommand()));// EToolBar에서
																				// 'valueOf(e.getActionCommand())'라는 이름을
																				// 가진 객체를 찾아라
		}
	}
}
