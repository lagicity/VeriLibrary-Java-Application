package welcome;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextPane;

import dialog.FileChooserSelDir;
import helper.HelperAlerts;
import helper.HelperClass;
import helper.HelperGetSet;
import net.miginfocom.swing.MigLayout;
import ui.UIElements;

public class CardTargetDir extends JPanel {
	private static final long serialVersionUID = -2459359740545789948L;

	/***************
	 * UI ELEMENTS *
	 ***************/

	// This is public so that other classes can set the target directory.
	public JLabel labelTargetDir;

	private JButton buttonNext;

	/*************
	 * VARIABLES *
	 *************/

	/*************
	 * LISTENERS *
	 ************/

	/***********
	 * HELPERS *
	 ***********/

	private HelperGetSet helperGetSet = new HelperGetSet();
	private HelperClass helperClass = new HelperClass();
	private HelperAlerts helperAlerts = new HelperAlerts();

	private UIElements uiLabel = new UIElements();

	/***************
	 * CONSTRUCTOR *
	 **************/

	public CardTargetDir(JButton next) {
		this.buttonNext = next;

		setLayout(new MigLayout("", "[83px][321.00px][]", "[20.00][26.00px]"));

		// We set the main text showing the instructions.
		{
			JTextPane labelInst = uiLabel.textPaneText("Create a new and empty target directory."
					+ "\n\nThe target directory is the parent directory of all your files."
					+ "\nIt is highly recommended not to manually modify or delete any file unless notified by VeriLibrary."
					+ "\n\nYou can use the \"Add\" or \"Edit/Remove\" functions in VeriLibrary to help with file management.");
			add(labelInst, BorderLayout.NORTH);

			JSeparator separator = new JSeparator();
			add(separator, "cell 0 0 2 1");

			JLabel labelTargetDirPrompt = new JLabel("Target Directory:");
			add(labelTargetDirPrompt, "cell 0 1,alignx left,growy");
		}

		// We set the target directory and button to browse for selecting another target
		// directory.
		{
			labelTargetDir = new JLabel();
			add(labelTargetDir, "cell 1 1,grow");

			JButton buttonBrowse = new JButton("Browse");
			buttonBrowse.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					FileChooserSelDir fileChooserSelDir = new FileChooserSelDir();
					String selDir = fileChooserSelDir.getSelDir();
					if (helperClass.isNoDirs(selDir)) {
						helperGetSet.setTargetDir(selDir);
						labelTargetDir.setText(selDir);
						buttonNext.setEnabled(true);
					} else {
						helperAlerts.fileTargetDirIncompat();
					}
				}
			});
			add(buttonBrowse, "cell 2 1");
		}
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
