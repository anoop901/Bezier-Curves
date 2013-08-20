/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beziercurves;

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 *
 * @author anoop
 */
public class BezierCurve {
	
	private ArrayList<Point2D> controlPoints;
	
	public BezierCurve(ArrayList<Point2D> controlPoints) {
		if (controlPoints.size() > 0)
			this.controlPoints = controlPoints;
		else
			throw new IllegalArgumentException();
	}
	
	public int getOrder() {
		return controlPoints.size() - 1;
	}
	
	public void addPoint(int i, Point2D newPoint) {
		controlPoints.add(i, newPoint);
	}
	
	public void movePoint(int i, Point2D newPoint) {
		controlPoints.set(i, newPoint);
	}
	
	public void removePoint(int i) {
		if (controlPoints.size() > 2)
			controlPoints.remove(i);
	}
	
	public Point2D getPoint(int i) {
		return controlPoints.get(i);
	}
	
	public Point2D[][] getConstruction(double t) {
		Point2D[][] construction = new Point2D[getOrder()][];
		
		construction[0] = new Point2D[getOrder()];
		for (int j = 0; j < construction[0].length; j++) {
			Point2D a = controlPoints.get(j);
			Point2D b = controlPoints.get(j + 1);
			double newX = (1 - t) * a.getX() + t * b.getX();
			double newY = (1 - t) * a.getY() + t * b.getY();
			construction[0][j] = new Point2D.Double(newX, newY);
		}
		
		for (int i = 1; i < construction.length; i++) {
			construction[i] = new Point2D[getOrder() - i];
			for (int j = 0; j < construction[i].length; j++) {
				Point2D a = construction[i - 1][j];
				Point2D b = construction[i - 1][j + 1];
				double newX = (1 - t) * a.getX() + t * b.getX();
				double newY = (1 - t) * a.getY() + t * b.getY();
				construction[i][j] = new Point2D.Double(newX, newY);
			}
		}
		
		return construction;
	}
	
	public Point2D evaluate(double t) {
		
		ArrayList<Point2D> cps = new ArrayList<Point2D>(controlPoints);
		
		while (cps.size() > 1) {
			for (int i = 0; i < cps.size() - 1; i++) {
				Point2D a = cps.get(i);
				Point2D b = cps.get(i + 1);
				
				double newX = (1 - t) * a.getX() + t * b.getX();
				double newY = (1 - t) * a.getY() + t * b.getY();
				
				cps.set(i, new Point2D.Double(newX, newY));
			}
			cps.remove(cps.size() - 1);
		}
		
		return cps.get(0);
	}
}
