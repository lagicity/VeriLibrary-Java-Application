package helper;

/**
 * A helper class for get/set methods.
 * 
 * The variables used in this class encompass the whole application.
 */
public class HelperGetSet {
	private HelperPrefs helperPrefs = new HelperPrefs();
	
	/***********************
	*  Constant Variables  *
	***********************/
	
	// Used with JTextFields which require a compulsory input.
	private static final String COMP_INPUT_PROMPT = "(*)";
	// The maximum number of integers allowed in a JTextField.
	private static final int INPUT_INT_MAX_LENGTH = 7;
	// The maximum number of characters allowed in a JTextField.
	private static final int INPUT_NAME_MAX_LENGTH = 35;

	public String getWarningName() {
		return COMP_INPUT_PROMPT;
	}

	public int getInputIntMaxLength() {
		return INPUT_INT_MAX_LENGTH;
	}

	public int getInputNameMaxLength() {
		return INPUT_NAME_MAX_LENGTH;
	}
	
	/***********************
	*  Static Variables  *
	***********************/
	
	// The selected module in the view panels' directory.
	private static String selDir;
	// The full top module (with underscores) associated with the selected module.
	private static String fullTopMod;
	// The full directory (selDir + \\ + fullTopMod) of a selected module.
	private static String fullDir;
	
	// When the cursor has moved into the remove label boundary in the instantiate module dialog.
	private static boolean instModEnteredRemove = false;
	// When the cursor has moved into the replace label boundary in the instantiate module dialog.
	private static boolean instModEnteredReplace = false;
	// When JCheckBox is checked to allow the modules to be reordered in the instantiate module dialog.
	private static boolean instModReorder = false;
	
	// When the files are first added in the Welcome dialog.
	private static boolean filesAdded = false;
	
	/***********
	 * GET/SET *
	 **********/
	
	public String getTargetDir() {
		return helperPrefs.getTargetDirPref();
	}

	public void setTargetDir(String dir) {
		System.out.println("target dir set = " + dir);
		helperPrefs.saveTargetDirPref(dir);
	}

	public void setSelDir(String dir) {
		System.out.println("setSelDir dir set = " + dir);

		selDir = dir;
	}

	public String getSelDir() {
		return selDir;
	}

	public String getFullTopMod() {
		return fullTopMod;
	}

	public void setFullTopMod(String ftm) {
		System.out.println("setFullTopMod dir set = " + ftm);

		fullTopMod = ftm;
	}

	public String getFullDir() {
		return fullDir;
	}

	public void setFullDir(String fd) {
		fullDir = fd;
	}

	public boolean isInstModEnteredRemove() {
		return instModEnteredRemove;
	}

	public boolean isInstModEnteredReplace() {
		return instModEnteredReplace;
	}

	public boolean isInstModReorder() {
		return instModReorder;
	}
	
	public void setInstModReorder(boolean imr) {
		instModReorder = imr;
	}

	public boolean isFilesAdded() {
		return filesAdded;
	}

	public void setFilesAdded(boolean fa) {
		filesAdded = fa;
	}

	// Set whenever the cursor is moved within the respective label.
	public void setEnteredLabel(String labelEntered) {
		switch (labelEntered) {
		case "instModRemove":
			instModEnteredRemove = true ;
			break;
		case "instModReplace":
			instModEnteredReplace = true;
			break;
		}
	}
	
	// Set whenever the cursor is moved out of the respective label.
	public void setExitedLabel(String labelEntered) {
		switch (labelEntered) {
		case "instModRemove":
			instModEnteredRemove = false ;
			break;
		case "instModReplace":
			instModEnteredReplace = false;
			break;
		}
	}
}
