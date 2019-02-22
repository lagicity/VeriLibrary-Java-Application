package panels;

import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import dialog.DialogSelTopMod;
import help.DialogHelp;
import helper.HelperAlerts;
import helper.HelperClass;
import helper.HelperGetSet;
import ui.UIElements;

/**
 * The main view panel which shows all the modules in the form of the
 * directories that it is saved in and their Verilog files.
 * 
 * The user can select any list which shows the directories under that list. The
 * top module is automatically detected as the shortest length .v file. However,
 * the user can manually select their own top module if the detection is off
 * 
 * Every time the list is clicked, it sets the the selected directory and
 * (estimated) top module in HelperGetSet for usage by other classes.
 */
public class PanelViewMod extends JPanel {
	private static final long serialVersionUID = -4438405598070797161L;

	/****************
	 * UI ELEMENTS *
	 ***************/

	// We create 4 objects for the 4 lists.
	private PanelViewModList panelName;
	private PanelViewModList panelOpt;
	private PanelViewModList panelFreq;
	private PanelViewModList panelDataPoints;

	private JLabel labelShowLatency;
	private JLabel labelShowEstClk;
	private JLabel labelShowLuts;
	private JLabel labelShowTopMod;

	private JButton buttonSelTopMod;
	private JButton buttonRefresh;
	private JButton buttonHelp;

	/*************
	 * VARIABLES *
	 *************/

	private String dirName;
	private String dirOpt;
	private String dirFreq;
	private String dirDataPoints;

	private String selName;
	private String selOpt;
	private String selFreq;
	private String selDataPoints;

	private String selDir;
	private String fullTopMod;

	/*************
	 * LISTENERS *
	 ************/

	private ActionListener buttonSelTopModListener = new ButtonSelTopModListener();
	private ActionListener buttonRefreshListener = new ButtonRefreshListener();
	private ActionListener buttonHelpListener = new ButtonHelpListener();

	private ListSelectionListener listNameListener = new ListNameListener();
	private ListSelectionListener listOptListener = new ListOptListener();
	private ListSelectionListener listFreqListener = new ListFreqListener();
	private ListSelectionListener listDataPointsListener = new ListDataPointsListener();

	/***********
	 * HELPERS *
	 ***********/

	private HelperGetSet helperGetSet = new HelperGetSet();
	private HelperClass helperClass = new HelperClass();
	private HelperAlerts helperAlerts = new HelperAlerts();
	private UIElements uiElements = new UIElements();

