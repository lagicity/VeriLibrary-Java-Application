package help;

import java.awt.EventQueue;

public class CardAddFileErrors extends HelpPanel {
	private static final long serialVersionUID = 5181209600024906352L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new CardAddFileErrors();
			}
		});
	}
	
	public CardAddFileErrors() {
		titleText.setText("Errors");
		
		problemText.setText("1. I have a \"File Not Selected\" error."
				
				+ "\n\n2. I have a \"Empty Text Field\" error."
				
				+ "\n\n3. I have a \"Incompatible Verilog File\" error.");
		
		solutionText.setText("1. You have not selected any file to add. Please click on \"Select Verilog Files\" to add some."
				
				+ "\n\n2. You have left a text field empty. All text fields marked with \"(*)\" must be filled in. "
				
				+ "\n\n3. You have added a file that VeriLibrary does not support. VeriLibrary only supposed \".v\" and \".tcl\" files.");
		
		button1.setText("Invalid Input");
		button1.setEnabled(true);
	}
}
