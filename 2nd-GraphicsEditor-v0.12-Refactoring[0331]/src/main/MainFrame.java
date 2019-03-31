package main;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import drawingPanel.DrawingPanel;
import global.Constants.EMainFrame;
import menu.MenuBar;
import toolBar.ToolBar;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	// Component = 안에서 new를 하면 된다.
	private MenuBar menuBar;
	private ToolBar toolBar;
	private DrawingPanel drawingPanel;

	public MainFrame() {
		// attributes
		this.setLocation(EMainFrame.x.getvalue(), EMainFrame.y.getvalue());
		this.setSize(EMainFrame.w.getvalue(), EMainFrame.h.getvalue());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// components
		this.menuBar = new MenuBar();
		this.setJMenuBar(this.menuBar);

		this.setLayout(new BorderLayout());
		this.toolBar = new ToolBar();
		this.add(this.toolBar, BorderLayout.NORTH);

		this.drawingPanel = new DrawingPanel();
		this.add(this.drawingPanel, BorderLayout.CENTER);

		// association
		this.menuBar.associate(this.drawingPanel);
		this.toolBar.associate(this.drawingPanel);

	}

}
