package help;

import java.awt.EventQueue;

public class CardEditRem extends HelpPanel {
	private static final long serialVersionUID = -6823018980963911789L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new CardEditRem();
			}
		});
	}
	
	public CardEditRem() {
		titleText.setText("Edit/Removing Files");
		
		problemText.setText("This section contains articles related to the editing/removing of files.");

		scrollPaneSolution.setVisible(false);
		scrollPaneAdditional.setVisible(false);
	}
}
