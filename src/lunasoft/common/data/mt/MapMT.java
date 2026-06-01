package lunasoft.common.data.mt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import lunasoft.applications.videos_duration.core.S;

/**
 * Map multi type.
 * Карта (ключ -> значение).
 *
 * Created : 16.12.2009, 14:46:06.
 * Modified: 27.07.2013, 10:10:10.
 * Modified: 18.08.2013, 20:55:00.
 * Modified: 19.08.2013, 17:15:00.
 * Modified: 20.08.2013, 17:52:00.
 * Modified: 20.08.2013, 20:57:00.
 * Modified: 21.02.2013, 20:00:00.
 * Modified: 20.08.2014, 17:45:00. Добавил функцию loadUTF8(...).
 * Modified: 24.08.2014, 19:01:00. Пофиксил sortByKeys().
 */
public class MapMT implements MT
{
	/** Тип ключей. */
	public byte keysType = TYPE_1_BYTE;
	/** Тип значений. */
	public byte valuesType = TYPE_1_BYTE;

	/** Ключи. */
	private Object keys;
	/** Значения. */
	private Object values;
	/** Общий размер массивов. */
	private int sizeTotal;
	/** Текущий размер занятых данных. */
	public int sizeCurrent;

	public MapMT(byte dataTypeKeys, byte dataTypeValues, int sizeTotal)
	{
		this.keysType = dataTypeKeys;
		this.valuesType = dataTypeValues;
		this.sizeTotal = sizeTotal;
		this.sizeCurrent = 0;

		// Ключи.
		if (dataTypeKeys == TYPE_1_BYTE)
		{
			keys = new byte[sizeTotal];
		}
		else if (dataTypeKeys == TYPE_2_SHORT)
		{
			keys = new short[sizeTotal];
		}
		else if (dataTypeKeys == TYPE_3_INT)
		{
			keys = new int[sizeTotal];
		}
		else if (dataTypeKeys == TYPE_4_LONG)
		{
			keys = new long[sizeTotal];
		}
		else if (dataTypeKeys == TYPE_6_CHAR)
		{
			keys = new char[sizeTotal];
		}
		else if (dataTypeKeys == TYPE_7_FLOAT)
		{
			keys = new float[sizeTotal];
		}
		else if (dataTypeKeys == TYPE_9_OBJECT)
		{
			keys = new Object[sizeTotal];
		}
		else
		{
			throw new RuntimeException(MAP_MT + ERROR_KEY_TYPE_ERROR + dataTypeKeys);
		}

		// Значения.
		if (dataTypeValues == TYPE_1_BYTE)
		{
			values = new byte[sizeTotal];
		}
		else if (dataTypeValues == TYPE_2_SHORT)
		{
			values = new short[sizeTotal];
		}
		else if (dataTypeValues == TYPE_3_INT)
		{
			values = new int[sizeTotal];
		}
		else if (dataTypeValues == TYPE_4_LONG)
		{
			values = new long[sizeTotal];
		}
		else if (dataTypeValues == TYPE_6_CHAR)
		{
			values = new char[sizeTotal];
		}
		else if (dataTypeValues == TYPE_7_FLOAT)
		{
			values = new float[sizeTotal];
		}
		else if (dataTypeValues == TYPE_9_OBJECT)
		{
			values = new Object[sizeTotal];
		}
		else
		{
			throw new RuntimeException(MAP_MT + ERROR_VALUE_TYPE_ERROR + dataTypeValues);
		}
	}

	public byte[] getKeysByte()
	{
		return (byte[]) keys;
	}

	public short[] getKeysShort()
	{
		return (short[]) keys;
	}

	public int[] getKeysInt()
	{
		return (int[]) keys;
	}

	public float[] getKeysFloat()
	{
		return (float[]) keys;
	}

	public Object[] getKeysObject()
	{
		return (Object[]) keys;
	}

	public long[] getKeysLong()
	{
		return (long[]) keys;
	}

	public char[] getKeysChar()
	{
		return (char[]) keys;
	}

	public byte[] getValuesByte()
	{
		return (byte[]) values;
	}

	public short[] getValuesShort()
	{
		return (short[]) values;
	}

	public int[] getValuesInt()
	{
		return (int[]) values;
	}

	public float[] getValuesFloat()
	{
		return (float[]) values;
	}

	public Object[] getValuesObject()
	{
		return (Object[]) values;
	}

	public long[] getValuesLong()
	{
		return (long[]) values;
	}

	public char[] getValuesChar()
	{
		return (char[]) values;
	}

	/**
	 * Возвращает индекс ключа.
	 * @param key Ключ.
	 * @return    Индекс ключа (-1, если нет).
	 */
	public int getKeyIndex(int key)
	{
		int keyIndex = -1;

		for (int i = 0; i < sizeCurrent; i++)
		{
			if (keysType == TYPE_1_BYTE)
			{
				if (((byte[]) keys)[i] == key)
				{
					keyIndex = i;
					break;
				}
			}
			else if (keysType == TYPE_2_SHORT)
			{
				if (((short[]) keys)[i] == key)
				{
					keyIndex = i;
					break;
				}
			}
			else if (keysType == TYPE_3_INT)
			{
				if (((int[]) keys)[i] == key)
				{
					keyIndex = i;
					break;
				}
			}
			else if (keysType == TYPE_6_CHAR)
			{
				if (((char[]) keys)[i] == key)
				{
					keyIndex = i;
					break;
				}
			}
			else
			{
				throw new RuntimeException(MAP_MT + ERROR_KEY_TYPE_ERROR + keysType);
			}
		}

		return keyIndex;
	}

	/**
	 * Возвращает индекс ключа.
	 * @param key Ключ.
	 * @return    Индекс ключа (-1, если нет).
	 */
	public int getKeyIndex(float key)
	{
		int keyIndex = -1;

		for (int i = 0; i < sizeCurrent; i++)
		{
			if (keysType == TYPE_7_FLOAT)
			{
				if (((float[]) keys)[i] == key)
				{
					keyIndex = i;
					break;
				}
			}
			else
			{
				throw new RuntimeException(MAP_MT + ERROR_KEY_TYPE_ERROR + keysType);
			}
		}

		return keyIndex;
	}

	/**
	 * Возвращает индекс ключа.
	 * @param key Ключ.
	 * @return    Индекс ключа (-1, если нет).
	 */
	public int getKeyIndex(Object key)
	{
		int keyIndex = -1;

		for (int i = 0; i < sizeCurrent; i++)
		{
			if (keysType == TYPE_9_OBJECT)
			{
				if (
						(key == null && ((Object[]) keys)[i] == null) ||
						(key != null && key.equals(((Object[]) keys)[i]))
					)
				{
					keyIndex = i;
					break;
				}
			}
			else
			{
				throw new RuntimeException(MAP_MT + ERROR_KEY_TYPE_ERROR + keysType);
			}
		}

		return keyIndex;
	}

