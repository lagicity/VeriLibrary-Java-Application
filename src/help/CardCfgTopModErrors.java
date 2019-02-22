package help;

import java.awt.EventQueue;

public class CardCfgTopModErrors extends HelpPanel {
	private static final long serialVersionUID = -715084724948487246L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new CardCfgTopModErrors();
			}
		});
	}
	
	public CardCfgTopModErrors() {
		titleText.setText("Potential Errors When Configuring Wires");
		problemText.setText("1. I have a \"1 Wire Remaining\" error."
				
				+ "\n\n2. I have a \"Invalid Wire Type\" error."
				
				+ "\n\n3. I have a \"No Inputs or Outputs Found\" error."
				
				+ "\n\n4. I have a \"Invalid Wire Name\" error."
				
				+ "\n\n5. I have red text telling me \"Error: Wire Mismatch\".");
		
		solutionText.setText("1. Verilog modules always have to have at least one input and one output."
				+ "\nVeriLibrary prevents this error by preventing you from removing your last input or output."
				
				+ "\n\n2. VeriLibrary has detected that you are trying to add an input to a list meant for outputs or vice versa."
				+ "\nThe lists are split according to their inputs or outputs and therefore inputs or outputs should be added to their respective list."
				
				+ "\n\n3. VeriLibrary could not detect any inputs or outputs in the top module. "
				+ "Please ensure you have chosen the correct top module and try again."
				
				+ "\n\n4. VeriLibrary has detected that you are trying to add a wire called \"input\" or \"output\". "
				+ "\"input\" and \"output\" are reserved words and should not solely be used in a wire declaration's name."
				
				+ "\n\n5. VeriLibrary has detected that the number of wires connecting two modules together are not the same. "
				+ "A wiremismatch is defined as when the number of inputs/outputs of two connected modules are not the same. "
				+ "This will raise an error during simulation.");
		
		additionalText.setText("VeriLibrary searches for the reserved words \"input\" and \"output\" when parsing the top module for inputs and outputs.");
	}
}
