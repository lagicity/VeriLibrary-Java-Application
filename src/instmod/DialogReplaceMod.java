package instmod;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import helper.HelperGetSet;
import main.TabCompare;
import ui.UIImages;

/**
 * A dialog to replace a module that is dragged into the label to replace. The
 * dialog parses the module that is to be replaced and displays it in the view
 * panel. 
 * 
 * The user can compare a new module to replace and replace it.
 */
public class DialogReplaceMod extends JDialog {
	private static final long serialVersionUID = -7393178558476585679L;

	/****************
	 * UI ELEMENTS *
	 ***************/

	private TabCompare tabCompare;

	/*************
	 * VARIABLES *
	 *************/

	private boolean close = false;

	/*************
	 * LISTENERS *
	 * 
	 ************/

	private ActionListener buttonCancelListener = new ButtonCancelListener();
	private ActionListener buttonReplaceListener = new ButtonReplaceListener();

	/***********
	 * HELPERS *
	 ***********/

	private HelperGetSet helperGetSet = new HelperGetSet();

	private UIImages uiImages = new UIImages();

	/***************
	 * CONSTRUCTOR *
	 **************/

	public DialogReplaceMod(Component comp, String title, ModalityType modal) {
		createGUI();

		setTitle(title);
		setModalityType(modal);
		setLocationRelativeTo(comp);
		setIconImage(uiImages.getReplaceHover());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		pack();
		setVisible(true);
	}

	/******************
	 * INITIALISATION *
	 *****************/

	private void createGUI() {
		getContentPane().setLayout(new BorderLayout());

		// Setup the panel to compare modules.
		{
			tabCompare = new TabCompare();
			tabCompare.labelPrompt
					.setText("Replace the module by adding it to the right side and clicking on \"Replace\".");
			tabCompare.panelCompareModBasic.parseFullDir();

			tabCompare.buttonCompLHS.setEnabled(false);
			tabCompare.buttonReset.setVisible(false);
			getContentPane().add(tabCompare, BorderLayout.CENTER);
		}

		// Setup the bottom panel of buttons.
		JPanel paneButton = new JPanel();
		paneButton.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(paneButton, BorderLayout.SOUTH);

		{
			JButton buttonReplace = new JButton("Replace");
			buttonReplace.addActionListener(buttonReplaceListener);
			getRootPane().setDefaultButton(buttonReplace);
			paneButton.add(buttonReplace);

			JButton buttonCancel = new JButton("Cancel");
			buttonCancel.addActionListener(buttonCancelListener);
			paneButton.add(buttonCancel);
		}
	}

	/***********
	 * METHODS *
	 **********/

	/*************
	 * LISTENERS *
	 ************/

	private class ButtonCancelListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		};
	}

	private class ButtonReplaceListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			helperGetSet.setFullTopMod(tabCompare.panelCompareModBasic.getFullTopMod());
			helperGetSet.setSelDir(tabCompare.panelCompareModBasic.getSelDir());
			setClose(true);
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
