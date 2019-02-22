package help;

import java.awt.EventQueue;

public class CardViewFilesMissTopMod extends HelpPanel {
	private static final long serialVersionUID = 5987041319202020873L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new CardViewFilesMissTopMod();
			}
		});
	}

	public CardViewFilesMissTopMod() {
		titleText.setText("Changing My Top Module File");
		
		problemText.setText("I cannot find a top module file I want to change with.");
		
		solutionText.setText("VeriLibrary only displays \".v\" files when selecting the top module to prevent the accidental selection of incompatible files."
				+ "\n\nIf your file is not there, it is not compatible to be a top module."
				+ "\n\nPlease check your file(s) again and re-add it to VeriLibrary using the \"Add\" function.");
				
		additionalText.setText("VeriLibrary automatically predicts and selects the top module for you."
				+ "\nYou can change it by clicking on \"Change Top Module\".");
	}
}
