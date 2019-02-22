package dragdrop;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JWindow;
import javax.swing.JLabel;
import ui.UIImages;

/**
 * Initialises the ghosted image when we click on a module in DialogInstMod. We
 * set up the ghosted image as a transparent window and set a JLabel to act as
 * the ghosted image. The image in the JLabel has to have transparency.
 * 
 * @see HelperInstMod
 */

public class GhostedImage extends JWindow {
	private static final long serialVersionUID = -8189042979302624753L;

	/****************
	 * UI ELEMENTS *
	 ***************/

	/*************
	 * VARIABLES *
	 *************/

	/***********
	 * HELPERS *
	 ***********/

	private UIImages uiImages = new UIImages();

	/***************
	 * CONSTRUCTOR *
	 **************/

	public GhostedImage() {
		// We set the background of the window to be transparent.
		setBackground(new Color(0, 0, 0, 0));
		getContentPane().setLayout(new BorderLayout());

		// We set up the JLabel to inflate the image on.
		JLabel cursor = new JLabel();
		add(cursor, BorderLayout.CENTER);

		// We set the image of the JLabel to be the ghosted image.
		cursor.setIcon(new ImageIcon(uiImages.getGhostedImg()));
		
		// We set the size of the image to display and show it.
		setBounds(0, 0, uiImages.getGhostedImg().getWidth(), uiImages.getGhostedImg().getHeight());
		setVisible(true);
	}

	/***********
	 * METHODS *
	 **********/

	/*************
	 * LISTENERS *
	 ************/

	/***********
	 * GET/SET *
	 **********/
}