	/***************
	 * CONSTRUCTOR *
	 **************/

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new PanelViewMod();
			}
		});
	}

	public PanelViewMod() {
		// Initialise the UI
		setupUI();
		initialise();
	}

	/******************
	 * INITIALISATION *
	 *****************/

	private void setupUI() {
		setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("center:max(150px;min):grow"),
						FormSpecs.LABEL_COMPONENT_GAP_COLSPEC, FormSpecs.PREF_COLSPEC,
						FormSpecs.LABEL_COMPONENT_GAP_COLSPEC, ColumnSpec.decode("center:max(150px;min):grow"),
						FormSpecs.LABEL_COMPONENT_GAP_COLSPEC, FormSpecs.PREF_COLSPEC,
						FormSpecs.LABEL_COMPONENT_GAP_COLSPEC, ColumnSpec.decode("center:max(150px;min)"),
						FormSpecs.LABEL_COMPONENT_GAP_COLSPEC, FormSpecs.PREF_COLSPEC,
						FormSpecs.LABEL_COMPONENT_GAP_COLSPEC, ColumnSpec.decode("right:max(90px;default)"),
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.BUTTON_COLSPEC, ColumnSpec.decode("2dlu"),
						ColumnSpec.decode("2dlu"), },
				new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, RowSpec.decode("pref:grow"),
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, }));

		// We setup the basic JLabels.
		{
			JLabel arrow1 = new JLabel(">");
			JLabel arrow2 = new JLabel(">");
			JLabel arrow3 = new JLabel(">");

			add(arrow1, "4, 6, left, center");
			add(arrow2, "8, 6, left, center");
			add(arrow3, "12, 6, left, center");

			JLabel labelNameMod = new JLabel("Module Name");
			JLabel labelNameOpt = new JLabel("Optimisation");
			JLabel labelNameFreq = new JLabel("Frequency");
			JLabel labelNameDataPoints = new JLabel("Data Points");

			add(labelNameMod, "2, 4");
			add(labelNameOpt, "6, 4");
			add(labelNameFreq, "10, 4");
			add(labelNameDataPoints, "14, 4, 3, 1, center, default");

			JLabel labelName = new JLabel("Latency (clock cycles)");
			JLabel labelEstClk = new JLabel("Estimated Clock (ns)");
			JLabel labelLuts = new JLabel("LUTs");
			JLabel labelTopMod = new JLabel("Top Level Module");

			add(labelName, "2, 8, center, default");
			add(labelEstClk, "6, 8, center, default");
			add(labelLuts, "10, 8, center, default");
			add(labelTopMod, "14, 8, 3, 1, center, default");
		}

		// We setup the buttons and panels.
		{
			buttonRefresh = uiElements.buttonRefresh();
			buttonRefresh.addActionListener(buttonRefreshListener);
			add(buttonRefresh, "16, 2, right, default");

			buttonHelp = uiElements.buttonHelp();
			buttonHelp.addActionListener(buttonHelpListener);
			add(buttonHelp, "14, 2, right, default");

			buttonSelTopMod = new JButton("Change Top Module");
			buttonSelTopMod.addActionListener(buttonSelTopModListener);
			add(buttonSelTopMod, "14, 12, 3, 1");

			panelName = new PanelViewModList();
			panelName.list.setVisibleRowCount(15);
			panelOpt = new PanelViewModList();
			panelOpt.list.setVisibleRowCount(15);
			panelFreq = new PanelViewModList();
			panelFreq.list.setVisibleRowCount(15);
			panelDataPoints = new PanelViewModList();
			panelDataPoints.list.setVisibleRowCount(15);

			add(panelName, "2, 6, fill, fill");
			add(panelOpt, "6, 6, fill, fill");
			add(panelFreq, "10, 6, fill, fill");
			add(panelDataPoints, "14, 6, 3, 1, fill, fill");

			labelShowLatency = new JLabel("lat");
			labelShowEstClk = new JLabel("clk");
			labelShowLuts = new JLabel("lut");
			labelShowTopMod = new JLabel("mod");

			add(labelShowLatency, "2, 10, center, default");
			add(labelShowEstClk, "6, 10, center, default");
			add(labelShowLuts, "10, 10, center, default");
			add(labelShowTopMod, "14, 10, 3, 1, center, default");
		}
	}

	/***********
	 * METHODS *
	 **********/

	/**
	 * Initialise the panels.
	 */
	public void initialise() {
		setupPanel(0);
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
		//
		// We don't need a try-catch to look for null pointers because that is handled
		// in the PanelViewModList class.

		switch (panel) {
		// We are setting up the module name panel.
		case 0:
			panelName.setupListDir(startDir);

			panelName.list.addListSelectionListener(listNameListener);
			setSelName(panelName.list.getSelectedValue());

			setupPanel(1);
			break;

		// We are setting up the optimisation panel.
		case 1:
			dirName = startDir + "\\" + getSelName();

			panelOpt.setupListDir(dirName);
			panelOpt.list.addListSelectionListener(listOptListener);

			setSelOpt(panelOpt.list.getSelectedValue());

			setupPanel(2);
			break;

		// We are setting up the frequency panel.
		case 2:
			dirOpt = dirName + "\\" + getSelOpt();

			panelFreq.setupListDir(dirOpt);
			panelFreq.list.addListSelectionListener(listFreqListener);

			setSelFreq(panelFreq.list.getSelectedValue());

			setupPanel(3);
			break;

		// We are setting up the data points panel.
		case 3:
			// dp panel
			dirFreq = dirOpt + "\\" + getSelFreq();

			panelDataPoints.setupListDir(dirFreq);
			panelDataPoints.list.addListSelectionListener(listDataPointsListener);

			setSelDataPoints(panelDataPoints.list.getSelectedValue());

			setupPanel(4);
			break;

		// We are not setting any panel, but configuring our values.
		case 4:
			// If we have no Verilog files, we set the JLabels to show "NO DATA"
			if (getSelDataPoints() == null) {
				labelShowLatency.setText("NO DATA");
				labelShowEstClk.setText("NO DATA");
				labelShowLuts.setText("NO DATA");
				labelShowTopMod.setText("NO DATA");
			} else {
				// We have files to display, so let's configure everything.
				dirDataPoints = dirFreq + "\\" + panelDataPoints.list.getSelectedValue();

				// We set the selected directory and top module.
				// We have a try-catch in case we have Verilog files, but they do not follow the
				// 1_2_3_ format (foreign Verilog files). This can lead to problems when
				// splitting them and reaise an ArrayOutOfBounds exception.
				setSelDir(dirDataPoints);
				helperGetSet.setSelDir(dirDataPoints);
				try {
					setFullTopMod(helperClass.getEstTopMod(dirDataPoints));
					helperGetSet.setFullTopMod(helperClass.getEstTopMod(dirDataPoints));

					setupText();
				} catch (ArrayIndexOutOfBoundsException e) {
					helperAlerts.fileGeneralIncompat(getSelDir());
					initialise();
				}
			}
			break;
		default:
			System.out.println("Wrong choice selected @ PanelViewModv2");
		}
	}

	/**
	 * Sets up the values of the JLabels at the bottom.
	 */
	private void setupText() {
		// We get 1 Verilog file.
		File file = new File(getSelDir());
		File[] fileNames = file.listFiles(File::isFile);

		// We only need 1 file as the rest have the same format.
		String name = fileNames[0].getName();
		labelShowLatency.setText(helperClass.getParsedFileName(name, 0));
		labelShowEstClk.setText(helperClass.getParsedFileName(name, 1));
		labelShowLuts.setText(helperClass.getParsedFileName(name, 2));
		labelShowTopMod.setText(helperClass.getParsedFileName(getFullTopMod(), 3));
	}

	/*************
	 * LISTENERS *
	 ************/

	/**
	 * Initialises the view panel lists.
	 */
	private class ButtonRefreshListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			initialise();
		}
	}

	/**
	 * Launches the help dialog.
	 */
	private class ButtonHelpListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			DialogHelp dialogHelp = new DialogHelp();
			dialogHelp.setHelpCard(DialogHelp.getViewFileEmpty());
		}
	}

	/**
	 * Launches the dialog to select the top module.
	 */
	private class ButtonSelTopModListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			DialogSelTopMod dialogSelTopMod = new DialogSelTopMod(getTopLevelAncestor(), "Select a Top Module",
					ModalityType.APPLICATION_MODAL);

			if (dialogSelTopMod.isClose()) {
				labelShowTopMod.setText(helperClass.getParsedFileName(helperGetSet.getFullTopMod(), 3));
			}
		}
	}

	/**
	 * Listens to the index that is clicked for the data points list.
	 */
	private class ListDataPointsListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			setupPanel(4);

			setSelDataPoints(panelDataPoints.list.getSelectedValue());
		}
	}

	/**
	 * Listens to the index that is clicked for the frequency list.
	 */
	private class ListFreqListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			setupPanel(3);

			setSelFreq(panelFreq.list.getSelectedValue());
		}
	}

	/**
	 * Listens to the index that is clicked for the optimisation list.
	 */
	private class ListOptListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			setupPanel(2);

			setSelOpt(panelOpt.list.getSelectedValue());
		}
	}

	/**
	 * Listens to the index that is clicked for the module name list.
	 */
	private class ListNameListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			setupPanel(1);

			setSelName(panelName.list.getSelectedValue());
		}
	}

	/***********
	 * GET/SET *
	 **********/

	public String getSelName() {
		return selName;
	}

	private void setSelName(String selName) {
		this.selName = selName;
	}

	public String getSelOpt() {
		return selOpt;
	}

	private void setSelOpt(String selOpt) {
		this.selOpt = selOpt;
	}

	public String getSelFreq() {
		return selFreq;
	}

	private void setSelFreq(String selFreq) {
		this.selFreq = selFreq;
	}

	public String getSelDataPoints() {
		return selDataPoints;
	}

	private void setSelDataPoints(String selDataPoints) {
		this.selDataPoints = selDataPoints;
	}

	public String getSelDir() {
		return selDir;
	}

	private void setSelDir(String selDir) {
		this.selDir = selDir;
	}

	public String getFullTopMod() {
		return fullTopMod;
	}

	private void setFullTopMod(String fullTopMod) {
		this.fullTopMod = fullTopMod;
	}
}
