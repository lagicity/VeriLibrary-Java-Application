package help;

import java.awt.EventQueue;

public class CardEditRemEmpty extends HelpPanel {
	private static final long serialVersionUID = 1054438167001735068L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new CardEditRemEmpty();
			}
		});
	}
	
	public CardEditRemEmpty() {
		titleText.setText("Empty Lists when Viewing Files");
		
		problemText.setText("There are lists that are empty when I am viewing them.");
		
		solutionText.setText("Refer to the \"Invalid Files in Lists\" article on the right.");
	}
}
