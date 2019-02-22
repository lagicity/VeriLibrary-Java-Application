package main;

import java.awt.Dialog.ModalityType;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import help.DialogHelp;
import helper.HelperAlerts;
import helper.HelperClass;
import helper.HelperPrefs;
import menu.DialogAdd;
import menu.DialogEditRem;
import menu.DialogSettings;
import net.miginfocom.swing.MigLayout;
import ui.UIImages;
import welcome.DialogWelcome;

public class MainView extends JFrame {
	private static final long serialVersionUID = 7337060942463236424L;

	/****************
	 * UI ELEMENTS *
	 ***************/

	private TabView tabView;
	private TabCompare tabCompare;

	private static JFrame frame;

	/*************
	 * VARIABLES *
	 *************/

	/*************
	 * LISTENERS *
	 ************/

	private ActionListener buttonAddListener = new ButtonAddListener();
	private ActionListener buttonEditRemListener = new ButtonEditRemListener();
	private ActionListener buttonHelpListener = new ButtonHelpListener();
	private ActionListener buttonSettingsListener = new ButtonSettingsListener();
	
	private WindowAdapter refreshPanelsListener = new RefreshPanelsListener();

	/***********
	 * HELPERS *
	 ***********/

	private static HelperPrefs helperPrefs = new HelperPrefs();
	private static HelperClass helperClass = new HelperClass();
	private static HelperAlerts helperAlerts = new HelperAlerts();

	private static UIImages uiImages = new UIImages();

	/***************
	 * CONSTRUCTOR *
	 **************/

	public MainView() {
		// Initialise the UI
		setupUI();
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				// We load the UI for the whole application.
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					helperAlerts.UINotLoaded();
					e.printStackTrace();
				}

				// We initialise the dialog properties
				frame = new MainView();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setResizable(false);
				frame.setTitle("VeriLibrary");
				frame.setIconImage(uiImages.getMain_icon());
				frame.setLocation(helperClass.getCenterScreenLocation(frame.getHeight(), frame.getWidth()));
				frame.pack();

				frame.setVisible(true);
				// We show the dialog
				/*if (!helperPrefs.getWelcomePref()) {
					new DialogWelcome();
				} else {
					frame.setVisible(true);
				}*/
			}
		});
	}

	/******************
	 * INITIALISATION *
	 *****************/

	private void setupUI() {
		getContentPane().setLayout(new MigLayout("", "[grow,fill]", "[fill]"));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, "cell 0 0");

		// We setup the two tabs.
		// The try-catch is in case we initialise the tabs and there are no directories
		// to be loaded (null pointer) or if there are files which we cannot parse
		// (array out of bounds).
		{
			try {
				tabCompare = new TabCompare();
				tabView = new TabView();
				tabbedPane.addTab("View", null, tabView, null);
				tabbedPane.addTab("Compare", null, tabCompare, null);
			} catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
				helperAlerts.fileInvalid();
				launchDialogSettings();
				e.printStackTrace();
			}
		}

		// We setup the menu bar at the top.
		{
			JMenuBar menuBar = new JMenuBar();
			setJMenuBar(menuBar);

			JButton buttonAddFiles = new JButton("Add");
			buttonAddFiles.setToolTipText("Add Verilog files to VeriLibrary.");
			buttonAddFiles.setIcon(new ImageIcon(uiImages.getAddSmall()));
			buttonAddFiles.addActionListener(buttonAddListener);
			menuBar.add(buttonAddFiles);

			JButton buttonEditRem = new JButton("Edit/Remove");
			buttonEditRem.setToolTipText("Edit or Remove files in VeriLibrary.");
			buttonEditRem.setIcon(new ImageIcon(uiImages.getEditSmall()));
			buttonEditRem.addActionListener(buttonEditRemListener);
			menuBar.add(buttonEditRem);

			JButton buttonSettings = new JButton("Settings");
			buttonSettings.setToolTipText("Change some settings in VeriLibrary.");
			buttonSettings.setIcon(new ImageIcon(uiImages.getSettingsSmall()));
			buttonSettings.addActionListener(buttonSettingsListener);
			menuBar.add(buttonSettings);

			JButton buttonHelp = new JButton("Help");
			buttonHelp.setToolTipText("Find help on some common VeriLibrary problems.");
			buttonHelp.setIcon(new ImageIcon(uiImages.getHelpSmall()));
			buttonHelp.addActionListener(buttonHelpListener);
			menuBar.add(buttonHelp);
		}
	}

	/***********
	 * METHODS *
	 **********/

	/**
	 * Launches the settings dialog.
	 */
	private void launchDialogSettings() {
		new DialogSettings(frame, "Settings", ModalityType.APPLICATION_MODAL);
	}

	/*************
	 * LISTENERS *
	 ************/

	private class RefreshPanelsListener extends WindowAdapter {
		@Override
		public void windowClosed(WindowEvent e) {
			tabView.panelViewMod.initialise();
			tabCompare.panelCompareModBasic.initialise();
		}
	}

	/**
	 * Launches the help dialog.
	 */
	private class ButtonHelpListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			new DialogHelp();
		}
	}

	/**
	 * Launches the add files dialog.
	 */
	private class ButtonAddListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			DialogAdd dialogAdd = new DialogAdd(frame, "Add Files", ModalityType.APPLICATION_MODAL);
			
			// We refresh the panels to reflect the added file. 
			dialogAdd.addWindowListener(refreshPanelsListener);
		}
	}

	/**
	 * Launches the edit/remove files dialog.
	 */
	private class ButtonEditRemListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			DialogEditRem dialogEditRem = new DialogEditRem(frame, "Edit/Remove Files", ModalityType.APPLICATION_MODAL);
			
			// We refresh the panels to reflect the edited/removed file. 
			dialogEditRem.addWindowListener(refreshPanelsListener);
		}
	}

	/**
	 * Launches the settings files dialog.
	 */
	private class ButtonSettingsListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			launchDialogSettings();

			// We update the two tabs' main views when we close the settings dialog to
			// reflect any changes made in the settings dialog (for example, changing of
			// target directory or deleting of files).
			try {
				tabView.panelViewMod.initialise();
				tabCompare.panelCompareModBasic.initialise();
				tabCompare.initialise();

			} catch (NullPointerException | ArrayIndexOutOfBoundsException e2) {
				helperAlerts.fileInvalid();
				launchDialogSettings();
				e2.printStackTrace();
			}
		}
	}
	/***********
	 * GET/SET *
	 **********/
}
