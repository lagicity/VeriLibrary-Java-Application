package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;
import panels.PanelViewModBasic;
import ui.UIImages;
import helper.HelperClass;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JSeparator;

/**
 * The second tab JPanel which allows the user to compare two modules together.
 * 
 * The user selects the modules to compare via the top view panel and selects
 * the respective button to compare them at. There are visual guidelines in the
 * form of icons to help the user distinguish the better/worse module.
 */
public class TabCompare extends JPanel {
	private static final long serialVersionUID = -266896162346010149L;

	/****************
	 * UI ELEMENTS *
	 ***************/

	// This JPanel is public so it can be initialised from other classes.
	public PanelViewModBasic panelCompareModBasic;

	private JButton buttonCompRHS;
	// This button is public because DialogReplaceMod uses it to disable the button.
	public JButton buttonCompLHS;
	public JButton buttonReset;

	private JPanel panelCompLHS = new JPanel();
	private JPanel panelCompRHS = new JPanel();

	// This JLabel is public because DialogReplaceMod uses it to change the prompt.
	public JLabel labelPrompt;

	// These JLabels reflect the LHS/RHS values.
	private JLabel labelLHSName;
	private JLabel labelLHSOpt;
	private JLabel labelLHSLuts;
	private JLabel labelLHSDataPoints;
	private JLabel labelLHSFreq;
	private JLabel labelLHSLatency;
	private JLabel labelLHSEstClk;

	private JLabel labelRHSName;
	private JLabel labelRHSOpt;
	private JLabel labelRHSLuts;
	private JLabel labelRHSDataPoints;
	private JLabel labelRHSFreq;
	private JLabel labelRHSLatency;
	private JLabel labelRHSEstClk;

	// These JLabels show the up and down arrows.
	private JLabel iconLHSLuts;
	private JLabel iconLHSFreq;
	private JLabel iconLHSLatency;
	private JLabel iconLHSEstClk;
	private JLabel iconLHSDataPoints;
	private JLabel iconRHSEstClk;
	private JLabel iconRHSLatency;
	private JLabel iconRHSFreq;
	private JLabel iconRHSLuts;
	private JLabel iconRHSDataPoints;

	/*************
	 * VARIABLES *
	 *************/

	private String lutsLHS;
	private String estClkLHS;
	private String latencyLHS;
	private String freqLHS;
	private String dataPointsLHS;

	private String lutsRHS;
	private String estClkRHS;
	private String latencyRHS;
	private String freqRHS;
	private String dataPointsRHS;

	// We use an ArrayList<JLabel> to store all the relevant labels so we don't need
	// to repeat code for simple things like setVisible or setFont.
	ArrayList<JLabel> listLHSNames = new ArrayList<JLabel>();
	ArrayList<JLabel> listRHSNames = new ArrayList<JLabel>();

	ArrayList<JLabel> listLHSIcons = new ArrayList<JLabel>();
	ArrayList<JLabel> listRHSIcons = new ArrayList<JLabel>();

	/*************
	 * LISTENERS *
	 ************/

	private ActionListener buttonCompLHSListener = new ButtonCompLHSListener();
	private ActionListener buttonCompRHSListener = new ButtonCompRHSListener();
	private ActionListener buttonRefreshListener = new ButtonRefreshListener();
	private ActionListener buttonResetListener = new ButtonResetListener();

	/***********
	 * HELPERS *
	 ***********/

	private HelperClass helperClass = new HelperClass();

	private UIImages uiImages = new UIImages();

