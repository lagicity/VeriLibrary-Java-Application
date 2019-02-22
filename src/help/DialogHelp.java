package help;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.miginfocom.swing.MigLayout;
import ui.UIImages;

public class DialogHelp extends JDialog {
	private static final long serialVersionUID = 3045741184066049112L;

	/**************
	 * CONSTANTS *
	 *************/

	private static final String WELCOME = "Welcome";

	private static final String CMN_PRB = "Common Problems";
	private static final String CMN_PRB_MISS_LIST = "++ Invalid Files in Lists";
	private static final String CMN_PRB_INV_INPUT = "++ Invalid Input";
	private static final String CMN_PRB_TGT_DIR = "++ Target Directory Errors";
	private static final String CMN_PRB_TGT_DIR_DSP = "++ Target Directory Not Saved";

	private HelpPanel cardCmnPrb = new CardCmnPrb();
	private HelpPanel cardCmnMissList = new CardCmnMissList();
	private HelpPanel cardCmnPrbInvInput = new CardCmnPrbInvInput();
	private HelpPanel cardCmnPrbTgtDir = new CardCmnPrbTgtDir();
	private HelpPanel cardCmnPrbTgtDirDsp = new CardCmnPrbTgtDirDsp();

	private static final String INST_MOD = "Instantiating Modules";
	private static final String INST_MOD_HOW = "++ How to Instantiate Modules";
	private static final String INST_MOD_DRAG_DROP = "++ Dragging and Dropping";

	private HelpPanel cardInstMod = new CardInstMod();
	private HelpPanel cardInstModHow = new CardInstModHow();
	private HelpPanel cardInstModDropDrop = new CardInstModDragDrop();

	private static final String ADD_FILE = "Adding Files";
	private static final String ADD_FILE_HOW = "++ How To Add Files";
	private static final String ADD_FILE_MISS_NAME_OPT = "++ Dropdown Menu Missing Values";
	private static final String ADD_FILE_ERRORS = "++ Errors when Adding";

	private HelpPanel cardAddFile = new CardAddFile();
	private HelpPanel cardAddFileHow = new CardAddFileHow();
	private HelpPanel cardAddFileMissNameOpt = new CardAddFileMissNameOpt();
	private HelpPanel cardAddFileErrors = new CardAddFileErrors();

	private static final String VIEW_FILE = "Viewing Files";
	private static final String VIEW_FILE_EMPTY = "++ Empty Lists";
	private static final String VIEW_FILE_MISS_TOP_MOD = "++ Missing Top Module File";

	private HelpPanel cardViewFiles = new CardViewFiles();
	private HelpPanel cardViewFilesEmpty = new CardViewFilesEmpty();
	private HelpPanel cardViewFilesMissTopMod = new CardViewFilesMissTopMod();

	private static final String EDIT_REM = "Edit/Remove Files";
	private static final String EDIT_REM_EMPTY = "++ Empty Lists";
	private static final String EDIT_REM_EDIT_HOW = "++ How to Edit Files";
	private static final String EDIT_REM_REMOVE_HOW = "++ How to Remove Files";
	private static final String EDIT_REM_ERRORS = "++ Errors when Editing or Removing";

	private HelpPanel cardEditRem = new CardEditRem();
	private HelpPanel cardEditRemEmpty = new CardEditRemEmpty();
	private HelpPanel cardEditRemEditHow = new CardEditRemEditHow();
	private HelpPanel cardEditRemRemoveHow = new CardEditRemRemoveHow();
	private HelpPanel cardEditRemErrors = new CardEditRemErrors();

	private static final String CFG_TOP_MOD = "Configuring and Exporting Top Modules";
	private static final String CFG_TOP_MOD_HOW = "++ How to Configure Top Modules";
	private static final String CFG_TOP_MOD_EXP_HOW = "++ How to Export the Modules";
	private static final String CFG_TOP_MOD_BUTTONS = "++ Explanation of the Buttons";
	private static final String CFG_TOP_MOD_ERRORS = "++ Errors when Configuring";

