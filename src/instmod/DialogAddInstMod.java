package instmod;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import helper.HelperClass;
import net.miginfocom.swing.MigLayout;
import panels.PanelViewMod;
import ui.UIImages;

/**
 * A dialog to add modules to the list. The user selects a module via the view
 * panels and adds it to the list.
 */
public class DialogAddInstMod extends JDialog {
	private static final long serialVersionUID = -4534095809024674237L;

	/****************
	 * UI ELEMENTS *
	 ***************/

	private JPanel container = new JPanel();

	// The view panel.
	private PanelViewMod panelViewMod;

	// We are public because the work we need to do is in DIalogInstMod.
	public JButton buttonAddMod;

	/*************
	 * VARIABLES *
	 *************/

	private boolean close = false;

	/*************
	 * LISTENERS *
	 ************/

	private ActionListener buttonAddListener = new ButtonAddListener();
	private ActionListener buttonCancelListener = new ButtonCancelListener();

	/***********
	 * HELPERS *
	 ***********/

	private HelperClass helperClass = new HelperClass();
	
	private UIImages uiImages = new UIImages();

	/***************
	 * CONSTRUCTOR *
	 **************/

	public DialogAddInstMod(Component comp, String title, ModalityType modal) {
		// Initialise the UI
		createGUI();

		// Initialise the dialog properties
		setTitle(title);
		setModalityType(modal);
		setLocation(helperClass.getCenterScreenLocation(comp.getHeight(), comp.getWidth()));
		setIconImage(uiImages.getAddSmall());

		// Show the dialog
		pack();
		setVisible(true);
	}

	/******************
	 * INITIALISATION *
	 *****************/

	private void createGUI() {
		container.setLayout(new MigLayout("", "[grow,fill]", "[][147.00]"));
		getContentPane().add(container, BorderLayout.CENTER);

		// Setup the title and the view panel.
		{
			panelViewMod = new PanelViewMod();
			panelViewMod.setBorder(new TitledBorder(BorderFactory.createTitledBorder(" Select A Module ")));
			container.add(panelViewMod, "cell 0 1,grow");
		}

		// Setup the panel of buttons at the bottom.
		{
			JPanel panelButton = new JPanel();
			panelButton.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(panelButton, BorderLayout.SOUTH);

			buttonAddMod = new JButton("Add");
			buttonAddMod.addActionListener(buttonAddListener);
			panelButton.add(buttonAddMod);
			getRootPane().setDefaultButton(buttonAddMod);

			JButton buttonCancel = new JButton("Cancel");
			buttonCancel.addActionListener(buttonCancelListener);
			panelButton.add(buttonCancel);
		}
	}

	/***********
	 * METHODS *
	 **********/

	/*************
	 * LISTENERS *
	 ************/

	/**
	 * Closes the dialog. The actual listener is in DialogInstMod.
	 */
	private class ButtonAddListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			setClose(true);
			dispose();
		};
	}

	/**
	 * Closes the dialog.
	 */
	private class ButtonCancelListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		};
	}

	/***********
	 * GET/SET *
	 **********/

	public boolean isClose() {
		return close;
	}

	public void setClose(boolean close) {
		this.close = close;
	}

}
