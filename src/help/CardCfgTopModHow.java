package help;

import java.awt.EventQueue;

public class CardCfgTopModHow extends HelpPanel {
	private static final long serialVersionUID = 7238005131786160217L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new CardCfgTopModHow();
			}
		});
	}
	
	public CardCfgTopModHow() {
		titleText.setText("Configuring Top Modules");
		
		problemText.setText("1. How do I configure the top modules?"
				
				+ "\n\n2. How do I edit/change the order of the submodules?"
				
				+ "\n\n3. How do I use the buttons do?");
		
		solutionText.setText("1. Click on any of the buttons in the \"Modify\" section to begin editing."
				
				+ "\n\n2. Use the up and down arrow buttons in the \"Reorder\" section to change the order of the wires."
				
				+ "\n\n3. Refer to the \"Explanation of Buttons\" article on the right.");
		
		additionalText.setText("The first module (\"Top Module\") is the top module of all the submodules and contains the declaration of the "
				+ "submodules' inputs and outputs. Connect your external output and input wires to the \"Top Module\"'s declarations of inputs and outputs "
				+ "respectively."
				+ "\n\nNOTE: The editing of wire declaration names only affects the declaration in the submodules' top module."
				+ "\nFurther manual modification will be required to connect wires connected to the submodules' top module.");
	}
}
