package main;
import javax.swing.JFrame;

import frames.GEMainFrame;


public class GELauncher {
	public static void main(String[] args) {
		// frame object creation
		GEMainFrame frame = new GEMainFrame();
		frame.initialize();
		frame.setTitle("Graphic Editor1.0");
	}
}
