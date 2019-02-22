package menu;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.Sizes;

import help.DialogHelp;
import helper.HelperAlerts;
import helper.HelperGetSet;
import panels.PanelViewModList;
import ui.UIElements;

/**
 * Allows the deleting of any files from VeriLibrary.
 * 
 * The user selects what they want to remove and adds them to a list. They are
 * then prompted whether they want to remove the files in the list. If they do,
 * the files are removed using walkFileTree in MenuEditRemFiles.
 */
public class EditRemTabRem extends JPanel {
	private static final long serialVersionUID = -4438405598070797161L;

	/****************
	 * UI ELEMENTS *
	 ***************/

	// We create 5 objects of PanelViewModList to represent each type.
	private PanelViewModList panelName;
	private PanelViewModList panelOpt;
	private PanelViewModList panelFreq;
	private PanelViewModList panelDataPoints;
	private PanelViewModList panelFiles;

	private JScrollPane scrollPane;

	private JButton buttonAdd;
	private JButton buttonRemove;
	private JButton buttonClear;

	// This is public because we need to access it in DialogEditRem to clear the list when we remove files.
	private JList<String> listRemove;

	private JComboBox<Object> comboBoxType;

	private JSeparator separator;

	// This is public because we need to use it in the remove button listener in
	// MenuEditRemFiles.
	public DefaultListModel<String> modelRemove = new DefaultListModel<String>();

	/*************
	 * VARIABLES *
	 *************/

	private String dirName;
	private String dirOpt;
	private String dirFreq;
	private String dirDataPoints;

	// This is what the user wants to remove (module name, optimisation, frequency,
	// data point, Verilog files).
	private int typeIndex = 0;

	/*************
	 * LISTENERS *
	 ************/

	// Listens for what the type was selected.
	private ItemListener comboBoxTypeListener = new ComboBoxTypeListener();

	private ListSelectionListener listRemoveListener = new ListRemoveListener();

	private ListSelectionListener listNameListener = new ListNameListener();
	private ListSelectionListener listOptListener = new ListOptListener();
	private ListSelectionListener listFreqListener = new ListFreqListener();
	private ListSelectionListener listDataPointsListener = new ListDataPointsListener();
	private ListSelectionListener listFilesListener = new ListFileListener();

	private ActionListener buttonAddListener = new ButtonAddListener();
	private ActionListener buttonClearListener = new ButtonClearListener();
	private ActionListener buttonHelpListener = new ButtonHelpListener();
	private ActionListener buttonRemoveListener = new ButtonRemoveListener();

	/***********
	 * HELPERS *
	 ***********/

	private HelperGetSet helperGetSet = new HelperGetSet();
	private HelperAlerts helperAlerts = new HelperAlerts();
	private UIElements uiElements = new UIElements();

