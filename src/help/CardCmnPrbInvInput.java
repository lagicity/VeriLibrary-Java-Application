package help;

import java.awt.EventQueue;

public class CardCmnPrbInvInput extends HelpPanel {
	private static final long serialVersionUID = -1831248961600183654L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new CardCmnPrbInvInput();
			}
		});
	}

	
	public CardCmnPrbInvInput() {
		titleText.setText("Invalid Input");
		
		problemText.setText("VeriLibrary has detected an input that is not compatible with the text field.");
		
		solutionText.setText("The following fields have the following restrictions:"
				+ "\n\n1. MSB/LSB text fields can only have integers (0-9)."
				+ "\n2. Any numerical input (frequency, LUTs, estimated clock speed etc.) can only have integers (0-9)."
				+ "\n3. The name of modules and optmisations can only contain alphanumeric characters, \"-\", and \"_\".");
		
		additionalText.setText("A error dialog will notify you of the possible input parameters.");
	}
}