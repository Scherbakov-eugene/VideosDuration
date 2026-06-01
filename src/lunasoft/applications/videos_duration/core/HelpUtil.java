package lunasoft.applications.videos_duration.core;

import java.awt.Frame;

import javax.swing.JOptionPane;

import lunasoft.common.util.ImageManager;
import lunasoft.common.view.dialogs.TxtFileBrowserDialog;

public class HelpUtil
{
	public static final String NAME = "Videos duration";
	public static final String VERSION = "v1.0";
	public static final String NAME_VERSION = NAME + " " + VERSION;
	public static final String DATE_PERIOD = "28.05.2026 - 31.05.2026";
	public static final String AUTHOR = "Created by Scherbakov Eugene aka Rewers";
	public static final String VENDOR = "LunaSoft";

	/**
	 * Отображает окно "Справка".
	 * @param parent Родительское окно.
	 */
	public static void showHelpTopics(Frame parent)
	{
		String filler = S.fill(NAME_VERSION.length(), '-');

		StringBuilder sbText = new StringBuilder();
		sbText.append("/---").append(filler).append("---\\\n");
		sbText.append("|   ").append(NAME_VERSION).append("   |\n");
		sbText.append("\\---").append(filler).append("---/\n");
		sbText.append("\n");
		sbText.append("RUSSIAN (РУССКИЙ):\n");
		sbText.append("Оценивает длительность видосов.\n");
		sbText.append("\n");
		sbText.append("ENGLISH:\n");
		sbText.append("Estimates the duration of videos.\n");

		TxtFileBrowserDialog dialog = new TxtFileBrowserDialog(parent,
				S.propertyManager.getMessage(PropertyManagerImpl.MSG_MENU_HELP_HELP_TOPICS),
				sbText.toString(), 480, 360);

		dialog.setIconImage(ImageManager.readImageRelativePathPrefixCommon(ImageManager.IMG_LINK_HELP_HELP_TOPICS));
		dialog.setVisible(true);
	}

	/**
	 * Отображает окно "О программе".
	 * @param parent Родительское окно.
	 */
	public static void showAbout(Frame parent)
	{
		JOptionPane.showMessageDialog(parent, NAME_VERSION + ".\n" +
				AUTHOR + ".\n" +
				VENDOR + "™ © " + DATE_PERIOD + ".\n",
				S.propertyManager.getMessage(PropertyManagerImpl.MSG_MENU_HELP_ABOUT) + "...",
				JOptionPane.INFORMATION_MESSAGE);
	}
}
