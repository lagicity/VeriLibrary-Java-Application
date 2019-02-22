package help;

import java.awt.EventQueue;

public class CardAddFileMissNameOpt extends HelpPanel {
	private static final long serialVersionUID = 3911865558508591700L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new CardAddFileMissNameOpt();
			}
		});
	}

	public CardAddFileMissNameOpt() {
		titleText.setText("Missing Module Name or Optimisation");
		
		problemText.setText("I cannot find the module name or optimisation that I am looking for.");
		
		solutionText.setText("Ensure that the target directory you have set contains the relevant module name.");
		
		additionalText.setText("VeriLibrary loads the dropdown boxes by searching for directories within your target directory. "
				+ "If the module name or optimisation does not appear, it is not a child directory of your target directory.");
	}
}
