package help;

import java.awt.EventQueue;

public class CardEditRemRemoveHow extends HelpPanel {
	private static final long serialVersionUID = 6121406696978430560L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new CardEditRemRemoveHow();
			}
		});
	}
	
	public CardEditRemRemoveHow() {
		titleText.setText("How to Remove Files");
		
		problemText.setText("How do I remove files?");
		
		solutionText.setText("1. Select what you would like to remove from the drop down menu."
				+ "\n2. Add the files you wish to remove by clicking on \"Add to List\"."
				+ "\n3. Click on \"Remove\" to remove the files in the \"Files to be Removed\" list.");
		
		additionalText.setText("You can select multiple files to add to the list at once by using the \"Ctrl\" and \"Shift\" keys on your keyboard."
				+ "\nYou can remove files from the list by clicking on \"Remove From List\"."
				+ "\nYou can clear the list by clicking on \"Clear List\"."
				+ "\n\nWARNING: THIS ACTION IS IRREVERSIBLE.");
	}
}
