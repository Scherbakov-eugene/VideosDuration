package lunasoft.common.data.mt;

/**
 * Multi type.
 * Общие константы для VectorMT и MapMT.
 * Created : 27.07.2013 10:10:10.
 * Modified: 18.08.2013 19:57:00.
 * Modified: 19.08.2013 17:13:00.
 * Modified: 20.08.2013 20:03:00.
 */
public interface MT
{
	public static final String VECTOR_MT = "VectorMT";
	public static final String MAP_MT = "MapMT";

	// Идентификаторы ошибок.
	public static final String ERROR_KEY_TYPE_ERROR = ": key type error. Type = ";
	public static final String ERROR_KEY_TYPE_NOT_IMPLEMENTED_YET = ": key type not implemented yet. Type = ";
	public static final String ERROR_KEY_NOT_FOUND = ": key not found.";
	public static final String ERROR_KEY_ALREADY_EXISTS = ": key already exists, key = ";

	public static final String ERROR_VALUE_TYPE_ERROR = ": value type error. Type = ";
	public static final String ERROR_VALUE_TYPE_NOT_IMPLEMENTED_YET = ": value type not implemented yet.";
	public static final String ERROR_VALUE_NOT_FOUND = ": value not found.";
	public static final String ERROR_VALUE_NOT_FOUND_FOR_KEY = ": value not found for key = ";
	public static final String ERROR_VALUE_RANGE_ERROR = ": value range error.";

	public static final String ERROR_WRONG_INDEX = ": wrong index.";

	// Идентификаторы типов данных. Менять значения запрещено. Используются при загрузке и сохранении данных.
	/** Длина: 1 байт. Диапазон: [-128, 127]. */
	public static final byte TYPE_1_BYTE = 1;
	/** Длина: 2 байта. Диапазон: [-32 768, 32 767]. */
	public static final byte TYPE_2_SHORT = 2;
	/** Длина: 4 байта. Диапазон:  [-2 147 483 648, 2 147 483 647] или [0x80000000, 0x7fffffff]. */
	public static final byte TYPE_3_INT = 3;
	/** Длина: 8 байт. Диапазон: [-9 223 372 036 854 775 808, 9 223 372 036 854 775 807] или [0x8000000000000000L, 0x7fffffffffffffffL]. */
	public static final byte TYPE_4_LONG = 4;
	/** Длина: 1 байт. Диапазон: [0, 255]. */
	public static final byte TYPE_5_UNSIGNED_BYTE = 5;
	/** Длина: 2 байта. Диапазон: [0, 65 535]. */
	public static final byte TYPE_6_CHAR = 6;
	/** Длина: 4 байта. Равенство неопределено. */
	public static final byte TYPE_7_FLOAT = 7; 
	/** Длина: 8 байт. Равенство неопределено. */
	public static final byte TYPE_8_DOUBLE = 8;
	/** Равенство должно быть через equals везде. */
	public static final byte TYPE_9_OBJECT = 9;

	/** Величина, на которую итерируется размер. */
	public static final int SIZE_INCREMENT_VALUE = 10;
}
