package panels;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;

/**
 * Launches the dialog for the user to select the path to save their Verilog
 * files at.
 * 
 * @see PanelSelFiles
 */
public class PanelSaveFiles extends JPanel {
	private static final long serialVersionUID = 7804723461045841456L;

	/****************
	 * UI ELEMENTS *
	 ***************/

	// This is public because we need to set the directory we are saving to when we
	// create this object.
	public JLabel labelSaveDir;

	/*************
	 * VARIABLES *
	 *************/

	public String savePath;

	/*************
	 * LISTENERS *
	 ************/

	private ActionListener buttonSaveFilesListener = new ButtonSaveFilesListener();

	/***********
	 * HELPERS *
	 ***********/

	/***************
	 * CONSTRUCTOR *
	 **************/

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new PanelSaveFiles();
			}
		});
	}

	public PanelSaveFiles() {
		setLayout(new MigLayout("", "[][fill,grow][]", "[]"));

		JLabel labelPrompt = new JLabel("Save Files To: ");
		add(labelPrompt, "cell 0 0");

		labelSaveDir = new JLabel("Save dir");
		add(labelSaveDir, "cell 1 0");

		JButton buttonSaveFiles = new JButton("Browse");
		add(buttonSaveFiles, "cell 2 0");

		buttonSaveFiles.addActionListener(buttonSaveFilesListener);
	}

	/***********
	 * METHODS *
	 **********/

	/*************
	 * LISTENERS *
	 ************/

	/**
	 * Launches the dialog for the user to choose the path of where they want to
	 * save their VerilogFiles.
	 * 
	 * Used when the user selects the path to save their Verilog files when adding
	 * them to VeriLibrary.
	 * 
	 * Sets the path selected in the get/set method.
	 */
	private class ButtonSaveFilesListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser chooser = new JFileChooser();
			File targetDir = new File(getSavePath());

			chooser.setCurrentDirectory(targetDir);
			chooser.setDialogTitle("Save Verilog Files To...");
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.setAcceptAllFileFilterUsed(false);

			if (chooser.showOpenDialog(PanelSaveFiles.this) == JFileChooser.APPROVE_OPTION) {
				setSavePath(chooser.getSelectedFile().toString());
				labelSaveDir.setText(getSavePath());
			} else {
				System.out.println("No Selection ");
				return;
			}
		}
	}

	/***********
	 * GET/SET *
	 **********/

	public void setSavePath(String path) {
		savePath = path;
	}

	public String getSavePath() {
		return savePath;
	}

}
