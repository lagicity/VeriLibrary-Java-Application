package instmod;

import java.awt.EventQueue;

import javax.swing.JLabel;
import javax.swing.JPanel;

import helper.HelperClass;
import helper.HelperGetSet;
import net.miginfocom.swing.MigLayout;
import javax.swing.border.TitledBorder;

/**
 * A template to display the modules that are added the list.
 * 
 * Every time the template is used to create a new object (module to display on
 * the list), it gets the data of the module, then parses and displays it.
 */
public class PanelInstMod extends JPanel {
	private static final long serialVersionUID = 7552597846879931915L;

	/****************
	 * UI ELEMENTS *
	 ***************/

	private JLabel labelShowTopMod;
	private JLabel labelShowName;
	private JLabel labelShowOpt;
	private JLabel labelShowFreq;
	private JLabel labelShowDataPoints;
	private JLabel labelShowLatency;
	private JLabel labelShowEstClk;
	private JLabel labelShowLuts;

	/*************
	 * VARIABLES *
	 *************/

	// Values for get/set methods.
	private String selDir;
	private String name;
	private String opt;
	private String freq;
	private String dataPoints;
	private String latency;
	private String estClk;
	private String luts;
	private String topMod;
	private String fullTopMod;

	/*************
	 * LISTENERS *
	 ************/

	/***********
	 * HELPERS *
	 ***********/

	private HelperClass helperClass = new HelperClass();
	private HelperGetSet helperGetSet = new HelperGetSet();

	/***************
	 * CONSTRUCTOR *
	 **************/

	public PanelInstMod() {
		setupUI();
		initialise();
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new PanelInstMod();
			}
		});
	}


	/******************
	 * INITIALISATION *
	 *****************/

	private void setupUI() {
		setBorder(new TitledBorder(""));
		setLayout(new MigLayout("", "[][:125.00:125px][][]", "[][][20.00][][][pref!][11.00]"));

		// Setup the placeholder labels.
		{
			JLabel labelModuleName = new JLabel("Module Name");
			JLabel labelTopMod = new JLabel("Top Level Module");
			JLabel labelOpt = new JLabel("Optimisation");
			JLabel labelFreq = new JLabel("Frequency (MHz)");
			JLabel labelDataPoints = new JLabel("Data Points");
			JLabel labelLatency = new JLabel("Latency (clock cycles)");
			JLabel labelEstClk = new JLabel("Estimated Clock (ns)");
			JLabel labelLuts = new JLabel("LUTs");

			add(labelModuleName, "cell 0 0");
			add(labelTopMod, "cell 2 0");
			add(labelOpt, "cell 0 1");
			add(labelFreq, "cell 0 3");
			add(labelDataPoints, "cell 2 3");
			add(labelLatency, "cell 0 4");
			add(labelEstClk, "cell 2 4");
			add(labelLuts, "cell 0 5");
		}

		// Setup the labels that show the value.
		{
			labelShowTopMod = new JLabel("top mod");
			labelShowOpt = new JLabel("opt");
			labelShowName = new JLabel("name");
			labelShowFreq = new JLabel("freq");
			labelShowDataPoints = new JLabel("data points");
			labelShowLatency = new JLabel("latency");
			labelShowEstClk = new JLabel("est clk");
			labelShowLuts = new JLabel("luts");

			add(labelShowTopMod, "flowy,cell 3 0");
			add(labelShowOpt, "cell 1 1,alignx left");
			add(labelShowName, "cell 1 0");
			add(labelShowFreq, "cell 1 3");
			add(labelShowDataPoints, "cell 3 3");
			add(labelShowLatency, "cell 1 4");
			add(labelShowEstClk, "cell 3 4");
			add(labelShowLuts, "cell 1 5");
		}
	}

	/***********
	 * METHODS *
	 **********/

	/**
	 * Initialise the template panel with values.
	 * 
	 * Used when you added a new module to the list.
	 */
	private void initialise() {
		// Get and set the selDir and full top module to begin initialisation.
		String selDir = helperGetSet.getSelDir();
		String fullTopMod = helperGetSet.getFullTopMod();

		setSelDir(selDir);
		setFullTopMod(fullTopMod);

		// We parse the file name and set the respective parts.
		setTopMod(helperClass.getParsedFileName(fullTopMod, 3));
		setLuts(helperClass.getParsedFileName(fullTopMod, 2));
		setEstClk(helperClass.getParsedFileName(fullTopMod, 1));
		setLatency(helperClass.getParsedFileName(fullTopMod, 0));

		// We parse the file path and set the respective parts. The index is -1 as we
		// are passing selDir and not fullDir (there is no file name).
		setDataPoints(helperClass.getParsedFilePath(selDir, 0));
		setFreq(helperClass.getParsedFilePath(selDir, 1));
		setOpt(helperClass.getParsedFilePath(selDir, 2));
		setName(helperClass.getParsedFilePath(selDir, 3));

		// We set the text to the labels.
		labelShowTopMod.setText(getTopMod());
		labelShowLuts.setText(getLuts());
		labelShowEstClk.setText(getEstClk());
		labelShowLatency.setText(getLatency());

		labelShowDataPoints.setText(getDataPoints());
		labelShowFreq.setText(getFreq());
		labelShowOpt.setText(getOpt());
		labelShowName.setText(getName());
	}

	/*************
	 * LISTENERS *
	 ************/

	/***********
	 * GET/SET *
	 **********/

	public String getSelDir() {
		return selDir;
	}

	public void setSelDir(String selDir) {
		this.selDir = selDir;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOpt() {
		return opt;
	}

	public void setOpt(String opt) {
		this.opt = opt;
	}

	public String getFreq() {
		return freq;
	}

	public void setFreq(String freq) {
		this.freq = freq;
	}

	public String getDataPoints() {
		return dataPoints;
	}

	public void setDataPoints(String dataPoints) {
		this.dataPoints = dataPoints;
	}

	public String getLatency() {
		return latency;
	}

	public void setLatency(String latency) {
		this.latency = latency;
	}

	public String getEstClk() {
		return estClk;
	}

	public void setEstClk(String estClk) {
		this.estClk = estClk;
	}

	public String getLuts() {
		return luts;
	}

	public void setLuts(String luts) {
		this.luts = luts;
	}

	public String getTopMod() {
		return topMod;
	}

	public void setTopMod(String topMod) {
		this.topMod = topMod;
	}

	public String getFullTopMod() {
		return fullTopMod;
	}

	public void setFullTopMod(String fullTopMod) {
		this.fullTopMod = fullTopMod;
	}
}