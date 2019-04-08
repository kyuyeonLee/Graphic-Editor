package transform;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Vector;

import shapes.GEGroup;
import shapes.GEShape;

public class GERotater extends GETransformer {
	protected Point2D.Double ROrigin;
	protected double theta;

	public GERotater(GEShape shape) {
		super(shape);
		if(shape instanceof GEGroup) {
			shapelist = new Vector<GEShape>();
			for(GEShape subshape : ((GEGroup) shape).getSubShapes()) {
				shapelist.add(subshape);
			}
		}
		af = new AffineTransform();
	}

	@Override
	public void init(int x, int y) {
		// 회전할 도형의 중심을 구합니다
		ROrigin = new Point2D.Double(shape.getShape().getBounds().getCenterX(), shape.getShape().getBounds().getCenterY());
		//  마우스 포인터와 중심점이 이루는 각을 구합니다
		theta = Math.atan2(ROrigin.y - y, x - ROrigin.x);
	}

	@Override
	public void Transform(int x, int y) {
		// 처음 구한 각에서 이동한 각(마우스 포인터로 움직인 각도)을 구합니다
		double theta2 = theta - Math.atan2(ROrigin.y - y, x - ROrigin.x);
		
		// AffineTrasform으로 회전변환시킴
		af.setToRotation(theta2, ROrigin.x, ROrigin.y);
		//shape.getAnchor().rotate(theta2, ROrigin);
		shape.getAnchor().resize(shape.getShape().getBounds());
		
		if(shape instanceof GEGroup) { // 그룹이면 내부 원소변경
			GEShape temp;
			for(int i=0; i < shapelist.size(); i++) {
				temp = shapelist.get(i);
				temp.setShape(af.createTransformedShape(temp.getShape()));
				shape.setShape(shape.getShape().getBounds().createUnion(temp.getShape().getBounds()));
			}
		} else {
			shape.setShape(af.createTransformedShape(shape.getShape())); // 자신 변경
		}
		
		//shape.setShape(af.createTransformedShape(shape.getShape())); // 자신 변경
		theta = Math.atan2(ROrigin.y - y, x - ROrigin.x); // 이동한 각 저장
	}

}