	private HelpPanel cardCfgTopMod = new CardCfgTopMod();
	private HelpPanel cardCfgTopModHow = new CardCfgTopModHow();
	private HelpPanel cardCfgTopModExpHow = new CardCfgTopModExpHow();
	private HelpPanel cardCfgTopModButtons = new CardCfgTopModButtons();
	private HelpPanel cardCfgTopModErrors = new CardCfgTopModErrors();

	private static final String EXP_MOD = "Exporting Modules";
	private static final String EXP_MOD_HOW = "++ How to Export Modules";
	private static final String EXP_MOD_ERRORS = "++ Errors when Exporting";

	private HelpPanel cardExpMod = new CardExpMod();
	private HelpPanel cardExpModHow = new CardExpModHow();
	private HelpPanel cardExpModErrors = new CardExpModErrors();

	private static final String SETT = "Settings";
	private static final String SETT_TARGET_DIR = "++ Changing Target Directory";
	private static final String SETT_REM_UNWAN_FILES = "++ Removing Unwanted Files";
	private static final String SETT_DEL_ALL_FILES = "++ Deleting All Files";

	private HelpPanel cardSett = new CardSett();
	private HelpPanel cardSettTargetDir = new CardSettTargetDir();
	private HelpPanel cardSettRemUnwanFiles = new CardSettRemUnwanFiles();
	private HelpPanel cardSellDelAllFiles = new CardSettDelAllFiles();

	/***************
	 * UI ELEMENTS *
	 ***************/
	private JPanel contentPanel = new JPanel();

	private JList<String> list;

	private DefaultListModel<String> model = new DefaultListModel<>();

	private JPanel panelCard = new JPanel();

	/*************
	 * VARIABLES *
	 *************/

	/*************
	 * LISTENERS *
	 ************/

	private CardLayout cardLayout = new CardLayout();

	/***********
	 * HELPERS *
	 ***********/

	private UIImages uiImages = new UIImages();

	/***************
	 * CONSTRUCTOR *
	 **************/

	public DialogHelp() {
		// Initialise the UI
		createGUI();

		initialiseCmnPrb();
		initialiseInstMod();
		initialiseAddMod();
		initialiseViewFiles();
		initialiseEditRem();
		initialiseCfgMod();
		initialiseExpMod();
		initialiseSett();

		// Initialise the dialog properties
		setTitle("Help");
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setIconImage(uiImages.getHelpSmall());

		// Show the dialog
		pack();
		setVisible(true);
	}

	/******************
	 * INITIALISATION *
	 *****************/

