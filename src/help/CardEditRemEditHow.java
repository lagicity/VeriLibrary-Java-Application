package help;

import java.awt.EventQueue;

public class CardEditRemEditHow extends HelpPanel {
	private static final long serialVersionUID = 7341303331644017569L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new CardEditRemEditHow();
			}
		});
	}
	
	public CardEditRemEditHow() {
		titleText.setText("How to Edit Files");
		
		problemText.setText("How do I edit files?");
		
		solutionText.setText("1. Select the module you wish to edit."
				+ "\n2. Enter the respective parameters."
				+ "\n3. Click on \"Edit\" to edit the module."
				+ "\n\nIf you wish to start editing again, click on the button \"Reset\".");
		
		additionalText.setText("VeriLibrary does not allow you to edit the name of Verilog files "
				+ "due to them being intertwined with one another in the contents of their files (ie. input/output declaration)."
				+ "\n\nWARNING: THIS ACTION CANNOT BE REVERSED.");
	}
}
