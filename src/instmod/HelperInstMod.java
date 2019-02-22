package instmod;

import java.awt.Color;
import java.awt.Dialog.ModalityType;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;

import dragdrop.GhostedImageCursor;
import helper.HelperAlerts;
import helper.HelperGetSet;
import renderer.RendererPanelInstMod;

/**
 * A helper class that manages the list.
 * 
 * It creates a new JList every time a module is added or removed. It houses a
 * MouseAdapter to listen to mouse movements within the JList for the drag and
 * drop function.
 */
public class HelperInstMod extends JPanel {
	private static final long serialVersionUID = -2396787064876154906L;

	/****************
	 * UI ELEMENTS *
	 ***************/

	// A list and model hosting the modules of type PanelInstMod added.
	private JList<PanelInstMod> list = new JList<PanelInstMod>();

	public DefaultListModel<PanelInstMod> model = new DefaultListModel<PanelInstMod>();

	/*************
	 * VARIABLES *
	 *************/

	// The original index of the module in the list that was clicked.
	private int sourceIndex = 0;

	/*************
	 * LISTENERS *
	 ************/

	/***********
	 * HELPERS *
	 ***********/

	private HelperGetSet helperGetSet = new HelperGetSet();
	private HelperAlerts helperAlerts = new HelperAlerts();

	// We create the object to display the ghosted image.
	private GhostedImageCursor cursorGhostedImage;

	/***************
	 * CONSTRUCTOR *
	 **************/

	public HelperInstMod() {
		// Setup a list containing objects of type PanelInstMod.
		list = new JList<PanelInstMod>();
	}

	/***********
	 * METHODS *
	 **********/

	/**
	 * Adds or removes a module in the list by creating a whole new list with the
	 * updated model.
	 * 
	 * Used when adding or removing a module in the list.
	 * 
	 * @param addRemove - whether to add (true) or remove (false).
	 */
	public void addRemovePane(boolean addRemove) {
		// If we are adding a module (true), we create a panel and add it.
		if (addRemove) {
			PanelInstMod panel = new PanelInstMod();
			model.addElement(panel);
		} else {
			// Else, we are removing a module (false).
			model.removeElementAt(sourceIndex);
		}

		// We initialise the properties of the list.
		list = new JList<PanelInstMod>(model);
		list.setVisibleRowCount(3);
		list.setFixedCellHeight(115);
		// We set the cell renderer to display images (in this case PanelInstMod)
		// instead of text due to JList defaulting to displaying elements via
		// toString().
		list.setCellRenderer(new RendererPanelInstMod());

		// We add a mouse adaptor to listen to mouse movements when in the list.
		MouseAdapter mAdapter = new MyMouseAdapter();
		list.addMouseListener(mAdapter);
		list.addMouseMotionListener(mAdapter);

		// We re-initialise the list to display the updated model by removing all
		// elements and adding them again.
		removeAll();
		add(list);
		revalidate();
		repaint();
	}

	/**
	 * Checks whether a module we have added/replaced already exists in the list.
	 * 
	 * Used when we want to check if there is an existing module in the list.
	 * 
	 * @return - whether there is an existing module in the list.
	 */
	public boolean isDuplicate() {
		// We check the seldir and full top mod of the modules in the list against the
		// seldir and full top module set when we selected the module in the replace or
		// add view panels.
		for (int i = 0; i < model.size(); i++) {
			if (model.getElementAt(i).getSelDir().contentEquals(helperGetSet.getSelDir())
					&& model.getElementAt(i).getFullTopMod().contentEquals(helperGetSet.getFullTopMod())) {
				return true;
			}
		}
		return false;
	}

	/*************
	 * LISTENERS *
	 ************/

	private class MyMouseAdapter extends MouseAdapter {
		// We need to keep track when we clicked on the list to prevent mouseDragged()
		// from being continuously called even though we didn't click on the list.
		private boolean clicked = false;

		@Override
		public void mousePressed(MouseEvent e) {
			// We now know that we have clicked on an element and so we can initialise and
			// run things.
			clicked = true;

			// We display the ghosted image.
			cursorGhostedImage =  new GhostedImageCursor();
			cursorGhostedImage.run();

			// We set where we initially clicked to the closest index in the list.
			sourceIndex = list.locationToIndex(e.getPoint());

			// We darken the background to give feedback to the user that the element has
			// been clicked.
			list.getSelectedValue().setBackground(Color.LIGHT_GRAY);
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// We continue displaying the ghosted image.
			if (clicked) {
				cursorGhostedImage.run();
			}

			// We reorder the modules if the JCheckBox has been checked.
			if (clicked && helperGetSet.isInstModReorder()) {
				// We get the current index where we are now.
				int currentIndex = list.locationToIndex(e.getPoint());

				// We ensure we are not on the same index (moved to another index).
				if (currentIndex != sourceIndex) {

					// Our new index is the one we are dragging our cursor at. This makes use of the
					// listener that always calls getSelectedIndex() when the mouse
					// is dragged in the JList.
					int targetIndex = list.getSelectedIndex();

					// We get the panel where we initially clicked and dynamically swap the
					// positions as the mouse is dragged.
					PanelInstMod panel = model.get(sourceIndex);
					model.remove(sourceIndex);
					model.add(targetIndex, panel);

					sourceIndex = currentIndex;
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (clicked) {
				// We set the background to the original when the mouse has been released.
				list.getSelectedValue().setBackground(getBackground());

				// We remove a module when the mouse is in the label to remove, and we are not
				// reordering.
				if (!(helperGetSet.isInstModReorder()) && helperGetSet.isInstModEnteredRemove() && clicked) {
					addRemovePane(false);
				}

				// We replace a module when the mouse is in the label to replace, and we are not
				// reordering.
				if (!(helperGetSet.isInstModReorder()) && helperGetSet.isInstModEnteredReplace() && clicked) {
					// We stop displaying the ghosted image.
					cursorGhostedImage.stop();

					// We get the panel we originally clicked on.
					PanelInstMod panel = model.get(sourceIndex);

					// We initialise the dialog to replace the panel by passing the full directory
					// via HelperGetSet.
					String fullDir = panel.getSelDir() + "\\" + panel.getFullTopMod();
					helperGetSet.setFullDir(fullDir);

					DialogReplaceMod dialogReplaceMod = new DialogReplaceMod(getRootPane(), "Replace Module",
							ModalityType.APPLICATION_MODAL);

					// We check if the module we are replacing is a duplicate or not.
					if (dialogReplaceMod.isClose()) {
						if (isDuplicate() == true) {
							helperAlerts.fileDuplicateGeneric();
						} else {
							addRemovePane(false);
							addRemovePane(true);
						}
					}
				}
			}

			cursorGhostedImage.stop();
			clicked = false;
			System.out.println("clicked = " + clicked);
		}
	}

	/***********
	 * GET/SET *
	 **********/
}
