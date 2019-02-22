package help;

import java.awt.EventQueue;

public class CardCfgTopModButtons extends HelpPanel {
	private static final long serialVersionUID = -7815565384070387721L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new CardCfgTopModButtons();
			}
		});
	}
	
	public CardCfgTopModButtons() {
		titleText.setText("How to use the Buttons");
		
		problemText.setText("1. How do I use the \"Add\" function?"
				
				+ "\n\n2. How do I use the \"Remove\" function?"
				
				+ "\n\n3. How do I use the \"Edit\" function?"
				
				+ "\n\n4. How do I use the \"Reset Wires\" function?");
		
		solutionText.setText("1. Begin by clicking on \"Add\"."
				+ "\n2. Enter the name of the wire you wish to add."
				+ "\n3. You can select whether the wire is an input/output via the drop down menu."
				+ "\n4. You can declare the width of the wire by checking \"MSB/LSB\" and entering the respective values."
				+ "\n5. When you are satisfied, add the wire to the respective list by clicking on the position you wish to add it to."
				+ "\n6. Cancel by clicking on \"Cancel\" or by clicking on \"Add\" again."
				
				+ "\n\n1. Begin by clicking on the wire you wish to remove."
				+ "\n2. Click on \"Remove\" to remove the wire."
				
				+ "\n\n1. Begin by clicking on \"Edit\"."
				+ "\n2. Click on the wire you wish to edit."
				+ "\n3. If you wish to remove the wires' width declaration, uncheck \"MSB/LSB\"."
				+ "\n4. If you wish to add a width declaration, check \"MSB/LSB\" and fill in the respective MSB and LSB values."
				+ "\n5. Click \"Save\" when you are finished editing."
				+ "\n6. Cancel by clicking on \"Cancel\" or by clicking on \"Edit\" again."
				
				+ "\n\n1. Begin by clicking on \"Reset Wires\"."
				+ "\n2. Click on the list to reset the lists' wires."
				+ "\n3. WARNING: THIS ACTION IS IRREVERSIBLE.");
		
		additionalText.setText("VeriLibrary provides visual cues to help you know which wire are modifying."
				+ "\nBe careful of wire mismatches! VeriLibrary provides warnings regarding this problem.");
	}
}
