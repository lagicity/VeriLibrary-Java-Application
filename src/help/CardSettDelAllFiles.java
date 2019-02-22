package help;

import java.awt.EventQueue;

public class CardSettDelAllFiles extends HelpPanel {
	private static final long serialVersionUID = 7461464565596466305L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new CardSettDelAllFiles();
			}
		});
	}
	
	public CardSettDelAllFiles() {
		titleText.setText("Delete All Files");
		
		problemText.setText("1. How do I delete all my files?"
				
				+ "\n\n2. I have a \"File Not Found\" error.");
		
		solutionText.setText("1. Go to Settings > Click on \"Delete All Files\"."
				
				+ "\n\n2. A directory or file marked for deletion is not there."
				+ "Please reload the application and try again.");
		
		additionalText.setText("This feature deletes all files up till the target directory."
				+ "The target directory itself is not deleted."
				+ "\n\nWARNING: THIS ACTION IS IRREVERSIBLE.");
	}
}
