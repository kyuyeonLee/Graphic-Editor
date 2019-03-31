package global;

import shape.Rectangle;
import shape.Polygon;
import shape.Shape;

public class Constants {

	public enum EMainFrame {
		x(200),
		y(100),
		w(400),
		h(600);
		private int value;
		private EMainFrame(int value) {
			this.value = value;
		}
		public int getvalue() {
			return this.value;
		}
	}

	public enum EToolBar {
		rect("네모", new Rectangle()),
		rect1("네모", new Rectangle()),
		polygon("다각형", new Polygon()),
		;
		private String text;
		private Shape shape;
		private EToolBar(String text, Shape shape) {
			this.text = text;
			this.shape = shape;
		}
		public String getText() {
			return this.text;
		}
		public Shape getShape() {
			return this.shape;
		}
	}
	
	public enum EMenu {
		fileMenu("파일"),
		editMenu("Edit");
		private String text;
		private EMenu(String text) {
			this.text = text;
		}
		public String getText() {
			return this.text;
		}
	}

	public enum EFileMenu {
		newItem("새로만들기"),
		openItem("open");
		private String text;
		private EFileMenu(String text) {
			this.text = text;
		}
		public String getText() {
			return this.text;
		}
	}
}
