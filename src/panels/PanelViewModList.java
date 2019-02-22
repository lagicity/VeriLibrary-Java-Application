package panels;

import java.awt.EventQueue;
import java.io.File;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import helper.HelperClass;
import helper.HelperGetSet;
import net.miginfocom.swing.MigLayout;

/**
 * A JPanel housing the JScrollPane and JList that is used to display the
 * necessary files.
 * 
 * There are two types of JLists - one for directories, and one for files.
 * 
 * The difference is that the file JList does not check for null pointers and
 * assumes that there are always files. If there isn't, NO DATA will be
 * displayed on the view panels' value JLabels. The exception is handled in
 * PanelViewMod/PanelViewModBasic.
 */
public class PanelViewModList extends JPanel {
	static final long serialVersionUID = 8908446835331781505L;

	/****************
	 * UI ELEMENTS *
	 ***************/

	// This is public because we need to access the list for it's index or length.
	public JList<String> list;

	private JScrollPane scrollPane;

	/*************
	 * VARIABLES *
	 *************/

	private List<String> listSel;
	private int[] listInt;

	// This is public because we need to access the model for it's values, index or
	// length.
	public DefaultListModel<String> model = new DefaultListModel<String>();

	/*************
	 * LISTENERS *
	 ************/

	/***********
	 * HELPERS *
	 ***********/

	private HelperGetSet helperGetSet = new HelperGetSet();
	private HelperClass helperClass = new HelperClass();

	/***************
	 * CONSTRUCTOR *
	 **************/

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new PanelViewModList();
			}
		});
	}

	public PanelViewModList() {
		setupUI();
	}

	/******************
	 * INITIALISATION *
	 *****************/

	private void setupUI() {
		setLayout(new MigLayout("", "[150px:n,grow,right]", "[200px:n,grow,fill]"));

		list = new JList<String>();
		scrollPane = new JScrollPane(list);
		add(scrollPane, "cell 0 0,grow");
	}

	/***********
	 * METHODS *
	 **********/

	/**
	 * Sets up the JList for displaying directories.
	 * 
	 * @param startingPath - the path where we load our files from.
	 */
	public void setupListDir(String startingPath) {
		model = new DefaultListModel<String>();

		File file = new File(startingPath);

		if (!file.exists()) {
			// If we detect that starting path has no child directories (is empty), we avoid
			// a null pointer exception and just display an empty list and guide the user to
			// Help.
			model.addElement("Error! Visit help");
			list = new JList<String>(model);

			helperGetSet.setSelDir(startingPath);
			list.setEnabled(false);
			scrollPane.setViewportView(list);

			setSel(null);
		} else {
			// Else, we determine that there are directories and we can show them.
			File[] fileNames = file.listFiles(File::isDirectory);

			for (int i = 0; i < fileNames.length; i++) {
				model.addElement(fileNames[i].getName());
			}

			list = new JList<String>(model);
			list.setSelectedIndex(0);
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			scrollPane.setViewportView(list);

			setSel(list.getSelectedValuesList());
		}
	}

	/**
	 * Sets up the JList for displaying files (Verilog files in this case).
	 * 
	 * @param startingPath - the path where we load our files from.
	 */
	public void setupListFile(String startingPath) {
		model = new DefaultListModel<String>();

		File file = new File(startingPath);
		File[] fileNames = file.listFiles(File::isFile);

		for (int i = 0; i < fileNames.length; i++) {
			model.addElement(helperClass.getParsedFileName(fileNames[i].getName(), 3));

			list = new JList<String>(model);
			scrollPane.setViewportView(list);
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			list.setSelectedIndex(0);

			setListInt(list.getSelectedIndices());
		}
	}

	/*************
	 * LISTENERS *
	 ************/

	/***********
	 * GET/SET *
	 **********/

	public List<String> getSel() {
		return listSel;
	}

	public void setSel(List<String> list) {
		this.listSel = list;
	}

	public int[] getListInt() {
		return listInt;
	}

	public void setListInt(int[] listInt) {
		this.listInt = listInt;
	}
}
