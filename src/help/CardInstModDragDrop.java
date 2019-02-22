package help;

import java.awt.EventQueue;

public class CardInstModDragDrop extends HelpPanel{
	private static final long serialVersionUID = 8700974818202857596L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new CardInstModDragDrop();
			}
		});
	}
	
	public CardInstModDragDrop() {
		titleText.setText("Dragging and Dropping of Modules");
		
		problemText.setText("1. I cannot reorder my modules"
				
				+ "\n\n2. I cannot remove or replace my modules.");
		
		solutionText.setText("1. Ensure the \"Reorder modules\" checkbox is checked before trying to reorder modules."
				
				+ "\n\n2. Ensure that you are dragging and dropping a module to the respective label."
				+ " The module you are clicking on will have its background darkened. "
				+ " Drag this module until the label increases in size and let go of your mouse.");
	}
}
