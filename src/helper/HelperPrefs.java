package helper;

import java.util.prefs.Preferences;

/**
 * This helper class is for the saving of Preferences.
 */
public class HelperPrefs {

	/***********************
	 * Constant Variables *
	 ***********************/

	// Target directory preference key.
	private static final String TARGET_DIR = "targetDir";
	// Welcome preference key.
	private static final String WELCOME = "welcome";

	private Preferences prefs = Preferences.userNodeForPackage(HelperPrefs.class);

	/***********
	 * GET/SET *
	 **********/

	/**
	 * This pair saves the target directory.
	 * 
	 * Used when we run VeriLibrary and need the target directory to be loaded.
	 */
	public void saveTargetDirPref(String data) {
		prefs.put(TARGET_DIR, data);
	}

	public String getTargetDirPref() {
		return prefs.get(TARGET_DIR, "No target directory");
	}

	/**
	 * This pair saves whether we need the welcome dialog to be shown.
	 * 
	 * Used when we have launched VeriLibrary for the first time.
	 * 
	 * @param option
	 */
	public void saveWelcomePref(boolean option) {
		prefs.putBoolean(WELCOME, option);
	}

	public boolean getWelcomePref() {
		return prefs.getBoolean(WELCOME, false);
	}

}
