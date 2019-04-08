package transform;

import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Vector;

import main.GEConstants.EPointerState;
import shapes.*;

public class GEResizer extends GETransformer {

	public GEResizer(GEShape shape) {
		super(shape);
		if(shape instanceof GEGroup) {
			shapelist = new Vector<GEShape>();
			for(GEShape subshape : ((GEGroup) shape).getSubShapes()) {
				shapelist.add(subshape);
			}
		}
		af = new AffineTransform();
	}

	public void init(int x, int y) {
		oldX = shape.getShape().getBounds().x;
		oldY = shape.getShape().getBounds().y;
	}

	public void Transform(int x, int y, EPointerState pointer) {
		Point2D.Double ratio = new Point2D.Double();
		Point move = new Point();
		int width = shape.getShape().getBounds().width;
		int height = shape.getShape().getBounds().height;
		
/*-----------------------------------------------------------------------------------------------------------------*/
		
		move.x = -oldX; move.y = -oldY;
		af.setToTranslation(move.x, move.y); // 원점으로 이동시킴
			
		if(shape instanceof GEGroup) { // 그룹이면 내부 원소변경
			GEShape temp;
			for(int i=0; i < shapelist.size(); i++) {
				temp = shapelist.get(i);
				temp.setShape(af.createTransformedShape(temp.getShape()));
			}
		}
		
		shape.setShape(af.createTransformedShape(shape.getShape())); // 자신 변경
		
/*-----------------------------------------------------------------------------------------------------------------*/
		
		/* 좌표변경 */
		switch (pointer) {
		case NE: //↗
			ratio.x = (double)(x - oldX) / (width);
			ratio.y = (double)(oldY - y + height) / (height);
			move.x = oldX; move.y = y;
			oldY = y;
			break;
		case EE: //→
			ratio.x = (double)(x - oldX) / (width);
			ratio.y = 1;
			move.x = oldX; move.y = oldY;
			break;
		case SE: //↘
			ratio.x = (double)(x - oldX) / (width);
			ratio.y = (double)(y - oldY) / (height);
			move.x = oldX; move.y = oldY;
			break;
		case SS: //↓
			ratio.x = 1;
			ratio.y = (double)(y - oldY) / (height);
			move.x = oldX; move.y = oldY;
			break;
		case SW: //↙
			ratio.x = (double)(oldX - x + width) / (width);
			ratio.y = (double)(y - oldY) / (height);
			move.x = x; move.y = oldY;
			oldX = x; 
			break;
		case WW: //←
			ratio.x = (double)(oldX - x + width) / (width);
			ratio.y = 1;
			move.x = x; move.y = oldY;
			oldX = x;
			break;
		case NW: //↖
			ratio.x = (double)(oldX - x + width) / (width);
			ratio.y = (double)(oldY - y + height) / (height);
			move.x = x; move.y = y;
			oldX = x; oldY = y;
			break; 
		case NN: // ↑
			ratio.x = 1;
			ratio.y = (double)(oldY - y + height) / (height);
			move.x = oldX; move.y = y;
			oldY = y;
			break;
		}

/*-----------------------------------------------------------------------------------------------------------------*/
		
		af.setToScale(ratio.x, ratio.y); // 원점(0,0)에서부터 현재좌표까지 '거리'를 해당 비율값으로 늘려줌..
			
		if(shape instanceof GEGroup) { // 그룹이면 내부 원소변경
			GEShape temp;
			for(int i=0; i < shapelist.size(); i++) {
				temp = shapelist.get(i);
				temp.setShape(af.createTransformedShape(temp.getShape()));
			}
		}
		
		shape.setShape(af.createTransformedShape(shape.getShape())); // 자신변경
		
/*-----------------------------------------------------------------------------------------------------------------*/
		
		af.setToTranslation(move.x, move.y); // 현재 위치에서 함수안의 값만큼 좌표만큼 이동 (원점에서 현재좌표의 거리값에 함수안을 더해줌)
		
		if(shape instanceof GEGroup) { // 그룹이면 내부 원소변경
			GEShape temp;
			for(int i=0; i < shapelist.size(); i++) {
				temp = shapelist.get(i);
				temp.setShape(af.createTransformedShape(temp.getShape()));
			}
		}
		
		shape.setShape(af.createTransformedShape(shape.getShape())); // 자신변경

/*-----------------------------------------------------------------------------------------------------------------*/
		
		shape.getAnchor().resize(shape.getShape().getBounds());
	}

	@Override
	public void Transform(int x, int y) {}
}
