package panels;

import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;

/**
 * A JPanel which houses a list that contains the Verilog files to be added to
 * VeriLibrary.
 * 
 * Also launches a dialog for the user to select the Verilog files.
 * 
 * @see PanelSaveFiles
 */
public class PanelSelFiles extends JPanel {
	private static final long serialVersionUID = -8394680668287262746L;

	/****************
	 * UI ELEMENTS *
	 ***************/

	private String selPath;

	private File[] selFiles;

	private JList<String> listSelFiles;

	private JButton buttonClearFiles;

	// This is public because the listener to call this button is in MenuAddFiles.
	public JButton buttonSelFiles;

	// This is public because we need to get the strings (selected files) in this
	// model.
	public DefaultListModel<String> model = new DefaultListModel<String>();

	/*************
	 * VARIABLES *
	 *************/

	/*************
	 * LISTENERS *
	 ************/

	private ActionListener buttonClearFilesListener = new ButtonClearFilesListener();
	private ActionListener buttonSelFilesListener = new ButtonSelFilesListener();

	/***********
	 * HELPERS *
	 ***********/

	/***************
	 * CONSTRUCTOR *
	 **************/

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new PanelSelFiles();
			}
		});
	}

	public PanelSelFiles() {
		setupUI();
	}

	/******************
	 * INITIALISATION *
	 *****************/

	private void setupUI() {
		setLayout(new MigLayout("", "[311px,grow][121px]", "[][257px][][][]"));

		JLabel lblNewLabel = new JLabel("Click on \"Select Verilog Files\" to add files.");
		add(lblNewLabel, "cell 0 0");

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "cell 0 1 1 4,grow");

		listSelFiles = new JList<String>(model);
		listSelFiles.setVisibleRowCount(10);
		scrollPane.setViewportView(listSelFiles);

		buttonSelFiles = new JButton("Select Verilog Files");
		buttonSelFiles.addActionListener(buttonSelFilesListener);
		add(buttonSelFiles, "cell 1 1,alignx left,aligny top");

		buttonClearFiles = new JButton("Clear Files");
		buttonClearFiles.addActionListener(buttonClearFilesListener);
		add(buttonClearFiles, "cell 1 2,alignx left");
	}

	/***********
	 * METHODS *
	 **********/

	/**
	 * Clears the elements in the model.
	 */
	public void clearFiles() {
		model.clear();
	}

	/*************
	 * LISTENERS *
	 ************/

	/**
	 * Launches a FileDialog to allow the user to select the files that they want to
	 * add to VeriLibrary.
	 * 
	 * Adds the files that are selected to the model.
	 */
	private class ButtonSelFilesListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			FileDialog fileBrowser = new FileDialog(new JFrame(), "Select Verilog Files", FileDialog.LOAD);

			// Allow the selection of multiple files.
			fileBrowser.setMultipleMode(true);
			fileBrowser.setVisible(true);

			// Set the files selected in FileDialog
			setSelFiles(fileBrowser.getFiles());
			// Set the path of where the files were selected.
			setSelPath(fileBrowser.getDirectory());

			model.clear();

			for (File file : getSelFiles()) {
				model.addElement(file.getName());
			}
		}
	}

	/**
	 * Clears the elements in the model.
	 */
	private class ButtonClearFilesListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			clearFiles();
		}
	}

	/***********
	 * GET/SET *
	 **********/

	private void setSelFiles(File[] files) {
		selFiles = files;
	}

	public File[] getSelFiles() {
		return selFiles;
	}

	private void setSelPath(String path) {
		selPath = path;
	}

	public String getSelPath() {
		return selPath;
	}
}
