package help;

import java.awt.EventQueue;

public class CardCfgTopModExpHow extends HelpPanel {
	private static final long serialVersionUID = -5184498651033127667L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new CardCfgTopModExpHow();
			}
		});
	}
	
	public CardCfgTopModExpHow() {
		titleText.setText("How to Export the Top Modules");
		
		problemText.setText("How do I export the modules?");
		
		solutionText.setText("1. Enter the name for the top module file."
				+ "\n2. Check the \"Export with module files\" checkbox to export the top module along with "
				+ "all the files associated with the top module."
				+ "\n3. Click on \"Export Files\" to export the files to the selected destination.");
		
		additionalText.setText("All fields marked with \"(*)\" must be filled."
				+ "\n\nTo only export the files of the modules, use the \"Export Modules\" function");
	}
}
