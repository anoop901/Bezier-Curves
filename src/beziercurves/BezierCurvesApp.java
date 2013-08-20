/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beziercurves;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author anoop
 */
public class BezierCurvesApp extends JFrame {
	
	public BezierCurvesApp() {
		super("Bezier Curves");
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		// TODO code application logic here
		
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				BezierCurvesApp app = new BezierCurvesApp();
				BezierCurvesAppPanel panel = new BezierCurvesAppPanel();
				panel.setPreferredSize(new Dimension(800, 600));
				app.setContentPane(panel);
				app.pack();
				Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
				app.setLocation((screen.width - app.getWidth()) / 2, (screen.height - app.getHeight()) / 2);
				app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				app.setResizable(false);
				app.setVisible(true);
			}
		});
	}
}
