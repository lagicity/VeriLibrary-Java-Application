package main;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import export.DialogExport;
import instmod.DialogInstMod;
import net.miginfocom.swing.MigLayout;
import panels.PanelViewMod;
import ui.UIImages;

/**
 * The first tab JPanel which displays the modules in VeriLibrary and has
 * options to export or configure and instantiate modules.
 */

public class TabView extends JPanel {
	private static final long serialVersionUID = 2085962143751343935L;

	/****************
	 * UI ELEMENTS *
	 ***************/

	public PanelViewMod panelViewMod = new PanelViewMod();

	/*************
	 * VARIABLES *
	 *************/

	/*************
	 * LISTENERS *
	 ************/

	private ActionListener buttonExpListener = new ButtonExpListener();
	private ActionListener buttonInstModListener = new ButtonInstModListener();

	/***********
	 * HELPERS *
	 ***********/

	private UIImages uiImages = new UIImages();

	/***************
	 * CONSTRUCTOR *
	 **************/

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new TabView();
			}
		});
	}

	public TabView() {
		setupUI();
	}

	/******************
	 * INITIALISATION *
	 *****************/

	private void setupUI() {
		setLayout(new MigLayout("", "[]", "[][][]"));

		// We setup the panel to view modules at the top.
		{
			JPanel panelView = new JPanel();
			panelView.setBorder(new TitledBorder(BorderFactory.createTitledBorder(" View Modules ")));
			panelView.add(panelViewMod);
			add(panelView, "cell 0 0,growx");
		}

		// We setup the middle panel to create a top module from instantiated modules.
		{
			JPanel panelInstMod = new JPanel();
			panelInstMod.setLayout(new BorderLayout(0, 0));
			panelInstMod.setBorder(new TitledBorder(BorderFactory.createTitledBorder(" Instantiate More Modules ")));
			add(panelInstMod, "cell 0 1,growx,aligny top");

			JButton buttonInstMod = new JButton("Instantiate Modules");
			buttonInstMod.setIcon(new ImageIcon(uiImages.getInstModSmall()));
			buttonInstMod.addActionListener(buttonInstModListener);
			panelInstMod.add(buttonInstMod, BorderLayout.EAST);

			JLabel labelInstModPrompt = new JLabel("     Create a top module with the input/output declarations of a module/instantiated modules.");
			panelInstMod.add(labelInstModPrompt, BorderLayout.CENTER);
		}

		// We setup the bottom panel to export modules.
		{
			JPanel panelExp = new JPanel();
			panelExp.setLayout(new BorderLayout(0, 0));
			panelExp.setBorder(new TitledBorder(BorderFactory.createTitledBorder(" Export Module(s) ")));
			add(panelExp, "cell 0 2,grow");

			JLabel labelExpPrompt = new JLabel("     Export modules and their respective files from VeriLibrary.");
			panelExp.add(labelExpPrompt, BorderLayout.WEST);

			JButton buttonExp = new JButton("Export Modules");
			buttonExp.setIcon(new ImageIcon(uiImages.getExportSmall()));
			buttonExp.addActionListener(buttonExpListener);
			panelExp.add(buttonExp, BorderLayout.EAST);
		}

	}

	/***********
	 * METHODS *
	 **********/

	/*************
	 * LISTENERS *
	 ************/

	/**
	 * Launches the dialog to create a top module.
	 */
	private class ButtonInstModListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			new DialogInstMod(getRootPane(), "Instantiate Modules", ModalityType.APPLICATION_MODAL);
		}
	}

	/**
	 * Launches the dialog to export modules.
	 */
	private class ButtonExpListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			new DialogExport(getRootPane(), "Export Files", ModalityType.APPLICATION_MODAL);
		}
	}

	/***********
	 * GET/SET *
	 **********/
}
