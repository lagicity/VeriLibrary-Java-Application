package export;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import dialog.FileChooserSelDir;
import help.DialogHelp;
import helper.HelperAlerts;
import helper.HelperClass;
import helper.HelperFields;
import helper.HelperGetSet;
import helper.HelperIO;
import net.miginfocom.swing.MigLayout;
import panels.PanelViewModBasic;
import ui.UIElements;
import ui.UIImages;
import ui.UILoading;

/**
 * Exports modules and their associated files to a specified location.
 * 
 * The user selects the module to export via the lists at the top and adds them
 * to the lists at the bottom. The parameters are split automatically and added
 * to the list. When the "Export" button is clicked, the lists at the bottom are
 * scanned for their elements and the source directories to grab the files are
 * generated and stored in an ArrayList for export.
 */
public class DialogExport extends JDialog {
	private static final long serialVersionUID = 1127694253751362421L;

	/****************
	 * UI ELEMENTS *
	 ***************/

	// The four PanelLists for the four respective lists.
	private PanelList listName;
	private PanelList listOpt;
	private PanelList listFreq;
	private PanelList listDataPoints;

	private JButton buttonAdd;
	private JButton buttonCancel;
	private JButton buttonExport;
	private JButton buttonHelp;
	private JButton buttonRemove;
	private JButton buttonReset;

	private JPanel panelDirName;

	// The main viewing panel.
	private PanelViewModBasic panelViewModBasic = new PanelViewModBasic();

	private JTextField inputDirName;
	private JCheckBox checkBoxSaveInDir;

	private JLabel labelTotal;

	/*************
	 * VARIABLES *
	 *************/

	// Contains the directories of those in the 4 lists.
	private ArrayList<String> exportDir = new ArrayList<String>();

	// The index that is selected in either of the 4 lists.
	private int selIndex;

	/*************
	 * LISTENERS *
	 ************/

	private ActionListener buttonAddListener = new ButtonAddListener();
	private ActionListener buttonCancelListener = new ButtonCancelListener();
	private ActionListener buttonExportListener = new ButtonExportListener();
	private ActionListener buttonHelpListener = new ButtonHelpListener();
	private ActionListener buttonRemoveListener = new ButtonRemoveListener();
	private ActionListener buttonResetListener = new ButtonResetListener();
	private ActionListener checkBoxSaveInDirListener = new CheckBoxSaveInDirListener();
	private ListSelectionListener listListener = new ListListener();

	/***********
	 * HELPERS *
	 ***********/

	private HelperIO helperIO = new HelperIO();
	private HelperGetSet helperGetSet = new HelperGetSet();
	private HelperFields helperFields = new HelperFields();
	private HelperClass helperClass = new HelperClass();
	private HelperAlerts helperAlerts = new HelperAlerts();

	private UIImages uiImages = new UIImages();
	private UILoading uiLoading = new UILoading();
	private UIElements uiElements = new UIElements();

	/***************
	 * CONSTRUCTOR *
	 **************/

	public DialogExport(Component comp, String title, ModalityType modal) {
		// Initialise the UI
		createGUI();
		addRow();

		// Initialise the dialog properties
		setTitle(title);
		setResizable(false);
		setModalityType(modal);
		setLocationRelativeTo(comp);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setIconImage(uiImages.getExportSmall());

		// Show the dialog
		pack();
		setVisible(true);
	}

	/******************
	 * INITIALISATION *
	 *****************/

