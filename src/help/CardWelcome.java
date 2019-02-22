package help;

import java.awt.EventQueue;

public class CardWelcome extends HelpPanel {
	private static final long serialVersionUID = -8720672488184117119L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new CardWelcome();
			}
		});
	}
	
	public CardWelcome() {
		titleText.setText("Welcome to Help!");
		
		problemText.setText("To start, select a help article from the list on the left."
				+ "\n\nRelated articles may appear on the right. Click on them to be directed to the "
				+ "relevant help article.");

		solutionText.setText("This section contains the solution for the problem.");
		
		additionalText.setText("This section contains additional information to better understand the problem.");
	}
}
