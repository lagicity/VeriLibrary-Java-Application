package renderer;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

/**
 * This renderer tells the renderer of the JList to render using the object
 * (JPanel) I am going to return (PanelInstMod).
 * 
 * The default way a JList displays elements is via the toString() method. By
 * invoking this method, whenever a new component is added to the JList, this
 * method is called and returns the component to add. In this case, it is
 * PanelInstMod to display onto the list.
 */
public class RendererPanelInstMod implements ListCellRenderer<Object> {
	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		JPanel renderer = (JPanel) value;
		return renderer;
	}
}
