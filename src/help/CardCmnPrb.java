package help;

import java.awt.EventQueue;

public class CardCmnPrb extends HelpPanel {
	private static final long serialVersionUID = -216596589546713846L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new CardCmnPrb();
			}
		});
	}
	
	public CardCmnPrb() {
		titleText.setText("Common Problems");
		
		problemText.setText("This section contains articles for common problems.");

		scrollPaneSolution.setVisible(false);
		scrollPaneAdditional.setVisible(false);
	}
}