	/***************
	 * CONSTRUCTOR *
	 **************/

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new TabCompare();
			}
		});
	}

	public TabCompare() {
		setupGUI();
		initialise();
	}

	/******************
	 * INITIALISATION *
	 *****************/

	private void setupGUI() {
		// We setup the container to house all the UI elements.
		JPanel container = new JPanel();
		container.setBorder(new TitledBorder(BorderFactory.createTitledBorder(" Select a module to compare ")));
		container.setLayout(new MigLayout("", "[716px]", "[274px][][][175px]"));
		add(container, BorderLayout.CENTER);

		// We setup the main view panel.
		{
			panelCompareModBasic = new PanelViewModBasic();
			panelCompareModBasic.buttonRefresh.addActionListener(buttonRefreshListener);
			container.add(panelCompareModBasic, "cell 0 0,alignx left,aligny top");
		}

		// We setup the prompt panel at the top.
		{
			JPanel panelPrompt = new JPanel();
			panelPrompt.setLayout(new BorderLayout(0, 0));
			container.add(panelPrompt, "cell 0 1,growx");

			labelPrompt = new JLabel("Compare modules by clicking on the side you wish to compare them on.");
			panelPrompt.add(labelPrompt, BorderLayout.WEST);

			buttonReset = new JButton("Reset");
			buttonReset.addActionListener(buttonResetListener);
			panelPrompt.add(buttonReset, BorderLayout.EAST);

			JSeparator separator = new JSeparator();
			panelPrompt.add(separator, BorderLayout.NORTH);
		}

		// We setup the two comparison buttons.
		{
			JPanel panelCompareButton = new JPanel();
			panelCompareButton.setLayout(new MigLayout("", "[][][][][][][][][]", "[]"));
			container.add(panelCompareButton, "flowx,cell 0 2,alignx center,aligny center");

			buttonCompRHS = new JButton("Add to Right Side");
			buttonCompRHS.addActionListener(buttonCompRHSListener);
			panelCompareButton.add(buttonCompRHS, "cell 6 0");

			buttonCompLHS = new JButton("Add to Left Side");
			buttonCompLHS.addActionListener(buttonCompLHSListener);
			panelCompareButton.add(buttonCompLHS, "cell 2 0");
		}

		// We setup the labels of the comparison values.
		// panelCompare houses the JLabel of names, icons and values.
		JPanel panelCompare = new JPanel();
		panelCompare.setLayout(new MigLayout("", "[44px][166px][44px]", "[165px]"));
		container.add(panelCompare, "flowx,cell 0 3,alignx center,aligny center");

		// panelShowCompare houses panelCompare and ensures the elements in it are
		// centre.
		JPanel panelShowCompare = new JPanel();
		container.add(panelShowCompare, "cell 0 3,alignx right,aligny bottom");
		panelShowCompare.setLayout(new BorderLayout(0, 0));
		{
			// We setup the LHS values and the list.
			panelCompLHS.setLayout(new MigLayout("", "[]", "[][][][][][][][]"));
			panelCompare.add(panelCompLHS, "cell 0 0,alignx left,aligny top");

			labelLHSName = new JLabel("name");
			labelLHSOpt = new JLabel("opt");
			labelLHSDataPoints = new JLabel("dp");
			labelLHSLuts = new JLabel("luts");
			labelLHSFreq = new JLabel("freq");
			labelLHSLatency = new JLabel("lat");
			labelLHSEstClk = new JLabel("est clk");

			listLHSNames.add(labelLHSName);
			listLHSNames.add(labelLHSOpt);
			listLHSNames.add(labelLHSDataPoints);
			listLHSNames.add(labelLHSLuts);
			listLHSNames.add(labelLHSFreq);
			listLHSNames.add(labelLHSLatency);
			listLHSNames.add(labelLHSEstClk);

			panelCompLHS.add(labelLHSName, "cell 0 0,alignx right");
			panelCompLHS.add(labelLHSOpt, "cell 0 1,alignx right");
			panelCompLHS.add(labelLHSDataPoints, "cell 0 3,alignx right");
			panelCompLHS.add(labelLHSLuts, "cell 0 7,alignx right");
			panelCompLHS.add(labelLHSFreq, "cell 0 4,alignx right");
			panelCompLHS.add(labelLHSLatency, "cell 0 5,alignx right");
			panelCompLHS.add(labelLHSEstClk, "cell 0 6,alignx right");

		}
		{
			// We setup the RHS values and the list.
			panelCompRHS.setLayout(new MigLayout("", "[]", "[][][][][][][][]"));
			panelCompare.add(panelCompRHS, "cell 2 0,alignx left,aligny top");

			labelRHSName = new JLabel("name");
			labelRHSOpt = new JLabel("opt");
			labelRHSDataPoints = new JLabel("dp");
			labelRHSLuts = new JLabel("luts");
			labelRHSFreq = new JLabel("freq");
			labelRHSLatency = new JLabel("lat");
			labelRHSEstClk = new JLabel("est clk");

			listRHSNames.add(labelRHSName);
			listRHSNames.add(labelRHSOpt);
			listRHSNames.add(labelRHSDataPoints);
			listRHSNames.add(labelRHSLuts);
			listRHSNames.add(labelRHSFreq);
			listRHSNames.add(labelRHSLatency);
			listRHSNames.add(labelRHSEstClk);

			panelCompRHS.add(labelRHSName, "cell 0 0,alignx left");
			panelCompRHS.add(labelRHSOpt, "cell 0 1,alignx left");
			panelCompRHS.add(labelRHSDataPoints, "cell 0 3,alignx left");
			panelCompRHS.add(labelRHSLuts, "cell 0 7,alignx left");
			panelCompRHS.add(labelRHSFreq, "cell 0 4,alignx left");
			panelCompRHS.add(labelRHSLatency, "cell 0 5,alignx left");
			panelCompRHS.add(labelRHSEstClk, "cell 0 6,alignx left,aligny bottom");
		}

		// We setup the JLabel of names and icons between the LHS and RHS panels.
		{
			// We setup the JLabel of names.
			JPanel panelNames = new JPanel();
			panelNames.setLayout(new MigLayout("", "[20.00][][20.00]", "[][][][][][][][]"));
			panelCompare.add(panelNames, "cell 1 0,alignx left,aligny top");

			JLabel labelName = new JLabel("Module Name");
			JLabel labelOpt = new JLabel("Optimisation");
			JLabel labelDataPoints = new JLabel("Data Points");
			JLabel labelLuts = new JLabel("LUTs");
			JLabel labelFreq = new JLabel("Frequency (MHz)");
			JLabel labelLatency = new JLabel("Latency (clock cycles)");
			JLabel labelEstClk = new JLabel("Estimated Clock (ns)");

			panelNames.add(labelName, "cell 1 0,alignx center");
			panelNames.add(labelOpt, "cell 1 1,alignx center");
			panelNames.add(labelDataPoints, "cell 1 3,alignx center");
			panelNames.add(labelLuts, "cell 1 7,alignx center");
			panelNames.add(labelFreq, "cell 1 4,alignx center");
			panelNames.add(labelLatency, "cell 1 5,alignx center");
			panelNames.add(labelEstClk, "cell 1 6,alignx center");

			// We setup the LHS and RHS icons and the list.
			iconLHSDataPoints = new JLabel();
			iconLHSLuts = new JLabel();
			iconLHSFreq = new JLabel();
			iconLHSEstClk = new JLabel();
			iconLHSLatency = new JLabel();

			listLHSIcons.add(iconLHSDataPoints);
			listLHSIcons.add(iconLHSLuts);
			listLHSIcons.add(iconLHSFreq);
			listLHSIcons.add(iconLHSEstClk);
			listLHSIcons.add(iconLHSLatency);

			panelNames.add(iconLHSDataPoints, "cell 0 3");
			panelNames.add(iconLHSLuts, "cell 0 7");
			panelNames.add(iconLHSFreq, "cell 0 4");
			panelNames.add(iconLHSLatency, "cell 0 5");
			panelNames.add(iconLHSEstClk, "cell 0 6");

			iconRHSDataPoints = new JLabel();
			iconRHSLuts = new JLabel();
			iconRHSFreq = new JLabel();
			iconRHSLatency = new JLabel();
			iconRHSEstClk = new JLabel();

			listRHSIcons.add(iconRHSDataPoints);
			listRHSIcons.add(iconRHSLuts);
			listRHSIcons.add(iconRHSFreq);
			listRHSIcons.add(iconRHSEstClk);
			listRHSIcons.add(iconRHSLatency);

			panelNames.add(iconRHSDataPoints, "cell 2 3,alignx left");
			panelNames.add(iconRHSLuts, "cell 2 7");
			panelNames.add(iconRHSFreq, "cell 2 4");
			panelNames.add(iconRHSLatency, "cell 2 5");
			panelNames.add(iconRHSEstClk, "cell 2 6");
		}
	}

	/***********
	 * METHODS *
	 **********/

	/**
	 * Initialises the whole panel by loading the values and icons.
	 * 
	 * It is public as we sometimes need to initialise it manually (ie. in
	 * MainView).
	 */
	public void initialise() {
		setTextLHS();
		setTextRHS();

		processTextLHS();
		processTextRHS();
	}

	/**
	 * Processes the LHS values.
	 * 
	 * It shows the labels and icons and compares them to display the arrows.
	 */
	public void processTextLHS() {
		setLabelsVisible(listLHSNames, true);
		setLabelsVisible(listLHSIcons, true);

		setTextLHS();

		compareData(lutsLHS, lutsRHS, labelLHSLuts, labelRHSLuts, iconLHSLuts, iconRHSLuts);
		compareData(dataPointsLHS, dataPointsRHS, labelLHSDataPoints, labelRHSDataPoints, iconLHSDataPoints,
				iconRHSDataPoints);
		compareData(freqLHS, freqRHS, labelLHSFreq, labelRHSFreq, iconLHSFreq, iconRHSFreq);
		compareData(latencyLHS, latencyRHS, labelLHSLatency, labelRHSLatency, iconLHSLatency, iconRHSLatency);
		compareData(estClkLHS, estClkRHS, labelLHSEstClk, labelRHSEstClk, iconLHSEstClk, iconRHSEstClk);
	}

	/**
	 * Processes the RHS values.
	 * 
	 * It shows the labels and icons and compares them to display the arrows.
	 */
	public void processTextRHS() {
		setLabelsVisible(listRHSNames, true);
		setLabelsVisible(listRHSIcons, true);

		setTextRHS();

		compareData(lutsLHS, lutsRHS, labelLHSLuts, labelRHSLuts, iconLHSLuts, iconRHSLuts);
		compareData(dataPointsLHS, dataPointsRHS, labelLHSDataPoints, labelRHSDataPoints, iconLHSDataPoints,
				iconRHSDataPoints);
		compareData(freqLHS, freqRHS, labelLHSFreq, labelRHSFreq, iconLHSFreq, iconRHSFreq);
		compareData(latencyLHS, latencyRHS, labelLHSLatency, labelRHSLatency, iconLHSLatency, iconRHSLatency);
		compareData(estClkLHS, estClkRHS, labelLHSEstClk, labelRHSEstClk, iconLHSEstClk, iconRHSEstClk);
	}

	/**
	 * Sets the LHS values.
	 * 
	 * It parses the module selected in the view JPanel and displays their values.
	 */
	public void setTextLHS() {
		lutsLHS = panelCompareModBasic.labelShowLuts.getText();
		dataPointsLHS = helperClass.removeUnits(panelCompareModBasic.getSelDataPoints());
		freqLHS = helperClass.removeUnits(panelCompareModBasic.getSelFreq());
		latencyLHS = panelCompareModBasic.labelShowLatency.getText();
		estClkLHS = panelCompareModBasic.labelShowEstClk.getText();

		labelLHSName.setText(panelCompareModBasic.getSelName());
		labelLHSOpt.setText(panelCompareModBasic.getSelOpt());
		labelLHSLuts.setText(lutsLHS);
		labelLHSDataPoints.setText(dataPointsLHS);
		labelLHSFreq.setText(freqLHS);
		labelLHSLatency.setText(latencyLHS);
		labelLHSEstClk.setText(estClkLHS);
	}

	/**
	 * Sets the RHS values.
	 * 
	 * It parses the module selected in the view JPanel and displays their values.
	 */
	public void setTextRHS() {
		lutsRHS = panelCompareModBasic.labelShowLuts.getText();
		dataPointsRHS = helperClass.removeUnits(panelCompareModBasic.getSelDataPoints());
		freqRHS = helperClass.removeUnits(panelCompareModBasic.getSelFreq());
		latencyRHS = panelCompareModBasic.labelShowLatency.getText();
		estClkRHS = panelCompareModBasic.labelShowEstClk.getText();

		labelRHSName.setText(panelCompareModBasic.getSelName());
		labelRHSOpt.setText(panelCompareModBasic.getSelOpt());
		labelRHSLuts.setText(lutsRHS);
		labelRHSDataPoints.setText(dataPointsRHS);
		labelRHSFreq.setText(freqRHS);
		labelRHSLatency.setText(latencyRHS);
		labelRHSEstClk.setText(estClkRHS);
	}

	/**
	 * Removes the LHS values.
	 * 
	 * It hides the icons and labels and resets the font.
	 */
	private void removeTextLHS() {
		setLabelsVisible(listLHSNames, false);
		setLabelsVisible(listLHSIcons, false);

		setLabelsFont(listLHSNames, new Font(getFont().toString(), Font.PLAIN, 12));
	}

	/**
	 * Removes the RHS values.
	 * 
	 * It hides the icons and labels and resets the font.
	 */
	private void removeTextRHS() {
		setLabelsVisible(listRHSNames, false);
		setLabelsVisible(listRHSIcons, false);

		setLabelsFont(listRHSNames, new Font(getFont().toString(), Font.PLAIN, 12));
	}

	/**
	 * Compares the values parsed and sets the respective icon and font.
	 * 
	 * @param dataLHS  - the value of the LHS name JLabel.
	 * @param dataRHS  - the value of the RHS name JLabel.
	 * @param labelLHS - the LHS name JLabel.
	 * @param labelRHS - the RHS name JLabel.
	 * @param iconLHS  - the LHS icon JLabel.
	 * @param iconRHS  - the RHS icon JLabel.
	 */
	private void compareData(String dataLHS, String dataRHS, JLabel labelLHS, JLabel labelRHS, JLabel iconLHS,
			JLabel iconRHS) {
		// We get the value from the LHS and RHS JLabels. We use double as we might have
		// to deal with the decimal point.
		double intLHS = Double.parseDouble(dataLHS);
		double intRHS = Double.parseDouble(dataRHS);

		// We compare the values and set the respective icon and font.
		if (intLHS > intRHS) {
			// If the LHS > RHS.
			showHigher(iconLHS, labelLHS);
			showLower(iconRHS, labelRHS);
		} else if (intLHS < intRHS) {
			// Else if the LHS < RHS.
			showLower(iconLHS, labelLHS);
			showHigher(iconRHS, labelRHS);
		} else {
			// Else, they are equal.
			showEqual(iconLHS, labelLHS);
			showEqual(iconRHS, labelRHS);
		}
	}

	/**
	 * A for loop to set the font of each label in the ArrayList<JLabel>.
	 * 
	 * Used when we want to set the font back to the default font.
	 * 
	 * @param labels - the labels in the ArrayList<JLabel>.
	 * @param font   - the font we want to set.
	 */
	private void setLabelsFont(ArrayList<JLabel> labels, Font font) {
		for (int i = 0; i < labels.size(); i++) {
			labels.get(i).setFont(font);
		}
	}

	/**
	 * A for loop to set the visibility of each label in the ArrayList<JLabel>.
	 * 
	 * Used when we want to set the visibility of the JLabel.
	 * 
	 * @param labels  - the labels in the ArrayList<JLabel>.
	 * @param visible - whether we want to set visible or not.
	 */
	private void setLabelsVisible(ArrayList<JLabel> labels, boolean visible) {
		for (int i = 0; i < labels.size(); i++) {
			labels.get(i).setVisible(visible);
		}
	}

	/**
	 * Sets the JLabel icon to a down arrow and makes its font default.
	 * 
	 * Used when we want to show the lower value.
	 * 
	 * @param icon  - the icon we are setting (down arrow).
	 * @param label - the label we are setting the icon on.
	 */
	private void showLower(JLabel icon, JLabel label) {
		icon.setIcon(new ImageIcon(uiImages.getDownArrowRed()));
		label.setFont(new Font(getFont().toString(), Font.PLAIN, 12));
	}

	/**
	 * Sets the JLabel icon to a up arrow and makes its font bold.
	 * 
	 * Used when we want to show the higher value.
	 * 
	 * @param icon  - the icon we are setting (up arrow).
	 * @param label - the label we are setting the icon on.
	 */
	private void showHigher(JLabel icon, JLabel label) {
		icon.setIcon(new ImageIcon(uiImages.getUpArrowGreen()));
		label.setFont(new Font(getFont().toString(), Font.BOLD, 12));
	}

	/**
	 * Sets the JLabel icon to an equals sign and makes its font default.
	 * 
	 * Used when we want to show the the values are the same.
	 * 
	 * @param icon  - the icon we are setting (equals sign).
	 * @param label - the label we are setting the icon on.
	 */
	private void showEqual(JLabel icon, JLabel label) {
		icon.setIcon(new ImageIcon(uiImages.getHorizontalArrow()));
		label.setFont(new Font(getFont().toString(), Font.PLAIN, 12));
	}

	/*************
	 * LISTENERS *
	 ************/

	/**
	 * Refreshes the main view panel.
	 */
	private class ButtonRefreshListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			initialise();
		}
	}

	/**
	 * Adds the selected module to the LHS.
	 */
	private class ButtonCompLHSListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			processTextLHS();
		}
	}

	/**
	 * Adds the selected module to the RHS.
	 */
	private class ButtonCompRHSListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			processTextRHS();
		}
	}

	/**
	 * Resets LHS and RHS by setting their visibility to false.
	 */
	private class ButtonResetListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			removeTextLHS();
			removeTextRHS();
		}
	}

	/***********
	 * GET/SET *
	 **********/
}