	private void createGUI() {

		JPanel container = new JPanel();
		container.setLayout(new MigLayout("", "[grow][200px:n:200px][200px:n:200px][201.00px:n:150px][150px:n:150px][]",
				"[grow][][][][][::300px,grow][][grow][grow]"));
		getContentPane().add(container, BorderLayout.SOUTH);

		// Setup the top lists that shows the directories.
		{
			JPanel panelView = new JPanel();
			panelView.setLayout(new BorderLayout(0, 0));
			panelViewModBasic = new PanelViewModBasic();
			panelView.add(panelViewModBasic, BorderLayout.CENTER);
			container.add(panelView, "cell 0 0 6 1,grow");
		}

		// Setup the labels of the bottom lists
		{
			JLabel labelName = new JLabel("Module Name");
			JLabel labelOpt = new JLabel("Optimisation");
			JLabel labelFreq = new JLabel("Frequency");
			JLabel labelDataPoints = new JLabel("Data Points");

			JLabel labelPrompt = new JLabel("List of Modules to Export");
			container.add(labelPrompt, "cell 1 2 4 1,alignx center");

			container.add(labelName, "cell 1 3,alignx center");
			container.add(labelOpt, "cell 2 3,alignx center");
			container.add(labelFreq, "cell 3 3,alignx center");
			container.add(labelDataPoints, "cell 4 3,alignx center");
		}

		// Setup the buttons that are used with the bottom lists.
		{
			buttonRemove = new JButton("Remove");
			// We set it to false first because the list is initialised to show only one
			// element
			// and we don't want to be able to remove that.
			buttonRemove.setEnabled(false);
			buttonRemove.addActionListener(buttonRemoveListener);
			container.add(buttonRemove, "cell 5 5,alignx left,aligny bottom");

			buttonAdd = new JButton("Add to List");
			buttonAdd.addActionListener(buttonAddListener);
			container.add(buttonAdd, "cell 5 4");

			buttonHelp = uiElements.buttonHelp();
			buttonHelp.addActionListener(buttonHelpListener);
			container.add(buttonHelp, "cell 5 3");

			buttonReset = new JButton("Reset");
			buttonReset.addActionListener(buttonResetListener);
			container.add(buttonReset, "cell 0 5,alignx right,aligny bottom");
		}

		// Setup the bottom lists.
		{
			JPanel panelName = new JPanel();
			JPanel panelOpt = new JPanel();
			JPanel panelFreq = new JPanel();
			JPanel panelDataPoints = new JPanel();

			panelName.setLayout(new BorderLayout(0, 0));
			panelOpt.setLayout(new BorderLayout(0, 0));
			panelFreq.setLayout(new BorderLayout(0, 0));
			panelDataPoints.setLayout(new BorderLayout(0, 0));

			listName = new PanelList();
			listOpt = new PanelList();
			listFreq = new PanelList();
			listDataPoints = new PanelList();

			listName.list.addListSelectionListener(listListener);
			listOpt.list.addListSelectionListener(listListener);
			listFreq.list.addListSelectionListener(listListener);
			listDataPoints.list.addListSelectionListener(listListener);

			panelName.add(listName);
			panelOpt.add(listOpt);
			panelFreq.add(listFreq);
			panelDataPoints.add(listDataPoints);

			container.add(panelName, "cell 1 4 1 2,grow");
			container.add(panelOpt, "cell 2 4 1 2,grow");
			container.add(panelFreq, "cell 3 4 1 2,grow");
			container.add(panelDataPoints, "cell 4 4 1 2,grow");

			// Shows the total number of elements in the bottom lists.
			labelTotal = new JLabel("total");
			container.add(labelTotal, "cell 1 7");
		}

		// Setup the JTextField to input the directory name (if the checkbox is
		// checked).
		{
			panelDirName = new JPanel();
			panelDirName.setLayout(new MigLayout("", "[78px][86px,grow,fill]", "[20px]"));
			// We set this as false first because the JCheckBox is default to unchecked.
			panelDirName.setVisible(false);
			container.add(panelDirName, "cell 3 7 2 1,grow");

			// Error label when an invalid input is entered.
			JLabel labelError = helperFields.createErrorLabel();
			container.add(labelError, "cell 3 5");

			JLabel labelDirName = new JLabel("Directory Name:");
			panelDirName.add(labelDirName, "cell 0 0,alignx left,aligny center");

			inputDirName = helperFields.createTextField(helperGetSet.getInputNameMaxLength(), labelError, 1);
			panelDirName.add(inputDirName, "cell 1 0,alignx left,aligny top");

			checkBoxSaveInDir = new JCheckBox("Export in a Directory");
			checkBoxSaveInDir.setToolTipText("Check to create a new directory to export the files in.");
			checkBoxSaveInDir.addActionListener(checkBoxSaveInDirListener);
			container.add(checkBoxSaveInDir, "cell 5 7");
		}

		// Setup the panel at the bottom to export the files.
		{
			JPanel panelExport = new JPanel();
			panelExport.setLayout(new MigLayout("", "[-47.00,grow][65px,grow][65px]", "[23px,grow]"));

			// The panel to show the status of exporting the files.
			container.add(uiLoading, "flowx,cell 3 8");

			buttonExport = new JButton("Export");
			buttonExport.setIcon(new ImageIcon(uiImages.getExportSmall()));
			buttonExport.addActionListener(buttonExportListener);
			panelExport.add(buttonExport, "cell 1 0");

			buttonCancel = new JButton("Cancel");
			buttonCancel.addActionListener(buttonCancelListener);
			panelExport.add(buttonCancel, "cell 2 0,alignx left,aligny top");
			container.add(panelExport, "cell 0 8 6 1,alignx right,growy");
		}
	}

