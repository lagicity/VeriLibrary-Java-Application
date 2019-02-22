package help;

import java.awt.EventQueue;

public class CardViewFiles extends HelpPanel {
	private static final long serialVersionUID = 3219509980801158653L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new CardViewFiles();
			}
		});
	}
	
	public CardViewFiles() {
		titleText.setText("Viewing of Files");
		
		problemText.setText("This section contains articles related to the viewing files in the main view.");

		scrollPaneSolution.setVisible(false);
		scrollPaneAdditional.setVisible(false);
	}
}
