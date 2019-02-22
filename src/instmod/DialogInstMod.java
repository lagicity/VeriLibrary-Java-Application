package instmod;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import help.DialogHelp;
import helper.HelperAlerts;
import helper.HelperClass;
import helper.HelperGetSet;
import net.miginfocom.swing.MigLayout;
import ui.UIElements;
import ui.UIImages;
import wire.DialogWire;
import wire.HelperWire;

/**
 * A dialog to manage the modules to instantiate. The user can add, remove, and
 * replace modules by using the relevant settings. The user can also drag and
 * drop and reorder modules.
 * 
 * When the user is finished, they can proceed to configure the modules for
 * export.
 */
public class DialogInstMod extends JDialog {
	private static final long serialVersionUID = -3377293791898170077L;

	/****************
	 * UI ELEMENTS *
	 ***************/

	private JPanel container = new JPanel();

	private JLabel labelLuts;
	private JLabel labelTotal;

	private JCheckBox checkBoxReorder;

	/*************
	 * VARIABLES *
	 *************/

	/*************
	 * LISTENERS *
	 ************/

	private ActionListener buttonAddListener = new ButtonAddListener();
	private ActionListener buttonCancelListener = new ButtonCancelListener();
	private ActionListener buttonExportListener = new ButtonExportListener();
	private ActionListener buttonHelpListener = new ButtonHelpListener();

	private ActionListener checkBoxReorderListener = new CheckBoxReorderListener();

	private ListDataListener modelListListener = new ModelListListener();

	/***********
	 * HELPERS *
	 ***********/

	private HelperInstMod helperInstMod = new HelperInstMod();
	private HelperWire helperWire = new HelperWire();
	private HelperGetSet helperGetSet = new HelperGetSet();
	private HelperAlerts helperAlerts = new HelperAlerts();
	private HelperClass helperClass = new HelperClass();

	private UIImages uiImages = new UIImages();
	private UIElements uiElements = new UIElements();

	/******************
	 * INITIALISATION *
	 *****************/

	public DialogInstMod(Component comp, String title, ModalityType modal) {
		// Initialise the UI
		createGUI();

		updateLuts();
		updateTotal();

		helperInstMod.addRemovePane(true);
		helperInstMod.model.addListDataListener(modelListListener);

		// Initialise the dialog properties
		setTitle(title);
		setLocation(helperClass.getCenterScreenLocation(comp.getHeight(), comp.getWidth()));
		setModalityType(modal);
		setIconImage(uiImages.getInstModBig());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Instantiate Modules");

		// Show the dialog
		pack();
		setVisible(true);
	}

	/***************
	 * CONSTRUCTOR *
	 **************/

	private void createGUI() {

		container.setLayout(new MigLayout("", "[642px,grow]", "[][382.00px,grow][]"));
		getContentPane().add(container, BorderLayout.CENTER);

		// Setup the top prompt panel.
		{
			JPanel panelPrompt = new JPanel();
			panelPrompt.setLayout(new BorderLayout(0, 0));
			container.add(panelPrompt, "cell 0 0,grow");

			JLabel labelPrompt = new JLabel("Interact with modules by dragging and dropping.");
			panelPrompt.add(labelPrompt, BorderLayout.WEST);

			JButton buttonHelp = uiElements.buttonHelp();
			buttonHelp.addActionListener(buttonHelpListener);
			panelPrompt.add(buttonHelp, BorderLayout.EAST);

		}

		// Setup the middle main view panel.
		JPanel panelView = new JPanel();
		panelView.setLayout(new BorderLayout(0, 0));
		container.add(panelView, "cell 0 1,grow");

		// Setup the east panel containing the list of modules and labels at the bottom.
		{
			JPanel panelViewInstMod = new JPanel();
			panelViewInstMod.setLayout(new BorderLayout(0, 0));
			panelView.add(panelViewInstMod, BorderLayout.CENTER);

			JScrollPane scrollPane = new JScrollPane(helperInstMod);
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			panelViewInstMod.add(scrollPane, BorderLayout.CENTER);

			JPanel panelLuts = new JPanel();
			panelLuts.setLayout(new MigLayout("", "[][][][][][][][][][][][][][][][][]", "[]"));
			panelViewInstMod.add(panelLuts, BorderLayout.SOUTH);

			labelLuts = new JLabel("luts");
			panelLuts.add(labelLuts, "cell 0 0,alignx left,aligny top");

			labelTotal = new JLabel("total");
			panelLuts.add(labelTotal, "cell 16 0");
		}

		// Setup the west panel containing the settings.
		{
			JPanel panelSettings = new JPanel();
			panelView.add(panelSettings, BorderLayout.EAST);
			panelSettings.setLayout(new BorderLayout(0, 0));

			// Setup the top part of the Settings panel.
			{
				JPanel panelTop = new JPanel();
				panelTop.setLayout(new MigLayout("", "[131.00px,fill]", "[16px][][]"));
				panelSettings.add(panelTop, BorderLayout.NORTH);

				JLabel labelRemove = uiElements.labelDragDrop("Remove Module", uiImages.getRemove(),
						uiImages.getRemoveHover(), container.getFont().toString(), "instModRemove");
				labelRemove.setToolTipText("Removes the module.");
				panelTop.add(labelRemove, "cell 0 0,alignx center,aligny top");

				JLabel labelReplace = uiElements.labelDragDrop("Replace Module", uiImages.getReplace(),
						uiImages.getReplaceHover(), container.getFont().toString(), "instModReplace");
				labelReplace.setToolTipText("Replaces the module with another module.");
				panelTop.add(labelReplace, "cell 0 2,alignx center,aligny top");

				JSeparator separator = new JSeparator();
				panelTop.add(separator, "cell 0 1");
			}

			// Setup the bottom part of the Settings panel.
			{
				JPanel panelBottom = new JPanel();
				panelBottom.setLayout(new MigLayout("", "[129px]", "[][23px]"));
				panelSettings.add(panelBottom, BorderLayout.SOUTH);

				checkBoxReorder = new JCheckBox("Reorder modules");
				checkBoxReorder.setToolTipText("Check to reorder modules by dragging and dropping.");
				checkBoxReorder.addActionListener(checkBoxReorderListener);
				panelBottom.add(checkBoxReorder, "cell 0 0,alignx left,aligny top");

				JButton buttonAddMod = new JButton("Add New Module");
				buttonAddMod.setIcon(new ImageIcon(uiImages.getAddSmall()));
				buttonAddMod.addActionListener(buttonAddListener);
				panelBottom.add(buttonAddMod, "cell 0 1,alignx left,aligny top");
			}
		}

		// Setup the bottom buttons.
		{
			JButton buttonExport = new JButton("Configure & Export Modules");
			buttonExport.setIcon(new ImageIcon(uiImages.getExportSmall()));
			buttonExport.addActionListener(buttonExportListener);
			container.add(buttonExport, "flowx,cell 0 2,alignx right");

			JButton buttonCancel = new JButton("Cancel");
			buttonCancel.addActionListener(buttonCancelListener);
			container.add(buttonCancel, "cell 0 2,alignx right");
		}
	}

