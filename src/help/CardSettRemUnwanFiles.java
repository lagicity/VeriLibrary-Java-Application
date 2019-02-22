package help;

import java.awt.EventQueue;

public class CardSettRemUnwanFiles extends HelpPanel {
	private static final long serialVersionUID = -4278558600619715951L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new CardSettRemUnwanFiles();
			}
		});
	}
	
	public CardSettRemUnwanFiles() {
		titleText.setText("Remove Unwanted Files");
		
		problemText.setText("1. How do I remove unwanted files? "
				
				+ "\n\n2. I have a \"File Not Found\" error.");
		 
		solutionText.setText("1. Go to Settings > Click on \"Remove Unwanted Files\"."
				
				+ "\n\n2.A directory or file marked for deletion is not there. "
				+ "Please reload the application and try again.");
		
		additionalText.setText("This feature removes all empty directories up till the target directory. "
				+ "Empty directories may arise when a file is manually deleted or when there is a problem in deleting files."
				+ "\n\nWARNING: THIS ACTION IS IRREVERSIBLE.");
	}
}
