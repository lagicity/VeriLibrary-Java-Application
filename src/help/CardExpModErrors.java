package help;

import java.awt.EventQueue;

public class CardExpModErrors extends HelpPanel {
	private static final long serialVersionUID = -7736264522988018957L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new CardExpModErrors();
			}
		});
	}
	
	public CardExpModErrors() {
		titleText.setText("Errors");
		
		problemText.setText("1. I have a \"File Not Found\" error."
				
				+ "\n\n2. I have a \"File Duplicate\" error.");
		
		solutionText.setText("1. VeriLibrary can not find the files to export. Please ensure the file is present at the correct path."
				
				+ "\n\n2. VeriLibrary has detected that the file you are trying to add already exists in the list. Please select another file.");
	}
}