	/***********
	 * METHODS *
	 **********/

	/**
	 * Updates the total number of Luts of the modules in the list.
	 */
	private void updateLuts() {
		int luts = 0;

		// We get the Luts of each module and add them together.
		for (int i = 0; i < helperInstMod.model.size(); i++) {
			luts += Integer.valueOf(helperInstMod.model.getElementAt(i).getLuts());
		}

		labelLuts.setText("Total Luts: " + String.valueOf(luts));
	}

	/**
	 * Updates the total number of modules in the list.
	 */
	private void updateTotal() {
		// We get the size of the model containing all the modules in the list.
		labelTotal.setText("Total Modules: " + String.valueOf(helperInstMod.model.size()));
	}

	/**
	 * Gets the full directory (selDir + full top module).
	 * 
	 * Used when we want to get the full directory of the module.
	 * 
	 * @return - the full directory of the module.
	 */
	private ArrayList<String> getFullDir() {
		ArrayList<String> fullDir = new ArrayList<String>();

		// We go through all the modules in the model and connect the selDir and full
		// top module together.
		for (int i = 0; i < helperInstMod.model.size(); i++) {
			PanelInstMod panel = helperInstMod.model.getElementAt(i);

			if (i == 0) {
				// We add one more as a buffer for the top module when configuring the modules
				// (DialogWire).
				fullDir.add(panel.getSelDir() + "\\" + panel.getFullTopMod());
			}
			fullDir.add(panel.getSelDir() + "\\" + panel.getFullTopMod());
		}
		return fullDir;
	}

	/*************
	 * LISTENERS *
	 ************/

	/**
	 * Listener for the model.
	 * 
	 * Used whenever a module is added or removed to update the total Luts and total
	 * number of module labels.
	 */
	private class ModelListListener implements ListDataListener {
		@Override
		public void intervalRemoved(ListDataEvent e) {
			updateLuts();
			updateTotal();
		}

		@Override
		public void intervalAdded(ListDataEvent e) {
			updateLuts();
			updateTotal();
		}

		@Override
		public void contentsChanged(ListDataEvent e) {
			return;
		}
	}

	/**
	 * Listener for the JCheckBox for reordering modules.
	 * 
	 * Used to see if the user is reordering modules.
	 */
	private class CheckBoxReorderListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// When reorder modules is checked, we use the get/set helper to notify
			// the mouse adapter in HelperInstMod.
			if (checkBoxReorder.isSelected() == true) {
				helperGetSet.setInstModReorder(true);
			} else if (checkBoxReorder.isSelected() == false) {
				helperGetSet.setInstModReorder(false);
			}
		}
	}

	/**
	 * Listener for the help button.
	 * 
	 * Used to launch the help dialog.
	 */
	private class ButtonHelpListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			DialogHelp dialogHelp = new DialogHelp();
			dialogHelp.setHelpCard(DialogHelp.getInstModHow());
		}
	}

	/**
	 * Listener for the add button.
	 * 
	 * Used for adding modules to the list.
	 */
	private class ButtonAddListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			DialogAddInstMod dialogAddInstMod = new DialogAddInstMod(getFocusOwner(), "Add a Module",
					ModalityType.APPLICATION_MODAL);

			if (dialogAddInstMod.isClose()) {
				if (helperInstMod.isDuplicate()) {
					helperAlerts.fileDuplicateGeneric();
				} else {
					helperInstMod.addRemovePane(true);
				}
			}
		};
	}

	/**
	 * Listener for the export button.
	 * 
	 * Used to launch the dialog to configure and export the modules.
	 */
	private class ButtonExportListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// We set some values in the helper for initiaing the dialog.
			helperWire.setInstModPanels(helperInstMod.model);
			helperWire.setInstModFullDirs(getFullDir());

			new DialogWire(getFocusOwner(), "Configure & Export Modules", ModalityType.APPLICATION_MODAL);
		};
	}

	/**
	 * Listener for the cancel button.
	 * 
	 * Used to close the dialog.
	 */
	private class ButtonCancelListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (helperAlerts.ynCancel()) {
				dispose();
			}
		};
	}

	/***********
	 * GET/SET *
	 **********/
}
