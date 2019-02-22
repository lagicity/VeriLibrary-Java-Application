package helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import ui.UILoading;

/**
 * A helper class for I/O operations.
 */
public class HelperIO {

	/*************
	 * VARIABLES *
	 *************/

	private String targetData;
	private String sourceData;

	/***********
	 * HELPERS *
	 ***********/

	private HelperGetSet helperGetSet = new HelperGetSet();
	private HelperClass helperClass = new HelperClass();
	private HelperAlerts helperAlerts = new HelperAlerts();

	private UILoading uiLoading;

	/***********
	 * METHODS *
	 **********/

	/**
	 * Removes a folder at the specified path.
	 * 
	 * Used when removing a folder.
	 * 
	 * @param path - path of the folder to be removed.
	 */
	public void removeFolder(String path) {
		// As we can only delete folders that are empty, we have to check if the
		// directory at the path is empty. We do not delete non-empty folders.
		if (helperClass.isNoDirs(path)) {
			// If it is empty, we delete the file.
			File file = new File(path);
			file.delete();
		}
	}

	/**
	 * Prepares the data to be exported by creating the target directory if it
	 * doesn't exist.
	 * 
	 * Used whenever we are exporting data (writing/exporting) to another location.
	 * 
	 * @param targetPath - the path we are writing/exporting to.
	 * @param data       - the data we are exporting.
	 */
	public void exportFileIO(String targetPath, ArrayList<String> data) {
		// We check if the target directory exists.
		if (isDirFileExists(targetPath)) {
			// If it already exists, we just write the data there.
			exportFileWrite(targetPath, data);
		} else {
			createFile(targetPath);
			exportFileWrite(targetPath, data);
		}
	}

