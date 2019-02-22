package help;

import java.awt.EventQueue;

public class CardInstMod extends HelpPanel {
	private static final long serialVersionUID = -977314360774035566L;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new CardInstMod();
			}
		});
	}
	
	public CardInstMod() {
		titleText.setText("Instantiate Modules");
		
		problemText.setText("This section contains articles related to the instantiation of modules.");

		scrollPaneSolution.setVisible(false);
		scrollPaneAdditional.setVisible(false);
	}
}
