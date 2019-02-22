package helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import ui.UILoading;

/**
 * This helper class is for the Zipping of files. 
 */
public class HelperZip {
	private HelperClass helperClass = new HelperClass();
	private HelperAlerts helperAlerts = new HelperAlerts();
	private UILoading uiLoading;

	public void createZipFile(File[] fileNames, String targetPath, String selDir) {
		try {
			// where to store zip file + name
			FileOutputStream fos = new FileOutputStream(targetPath + ".zip");
			ZipOutputStream zos = new ZipOutputStream(fos);

			// find names of file to zip, reiteravely zip
			for (File file1 : fileNames) {
				addToZipFile(selDir + "\\" + file1.getName(), zos, targetPath);
				System.out.println("writing" + selDir + "\\\\" + file1.getName() + " to " + targetPath);
			}
			zos.close();
			fos.close();
			getUiLoading().success("Files Exported!");
		} catch (FileNotFoundException e) {
			uiLoading.fail();
			helperAlerts.fileNotFound();
			e.printStackTrace();
		} catch (IOException e) {
			uiLoading.fail();
			helperAlerts.fileNotWritten(targetPath);
			e.printStackTrace();
		}
	}

	private void addToZipFile(String name, ZipOutputStream zos, String targetPath) {
		try {
			File file = new File(name);
			FileInputStream fis = new FileInputStream(file);

			// parse name to put in zip file
			name = helperClass.getParsedFileName(name, 3);
			zos.putNextEntry(new ZipEntry(name));

			// read data from file, write into zip
			byte[] bytes = new byte[1024];
			int length;
			while ((length = fis.read(bytes)) >= 0) {
				zos.write(bytes, 0, length);
			}

			zos.closeEntry();
			fis.close();
		} catch (FileNotFoundException e) {
			uiLoading.fail();
			helperAlerts.fileNotFound();
			e.printStackTrace();
		} catch (IOException e) {
			uiLoading.fail();
			helperAlerts.fileNotWritten(targetPath);
			e.printStackTrace();
		}
	}

	public UILoading getUiLoading() {
		return uiLoading;
	}

	public void setUiLoading(UILoading uiLoading) {
		this.uiLoading = uiLoading;
	}
}
