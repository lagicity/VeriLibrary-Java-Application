package help;

import java.awt.EventQueue;

public class CardCmnPrbTgtDir extends HelpPanel {
	private static final long serialVersionUID = 1445106592900321397L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new CardCmnPrbTgtDir();
			}
		});
	}

	public CardCmnPrbTgtDir() {
		titleText.setText("Target Directory");
		
		problemText.setText("VeriLibrary has detected that a file or directory in the selected path is incompatible with the way "
			+ "VeriLibrary parses files and directories."
			+ "\n\nThis leads to an error in how VeriLibrary retrieves the information to display."
			+ "\n\nCommon causes:"
			+ "\n1. A Verilog file that VeriLibrary cannot parse."
			+ "\n2. An empty or incompatible directory (ie. directories that do not follow VeriLibrary's format).");
		
		solutionText.setText("Go to Settings > Change Target Directory and change your target directory to a compatible directory. " 
				+ "This directory should not have any of the causes listed above." 
				+ "\n\nGo to Settings > Remove Unwanted Files. "
				+ "\nThis removes any empty directories." 
				+ "\n\nIf all else fails, create a new directory and move all compatible modules over using the \"Edit/Remove\" function.");
		
		additionalText.setText("It is highly recommended to not manually edit or remove any module. Use the Edit/Remove function in VeriLibrary."  
				+ "Manually editing or removing modules can disrupt the way VeriLibrary retrieves the information to display.");
	}
}
