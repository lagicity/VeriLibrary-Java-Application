package menu;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import help.DialogHelp;
import helper.HelperAlerts;
import helper.HelperClass;
import helper.HelperFields;
import helper.HelperGetSet;
import helper.HelperIO;
import panels.PanelSaveFiles;
import panels.PanelSelFiles;
import ui.UIElements;
import ui.UIImages;
import ui.UILoading;

public class DialogAdd extends JDialog {
	private static final long serialVersionUID = -8962258185810464185L;

	/****************
	 * UI ELEMENTS *
	 ***************/

	private JComboBox<Object> comboBoxOpt;
	private JComboBox<Object> comboBoxName;

	private JCheckBox checkBoxAddNewName;
	private JCheckBox checkBoxAddNewOpt;

	private JLabel errorName;
	private JLabel errorOpt;

	private JButton buttonAddFiles;
	private JButton buttonMoveFiles;

	private JTextField inputAddNewName;
	private JTextField inputAddNewOpt;

	private JTextField inputFreq;
	private JTextField inputLatency;
	private JTextField inputEstClk;
	private JTextField inputLuts;
	private JTextField inputDataPoints;

	private PanelSelFiles panelSelFiles;
	private PanelSaveFiles panelSaveFiles;

	/*************
	 * VARIABLES *
	 *************/

	// Whether there is a null pointer (no directory found) when loading the
	// JComboBoxes. This can occur when we launch VeriLibrary for the first time and
	// the target directory has no sub directories.
	private boolean targetDirNull = false;

	private ArrayList<String> fileNames = new ArrayList<String>();

	/*************
	 * LISTENERS *
	 ************/

	private ItemListener comboBoxNameListener = new ComboBoxNameListener();

	private ActionListener checkBoxAddNewNameListener = new CheckBoxAddNewNameListener();
	private ActionListener checkBoxAddNewOptListener = new CheckBoxAddNewOptListener();

	private ActionListener buttonHelpListener = new ButtonHelpListener();
	private ActionListener buttonAddFilesListener = new ButtonAddFilesListener();
	private ActionListener buttonMoveFilesListener = new ButtonMoveFilesListener();
	private ActionListener buttonFinishedListener = new ButtonFinishedListener();

	/***********
	 * HELPERS *
	 ***********/

	private HelperIO helperIO = new HelperIO();
	private HelperGetSet helperGetSet = new HelperGetSet();
	private HelperFields helperFields = new HelperFields();
	private HelperAlerts helperAlerts = new HelperAlerts();
	private HelperClass helperClass = new HelperClass();

	private UILoading uiLoading = new UILoading();
	private UIImages uiImages = new UIImages();
	private UIElements uiElements = new UIElements();

	/***************
	 * CONSTRUCTOR *
	 **************/

	public DialogAdd(Component comp, String title, ModalityType modal) {
		// Initialise the UI
		setupUI();

		// Initialise the dialog properties
		setTitle(title);
		setModalityType(modal);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setIconImage(uiImages.getAddSmall());
		setLocationRelativeTo(comp);
		setResizable(false);

		// Show the dialog
		pack();
		setVisible(true);
	}

	/******************
	 * INITIALISATION *
	 *****************/

	private void setupUI() {
		// We setup our container JPanel.
		JPanel container = new JPanel();
		container.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(container, BorderLayout.NORTH);

		container.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("150px"),
				FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("max(14dlu;pref)"),
				FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("150px"),
				FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("fill:max(13dlu;default)"),
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				RowSpec.decode("8px"),
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				RowSpec.decode("fill:20px"),
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				RowSpec.decode("30px"),
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("226px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				RowSpec.decode("37px"),
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));

		// We setup the top part of prompts.
		{
			JLabel labelPrompt = new JLabel("Start by filling up the relevant details.");
			container.add(labelPrompt, "2, 2, 7, 1");

			JButton buttonHelp = uiElements.buttonHelp();
			buttonHelp.addActionListener(buttonHelpListener);
			container.add(buttonHelp, "10, 2, right, center");

			JSeparator separator = new JSeparator();
			container.add(separator, "2, 4, 10, 1");

			JLabel labelFillInPrompt = new JLabel("Fields marked with (*) cannot be left empty.");
			container.add(labelFillInPrompt, "2, 6, 11, 1, center, bottom");
		}

