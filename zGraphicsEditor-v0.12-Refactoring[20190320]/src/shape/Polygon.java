package shape;

import java.awt.Graphics;

public class Polygon extends Shape{
	
	private final static int nMaxPoints = 20;
	private int nPoints;
	private int[] xPoints;
	private int[] yPoints;
	
	public Polygon() {
		this.nPoints = 0;
		this.xPoints = new int[nMaxPoints];
		this.yPoints = new int[nMaxPoints];
		
	}
	
	public void setOrigin(int x,int y) {
		//점을 하나 추가하는 것을 아래 3줄로 만듬.. -> 점을 추가한다는 것은 현재에 npoint에 새로운점을 추가하고 npoint를 +1해준다
		this.xPoints[this.nPoints] = x;  
		this.yPoints[this.nPoints] = y;
		this.nPoints = this.nPoints +1;

		this.xPoints[this.nPoints] = x; 
		this.yPoints[this.nPoints] = y;
		this.nPoints = this.nPoints +1;
	}
	
	public void setPoint(int x, int y) {
		this.xPoints[this.nPoints-1] = x;
		this.yPoints[this.nPoints-1] = y;
	}
	
	public void addPoint(int x, int y) {
		this.xPoints[this.nPoints] = x; 
		this.yPoints[this.nPoints] = y;
		this.nPoints = this.nPoints +1;
	}
	
	public void draw(Graphics g) {
		g.drawPolygon(xPoints, yPoints, nPoints);
	}
}