	/***********
	 * METHODS *
	 **********/

	/**
	 * Exports the files by going through each full directory added into the
	 * ArrayList.
	 */
	private void exportFiles() {
		// We inflate the file chooser for the user to select where to export the files
		// to.
		FileChooserSelDir fileChooserSelDir = new FileChooserSelDir();

		// We initialise the UILoading panel to give us the status of the export.
		helperIO.setUiLoading(uiLoading);
		uiLoading.initialise();

		// Initialising some String variables.
		// They are outside the for loop because they have to be updated for each new
		// directory.
		String name;
		String opt;
		String freq;
		String dataPoints;
		// The default directory name that contains all the modules' files.
		String defaultDirName;
		String targetPath;

		for (int i = 0; i < exportDir.size(); i++) {
			// We get the individual components of the full path to make the default
			// directory name.
			name = helperClass.getParsedFilePath(exportDir.get(i), 3);
			opt = helperClass.getParsedFilePath(exportDir.get(i), 2);
			freq = helperClass.getParsedFilePath(exportDir.get(i), 1);
			dataPoints = helperClass.getParsedFilePath(exportDir.get(i), 0);

			// We create the default directory name.
			defaultDirName = name + "_" + opt + "_" + freq + "_" + dataPoints;

			// We check if the user wants to add the modules' files into another directory.
			if (checkBoxSaveInDir.isSelected()) {
				targetPath = fileChooserSelDir.getSelDir() + "\\" + inputDirName.getText() + "\\" + defaultDirName;
			} else {
				targetPath = fileChooserSelDir.getSelDir() + "\\" + defaultDirName;
			}

			// Our source paths are from the ArrayList.
			// The source path contains the path to all the modules' files.
			String sourcePath = exportDir.get(i);

			// We get all the files from the source path.
			File sourceFile = new File(sourcePath);
			File[] sourceFileNames = sourceFile.listFiles(File::isFile);

			// We transfer each file from the source to the target.
			for (File file : sourceFileNames) {

				// We get the name of the file.
				String sourceFileName = file.getName();
				// We have to parse the name to remove the underscores created when adding the
				// files.
				// 1_2_3_filename -> filename
				String targetFileName = helperClass.getParsedFileName(sourceFileName, 3);

				// We move the files to the target path.
				helperIO.addMoveInitialiseIO(sourceFileName, targetFileName, sourcePath, targetPath);
				helperIO.fileWriteAdd();
			}
		}
	}