		// We setup the JLabels and JTextFields of the input form.
		{
			// The JLabels of the names.
			JLabel labelName = new JLabel("Module Name");
			JLabel labelFreq = new JLabel("Frequency (MHz)");
			JLabel labelLatency = new JLabel("Latency (clock cycles)");
			JLabel labelDataPoints = new JLabel("Data Points");
			JLabel labelEstClk = new JLabel("Estimated Clock (ns)");
			JLabel labelLuts = new JLabel("LUTs");
			JLabel labelOpt = new JLabel("Optimisation");

			container.add(labelName, "2, 8, right, center");
			container.add(labelFreq, "2, 12, right, center");
			container.add(labelLatency, "2, 14, right, center");
			container.add(labelLuts, "2, 16, right, center");
			container.add(labelDataPoints, "8, 14, right, center");
			container.add(labelEstClk, "8, 16, right, center");
			container.add(labelOpt, "2, 10, right, center");

			// The JLabel to display the error if an invalid input is entered.
			JLabel labelError = helperFields.createErrorLabel();
			container.add(labelError, "8 17 3 1, left center");

			// Setting up the JTextFields to receive valid inputs.
			int nameMaxLength = helperGetSet.getInputNameMaxLength();
			int intMaxLength = helperGetSet.getInputIntMaxLength();

			inputAddNewName = helperFields.createTextField(nameMaxLength, labelError, 1);
			inputAddNewName.setEditable(false);

			inputAddNewOpt = helperFields.createTextField(nameMaxLength, labelError, 1);
			inputAddNewOpt.setEditable(false);

			inputFreq = helperFields.createTextField(intMaxLength, labelError, 0);
			inputLatency = helperFields.createTextField(intMaxLength, labelError, 0);
			inputLuts = helperFields.createTextField(intMaxLength, labelError, 0);
			inputDataPoints = helperFields.createTextField(intMaxLength, labelError, 0);
			inputEstClk = helperFields.createTextField(intMaxLength, labelError, 0);

			container.add(inputAddNewName, "10 8");
			container.add(inputAddNewOpt, "10 10");
			container.add(inputFreq, "4 12");
			container.add(inputLatency, "4 14");
			container.add(inputLuts, "4 16");
			container.add(inputDataPoints, "10 14");
			container.add(inputEstClk, "10 16");
		}

		// We setup the JCheckBoxes of the input form.
		{
			checkBoxAddNewName = new JCheckBox("Add New");
			checkBoxAddNewName.addActionListener(checkBoxAddNewNameListener);
			checkBoxAddNewName.setToolTipText("Add a new module name.");
			container.add(checkBoxAddNewName, "8, 8, right, center");

			checkBoxAddNewOpt = new JCheckBox("Add New");
			checkBoxAddNewOpt.addActionListener(checkBoxAddNewOptListener);
			checkBoxAddNewOpt.setToolTipText("Add a new optimisation.");
			container.add(checkBoxAddNewOpt, "8, 10, right, default");

			// Checks whether the target directory is empty. This may happen when we add
			// files to VeriLibrary for the first time.
			if (helperClass.isNoDirs(helperGetSet.getTargetDir())) {
				// If the target directory is empty, we force the user to add a new module name
				// and optimisation.
				String[] filler = { "No directory detected. Add New." };
				comboBoxName = new JComboBox<Object>(filler);
				comboBoxOpt = new JComboBox<Object>(filler);

				comboBoxName.addItem(filler);
				comboBoxName.setEnabled(false);
				checkBoxAddNewName.setSelected(true);
				checkBoxAddNewName.setEnabled(false);
				inputAddNewName.setEditable(true);

				comboBoxOpt.addItem(filler);
				comboBoxOpt.setEnabled(false);
				checkBoxAddNewOpt.setSelected(true);
				checkBoxAddNewOpt.setEnabled(false);
				inputAddNewOpt.setEditable(true);

				// We set this to true so that when we get the module name and optimisation
				// text, we only get it from the add new JTextField and not the JComboBox.
				targetDirNull = true;
			} else {
				// If all is fine (the target directory has valid child directories), we simply
				// setup the JComboBoxes.
				setupComboBox();
			}

			container.add(comboBoxName, "4, 8, 3, 1, fill, center");
			container.add(comboBoxOpt, "4, 10, 3, 1, fill, center");
		}

