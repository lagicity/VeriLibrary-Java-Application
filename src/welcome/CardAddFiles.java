package welcome;

import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import helper.HelperGetSet;
import menu.DialogAdd;
import net.miginfocom.swing.MigLayout;
import ui.UIElements;

public class CardAddFiles extends JPanel {
	private static final long serialVersionUID = 3769027896784436203L;

	/***************
	 * UI ELEMENTS *
	 ***************/

	/*************
	 * VARIABLES *
	 *************/

	/*************
	 * LISTENERS *
	 ************/

	/***********
	 * HELPERS *
	 ***********/

	private HelperGetSet helperGetSet = new HelperGetSet();

	private UIElements uiLabel = new UIElements();

	/***************
	 * CONSTRUCTOR *
	 **************/

	public CardAddFiles(JButton next) {
		setLayout(new MigLayout("", "[grow]", "[][][]"));

		JTextPane textPane = uiLabel.textPaneText("To start using VeriLibrary, add some Verilog files."
				+ "\n\nSimply enter the relevant information, select your files, choose where to save them and you're good to go!");
		add(textPane, "cell 0 0,growx");

		JButton buttonAdd = new JButton("Add Verilog Files");
		buttonAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DialogAdd menuAddFiles = new DialogAdd(getTopLevelAncestor(), "Add Files",
						ModalityType.DOCUMENT_MODAL);

				menuAddFiles.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						if (helperGetSet.isFilesAdded() == true) {
							next.setEnabled(true);
						}
					}
				});
			}
		});
		add(buttonAdd, "cell 0 2,alignx right");

	}

	/***********
	 * METHODS *
	 **********/

	/*************
	 * LISTENERS *
	 ************/

	/***********
	 * GET/SET *
	 **********/
}
