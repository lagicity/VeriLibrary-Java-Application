package menu;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import helper.HelperAlerts;
import helper.HelperClass;
import helper.HelperGetSet;
import helper.HelperIO;
import net.miginfocom.swing.MigLayout;
import ui.UIImages;
import ui.UILoading;

/**
 * The main dialog that houses the two edit and remove tabs.
 * 
 * The ok JButton has its listener added and removed according to the tab that
 * is clicked via a tab ChangeListener. This way we only need one button to deal
 * with both tabs and can reduce UI clutter.
 * 
 * @see EditRemTabEdit
 * @see EditRemTabRem
 */
public class DialogEditRem extends JDialog {
	private static final long serialVersionUID = 4342160779210962839L;

	/****************
	 * UI ELEMENTS *
	 ***************/

	public EditRemTabEdit menuEditRemoveTabEdit = new EditRemTabEdit();
	public EditRemTabRem menuEditRemoveTabRemove = new EditRemTabRem();

	private JPanel contentPanel = new JPanel();

	private JButton buttonOK;

	/*************
	 * VARIABLES *
	 *************/

	/*************
	 * LISTENERS *
	 ************/

	private ActionListener buttonEditListener = new ButtonEditListener();
	private ActionListener buttonRemoveListener = new ButtonRemoveListener();
	private ActionListener buttonCloseListener = new ButtonCloseListener();

	// The listener to see which tab we are clicking on.
	private ChangeListener tabChangeListener = new TabChangeListener();

	/***********
	 * HELPERS *
	 ***********/

	private HelperIO helperIO = new HelperIO();
	private HelperGetSet helperGetSet = new HelperGetSet();
	private HelperClass helperClass = new HelperClass();
	private HelperAlerts helperAlerts = new HelperAlerts();

	private UIImages uiImages = new UIImages();
	private UILoading uiLoading;

	/***************
	 * CONSTRUCTOR *
	 **************/

	public DialogEditRem(Component comp, String title, ModalityType modal) {
		// Initialise the UI
		setupUI();

		// Initialise the dialog properties
		setTitle(title);
		setModalityType(modal);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocation(helperClass.getCenterScreenLocation(comp.getHeight(), comp.getWidth()));
		setResizable(false);
		setIconImage(uiImages.getEditBig());

		// Show the dialog
		pack();
		setVisible(true);
	}

	/******************
	 * INITIALISATION *
	 *****************/

	private void setupUI() {

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(new MigLayout("", "[883.00px,fill]", "[38px,fill]"));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		// We setup the bottom button pane and the UILoading panel.
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);

			uiLoading = new UILoading();
			buttonPane.add(uiLoading);

			buttonOK = new JButton("Edit");
			getRootPane().setDefaultButton(buttonOK);
			buttonPane.add(buttonOK);