	private void createGUI() {
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		JPanel container = new JPanel();
		container.setLayout(new MigLayout("", "[200px:n][]", "[grow,fill]"));
		contentPanel.add(container, BorderLayout.CENTER);
		// We setup the card layout on the right side.
		{
			panelCard.setLayout(cardLayout);
			container.add(panelCard, "cell 1 0");
		}

		// We setup the content page on the left side.
		{
			CardWelcome cardWelcome = new CardWelcome();
			model.addElement(WELCOME);
			panelCard.add(cardWelcome, WELCOME);

			list = new JList<String>(model);
			list.setSelectedIndex(0);
			list.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent arg0) {
					String name = list.getSelectedValue();

					cardLayout.show(panelCard, name);
				}
			});
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setViewportView(list);
			container.add(scrollPane, "cell 0 0");
		}

		// We setup the bottom buttons.
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			JButton buttonClose = new JButton("Close");
			buttonClose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			buttonPane.add(buttonClose);
			getRootPane().setDefaultButton(buttonClose);
		}
	}

	/***********
	 * METHODS *
	 **********/

	/**
	 * Initialises the common problem cards.
	 */
	private void initialiseCmnPrb() {
		addCard(cardCmnPrb, CMN_PRB);
		addCard(cardCmnMissList, CMN_PRB_MISS_LIST);
		addCard(cardCmnPrbInvInput, CMN_PRB_INV_INPUT);
		addCard(cardCmnPrbTgtDir, CMN_PRB_TGT_DIR);
		addCard(cardCmnPrbTgtDirDsp, CMN_PRB_TGT_DIR_DSP);

		initialiseButton(cardCmnMissList, SETT_TARGET_DIR, 1);
		initialiseButton(cardCmnMissList, SETT_REM_UNWAN_FILES, 2);
		initialiseButton(cardCmnMissList, EDIT_REM_REMOVE_HOW, 3);
		initialiseButton(cardCmnPrbTgtDir, SETT_TARGET_DIR, 1);
		initialiseButton(cardCmnPrbTgtDir, SETT_REM_UNWAN_FILES, 2);
		initialiseButton(cardCmnPrbTgtDirDsp, SETT_TARGET_DIR, 1);
	}

	/**
	 * Initialises the instantiate module cards.
	 */
	private void initialiseInstMod() {
		addCard(cardInstMod, INST_MOD);
		addCard(cardInstModHow, INST_MOD_HOW);
		addCard(cardInstModDropDrop, INST_MOD_DRAG_DROP);

		initialiseButton(cardInstModHow, VIEW_FILE_MISS_TOP_MOD, 1);
		initialiseButton(cardInstModHow, INST_MOD_DRAG_DROP, 2);
		initialiseButton(cardInstModHow, CFG_TOP_MOD_HOW, 3);
	}

	/**
	 * Initialises the add module cards.
	 */
	private void initialiseAddMod() {
		addCard(cardAddFile, ADD_FILE);
		addCard(cardAddFileHow, ADD_FILE_HOW);
		addCard(cardAddFileMissNameOpt, ADD_FILE_MISS_NAME_OPT);
		addCard(cardAddFileErrors, ADD_FILE_ERRORS);

		initialiseButton(cardAddFileHow, ADD_FILE_MISS_NAME_OPT, 1);
		initialiseButton(cardAddFileHow, SETT_TARGET_DIR, 2);
		initialiseButton(cardAddFileMissNameOpt, CMN_PRB_TGT_DIR, 1);
		initialiseButton(cardAddFileErrors, CMN_PRB_INV_INPUT, 1);
	}

	/**
	 * Initialises the view panel cards.
	 */
	private void initialiseViewFiles() {
		addCard(cardViewFiles, VIEW_FILE);
		addCard(cardViewFilesEmpty, VIEW_FILE_EMPTY);
		addCard(cardViewFilesMissTopMod, VIEW_FILE_MISS_TOP_MOD);

		initialiseButton(cardViewFilesEmpty, CMN_PRB_MISS_LIST, 1);
	}

	/**
	 * Initialises the edit/remove module cards.
	 */
	private void initialiseEditRem() {
		addCard(cardEditRem, EDIT_REM);
		addCard(cardEditRemEmpty, EDIT_REM_EMPTY);
		addCard(cardEditRemEditHow, EDIT_REM_EDIT_HOW);
		addCard(cardEditRemRemoveHow, EDIT_REM_REMOVE_HOW);
		addCard(cardEditRemErrors, EDIT_REM_ERRORS);

		initialiseButton(cardEditRemEmpty, CMN_PRB_MISS_LIST, 1);
	}

	/**
	 * Initialises the configure modules and export cards.
	 */
	private void initialiseCfgMod() {
		addCard(cardCfgTopMod, CFG_TOP_MOD);
		addCard(cardCfgTopModHow, CFG_TOP_MOD_HOW);
		addCard(cardCfgTopModExpHow, CFG_TOP_MOD_EXP_HOW);
		addCard(cardCfgTopModButtons, CFG_TOP_MOD_BUTTONS);
		addCard(cardCfgTopModErrors, CFG_TOP_MOD_ERRORS);

		initialiseButton(cardCfgTopModHow, CFG_TOP_MOD_BUTTONS, 1);
		initialiseButton(cardCfgTopModExpHow, EXP_MOD_HOW, 1);
		initialiseButton(cardCfgTopModExpHow, EXP_MOD_ERRORS, 2);
		initialiseButton(cardCfgTopModButtons, EXP_MOD_ERRORS, 1);
	}

	/**
	 * Initialises the export module cards.
	 */
	private void initialiseExpMod() {
		addCard(cardExpMod, EXP_MOD);
		addCard(cardExpModHow, EXP_MOD_HOW);
		addCard(cardExpModErrors, EXP_MOD_ERRORS);

		initialiseButton(cardExpModHow, INST_MOD_HOW, 1);
	}

	/**
	 * Initialises the setting cards.
	 */
	private void initialiseSett() {
		addCard(cardSett, SETT);
		addCard(cardSettTargetDir, SETT_TARGET_DIR);
		addCard(cardSettRemUnwanFiles, SETT_REM_UNWAN_FILES);
		addCard(cardSellDelAllFiles, SETT_DEL_ALL_FILES);

		initialiseButton(cardSettTargetDir, CMN_PRB_TGT_DIR, 1);
	}

	/**
	 * Initialises the buttons we use for the related articles.
	 * 
	 * Used when we want to add buttons to the right side related articles.
	 * 
	 * There is a maximum of 3 buttons we can use.
	 * 
	 * @param panel       - the help panel we are adding the buttons to.
	 * @param text        - the text to set the button.
	 * @param buttonIndex - the button index we are adding.
	 */
	private void initialiseButton(HelpPanel panel, String text, int buttonIndex) {
		switch (buttonIndex) {
		case 1:
			panel.button1.setText(text);
			panel.button1.setEnabled(true);
			panel.button1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					jumpCard(text);
				}
			});
			break;
		case 2:
			panel.button2.setText(text);
			panel.button2.setVisible(true);
			panel.button2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					jumpCard(text);
				}
			});
			break;
		case 3:
			panel.button3.setText(text);
			panel.button3.setVisible(true);
			panel.button3.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					jumpCard(text);
				}
			});
			break;
		}
	}

	/**
	 * Adds a card to the content page and card layout.
	 * 
	 * @param panel - panel we are adding.
	 * @param name  - name of the panel we are adding.
	 */
	private void addCard(Component panel, String name) {
		model.addElement(name);
		panelCard.add(panel, name);
	}

	/**
	 * Jumps to the card (shows the card).
	 * 
	 * @param jump - the name of the card we want to show.
	 */
	private void jumpCard(String jump) {
		cardLayout.show(panelCard, jump);
		list.setSelectedValue(jump, true);
	}

	/***********
	 * GET/SET *
	 **********/

	public void setHelpCard(String card) {
		jumpCard(card);
	}

	public static String getInstModHow() {
		return INST_MOD_HOW;
	}

	public static String getViewFileEmpty() {
		return VIEW_FILE_EMPTY;
	}

	public static String getCmnPrbTgtDir() {
		return CMN_PRB_TGT_DIR;
	}

	public static String getSettTargetDir() {
		return SETT_TARGET_DIR;
	}

	public static String getSettRemUnwanFiles() {
		return SETT_REM_UNWAN_FILES;
	}

	public static String getSettDelAllFiles() {
		return SETT_DEL_ALL_FILES;
	}

	public static String getEditRemRemoveHow() {
		return EDIT_REM_REMOVE_HOW;
	}

	public static String getAddFileHow() {
		return ADD_FILE_HOW;
	}

	public static String getExpModHow() {
		return EXP_MOD_HOW;
	}

	public static String getViewFileMissTopMod() {
		return VIEW_FILE_MISS_TOP_MOD;
	}

	public static String getCfgTopModExpHow() {
		return CFG_TOP_MOD_EXP_HOW;
	}

	public static String getCfgTopModHow() {
		return CFG_TOP_MOD_HOW;
	}
}