		// We setup the JLabels that show that the JTextField needs to be filled up.
		// It is (*).
		{
			String error = helperGetSet.getWarningName();

			errorName = new JLabel(error);
			errorName.setVisible(false);

			errorOpt = new JLabel(error);
			errorOpt.setVisible(false);

			JLabel errorFreq = new JLabel(error);
			JLabel errorLatency = new JLabel(error);
			JLabel errorLuts = new JLabel(error);
			JLabel errorDataPoints = new JLabel(error);
			JLabel errorEstClk = new JLabel(error);

			container.add(errorName, "12, 8");
			container.add(errorOpt, "12, 10");
			container.add(errorFreq, "6, 12");
			container.add(errorLatency, "6, 14");
			container.add(errorLuts, "12, 14");
			container.add(errorDataPoints, "6, 16");
			container.add(errorEstClk, "12, 16");
		}

		// We setup the panel for us to select the Verilog files we want to add.
		{
			JSeparator separator_1 = new JSeparator();
			container.add(separator_1, "2, 18, 9, 1");

			panelSelFiles = new PanelSelFiles();
			container.add(panelSelFiles, "2, 20, 11, 1, fill, fill");

			JSeparator separator_2 = new JSeparator();
			container.add(separator_2, "2, 22, 9, 1");
		}

		// We setup the panel to select where to save our Verilog files to.
		{
			JLabel labelSaveFilesPrompt = new JLabel(
					"Select where to save your files. It should be a valid target directory.");
			container.add(labelSaveFilesPrompt, "2, 24, 10, 1");

			panelSaveFiles = new PanelSaveFiles();
			panelSaveFiles.setSavePath(helperGetSet.getTargetDir());
			panelSaveFiles.labelSaveDir.setText(helperGetSet.getTargetDir());
			container.add(panelSaveFiles, "2, 26, 11, 1, fill, fill");
		}

