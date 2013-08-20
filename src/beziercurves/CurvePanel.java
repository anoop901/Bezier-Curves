/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beziercurves;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author anoop
 */
public class CurvePanel extends JPanel implements MouseListener, MouseMotionListener {
	
	private BezierCurve bezierCurve;
	private int currentControlPointIndex;
	private double t;
	
	private static final int pointRadius = 10;
	private static final double lineWidth = 10.0;
	private static final int curveResolution = 100;
	
	public CurvePanel() {
		
		ArrayList<Point2D> controlPoints = new ArrayList<Point2D>();
		controlPoints.add(new Point(100, 100));
		controlPoints.add(new Point(700, 100));
		bezierCurve = new BezierCurve(controlPoints);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		currentControlPointIndex = -1;
		
		setBackground(Color.WHITE);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setColor(Color.LIGHT_GRAY);
		g2.setStroke(new BasicStroke((float) lineWidth));
		
		for (int i = 1; i < bezierCurve.getOrder() + 1; i++) {	
			Point2D pt = bezierCurve.getPoint(i);
			Point2D prevPt = bezierCurve.getPoint(i - 1);
			g2.draw(new Line2D.Double(prevPt, pt));
		}
		
		g2.setColor(Color.GRAY);
		
		for (int i = 0; i < bezierCurve.getOrder() + 1; i++) {
			Point2D pt = bezierCurve.getPoint(i);
			Shape ptShape = new Ellipse2D.Double(pt.getX() - pointRadius, pt.getY() - pointRadius, pointRadius * 2, pointRadius * 2);
			g2.fill(ptShape);
		}
		
		GeneralPath path = new GeneralPath();
		
		Point2D pt0 = bezierCurve.evaluate(0.0);
		path.moveTo(pt0.getX(), pt0.getY());
		
		for (int i = 1; i <= curveResolution; i++) {
			double t = (double) i / curveResolution;
			Point2D pt = bezierCurve.evaluate(t);
			path.lineTo(pt.getX(), pt.getY());
		}
		
		g2.setColor(Color.RED);
		g2.setStroke(new BasicStroke(3.0f));
		g2.draw(path);
		
		Color[] colors = {Color.GREEN, Color.BLUE, new Color(255, 0, 192)};
		Point2D[][] construction = bezierCurve.getConstruction(t);
		
		g2.setStroke(new BasicStroke(1.0f));
		for (int i = 0; i < construction.length - 1; i++) {
			g2.setColor(colors[i % colors.length]);
			for (int j = 0; j < construction[i].length; j++) {
				Point2D pt = construction[i][j];
				g2.fill(new Ellipse2D.Double(pt.getX() - 3, pt.getY() - 3, 6, 6));
				if (j > 0) {
					Point2D prevPt = construction[i][j - 1];
					g2.draw(new Line2D.Double(prevPt.getX(), prevPt.getY(), pt.getX(), pt.getY()));
				}
			}
		}
		
		Point2D pt = construction[construction.length - 1][0];
		g2.setColor(Color.RED);
		g2.fill(new Ellipse2D.Double(pt.getX() - 5, pt.getY() - 5, 10, 10));
	}
	
	public void setT(double t) {
		this.t = t;
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent me) {
	}

	@Override
	public void mousePressed(MouseEvent me) {
		
		for (int i = 0; i < bezierCurve.getOrder() + 1; i++) {
			Point2D pt = bezierCurve.getPoint(i);
			if (pt.distance(me.getPoint()) < pointRadius) {
				if (me.getClickCount() == 1) {
					currentControlPointIndex = i;
				} else {
					bezierCurve.removePoint(i);
				}
				repaint();
				return;
			}
		}
		
		for (int i = 1; i < bezierCurve.getOrder() + 1; i++) {
			Point2D a = bezierCurve.getPoint(i - 1);
			Point2D b = bezierCurve.getPoint(i);
			Point2D m = me.getPoint();
			
			double crossProductABAM = (b.getX() - a.getX()) * (m.getY() - a.getY()) - (m.getX() - a.getX()) * (b.getY() - a.getY());
			double distanceAB = a.distance(b);
			
			double dotProductABAM = (b.getX() - a.getX()) * (m.getX() - a.getX()) + (b.getY() - a.getY()) * (m.getY() - a.getY());
			double dotProductABBM = (b.getX() - a.getX()) * (m.getX() - b.getX()) + (b.getY() - a.getY()) * (m.getY() - b.getY());
			
			if (Math.abs(crossProductABAM / distanceAB) < lineWidth / 2 && dotProductABAM > 0 && dotProductABBM < 0) {
				bezierCurve.addPoint(i, m);
				currentControlPointIndex = i;
				repaint();
				return;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		currentControlPointIndex = -1;
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent me) {
	}

	@Override
	public void mouseExited(MouseEvent me) {
		currentControlPointIndex = -1;
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent me) {
		if (currentControlPointIndex >= 0) {
			bezierCurve.movePoint(currentControlPointIndex, me.getPoint());
		}
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent me) {
	}
}
