package help;

import java.awt.EventQueue;

public class CardViewFilesEmpty extends HelpPanel {
	private static final long serialVersionUID = 8405049237043443536L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new CardViewFilesEmpty();
			}
		});
	}
	
	public CardViewFilesEmpty() {
		titleText.setText("Empty Lists when Viewing Files");
		
		problemText.setText("There are lists that have no files in them.");
		
		solutionText.setText("Please refer to the article \"Invalid Files in Lists\" article on the right.");
		
		additionalText.setText("As far as possible, use VeriLibrary's \"Edit/Remove\" function to manage your files.");
	}
}