	/***************
	 * CONSTRUCTOR *
	 **************/

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new EditRemTabRem();
			}
		});
	}

	public EditRemTabRem() {
		setupUI();
		initialise();
	}

	/******************
	 * INITIALISATION *
	 *****************/

	private void setupUI() {
		// We setup the main container view.
		setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("center:max(150px;min)"),
						FormSpecs.LABEL_COMPONENT_GAP_COLSPEC, FormSpecs.PREF_COLSPEC,
						FormSpecs.LABEL_COMPONENT_GAP_COLSPEC, ColumnSpec.decode("center:max(150px;min)"),
						FormSpecs.LABEL_COMPONENT_GAP_COLSPEC, FormSpecs.PREF_COLSPEC,
						FormSpecs.LABEL_COMPONENT_GAP_COLSPEC, ColumnSpec.decode("center:max(150px;min)"),
						FormSpecs.LABEL_COMPONENT_GAP_COLSPEC, FormSpecs.PREF_COLSPEC,
						FormSpecs.LABEL_COMPONENT_GAP_COLSPEC, ColumnSpec.decode("center:max(150px;min)"),
						ColumnSpec.decode("2dlu"), FormSpecs.DEFAULT_COLSPEC, ColumnSpec.decode("2dlu"),
						ColumnSpec.decode("min(96px;min)"), FormSpecs.RELATED_GAP_COLSPEC,
						new ColumnSpec(ColumnSpec.FILL,
								Sizes.bounded(Sizes.DEFAULT, Sizes.constant("49dlu", true),
										Sizes.constant("56px", true)),
								1), },
				new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						RowSpec.decode("pref:grow"), FormSpecs.UNRELATED_GAP_ROWSPEC, RowSpec.decode("14px"),
						FormSpecs.UNRELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						RowSpec.decode("max(62dlu;pref):grow"), FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
						RowSpec.decode("23px"), }));

		// We setup the basic JLabels of the UI.
		{
			JLabel labelInstruction = new JLabel(
					"Start by selecting what you would like to remove. After which, add it to the list.");
			add(labelInstruction, "2, 2, 13, 1");

			JLabel arrow1 = new JLabel(">");
			JLabel arrow2 = new JLabel(">");
			JLabel arrow3 = new JLabel(">");
			JLabel arrow4 = new JLabel(">");

			add(arrow1, "4, 10, left, center");
			add(arrow2, "8, 10, left, center");
			add(arrow3, "12, 10, left, center");
			add(arrow4, "16, 10");

			JLabel labelPromptComboBox = new JLabel("What would you like to remove?");
			JLabel labelPromptRemList = new JLabel("Files to be Removed");

			add(labelPromptComboBox, "2, 6, right, center");
			add(labelPromptRemList, "6, 12, 9, 1, center, top");

			JLabel labelNameMod = new JLabel("Module Name");
			JLabel labelNameOpt = new JLabel("Optimisation");
			JLabel labelNameFreq = new JLabel("Frequency");
			JLabel labelNameDataPoints = new JLabel("Data Points");
			JLabel labelNameFiles = new JLabel("Verilog Files");

			add(labelNameMod, "2, 8");
			add(labelNameOpt, "6, 8");
			add(labelNameFreq, "10, 8");
			add(labelNameDataPoints, "14, 8");
			add(labelNameFiles, "18, 8, 3, 1, center, default");

			separator = new JSeparator();
			add(separator, "2, 4, 17, 1");
		}

		// We setup the buttons and combo box.
		{
			JButton buttonHelp = uiElements.buttonHelp();
			buttonHelp.addActionListener(buttonHelpListener);
			add(buttonHelp, "20, 4, right, default");

			String options[] = { "Module Name", "Optimisation", "Frequency", "Data Points", "Verilog Files" };
			comboBoxType = new JComboBox<Object>(options);
			comboBoxType.addItemListener(comboBoxTypeListener);
			comboBoxType.setSelectedItem(0);
			add(comboBoxType, "6, 6, fill, default");

			buttonAdd = new JButton("Add to List");
			buttonAdd.addActionListener(buttonAddListener);
			add(buttonAdd, "18, 14, left, center");

			buttonClear = new JButton("Clear List");
			buttonClear.addActionListener(buttonClearListener);
			add(buttonClear, "2, 16, right, bottom");

			buttonRemove = new JButton("Remove From List");
			buttonRemove.addActionListener(buttonRemoveListener);
			add(buttonRemove, "18, 16, 3, 1, left, bottom");
		}

		// We setup the view panels.
		{
			panelName = new PanelViewModList();
			panelOpt = new PanelViewModList();
			panelFreq = new PanelViewModList();
			panelDataPoints = new PanelViewModList();
			panelFiles = new PanelViewModList();

			add(panelName, "2, 10, fill, fill");
			add(panelOpt, "6, 10, fill, fill");
			add(panelFreq, "10, 10, fill, fill");
			add(panelDataPoints, "14, 10, fill, fill");
			add(panelFiles, "18, 10, 3, 1, fill, fill");

			scrollPane = new JScrollPane();
			add(scrollPane, "6, 14, 9, 3, fill, fill");

			listRemove = new JList<String>();
			listRemove.setVisibleRowCount(15);
			listRemove.addListSelectionListener(listRemoveListener);
			scrollPane.setViewportView(listRemove);
		}
	}

	/***********
	 * METHODS *
	 **********/

	/**
	 * Initialises the whole panel, its buttons and the view panels.
	 */
	public void initialise() {
		if (modelRemove.isEmpty()) {
			buttonRemove.setEnabled(false);
			buttonClear.setEnabled(false);
		} else {
			buttonRemove.setEnabled(true);
			buttonClear.setEnabled(true);
		}

		setupPanel(0);
		setupModes(0);
	}

	/**
	 * Checks if there are any duplicates that are added to the list.
	 * 
	 * @param paths - the paths that are being added.
	 * @return whether there are any duplicates.
	 */
	private boolean isDuplicate(List<String> paths) {
		// We iterate through all the elements in the model.
		for (int i = 0; i < modelRemove.size(); i++) {
			// We compare it to the paths that we want to add.
			for (int j = 0; j < paths.size(); j++) {
				if (paths.get(j).toString().contentEquals(modelRemove.getElementAt(i))) {
					// If there is a duplicate, we return true.
					return true;
				}
			}
		}

		// Else, we return false.
		return false;
	}

	/**
	 * Removes duplicates when some of the files to be added are already in the
	 * list. Those that are not duplicates are added to the list.
	 * 
	 * Used when a duplicate is detected.
	 * 
	 * @param addPaths - the paths that are trying to be added.
	 */
	private void removeDuplicate(List<String> addPaths) {
		// An ArrayList<String> consisting duplicate paths to display in our alert.
		ArrayList<String> paths = new ArrayList<String>();

		// We iterate through all the elements in the model.
		for (int i = 0; i < modelRemove.size(); i++) {
			// We compare it to the paths that we want to add.
			for (int j = 0; j < addPaths.size(); j++) {
				if (addPaths.get(j).toString().contentEquals(modelRemove.getElementAt(i))) {
					// If there is a duplicate, we add it to paths.
					paths.add(addPaths.get(j).toString());
					// We also remove the duplicate from addPaths.
					addPaths.remove(j);
				}
			}
		}

		// What's left are paths that are not duplicates, so we add them to the list.
		addToModel(addPaths);
		// We then display the warning.
		helperAlerts.fileDuplicateList(paths);
	}

	/**
	 * Adds the paths to the model.
	 * 
	 * @param paths - paths that are to be added to the model.
	 */
	private void addToModel(List<String> paths) {
		for (int i = 0; i < paths.size(); i++) {
			modelRemove.addElement(paths.get(i));
		}

		listRemove.setModel(modelRemove);
		listRemove.setSelectedIndex(modelRemove.size() - 1);

		buttonClear.setEnabled(true);
		buttonRemove.setEnabled(true);
	}

	/**
	 * Sets up the panels.
	 * 
	 * Adds their listener and updates the path up till the panel according to what
	 * panel and which element the user clicks.
	 * 
	 * @param panel - the panel we need to setup.
	 */
	private void setupPanel(int panel) {
		String startDir = helperGetSet.getTargetDir();

		// Each panel is set up in a similar fashion. The module name panel is set up
		// differently as it is the base panel.
		//
		// The panels are set up as follows.
		// 1. Generate the path up to their panel by getting the previous
		// panels' selected element.
		//
		// 2. Create the new list and load it with directories from the path
		// directory.
		//
		// 3. Add the listener so we can update the next panel with the element just
		// clicked on.
		//
		// 4. We call the next panel to update it and start the process again.

		// The try-catch is for when we cannot find any directory to select as we
		// automatically select index 0 (that is, there is no directory to select).

		switch (panel) {
		// We are setting up the module name panel.
		case 0:
			panelName.setupListDir(startDir);

			panelName.list.addListSelectionListener(listNameListener);

			setupPanel(1);
			setupModes(typeIndex);
			break;

		// We are setting up the optimisation panel.
		case 1:
			try {
				dirName = startDir + "\\" + panelName.getSel().get(0).toString();

				panelOpt.setupListDir(dirName);

				panelOpt.list.addListSelectionListener(listOptListener);

				setupPanel(2);
				setupModes(typeIndex);
			} catch (IndexOutOfBoundsException e) {
				helperAlerts.fileGeneralIncompat(startDir);
			}

			break;

		// We are setting up the frequency panel.
		case 2:
			try {
				dirOpt = dirName + "\\" + panelOpt.getSel().get(0).toString();

				panelFreq.setupListDir(dirOpt);
				panelFreq.list.addListSelectionListener(listFreqListener);

				setupPanel(3);
				setupModes(typeIndex);
			} catch (IndexOutOfBoundsException e) {
				helperAlerts.fileGeneralIncompat(dirName);
			}

			break;

		// We are setting up the data points panel.
		case 3:
			try {
				dirFreq = dirOpt + "\\" + panelFreq.getSel().get(0).toString();

				panelDataPoints.setupListDir(dirFreq);
				panelDataPoints.list.addListSelectionListener(listDataPointsListener);

				setupPanel(4);
				setupModes(typeIndex);
			} catch (IndexOutOfBoundsException e) {
				helperAlerts.fileGeneralIncompat(dirFreq);
			}

			break;

		// We are setting up the files panel.
		case 4:
			try {
				dirDataPoints = dirFreq + "\\" + panelDataPoints.getSel().get(0).toString();
				panelFiles.setupListFile(dirDataPoints);

			} catch (IndexOutOfBoundsException e) {
				helperAlerts.fileGeneralIncompat(dirDataPoints);
			}

			panelFiles.list.addListSelectionListener(listFilesListener);
			setupModes(typeIndex);
			break;

		default:
			System.out.println("Out of bounds in setupPanel() in MenuEditRembTabRem");
			break;
		}
	}

	/**
	 * Controls which panel should be enabled and what their type of selection
	 * should be.
	 * 
	 * It depends on what type the user wants to remove. The panel of the type the
	 * user selects is multiple_interval_selection, the panels before that will be
	 * single_selection, and the panels after that will be disabled.
	 * 
	 * This is to help guide the user and prevent the accidental adding of
	 * directories.
	 * 
	 * @param panel - the panel we are setting.
	 */
	private void setupModes(int panel) {
		switch (panel) {

		// The type selected is the module name panel.
		case 0:
			panelName.list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

			panelName.list.setEnabled(true);
			panelOpt.list.setEnabled(false);
			panelFreq.list.setEnabled(false);
			panelDataPoints.list.setEnabled(false);
			panelFiles.list.setEnabled(false);
			break;

		// The type selected is the optimisation panel.
		case 1:
			panelName.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			panelOpt.list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

			panelName.list.setEnabled(true);
			panelOpt.list.setEnabled(true);
			panelFreq.list.setEnabled(false);
			panelDataPoints.list.setEnabled(false);
			panelFiles.list.setEnabled(false);
			break;

		// The type selected is the frequency panel.
		case 2:
			panelName.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			panelOpt.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			panelFreq.list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

			panelName.list.setEnabled(true);
			panelOpt.list.setEnabled(true);
			panelFreq.list.setEnabled(true);
			panelDataPoints.list.setEnabled(false);
			panelFiles.list.setEnabled(false);
			break;

		// The type selected is the data points panel.
		case 3:
			panelName.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			panelOpt.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			panelFreq.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			panelDataPoints.list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

			panelName.list.setEnabled(true);
			panelOpt.list.setEnabled(true);
			panelFreq.list.setEnabled(true);
			panelDataPoints.list.setEnabled(true);
			panelFiles.list.setEnabled(false);
			break;

		// The type selected is the files panel.
		case 4:
			panelName.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			panelOpt.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			panelFreq.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			panelDataPoints.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			panelFiles.list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

			panelName.list.setEnabled(true);
			panelOpt.list.setEnabled(true);
			panelFreq.list.setEnabled(true);
			panelDataPoints.list.setEnabled(true);
			panelFiles.list.setEnabled(true);
			break;
		}
	}

	/*************
	 * LISTENERS *
	 ************/

	/**
	 * Removes the selected elements from the list.
	 * 
	 * Used when you want to remove elements from the list (not delete the files).
	 */
	private class ButtonRemoveListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// We get the indexes that we selected to remove.
			int[] indexes = listRemove.getSelectedIndices();

			// If there are >2 indexes, when you remove 1 index, all the other indexes are
			// off by 1 as the array shortens by 1.
			for (int i = 0; i < indexes.length; i++) {
				if (indexes.length == 1) {
					modelRemove.remove(indexes[i]);
				} else {
					// This corrects the difference
					modelRemove.remove(indexes[i] - i);
				}
			}

			listRemove.setModel(modelRemove);
			listRemove.setSelectedIndex(modelRemove.size() - 1);
		}
	}

	/**
	 * Clears the list of all the elements.
	 */
	private class ButtonClearListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			modelRemove.clear();
		}
	}

	/**
	 * Adds the selected files in the view panels to the list.
	 */
	private class ButtonAddListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			List<String> remove = new ArrayList<String>();

			if (typeIndex == 0) {
				remove = getRemoveDir(helperGetSet.getTargetDir(), panelName.getSel());
			} else if (typeIndex == 1) {
				remove = getRemoveDir(dirName, panelOpt.getSel());
			} else if (typeIndex == 2) {
				remove = getRemoveDir(dirOpt, panelFreq.getSel());
			} else if (typeIndex == 3) {
				remove = getRemoveDir(dirFreq, panelDataPoints.getSel());
			} else if (typeIndex == 4) {
				remove = getRemoveFiles(dirDataPoints, panelFiles.getListInt());
			}

			// can't have duplicates if your model is empty
			if (modelRemove.isEmpty()) {
				addToModel(remove);
			} else {
				// now the model is not empty, let's check for duplicates
				if (isDuplicate(remove)) {
					removeDuplicate(remove);
				} else {
					// no duplicates, just add
					addToModel(remove);

				}
			}
		}
	}

	/**
	 * Launches the help dialog.
	 */
	private class ButtonHelpListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			DialogHelp dialogHelp = new DialogHelp();
			dialogHelp.setHelpCard(DialogHelp.getEditRemRemoveHow());
		}
	}

	/**
	 * Checks the list if it is empty. If it is, we disable the clear and remove
	 * buttons.
	 * 
	 * Used when the list is empty and we want to prevent the user from clicking on
	 * the buttons.
	 */
	private class ListRemoveListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (modelRemove.isEmpty()) {
				buttonClear.setEnabled(false);
				buttonRemove.setEnabled(false);
			} else {
				buttonClear.setEnabled(true);
				buttonRemove.setEnabled(true);
			}
		}
	}

	/**
	 * Listens to the index that is clicked for the files list.
	 */
	private class ListFileListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			panelFiles.setListInt(panelFiles.list.getSelectedIndices());
		}
	}

	/**
	 * Listens to the index that is clicked for the data points list.
	 */
	private class ListDataPointsListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			panelDataPoints.setSel(panelDataPoints.list.getSelectedValuesList());

			setupPanel(4);
		}
	}

	/**
	 * Listens to the index that is clicked for the frequency list.
	 */
	private class ListFreqListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			panelFreq.setSel(panelFreq.list.getSelectedValuesList());

			setupPanel(3);
		}
	}

	/**
	 * Listens to the index that is clicked for the optimisation list.
	 */
	private class ListOptListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			panelOpt.setSel(panelOpt.list.getSelectedValuesList());

			setupPanel(2);
		}
	}

	/**
	 * Listens to the index that is clicked for the module name list.
	 */
	private class ListNameListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			panelName.setSel(panelName.list.getSelectedValuesList());

			setupPanel(1);
		}
	}

	/**
	 * Listens to the index that is clicked for combo box.
	 * 
	 * We setup all the panels depending on the type we selected.
	 */
	private class ComboBoxTypeListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			typeIndex = comboBoxType.getSelectedIndex();

			setupPanel(0);
		}
	}

	/***********
	 * GET/SET *
	 **********/

	/**
	 * Gets the full path when directories are selected to be added to the list.
	 * 
	 * Used when a directory is added to the remove list.
	 * 
	 * @param path   - the path up till the indexType.
	 * @param selDir - the List<String> of directories that are selected.
	 * @return the full path of the directories that are to be removed.
	 */
	private List<String> getRemoveDir(String path, List<String> selDir) {
		List<String> remove = new ArrayList<String>();

		for (int i = 0; i < selDir.size(); i++) {
			remove.add(path + "\\" + selDir.get(i));
		}
		return remove;
	}

	/**
	 * Gets the full path when files are selected to be added to the list. We need a
	 * separate method from getRemoveDir because the list that houses files and list
	 * that houses directories are different from each other.
	 * 
	 * Used when a file is added to the remove list.
	 * 
	 * @param path   - the path up till the indexType.
	 * @param selInt - the string of int of files that are selected.
	 * @return the full path of the files that are to be removed.
	 */
	private List<String> getRemoveFiles(String path, int[] selInt) {
		List<String> selFiles = new ArrayList<String>();

		File file = new File(path);
		File[] fileNames = file.listFiles(File::isFile);

		for (int i = 0; i < selInt.length; i++) {
			selFiles.add(path + "\\" + fileNames[selInt[i]].getName());
		}
		return selFiles;
	}
}
