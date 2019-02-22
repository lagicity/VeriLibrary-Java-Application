package dragdrop;

import java.awt.MouseInfo;
import java.awt.Point;

/**
 * Manages the position of the ghosted image and when to start and stop
 * displaying the image.
 */
public class GhostedImageCursor {

	/****************
	 * UI ELEMENTS *
	 ***************/

	/*************
	 * VARIABLES *
	 *************/

	// We get the image to display from GhostedImage.
	private GhostedImage ghostedImg;

	// We setup the position of the point (cursor).
	private Point currPoint = null;
	private Point prevPoint = null;
	private Point cursorPoint = null;

	/***********
	 * HELPERS *
	 ***********/

	/***************
	 * CONSTRUCTOR *
	 **************/

	public GhostedImageCursor() {
		// The point of where the cursor previously was.
		prevPoint = new Point(MouseInfo.getPointerInfo().getLocation());

		// The point of where the cursor currently is.
		currPoint = new Point(MouseInfo.getPointerInfo().getLocation());

		// The point of where the image is to be inflated with respect to the cursor.
		cursorPoint = new Point((int) currPoint.getX() + 1, (int) currPoint.getY() + 1);

		// Setting up the image to be displayed at the location of ghostPoint.
		ghostedImg = new GhostedImage();
		ghostedImg.setLocation(cursorPoint);
		ghostedImg.setAlwaysOnTop(true);
		ghostedImg.setVisible(true);
		ghostedImg.toFront();
	}

	/***********
	 * METHODS *
	 **********/

	/**
	 * Displays the ghosted image with respect to the cursor's position.
	 * Called when the mouse has been clicked.
	 * 
	 * @see - mouseMoving in HelperInstMod
	 */
	public void run() {
		// We get the cursor's current location.
		currPoint = MouseInfo.getPointerInfo().getLocation();
		
		// If we have moved the cursor, we update the cursor's current location.
		if ((prevPoint.getX() != currPoint.getX()) || (prevPoint.getY() != currPoint.getY())) {
			cursorPoint = new Point((int) currPoint.getX() + 1, (int) currPoint.getY() + 1);

			// We set the location of the ghosted image to be that with respect to the
			// cursor's current location.
			if (ghostedImg != null) {
				ghostedImg.setLocation(cursorPoint);
				ghostedImg.setAlwaysOnTop(true);
				ghostedImg.toFront();
			}

			prevPoint = currPoint;
		}
	}

	/**
	 * Stops the ghosted image from showing. 
	 * Called when the mouse has been released.
	 */
	public void stop() {
		if (ghostedImg != null) {
			ghostedImg.setVisible(false);
		}
	}

	/*************
	 * LISTENERS *
	 ************/

	/***********
	 * GET/SET *
	 **********/
}