	/**
	 * Checks for any duplicates between the top and bottom lists.
	 * 
	 * Any module that is added to the list has its full directory added to the
	 * ArrayList exportDir. This method checks the full directory of the selected
	 * module (from getExportDirPath()) against the modules in the bottom list (in
	 * exportDir).
	 * 
	 * @return - whether there is a duplicate when adding to the bottom list.
	 */
	private boolean isDuplicate() {
		for (int i = 0; i < exportDir.size(); i++) {
			if (exportDir.get(i).contentEquals(getExportDirPath())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Add a row to all the four lists. The full directory of the module selected is
	 * parsed and broken down and added to its respective list.
	 * 
	 * At the same time, it updates exportDir and the label showing the total number
	 * of elements in the list.
	 */
	public void addRow() {
		// We get the full directory of the module selected.
		String initDir = panelViewModBasic.getSelDir();

		// We parse the directory into its respective components.
		String name = helperClass.getParsedFilePath(initDir, 3);
		String opt = helperClass.getParsedFilePath(initDir, 2);
		String freq = helperClass.getParsedFilePath(initDir, 1);
		String dataPoints = helperClass.getParsedFilePath(initDir, 0);

		// We add the parsed components into the respective list.
		listName.add(name);
		listOpt.add(opt);
		listFreq.add(freq);
		listDataPoints.add(dataPoints);

		// We update with the added module.
		exportDir.add(getExportDirPath());
		labelTotal.setText("Total Files: " + listName.getModel().size());
	}

	/**
	 * Removes a row in all the four lists. The row removed is the selected index in
	 * any list.
	 * 
	 * At the same time, it updates exportDir and the label showing the total number
	 * of elements in the list.
	 */
	private void removeRow() {
		listName.remove(selIndex);
		listOpt.remove(selIndex);
		listFreq.remove(selIndex);
		listDataPoints.remove(selIndex);

		// We update with the removed module.
		exportDir.remove(selIndex);
		labelTotal.setText("Total Files: " + listName.getModel().size());
	}

	/**
	 * Sets the selected index of all the four lists to whatever index was selected
	 * in any list. This makes all the four lists have the same index regardless of
	 * which list was clicked.
	 */
	private void setRow() {
		listName.setListIndex(selIndex);
		listOpt.setListIndex(selIndex);
		listFreq.setListIndex(selIndex);
		listDataPoints.setListIndex(selIndex);
	}

	/*************
	 * LISTENERS *
	 ************/

	/**
	 * Controls the display of the panel to input the directory name.
	 */
	private class CheckBoxSaveInDirListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (checkBoxSaveInDir.isSelected()) {
				panelDirName.setVisible(true);
			} else {
				panelDirName.setVisible(false);
			}
		};
	}

	/**
	 * Gets the index that was selected. This index is used to set the index of all
	 * the four lists.
	 */
	private class ListListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			selIndex = listName.list.getSelectedIndex();
			selIndex = listOpt.list.getSelectedIndex();
			selIndex = listFreq.list.getSelectedIndex();
			selIndex = listDataPoints.list.getSelectedIndex();

			setRow();
		}
	}

	/**
	 * Launches the Help dialog.
	 */
	private class ButtonHelpListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			DialogHelp dialogHelp = new DialogHelp();
			dialogHelp.setHelpCard(DialogHelp.getExpModHow());

		};
	}

	/**
	 * Closes the dialog.
	 */
	private class ButtonCancelListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (helperAlerts.ynCancel()) {
				dispose();
			}
		};
	}

	/**
	 * Resets the elements in the bottom list to whatever is selected in the top
	 * list.
	 */
	private class ButtonResetListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			listName.clear();
			listOpt.clear();
			listFreq.clear();
			listDataPoints.clear();
			exportDir.clear();

			addRow();
		};
	}

	/**
	 *  Begins the process of exporting the files.
	 */
	private class ButtonExportListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (panelDirName.isVisible()) {
				if (inputDirName.getText().isEmpty()) {
					helperAlerts.inputNotEntered();
					return;
				}
			}
			exportFiles();
		};
	}

	/**
	 *  Adds the selected module in the top list to the bottom list.
	 */
	private class ButtonAddListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			// Checks for any duplicates between the top and bottom lists.
			if (isDuplicate()) {
				helperAlerts.fileDuplicateGeneric();
			} else {
				addRow();
				buttonRemove.setEnabled(true);
			}
		};
	}

	/**
	 *  Removes the selected row in the bottom list.
	 */
	private class ButtonRemoveListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			// We disable the button if there is only 1 element left so the last element
			// cannot be removed.
			if (listName.getModel().size() == 1) {
				buttonRemove.setEnabled(false);
			} else {
				removeRow();
			}
		};
	}

	/***********
	 * GET/SET *
	 **********/

	/**
	 * @return - the full directory of the module selected in the top list.
	 */
	private String getExportDirPath() {
		return panelViewModBasic.getSelDir();
	}
}
