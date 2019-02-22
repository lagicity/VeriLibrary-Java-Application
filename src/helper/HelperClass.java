package helper;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * A helper class for common methods.
 */
public class HelperClass {
	private HelperAlerts helperAlerts = new HelperAlerts();

	/**
	 * Gets the centre location of the screen.
	 * 
	 * Used for initialising MainView to load at the centre of the screen.
	 * 
	 * @param height - the height of MainView.
	 * @param width  - the width of MainView.
	 * @return - the point of the top left corner of MainView to set its location.
	 */
	public Point getCenterScreenLocation(int height, int width) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		int x = (screenSize.width - width) / 2;
		int y = (screenSize.height - height) / 2;
		Point point = new Point(x, y);
		return point;
	}

	/**
	 * Gets the list of relevant directories for loading a JComboBox.
	 * 
	 * Used when adding files.
	 * 
	 * @param path - path to search for directories.
	 * @return - a String[] of all the directories.
	 */
	public String[] getComboBoxList(String path) {
		// We setup the path to search.
		File file = new File(path);
		File[] files = file.listFiles(File::isDirectory);

		// We initialise the length of String[] to the length of all the files.
		// This way, we prevent any OutOfBounds exceptions as the maximum length is the
		// length of all the files.
		String[] directories = new String[files.length];

		// We add the directories to the String[].
		for (int i = 0; i < files.length; i++) {
			directories[i] = files[i].getName().toString();
		}

		return directories;
	}

	/**
	 * Gets the directories at the path.
	 * 
	 * Used when we want the directories at the path.
	 * 
	 * @param path - path to search for directories.
	 * @return - a String[] of all the directories.
	 */
	public String[] getDirectories(String path) {
		// We initialise the String[].
		String[] directories = { "No Directories Found" };

		// We setup the path to search.
		File file = new File(path);
		File[] files = file.listFiles(File::isDirectory);

		// We check if there are any directories at all.
		// If there are no directories, we just return the initialised string.
		if (files.length != 0) {
			directories = new String[files.length];

			// We add the directories to the String[].
			for (int i = 0; i < files.length; i++) {
				directories[i] = files[i].getName().toString();
			}
		}
		return directories;
	}

	/**
	 * Checks if there are any directories in the path.
	 * 
	 * Used when we want to check if there are directories at the path.
	 * 
	 * @param path - path to search if there are any directories.
	 * @return - true = no directories found (doesn't exist). false = directories found (exists).
	 */
	public boolean isNoDirs(String path) {
		// We setup the path to search.
		File file = new File(path);
		File[] files = file.listFiles(File::isDirectory);

		// We check how many directories we find.
		if (files.length == 0) {
			// We find 0, so we return true.
			return true;
		} else {
			// We find some, so we return false.
			return false;
		}
	}

	/**
	 * Splits the file name into specific parts.
	 * 
	 * Used when we want a specific part of a file name.
	 * 
	 * @param name  - name the file name.
	 * @param index - index of the component we are splitting into.
	 * @param index 0 - latency
	 * @param index 1 - estimated clock
	 * @param index 2 - luts
	 * @param index 3 - file name
	 * @return - the variable we parsed.
	 */
	public String getParsedFileName(String name, int index) {
		// We split until the first 3 (4-1) underscores.
		String[] parsedText = name.split("_", 4);

		// format: latency_estclk_luts_filename
		// format: 0_1_2_3
		switch (index) {
		case 0:
			// We return the latency.
			return parsedText[0];
		case 1:
			// We return the estimated clock.
			return parsedText[1];
		case 2:
			// We return the luts.
			return parsedText[2];
		case 3:
			// We return the file name.
			return parsedText[3];
		default:
			return ("Out of bounds in getParsedFileName");
		}
	}

	/**
	 * Splits the file path into specific components.
	 * 
	 * Used when we want a specific part of a file path.
	 * 
	 * @param path  - the path.
	 * @param index - index of the component we are splitting into.
	 * @param index 0 - file name.
	 * @param index 1 - data points.
	 * @param index 2 - frequency.
	 * @param index 3 - optimisation.
	 * @param index 4 - module name.
	 * 
	 * @return - the variable we parsed.
	 */
	public String getParsedFilePath(String path, int index) {
		// We split with respect to \.
		String[] splitDir = path.split("\\\\");

		// Since what we want is at the end, we start counting from the back.
		int len = splitDir.length;

		// format: \targetDir\name\opt\freq\dp\filename
		// format: \4\3\2\1\0
		switch (index) {
		case 0:
			// We return the file name.
			return splitDir[len - 1];
		case 1:
			// We return the data points.
			return splitDir[len - 2];
		case 2:
			// We return the frequency.
			return splitDir[len - 3];
		case 3:
			// We return the optimisation.
			return splitDir[len - 4];
		case 4:
			// We return the module name.
			return splitDir[len - 5];
		default:
			return ("Out of bounds in getParsedFilePath");
		}
	}

	/**
	 * Gets the estimated top module by search all the files of the module and
	 * finding the file with the shortest name.
	 * 
	 * Used when we want the top module of a module.
	 * 
	 * @param - selDir the directory to search for the top module.
	 * @return - the estimated top module.
	 */
	public String getEstTopMod(String selDir) {
		// Setup the directory to search for the files.
		File file = new File(selDir);
		File[] files = file.listFiles(File::isFile);

		// The shortest length amongst all the files.
		int shortestLength = 0;
		// The shortest length files' index.
		int shortestLengthIndex = 0;
		// The length of the file.
		int fileLength = 0;

		// We search all files.
		for (int i = 0; i < files.length; i++) {
			// We get the length of the file name at this index.
			fileLength = files[i].getName().length();

			// We initialise the shortest length to be the length of the first file name.
			if (i == 0) {
				shortestLength = fileLength;
			}

			// If the length of the subsequent file names < the shortest length,
			if (fileLength < shortestLength) {
				// We set that file's length as the shortest length.
				shortestLength = fileLength;
				// We set the shortest file amongst all files as that file's index.
				shortestLengthIndex = i;
			}
		}

		// We need this try-catch in case we have incompatible/no files that we are
		// searching.
		try {
			return files[shortestLengthIndex].getName();
		} catch (ArrayIndexOutOfBoundsException e) {
			helperAlerts.fileGeneralIncompat(selDir);
			return "Incompatible files found.";
		}
	}

	/**
	 * Removes any "baggage" from a string, leaving just the numerical value.
	 * 
	 * Used when we just want the numerical value.
	 * 
	 * For example, when we want 52 instead of 52MHz.
	 * 
	 * @param data - the string that we want to remove "baggage" from.
	 * @return - the numerical value of the string.
	 */

	public String removeUnits(String data) {
		// We remove any integer (0-9), period (.) and backslash (\).
		return data.replaceAll("[^\\.0123456789]", "");
	}

	/**
	 * Disables any possible input JTextFields in a JFileChooser.
	 * 
	 * Used when we want to force the user to select a directory and not enter any
	 * values. This saves us the need to do any error checking.
	 * 
	 * @param comp - the components in the JFileChooser.
	 */
	public void disableDialogTextFields(Component[] comp) {
		for (int i = 0; i < comp.length; i++) {
			if (comp[i] instanceof JPanel) {
				disableDialogTextFields(((JPanel) comp[i]).getComponents());
			} else if (comp[i] instanceof JTextField) {
				((JTextField) comp[i]).setEditable(false);
			}
		}
	}
}