	/**
	 * Возвращает индекс значения.
	 * @param value Значение.
	 * @return      Индекс значения (-1, если нет).
	 */
	public int getValueIndex(int value)
	{
		int valueIndex = -1;

		if (valuesType == TYPE_1_BYTE)
		{
			byte[] valuesByte = (byte[]) values;
			for (int i = 0; i < sizeCurrent; i++)
			{
				if (value == valuesByte[i])
				{
					valueIndex = i;
					break;
				}
			}
		}
		else if (valuesType == TYPE_2_SHORT)
		{
			short[] valuesShort = (short[]) values;
			for (int i = 0; i < sizeCurrent; i++)
			{
				if (value == valuesShort[i])
				{
					valueIndex = i;
					break;
				}
			}
		}
		else if (valuesType == TYPE_3_INT)
		{
			int[] valuesInt = (int[]) values;
			for (int i = 0; i < sizeCurrent; i++)
			{
				if (value == valuesInt[i])
				{
					valueIndex = i;
					break;
				}
			}
		}
		else if (valuesType == TYPE_6_CHAR)
		{
			char[] valuesChar = (char[]) values;
			for (int i = 0; i < sizeCurrent; i++)
			{
				if (value == valuesChar[i])
				{
					valueIndex = i;
					break;
				}
			}
		}
		else
		{
			throw new RuntimeException(MAP_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
		}

		return valueIndex;
	}

	/**
	 * Возвращает индекс значения.
	 * @param value Значение.
	 * @return      Индекс значения (-1, если нет).
	 */
	public int getValueIndex(float value)
	{
		int valueIndex = -1;

		if (valuesType == TYPE_7_FLOAT)
		{
			float[] valuesFloat = (float[]) values;
			for (int i = 0; i < sizeCurrent; i++)
			{
				if (Float.compare(value, valuesFloat[i]) == 0)
				{
					valueIndex = i;
					break;
				}
			}
		}
		else
		{
			throw new RuntimeException(MAP_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
		}

		return valueIndex;
	}

	/**
	 * Возвращает индекс значения.
	 * @param value Значение.
	 * @return      Индекс значения (-1, если нет).
	 */
	public int getValueIndex(Object value)
	{
		int valueIndex = -1;

		if (valuesType == TYPE_9_OBJECT)
		{
			Object[] valuesObject = (Object[]) values;
			for (int i = 0; i < sizeCurrent; i++)
			{
				if (
						(value == null && valuesObject[i] == null) ||
						(value != null && value.equals(valuesObject[i]))
					)
				{
					valueIndex = i;
					break;
				}
			}
		}
		else
		{
			throw new RuntimeException(MAP_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
		}

		return valueIndex;
	}

	/**
	 * Проверить, есть ли ключ в карте.
	 * @param key Ключ.
	 * @return    true/false.
	 */
	public boolean containsKey(int key)
	{
		int keyIndex = getKeyIndex(key);
		return keyIndex != -1;
	}

	/**
	 * Проверить, есть ли ключ в карте.
	 * @param key Ключ.
	 * @return    true/false.
	 */
	public boolean containsKey(float key)
	{
		int keyIndex = getKeyIndex(key);
		return keyIndex != -1;
	}

	/**
	 * Проверить, есть ли ключ в карте.
	 * @param key Ключ.
	 * @return    true/false.
	 */
	public boolean containsKey(Object key)
	{
		int keyIndex = getKeyIndex(key);
		return keyIndex != -1;
	}

	/**
	 * Проверить, есть ли значение в карте.
	 * @param value Значение.
	 * @return      true/false.
	 */
	public boolean containsValue(int value)
	{
		int valueIndex = getValueIndex(value);
		return valueIndex != -1;
	}

	/**
	 * Проверить, есть ли значение в карте.
	 * @param value Значение.
	 * @return      true/false.
	 */
	public boolean containsValue(float value)
	{
		int valueIndex = getValueIndex(value);
		return valueIndex != -1;
	}

	/**
	 * Проверить, есть ли значение в карте.
	 * @param value Значение.
	 * @return      true/false.
	 */
	public boolean containsValue(Object value)
	{
		int valueIndex = getValueIndex(value);
		return valueIndex != -1;
	}

	/**
	 * Увеличивает общий размер карты.
	 */
	private void incSize()
	{
		// Ключи.
		if (keysType == TYPE_1_BYTE)
		{
			// Инициализировать новые массивы.
			byte[] keysNew = new byte[sizeTotal + SIZE_INCREMENT_VALUE];
			// Переписать все значимые данные из старых массивов в новые.
			for (int i = 0; i < sizeCurrent; i++)
			{
				keysNew[i] = ((byte[]) keys)[i];
			}
			// Установить для использования новые массивы.
			keys = keysNew;
		}
		else if (keysType == TYPE_2_SHORT)
		{
			// Инициализировать новые массивы.
			short[] keysNew = new short[sizeTotal + SIZE_INCREMENT_VALUE];
			// Переписать все значимые данные из старых массивов в новые.
			for (int i = 0; i < sizeCurrent; i++)
			{
				keysNew[i] = ((short[]) keys)[i];
			}
			// Установить для использования новые массивы.
			keys = keysNew;
		}
		else if (keysType == TYPE_3_INT)
		{
			// Инициализировать новые массивы.
			int[] keysNew = new int[sizeTotal + SIZE_INCREMENT_VALUE];
			// Переписать все значимые данные из старых массивов в новые.
			for (int i = 0; i < sizeCurrent; i++)
			{
				keysNew[i] = ((int[]) keys)[i];
			}
			// Установить для использования новые массивы.
			keys = keysNew;
		}
		else if (keysType == TYPE_7_FLOAT)
		{
			// Инициализировать новые массивы.
			float[] keysNew = new float[sizeTotal + SIZE_INCREMENT_VALUE];
			// Переписать все значимые данные из старых массивов в новые.
			for (int i = 0; i < sizeCurrent; i++)
			{
				keysNew[i] = ((float[]) keys)[i];
			}
			// Установить для использования новые массивы.
			keys = keysNew;
		}
		else if (keysType == TYPE_9_OBJECT)
		{
			// Инициализировать новые массивы.
			Object[] keysNew = new Object[sizeTotal + SIZE_INCREMENT_VALUE];
			// Переписать все значимые данные из старых массивов в новые.
			for (int i = 0; i < sizeCurrent; i++)
			{
				keysNew[i] = ((Object[]) keys)[i];
			}
			// Установить для использования новые массивы.
			keys = keysNew;
		}
		else if (keysType == TYPE_4_LONG)
		{
			// Инициализировать новые массивы.
			long[] keysNew = new long[sizeTotal + SIZE_INCREMENT_VALUE];
			// Переписать все значимые данные из старых массивов в новые.
			for (int i = 0; i < sizeCurrent; i++)
			{
				keysNew[i] = ((long[]) keys)[i];
			}
			// Установить для использования новые массивы.
			keys = keysNew;
		}
		else if (keysType == TYPE_6_CHAR)
		{
			// Инициализировать новые массивы.
			char[] keysNew = new char[sizeTotal + SIZE_INCREMENT_VALUE];
			// Переписать все значимые данные из старых массивов в новые.
			for (int i = 0; i < sizeCurrent; i++)
			{
				keysNew[i] = ((char[]) keys)[i];
			}
			// Установить для использования новые массивы.
			keys = keysNew;
		}
		else
		{
			throw new RuntimeException(MAP_MT + ERROR_KEY_TYPE_ERROR + keysType);
		}

		// Значения.
		if (valuesType == TYPE_1_BYTE)
		{
			// Инициализировать новые массивы.
			byte[] valuesNew = new byte[sizeTotal + SIZE_INCREMENT_VALUE];
			// Переписать все значимые данные из старых массивов в новые.
			for (int i = 0; i < sizeCurrent; i++)
			{
				valuesNew[i] = ((byte[]) values)[i];
			}
			// Установить для использования новые массивы.
			values = valuesNew;
		}
		else if (valuesType == TYPE_2_SHORT)
		{
			// Инициализировать новые массивы.
			short[] valuesNew = new short[sizeTotal + SIZE_INCREMENT_VALUE];
			// Переписать все значимые данные из старых массивов в новые.
			for (int i = 0; i < sizeCurrent; i++)
			{
				valuesNew[i] = ((short[]) values)[i];
			}
			// Установить для использования новые массивы.
			values = valuesNew;
		}
		else if (valuesType == TYPE_3_INT)
		{
			// Инициализировать новые массивы.
			int[] valuesNew = new int[sizeTotal + SIZE_INCREMENT_VALUE];
			// Переписать все значимые данные из старых массивов в новые.
			for (int i = 0; i < sizeCurrent; i++)
			{
				valuesNew[i] = ((int[]) values)[i];
			}
			// Установить для использования новые массивы.
			values = valuesNew;
		}
		else if (valuesType == TYPE_7_FLOAT)
		{
			// Инициализировать новые массивы.
			float[] valuesNew = new float[sizeTotal + SIZE_INCREMENT_VALUE];
			// Переписать все значимые данные из старых массивов в новые.
			for (int i = 0; i < sizeCurrent; i++)
			{
				valuesNew[i] = ((float[]) values)[i];
			}
			// Установить для использования новые массивы.
			values = valuesNew;
		}
		else if (valuesType == TYPE_9_OBJECT)
		{
			// Инициализировать новые массивы.
			Object[] valuesNew = new Object[sizeTotal + SIZE_INCREMENT_VALUE];
			// Переписать все значимые данные из старых массивов в новые.
			for (int i = 0; i < sizeCurrent; i++)
			{
				valuesNew[i] = ((Object[]) values)[i];
			}
			// Установить для использования новые массивы.
			values = valuesNew;
		}
		else if (valuesType == TYPE_4_LONG)
		{
			// Инициализировать новые массивы.
			long[] valuesNew = new long[sizeTotal + SIZE_INCREMENT_VALUE];
			// Переписать все значимые данные из старых массивов в новые.
			for (int i = 0; i < sizeCurrent; i++)
			{
				valuesNew[i] = ((long[]) values)[i];
			}
			// Установить для использования новые массивы.
			values = valuesNew;
		}
		else if (valuesType == TYPE_6_CHAR)
		{
			// Инициализировать новые массивы.
			char[] valuesNew = new char[sizeTotal + SIZE_INCREMENT_VALUE];
			// Переписать все значимые данные из старых массивов в новые.
			for (int i = 0; i < sizeCurrent; i++)
			{
				valuesNew[i] = ((char[]) values)[i];
			}
			// Установить для использования новые массивы.
			values = valuesNew;
		}
		else
		{
			throw new RuntimeException(MAP_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
		}

		// Итерируем общий размер.
		sizeTotal += SIZE_INCREMENT_VALUE;
	}
	// TODO выше расположены безопасные функции.

	/**
	 * Обнуляет и очищает карту.
	 */
	public void clear()
	{
		if (keysType == TYPE_1_BYTE)
		{
			for (int i = 0; i < sizeCurrent; i++)
			{
				((byte[]) keys)[i] = 0;
			}
		}
		else if (keysType == TYPE_2_SHORT)
		{
			for (int i = 0; i < sizeCurrent; i++)
			{
				((short[]) keys)[i] = 0;
			}
		}
		else if (keysType == TYPE_3_INT)
		{
			for (int i = 0; i < sizeCurrent; i++)
			{
				((int[]) keys)[i] = 0;
			}
		}
		else if (keysType == TYPE_7_FLOAT)
		{
			for (int i = 0; i < sizeCurrent; i++)
			{
				((float[]) keys)[i] = 0;
			}
		}
		else if (keysType == TYPE_9_OBJECT)
		{
			for (int i = 0; i < sizeCurrent; i++)
			{
				((Object[]) keys)[i] = null;
			}
		}
		else if (keysType == TYPE_4_LONG)
		{
			for (int i = 0; i < sizeCurrent; i++)
			{
				((long[]) keys)[i] = 0;
			}
		}
		else if (keysType == TYPE_6_CHAR)
		{
			for (int i = 0; i < sizeCurrent; i++)
			{
				((char[]) keys)[i] = 0;
			}
		}
		else
		{
			throw new RuntimeException(MAP_MT + ERROR_KEY_TYPE_ERROR + keysType);
		}

		if (valuesType == TYPE_1_BYTE)
		{
			for (int i = 0; i < sizeCurrent; i++)
			{
				((byte[]) values)[i] = 0;
			}
		}
		else if (valuesType == TYPE_2_SHORT)
		{
			for (int i = 0; i < sizeCurrent; i++)
			{
				((short[]) values)[i] = 0;
			}
		}
		else if (valuesType == TYPE_3_INT)
		{
			for (int i = 0; i < sizeCurrent; i++)
			{
				((int[]) values)[i] = 0;
			}
		}
		else if (valuesType == TYPE_7_FLOAT)
		{
			for (int i = 0; i < sizeCurrent; i++)
			{
				((float[]) values)[i] = 0;
			}
		}
		else if (valuesType == TYPE_9_OBJECT)
		{
			for (int i = 0; i < sizeCurrent; i++)
			{
				((Object[]) values)[i] = null;
			}
		}
		else if (valuesType == TYPE_4_LONG)
		{
			for (int i = 0; i < sizeCurrent; i++)
			{
				((long[]) values)[i] = 0;
			}
		}
		else if (valuesType == TYPE_6_CHAR)
		{
			for (int i = 0; i < sizeCurrent; i++)
			{
				((char[]) values)[i] = 0;
			}
		}
		else
		{
			throw new RuntimeException(MAP_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
		}

		// Обнуляем размер.
		sizeCurrent = 0;
	}

	/**
	 * Добавляет пару [key, value].
	 * Если ключ уже есть, то заменяем значение.
	 * @param key   Ключ.
	 * @param value Значение.
	 */
	public MapMT put(int key, int value)
	{
		int keyIndex = getKeyIndex(key);

		// Если ключ уже есть.
		if (keyIndex != -1)
		{
			// Заменяем значение.
			if (valuesType == TYPE_1_BYTE)
			{
				((byte[]) values)[keyIndex] = (byte) value;
			}
			else if (valuesType == TYPE_2_SHORT)
			{
				((short[]) values)[keyIndex] = (short) value;
			}
			else if (valuesType == TYPE_3_INT)
			{
				((int[]) values)[keyIndex] = value;
			}
			else if (valuesType == TYPE_6_CHAR)
			{
				((char[]) values)[keyIndex] = (char) value;
			}
			else
			{
				throw new RuntimeException(MAP_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
			}

			//throw new RuntimeException(MAP_MT + ERROR_KEY_ALREADY_EXISTS);
		}
		// Иначе проверить на наличие свободного места.
		else
		{
			// Если места нет, то увеличить общий размер карты.
			if (sizeCurrent >= sizeTotal)
			{
				incSize();
			}

			if (keysType == TYPE_1_BYTE)
			{
				((byte[]) keys)[sizeCurrent] = (byte) key;
			}
			else if (keysType == TYPE_2_SHORT)
			{
				((short[]) keys)[sizeCurrent] = (short) key;
			}
			else if (keysType == TYPE_3_INT)
			{
				((int[]) keys)[sizeCurrent] = key;
			}
			else if (keysType == TYPE_6_CHAR)
			{
				((char[]) keys)[sizeCurrent] = (char) key;
			}
			else
			{
				throw new RuntimeException(MAP_MT + ERROR_KEY_TYPE_ERROR + keysType);
			}

			if (valuesType == TYPE_1_BYTE)
			{
				((byte[]) values)[sizeCurrent] = (byte) value;
			}
			else if (valuesType == TYPE_2_SHORT)
			{
				((short[]) values)[sizeCurrent] = (short) value;
			}
			else if (valuesType == TYPE_3_INT)
			{
				((int[]) values)[sizeCurrent] = value;
			}
			else if (valuesType == TYPE_6_CHAR)
			{
				((char[]) values)[sizeCurrent] = (char) value;
			}
			else
			{
				throw new RuntimeException(MAP_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
			}

			// Итерируем текущий размер.
			sizeCurrent++;
		}

		return this;
	}

	/**
	 * Добавляет пару [key, value].
	 * Если ключ уже есть, то заменяем значение.
	 * @param key   Ключ.
	 * @param value Значение.
	 */
	public MapMT put(int key, float value)
	{
		int keyIndex = getKeyIndex(key);

		// Если ключ уже есть.
		if (keyIndex != -1)
		{
			// Заменяем значение.
			if (valuesType == TYPE_7_FLOAT)
			{
				((float[]) values)[keyIndex] = value;
			}
			else
			{
				throw new RuntimeException(MAP_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
			}

			//throw new RuntimeException(MAP_MT + ERROR_KEY_ALREADY_EXISTS);
		}
		// Иначе проверить на наличие свободного места.
		else
		{
			// Если места нет, то увеличить общий размер карты.
			if (sizeCurrent >= sizeTotal)
			{
				incSize();
			}

			if (keysType == TYPE_1_BYTE)
			{
				((byte[]) keys)[sizeCurrent] = (byte) key;
			}
			else if (keysType == TYPE_2_SHORT)
			{
				((short[]) keys)[sizeCurrent] = (short) key;
			}
			else if (keysType == TYPE_3_INT)
			{
				((int[]) keys)[sizeCurrent] = key;
			}
			else if (keysType == TYPE_6_CHAR)
			{
				((char[]) keys)[sizeCurrent] = (char) key;
			}
			else
			{
				throw new RuntimeException(MAP_MT + ERROR_KEY_TYPE_ERROR + keysType);
			}

			if (valuesType == TYPE_7_FLOAT)
			{
				((float[]) values)[sizeCurrent] = value;
			}
			else
			{
				throw new RuntimeException(MAP_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
			}

			// Итерируем текущий размер.
			sizeCurrent++;
		}

		return this;
	}

	/**
	 * Добавляет пару [key, value].
	 * Если ключ уже есть, то заменяем значение.
	 * @param key   Ключ.
	 * @param value Значение.
	 */
	public MapMT put(int key, Object value)
	{
		int keyIndex = getKeyIndex(key);

		// Если ключ уже есть.
		if (keyIndex != -1)
		{
			// Заменяем значение.
			if (valuesType == TYPE_9_OBJECT)
			{
				((Object[]) values)[keyIndex] = value;
			}
			else
			{
				throw new RuntimeException(MAP_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
			}

			//throw new RuntimeException(MAP_MT + ERROR_KEY_ALREADY_EXISTS);
		}
		// Иначе проверить на наличие свободного места.
		else
		{
			// Если места нет, то увеличить общий размер карты.
			if (sizeCurrent >= sizeTotal)
			{
				incSize();
			}

			if (keysType == TYPE_1_BYTE)
			{
				((byte[]) keys)[sizeCurrent] = (byte) key;
			}
			else if (keysType == TYPE_2_SHORT)
			{
				((short[]) keys)[sizeCurrent] = (short) key;
			}
			else if (keysType == TYPE_3_INT)
			{
				((int[]) keys)[sizeCurrent] = key;
			}
			else if (keysType == TYPE_6_CHAR)
			{
				((char[]) keys)[sizeCurrent] = (char) key;
			}
			else
			{
				throw new RuntimeException(MAP_MT + ERROR_KEY_TYPE_ERROR + keysType);
			}

			if (valuesType == TYPE_9_OBJECT)
			{
				((Object[]) values)[sizeCurrent] = value;
			}
			else
			{
				throw new RuntimeException(MAP_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
			}

			// Итерируем текущий размер.
			sizeCurrent++;
		}

		return this;
	}

	/**
	 * Добавляет пару [key, value].
	 * Если ключ уже есть, то заменяем значение.
	 * @param key   Ключ.
	 * @param value Значение.
	 */
	public MapMT put(Object key, Object value)
	{
		int keyIndex = getKeyIndex(key);

		// Если ключ уже есть.
		if (keyIndex != -1)
		{
			// Заменяем значение.
			throw new RuntimeException(MAP_MT + ERROR_KEY_ALREADY_EXISTS + "\"" + key + "\"");
		}
		// Иначе проверить на наличие свободного места.
		else
		{
			// Если места нет, то увеличить общий размер карты.
			if (sizeCurrent >= sizeTotal)
			{
				incSize();
			}

			if (keysType == TYPE_9_OBJECT)
			{
				((Object[]) keys)[sizeCurrent] = key;
			}
			else
			{
				throw new RuntimeException(MAP_MT + ERROR_KEY_TYPE_ERROR + keysType);
			}

			if (valuesType == TYPE_9_OBJECT)
			{
				((Object[]) values)[sizeCurrent] = value;
			}
			else
			{
				throw new RuntimeException(MAP_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
			}

			// Итерируем текущий размер.
			sizeCurrent++;
		}

		return this;
	}

	/**
	 * Добавляет пару [key, value].
	 * Если ключ уже есть, то заменяем значение.
	 * @param key   Ключ.
	 * @param value Значение.
	 */
	public MapMT put(Object key, int value)
	{
		int keyIndex = getKeyIndex(key);

		// Если ключ уже есть.
		if (keyIndex != -1)
		{
			// Заменяем значение.
			throw new RuntimeException(MAP_MT + ERROR_KEY_ALREADY_EXISTS + "\"" + key + "\"");
		}
		// Иначе проверить на наличие свободного места.
		else
		{
			// Если места нет, то увеличить общий размер карты.
			if (sizeCurrent >= sizeTotal)
			{
				incSize();
			}

			if (keysType == TYPE_9_OBJECT)
			{
				((Object[]) keys)[sizeCurrent] = key;
			}
			else
			{
				throw new RuntimeException(MAP_MT + ERROR_KEY_TYPE_ERROR + keysType);
			}

			if (valuesType == TYPE_1_BYTE)
			{
				((byte[]) values)[sizeCurrent] = (byte) value;
			}
			else if (valuesType == TYPE_2_SHORT)
			{
				((short[]) values)[sizeCurrent] = (short) value;
			}
			else if (valuesType == TYPE_3_INT)
			{
				((int[]) values)[sizeCurrent] = value;
			}
			else if (valuesType == TYPE_6_CHAR)
			{
				((char[]) values)[sizeCurrent] = (char) value;
			}
			else
			{
				throw new RuntimeException(MAP_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
			}

			// Итерируем текущий размер.
			sizeCurrent++;
		}

		return this;
	}

	/**
	 * Добавляет пару [key, value].
	 * Если ключ уже есть, то заменяем значение.
	 * @param key   Ключ.
	 * @param value Значение.
	 */
	public MapMT put(int key, long value)
	{
		int keyIndex = getKeyIndex(key);

		// Если ключ уже есть.
		if (keyIndex != -1)
		{
			// Заменяем значение.
			if (valuesType == TYPE_4_LONG)
			{
				((long[]) values)[keyIndex] = value;
			}
			else
			{
				throw new RuntimeException(MAP_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
			}

			//throw new RuntimeException(MAP_MT + ERROR_KEY_ALREADY_EXISTS);
		}
		// Иначе проверить на наличие свободного места.
		else
		{
			// Если места нет, то увеличить общий размер карты.
			if (sizeCurrent >= sizeTotal)
			{
				incSize();
			}

			if (keysType == TYPE_1_BYTE)
			{
				((byte[]) keys)[sizeCurrent] = (byte) key;
			}
			else if (keysType == TYPE_2_SHORT)
			{
				((short[]) keys)[sizeCurrent] = (short) key;
			}
			else if (keysType == TYPE_3_INT)
			{
				((int[]) keys)[sizeCurrent] = key;
			}
			else if (keysType == TYPE_6_CHAR)
			{
				((char[]) keys)[sizeCurrent] = (char) key;
			}
			else
			{
				throw new RuntimeException(MAP_MT + ERROR_KEY_TYPE_ERROR + keysType);
			}

			if (valuesType == TYPE_4_LONG)
			{
				((long[]) values)[sizeCurrent] = value;
			}
			else
			{
				throw new RuntimeException(MAP_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
			}

			// Итерируем текущий размер.
			sizeCurrent++;
		}

		return this;
	}

	/**
	 * Вставляет пару [key, value] по указанному индексу.
	 * Указанный индекс, а также все последующие перемещаются далее.
	 * Если ключ уже есть, то ошибка.
	 * @param key   Ключ.
	 * @param value Значение.
	 */
	public void putToIndex(int key, Object value, int index)
	{
		if (index < 0 || index > sizeCurrent)
		{
			throw new RuntimeException(MAP_MT + ERROR_WRONG_INDEX + " Index = " + index + ", sizeCurrent = " + sizeCurrent + ".");
		}

		int keyIndex = getKeyIndex(key);

		// Если ключ уже есть.
		if (keyIndex != -1)
		{
			throw new RuntimeException(MAP_MT + ERROR_KEY_ALREADY_EXISTS + "\"" + key + "\"");
		}
		// Иначе проверить на наличие свободного места.
		else
		{
			// Если места нет, то увеличить общий размер карты.
			if (sizeCurrent >= sizeTotal)
			{
				incSize();
			}

			// Передвигаем указанный и все последующие индексы вперёд на одну позицию.
			for (int i = sizeCurrent - 1; i > index; i--)
			{
				if (keysType == TYPE_1_BYTE)
				{
					((byte[]) keys)[i] = ((byte[]) keys)[i - 1];
				}
				else if (keysType == TYPE_2_SHORT)
				{
					((short[]) keys)[i] = ((short[]) keys)[i - 1];
				}
				else if (keysType == TYPE_3_INT)
				{
					((int[]) keys)[i] = ((int[]) keys)[i - 1];
				}
				else if (keysType == TYPE_6_CHAR)
				{
					((char[]) keys)[i] = ((char[]) keys)[i - 1];
				}
				else
				{
					throw new RuntimeException(MAP_MT + ERROR_KEY_TYPE_ERROR + keysType);
				}

				if (valuesType == TYPE_9_OBJECT)
				{
					((Object[]) values)[i] = ((Object[]) values)[i - 1];
				}
				else
				{
					throw new RuntimeException(MAP_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
				}
			}

			if (keysType == TYPE_1_BYTE)
			{
				((byte[]) keys)[index] = (byte) key;
			}
			else if (keysType == TYPE_2_SHORT)
			{
				((short[]) keys)[index] = (short) key;
			}
			else if (keysType == TYPE_3_INT)
			{
				((int[]) keys)[index] = key;
			}
			else if (keysType == TYPE_6_CHAR)
			{
				((char[]) keys)[index] = (char) key;
			}
			else
			{
				throw new RuntimeException(MAP_MT + ERROR_KEY_TYPE_ERROR + keysType);
			}

			if (valuesType == TYPE_9_OBJECT)
			{
				((Object[]) values)[index] = value;
			}
			else
			{
				throw new RuntimeException(MAP_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
			}

			// Итерируем текущий размер.
			sizeCurrent++;
		}
	}

	/**
	 * Вставляет пару [key, value] по указанному индексу.
	 * Указанный индекс, а также все последующие перемещаются далее.
	 * Если ключ уже есть, то ошибка.
	 * @param key   Ключ.
	 * @param value Значение.
	 */
	public void putToIndex(int key, int value, int index)
	{
		if (index < 0 || index > sizeCurrent)
		{
			throw new RuntimeException(MAP_MT + ERROR_WRONG_INDEX + " Index = " + index + ", sizeCurrent = " + sizeCurrent + ".");
		}

		int keyIndex = getKeyIndex(key);

		// Если ключ уже есть.
		if (keyIndex != -1)
		{
			throw new RuntimeException(MAP_MT + ERROR_KEY_ALREADY_EXISTS + "\"" + key + "\"");
		}
		// Иначе проверить на наличие свободного места.
		else
		{
			// Если места нет, то увеличить общий размер карты.
			if (sizeCurrent >= sizeTotal)
			{
				incSize();
			}

			// Передвигаем указанный и все последующие индексы вперёд на одну позицию.
			for (int i = sizeCurrent - 1; i > index; i--)
			{
				if (keysType == TYPE_1_BYTE)
				{
					((byte[]) keys)[i] = ((byte[]) keys)[i - 1];
				}
				else if (keysType == TYPE_2_SHORT)
				{
					((short[]) keys)[i] = ((short[]) keys)[i - 1];
				}
				else if (keysType == TYPE_3_INT)
				{
					((int[]) keys)[i] = ((int[]) keys)[i - 1];
				}
				else if (keysType == TYPE_6_CHAR)
				{
					((char[]) keys)[i] = ((char[]) keys)[i - 1];
				}
				else
				{
					throw new RuntimeException(MAP_MT + ERROR_KEY_TYPE_ERROR + keysType);
				}

				if (valuesType == TYPE_1_BYTE)
				{
					((byte[]) values)[i] = ((byte[]) values)[i - 1];
				}
				else if (valuesType == TYPE_2_SHORT)
				{
					((short[]) values)[i] = ((short[]) values)[i - 1];
				}
				else if (valuesType == TYPE_3_INT)
				{
					((int[]) values)[i] = ((int[]) values)[i - 1];
				}
				else if (valuesType == TYPE_6_CHAR)
				{
					((char[]) values)[i] = ((char[]) values)[i - 1];
				}
				else
				{
					throw new RuntimeException(MAP_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
				}
			}

			if (keysType == TYPE_1_BYTE)
			{
				((byte[]) keys)[index] = (byte) key;
			}
			else if (keysType == TYPE_2_SHORT)
			{
				((short[]) keys)[index] = (short) key;
			}
			else if (keysType == TYPE_3_INT)
			{
				((int[]) keys)[index] = key;
			}
			else if (keysType == TYPE_6_CHAR)
			{
				((char[]) keys)[index] = (char) key;
			}
			else
			{
				throw new RuntimeException(MAP_MT + ERROR_KEY_TYPE_ERROR + keysType);
			}

			if (valuesType == TYPE_1_BYTE)
			{
				((byte[]) values)[index] = (byte) value;
			}
			else if (valuesType == TYPE_2_SHORT)
			{
				((short[]) values)[index] = (short) value;
			}
			else if (valuesType == TYPE_3_INT)
			{
				((int[]) values)[index] = value;
			}
			else if (valuesType == TYPE_6_CHAR)
			{
				((char[]) values)[index] = (char) value;
			}
			else
			{
				throw new RuntimeException(MAP_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
			}

			// Итерируем текущий размер.
			sizeCurrent++;
		}
	}

	/**
	 * Вставляет пару [key, value] по указанному индексу.
	 * Указанный индекс, а также все последующие перемещаются далее.
	 * Если ключ уже есть, то ошибка.
	 * @param key   Ключ.
	 * @param value Значение.
	 */
	public void putToIndex(int key, long value, int index)
	{
		if (index < 0 || index > sizeCurrent)
		{
			throw new RuntimeException(MAP_MT + ERROR_WRONG_INDEX + " Index = " + index + ", sizeCurrent = " + sizeCurrent + ".");
		}

		int keyIndex = getKeyIndex(key);

		// Если ключ уже есть.
		if (keyIndex != -1)
		{
			throw new RuntimeException(MAP_MT + ERROR_KEY_ALREADY_EXISTS + "\"" + key + "\"");
		}
		// Иначе проверить на наличие свободного места.
		else
		{
			// Если места нет, то увеличить общий размер карты.
			if (sizeCurrent >= sizeTotal)
			{
				incSize();
			}

			// Передвигаем указанный и все последующие индексы вперёд на одну позицию.
			for (int i = sizeCurrent - 1; i > index; i--)
			{
				if (keysType == TYPE_1_BYTE)
				{
					((byte[]) keys)[i] = ((byte[]) keys)[i - 1];
				}
				else if (keysType == TYPE_2_SHORT)
				{
					((short[]) keys)[i] = ((short[]) keys)[i - 1];
				}
				else if (keysType == TYPE_3_INT)
				{
					((int[]) keys)[i] = ((int[]) keys)[i - 1];
				}
				else if (keysType == TYPE_6_CHAR)
				{
					((char[]) keys)[i] = ((char[]) keys)[i - 1];
				}
				else
				{
					throw new RuntimeException(MAP_MT + ERROR_KEY_TYPE_ERROR + keysType);
				}

				if (valuesType == TYPE_4_LONG)
				{
					((long[]) values)[i] = ((long[]) values)[i - 1];
				}
				else
				{
					throw new RuntimeException(MAP_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
				}
			}

			if (keysType == TYPE_1_BYTE)
			{
				((byte[]) keys)[index] = (byte) key;
			}
			else if (keysType == TYPE_2_SHORT)
			{
				((short[]) keys)[index] = (short) key;
			}
			else if (keysType == TYPE_3_INT)
			{
				((int[]) keys)[index] = key;
			}
			else if (keysType == TYPE_6_CHAR)
			{
				((char[]) keys)[index] = (char) key;
			}
			else
			{
				throw new RuntimeException(MAP_MT + ERROR_KEY_TYPE_ERROR + keysType);
			}

			if (valuesType == TYPE_4_LONG)
			{
				((long[]) values)[index] = value;
			}
			else
			{
				throw new RuntimeException(MAP_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
			}

			// Итерируем текущий размер.
			sizeCurrent++;
		}
	}

	/**
	 * Возвращает значение по ключу.
	 * @param key Ключ.
	 * @return    Значение.
	 */
	public byte getValueByte(int key)
	{
		int keyIndex = getKeyIndex(key);
		if (keyIndex != -1)
		{
			if (valuesType == TYPE_1_BYTE)
			{
				return ((byte[]) values)[keyIndex];
			}
			else
			{
				throw new RuntimeException(MAP_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
			}
		}
		else
		{
			throw new RuntimeException(MAP_MT + ERROR_VALUE_NOT_FOUND);
		}
	}

	/**
	 * Возвращает значение по ключу.
	 * @param key Ключ.
	 * @return    Значение.
	 */
	public short getValueShort(int key)
	{
		int keyIndex = getKeyIndex(key);
		if (keyIndex != -1)
		{
			if (valuesType == TYPE_2_SHORT)
			{
				return ((short[]) values)[keyIndex];
			}
			else
			{
				throw new RuntimeException(MAP_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
			}
		}
		else
		{
			throw new RuntimeException(MAP_MT + ERROR_VALUE_NOT_FOUND);
		}
	}

	/**
	 * Возвращает значение по ключу.
	 * @param key Ключ.
	 * @return    Значение.
	 */
	public int getValueInt(int key)
	{
		int keyIndex = getKeyIndex(key);
		if (keyIndex != -1)
		{
			if (valuesType == TYPE_3_INT)
			{
				return ((int[]) values)[keyIndex];
			}
			else
			{
				throw new RuntimeException(MAP_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
			}
		}
		else
		{
			throw new RuntimeException(MAP_MT + ERROR_VALUE_NOT_FOUND);
		}
	}

	/**
	 * Возвращает значение по ключу.
	 * @param key Ключ.
	 * @return    Значение.
	 */
	public char getValueChar(int key)
	{
		int keyIndex = getKeyIndex(key);
		if (keyIndex != -1)
		{
			if (valuesType == TYPE_6_CHAR)
			{
				return ((char[]) values)[keyIndex];
			}
			else
			{
				throw new RuntimeException(MAP_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
			}
		}
		else
		{
			throw new RuntimeException(MAP_MT + ERROR_VALUE_NOT_FOUND);
		}
	}

	/**
	 * Возвращает значение по ключу.
	 * @param key Ключ.
	 * @return    Значение.
	 */
	public float getValueFloat(int key)
	{
		int keyIndex = getKeyIndex(key);
		if (keyIndex != -1)
		{
			if (valuesType == TYPE_7_FLOAT)
			{
				return ((float[]) values)[keyIndex];
			}
			else
			{
				throw new RuntimeException(MAP_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
			}
		}
		else
		{
			throw new RuntimeException(MAP_MT + ERROR_VALUE_NOT_FOUND);
		}
	}

	/**
	 * Возвращает значение по ключу.
	 * @param key Ключ.
	 * @return    Значение.
	 */
	public Object getValueObject(int key)
	{
		int keyIndex = getKeyIndex(key);
		if (keyIndex != -1)
		{
			if (valuesType == TYPE_9_OBJECT)
			{
				return ((Object[]) values)[keyIndex];
			}
			else
			{
				throw new RuntimeException(MAP_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
			}
		}
		else
		{
			throw new RuntimeException(MAP_MT + ERROR_VALUE_NOT_FOUND);
		}
	}

	/**
	 * Возвращает значение по ключу.
	 * @param key Ключ.
	 * @return    Значение.
	 */
	public Object getValueObject(Object key)
	{
		int keyIndex = getKeyIndex(key);
		if (keyIndex != -1)
		{
			if (valuesType == TYPE_9_OBJECT)
			{
				return ((Object[]) values)[keyIndex];
			}
			else
			{
				throw new RuntimeException(MAP_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
			}
		}
		else
		{
			throw new RuntimeException(MAP_MT + ERROR_VALUE_NOT_FOUND_FOR_KEY + "\"" + key + "\".");
		}
	}

	public String[] valuesToStringArray()
	{
		String[] strings = new String[sizeCurrent];

		if (valuesType == TYPE_9_OBJECT)
		{
			for (int i = 0; i < sizeCurrent; i++)
			{
				strings[i] = (String) ((Object[]) values)[i];
			}
		}
		else
		{
			throw new RuntimeException(MAP_MT + ERROR_VALUE_TYPE_ERROR);
		}

		return strings;
	}

	/**
	 * Возвращает свободный ключ, ещё не используемый в карте.
	 * TODO no free key exception.
	 */
	public char getFreeKeyChar()
	{
		char keyNew = 0;

		if (keysType == TYPE_6_CHAR)
		{
			char[] keys = getSortedKeysChar();
			keyNew = keys.length == 0 ? 0 : (char) (keys[0] + 1);

			for (int i = 0; i < keys.length; i++)
			{
				// Если последний ключ.
				if (i == keys.length - 1)
				{
					keyNew++;
				}
				// Иначе сверяем со следующим.
				else
				{
					// Если keyNew не равен следующему ключу, то он и будет freeKey.
					if (keyNew != keys[i + 1])
					{
						break;
					}
					// Иначе увеличиваем keyNew на единицу и идём на следующую итерацию.
					else
					{
						keyNew++;
					}
				}
			}
		}
		else
		{
			throw new RuntimeException(MAP_MT + ERROR_KEY_TYPE_ERROR + keysType);
		}

		return keyNew;
	}

	/**
	 * Возвращает свободный ключ, ещё не используемый в карте.
	 * TODO no free key exception.
	 */
	public int getFreeKey()
	{
		int keyNew = -1;

		if (keysType == TYPE_1_BYTE)
		{
			byte[] keys = getSortedKeysByte();
			keyNew = keys.length == 0 ? 0 : (keys[0] + 1);

			for (int i = 0; i < keys.length; i++)
			{
				// Если последний ключ.
				if (i == keys.length - 1)
				{
					keyNew++;
				}
				// Иначе сверяем со следующим.
				else
				{
					// Если keyNew не равен следующему ключу, то он и будет freeKey.
					if (keyNew != keys[i + 1])
					{
						break;
					}
					// Иначе увеличиваем keyNew на единицу и идём на следующую итерацию.
					else
					{
						keyNew++;
					}
				}
			}
		}
		else if (keysType == TYPE_2_SHORT)
		{
			short[] keys = getSortedKeysShort();
			keyNew = keys.length == 0 ? 0 : (keys[0] + 1);

			for (int i = 0; i < keys.length; i++)
			{
				// Если последний ключ.
				if (i == keys.length - 1)
				{
					keyNew++;
				}
				// Иначе сверяем со следующим.
				else
				{
					// Если keyNew не равен следующему ключу, то он и будет freeKey.
					if (keyNew != keys[i + 1])
					{
						break;
					}
					// Иначе увеличиваем keyNew на единицу и идём на следующую итерацию.
					else
					{
						keyNew++;
					}
				}
			}
		}
		else if (keysType == TYPE_3_INT)
		{
			int[] keys = getSortedKeysInt();
			keyNew = keys.length == 0 ? 0 : (keys[0] + 1);

			for (int i = 0; i < keys.length; i++)
			{
				// Если последний ключ.
				if (i == keys.length - 1)
				{
					keyNew++;
				}
				// Иначе сверяем со следующим.
				else
				{
					// Если keyNew не равен следующему ключу, то он и будет freeKey.
					if (keyNew != keys[i + 1])
					{
						break;
					}
					// Иначе увеличиваем keyNew на единицу и идём на следующую итерацию.
					else
					{
						keyNew++;
					}
				}
			}
		}
		else
		{
			throw new RuntimeException(MAP_MT + ERROR_KEY_TYPE_ERROR + keysType);
		}

		return keyNew;
	}

	/**
	 * Генерирует набор ключей из набора элементов и сортирует по возрастанию.
	 */
	public byte[] getSortedKeysByte()
	{
		if (keysType != TYPE_1_BYTE)
		{
			throw new RuntimeException(MAP_MT + ERROR_KEY_TYPE_ERROR + keysType);
		}

		// Создать пустой массив ключей.
		byte[] itemsKeys = new byte[sizeCurrent];

		// Скопировать актуальные ключи в новый массив.
		for (int i = 0; i < sizeCurrent; i++)
		{
			itemsKeys[i] = ((byte[]) keys)[i];
		}

		// Отсортировать по возрастанию массив ключей.
		quickSort(itemsKeys);

		return itemsKeys;
	}

	/**
	 * Генерирует набор ключей из набора элементов и сортирует по возрастанию.
	 */
	public short[] getSortedKeysShort()
	{
		if (keysType != TYPE_2_SHORT)
		{
			throw new RuntimeException(MAP_MT + ERROR_KEY_TYPE_ERROR + keysType);
		}

		// Создать пустой массив ключей.
		short[] itemsKeys = new short[sizeCurrent];

		// Скопировать актуальные ключи в новый массив.
		for (int i = 0; i < sizeCurrent; i++)
		{
			itemsKeys[i] = ((short[]) keys)[i];
		}

		// Отсортировать по возрастанию массив ключей.
		quickSort(itemsKeys);

		return itemsKeys;
	}

	/**
	 * Генерирует набор ключей из набора элементов и сортирует по возрастанию.
	 */
	public int[] getSortedKeysInt()
	{
		if (keysType != TYPE_3_INT)
		{
			throw new RuntimeException(MAP_MT + ERROR_KEY_TYPE_ERROR + keysType);
		}

		// Создать пустой массив ключей.
		int[] itemsKeys = new int[sizeCurrent];

		// Скопировать актуальные ключи в новый массив.
		for (int i = 0; i < sizeCurrent; i++)
		{
			itemsKeys[i] = ((int[]) keys)[i];
		}

		// Отсортировать по возрастанию массив ключей.
		quickSort(itemsKeys);

		return itemsKeys;
	}

	/**
	 * Генерирует набор ключей из набора элементов и сортирует по возрастанию.
	 */
	public char[] getSortedKeysChar()
	{
		if (keysType != TYPE_6_CHAR)
		{
			throw new RuntimeException(MAP_MT + ERROR_KEY_TYPE_ERROR + keysType);
		}

		// Создать пустой массив ключей.
		char[] itemsKeys = new char[sizeCurrent];

		// Скопировать актуальные ключи в новый массив.
		for (int i = 0; i < sizeCurrent; i++)
		{
			itemsKeys[i] = ((char[]) keys)[i];
		}

		// Отсортировать по возрастанию массив ключей.
		quickSort(itemsKeys);

		return itemsKeys;
	}

	/**
	 * Сортирует пары [key, value] по ключам.
	 * Быстрая сортировка.
	 */
	public void sortByKeys()
	{
		if (keysType == TYPE_1_BYTE)
		{
			byte[] sortedKeys = getSortedKeysByte();

			for (int i = 0; i < sortedKeys.length; i++)
			{
				int j = getKeyIndex(sortedKeys[i]);

				// Swap.
				if (i != j)
				{
					// Keys.
					byte temp = ((byte[]) keys)[i];
					((byte[]) keys)[i] = ((byte[]) keys)[j];
					((byte[]) keys)[j] = temp;

					// Values.
					if (valuesType == TYPE_1_BYTE)
					{
						byte temp2 = ((byte[]) values)[i];
						((byte[]) values)[i] = ((byte[]) values)[j];
						((byte[]) values)[j] = temp2;
					}
					else if (valuesType == TYPE_2_SHORT)
					{
						short temp2 = ((short[]) values)[i];
						((short[]) values)[i] = ((short[]) values)[j];
						((short[]) values)[j] = temp2;
					}
					else if (valuesType == TYPE_3_INT)
					{
						int temp2 = ((int[]) values)[i];
						((int[]) values)[i] = ((int[]) values)[j];
						((int[]) values)[j] = temp2;
					}
					else if (valuesType == TYPE_6_CHAR)
					{
						char temp2 = ((char[]) values)[i];
						((char[]) values)[i] = ((char[]) values)[j];
						((char[]) values)[j] = temp2;
					}
					else
					{
						throw new RuntimeException(MAP_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
					}
				}
			}
		}
		else if (keysType == TYPE_2_SHORT)
		{
			short[] sortedKeys = getSortedKeysShort();

			for (int i = 0; i < sortedKeys.length; i++)
			{
				int j = getKeyIndex(sortedKeys[i]);

				// Swap.
				if (i != j)
				{
					// Keys.
					short temp = ((short[]) keys)[i];
					((short[]) keys)[i] = ((short[]) keys)[j];
					((short[]) keys)[j] = temp;

					// Values.
					if (valuesType == TYPE_1_BYTE)
					{
						byte temp2 = ((byte[]) values)[i];
						((byte[]) values)[i] = ((byte[]) values)[j];
						((byte[]) values)[j] = temp2;
					}
					else if (valuesType == TYPE_2_SHORT)
					{
						short temp2 = ((short[]) values)[i];
						((short[]) values)[i] = ((short[]) values)[j];
						((short[]) values)[j] = temp2;
					}
					else if (valuesType == TYPE_3_INT)
					{
						int temp2 = ((int[]) values)[i];
						((int[]) values)[i] = ((int[]) values)[j];
						((int[]) values)[j] = temp2;
					}
					else if (valuesType == TYPE_6_CHAR)
					{
						char temp2 = ((char[]) values)[i];
						((char[]) values)[i] = ((char[]) values)[j];
						((char[]) values)[j] = temp2;
					}
					else
					{
						throw new RuntimeException(MAP_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
					}
				}
			}
		}
		else if (keysType == TYPE_3_INT)
		{
			int[] sortedKeys = getSortedKeysInt();

			for (int i = 0; i < sortedKeys.length; i++)
			{
				int j = getKeyIndex(sortedKeys[i]);

				// Swap.
				if (i != j)
				{
					// Keys.
					int temp = ((int[]) keys)[i];
					((int[]) keys)[i] = ((int[]) keys)[j];
					((int[]) keys)[j] = temp;

					// Values.
					if (valuesType == TYPE_1_BYTE)
					{
						byte temp2 = ((byte[]) values)[i];
						((byte[]) values)[i] = ((byte[]) values)[j];
						((byte[]) values)[j] = temp2;
					}
					else if (valuesType == TYPE_2_SHORT)
					{
						short temp2 = ((short[]) values)[i];
						((short[]) values)[i] = ((short[]) values)[j];
						((short[]) values)[j] = temp2;
					}
					else if (valuesType == TYPE_3_INT)
					{
						int temp2 = ((int[]) values)[i];
						((int[]) values)[i] = ((int[]) values)[j];
						((int[]) values)[j] = temp2;
					}
					else if (valuesType == TYPE_6_CHAR)
					{
						char temp2 = ((char[]) values)[i];
						((char[]) values)[i] = ((char[]) values)[j];
						((char[]) values)[j] = temp2;
					}
					else
					{
						throw new RuntimeException(MAP_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
					}
				}
			}
		}
		else if (keysType == TYPE_6_CHAR)
		{
			char[] sortedKeys = getSortedKeysChar();

			for (int i = 0; i < sortedKeys.length; i++)
			{
				int j = getKeyIndex(sortedKeys[i]);

				// Swap.
				if (i != j)
				{
					// Keys.
					char temp = ((char[]) keys)[i];
					((char[]) keys)[i] = ((char[]) keys)[j];
					((char[]) keys)[j] = temp;

					// Values.
					if (valuesType == TYPE_1_BYTE)
					{
						byte temp2 = ((byte[]) values)[i];
						((byte[]) values)[i] = ((byte[]) values)[j];
						((byte[]) values)[j] = temp2;
					}
					else if (valuesType == TYPE_2_SHORT)
					{
						short temp2 = ((short[]) values)[i];
						((short[]) values)[i] = ((short[]) values)[j];
						((short[]) values)[j] = temp2;
					}
					else if (valuesType == TYPE_3_INT)
					{
						int temp2 = ((int[]) values)[i];
						((int[]) values)[i] = ((int[]) values)[j];
						((int[]) values)[j] = temp2;
					}
					else if (valuesType == TYPE_6_CHAR)
					{
						char temp2 = ((char[]) values)[i];
						((char[]) values)[i] = ((char[]) values)[j];
						((char[]) values)[j] = temp2;
					}
					else if (valuesType == TYPE_9_OBJECT)
					{
						Object temp2 = ((Object[]) values)[i];
						((Object[]) values)[i] = ((Object[]) values)[j];
						((Object[]) values)[j] = temp2;
					}
					else
					{
						throw new RuntimeException(MAP_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
					}
				}
			}
		}
		else
		{
			throw new RuntimeException(MAP_MT + ERROR_KEY_TYPE_ERROR + keysType);
		}
	}

	/**
	 * Сортирует пары [key, value] по значениям.
	 * Медленная сортировка пузырьком.
	 */
	public void sortByValues()
	{
		if (valuesType == TYPE_1_BYTE)
		{
			for (int i = 0; i < sizeCurrent - 1; i++)
			{
				byte valueMin = ((byte[]) values)[i];
				int valueMinIndex = i;

				// Ищем минимальное значение.
				for (int j = i + 1; j < sizeCurrent; j++)
				{
					byte value = ((byte[]) values)[j];

					if (valueMin > value)
					{
						valueMin = value;
						valueMinIndex = j;
					}
				}

				// Меняем местами значения.
				byte temp = ((byte[]) values)[i];
				((byte[]) values)[i] = ((byte[]) values)[valueMinIndex];
				((byte[]) values)[valueMinIndex] = temp;

				if (keysType == TYPE_1_BYTE)
				{
					byte temp2 = ((byte[]) keys)[i];
					((byte[]) keys)[i] = ((byte[]) keys)[valueMinIndex];
					((byte[]) keys)[valueMinIndex] = temp2;
				}
				else if (keysType == TYPE_2_SHORT)
				{
					short temp2 = ((short[]) keys)[i];
					((short[]) keys)[i] = ((short[]) keys)[valueMinIndex];
					((short[]) keys)[valueMinIndex] = temp2;
				}
				else if (keysType == TYPE_3_INT)
				{
					int temp2 = ((int[]) keys)[i];
					((int[]) keys)[i] = ((int[]) keys)[valueMinIndex];
					((int[]) keys)[valueMinIndex] = temp2;
				}
				else if (keysType == TYPE_6_CHAR)
				{
					char temp2 = ((char[]) keys)[i];
					((char[]) keys)[i] = ((char[]) keys)[valueMinIndex];
					((char[]) keys)[valueMinIndex] = temp2;
				}
				else
				{
					throw new RuntimeException(MAP_MT + ERROR_KEY_TYPE_ERROR + keysType);
				}
			}
		}
		else if (valuesType == TYPE_2_SHORT)
		{
			for (int i = 0; i < sizeCurrent - 1; i++)
			{
				short valueMin = ((short[]) values)[i];
				int valueMinIndex = i;

				// Ищем минимальное значение.
				for (int j = i + 1; j < sizeCurrent; j++)
				{
					short value = ((short[]) values)[j];

					if (valueMin > value)
					{
						valueMin = value;
						valueMinIndex = j;
					}
				}

				// Меняем местами значения.
				short temp = ((short[]) values)[i];
				((short[]) values)[i] = ((short[]) values)[valueMinIndex];
				((short[]) values)[valueMinIndex] = temp;

				if (keysType == TYPE_1_BYTE)
				{
					byte temp2 = ((byte[]) keys)[i];
					((byte[]) keys)[i] = ((byte[]) keys)[valueMinIndex];
					((byte[]) keys)[valueMinIndex] = temp2;
				}
				else if (keysType == TYPE_2_SHORT)
				{
					short temp2 = ((short[]) keys)[i];
					((short[]) keys)[i] = ((short[]) keys)[valueMinIndex];
					((short[]) keys)[valueMinIndex] = temp2;
				}
				else if (keysType == TYPE_3_INT)
				{
					int temp2 = ((int[]) keys)[i];
					((int[]) keys)[i] = ((int[]) keys)[valueMinIndex];
					((int[]) keys)[valueMinIndex] = temp2;
				}
				else if (keysType == TYPE_6_CHAR)
				{
					char temp2 = ((char[]) keys)[i];
					((char[]) keys)[i] = ((char[]) keys)[valueMinIndex];
					((char[]) keys)[valueMinIndex] = temp2;
				}
				else
				{
					throw new RuntimeException(MAP_MT + ERROR_KEY_TYPE_ERROR + keysType);
				}
			}
		}
		else if (valuesType == TYPE_3_INT)
		{
			for (int i = 0; i < sizeCurrent - 1; i++)
			{
				int valueMin = ((int[]) values)[i];
				int valueMinIndex = i;

				// Ищем минимальное значение.
				for (int j = i + 1; j < sizeCurrent; j++)
				{
					int value = ((int[]) values)[j];

					if (valueMin > value)
					{
						valueMin = value;
						valueMinIndex = j;
					}
				}

				// Меняем местами значения.
				int temp = ((int[]) values)[i];
				((int[]) values)[i] = ((int[]) values)[valueMinIndex];
				((int[]) values)[valueMinIndex] = temp;

				if (keysType == TYPE_1_BYTE)
				{
					byte temp2 = ((byte[]) keys)[i];
					((byte[]) keys)[i] = ((byte[]) keys)[valueMinIndex];
					((byte[]) keys)[valueMinIndex] = temp2;
				}
				else if (keysType == TYPE_2_SHORT)
				{
					short temp2 = ((short[]) keys)[i];
					((short[]) keys)[i] = ((short[]) keys)[valueMinIndex];
					((short[]) keys)[valueMinIndex] = temp2;
				}
				else if (keysType == TYPE_3_INT)
				{
					int temp2 = ((int[]) keys)[i];
					((int[]) keys)[i] = ((int[]) keys)[valueMinIndex];
					((int[]) keys)[valueMinIndex] = temp2;
				}
				else if (keysType == TYPE_6_CHAR)
				{
					char temp2 = ((char[]) keys)[i];
					((char[]) keys)[i] = ((char[]) keys)[valueMinIndex];
					((char[]) keys)[valueMinIndex] = temp2;
				}
				else
				{
					throw new RuntimeException(MAP_MT + ERROR_KEY_TYPE_ERROR + keysType);
				}
			}
		}
		else
		{
			throw new RuntimeException(MAP_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
		}
	}

	/**
	 * Возвращает ключ по значению (если ключей несколько для данного значения, то первый).
	 * @param value Значение.
	 * @return      Ключ.
	 */
	public byte getKeyByte(Object value)
	{
		if (keysType == TYPE_1_BYTE)
		{
			if (valuesType == TYPE_9_OBJECT)
			{
				Object[] valuesObject = (Object[]) values;
				for (int i = 0; i < sizeCurrent; i++)
				{
					if (
							(value == null && valuesObject[i] == null) ||
							(value != null && value.equals(valuesObject[i]))
						)
					{
						return ((byte[]) keys)[i];
					}
				}

				throw new RuntimeException(MAP_MT + ERROR_KEY_NOT_FOUND);
			}
			else
			{
				throw new RuntimeException(MAP_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
			}
		}
		else
		{
			throw new RuntimeException(MAP_MT + ERROR_KEY_TYPE_ERROR + keysType);
		}
	}

	/**
	 * Возвращает ключ по значению (если ключей несколько для данного значения, то первый).
	 * @param value Значение.
	 * @return      Ключ.
	 */
	public byte getKeyByte(int value)
	{
		if (keysType == TYPE_1_BYTE)
		{
			if (valuesType == TYPE_1_BYTE)
			{
				byte[] valuesByte = (byte[]) values;
				for (int i = 0; i < sizeCurrent; i++)
				{
					if (value == valuesByte[i])
					{
						return ((byte[]) keys)[i];
					}
				}

				throw new RuntimeException(MAP_MT + ERROR_KEY_NOT_FOUND);
			}
			else if (valuesType == TYPE_2_SHORT)
			{
				short[] valuesShort = (short[]) values;
				for (int i = 0; i < sizeCurrent; i++)
				{
					if (value == valuesShort[i])
					{
						return ((byte[]) keys)[i];
					}
				}

				throw new RuntimeException(MAP_MT + ERROR_KEY_NOT_FOUND);
			}
			else if (valuesType == TYPE_3_INT)
			{
				int[] valuesInt = (int[]) values;
				for (int i = 0; i < sizeCurrent; i++)
				{
					if (value == valuesInt[i])
					{
						return ((byte[]) keys)[i];
					}
				}

				throw new RuntimeException(MAP_MT + ERROR_KEY_NOT_FOUND);
			}
			else
			{
				throw new RuntimeException(MAP_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
			}
		}
		else
		{
			throw new RuntimeException(MAP_MT + ERROR_KEY_TYPE_ERROR + keysType);
		}
	}

	/**
	 * Возвращает ключ по значению (если ключей несколько для данного значения, то первый).
	 * @param value Значение.
	 * @return      Ключ.
	 */
	public short getKeyShort(Object value)
	{
		if (keysType == TYPE_2_SHORT)
		{
			if (valuesType == TYPE_9_OBJECT)
			{
				Object[] valuesObject = (Object[]) values;
				for (int i = 0; i < sizeCurrent; i++)
				{
					if (
							(value == null && valuesObject[i] == null) ||
							(value != null && value.equals(valuesObject[i]))
						)
					{
						return ((short[]) keys)[i];
					}
				}

				throw new RuntimeException(MAP_MT + ERROR_KEY_NOT_FOUND);
			}
			else
			{
				throw new RuntimeException(MAP_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
			}
		}
		else
		{
			throw new RuntimeException(MAP_MT + ERROR_KEY_TYPE_ERROR + keysType);
		}
	}

	/**
	 * Возвращает ключ по значению (если ключей несколько для данного значения, то первый).
	 * @param value Значение.
	 * @return      Ключ.
	 */
	public short getKeyShort(int value)
	{
		if (keysType == TYPE_2_SHORT)
		{
			if (valuesType == TYPE_3_INT)
			{
				int[] valuesInt = (int[]) values;
				for (int i = 0; i < sizeCurrent; i++)
				{
					if (value == valuesInt[i])
					{
						return ((short[]) keys)[i];
					}
				}

				throw new RuntimeException(MAP_MT + ERROR_KEY_NOT_FOUND);
			}
			else
			{
				throw new RuntimeException(MAP_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
			}
		}
		else
		{
			throw new RuntimeException(MAP_MT + ERROR_KEY_TYPE_ERROR + keysType);
		}
	}

	/**
	 * Возвращает ключ по значению (если ключей несколько для данного значения, то первый).
	 * @param value Значение.
	 * @return      Ключ.
	 */
	public int getKeyInt(Object value)
	{
		if (keysType == TYPE_3_INT)
		{
			if (valuesType == TYPE_9_OBJECT)
			{
				Object[] valuesObject = (Object[]) values;
				for (int i = 0; i < sizeCurrent; i++)
				{
					if (
							(value == null && valuesObject[i] == null) ||
							(value != null && value.equals(valuesObject[i]))
						)
					{
						return ((int[]) keys)[i];
					}
				}

				throw new RuntimeException(MAP_MT + ERROR_KEY_NOT_FOUND);
			}
			else
			{
				throw new RuntimeException(MAP_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
			}
		}
		else
		{
			throw new RuntimeException(MAP_MT + ERROR_KEY_TYPE_ERROR + keysType);
		}
	}

	/**
	 * Возвращает ключ по значению (если ключей несколько для данного значения, то первый).
	 * @param value Значение.
	 * @return      Ключ.
	 */
	public int getKeyInt(int value)
	{
		if (keysType == TYPE_3_INT)
		{
			if (valuesType == TYPE_3_INT)
			{
				int[] valuesInt = (int[]) values;
				for (int i = 0; i < sizeCurrent; i++)
				{
					if (value == valuesInt[i])
					{
						return ((int[]) keys)[i];
					}
				}

				throw new RuntimeException(MAP_MT + ERROR_KEY_NOT_FOUND);
			}
			else
			{
				throw new RuntimeException(MAP_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
			}
		}
		else
		{
			throw new RuntimeException(MAP_MT + ERROR_KEY_TYPE_ERROR + keysType);
		}
	}

	/**
	 * Удаляет пару [key, value] по индексу.
	 * @param index Индекс.
	 */
	public void removeIndex(int index)
	{
		// Проверка индекса.
		if (index < 0 || index > sizeCurrent - 1)
		{
			throw new RuntimeException(MAP_MT + ERROR_WRONG_INDEX);
		}

		// Если индекс не последний.
		if (index < sizeCurrent - 1)
		{
			// Перемещаем ключи и значения на один элемент левее.
			for (int i = index; i < sizeCurrent - 1; i++)
			{
				if (keysType == TYPE_1_BYTE)
				{
					((byte[]) keys)[i] = ((byte[]) keys)[i + 1];
				}
				else if (keysType == TYPE_2_SHORT)
				{
					((short[]) keys)[i] = ((short[]) keys)[i + 1];
				}
				else if (keysType == TYPE_3_INT)
				{
					((int[]) keys)[i] = ((int[]) keys)[i + 1];
				}
				else if (keysType == TYPE_7_FLOAT)
				{
					((float[]) keys)[i] = ((float[]) keys)[i + 1];
				}
				else if (keysType == TYPE_9_OBJECT)
				{
					((Object[]) keys)[i] = ((Object[]) keys)[i + 1];
				}
				else if (keysType == TYPE_4_LONG)
				{
					((long[]) keys)[i] = ((long[]) keys)[i + 1];
				}
				else if (keysType == TYPE_6_CHAR)
				{
					((char[]) keys)[i] = ((char[]) keys)[i + 1];
				}
				else
				{
					throw new RuntimeException(MAP_MT + ERROR_KEY_TYPE_ERROR + keysType);
				}

				if (valuesType == TYPE_1_BYTE)
				{
					((byte[]) values)[i] = ((byte[]) values)[i + 1];
				}
				else if (valuesType == TYPE_2_SHORT)
				{
					((short[]) values)[i] = ((short[]) values)[i + 1];
				}
				else if (valuesType == TYPE_3_INT)
				{
					((int[]) values)[i] = ((int[]) values)[i + 1];
				}
				else if (valuesType == TYPE_7_FLOAT)
				{
					((float[]) values)[i] = ((float[]) values)[i + 1];
				}
				else if (valuesType == TYPE_9_OBJECT)
				{
					((Object[]) values)[i] = ((Object[]) values)[i + 1];
				}
				else if (valuesType == TYPE_4_LONG)
				{
					((long[]) values)[i] = ((long[]) values)[i + 1];
				}
				else if (valuesType == TYPE_6_CHAR)
				{
					((char[]) values)[i] = ((char[]) values)[i + 1];
				}
				else
				{
					throw new RuntimeException(MAP_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
				}
			}
		}

		// Уменьшаем текущий размер.
		sizeCurrent--;

		if (keysType == TYPE_1_BYTE)
		{
			((byte[]) keys)[sizeCurrent] = 0;
		}
		else if (keysType == TYPE_2_SHORT)
		{
			((short[]) keys)[sizeCurrent] = 0;
		}
		else if (keysType == TYPE_3_INT)
		{
			((int[]) keys)[sizeCurrent] = 0;
		}
		else if (keysType == TYPE_7_FLOAT)
		{
			((float[]) keys)[sizeCurrent] = 0;
		}
		else if (keysType == TYPE_9_OBJECT)
		{
			((Object[]) keys)[sizeCurrent] = null;
		}
		else if (keysType == TYPE_4_LONG)
		{
			((long[]) keys)[sizeCurrent] = 0;
		}
		else if (keysType == TYPE_6_CHAR)
		{
			((char[]) keys)[sizeCurrent] = 0;
		}
		else
		{
			throw new RuntimeException(MAP_MT + ERROR_KEY_TYPE_ERROR + keysType);
		}

		if (valuesType == TYPE_1_BYTE)
		{
			((byte[]) values)[sizeCurrent] = 0;
		}
		else if (valuesType == TYPE_2_SHORT)
		{
			((short[]) values)[sizeCurrent] = 0;
		}
		else if (valuesType == TYPE_3_INT)
		{
			((int[]) values)[sizeCurrent] = 0;
		}
		else if (valuesType == TYPE_7_FLOAT)
		{
			((float[]) values)[sizeCurrent] = 0;
		}
		else if (valuesType == TYPE_9_OBJECT)
		{
			((Object[]) values)[sizeCurrent] = null;
		}
		else if (valuesType == TYPE_4_LONG)
		{
			((long[]) values)[sizeCurrent] = 0;
		}
		else if (valuesType == TYPE_6_CHAR)
		{
			((char[]) values)[sizeCurrent] = 0;
		}
		else
		{
			throw new RuntimeException(MAP_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
		}
	}

	/**
	 * Удаляет пару [key, value] по ключу.
	 * @param key Ключ.
	 */
	public void removeKey(int key)
	{
		int keyIndex = getKeyIndex(key);
		removeIndex(keyIndex);
	}

	/**
	 * Удаляет пару [key, value] по значению.
	 * @param value Значение.
	 */
	public void removeValue(int value)
	{
		int valueIndex = getValueIndex(value);
		removeIndex(valueIndex);
	}

	/**
	 * Удаляет пару [key, value] по значению.
	 * @param value Значение.
	 */
	public void removeValue(Object value)
	{
		int valueIndex = getValueIndex(value);
		removeIndex(valueIndex);
	}

	/**
	 * Загружает ключи и значения из текстового файла в формате UTF-8.
	 * @param path       Путь к файлу.
	 * @param clear      Флаг предварительной очистки карты. Если выключена очистка, то старая информация микшируется с новой.
	 * @throws Exception Ошибка.
	 */
	public void loadUTF8(String path, boolean clear) throws Exception
	{
		// Символ в начале строки, обозначающий комментарий.
		final char COMMENT = '#';
		// Символ, разделяющий ключи и значения в строках.
		final char DIVIDER = '=';

		// Очищаем старые ключи и значения.
		if (clear)
		{
			clear();
		}

		// Открываем входной поток для чтения файла.
		File file = new File(path);
		if (!file.exists())
		{
			throw new Exception("File not found.\npath = \"" + path + "\"");
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), S.ENCODING_UTF_8));

		// Читаем файл построчно.
		String line;
		while (true)
		{
			line = br.readLine();
			if (line != null)
			{
				// Пустые строки игнорируем.
				if (line.length() == 0)
				{
					// Ignore.
				}
				// Строки, начинающиеся на решётку, считаются комментарием и игнорируются.
				else if (line.charAt(0) == COMMENT)
				{
					// Ignore.
				}
				else
				{
					// Остальные строки должны содержать разделитель.
					int dividerIndex = line.indexOf(DIVIDER);
					if (dividerIndex != -1)
					{
						String key = line.substring(0, dividerIndex);
						String value = line.substring(dividerIndex + 1, line.length());

						// Помещаем пару [ключ, значение] в карту в соответствии с поддерживаемыми типами данных.
						if (keysType == TYPE_9_OBJECT)
						{
							if (valuesType == TYPE_9_OBJECT)
							{
								put(key, value);
							}
							else
							{
								br.close();
								throw new RuntimeException(MAP_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
							}
						}
						else
						{
							br.close();
							throw new RuntimeException(MAP_MT + ERROR_KEY_TYPE_ERROR + keysType);
						}
					}
					// Неизвестный формат строки.
					else
					{
						br.close();
						throw new Exception("Unknown line format.");
					}
				}
			}
			else
			{
				break;
			}
		}

		br.close();
	}

	/**
	 * QuickSortUtil.
	 */

	/**
	 * Алгоритм быстрой сортировки для byte[].
	 * @param array byte[] to sort.
	 */
	public static void quickSort(byte[] array)
	{
		quickSortInner(array, 0, array.length - 1);
	}
	private static void quickSortInner(byte[] array, int left, int right)
	{
		if (left < right)
		{
			int m = quickSortPartition(array, left, right);
			quickSortInner(array, left, m-1);
			quickSortInner(array, m+1, right);
		}
	}
	private static int quickSortPartition(byte[] array, int left, int right)
	{
		int left0 = left;
		byte pivot = array[left0];

		while (left < right)
		{
			if (array[right] > pivot)
			{
				--right;
			}
			else if (array[left] <= pivot)
			{
				++left;
			}
			else
			{
				quickSortSwap(array, left, right);
			}
		}
		quickSortSwap(array, left0, right);

		return right;
	}
	private static void quickSortSwap(byte[] array, int x, int y)
	{
		byte tmp = array[x];
		array[x] = array[y];
		array[y] = tmp;
	}

	/**
	 * Алгоритм быстрой сортировки для short[].
	 * @param array short[] to sort.
	 */
	public static void quickSort(short[] array)
	{
		quickSortInner(array, 0, array.length - 1);
	}
	private static void quickSortInner(short[] array, int left, int right)
	{
		if (left < right)
		{
			int m = quickSortPartition(array, left, right);
			quickSortInner(array, left, m-1);
			quickSortInner(array, m+1, right);
		}
	}
	private static int quickSortPartition(short[] array, int left, int right)
	{
		int left0 = left;
		short pivot = array[left0];

		while (left < right)
		{
			if (array[right] > pivot)
			{
				--right;
			}
			else if (array[left] <= pivot)
			{
				++left;
			}
			else
			{
				quickSortSwap(array, left, right);
			}
		}
		quickSortSwap(array, left0, right);

		return right;
	}
	private static void quickSortSwap(short[] array, int x, int y)
	{
		short tmp = array[x];
		array[x] = array[y];
		array[y] = tmp;
	}

	/**
	 * Алгоритм быстрой сортировки для char[].
	 * @param array char[] to sort.
	 */
	public static void quickSort(char[] array)
	{
		quickSortInner(array, 0, array.length - 1);
	}
	private static void quickSortInner(char[] array, int left, int right)
	{
		if (left < right)
		{
			int m = quickSortPartition(array, left, right);
			quickSortInner(array, left, m-1);
			quickSortInner(array, m+1, right);
		}
	}
	private static int quickSortPartition(char[] array, int left, int right)
	{
		int left0 = left;
		char pivot = array[left0];

		while (left < right)
		{
			if (array[right] > pivot)
			{
				--right;
			}
			else if (array[left] <= pivot)
			{
				++left;
			}
			else
			{
				quickSortSwap(array, left, right);
			}
		}
		quickSortSwap(array, left0, right);

		return right;
	}
	private static void quickSortSwap(char[] array, int x, int y)
	{
		char tmp = array[x];
		array[x] = array[y];
		array[y] = tmp;
	}

	/**
	 * Алгоритм быстрой сортировки для int[].
	 * @param array int[] to sort.
	 */
	public static void quickSort(int[] array)
	{
		quickSortInner(array, 0, array.length - 1);
	}
	private static void quickSortInner(int[] array, int left, int right)
	{
		if (left < right)
		{
			int m = quickSortPartition(array, left, right);
			quickSortInner(array, left, m - 1);
			quickSortInner(array, m + 1, right);
		}
	}
	private static int quickSortPartition(int[] array, int left, int right)
	{
		int left0 = left;
		int pivot = array[left0];

		while (left < right)
		{
			if (array[right] > pivot)
			{
				--right;
			}
			else if (array[left] <= pivot)
			{
				++left;
			}
			else
			{
				quickSortSwap(array, left, right);
			}
		}
		quickSortSwap(array, left0, right);

		return right;
	}
	private static void quickSortSwap(int[] array, int x, int y)
	{
		int tmp = array[x];
		array[x] = array[y];
		array[y] = tmp;
	}
}
