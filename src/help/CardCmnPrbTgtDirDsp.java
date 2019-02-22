package help;

import java.awt.EventQueue;

public class CardCmnPrbTgtDirDsp extends HelpPanel {
	private static final long serialVersionUID = -6930083349141522765L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new CardCmnPrbTgtDirDsp();
			}
		});
	}

	public CardCmnPrbTgtDirDsp() {
		titleText.setText("Target Directory Fails to Load");
		
		problemText.setText("Whenever I open VeriLibrary, I am always prompted to choose my target directory.");
		
		solutionText.setText("1. In Windows, open regedit and browse for HKEY_LOCAL_MACHINE > SOFTWARE > JavaSoft."
				+ "\n2. Right-click on JavaSoft and navigate to New > Key."
				+ "\n3. Create and name the new Key \"Prefs\"."
				+ "\n\nRepeat steps 1-3, but browse for HKEY_LOCAL_MACHINE > WOW6432Node > JavaSoft instead and create the \"Prefs\" Key.");
		
		additionalText.setText("VeriLibrary uses Preferences to save the path of the target directory onto the computer. "
				+ "Preferences allows the path to not be forgotten every time VeriLibrary is closed. "
				+ "The lack of the \"Prefs\" key prevents VeriLibrary from properly saving the path.");
	}
}
