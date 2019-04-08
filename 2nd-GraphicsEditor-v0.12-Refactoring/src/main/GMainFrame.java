package main;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import drawingPanel.GDrawingPanel;
import global.GConstants.EMainFrame;
import menu.GMenuBar;
import toolBar.GToolBar;

@SuppressWarnings("serial")
public class GMainFrame extends JFrame {

	// Component = 안에서 new를 하면 된다.
	private GMenuBar menuBar;
	private GToolBar toolBar;
	private GDrawingPanel drawingPanel;

	public GMainFrame() {
		// attributes
		this.setLocation(EMainFrame.x.getvalue(), EMainFrame.y.getvalue());
		this.setSize(EMainFrame.w.getvalue(), EMainFrame.h.getvalue());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// components
		this.menuBar = new GMenuBar();
		this.setJMenuBar(this.menuBar);

		this.setLayout(new BorderLayout());
		this.toolBar = new GToolBar();
		this.add(this.toolBar, BorderLayout.NORTH);

		this.drawingPanel = new GDrawingPanel();
		this.add(this.drawingPanel, BorderLayout.CENTER);

		// association
		this.menuBar.associate(this.drawingPanel);
		this.toolBar.associate(this.drawingPanel);

	}

	public void initialize() {
		this.menuBar.initialize();
		this.toolBar.initialize();
		this.drawingPanel.initialize();
	}

}
