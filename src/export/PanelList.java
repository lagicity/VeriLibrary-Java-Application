package export;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class PanelList extends JPanel {
	private static final long serialVersionUID = -3520104749379103677L;

	/****************
	 * UI ELEMENTS *
	 ***************/

	// Each component has its own list.
	// It is public as we need to check the list's selected index.
	public JList<String> list;

	/*************
	 * VARIABLES *
	 *************/

	// The model that contains each components' respective information.
	private DefaultListModel<String> model = new DefaultListModel<String>();
	
	/***********
	 * HELPERS *
	 ***********/
	
	
	/***************
	 * CONSTRUCTOR *
	 **************/
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new PanelList();
			}
		});
	}

	public PanelList() {
		setLayout(new BorderLayout(0, 0));
		
		// Setup the list. 
		list = new JList<String>();
		list.setModel(model);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		// Setup the scroll pane that houses the list.
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setViewportView(list);
		add(scrollPane);
	}
	
	/***********
	 * METHODS *
	 **********/
	
	/**
	 * Adds an element to the list and updates the selected index.
	 */
	public void add(String value) {
		model.addElement(value);
		list.setSelectedIndex(model.size() - 1);
	}

	/**
	 * Removes an element from the list and updates the selected index.
	 */
	public void remove(int index) {
		model.removeElementAt(index);
		list.setSelectedIndex(index - 1);
	}

	/**
	 * Clears the model and removes all elements.
	 */
	public void clear() {
		model.clear();
	}

	/***********
	 * GET/SET *
	 **********/
	
	/**
	 * Sets the index of the list. 
	 * @param - index the index that was selected.
	 */
	public void setListIndex(int index) {
		list.setSelectedIndex(index);
	}
	
	public DefaultListModel<String> getModel() {
		return model;
	}
}
