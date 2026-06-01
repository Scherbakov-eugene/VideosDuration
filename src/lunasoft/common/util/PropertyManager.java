package lunasoft.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.Properties;

import lunasoft.common.data.mt.MT;
import lunasoft.common.data.mt.MapMT;

public abstract class PropertyManager
{
	// Members.
	private MapMT messages = new MapMT(MT.TYPE_9_OBJECT, MT.TYPE_9_OBJECT, 10);
	private Properties properties = new Properties();

	/** Имя файла, содержащего общую часть сообщений. */
	public abstract String getFilenameMessages();
	public abstract String getFilenameProperties();

	// Messages / Methods ******************************************************
	/**
	 * Method try to load messages.
	 * @return Success.
	 */
	public boolean loadMessages()
	{
		boolean success = true;

		try
		{
			// Загружаем сообщения с очисткой карты.
			messages.loadUTF8(getFilenameMessages(), true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			success = false;
		}

		return success;
	}

	/**
	 * Method returns the message value.
	 * @param key Message key.
	 * @return    Value.
	 */
	public String getMessage(String key)
	{
		Object value = messages.getValueObject(key);

		if (value instanceof String)
		{
			return (String) value;
		}
		else
		{
			return null;
		}
	}

	// Properties / Methods ****************************************************
	/**
	 * Method try to load properties.
	 * @return Success.
	 */
	public boolean loadProperties()
	{
		boolean success = true;

		try
		{
			File file = new File(getFilenameProperties());
			FileInputStream fis = new FileInputStream(file);
			properties.load(fis);
		}
		catch (FileNotFoundException e)
		{
			success = false;
		}
		catch (IOException e)
		{
			success = false;
		}

		return success;
	}

	/**
	 * Method try to save properties.
	 * @return Success.
	 */
	public boolean saveProperties()
	{
		boolean success = true;

		try
		{
			File file = new File(getFilenameProperties());
			FileOutputStream fos = new FileOutputStream(file);
			properties.store(fos, null);
		}
		catch (IOException e)
		{
			success = false;
		}

		return success;
	}

	/**
	 * Return all properties.
	 * @return Properties.
	 */
	public Properties getProperties()
	{
		return properties;
	}

	/**
	 * Method returns the property value.
	 * @param key Property key.
	 * @return    Value.
	 */
	public String getProperty(String key)
	{
		Object value = properties.get(key);

		if (value instanceof String)
		{
			return (String) value;
		}
		else
		{
			return null;
		}
	}

	/**
	 * Возвращает int значение свойства, если удалось распарсить число. Иначе значение по-умолчанию.
	 * @param key          Ключ свойства.
	 * @param defaultValue Значение по-умолчанию.
	 * @return             int значение свойства.
	 */
	public int getPropertyInt(String key, int defaultValue)
	{
		int value = defaultValue;

		String property = getProperty(key);
		try
		{
			value = Integer.parseInt(property);
		}
		catch (Exception e)
		{
			// Can't read property. Default value will be used.
		}

		return value;
	}

	/**
	 * Возвращает boolean значение свойства, если удалось распарсить. Иначе значение по-умолчанию.
	 * @param key          Ключ свойства.
	 * @param defaultValue Значение по-умолчанию.
	 * @return             boolean значение свойства.
	 */
	public boolean getPropertyBoolean(String key, boolean defaultValue)
	{
		boolean value = defaultValue;

		String property = getProperty(key);
		try
		{
			value = Boolean.parseBoolean(property);
		}
		catch (Exception e)
		{
		}

		return value;
	}

	/**
	 * Method sets the property value.
	 * @param key   Property key.
	 * @param value Property value.
	 */
	public void setProperty(String key, String value)
	{
		if (value == null)
		{
			properties.remove(key);
		}
		else
		{
			properties.setProperty(key, value);
		}
	}

	/**
	 * Method sets the several properties.
	 * @param properties Properties to be set.
	 */
	public void setSeveralProperties(Properties properties)
	{
		for (int i = 0; i < properties.keySet().size(); i++)
		{
			Object obj = properties.keySet().toArray()[i];
			String key = (String) obj;
			String value = properties.get(key).toString();
			setProperty(key, value);
		}
	}

	public void printProperties()
	{
		System.out.println("Properties list for file \"" + getFilenameProperties() + "\":");
		for (Entry<Object, Object> entry : properties.entrySet())
		{
			System.out.println("    " + entry);
		}
		System.out.println("Properties end.");
	}
}
