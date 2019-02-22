package help;

import java.awt.EventQueue;

public class CardSettTargetDir extends HelpPanel {
	private static final long serialVersionUID = -28280795135384179L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new CardSettTargetDir();
			}
		});
	}
	
	public CardSettTargetDir() {
		titleText.setText("Changing the Target Directory");
		
		problemText.setText("1. I keep getting errors about my target directory being invalid."
				
				+ "\n\n2. How do I change my target directory?"
				
				+ "\n\n3. Why should I change my target directory?");
		
		solutionText.setText("1. VeriLibrary shows this alert when it detects some irregularities with your target directory. "
				+ "This could range from invalid files to empty directories."
				+ "\n\nVeriLibrary notifies you of the path where the problem originates from in the alert." 
				+ "Please take note of the path and delete the file(s) manually, if necessary."
				
				+ "\n\n2. Go to Settings -> Click on the \"Change\" button and select the path."
				+ "\nClick the \"Save\" button to confirm."
				
				+ "\n\n3. It is strongly recommended not to change your target directory if it is not necessary."
				+ "\nVeriLibrary contains the functions to help you modify or remove files via \"Edit/Remove\".");
		
		additionalText.setText("Only change the target directory if it is corrupted.");
	}
}
