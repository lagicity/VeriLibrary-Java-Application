package help;

import java.awt.EventQueue;

public class CardAddFile extends HelpPanel {
	private static final long serialVersionUID = 6687500530661211360L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new CardAddFile();
			}
		});
	}
	
	public CardAddFile() {
		titleText.setText("Adding Files");
		
		problemText.setText("This section contains articles related to the adding of Verilog files to VeriLibrary.");

		scrollPaneSolution.setVisible(false);
		scrollPaneAdditional.setVisible(false);
	}
}