	/**
	 * Writes the data to the file at the target path.
	 * 
	 * Used when writing files (top modules, changing the inputs/outputs of top
	 * modules)
	 * 
	 * @param targetPath - the path to write the data to.
	 * @param data       - the ArrayList<String> of the data to be written.
	 */
	private void exportFileWrite(String targetPath, ArrayList<String> data) {
		// We append our data using StringBuilder for efficiency.
		StringBuilder writeData = new StringBuilder();

		// The data passed in already has line breakers so we don't need to add them.
		for (int i = 0; i < data.size(); i++) {
			writeData.append(data.get(i));
		}

		// We write the data using a BufferedWriter for effeciency in case we are
		// writing big files.
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(targetPath));
			bw.write(writeData.toString());
			bw.close();
			getUiLoading().success("Files Exported!");
		} catch (IOException e) {
			getUiLoading().fail();
			helperAlerts.fileNotWritten(targetPath);
			e.printStackTrace();
		}
	}

	/**
	 * Prepares HelperIO for IO operations involving the moving/cutting of files.
	 * 
	 * Used when we are copying (add) or cutting (move) files from one location to
	 * another.
	 * 
	 * @param sourceFileName - the name of the file we are going to transfer.
	 * @param targetFileName - the name of the file when we have transferred. We
	 *                       might need to modify it (adding/removing underscores).
	 * @param sourcePath     - the path of the files we are going to transfer.
	 * @param targetPath     - the path of the files we are going to transfer to.
	 */
	public void addMoveInitialiseIO(String sourceFileName, String targetFileName, String sourcePath,
			String targetPath) {
		// We attach "\\" in case the source path does not end with it.
		if (!sourcePath.endsWith("\\")) {
			System.out.println("I am attaching the slashers");
			sourcePath += "\\";
		}

		// We set the source data.
		setSourceData(sourcePath + sourceFileName);
		System.out.println("reading file from " + getSourceData());

		// We set the target data.
		setTargetData(targetPath + "\\" + targetFileName);
		System.out.println("sending file to " + getTargetData());

		// If the target directory does not exist, we create it in preparation for
		// transfer.
		if (!isDirFileExists(getTargetData())) {
			createDirectory(getTargetData());
		}
	}

	/**
	 * Cuts (moves) files from one location to another.
	 * 
	 * Used when we want to move the files and not want them at their original
	 * location.
	 */
	public void fileWriteMove() {
		try {
			Files.move(Paths.get(getSourceData()), Paths.get(getTargetData()), StandardCopyOption.REPLACE_EXISTING);
			getUiLoading().success("Files Moved!");
			helperGetSet.setFilesAdded(true);
		} catch (IOException e) {
			getUiLoading().fail();
			helperAlerts.fileNotFound();
			e.printStackTrace();
		}
	}

	/**
	 * Adds (copies) files from one location to another.
	 * 
	 * Used when we want to move the files and keep them at their original location.
	 */
	public void fileWriteAdd() {
		try {
			Files.copy(Paths.get(getSourceData()), Paths.get(getTargetData()), StandardCopyOption.REPLACE_EXISTING);
			getUiLoading().success("Files Added!");
			helperGetSet.setFilesAdded(true);
		} catch (IOException e) {
			getUiLoading().fail();
			helperAlerts.fileNotFound();
			e.printStackTrace();
		}
	}

	/**
	 * Creates a file at the target path.
	 * 
	 * Used when a file needs to be created.
	 * 
	 * @param targetPath - the path to create our file at.
	 * 
	 * @throws IOException - invoked when we cannot create a file.
	 */
	private void createFile(String targetPath) {
		Path path = Paths.get(targetPath);
		try {
			Files.createFile((path));
		} catch (IOException e) {
			helperAlerts.fileNotWritten(targetPath);
			e.printStackTrace();
		}
	}

	/**
	 * Creates a directory at the target path.
	 * 
	 * Used when a directory needs to be created.
	 * 
	 * @param targetPath - the path to create our directory at.
	 * 
	 * @throws IOException - invoked when we cannot create a directory.
	 */
	public void createDirectory(String targetPath) {
		Path path = Paths.get(targetPath);
		try {
			Files.createDirectories(path);
		} catch (IOException e) {
			helperAlerts.fileNotWritten(targetPath);
			e.printStackTrace();
		}
	}

	/**
	 * Checks if a path is a directory that exists.
	 * 
	 * Used when we need to check if a path is a directory that exists.
	 * 
	 * @param path - the path to check if it is a directory that exists.
	 * 
	 * @return - whether the path is a directory that exists.
	 */
	public boolean isDirFileExists(String path) {
		File file = new File(path);
		return file.exists();
	}

	/**
	 * Checks if a full directory exists (targetDir + selDir + top module).
	 * 
	 * Used when we need to check if a full directory already exists.
	 * 
	 * @param newPath - the new directory path we are checking.
	 * @param newName - the new parsed file name we are checking (1_2_3_filename).
	 * 
	 * @return - whether the full directory exists.
	 */
	public boolean isFullDirExists(String newPath, String newName) {
		// We setup and get all the files in the new path.
		File file = new File(newPath);
		File[] fileNames = file.listFiles(File::isFile);

		// We have this try-catch in case our new path is an empty directory which will
		// invoke a null pointer exception.
		try {
			for (int i = 0; i < fileNames.length; i++) {
				// We go through each file and compare their name with the new name.
				if (fileNames[i].toString().contains(newName)) {
					// If we detect an identical directory, we return true.
					return true;
				}
			}
		} catch (NullPointerException e) {
			// If we encounter an empty directory, we return false so that the file can be
			// edited (moved) there.
			return false;
		}

		// Else, we don't detect an identical directory and return false.
		return false;
	}

	/***********
	 * GET/SET *
	 **********/

	private String getSourceData() {
		return sourceData;
	}

	private void setSourceData(String sd) {
		sourceData = sd;
	}

	private String getTargetData() {
		return targetData;
	}

	private void setTargetData(String td) {
		targetData = td;
	}

	public UILoading getUiLoading() {
		return uiLoading;
	}

	public void setUiLoading(UILoading uiLoading) {
		this.uiLoading = uiLoading;
	}
}
