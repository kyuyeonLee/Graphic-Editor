package toolBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import drawingPanel.DrawingPanel;
import global.Constants.EToolBar;

public class ToolBar extends JToolBar {
	
	//attributes
	private static final long serialVersionUID = 1L;
	// components
	private Vector<JRadioButton> buttons;
	// association
	private DrawingPanel drawingPanel;

	public void associate(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}

	public ToolBar() {
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
			drawingPanel.setCurrentTool(EToolBar.valueOf(e.getActionCommand()));// EToolBar����
																				// 'valueOf(e.getActionCommand())'��� �̸���
																				// ���� ��ü�� ã�ƶ�
		}
	}
}
