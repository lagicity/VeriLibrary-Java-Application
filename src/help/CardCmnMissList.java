package help;

import java.awt.EventQueue;

public class CardCmnMissList extends HelpPanel {
	private static final long serialVersionUID = -7394014530613162299L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new CardViewFilesEmpty();
			}
		});
	}
	
	public CardCmnMissList() {
		titleText.setText("Empty Lists When Viewing Files");
		
		problemText.setText("There are a few problems that can cause this error. "
				+ "\n\n1. If the list is empty, there is an empty directory."
				
				+ "\n\n2. If the list contains files with special characters, you have a directory that VeriLibrary did not create.");
		
		solutionText.setText("1. Go to Settings > Remove Unwanted Files to delete any empty directory."
				
				+ "\n\n2. Remove the affected file using \"Edit/Remove\". If that is not possible, delete the affected file manually.");
		
		additionalText.setText("As far as possible, use VeriLibrary's \"Edit/Remove\" function to manage your files.");
	}
}
