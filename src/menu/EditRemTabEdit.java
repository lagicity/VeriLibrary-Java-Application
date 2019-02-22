package menu;

import java.awt.EventQueue;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import helper.HelperClass;
import helper.HelperFields;
import helper.HelperGetSet;
import helper.HelperIO;
import net.miginfocom.swing.MigLayout;
import panels.PanelViewModBasic;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.ActionEvent;

/**
 * Allows the editing of any file or directory within VeriLibrary.
 * 
 * The "old" files are moved to the "new" files by generating the "new" files'
 * path and directory via the inputs the user entered.
 * 
 * The empty directory that is left when the files is deleted, and so is any
 * empty directory above it (parent).
 */
public class EditRemTabEdit extends JPanel {
	private static final long serialVersionUID = 8627015569286986297L;

	/****************
	 * UI ELEMENTS *
	 ***************/

	public PanelViewModBasic panelViewModBasic;

	public JTextField inputName;
	public JTextField inputFreq;
	public JTextField inputDataPoints;
	public JTextField inputLatency;
	public JTextField inputLuts;
	public JTextField inputEstClk;
	public JTextField inputOpt;

	/*************
	 * VARIABLES *
	 *************/

	private String origName;
	private String origOpt;
	private String origFreq;
	private String origDataPoints;
	private String origLatency;
	private String origLuts;
	private String origEstClk;

	private String newName;
	private String newOpt;
	private String newFreq;
	private String newDataPoints;
	private String newLatency;
	private String newLuts;
	private String newEstClk;

	/*************
	 * LISTENERS *
	 ************/

	private ActionListener buttonResetListener = new ButtonResetListener();

	// This listener is invoked whenever another module is clicked in the view panel
	// to update the JTextFields with the module that was clicked.
	private PropertyChangeListener labelSetTextListener = new LabelSetTextListener();

	/***********
	 * HELPERS *
	 ***********/

	private HelperIO helperIO = new HelperIO();
	private HelperGetSet helperGetSet = new HelperGetSet();
	private HelperClass helperClass = new HelperClass();
	private HelperFields helperFields = new HelperFields();

