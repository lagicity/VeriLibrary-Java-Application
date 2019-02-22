package dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import help.DialogHelp;
import helper.HelperClass;
import helper.HelperGetSet;
import net.miginfocom.swing.MigLayout;
import ui.UIElements;

/**
 * Allows the user to select the top module. It loads all files in the selected
 * directory that ends with ".v". It sets the setSelDir() method in
 * HelperGetSet.
 * 
 * @see HelperGetSet
 *
 */

public class DialogSelTopMod extends JDialog {
	private static final long serialVersionUID = 8771855325400612055L;

	/****************
	 * UI ELEMENTS *
	 ***************/

	// The container that houses all the UI elements
	private JPanel container = new JPanel();

	// The list that shows the .v files
	private JList<String> list = new JList<String>();

	// Only .v files are shown in the list, but there may be non .v files in the
	// directory.
	// This means if there are 3 .v files but 5 files in the directory (3x .v,
	// 2x .tcl), selecting index 2 may not be the corresponding .v file in the
	// directory.

	// verilogFileIndex maps the .v files shown in the list to the .v files in the
	// directory.
	private int[] verilogFileIndex;

	/*************
	 * VARIABLES *
	 *************/

	// Set to true when the "Select" button is pressed to allow the selected top
	// module to be displayed in the ViewMod panel.
	private boolean close = false;

	/*************
	 * LISTENERS *
	 ************/

	// The listener for the "Help" button.
	private ActionListener buttonHelpListener = new ButtonHelpListener();

	// The listener for the "Select" button.
	private ActionListener selectHelpListener = new ButtonSelectListener();

	// The listener for the "Cancel" button.
	private ActionListener cancelHelpListener = new ButtonCancelListener();

	/***********
	 * HELPERS *
	 ***********/

	private HelperClass helperClass = new HelperClass();
	private HelperGetSet helperGetSet = new HelperGetSet();

	private UIElements uiElements = new UIElements();

	/***************
	 * CONSTRUCTOR *
	 **************/

	public DialogSelTopMod(Component comp, String title, ModalityType modal) {
		// Initialise the UI
		createGUI();
		setupList();

		// Initialise the dialog properties
		setTitle(title);
		setModalityType(modal);
		setResizable(false);
		setLocationRelativeTo(comp);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		// Initialise the view
		pack();
		setVisible(true);
	}

	/******************
	 * INITIALISATION *
	 *****************/

	private void createGUI() {

		container.setLayout(new MigLayout("", "[424px]", "[14px][grow]"));
		add(container, BorderLayout.CENTER);

		// The top row's label
		{
			JLabel labelPrompt = new JLabel("Select another top module from the list below: ");
			container.add(labelPrompt, "flowx,cell 0 0,growx,aligny center");

		}

		// The help button at the top row
		{
			JButton buttonHelp = uiElements.buttonHelp();
			buttonHelp.addActionListener(buttonHelpListener);
			container.add(buttonHelp, "cell 0 0");
		}

		// The scroll pane that houses the list
		{
			JScrollPane scrollPane = new JScrollPane(list);
			container.add(scrollPane, "cell 0 1,growx,aligny top");
		}

		// The panel of buttons at the bottom row
		{
			JPanel panelButton = new JPanel();
			panelButton.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(panelButton, BorderLayout.SOUTH);

			JButton buttonSelect = new JButton("Select");
			buttonSelect.addActionListener(selectHelpListener);
			buttonSelect.setActionCommand("OK");
			panelButton.add(buttonSelect);
			getRootPane().setDefaultButton(buttonSelect);

			JButton buttonCancel = new JButton("Cancel");
			buttonCancel.addActionListener(cancelHelpListener);
			buttonCancel.setActionCommand("Cancel");
			panelButton.add(buttonCancel);
		}
	}

	/***********
	 * METHODS *
	 **********/

	/**
	 * Sets up the list.
	 */
	private void setupList() {
		// We create a model to store all the strings of our .v file names.
		DefaultListModel<String> model = new DefaultListModel<String>();

		// We look for all the .v, .tcl etc files at the selected directory.
		File file = new File(helperGetSet.getSelDir());
		File[] fileNames = file.listFiles(File::isFile);

		// We create an array of integers to map the .v files in the list to the .v
		// files
		// in the selected directory.
		verilogFileIndex = new int[fileNames.length];

		// We are keeping track of how many .v files we have added to verilogFileIndex
		int counter = 0;

		// We are mapping the .v files in the list to the .v files in the selected
		// directory.
		for (int i = 0; i < fileNames.length; i++) {

			// We get the name of each file and if it is a .v file, we add it to the model
			// and map it with verilogFileIndex.
			File file1 = fileNames[i];

			if (file1.getName().contains(".v")) {
				// We parse the file name to remove the underscores due to their format.
				// ie. 1_2_3_fileName -> fileName.
				model.addElement(helperClass.getParsedFileName(file1.getName(), 3));
				verilogFileIndex[counter] = i;
				counter++;

			}
		}

		list.setModel(model);
		list.setSelectedIndex(0);
	}

	/**
	 * Sets the full top module in HelperGetSet. It parses the directory and sets
	 * the corresponding selected .v file in the directory.
	 * 
	 * @param index - the index that is selected in the list.
	 * @return - void
	 * @see - HelperGetSet
	 */
	private void setFullTopMod(int index) {
		// Creates a file pointing to all the files (.v, .tcl etc) from the path
		// selected.
		File file = new File(helperGetSet.getSelDir());

		// We need to know all the files at the path.
		File[] fileNames = file.listFiles(File::isFile);

		// We set the top module as the corresponding selected .v file.
		helperGetSet.setFullTopMod(fileNames[verilogFileIndex[index]].getName());
	}

	/*************
	 * LISTENERS *
	 ************/

	// Closes the window.
	private class ButtonCancelListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			dispose();
		}
	}

	// Sets the full top module and prepares the dialog for closing.
	private class ButtonSelectListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			setFullTopMod(list.getSelectedIndex());
			setClose(true);
			dispose();
		}
	}

	// Launches the Help dialog.
	private class ButtonHelpListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			DialogHelp dialogHelp = new DialogHelp();
			dialogHelp.setHelpCard(DialogHelp.getViewFileMissTopMod());
		}
	}

	/***********
	 * GET/SET *
	 **********/

	public boolean isClose() {
		return close;
	}

	public void setClose(boolean close) {
		this.close = close;
	}
}
