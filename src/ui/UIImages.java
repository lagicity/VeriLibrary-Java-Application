package ui;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import helper.HelperAlerts;

/**
 * This class contains all the images used in the application.
 */
public class UIImages {
	// Icon for the main application.
	private BufferedImage mainIcon;

	// Icons for the main view.
	private BufferedImage addSmall;
	private BufferedImage addBig;
	private BufferedImage helpSmall;
	private BufferedImage helpSmallHover;
	private BufferedImage helpBig;
	private BufferedImage editBig;
	private BufferedImage editSmall;
	private BufferedImage settingsSmall;
	private BufferedImage settingsBig;
	private BufferedImage instModSmall;
	private BufferedImage instModBig;

	// Icons for the instantiating module dialog.
	private BufferedImage remove;
	private BufferedImage removeHover;
	private BufferedImage replace;
	private BufferedImage replaceHover;
	private BufferedImage editWire;
	private BufferedImage editWireHover;

	// Icons when comparing modules.
	private BufferedImage upArrowRed;
	private BufferedImage upArrowGreen;
	private BufferedImage downArrowRed;
	private BufferedImage horizontalArrow;

	// Icon for refreshing the JLists.
	private BufferedImage refreshSmall;

	// Icon for exporting files.
	private BufferedImage exportSmall;

	// Icon for ghosted image.
	private BufferedImage ghostedImg;

	// Icon for UILoading JPanel.
	private BufferedImage loading;
	private BufferedImage success;
	private BufferedImage fail;

	private HelperAlerts helperAlerts = new HelperAlerts();

	public UIImages() {
		try {
			mainIcon = ImageIO.read(this.getClass().getResource("/main_icon.png"));

			addBig = ImageIO.read(this.getClass().getResource("/add_big.png"));
			addSmall = ImageIO.read(this.getClass().getResource("/add_small.png"));
			editBig = ImageIO.read(this.getClass().getResource("/edit_big.png"));
			editSmall = ImageIO.read(this.getClass().getResource("/edit_small.png"));
			settingsBig = ImageIO.read(this.getClass().getResource("/settings_big.png"));
			settingsSmall = ImageIO.read(this.getClass().getResource("/settings_small.png"));
			helpBig = ImageIO.read(this.getClass().getResource("/help_big.png"));
			helpSmall = ImageIO.read(this.getClass().getResource("/help_small.png"));
			helpSmallHover = ImageIO.read(this.getClass().getResource("/help_small_hover.png"));
			instModBig = ImageIO.read(this.getClass().getResource("/inst_mod_big.png"));
			instModSmall = ImageIO.read(this.getClass().getResource("/inst_mod_small.png"));

			remove = ImageIO.read(this.getClass().getResource("/remove.png"));
			removeHover = ImageIO.read(this.getClass().getResource("/remove_hover.png"));
			replace = ImageIO.read(this.getClass().getResource("/replace.png"));
			replaceHover = ImageIO.read(this.getClass().getResource("/replace_hover.png"));
			editWire = ImageIO.read(this.getClass().getResource("/edit_wire.png"));
			editWireHover = ImageIO.read(this.getClass().getResource("/edit_wire_hover.png"));

			upArrowRed = ImageIO.read(getClass().getResource("/arrow_up_red.png"));
			upArrowGreen = ImageIO.read(getClass().getResource("/arrow_up_green.png"));
			downArrowRed = ImageIO.read(getClass().getResource("/arrow_down_red.png"));
			horizontalArrow = ImageIO.read(getClass().getResource("/arrow_horizontal_black.png"));

			refreshSmall = ImageIO.read(getClass().getResource("/refresh_small.png"));

			exportSmall = ImageIO.read(getClass().getResource("/export_small.png"));

			ghostedImg = ImageIO.read(getClass().getResource("/ghosted_image.png"));

			loading = ImageIO.read(this.getClass().getResource("/loading_circle.gif"));
			success = ImageIO.read(this.getClass().getResource("/loading_success.png"));
			fail = ImageIO.read(this.getClass().getResource("/loading_fail.png"));

		} catch (IOException e) {
			helperAlerts.imageNotFound();
			e.printStackTrace();
		}
	}

	public BufferedImage getAddSmall() {
		return addSmall;
	}

	public BufferedImage getHelpSmall() {
		return helpSmall;
	}

	public BufferedImage getEditBig() {
		return editBig;
	}

	public BufferedImage getSettingsSmall() {
		return settingsSmall;
	}

	public BufferedImage getMain_icon() {
		return mainIcon;
	}

	public BufferedImage getRemove() {
		return remove;
	}

	public BufferedImage getReplace() {
		return replace;
	}

	public BufferedImage getRemoveHover() {
		return removeHover;
	}

	public BufferedImage getReplaceHover() {
		return replaceHover;
	}

	public BufferedImage getUpArrowGreen() {
		return upArrowGreen;
	}

	public BufferedImage getDownArrowRed() {
		return downArrowRed;
	}

	public BufferedImage getHorizontalArrow() {
		return horizontalArrow;
	}

	public BufferedImage getRefreshSmall() {
		return refreshSmall;
	}

	public BufferedImage getLoading() {
		return loading;
	}

	public BufferedImage getSuccess() {
		return success;
	}

	public BufferedImage getFail() {
		return fail;
	}

	public BufferedImage getGhostedImg() {
		return ghostedImg;
	}

	public BufferedImage getInstModBig() {
		return instModBig;
	}

	public BufferedImage getInstModSmall() {
		return instModSmall;
	}

	public BufferedImage getAddBig() {
		return addBig;
	}

	public BufferedImage getEditSmall() {
		return editSmall;
	}

	public BufferedImage getSettingsBig() {
		return settingsBig;
	}

	public BufferedImage getHelpBig() {
		return helpBig;
	}

	public BufferedImage getExportSmall() {
		return exportSmall;
	}

	public BufferedImage getHelpSmallHover() {
		return helpSmallHover;
	}

	public BufferedImage getEditWire() {
		return editWire;
	}

	public BufferedImage getEditWireHover() {
		return editWireHover;
	}

	public BufferedImage getUpArrowRed() {
		return upArrowRed;
	}
}
