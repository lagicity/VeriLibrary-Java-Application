package help;

import java.awt.EventQueue;

public class CardEditRemErrors extends HelpPanel{
	private static final long serialVersionUID = 8211970634224401236L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new CardEditRemErrors();
			}
		});
	}
	
	public CardEditRemErrors() {
		titleText.setText("Errors");
		
		problemText.setText("1. I have a \"Empty List\" error."
				
				+ "\n\n2. I have a \"File Not Found\" error."

				+ "\n\n3. I have a \"Duplicated Files\" error."
				
				+ "\n\n4. I have a \"Incompatible File\" error.");
		
		solutionText.setText("1. Your list of files to remove is empty. Please add some files are try again."
				
				+ "\n\n2. The file you have tried to remove does not exist. Please refresh and try again."
				
				+ "\n\n3. The file you have tried to add to the list is already in the list. Please add another file."
				
				+ "\n\n4. VeriLibrary has detected an incompatible file or directory. "
				+ "Please refer to the article \"Invalid Files in Lists\" on the right.");
		
		additionalText.setText("VeriLibrary optimises the file deleting experience by not adding children directories "
				+ "to the list when their parent directory is already in the list.");
		
		button1.setText("Invalid Files in Lists");
		button1.setEnabled(true);
	}
}