			JButton closeButton = new JButton("Close");
			closeButton.addActionListener(buttonCloseListener);
			buttonPane.add(closeButton);
		}

		// We setup the two tabbed panes.
		{
			JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			// The listener is to see which tab is clicked to set the respective ok button
			// text and listener.
			tabbedPane.addChangeListener(tabChangeListener);
			contentPanel.add(tabbedPane, "cell 0 0,alignx left,aligny top");

			JPanel tabContainer = new JPanel();
			tabContainer.setLayout(new BorderLayout(0, 0));
			tabContainer.setBorder(new EmptyBorder(0, 80, 0, 0));
			tabContainer.add(menuEditRemoveTabEdit, BorderLayout.CENTER);

			tabbedPane.addTab("Edit", null, tabContainer, null);
			tabbedPane.addTab("Remove", null, menuEditRemoveTabRemove, null);
		}
	}

	/***********
	 * METHODS *
	 **********/

	/**
	 * Edits the file by moving (cutting) the selected module and its contents to
	 * the new "edited" module (new path). The old modules' parent directories are
	 * then deleted if they have no child directories as they can cause null
	 * pointers when parsing them to view.
	 * 
	 * Used when the module selected is to be edited.
	 * 
	 * @param partialName - the name of the file with only the underscores (1_2_3
	 *                    only)
	 * @param origDir     - the original directory path.
	 * @param newDir      - the edited (new) directory path.
	 */
	private void editFiles(String partialName, String origDir, String newDir) {
		// We initialise the UILoading panel.
		uiLoading.initialise();
		helperIO.setUiLoading(uiLoading);

		// We get the files at the original directory to prepare to move them.
		File fileOrig = new File(origDir);
		File[] fileOrigList = fileOrig.listFiles(File::isFile);

		// We move the files to the new destination with the edited file name (from the
		// edit input pane).
		for (File file : fileOrigList) {
			String origName = file.getName();
			// Our new name consists of the original file name (cannot be edited) and the
			// edited partial name (1_2_3 part of the name). The partial name is determined
			// from the inputs in the edit pane.
			String newName = helperClass.getParsedFileName(origName, 3);
			newName = partialName + newName;

			helperIO.addMoveInitialiseIO(origName, newName, origDir, newDir);
			helperIO.fileWriteMove();
		}

		// We delete the empty directories left by moving the files. These directories
		// will cause null pointers so we have to delete them.
		menuEditRemoveTabEdit.removeEmptyDir();
	}

	/**
	 * Sorts the paths selected for deletion from lowest to highest size. This is in
	 * preparation for filtering them (see filterPaths).
	 * 
	 * Used when removing files.
	 * 
	 * @param model - the model from the list that we are going to sort.
	 * @return a List<String> of the sorted model.
	 */
	private List<String> sortDir(List<String> list) {
		// We sort the list from lowest to highest number of characters.
		Collections.sort(list, new Comparator<String>() {
			@Override
			public int compare(String a, String b) {
				return a.length() - b.length();
			}
		});

		return list;
	}

	/**
	 * We filter the list of paths by removing paths with a common parent path.
	 * 
	 * When we have deleted a parent path but still need to delete its child path,
	 * we will get a null pointer as the child path does not exist.
	 * 
	 * We filter by iterating the sorted list with the shorter paths (parent paths).
	 * If another path contains the parent path, that path is removed from the list.
	 * 
	 * @param paths - the list of sorted paths.
	 * @return the List<String> of paths with no common parent directory.
	 */
	private List<String> filterPaths(List<String> paths) {
		List<String> removePath = new ArrayList<String>();

		// We iterate the list of paths.
		for (int i = 0; i < paths.size() - 1; i++) {
			// We get the assumed parent path.
			String parentPath = paths.get(i);
			for (int j = i + 1; j < paths.size(); j++) {
				// We get the assumed child path.
				String childPath = paths.get(j);

				if (childPath.contains(parentPath)) {
					// If the child path (longer) contains the parent path (shorter), we add the
					// parent path to the remove list.
					paths.remove(j);
					paths.add(j, "CLASH");
				} 
			}
		}

		for (int i = 0; i < paths.size();i++) {
			if (!paths.get(i).contentEquals("CLASH")) {
				removePath.add(paths.get(i));
			}
		}

		return paths;
	}

	/**
	 * Removes the selected directories in the list.
	 * 
	 * We use the walkFileTree method to traverse and delete files from the leaf of
	 * the passed directory to the passed directory.
	 * 
	 * Used when we are removing directories in MenuEditRemTabRem.
	 * 
	 * @param dirs - the directories to remove.
	 */
	private void removeDir(List<String> dirs) {
		// We initialise the UILoading panel.
		uiLoading.initialise();
		helperIO.setUiLoading(uiLoading);

		// For all the directories we want to remove...
		for (int i = 0; i < dirs.size(); i++) {
			System.out.println("deleting = " + dirs.get(i));
			// We get the path of the directory and pass it in to the walkFileTree method.
			Path directory = Paths.get(dirs.get(i).toString());
			try {
				Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
					@Override
					// invoked on file being visited
					public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
						Files.delete(file);
						return FileVisitResult.CONTINUE;
					}

					@Override
					// invoked when all the entries in dir are visited, going to next one
					public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
						Files.delete(dir);
						return FileVisitResult.CONTINUE;
					}
				});

				// Deletes any leftover empty directory
				Path start = Paths.get(helperGetSet.getTargetDir());
				Files.walkFileTree(start, new SimpleFileVisitor<Path>() {

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
				uiLoading.success("Files Removed!");
			} catch (IOException e1) {
				helperAlerts.fileNotFound();
				e1.printStackTrace();
			}
		}
	}

	/*************
	 * LISTENERS *
	 ************/

	/**
	 * The listener to remove directories.
	 * 
	 * "Directories" refers to the directories selected by the user for removal.
	 * "Paths" refers to the path of the directory.
	 *
	 * It prompts the user if they really want to remove the directories and checks
	 * for an empty list.
	 * 
	 * If everything is ok, we sort the paths and remove any common path (see
	 * removeDir and filterPaths).
	 */
	private class ButtonRemoveListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (helperAlerts.ynDeleteFile()) {
				if (menuEditRemoveTabRemove.modelRemove.isEmpty()) {
					// We check if the model containing the paths is empty.
					helperAlerts.listEmpty();
					return;
				} else {
					// All is fine, we proceed to prepare the paths for removal.
					DefaultListModel<String> remove = menuEditRemoveTabRemove.modelRemove;
					List<String> removeList = new ArrayList<String>();

					// We add the paths from the DefaultListModel<String> to List<String> as the
					// method that helps us to sort accepts only List<String>.
					for (int i = 0; i < remove.size(); i++) {
						removeList.add(remove.getElementAt(i));
					}

					// We only need to sort and filter the paths if there is more than 1 directory.
					// If there is only 1 directory, we can skip.
					if (removeList.size() > 1) {
						// We sort the paths from smallest length to largest length.
						removeList = sortDir(removeList);

						// We remove any child paths that have a common parent path.
						removeList = filterPaths(removeList);
					}

					// We remove the directories.
					removeDir(removeList);

					// We refresh the panels to reflect the changes.
					menuEditRemoveTabRemove.initialise();
					menuEditRemoveTabEdit.initalise();
					menuEditRemoveTabRemove.modelRemove.clear();
				}
			}
		}
	}

	/**
	 * The listener to edit modules.
	 * 
	 * It prompts the user if they really want to edit their module and prepares the
	 * module for editing (see editFiles for the actual editing process).
	 */
	private class ButtonEditListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (helperAlerts.ynEditFile() == true) {
				// We get the original directory from whatever module the user selected,
				// and the new directory and partial name from the input fields.
				String origDir = menuEditRemoveTabEdit.panelViewModBasic.getSelDir();
				String newDir = menuEditRemoveTabEdit.getParsedTargetPath();
				String partialName = menuEditRemoveTabEdit.getPartialName();

				// We check if the directory the user edited to already exists.
				if (helperIO.isFullDirExists(newDir, partialName)) {
					// If it exists, we prompt the user if they want to overwrite it.
					if (helperAlerts.ynOverwriteFile()) {
						editFiles(partialName, origDir, newDir);
					}
				} else {
					// Else, we just edit.
					editFiles(partialName, origDir, newDir);
				}

				// We refresh the view panels to reflect the changes.
				menuEditRemoveTabRemove.initialise();
				menuEditRemoveTabEdit.initalise();
			}
		}
	}

	/**
	 * Closes the dialog.
	 */
	private class ButtonCloseListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}

	/**
	 * The listener to keep track of which tab is clicked to assign them their
	 * respective button listener.
	 */
	private class TabChangeListener implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			JTabbedPane pane = (JTabbedPane) e.getSource();
			int index = pane.getSelectedIndex();

			if (index == 0) {
				// If the "edit" tab is clicked.
				buttonOK.setText("Edit");
				buttonOK.removeActionListener(buttonRemoveListener);
				buttonOK.addActionListener(buttonEditListener);
			} else if (index == 1) {
				// If the "remove" tab is clicked.
				buttonOK.setText("Remove");
				buttonOK.removeActionListener(buttonEditListener);
				buttonOK.addActionListener(buttonRemoveListener);
			}
		}
	}

	/***********
	 * GET/SET *
	 **********/
}
