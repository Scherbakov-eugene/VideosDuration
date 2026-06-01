package lunasoft.applications.videos_duration.core;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import lunasoft.common.util.ImageManager;
import lunasoft.common.util.PropertyManager;

public class S
{
	/** Кодировка файлов "UTF-8". */
	public static final String ENCODING_UTF_8 = "UTF-8";

	/** Генератор псевдослучайных чисел. */
	public static final Random RANDOM = new Random(System.currentTimeMillis());

	public static final PropertyManager propertyManager = new PropertyManagerImpl();

	public static BufferedImage getIconImage()
	{
		return ImageManager.readImageRelativePathPrefixCommon(ImageManager.IMG_APPLICATION);
	}

	public static Icon getIcon()
	{
		return new ImageIcon(getIconImage());
	}

	/**
	 * Возвращает случайное число из диапазона [from, to].
	 */
	public static int getRandom(int from, int to)
	{
		if (from > to)
		{
			int temp = from;
			from = to;
			to = temp;
		}

		int difference = to - from;

		return from + RANDOM.nextInt(difference + 1);
	}

	public static void setCenteredRelativelyDesktop(Window window)
	{
		Dimension scr = window.getToolkit().getScreenSize();
		Dimension wnd = window.getSize();
		Insets insets = window.getToolkit().getScreenInsets(window.getGraphicsConfiguration());
		int x = (scr.width - insets.left - insets.right - wnd.width) / 2;
		int y = (scr.height - insets.top - insets.bottom - wnd.height) / 2;
		window.setLocation(insets.left + x, insets.top + y);
	}

	public static void setCenteredRelativelyParent(Window parentWindow, Window childWindow)
	{
		Dimension scr = parentWindow.getSize();
		Dimension wnd = childWindow.getSize();
		int x = (scr.width - wnd.width) / 2;
		int y = (scr.height - wnd.height) / 2;
		Point p = parentWindow.getLocation();
		childWindow.setLocation(p.x + x, p.y + y);
	}

	/**
	 * Генерирует строку заданной ширины, состоящей из заданного символа (заполнителя).
	 * @param width    Нужная ширина строки.
	 * @param filler   Заполнитель.
	 * @return         Сгенерированная строка.
	 */
	public static String fill(int width, char filler)
	{
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < width; i++)
		{
			sb.append(filler);
		}

		return sb.toString();
	}
}
