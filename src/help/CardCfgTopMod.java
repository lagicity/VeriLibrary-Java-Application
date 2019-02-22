package help;

import java.awt.EventQueue;

public class CardCfgTopMod extends HelpPanel {
	private static final long serialVersionUID = -2789833046171485873L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new CardCfgTopModErrors();
			}
		});
	}
	
	public CardCfgTopMod() {
		titleText.setText("Configuring and Exporting Top Modules");
		
		problemText.setText("This section contains articles for configuring and exporting the selected top modules.");

		scrollPaneSolution.setVisible(false);
		scrollPaneAdditional.setVisible(false);
	}
}
