package menu;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;

import dialog.FileChooserSelDir;
import help.DialogHelp;
import helper.HelperAlerts;
import helper.HelperGetSet;
import helper.HelperPrefs;
import net.miginfocom.swing.MigLayout;
import ui.UIElements;
import ui.UIImages;

/**
 * The settings dialog.
 * 
 * It contains 3 main settings: 1. To change the target directory. 2. The
 * removal of unwanted files (empty directories). 3. The removal of all files
 * (up to the target directory).
 */
public class DialogSettings extends JDialog {
	private static final long serialVersionUID = -5169984844279962501L;

	/****************
	 * UI ELEMENTS *
	 ***************/

	private JLabel labelTargetDir;

	private JButton buttonSave;

	/*************
	 * VARIABLES *
	 *************/

	/*************
	 * LISTENERS *
	 ************/

	private ActionListener buttonCancelListener = new ButtonCancelListener();
	private ActionListener buttonChangeTargetDirListener = new ButtonChangeTargetDirListener();
	private ActionListener buttonDeleteAllListener = new ButtonDeleteAllListener();
	private ActionListener buttonHelpDeleteAllListener = new ButtonHelpDeleteAllListener();
	private ActionListener buttonHelpRemUnwanFilesListener = new ButtonHelpRemUnwanFilesListener();
	private ActionListener buttonHelpTargetDirListener = new ButtonHelpTargetDirListener();
	private ActionListener buttonRemUnwanFilesListener = new ButtonRemUnwanFilesListener();
	private ActionListener buttonSaveListener = new ButtonSaveListener();

	/***********
	 * HELPERS *
	 ***********/

	private HelperGetSet helperGetSet = new HelperGetSet();
	private HelperAlerts helperAlerts = new HelperAlerts();
	private HelperPrefs helperPrefs = new HelperPrefs();
	private UIImages uiImages = new UIImages();
	private UIElements uiElements = new UIElements();

	/***************
	 * CONSTRUCTOR *
	 **************/

	public DialogSettings(Component comp, String title, ModalityType modal) {
		// Initialise the UI
		setupUI();
		initialise();

		// Initialise the dialog properties
		setTitle(title);
		setModalityType(modal);
		setLocationRelativeTo(comp);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setIconImage(uiImages.getSettingsBig());

		// Show the dialog
		pack();
		setVisible(true);
	}

	/******************
	 * INITIALISATION *
	 *****************/

