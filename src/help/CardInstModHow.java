package help;

import java.awt.EventQueue;

public class CardInstModHow extends HelpPanel {
	private static final long serialVersionUID = -2925850502852316022L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new CardInstModHow();
			}
		});
	}
	
	public CardInstModHow() {
		titleText.setText("Adding Modules to Instantiate");
		
		problemText.setText("1. How do I instantiate a module?"
				
				+ "\n\n2. I cannot find my top module to instantiate.");
		
		solutionText.setText("1. To add a module to instantiate, follow these steps:"
				+ "\n\n1. Click \"Add New Module\" to add a module."
				+ "\n2. Select the module(s) you would like to instantiate."
				+ "\n3. Click \"Configure Wires & Export\" to configure any wires of any top module and export them."
				
				+ "\n\n2. Refer to \"Missing Top Module File\" on the right.");
		
		additionalText.setText("You can replace or remove modules by dragging and dropping."
				+ "\nYou can reorder modules by ticking \"Reorder modules\".");
	}
}
