package ui;

import javax.swing.JPanel;
import javax.swing.Timer;

import net.miginfocom.swing.MigLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * This JPanel provides visual feedback to the user regarding their IO
 * operation.
 * 
 * It tells them whether their IO operation was successful or if there was an
 * error encountered.
 */
public class UILoading extends JPanel {
	private static final long serialVersionUID = -7379150711370930397L;

	/***************
	 * UI ELEMENTS *
	 ***************/

	private JLabel labelText;

	/*************
	 * VARIABLES *
	 *************/
	// This timer hides the visual elements after a period of time.
	private Timer timer;

	/*************
	 * LISTENERS *
	 ************/

	/***********
	 * HELPERS *
	 ***********/

	private UIImages uiImages = new UIImages();

	/***************
	 * CONSTRUCTOR *
	 **************/

	public UILoading() {
		setLayout(new MigLayout("", "[fill]", "[fill]"));

		labelText = new JLabel();
		labelText.setVisible(false);

		add(labelText, "cell 0 0");

		// The JLabel is hidden after 2000ms.
		timer = new Timer(2000, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				labelText.setVisible(false);
			}
		});
	}

	/***********
	 * METHODS *
	 **********/

	/**
	 * Initialises the JLabels.
	 * 
	 * This is called when the IO operation has just begun.
	 */
	public void initialise() {
		labelText.setIcon(new ImageIcon(uiImages.getLoading()));
		labelText.setText("Working..");
		labelText.setVisible(true);
	}

	/**
	 * Displays a successful IO operation on the JLabels.
	 * 
	 * This is called when the IO operation is successful.
	 */
	public void success(String text) {
		labelText.setIcon(new ImageIcon(uiImages.getSuccess()));
		labelText.setText(text);
		labelText.setVisible(true);
		timer.start();
	}

	/**
	 * Displays an unsuccessful IO operation on the JLabels.
	 * 
	 * This is called when the IO operation fails.
	 */
	public void fail() {
		labelText.setIcon(new ImageIcon(uiImages.getFail()));
		labelText.setText("Error!");
		timer.start();
	}

	/*************
	 * LISTENERS *
	 ************/

	/***********
	 * GET/SET *
	 **********/
}