	private void setupUI() {

		JPanel container = new JPanel();
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(container, BorderLayout.CENTER);
		container.setLayout(new MigLayout("", "[124.00,left][]", "[grow][][grow][49.00,grow]"));
		{
			TitledBorder title1 = BorderFactory.createTitledBorder(" Set Target Directory ");
			JPanel panelTargetDir = new JPanel();
			panelTargetDir.setBorder(title1);

			panelTargetDir.setLayout(new MigLayout("", "[][][]", "[][][]"));

			JLabel lblNewLabel = new JLabel("Target Directory: ");
			panelTargetDir.add(lblNewLabel, "cell 0 0");
			labelTargetDir = new JLabel();
			panelTargetDir.add(labelTargetDir, "cell 1 0");

			JTextPane expTargetDir = uiElements.textPaneText("Set the parent folder of your modules. "
					+ "\nThe folders of all your modules and Verilog files should be in this directory.");
			panelTargetDir.add(expTargetDir, "cell 0 1 2 1");

			JButton buttonHelpTargetDir = uiElements.buttonHelp();
			buttonHelpTargetDir.addActionListener(buttonHelpTargetDirListener);
			panelTargetDir.add(buttonHelpTargetDir, "flowx,cell 2 2");

			JButton buttonChangeTargetDir = new JButton("Change");
			panelTargetDir.add(buttonChangeTargetDir, "cell 2 2");
			buttonChangeTargetDir.setBackground(SystemColor.control);
			buttonChangeTargetDir.addActionListener(buttonChangeTargetDirListener);
			container.add(panelTargetDir, "cell 0 0 2 1,alignx left,growy");
		}
		{
			TitledBorder title2 = BorderFactory.createTitledBorder(" Remove Unwanted Files ");
			JPanel panelRemoveUnwanFiles = new JPanel();
			panelRemoveUnwanFiles.setBorder(title2);

			container.add(panelRemoveUnwanFiles, "cell 0 2 2 1,grow");
			panelRemoveUnwanFiles.setLayout(new MigLayout("", "[269px,grow][95px]", "[48px][]"));

			JTextPane expRemoveUnwanFiles = new JTextPane();
			expRemoveUnwanFiles.setBackground(SystemColor.control);
			panelRemoveUnwanFiles.add(expRemoveUnwanFiles, "cell 0 0 2 1,alignx left,aligny top");
			expRemoveUnwanFiles.setText("Remove any directories that are empty. "
					+ "\nOnly do this if you are having problems viewing your lists of modules or files.");
			expRemoveUnwanFiles.setEditable(false);

			JButton buttonHelpRemUnwanFiles = uiElements.buttonHelp();
			buttonHelpRemUnwanFiles.addActionListener(buttonHelpRemUnwanFilesListener);
			panelRemoveUnwanFiles.add(buttonHelpRemUnwanFiles, "flowx,cell 1 1");

			JButton buttonRemUnwanFiles = new JButton("Remove Files");
			panelRemoveUnwanFiles.add(buttonRemUnwanFiles, "cell 1 1,alignx right");
			buttonRemUnwanFiles.addActionListener(buttonRemUnwanFilesListener);
		}
		{
			TitledBorder title3 = BorderFactory.createTitledBorder(" Delete All Files ");
			JPanel panelDeleteAll = new JPanel();
			panelDeleteAll.setBorder(title3);

			container.add(panelDeleteAll, "cell 0 3 2 1,grow");
			panelDeleteAll.setLayout(new MigLayout("", "[][][][][][][][][][][grow]", "[][]"));

			JTextPane expDeleteAll = uiElements.textPaneText("Delete all your modules and Verilog files."
					+ "\nOnly the target directory will be not be deleted.");
			panelDeleteAll.add(expDeleteAll, "cell 0 0 11 1");

			JButton buttonHelpDeleteAll = uiElements.buttonHelp();
			buttonHelpDeleteAll.addActionListener(buttonHelpDeleteAllListener);
			panelDeleteAll.add(buttonHelpDeleteAll, "flowx,cell 10 1,alignx right");

			JButton buttonDeleteAll = new JButton("Delete All Files");
			panelDeleteAll.add(buttonDeleteAll, "cell 10 1,alignx right");
			buttonDeleteAll.addActionListener(buttonDeleteAllListener);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				buttonSave = new JButton("Save");
				buttonSave.addActionListener(buttonSaveListener);
				buttonPane.add(buttonSave);
				getRootPane().setDefaultButton(buttonSave);
			}
			{
				JButton buttonCancel = new JButton("Cancel");
				buttonCancel.addActionListener(buttonCancelListener);
				buttonCancel.setActionCommand("Cancel");
				buttonPane.add(buttonCancel);
			}
		}
	}

	/***********
	 * METHODS *
	 **********/

	/**
	 * Initialise the dialog.
	 * 
	 * The target directory is loaded.
	 */
	private void initialise() {
		labelTargetDir.setText(helperGetSet.getTargetDir());
	}

	/**
	 * Removes any unwanted files/directories.
	 * 
	 * An unwanted directory is an empty directory. Empty directories can give us a
	 * null pointer exception so we traverse all directories up till the target
	 * directory and delete all empty directories.
	 * 
	 * An unwanted file is a file that is not a compatible Verilog file or a file
	 * that is not where it should be. For example, a file in the optimisation
	 * directory. This can cause errors when parsing the directories to display on
	 * the view panels.
	 */
	private void removeUnwanFiles() {
		// We traverse all leaf directories until the target directory.
		Path end = Paths.get(helperGetSet.getTargetDir());

		try {
			Files.walkFileTree(end, new SimpleFileVisitor<Path>() {
				@Override
				// invoked on file being visited
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					// We ignore any Verilog file at the leaf directory.
					if (file.toString().endsWith(".v") || file.toString().endsWith(".tcl")) {
						return FileVisitResult.CONTINUE;
					} else {
						// We detect some random file where it shouldn't be and delete it.
						Files.delete(file);
					}
					return FileVisitResult.CONTINUE;

				}

				@Override
				// invoked when all the entries in dir are visited, going to next one
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					File file = new File(dir.toString());

					// We detect and delete any empty directories.
					if (file.isDirectory()) {
						if (file.list().length > 0) {
							System.out.println("dirs detected");
						} else {
							System.out.println("dir is empty, deleting = " + dir);
							Files.delete(dir);
						}
					}
					return FileVisitResult.CONTINUE;

				}
			});
		} catch (IOException e1) {
			helperAlerts.fileNotFound();
			e1.printStackTrace();
		}
	}

	/**
	 * Deletes all files up till the target directory.
	 * 
	 * Used when the user wants to delete all files. The target directory will not
	 * be deleted.
	 */
	private void deleteAll() {
		// We delete until the target directory.
		Path end = Paths.get(helperGetSet.getTargetDir());

		System.out.println("start path = " + end.toString());
		try {
			Files.walkFileTree(end, new SimpleFileVisitor<Path>() {
				@Override
				// invoked on file being visited
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					// We delete all files.
					Files.delete(file);
					return FileVisitResult.CONTINUE;

				}

				@Override
				// invoked when all the entries in dir are visited, going to next one
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					if (dir.toString().contentEquals(helperGetSet.getTargetDir())) {
						// We don't delete the target directory.
						return FileVisitResult.TERMINATE;
					}

					// We delete all directories.
					Files.delete(dir);
					return FileVisitResult.CONTINUE;

				}
			});
		} catch (IOException e1) {
			helperAlerts.fileNotFound();
			e1.printStackTrace();
		}
	}

	/*************
	 * LISTENERS *
	 ************/

	/**
	 * Closes the dialog.
	 */
	private class ButtonCancelListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}

	/**
	 * Saves the target directory into HelperPreferences and closes the dialog.
	 */
	private class ButtonSaveListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			helperGetSet.setTargetDir(labelTargetDir.getText());
			helperPrefs.saveTargetDirPref(labelTargetDir.getText());
			dispose();
		}
	}

	/**
	 * Prompts the user and deletes all the files (see deleteAll).
	 */
	private class ButtonDeleteAllListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (helperAlerts.ynDeleteFile() == true) {
				deleteAll();
			}
		}
	}

	/**
	 * Launches the help dialog for delete all files.
	 */
	private class ButtonHelpDeleteAllListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			DialogHelp dialogHelp = new DialogHelp();
			dialogHelp.setHelpCard(DialogHelp.getSettDelAllFiles());
		}
	}

	/**
	 * Prompts the user and deletes all unwanted files.
	 */
	private class ButtonRemUnwanFilesListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (helperAlerts.ynDeleteFile() == true) {
				removeUnwanFiles();
			}
		}
	}

	/**
	 * Launches the help dialog for removing unwanted files.
	 */
	private class ButtonHelpRemUnwanFilesListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			DialogHelp dialogHelp = new DialogHelp();
			dialogHelp.setHelpCard(DialogHelp.getSettRemUnwanFiles());
		}
	}

	/**
	 * Launches a file chooser to select the target directory.
	 */
	private class ButtonChangeTargetDirListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			FileChooserSelDir fileChooserSelDir = new FileChooserSelDir();

			if (!(fileChooserSelDir.getSelDir() == null)) {
				labelTargetDir.setText(fileChooserSelDir.getSelDir());
			}
		}
	}
	
	/**
	 * Launches the help dialog for changing the target directory.
	 */
	private class ButtonHelpTargetDirListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			DialogHelp dialogHelp = new DialogHelp();
			dialogHelp.setHelpCard(DialogHelp.getSettTargetDir());
		}
	}

	/***********
	 * GET/SET *
	 **********/
}
