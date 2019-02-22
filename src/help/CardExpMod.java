package help;

import java.awt.EventQueue;

public class CardExpMod extends HelpPanel{
	private static final long serialVersionUID = -5073000294711315727L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new CardExpMod();
			}
		});
	}
	
	public CardExpMod() {
		titleText.setText("Exporting Modules");
		
		problemText.setText("This section contains articles related to exporting modules.");

		scrollPaneSolution.setVisible(false);
		scrollPaneAdditional.setVisible(false);
	}
}
