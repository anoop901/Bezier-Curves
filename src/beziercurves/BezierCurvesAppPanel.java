/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beziercurves;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author anoop
 */
public class BezierCurvesAppPanel extends JPanel {
	
	private JSlider tSlider;
	private CurvePanel curvePanel;
	
	private final int sliderResolution = 100;
	
	public BezierCurvesAppPanel() {
		initComponents();
	}

	private void initComponents() {
		this.setLayout(new BorderLayout());
		
		tSlider = new JSlider(JSlider.HORIZONTAL, 0, sliderResolution, sliderResolution / 2);
		curvePanel = new CurvePanel();
		curvePanel.setT((double) tSlider.getValue() / sliderResolution);
		
		tSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent ce) {
				double t = (double) tSlider.getValue() / sliderResolution;
				curvePanel.setT(t);
			}
		});
		tSlider.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		
		this.add(curvePanel, BorderLayout.CENTER);
		this.add(tSlider, BorderLayout.PAGE_END);
	}
	
	
}
