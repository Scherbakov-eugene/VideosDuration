package lunasoft.common.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Created  : 16.12.2009, 12:58.
 * Modified : 13.08.2013, 23:23.
 * Modified : 14.08.2013, 14:08.
 * Modified : 16.08.2013, 14:55.
 * Modified : 18.08.2013, 16:31.
 * Modified : 19.08.2013, 22:10.
 * Modified : 20.08.2013, 14:52.
 * Modified : 05.09.2013, 09:38.
 * Modified : 05.09.2013, 10:07.
 * Modified : 06.09.2013, 22:47.
 * Modified : 24.08.2014, 11:52.
 * Modified : 07.03.2018, 18:05.
 * Modified : 08.03.2018, 11:17.
 * Modified : 04.07.2018, 17:12.
 * Modified : 07.07.2018, 20:24.
 */
public class ImageManager
{
	// Constants.
	private static final String IMAGE_PATH_COMMON = "/lunasoft/common/images/";

	// Images.
	public static final String IMG_APPLICATION = "16x16/application.png";
	public static final String IMG_BOOK_HELP = "16x16/book_help.png";
	public static final String IMG_FILE_EXIT = "16x16/file_exit.png";
	public static final String IMG_OPTIONS_SETTINGS = "16x16/settings_1.png";
	public static final String IMG_INFO = "16x16/info.png";
	public static final String IMG_TEXTFIELD_PASSWORD = "16x16/textfield_password.png";

	// Image links.
	public static final String IMG_LINK_HELP_HELP_TOPICS = IMG_BOOK_HELP;
	public static final String IMG_LINK_HELP_ABOUT = IMG_INFO;

	public static BufferedImage readImageRelativePathPrefixCommon(String fileName)
	{
		return readImageRelativePath(IMAGE_PATH_COMMON + fileName);
	}

	public static BufferedImage readImageRelativePath(String fileName)
	{
		BufferedImage img;
		URL imageURL = ImageManager.class.getResource(fileName);

		if (imageURL != null)
		{
			try
			{
				img = ImageIO.read(imageURL);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				img = null;
			}
		}
		else
		{
			img = null;
		}

		return img;
	}

	public static BufferedImage readImageAbsolutePath(String fileName)
	{
		if (fileName == null)
		{
			return null;
		}

		File file = new File(fileName);
		if (!file.exists())
		{
			throw new RuntimeException("Image file not found.\n"
					+ "File name = \"" + fileName + "\"\n"
					+ "Absolute path = \"" + file.getAbsolutePath() + "\"");
		}

		BufferedImage img;
		try
		{
			img = ImageIO.read(file);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			img = null;
		}

		return img;
	}

	public static BufferedImage readImage(File file)
	{
		if (file == null)
		{
			return null;
		}

		if (!file.exists())
		{
			throw new RuntimeException("Image file not found.\n"
					+ "File = \"" + file + "\"\n"
					+ "Absolute path = \"" + file.getAbsolutePath() + "\"");
		}

		BufferedImage img;
		try
		{
			img = ImageIO.read(file);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			img = null;
			throw new RuntimeException("Error reading image file.\n"
					+ "File = \"" + file + "\"\n"
					+ "Absolute path = \"" + file.getAbsolutePath() + "\"");
		}

		return img;
	}

	/**
	 * Загружает изображение из потока. Использует int для размера данных.
	 * @param dis        Поток данных.
	 * @return           Изображение.
	 * @throws Exception Ошибка.
	 */
	public static BufferedImage readImage(DataInputStream dis) throws Exception
	{
		// Читаем размер данных.
		int length = dis.readInt();

		if (length == -1)
		{
			return null;
		}
		else
		{
			// Создаём байтовый массив для данных.
			byte[] buffer = new byte[length];
			// Читаем данные из входного потока в массив.
			int readed = 0;
			do
			{
				int readedInternal = dis.read(buffer, readed, length - readed);
				if (readedInternal < 1)
				{
					throw new Exception("Reading bytes error. Length = " + length + ".");
				}
				readed = readed + readedInternal;
			} while (readed != length);

			// Создаём входной поток на базе байтового массива.
			ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
			// Читаем из него изображение в формате png.
			BufferedImage image = ImageIO.read(bais);
			// Закрываем поток.
			bais.close();
			// Возвращаем прочитанное изображение.
			return image;
		}
	}

	public static ImageIcon readImageIconRelativePath(String fileName)
	{
		try
		{
			return new ImageIcon(readImageRelativePathPrefixCommon(fileName));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException("readImageIconRelativePath(...): can't read file \"" + fileName + "\".");
		}
	}
}
