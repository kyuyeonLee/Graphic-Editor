package main;

public class GMain {

	static GMainFrame mainFrame;
	public static void main(String[] args) {
		mainFrame = new GMainFrame();
		mainFrame.initialize();
		mainFrame.setVisible(true);
	}
}
