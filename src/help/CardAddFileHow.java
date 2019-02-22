package help;

import java.awt.EventQueue;

public class CardAddFileHow extends HelpPanel {
	private static final long serialVersionUID = 3911865558508591700L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new CardAddFileHow();
			}
		});
	}
	
	public CardAddFileHow() {
		titleText.setText("How to Add Files");
		
		problemText.setText("How do I add files?");
		
		solutionText.setText("Please follow the following steps:"
				+ "\n\n1. Select the module name or optimisation from the drop down menu. "
				+ "If you are adding a new item, check the respective \"Add New\" checkbox."
				+ "\n2. Fill in all the text fields marked with \"(*)\"."
				+ "\n3. Select your files to add by clicking on \"Select Verilog Files\"."
				+ "\n4. Select the target directory to save your files to."
				+ "\n5. Click on \"Add Files\" to copy the files to the destination, or click on \"Move Files\" to move the files to the destination."
				+ "\n\nAdding files copies the files to the destination (they are still present at the source), "
				+ "while moving files moves the files to the destination (they are deleted at the source).");
		
		additionalText.setText("All text fields marked with a \"(*)\" must be filled in."
				+ "\nOnly \".v\" and \".tcl\" Verilog files are supported.");
	}
}
