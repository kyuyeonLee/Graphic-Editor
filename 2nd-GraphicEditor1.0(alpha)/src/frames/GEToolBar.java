package frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import main.GEConstants;
import main.GEConstants.EToolBarButtons;

import shapes.GEShape;



public class GEToolBar extends JToolBar {

	// Attribute
	
	// Components
	private ButtonGroup buttonGroup;
	private Vector<JRadioButton> buttons;
	private ActionHandler handler;
	
	// Association
	private GEDrawingPanel panel;
	
	public GEToolBar() {
		super();
		
		buttons = new Vector<JRadioButton>();
		buttonGroup = new ButtonGroup();
		
		handler = new ActionHandler();
		
		for(GEConstants.EToolBarButtons eToolBarButton: GEConstants.EToolBarButtons.values()) {
			JRadioButton button = new JRadioButton();
			button.setIcon(new ImageIcon(GEConstants.libraryPath
											+ eToolBarButton + GEConstants.imageSufix));
			button.setSelectedIcon(new ImageIcon(GEConstants.libraryPath
					+ eToolBarButton + GEConstants.selectedImage + GEConstants.imageSufix));
			button.setActionCommand(eToolBarButton.getClassName());
			button.addActionListener(handler);
			buttons.add(button);
			buttonGroup.add(button);
			this.add(button);
		}	
	}
	
	public void initialize(GEDrawingPanel panel) {
		this.panel = panel;
		buttons.get(EToolBarButtons.rectangle.ordinal()).doClick();
	}	
	
	public GEDrawingPanel getPanel() {
		return panel;
	}

	public void setPanel(GEDrawingPanel panel) {
		this.panel = panel;
	}

	private class ActionHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				panel.setDrawingTool((GEShape)Class.forName(e.getActionCommand()).newInstance());
			} catch (InstantiationException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}

	
}
