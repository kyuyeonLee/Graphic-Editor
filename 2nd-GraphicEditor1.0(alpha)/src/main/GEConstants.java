package main;
import shapes.*;


public class GEConstants {

	public static enum EMenus {
		File("파일"), Edit("편집"), Color("색상");
		private String name;
		private EMenus(String s) {this.name = s;}
		public String getName() {return this.name;}
	}
	
	public static enum EFileMenuItems {
		nnew("새로만들기"), open("열기"), save("저장"), exit("종료");
		private String name;
		private EFileMenuItems(String s) {this.name = s;}
		public String getName() {return this.name;}
	}
	
	public static enum EEditMenuItems {
		redo("재실행"), undo("실행취소"), copy("복사"), cut("잘라내기"), paste("붙여넣기"), delete("삭제"), group("그룹"), ungroup("그룹해제");
		private String name;
		private EEditMenuItems(String s) {this.name = s;}
		public String getName() {return this.name;}
	}
	
	public static enum EColorMenuItems {
		lineColor("선색"), fillColor("면색"), clearLineColor("선색 초기화"), clearFillColor("면색 초기화");
		private String name;
		private EColorMenuItems(String s) {this.name = s;}
		public String getName() {return this.name;}
	}
	
	public static enum EToolBarButtons {
		group("그룹", GESelect.class.getName()), // 그리는것은 사각형으로 그리고 GEGroup은 Panel 멤버로 따로 처리한다
		rectangle("사각형", GERectangle.class.getName()),
		ellipse("원", GEEllipse.class.getName()),
		line("선", GELine.class.getName()),
		polygon("다각형", GEPolygon.class.getName());
		
		private String label;
		private String className;
		private EToolBarButtons(String label, String className) {
			this.label = label;
			this.className = className;
		}
		public String getLabel() {return this.label;}
		public String getClassName() {return className;}
	}
	
	public static enum EShapeType {
		TP, NP, GR
	}
	
	public static final String imageSufix = ".gif";
	public static final String libraryPath = "img/";
	public static final String selectedImage = "SLT";
	
	public static enum EPointerState {
		NW, WW, SW, NN, SS, NE, EE, SE, RR, MM
	}
	
}
