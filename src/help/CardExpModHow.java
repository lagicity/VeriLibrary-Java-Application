package help;

import java.awt.EventQueue;

public class CardExpModHow extends HelpPanel {
	private static final long serialVersionUID = -3870383557471752375L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new CardExpModHow();
			}
		});
	}
	
	public CardExpModHow() {
		titleText.setText("How to Export Modules");
		
		problemText.setText("How do I export modules?");
		
		solutionText.setText("1. Select the module you wish to export from the lists above."
				+ "\n2. Click on \"Add to List\" to add it to the list of files to export."
				+ "\n3. If you wish to export the files into another directory, check the \"Export in a Directory\" checkbox."
				+ "\n4. Click on \"Export\" to copy your files to their destination.");
		
		additionalText.setText("If you wish to edit the wires of the top module, use the \"Instantiate Modules\" function instead."
				+ "\n\nRemove files by clicking on \"Remove\"."
				+ "\nReset the list by clicking on \"Reset\".");
	}
}