		// We setup the bottom part where we add, move, or finish.
		{
			JSeparator separator = new JSeparator();
			container.add(separator, "2, 28, 9, 1");

			JPanel panelButton = new JPanel();
			container.add(panelButton, "2, 30, 11, 1, fill, fill");
			panelButton.setLayout(new FlowLayout(FlowLayout.RIGHT));

			uiLoading = new UILoading();
			panelButton.add(uiLoading);

			buttonAddFiles = new JButton("Add Files");
			buttonAddFiles.setToolTipText("Copies the files to the target destination.");
			getRootPane().setDefaultButton(buttonAddFiles);
			buttonAddFiles.addActionListener(buttonAddFilesListener);
			panelButton.add(buttonAddFiles);

			buttonMoveFiles = new JButton("Move Files");
			buttonMoveFiles.setToolTipText("Copies the files to the target destination and deletes them at their original destination.");
			buttonMoveFiles.addActionListener(buttonMoveFilesListener);
			panelButton.add(buttonMoveFiles);

			JButton buttonFinished = new JButton("Finished");
			buttonFinished.addActionListener(buttonFinishedListener);
			panelButton.add(buttonFinished);
		}
	}

	/***********
	 * METHODS *
	 **********/

	/**
	 * Sets up the module name and optimisation JComboBox by filling them with
	 * directories at their respective path.
	 */
	private void setupComboBox() {
		// We setup the module name JComboBox.
		String[] names = helperClass.getComboBoxList(helperGetSet.getTargetDir());
		comboBoxName = new JComboBox<Object>(names);
		comboBoxName.setSelectedItem(0);
		comboBoxName.setMaximumSize(getMaximumSize());
		comboBoxName.addItemListener(comboBoxNameListener);

		// We setup the optimisation JComboBox by getting the target directory and
		// appending the selected module name (index 0) in the module name JComboBox.
		String[] opt = helperClass
				.getComboBoxList(helperGetSet.getTargetDir() + "\\" + comboBoxName.getSelectedItem().toString());
		comboBoxOpt = new JComboBox<Object>(opt);
		comboBoxOpt.setSelectedItem(0);
		comboBoxOpt.setMaximumSize(comboBoxOpt.getMaximumSize());

	}

	/**
	 * Initialises the IO operation by getting the relevant paths and names needed
	 * and passing it to the HelperIO.
	 * 
	 * @param isFileAdd - whether we are adding (copying) or moving (cutting) files.
	 */
	private void initialiseIO(boolean isFileAdd) {
		// We setup the UILoading panel.
		uiLoading.initialise();
		helperIO.setUiLoading(uiLoading);

		// We check for errors.
		// We check if there are any files selected.
		// We check if there are any blank JTextField inputs.
		// We check if they have added any incompatible files (not .v/.tcl) .
		if (!isErrorPresent()) {
			// If there are no errors, we proceed to initialise IO.
			for (int i = 0; i < fileNames.size(); i++) {
				String sourceFileName = fileNames.get(i);

				// Prepare files and paths for transfer
				String sourcePath = panelSelFiles.getSelPath();
				String targetPath = getFullTargetPath();
				String targetFileName = getFullFileName(sourceFileName);

				// Set the values in HelperIO.
				helperIO.addMoveInitialiseIO(sourceFileName, targetFileName, sourcePath, targetPath);

				// Add or Move
				if (isFileAdd == true) {
					helperIO.fileWriteAdd();
				} else {
					helperIO.fileWriteMove();
				}
			}

			// We clear the select files model to allow the user to select a new set of
			// files.
			panelSelFiles.model.clear();
		}
	}

	/**
	 * Checks for any error present.
	 * 
	 * Checks if there are any files selected. Checks if there are any blank
	 * JTextField inputs. Checks if they have added any incompatible files (not
	 * .v/.tcl).
	 * 
	 * @return if there is any error present.
	 */
	private boolean isErrorPresent() {
		if (panelSelFiles.getSelPath() == null) {
			// Checks if there are any files selected.
			helperAlerts.fileNotSelected();
			panelSelFiles.buttonSelFiles.grabFocus();
			uiLoading.fail();
			return true;
		}

		if (isInputEmpty()) {
			// Checks if there are any blank JTextField inputs.
			helperAlerts.inputNotEntered();
			uiLoading.fail();
			return true;
		}

		if (isDuplicate()) {
			helperAlerts.fileDuplicateAdd();
			uiLoading.fail();
			return true;
		}

		// Checks if they have added any incompatible files (not .v/.tcl)
		for (File file : panelSelFiles.getSelFiles()) {
			if (file.getName().contains(".v") || file.getName().contains(".tcl")) {
				// We check for files that end with .v and .tcl.
				// If there is, we added it to the ArrayList<String> to add.
				fileNames.add(file.getName());
			} else {
				// Else, we tell the user there is an incompatible file.
				uiLoading.fail();
				helperAlerts.fileVerilogIncompat(file.getName());
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if the module that is to be added already exists.
	 * 
	 * It does this by checking whether the path of the module to add the files to
	 * already exists.
	 * 
	 * @return true = module to add already exists. false = module to add does not
	 *         exist.
	 */
	private boolean isDuplicate() {
		if (helperIO.isDirFileExists(getFullTargetPath())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks every JTextField input to see if they are empty.
	 * 
	 * @return true = empty text fields. false = no empty text fields.
	 */
	private boolean isInputEmpty() {
		if (checkBoxAddNewName.isSelected()) {
			if (inputAddNewName.getText().isEmpty()) {
				return true;
			}
		}

		if (checkBoxAddNewOpt.isSelected()) {
			if (inputAddNewOpt.getText().isEmpty()) {
				return true;
			}
		}
		if (inputFreq.getText().isEmpty() || inputDataPoints.getText().isEmpty() || inputLatency.getText().isEmpty()
				|| inputLuts.getText().isEmpty() || inputEstClk.getText().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/*************
	 * LISTENERS *
	 ************/

	/**
	 * Launches the help dialog.
	 */
	private class ButtonHelpListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			DialogHelp dialogHelp = new DialogHelp();
			dialogHelp.setHelpCard(DialogHelp.getAddFileHow());
		}
	}

	/**
	 * Sets the add IO operation underway.
	 */
	private class ButtonAddFilesListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			initialiseIO(true);
		}
	}

	/**
	 * Sets the move IO operation underway.
	 */
	private class ButtonMoveFilesListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			initialiseIO(false);
		}
	}

	/**
	 * Prepares the dialog for closing.
	 * 
	 * Does not prompt a ynCancel alert if the whole dialog is empty (list and text
	 * fields).
	 */
	private class ButtonFinishedListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// If the whole dialog is empty, we just dispose.
			if (isInputEmpty() && panelSelFiles.model.isEmpty()) {
				dispose();
			} else {
				// Else we show the ynCancel alert.
				if (helperAlerts.ynCancel()) {
					dispose();
				}
			}
		}
	}

	/**
	 * Toggles the respective UI elements when checkBoxAddNewName is toggled on/off.
	 * 
	 * Toggles the input JTextField to be editable or not. Toggles the JComboBox to
	 * be enabled or not. Toggles the error JLabel to be visible or not.
	 */
	private class CheckBoxAddNewNameListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (checkBoxAddNewName.isSelected()) {

				inputAddNewName.setEditable(true);
				inputAddNewName.grabFocus();
				comboBoxName.setEnabled(false);
				errorName.setVisible(true);

			} else {
				inputAddNewName.setEditable(false);
				comboBoxName.setEnabled(true);
				errorName.setVisible(false);
			}
		}
	}

	/**
	 * Toggles the respective UI elements when checkBoxAddNewOpt is toggled on/off.
	 * 
	 * Toggles the input JTextField to be editable or not. Toggles the JComboBox to
	 * be enabled or not. Toggles the error JLabel to be visible or not.
	 */
	private class CheckBoxAddNewOptListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (checkBoxAddNewOpt.isSelected()) {
				inputAddNewOpt.setEditable(true);
				inputAddNewOpt.grabFocus();

				errorOpt.setVisible(true);
				comboBoxOpt.setEnabled(false);

			} else {
				errorOpt.setVisible(false);
				comboBoxOpt.setEnabled(true);
				inputAddNewOpt.setEditable(false);
			}
		}
	}

	/**
	 * Sets up the optimisation JComboBox by listening to the module name JComboBox
	 * and loading the optimisations according to the element selected in the
	 * JComboBox.
	 */
	private class ComboBoxNameListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			String[] opt = helperClass
					.getComboBoxList(helperGetSet.getTargetDir() + "\\" + comboBoxName.getSelectedItem().toString());

			comboBoxOpt.removeAllItems();
			for (int i = 0; i < opt.length; i++) {
				comboBoxOpt.addItem(opt[i]);
			}
		}
	}

	/***********
	 * GET/SET *
	 **********/

	/**
	 * Gets the text in the add new name JTextField.
	 * 
	 * @return the text in the add new name JTextField.
	 */
	private String getInputNameText() {
		if (targetDirNull) {
			// There are no directories that can be loaded in the JComboBox, so we default
			// the input to add a new name.
			return inputAddNewName.getText();
		}

		// Else, we can freely select whether we want to add a new name (get text from
		// JTextField) or not (get selected index from the JComboBox).
		if (checkBoxAddNewName.isSelected()) {
			return inputAddNewName.getText();
		} else {
			return comboBoxName.getSelectedItem().toString();
		}
	}

	/**
	 * Gets the text in the optimisation JTextField.
	 * 
	 * @return the text in the optimisation JTextField.
	 */
	private String getInputOpt() {
		if (targetDirNull) {
			// There are no directories that can be loaded in the JComboBox, so we default
			// the input to add a new name.
			return inputAddNewOpt.getText();
		}

		// Else, we can freely select whether we want to add a new name (get text from
		// JTextField) or not (get selected index from the JComboBox).
		if (checkBoxAddNewOpt.isSelected()) {
			return inputAddNewOpt.getText();
		} else {
			return comboBoxOpt.getSelectedItem().toString();
		}
	}

	/**
	 * Gets the full path of the destination to store all our files. It is derived
	 * by combining the inputs from our JTextFields and JComboBoxes (our save path
	 * from the JFileChooser, name, optimisation, frequency, data points).
	 * 
	 * @return the full path of the destination.
	 */
	private String getFullTargetPath() {
		String opt = getInputOpt().trim();
		String inputNameText = getInputNameText().trim();
		String inputFreqText = inputFreq.getText();
		String inputDataPointsText = inputDataPoints.getText();

		return panelSaveFiles.getSavePath() + "\\" + inputNameText + "\\" + opt + "\\" + inputFreqText + "MHz" + "\\"
				+ inputDataPointsText + " Data Points" + "\\";
	}

	/**
	 * Gets the full name of the files we selected in the list.. It is derived by
	 * combining the inputs from our JTextFields (latency, est clk, luts).
	 * 
	 * @param fileName - the file names selected in the list.
	 * @return the full path of the destination.
	 */
	private String getFullFileName(String fileName) {
		String inputLatencyText = inputLatency.getText();
		String inputEstClkText = inputEstClk.getText();
		String inputLutsText = inputLuts.getText();

		return inputLatencyText + "_" + inputEstClkText + "_" + inputLutsText + "_" + fileName;
	}
}
