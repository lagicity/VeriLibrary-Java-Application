package helper;

import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * A helper class for UI elements associated with JTextFields.
 * 
 * It does error checking and the displaying of any incompatible input errors.
 */

public class HelperFields {
	/**
	 * Creates a JTextField with error checking.
	 * 
	 * Used when we want a JTextField with only inputs that we need. This reduces
	 * the need for repetitive error checking.
	 * 
	 * @param maxLen      - the maximum length of characters that can be entered.
	 * @param errorPrompt - the JLabel to feedback the error to the user.
	 * @param type        - the type of JTextField to create. 0 - for integers when
	 *                    adding files (frequency, luts etc). 1 - for characters
	 *                    when adding files (name, optimisation). 2 - for MSB/LSB. 3
	 *                    - for inputs/outputs of wires.
	 * 
	 * @return the generated JTextField according to the parameters.
	 */
	public JTextField createTextField(int maxLen, JLabel errorPrompt, int type) {
		// We create a default JTextField.
		JTextField inputField = new JTextField();

		// We create a document object to listen to the inputs in the JTextField.
		AbstractDocument document = (AbstractDocument) inputField.getDocument();

		// We set a filter to the document to check invalid inputs.
		document.setDocumentFilter(new DocumentFilter() {

			// Invoked whenever an input is entered in JTextField.
			public void replace(FilterBypass fb, int offs, int length, String str, AttributeSet a)
					throws BadLocationException {

				// text contains the String of all the text entered.
				// We get the first character entered (index == 0).
				String text = fb.getDocument().getText(0, fb.getDocument().getLength());

				// We add more text as more are entered.
				text += str;

				switch (type) {
				// For integers when adding files (frequency, luts etc).
				case 0:
					if ((fb.getDocument().getLength() + str.length() - length) <= maxLen
							&& text.matches("[0-9]+[.]?[0-9]{0," + maxLen + "}")) {
						// If the input is < maxLen and is an integer (0-9), we accept it.
						super.replace(fb, offs, length, str, a);
						errorPrompt.setVisible(false);
					} else if ((fb.getDocument().getLength() + str.length() - length) > maxLen) {
						// Else if the input is > maxLen, we reject it.
						errorPrompt.setText("Error! Maximum length is " + maxLen + ".");
						errorPrompt.setVisible(true);
						Toolkit.getDefaultToolkit().beep();
					} else if ((fb.getDocument().getLength() >= 1) && (text.contains(".") && str.contentEquals("."))) {
						// Else if a decimal is entered twice, we reject it.
						errorPrompt.setText("Error! Decimal point (.) already entered.");
						errorPrompt.setVisible(true);
						Toolkit.getDefaultToolkit().beep();
					} else if (!text.matches("[0123456789]{0,}")) {
						// Else if the input is not an integer, we reject it.
						errorPrompt.setText("Error! Integers (0-9) only.");
						errorPrompt.setVisible(true);
						Toolkit.getDefaultToolkit().beep();
					}
					break;

				// For characters when adding files (name, optimisation).
				case 1:
					if ((fb.getDocument().getLength() + str.length() - length) <= maxLen
							&& text.matches("[\\w- ]{0," + maxLen + "}")) {
						// If the input is < maxLen and is a valid character, we accept it.
						// Valid characters = 0-9 A-Z a-z _ -
						super.replace(fb, offs, length, str, a);
						errorPrompt.setVisible(false);
					} else if ((fb.getDocument().getLength() + str.length() - length) > maxLen) {
						// Else if the input is > maxLen, we reject it.
						errorPrompt.setText("Error! Maximum length is " + maxLen + ".");
						errorPrompt.setVisible(true);
						Toolkit.getDefaultToolkit().beep();
					} else {
						// Else we reject it.
						errorPrompt.setText("Error! A-Z, a-z, 0-9, \"_\", \"-\" only.");
						errorPrompt.setVisible(true);
						Toolkit.getDefaultToolkit().beep();
					}
					break;

				// For MSB/LSB.
				case 2:
					if ((fb.getDocument().getLength() + str.length() - length) <= maxLen
							&& text.matches("[\\d]{0," + maxLen + "}")) {
						// If the input is < maxLen and is an integer (0-9), we accept it.
						super.replace(fb, offs, length, str, a);
						errorPrompt.setVisible(false);
					} else if ((fb.getDocument().getLength() + str.length() - length) > maxLen) {
						// Else if the input is > maxLen, we reject it.
						errorPrompt.setText("Error! Maximum length is " + maxLen + ".");
						errorPrompt.setVisible(true);
						Toolkit.getDefaultToolkit().beep();
					} else {
						// Else we reject it.
						errorPrompt.setText("Error! Integers (0-9) only.");
						errorPrompt.setVisible(true);
						Toolkit.getDefaultToolkit().beep();
					}
					break;

				// For inputs/outputs of wires.
				case 3:
					if ((fb.getDocument().getLength() + str.length() - length) <= maxLen
							&& text.matches("[\\w]{0," + maxLen + "}")) {
						// If the input is < maxLen and is a valid character, we accept it.
						// Valid characters = 0-9 A-Z a-z _
						super.replace(fb, offs, length, str, a);
						errorPrompt.setVisible(false);
					} else if ((fb.getDocument().getLength() + str.length() - length) > maxLen) {
						// Else if the input is > maxLen, we reject it.
						errorPrompt.setText("Error! Maximum length is " + maxLen + ".");
						errorPrompt.setVisible(true);
						Toolkit.getDefaultToolkit().beep();
					} else if (text.matches("[ ]")) {
						// Else if the input has a whitespace, we reject it.
						errorPrompt.setText("Error! No whitespaces.");
						errorPrompt.setVisible(true);
						Toolkit.getDefaultToolkit().beep();
					} else {
						// Else we reject it.
						errorPrompt.setText("Error! A-Z, a-z, 0-9, \"_\" only.");
						errorPrompt.setVisible(true);
						Toolkit.getDefaultToolkit().beep();
					}
					break;
				default:
					System.out.println("Out of bounds in createTextField");
					break;
				}
			}

			// Do i need this..?
			/*
			 * public void insertString(FilterBypass fb, int offs, String str, AttributeSet
			 * a) throws BadLocationException {
			 * 
			 * String text = fb.getDocument().getText(0, fb.getDocument().getLength()); text
			 * += str; if ((fb.getDocument().getLength() + str.length()) <= maxLen &&
			 * text.matches("^[0-9]+[.]?[0-9]{0,1}$")) {
			 * System.out.println("the error is happening here2"); super.insertString(fb,
			 * offs, str, a); } else { Toolkit.getDefaultToolkit().beep(); } }
			 */
		});

		return inputField;
	}

	/**
	 * Creates the error JLabel when an invalid input is entered.
	 * 
	 * @return - the JLabel.
	 */
	public JLabel createErrorLabel() {
		JLabel label = new JLabel();
		label.setVisible(false);
		label.setForeground(Color.RED);

		return label;
	}
}
