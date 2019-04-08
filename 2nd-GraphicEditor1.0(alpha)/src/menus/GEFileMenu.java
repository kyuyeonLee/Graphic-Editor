package menus;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import shapes.GEShape;

import frames.GEDrawingPanel;

import main.GEConstants;
import main.GEConstants.EFileMenuItems;

public class GEFileMenu extends JMenu {
	
	private GEDrawingPanel panel;
	private Vector<JMenuItem> menuItems;
	private ActionHandler handler; 
	
	public GEFileMenu(String string) {
		super(string);
		
		menuItems = new Vector<JMenuItem>();
		handler = new ActionHandler();
		
		for(GEConstants.EFileMenuItems eMenuItem: GEConstants.EFileMenuItems.values()) {
			JMenuItem menuItem = new JMenuItem(eMenuItem.getName());
			menuItem.addActionListener(handler);
			menuItems.add(menuItem);
			this.add(menuItem);
		}
	}
	
	private void nnew() {
		panel.nnew();
	}
	
	private void load() {
		JFileChooser fileDialog = new JFileChooser(new File("*.*"));
		fileDialog.showOpenDialog(null);
		File file = fileDialog.getSelectedFile();
		ObjectInputStream in = null;
		if(file != null) {
			try {
				in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
				Object obj = in.readObject();
				panel.setsetShapeList((Vector<GEShape>) obj);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				if(in != null) {
					try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}


	private void save() {
		JFileChooser fileDialog = new JFileChooser(new File("*.*"));
		fileDialog.showSaveDialog(null);
		File file = fileDialog.getSelectedFile();
		ObjectOutputStream out = null;
		if(file != null) {
			try {
				out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
				out.writeObject(panel.getShapes());
			} catch(FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				if(out != null) {
					try {
						out.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}
	
	private class ActionHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String action = e.getActionCommand();
			if(action.equals(EFileMenuItems.nnew.getName())) {
				nnew();
			} else if(action.equals(EFileMenuItems.open.getName())) {
				load();
			} else if(action.equals(EFileMenuItems.save.getName())) {
				save();
			} else if(action.equals(EFileMenuItems.exit.getName())) {
				System.exit(0);
			}
		}
	}

	public void initialize(GEDrawingPanel panel) {
		this.panel = panel;		
	}
}
