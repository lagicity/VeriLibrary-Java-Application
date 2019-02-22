package dialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import helper.HelperClass;

/**
 * Inflates a file chooser to select a directory to get its path.
 * 
 * @return the path of the directory that was selected.
 */

public class FileChooserSelDir extends JPanel {
	private static final long serialVersionUID = -6346282785525972562L;

	/****************
	 * UI ELEMENTS *
	 ***************/


	/*************
	 * VARIABLES *
	 *************/
	
	private String selDir;

	/***********
	 * HELPERS *
	 ***********/
	
	private HelperClass helperClass = new HelperClass();
	
	/***************
	 * CONSTRUCTOR *
	 **************/
	
	public FileChooserSelDir() {
		JFileChooser chooser = new JFileChooser();

		// Disables any form of inputs (typing of directory name, selecting file type
		// etc).
		helperClass.disableDialogTextFields(chooser.getComponents());

		// Initialises the JFileChooser to only allow us to select directories.
		chooser.setDialogTitle("Save Files To...");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		// When "Select" is clicked, it sets the selected directory as the last
		// directory that was clicked.
		if (chooser.showSaveDialog(getTopLevelAncestor()) == JFileChooser.APPROVE_OPTION) {
			setSelDir(chooser.getSelectedFile().toString());
		} else {
			// If not (dialog is closed/"Cancel" is clicked), we return with nothing.
			return;
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

	private void setSelDir(String dir) {
		selDir = dir;
	}

	public String getSelDir() {
		System.out.println("save data = " + selDir);
		return selDir;
	}
}
