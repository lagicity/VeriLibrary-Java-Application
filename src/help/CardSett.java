package help;

import java.awt.EventQueue;

public class CardSett extends HelpPanel {
	private static final long serialVersionUID = 4536623965356809061L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new CardSett();
			}
		});
	}
	
	public CardSett() {
		titleText.setText("Settings");
		
		problemText.setText("This section contains articles related to the settings.");

		scrollPaneSolution.setVisible(false);
		scrollPaneAdditional.setVisible(false);
	}

}
