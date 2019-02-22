package ui;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextPane;

import helper.HelperGetSet;

/**
 * This class is for common UI elements like JLabels or JTextFields.
 */
public class UIElements {

	/***************
	 * UI ELEMENTS *
	 ***************/

	/*************
	 * VARIABLES *
	 *************/

	private Font defaultFont = new Font("Tahoma", Font.PLAIN, 11);
	private Font warningFont = new Font("Tahoma", Font.BOLD, 11);

	/*************
	 * LISTENERS *
	 ************/

	/***********
	 * HELPERS *
	 ***********/
	private HelperGetSet helperGetSet = new HelperGetSet();

	private UIImages uiImages = new UIImages();

	/***********
	 * METHODS *
	 **********/

	/**
	 * Creates a JLabel as the title header.
	 * 
	 * Font size - 16.
	 * 
	 * @param title - the title header.
	 * @return the title JLabel.
	 */
	public JLabel labelTitleFont(String title) {
		JLabel label = new JLabel();
		label.setText(title);
		label.setFont(new Font(label.getFont().toString(), Font.PLAIN, 16));
		return label;
	}

	/**
	 * Creates a JTextPane for large sections of text.
	 * 
	 * @param text - the text to display.
	 * @return the JTextPane.
	 */
	public JTextPane textPaneText(String text) {
		JTextPane textArea = new JTextPane();
		textArea.setBorder(
				BorderFactory.createCompoundBorder(textArea.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		textArea.setText(text);
		textArea.setEditable(false);
		textArea.setBackground(SystemColor.control);
		return textArea;
	}

	/**
	 * Creates a JLabel with properties for dragging and dropping.
	 * 
	 * @param text          - the text to set.
	 * @param icon          - the icon to set (mouse is not over the JLabel).
	 * @param iconHover     - the icon to set set when hovered (mouse is over the
	 *                      JLabel).
	 * @param font          - the font to set.
	 * @param labelEntering - the string ID of the JLabel.
	 * @return the JLabel.
	 */
	public JLabel labelDragDrop(String text, BufferedImage icon, BufferedImage iconHover, String font,
			String labelEntering) {
		JLabel label = new JLabel(text);
		label.setIcon(new ImageIcon(icon));

		MouseAdapter replaceMA = new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				helperGetSet.setEnteredLabel(labelEntering);
				label.setFont(new Font(font, Font.BOLD, 12));
				label.setIcon(new ImageIcon(iconHover));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				helperGetSet.setExitedLabel(labelEntering);
				label.setFont(new Font(font, Font.PLAIN, 11));
				label.setIcon(new ImageIcon(icon));
			}
		};
		label.addMouseListener(replaceMA);

		return label;
	}

	/**
	 * Creates a JButton for the help button.
	 * 
	 * @return the JButton.
	 */
	public JButton buttonHelp() {
		JButton button = new JButton();
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setIcon(new ImageIcon(uiImages.getHelpSmall()));

		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				button.setIcon(new ImageIcon(uiImages.getHelpSmallHover()));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				button.setIcon(new ImageIcon(uiImages.getHelpSmall()));
			}
		});

		return button;
	}

	/**
	 * Creates a JButton for refreshing the lists of the view panels.
	 * 
	 * @return the JButton.
	 */
	public JButton buttonRefresh() {
		JButton button = new JButton();
		button.setToolTipText("Refreshes the lists.");
		button.setIcon(new ImageIcon(uiImages.getRefreshSmall()));
		return button;
	}

	/*************
	 * LISTENERS *
	 ************/

	/***********
	 * GET/SET *
	 **********/

	public Font getDefaultFont() {
		return defaultFont;
	}

	public void setDefaultFont(Font defaultFont) {
		this.defaultFont = defaultFont;
	}

	public Font getWarningFont() {
		return warningFont;
	}
}
