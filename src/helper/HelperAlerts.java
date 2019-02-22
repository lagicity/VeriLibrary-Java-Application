package helper;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * A helper class for all the alert dialogs.
 */
public class HelperAlerts {
	private HelperGetSet helperGetSet = new HelperGetSet();

	/*************
	 * UI ALERTS *
	 *************/

	/**
	 * Called when the UI cannot be loaded.
	 */
	public void UINotLoaded() {
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(null, "Unable to load native UI! This shouldn't happen...", "Error!",
				JOptionPane.ERROR_MESSAGE);
	}

	/****************
	 * INPUT ALERTS *
	 ***************/

	/**
	 * Called when there is no input entered in a JTextField.
	 */
	public void inputNotEntered() {
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(null, "A text field is empty! " + "\nPlease fill all text fields and try again.",
				"Empty Text Field", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Called when there is no MSB/LSB input entered in a JTextField.
	 */
	public void valueMSBLSBAbsent() {
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(null,
				"You did not enter any MSB or LSB value." + "\nPlease enter a valid value and try again.",
				"MSB/LSB Absent", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Called when the MSB value < LSB value.
	 * 
	 * @param MSB - the MSB value
	 * @param LSB - the LSB value.
	 */
	public void valueMSBLSBInvalid(String MSB, String LSB) {
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(null,
				"Your MSB value (" + MSB + ")" + " is equal or less than your LSB value (" + LSB + ")."
						+ "\n\nPlease ensure your MSB value is greater than your LSB value and try again."
						+ "\nFormat: [MSB:LSB]",
				"MSB/LSB Invalid", JOptionPane.ERROR_MESSAGE);
	}

	/****************
	 * IMAGE ALERTS *
	 ***************/

	/**
	 * Called when an image is not found.
	 */
	public void imageNotFound() {
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(null, "Image not found!"
				+ "\nImage assets that VeriLibrary uses are not present. " + "Please reinstall the application.",
				"Image Not Found!", JOptionPane.ERROR_MESSAGE);
	}

	/********************
	 * DIRECTORY ALERTS *
	 *******************/

	/**
	 * Called when a directory cannot be created.
	 * 
	 * @param targetPath - the path we are creating our directory at.
	 */
	public void directoryNotCreated(String targetPath) {
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(null,
				"Your files could not be moved/added as a directory could not be created."
						+ "\n\nPlease check your directory read/write permissions." + "\nPath: " + targetPath,
				"Error!", JOptionPane.ERROR_MESSAGE);
	}

	/***************
	 * LIST ALERTS *
	 **************/

	/**
	 * Called when a list is empty and the user still attempts to remove an element.
	 */
	public void listEmpty() {
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(null,
				"The list is empty. There are no files to remove."
						+ "\nPlease add files to remove from the lists above."
						+ "\n\nRefer to Help - Errors when Editing or Removing",
				"Empty List", JOptionPane.ERROR_MESSAGE);
	}

	/***************
	 * FILE ALERTS *
	 **************/

	/**
	 * Called when a duplicate file is added to a list. Displays a list of
	 * duplicated files.
	 * 
	 * @param duplicates - a List<String> of the duplicate files.
	 */
	public void fileDuplicateList(List<String> duplicates) {
		String dup = "";

		for (int i = 0; i < duplicates.size(); i++) {
			dup += duplicates.get(i).toString() + "\n";
		}

		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(null,
				"You are adding an existing file already in the list. " + "\nThe following file(s) will not be added:"
						+ "\n\n" + dup + "\n\nRefer to Help - Errors when Editing or Removing",
				"Duplicated Files", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Called when a duplicate file is added.
	 */
	public void fileDuplicateGeneric() {
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(null,
				"You are adding an existing file/module. " + "\nPlease add another file.", "Duplicate File",
				JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Called when a module being added already exists.
	 */
	public void fileDuplicateAdd() {
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(null,
				"A module with the same parameters you are adding already exists. "
				+ "\n\nPlease check your parameters again."
						+ "\n\nNote: Click on \"Edit/Remove\" in the main menu to change the existing modules' parameters.", "Duplicate Module",
				JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Called when a directory that is passed in the viewing panels are
	 * incompatible.
	 * 
	 * @param selDir - the directory selected in the view panels.
	 */
	public void fileGeneralIncompat(String selDir) {
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(null,
				"An incompatible file or directory has been detected."
						+ "\nPlease manually delete the incompatible file or directory and restart the application."
						+ "\n\nPath: " + selDir + "\n\nRefer to Help - Target Directory Errors",
				"Incompatible File", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Called when a directory that is not empty is set for deletion.
	 * 
	 * @param path - the path of the directory to be deleted.
	 */
	public void fileNotEmpty(String path) {
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(null,
				"A directory that is not empty has been detected."
						+ "\nPlease manually delete the files in the directory and try again." + "\n\nPath: " + path,
				"Error!", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Called when a target directory that is selected is not compatible.
	 */
	public void fileTargetDirIncompat() {
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(null,
				"The target directory you selected is not empty." + "\nPlease create or select an empty directory.",
				"Error!", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Called when an incompatible Verilog file is added.
	 * 
	 * @param fileName - the name of the incompatible file.
	 */
	public void fileVerilogIncompat(String fileName) {
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(null,
				"An incompatible Verilog file been detected."
						+ "\n\nVeriLibrary only supports \".v\" and \".tcl\" files."
						+ "\nPlease omit the file and add your files again." + "\n\nFile: " + fileName,
				"Incompatible Verilog File", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Called when an invalid directory or file has been parsed and displayed in the
	 * view panels.
	 */
	public void fileInvalid() {
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(null,
				"An invalid file or directory has been detected."
						+ "\n\nPlease use the Settings dialog to change your target directory to"
						+ "\nan appropriate directory and restart the application." + "\n\nAffected path: "
						+ helperGetSet.getTargetDir() + "\n\nRefer to Help - Target Directory Errors",
				"Error!", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Called when no files are selected when attempting to add files.
	 */
	public void fileNotSelected() {
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(null,
				"No files selected! " + "\nPlease click on \"Select Verilog Files\" to begin adding.",
				"File Not Selected", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Called when a file that is trying to be exported cannot be due to
	 * permissions.
	 * 
	 * @param targetPath - the path we are saving our file to.
	 */
	public void fileNotWritten(String targetPath) {
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(null, "The file could not be written. "
				+ "\n\nPlease check your file read/write permissions." + "\nPath: " + targetPath, "Error!",
				JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Called when a file that is trying to be exported cannot be found.
	 */
	public void fileNotFound() {
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(null,
				"File(s) could not be found." + "\n\nPlease ensure the file(s) exists."
						+ "\nPlease ensure the file names are consistent.",
				"File Not Found", JOptionPane.ERROR_MESSAGE);
	}

	/***************
	 * WIRE ALERTS *
	 **************/

	/**
	 * Called when the last remaining wire is trying to be removed.
	 */
	public void wireNotEnough() {
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(null,
				"You have one wire left in this module. " + "\nAs a precaution, you will not be able to remove it."
						+ "\n\nRefer to Help - Errors when Configuring",
				"1 Wire Remaining", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Called when a top module is parsed and no inputs/outputs are found.
	 * 
	 * @param topMod - the top module we parsed for inputs/outputs.
	 */
	public void wireNotFound(String topMod) {
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(null,
				"No input(s) and/or output(s) were found in a top module you have selected."
						+ "\nPlease ensure you have the correct top module and try again." + "\n\nTop Module: " + topMod
						+ "\n\nRefer to Help - Errors when Configuring",
				"No Inputs or Outputs Found", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Called when there is a mismatch between the inputs and outputs of two
	 * connected modules.
	 * 
	 * @param mismatch - the ArrayList<String> of panels with mismatched wires.
	 */
	public void wireMismatch(ArrayList<String> mismatch) {
		StringBuilder wires = new StringBuilder();

		for (int i = 0; i < mismatch.size(); i++) {
			wires.append(mismatch.get(i) + "\n");
		}

		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(null,
				"A mismatch in the number of inputs and outputs between two modules has been detected. "
						+ "\nExporting this top module may cause errors during simulation."
						+ "\nPlease edit the respective panels to ensure the inputs and outputs match."
						+ "\n\nMismatched wires:" + "\n" + wires + "\n\nRefer to Help - Errors when Configuring",
				"Wire Mismatch!", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Called when the reserved words "input" and "output" are attempted to be added
	 * as wire names.
	 */
	public void wireInvalidName() {
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(null,
				"\"input\" and \"output\" are reserved words. " + "\nYou cannot add them as a variable name."
						+ "\n\nPlease change the name and try again." + "\n\nRefer to Help - Errors when Configuring",
				"Invalid Wire Name", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Called when another added/edited wire is the same as a wire in a list.
	 * 
	 * @param wire - the wire that is already in the list.
	 */
	public void wireDuplicate(String wire) {
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(
				null, "The wire is already in the list." + "\nPlease change the name and try again." + "\n\nWire: "
						+ wire + "\n\nRefer to Help - Errors when Configuring",
				"Duplicate Wire", JOptionPane.ERROR_MESSAGE);
	}

	/**************
	 * Y/N ALERTS *
	 *************/

	/**
	 * Called when a list is prompted to be reset to its original wires.
	 * 
	 * @return - whether the user continues or not.
	 */
	public boolean ynResetFile() {
		String[] options = { "Yes", "No" };
		Toolkit.getDefaultToolkit().beep();
		int value = JOptionPane.showOptionDialog(null,
				"You are about to reset this modules' wires to its original wires.  "
						+ "\nAll edited data will be lost. " + "\n\nAre you sure you want to continue?",
				"Warning!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);

		if (value == JOptionPane.YES_OPTION) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Called when an output is attempted to be added to an input list.
	 * 
	 * @return - whether the user continues or not.
	 */
	public boolean ynWireInvalidTypeOutput() {
		String[] options = { "Yes", "No" };
		Toolkit.getDefaultToolkit().beep();
		int value = JOptionPane.showOptionDialog(null,
				"You are attempting to add an output to a modules' input. " + "\n\nAre you sure you want to continue?",
				"Invalid Wire Type", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);

		if (value == JOptionPane.YES_OPTION) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Called when an input is attempted to be added to an output list.
	 * 
	 * @return - whether the user continues or not.
	 */
	public boolean ynWireInvalidTypeInput() {
		String[] options = { "Yes", "No" };
		Toolkit.getDefaultToolkit().beep();
		int value = JOptionPane.showOptionDialog(null,
				"You are attempting to add an input to a modules' output." + "\n\nAre you sure you want to continue?",
				"Invalid Wire Type", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);

		if (value == JOptionPane.YES_OPTION) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Called when a file is attempted to be overwritten.
	 * 
	 * @return - whether the user continues or not.
	 */
	public boolean ynOverwriteFile() {
		String[] options = { "Yes", "No" };
		Toolkit.getDefaultToolkit().beep();
		int value = JOptionPane.showOptionDialog(null,
				"The files already exist. " + "\n\nAre you sure you want to continue?", "Attempting to Overwrite",
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);

		if (value == JOptionPane.YES_OPTION) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Called when a file is attempted to be edited in MenuEditRemoveFiles.
	 * 
	 * @return - whether the user continues or not.
	 */
	public boolean ynEditFile() {
		String[] options = { "Yes", "No" };
		Toolkit.getDefaultToolkit().beep();
		int value = JOptionPane.showOptionDialog(null,
				"You are about to edit files. " + "\nThis action is unreversible. "
						+ "\n\nAre you sure you want to continue?",
				"Warning!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);

		if (value == JOptionPane.YES_OPTION) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Called when a file is attempted to be deleted in MenuEditRemoveFiles.
	 * 
	 * @return - whether the user continues or not.
	 */
	public boolean ynDeleteFile() {
		String[] options = { "Yes", "No" };
		Toolkit.getDefaultToolkit().beep();
		int value = JOptionPane.showOptionDialog(null,
				"You are about to remove files. " + "\nThis action is unreversible. "
						+ "\n\nAre you sure you want to continue?",
				"Warning!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);

		if (value == JOptionPane.YES_OPTION) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Called when a dialog is closed via the "Cancel" button.
	 * 
	 * @return - whether the user continues or not.
	 */
	public boolean ynCancel() {
		String[] options = { "Yes", "No" };
		Toolkit.getDefaultToolkit().beep();
		int value = JOptionPane.showOptionDialog(null,
				"All data will be lost if the dialog is closed." + "\n\n Are you sure you want to continue?",
				"Warning!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);

		if (value == JOptionPane.YES_OPTION) {
			return true;
		} else {
			return false;
		}
	}
}