	/***************
	 * CONSTRUCTOR *
	 **************/

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new EditRemTabEdit();
			}
		});
	}

	public EditRemTabEdit() {
		setupUI();
		setupText();
	}

	/******************
	 * INITIALISATION *
	 *****************/

	private void setupUI() {
		setLayout(new MigLayout("", "[544.00px]", "[140.00px][58px,fill]"));

		// We setup the basic view panel.
		{
			panelViewModBasic = new PanelViewModBasic();
			add(panelViewModBasic, "cell 0 0,alignx center,growy");
		}

		// We setup the edit panel containing the input JTextFields.
		JPanel panelEdit = new JPanel();
		add(panelEdit, "cell 0 1,alignx center,aligny center");
		panelEdit.setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("120dlu"), FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, },
				new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, RowSpec.decode("max(11dlu;default)"),
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, }));

		{
			// We setup basic generic JLabels.
			JLabel labelPromptChange = new JLabel("Change");
			JLabel labelPromptTo = new JLabel("To");
			JLabel labelPromptName = new JLabel("Module Name");
			JLabel labelPromptOpt = new JLabel("Optimisation");

			panelEdit.add(labelPromptChange, "12, 2, center, default");
			panelEdit.add(labelPromptTo, "16, 2");
			panelEdit.add(labelPromptName, "14, 4, center, default");
			panelEdit.add(labelPromptOpt, "14, 6, center, default");

			JLabel labelPromptChange2 = new JLabel("Change");
			JLabel labelPromptTo2 = new JLabel("To");

			panelEdit.add(labelPromptChange2, "12, 10");
			panelEdit.add(labelPromptTo2, "16, 10");

			JLabel labelPromptFreq = new JLabel("Frequency (MHz)");
			JLabel labelPromptDataPoints = new JLabel("Data Points");
			JLabel labelPromptLatency = new JLabel("Latency (clock cycles)");
			JLabel labelPromptEstClk = new JLabel("Estimated Clock (ns)");
			JLabel labelPromptLuts = new JLabel("LUTs");

			panelEdit.add(labelPromptFreq, "14, 14, center, default");
			panelEdit.add(labelPromptDataPoints, "14, 12, center, default");
			panelEdit.add(labelPromptLatency, "14, 16, center, default");
			panelEdit.add(labelPromptEstClk, "14, 18, center, default");
			panelEdit.add(labelPromptLuts, "14, 20, center, default");

			// We setup the warning to notify the user that text fields must be filled in.
			String warning = helperGetSet.getWarningName();

			JLabel labelstar1 = new JLabel(warning);
			JLabel labelstar2 = new JLabel(warning);
			JLabel labelstar3 = new JLabel(warning);
			JLabel labelstar4 = new JLabel(warning);
			JLabel labelstar5 = new JLabel(warning);
			JLabel labelstar6 = new JLabel(warning);
			JLabel labelstar7 = new JLabel(warning);

			panelEdit.add(labelstar1, "22, 4");
			panelEdit.add(labelstar2, "20, 12");
			panelEdit.add(labelstar3, "20, 14");
			panelEdit.add(labelstar4, "20, 16");
			panelEdit.add(labelstar5, "20, 18");
			panelEdit.add(labelstar6, "20, 20");
			panelEdit.add(labelstar7, "22, 6");
		}

		// We setup the JTextFields.
		{
			JLabel labelError = helperFields.createErrorLabel();
			panelEdit.add(labelError, "14, 22, 8, 1");

			int nameMaxLength = helperGetSet.getInputNameMaxLength();
			int intMaxLength = helperGetSet.getInputIntMaxLength();

			inputName = helperFields.createTextField(nameMaxLength, labelError, 1);
			inputOpt = helperFields.createTextField(nameMaxLength, labelError, 1);
			inputFreq = helperFields.createTextField(intMaxLength, labelError, 0);
			inputDataPoints = helperFields.createTextField(intMaxLength, labelError, 0);
			inputLatency = helperFields.createTextField(intMaxLength, labelError, 0);
			inputEstClk = helperFields.createTextField(intMaxLength, labelError, 0);
			inputLuts = helperFields.createTextField(intMaxLength, labelError, 0);

			panelEdit.add(inputName, "18, 4, 3, 1, fill, default");
			panelEdit.add(inputOpt, "18, 6, 3, 1, fill, default");
			panelEdit.add(inputFreq, "18, 14, fill, default");
			panelEdit.add(inputDataPoints, "18, 12, fill, default");
			panelEdit.add(inputLatency, "18, 16, fill, default");
			panelEdit.add(inputEstClk, "18, 18, fill, default");
			panelEdit.add(inputLuts, "18, 20, fill, default");

			// We setup the reset button.
			JButton buttonReset = new JButton("Reset");
			buttonReset.addActionListener(buttonResetListener);
			panelEdit.add(buttonReset, "26, 20");
		}

		// We add the property change listener.
		panelViewModBasic.labelSetText.addPropertyChangeListener("text", labelSetTextListener);
	}

	/***********
	 * METHODS *
	 **********/

	public void initalise() {
		panelViewModBasic.initialise();
	}
	
	/**
	 * Sets up the values of the module selected in the JTextFields.
	 */
	public void setupText() {
		origName = panelViewModBasic.getSelName();
		origOpt = panelViewModBasic.getSelOpt();
		origFreq = helperClass.removeUnits(panelViewModBasic.getSelFreq());
		origDataPoints = helperClass.removeUnits(panelViewModBasic.getSelDataPoints());
		origLatency = panelViewModBasic.labelShowLatency.getText();
		origLuts = panelViewModBasic.labelShowLuts.getText();
		origEstClk = panelViewModBasic.labelShowEstClk.getText();

		inputName.setText("");
		inputOpt.setText("");
		inputFreq.setText("");
		inputDataPoints.setText("");
		inputLatency.setText("");
		inputLuts.setText("");
		inputEstClk.setText("");
		
		inputName.setText(origName);
		inputOpt.setText(origOpt);
		inputFreq.setText(origFreq);
		inputDataPoints.setText(origDataPoints);
		inputLatency.setText(origLatency);
		inputLuts.setText(origLuts);
		inputEstClk.setText(origEstClk);
	}

	/**
	 * Removes the empty directory that is left when files are moved away.
	 * 
	 * This prevents a null pointer error when the path that we moved the files away
	 * from when editing is empty.
	 */
	public void removeEmptyDir() {
		int start = 1;

		// format: \\ targetDir\\4 (name)\\3 (opt)\\2 (freq)\\1 (dp) \\0 fileName
		// We check where we are starting our traverse from by comparing where the last
		// position that we didn't change any value is.
		if (!origName.contentEquals(newName)) {
			start = 4;
		} else if (!origOpt.contentEquals(newOpt)) {
			start = 3;
		} else if (!origFreq.contentEquals(newOpt)) {
			start = 2;
		} else if (!origDataPoints.contentEquals(newDataPoints)) {
			start = 1;
		}

		String dir = panelViewModBasic.getSelDir();

		for (int i = 1; i <= start; i++) {
			// If there is an empty directory, we delete it.
			helperIO.removeFolder(dir);

			// We search upwards to the parent directory.
			int last = dir.lastIndexOf("\\");
			dir = dir.substring(0, last);
		}
	}

	/*************
	 * LISTENERS *
	 ************/

	/**
	 * Detects whenever another module is selected in the view panel and updates the
	 * JTextFields with its values.
	 */
	private class LabelSetTextListener implements PropertyChangeListener {
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			setupText();
		}
	}

	private class ButtonResetListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// We set their text to "" first because the document filter gives us an error
			// when
			// reseting with an input that contains "." due to only 1 "." being allowed.
			inputName.setText("");
			inputOpt.setText("");
			inputFreq.setText("");
			inputDataPoints.setText("");
			inputLatency.setText("");
			inputEstClk.setText("");
			inputLuts.setText("");

			setupText();
		}
	}

	/***********
	 * GET/SET *
	 **********/

	/**
	 * Gets the full directory generated from the inputs in the JTextField.
	 * 
	 * Consists of the target directory, module name, optimisaation, frequency, and
	 * data points.
	 * 
	 * @return returns the full directory generated from the inputs.
	 */
	public String getParsedTargetPath() {
		// .trim is to remove any whitespace which causes errors when creating new dir
		newName = inputName.getText().trim();
		newOpt = inputOpt.getText().trim();
		newFreq = inputFreq.getText();
		newDataPoints = inputDataPoints.getText();

		return helperGetSet.getTargetDir() + "\\" + newName + "\\" + newOpt + "\\" + newFreq + "MHz" + "\\"
				+ newDataPoints + " Data Points";
	}

	/**
	 * Gets the partial file name (only the underscores - 1_2_3_).
	 * 
	 * Consists of the latency, estimated clock and luts.
	 * 
	 * @return returns the partial file name.
	 */
	public String getPartialName() {
		newLatency = inputLatency.getText();
		newEstClk = inputEstClk.getText();
		newLuts = inputLuts.getText();

		return newLatency + "_" + newEstClk + "_" + newLuts + "_";
	}

}
