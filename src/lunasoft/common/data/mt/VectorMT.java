package lunasoft.common.data.mt;

/**
 * Vector multi type.
 * Одномерный массив динамической длины.
 *
 * Created : 26.07.2013, 10:10.
 * Modified: 27.07.2013, 10:10.
 * Modified: 13.09.2013, 21:57.
 * Modified: 10.01.2016, 10:59. Исправил putToIndex.
 * Modified: 04.12.2018, 15:31. Добавил метод setMinimalSize(int minimalSize).
 *                              Добавил в метод incSize параметр (int sizeDelta).
 * Modified: 05.01.2019, 09:46. Добавил метод cloneVector().
 */
public class VectorMT implements MT
{
	/** Тип значений. */
	private byte valuesType = TYPE_1_BYTE;
	/** Значения. */
	private Object values;
	/** Общая длина массива данных. */
	private int sizeTotal;
	/** Занятая часть массива данных. */
	public int sizeCurrent;

	public VectorMT(byte valuesType, int sizeTotal)
	{
		this.valuesType = valuesType;
		this.sizeTotal = sizeTotal;
		this.sizeCurrent = 0;

		// Инициализация массива значений.
		if (valuesType == TYPE_1_BYTE)
		{
			values = new byte[sizeTotal];
		}
		else if (valuesType == TYPE_2_SHORT)
		{
			values = new short[sizeTotal];
		}
		else if (valuesType == TYPE_3_INT)
		{
			values = new int[sizeTotal];
		}
		else if (valuesType == TYPE_7_FLOAT)
		{
			values = new float[sizeTotal];
		}
		else if (valuesType == TYPE_9_OBJECT)
		{
			values = new Object[sizeTotal];
		}
		else if (valuesType == TYPE_4_LONG)
		{
			values = new long[sizeTotal];
		}
		else if (valuesType == TYPE_6_CHAR)
		{
			values = new char[sizeTotal];
		}
		else
		{
			throw new RuntimeException(VECTOR_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
		}
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
	 * Увеличивает общий размер вектора на sizeDelta элементов.
	 *
	 * @param sizeDelta   На сколько нужно увеличить размер вектора.
	 */
	private void incSize(int sizeDelta)
	{
		if (valuesType == TYPE_1_BYTE)
		{
			// Инициализировать новые массивы.
			byte[] valuesNew = new byte[sizeTotal + sizeDelta];
			// Переписать все значимые данные из старых массивов в новые.
//			for (int i = 0; i < sizeCurrent; i++)
//			{
//				valuesNew[i] = ((byte[]) values)[i];
//			}
			System.arraycopy(values, 0, valuesNew, 0, sizeCurrent);
			// Установить для использования новые массивы.
			values = valuesNew;
		}
		else if (valuesType == TYPE_2_SHORT)
		{
			// Инициализировать новые массивы.
			short[] valuesNew = new short[sizeTotal + sizeDelta];
			// Переписать все значимые данные из старых массивов в новые.
//			for (int i = 0; i < sizeCurrent; i++)
//			{
//				valuesNew[i] = ((short[]) values)[i];
//			}
			System.arraycopy(values, 0, valuesNew, 0, sizeCurrent);
			// Установить для использования новые массивы.
			values = valuesNew;
		}
		else if (valuesType == TYPE_3_INT)
		{
			// Инициализировать новые массивы.
			int[] valuesNew = new int[sizeTotal + sizeDelta];
			// Переписать все значимые данные из старых массивов в новые.
//			for (int i = 0; i < sizeCurrent; i++)
//			{
//				valuesNew[i] = ((int[]) values)[i];
//			}
			System.arraycopy(values, 0, valuesNew, 0, sizeCurrent);
			// Установить для использования новые массивы.
			values = valuesNew;
		}
		else if (valuesType == TYPE_7_FLOAT)
		{
			// Инициализировать новые массивы.
			float[] valuesNew = new float[sizeTotal + sizeDelta];
			// Переписать все значимые данные из старых массивов в новые.
//			for (int i = 0; i < sizeCurrent; i++)
//			{
//				valuesNew[i] = ((float[]) values)[i];
//			}
			System.arraycopy(values, 0, valuesNew, 0, sizeCurrent);
			// Установить для использования новые массивы.
			values = valuesNew;
		}
		else if (valuesType == TYPE_9_OBJECT)
		{
			// Инициализировать новые массивы.
			Object[] valuesNew = new Object[sizeTotal + sizeDelta];
			// Переписать все значимые данные из старых массивов в новые.
//			for (int i = 0; i < sizeCurrent; i++)
//			{
//				valuesNew[i] = ((Object[]) values)[i];
//			}
			System.arraycopy(values, 0, valuesNew, 0, sizeCurrent);
			// Установить для использования новые массивы.
			values = valuesNew;
		}
		else if (valuesType == TYPE_4_LONG)
		{
			// Инициализировать новые массивы.
			long[] valuesNew = new long[sizeTotal + sizeDelta];
			// Переписать все значимые данные из старых массивов в новые.
//			for (int i = 0; i < sizeCurrent; i++)
//			{
//				valuesNew[i] = ((long[]) values)[i];
//			}
			System.arraycopy(values, 0, valuesNew, 0, sizeCurrent);
			// Установить для использования новые массивы.
			values = valuesNew;
		}
		else if (valuesType == TYPE_6_CHAR)
		{
			// Инициализировать новые массивы.
			char[] valuesNew = new char[sizeTotal + sizeDelta];
			// Переписать все значимые данные из старых массивов в новые.
//			for (int i = 0; i < sizeCurrent; i++)
//			{
//				valuesNew[i] = ((char[]) values)[i];
//			}
			System.arraycopy(values, 0, valuesNew, 0, sizeCurrent);
			// Установить для использования новые массивы.
			values = valuesNew;
		}
		else
		{
			throw new RuntimeException(VECTOR_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
		}

		// Итерируем общий размер.
		sizeTotal += sizeDelta;
	}

	/**
	 * Устанавливает общий размер вектора не меньше, чем minimalSize.
	 *
	 * @param minimalSize   Минимальный общий размер вектора.
	 */
	public void setMinimalTotalSize(int minimalSize)
	{
		if (minimalSize > sizeTotal)
		{
			incSize(minimalSize - sizeTotal);
		}
	}

	/**
	 * Обнуляет и очищает вектор.
	 */
	public void clear()
	{
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
		else
		{
			throw new RuntimeException(VECTOR_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
		}

		// Обнуляем размер.
		sizeCurrent = 0;
	}

	/**
	 * Добавляет значение в конец вектора.
	 */
	public VectorMT put(int value)
	{
		// Если места нет, то увеличить общий размер вектора.
		if (sizeCurrent >= sizeTotal)
		{
			incSize(SIZE_INCREMENT_VALUE);
		}

		if (valuesType == TYPE_1_BYTE)
		{
			if (value < Byte.MIN_VALUE || value > Byte.MAX_VALUE)
			{
				throw new RuntimeException(VECTOR_MT + ERROR_VALUE_RANGE_ERROR);
			}
			((byte[]) values)[sizeCurrent] = (byte) value;
		}
		else if (valuesType == TYPE_2_SHORT)
		{
			if (value < Short.MIN_VALUE || value > Short.MAX_VALUE)
			{
				throw new RuntimeException(VECTOR_MT + ERROR_VALUE_RANGE_ERROR);
			}
			((short[]) values)[sizeCurrent] = (short) value;
		}
		else if (valuesType == TYPE_3_INT)
		{
			((int[]) values)[sizeCurrent] = value;
		}
		else if (valuesType == TYPE_6_CHAR)
		{
			if (value < Character.MIN_VALUE || value > Character.MAX_VALUE)
			{
				throw new RuntimeException(VECTOR_MT + ERROR_VALUE_RANGE_ERROR);
			}
			((char[]) values)[sizeCurrent] = (char) value;
		}
		else
		{
			throw new RuntimeException(VECTOR_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
		}

		// Итерируем текущий размер.
		sizeCurrent++;

		return this;
	}

	/**
	 * Добавляет значение в конец вектора.
	 */
	public void putLong(long value)
	{
		// Если места нет, то увеличить общий размер вектора.
		if (sizeCurrent >= sizeTotal)
		{
			incSize(SIZE_INCREMENT_VALUE);
		}

		if (valuesType == TYPE_4_LONG)
		{
			((long[]) values)[sizeCurrent] = value;
		}
		else
		{
			throw new RuntimeException(VECTOR_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
		}

		// Итерируем текущий размер.
		sizeCurrent++;
	}

	/**
	 * Добавляет значение в конец вектора.
	 */
	public VectorMT put(Object value)
	{
		// Если места нет, то увеличить общий размер вектора.
		if (sizeCurrent >= sizeTotal)
		{
			incSize(SIZE_INCREMENT_VALUE);
		}

		if (valuesType == TYPE_9_OBJECT)
		{
			((Object[]) values)[sizeCurrent] = value;
		}
		else
		{
			throw new RuntimeException(VECTOR_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
		}

		// Итерируем текущий размер.
		sizeCurrent++;

		return this;
	}

	/**
	 * Добавляет значение по указанному индексу.
	 */
	public void putLongToIndex(long value, int index)
	{
		if (index < 0 || index > sizeCurrent)
		{
			throw new RuntimeException(VECTOR_MT + ERROR_WRONG_INDEX + " Index = " + index + ", sizeCurrent = " + sizeCurrent + ".");
		}

		// Если места нет, то увеличить общий размер вектора.
		if (sizeCurrent >= sizeTotal)
		{
			incSize(SIZE_INCREMENT_VALUE);
		}

		// Передвигаем указанный и все последующие индексы вперёд на одну позицию.
		for (int i = sizeCurrent; i > index; i--)
		{
			if (valuesType == TYPE_4_LONG)
			{
				((long[]) values)[i] = ((long[]) values)[i - 1];
			}
			else
			{
				throw new RuntimeException(VECTOR_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
			}
		}

		if (valuesType == TYPE_4_LONG)
		{
			((long[]) values)[index] = value;
		}
		else
		{
			throw new RuntimeException(VECTOR_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
		}

		// Итерируем текущий размер.
		sizeCurrent++;
	}

	/**
	 * Добавляет значение по указанному индексу.
	 */
	public void putToIndex(Object value, int index)
	{
		if (index < 0 || index > sizeCurrent)
		{
			throw new RuntimeException(VECTOR_MT + ERROR_WRONG_INDEX + " Index = " + index + ", sizeCurrent = " + sizeCurrent + ".");
		}

		// Если места нет, то увеличить общий размер вектора.
		if (sizeCurrent >= sizeTotal)
		{
			incSize(SIZE_INCREMENT_VALUE);
		}

		// Передвигаем указанный и все последующие индексы вперёд на одну позицию.
		for (int i = sizeCurrent; i > index; i--)
		{
			if (valuesType == TYPE_9_OBJECT)
			{
				((Object[]) values)[i] = ((Object[]) values)[i - 1];
			}
			else
			{
				throw new RuntimeException(VECTOR_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
			}
		}

		if (valuesType == TYPE_9_OBJECT)
		{
			((Object[]) values)[index] = value;
		}
		else
		{
			throw new RuntimeException(VECTOR_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
		}

		// Итерируем текущий размер.
		sizeCurrent++;
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
	 * Возвращает индекс значения.
	 * @param value Значение.
	 * @return      Индекс значения (-1, если нет).
	 */
	public int getValueIndex(int value)
	{
		int valueIndex = -1;

		for (int i = 0; i < sizeCurrent; i++)
		{
			if (valuesType == TYPE_1_BYTE)
			{
				if (value == ((byte[]) values)[i])
				{
					valueIndex = i;
					break;
				}
			}
			else if (valuesType == TYPE_2_SHORT)
			{
				if (value == ((short[]) values)[i])
				{
					valueIndex = i;
					break;
				}
			}
			else if (valuesType == TYPE_3_INT)
			{
				if (value == ((int[]) values)[i])
				{
					valueIndex = i;
					break;
				}
			}
			else if (valuesType == TYPE_6_CHAR)
			{
				if (value == ((char[]) values)[i])
				{
					valueIndex = i;
					break;
				}
			}
			else
			{
				throw new RuntimeException(VECTOR_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
			}
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

		for (int i = 0; i < sizeCurrent; i++)
		{
			if (valuesType == TYPE_7_FLOAT)
			{
				if (value == ((float[]) values)[i])
				{
					valueIndex = i;
					break;
				}
			}
			else
			{
				throw new RuntimeException(VECTOR_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
			}
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

		for (int i = 0; i < sizeCurrent; i++)
		{
			if (valuesType == TYPE_9_OBJECT)
			{
				Object[] valuesObject = (Object[]) values;
				if (
						(value == null && valuesObject[i] == null) ||
						(value != null && value.equals(valuesObject[i]))
					)
				{
					valueIndex = i;
					break;
				}
			}
			else
			{
				throw new RuntimeException(VECTOR_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
			}
		}

		return valueIndex;
	}

	public void removeIndex(int index)
	{
		// Проверяем индекс.
		if (index < 0 || index > sizeCurrent - 1)
		{
			throw new RuntimeException(VECTOR_MT + ERROR_WRONG_INDEX + " Index = " + index + ", sizeCurrent = " + sizeCurrent + ".");
		}

		// Удаляем элемент.
		if (valuesType == TYPE_1_BYTE)
		{
			for (int i = index + 1; i < sizeCurrent; i++)
			{
				((byte[]) values)[i - 1] = ((byte[]) values)[i];
			}
		}
		else if (valuesType == TYPE_2_SHORT)
		{
			for (int i = index + 1; i < sizeCurrent; i++)
			{
				((short[]) values)[i - 1] = ((short[]) values)[i];
			}
		}
		else if (valuesType == TYPE_3_INT)
		{
			for (int i = index + 1; i < sizeCurrent; i++)
			{
				((int[]) values)[i - 1] = ((int[]) values)[i];
			}
		}
		else if (valuesType == TYPE_6_CHAR)
		{
			for (int i = index + 1; i < sizeCurrent; i++)
			{
				((char[]) values)[i - 1] = ((char[]) values)[i];
			}
		}
		else if (valuesType == TYPE_9_OBJECT)
		{
			for (int i = index + 1; i < sizeCurrent; i++)
			{
				((Object[]) values)[i - 1] = ((Object[]) values)[i];
			}
		}
		else
		{
			throw new RuntimeException(VECTOR_MT + ERROR_VALUE_TYPE_NOT_IMPLEMENTED_YET + " valuesType = " + valuesType + ".");
		}

		// Уменьшаем текущий размер.
		sizeCurrent--;
	}

	public void removeValue(Object value)
	{
		if (valuesType == TYPE_9_OBJECT)
		{
			// Ищем индекс элемента.
			int index = -1;
			for (int i = 0; i < sizeCurrent; i++)
			{
				if (
						(value == null && ((Object[]) values)[i] == null) ||
						(value != null && value.equals(((Object[]) values)[i]))
					)
				{
					index = i;
					break;
				}
			}

			// Проверяем индекс.
			if (index < 0 || index > sizeCurrent - 1)
			{
				throw new RuntimeException(VECTOR_MT + ERROR_WRONG_INDEX + " Index = " + index + ", sizeCurrent = " + sizeCurrent + ".");
			}

			// Удаляем элемент.
			for (int i = index + 1; i < sizeCurrent; i++)
			{
				((Object[]) values)[i - 1] = ((Object[]) values)[i];
			}

			// Уменьшаем текущий размер.
			sizeCurrent--;
		}
		else
		{
			throw new RuntimeException(VECTOR_MT + ERROR_VALUE_TYPE_NOT_IMPLEMENTED_YET);
		}
	}

	public String[] toStringArray()
	{
		String[] stringArray = new String[sizeCurrent];

		if (valuesType == TYPE_9_OBJECT)
		{
			for (int i = 0; i < sizeCurrent; i++)
			{
				stringArray[i] = (String) ((Object[]) values)[i];
			}
		}
		else
		{
			throw new RuntimeException(VECTOR_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
		}

		return stringArray;
	}

	public VectorMT cloneVector()
	{
		VectorMT newVector = new VectorMT(valuesType, sizeTotal);
		newVector.sizeCurrent = sizeCurrent;

		if (valuesType == TYPE_1_BYTE)
		{
			for (int i = 0; i < sizeCurrent; i++)
			{
				((byte[]) newVector.values)[i] = ((byte[]) values)[i];
			}
		}
//		else if (valuesType == TYPE_2_SHORT)
//		{
//			values = new short[sizeTotal];
//		}
//		else if (valuesType == TYPE_3_INT)
//		{
//			values = new int[sizeTotal];
//		}
		else if (valuesType == TYPE_4_LONG)
		{
			for (int i = 0; i < sizeCurrent; i++)
			{
				((long[]) newVector.values)[i] = ((long[]) values)[i];
			}
		}
//		else if (valuesType == TYPE_6_CHAR)
//		{
//			values = new char[sizeTotal];
//		}
//		else if (valuesType == TYPE_7_FLOAT)
//		{
//			values = new float[sizeTotal];
//		}
		else if (valuesType == TYPE_9_OBJECT)
		{
			for (int i = 0; i < sizeCurrent; i++)
			{
				((Object[]) newVector.values)[i] = ((Object[]) values)[i];
			}
		}
		else
		{
			throw new RuntimeException(VECTOR_MT + ERROR_VALUE_TYPE_ERROR + valuesType);
		}

		return newVector;
	}
}
