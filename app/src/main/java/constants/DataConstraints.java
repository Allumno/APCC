package constants;

import java.util.HashMap;
import java.util.regex.Pattern;

import files.FileValidator;

public class DataConstraints {

	//  Maximum values allowed
	public static final int minLevel = 0;
	public static final int maxLevel = 100;

	public static final HashMap<ViewTypes, Integer> STRING_LENGTHS = new HashMap<ViewTypes,Integer>()
	{{  put(ViewTypes.ID,           100);
		put(ViewTypes.AUTHOR,       100);
		put(ViewTypes.CATEGORY,     20);                //  Ex.: Geometry
		put(ViewTypes.ASSIGNMENT,   40);                //  Ex.: Geometry applied to architecture
		put(ViewTypes.LEVEL,        10);                //  The level can be numerical or abstract (easy, medium, ...)
		put(ViewTypes.OPTION,       50);                //  Text to be inserted in the buttons
		put(ViewTypes.QUESTION,     100);
		put(ViewTypes.LANG_TEXT,    3000);              //  Language text length
		put(ViewTypes.LESSON,       10000);
		put(ViewTypes.DIR_PATH,     10000);
		}};

	public static final HashMap<ViewTypes, Boolean> NULLABLE_VALUES = new HashMap<ViewTypes,Boolean>()
	{{  put(ViewTypes.ID,           false);
		put(ViewTypes.AUTHOR,       true);
		put(ViewTypes.CATEGORY,     false);
		put(ViewTypes.ASSIGNMENT,   false);
		put(ViewTypes.LEVEL,        false);
		put(ViewTypes.OPTION,       false);
		put(ViewTypes.QUESTION,     false);
		put(ViewTypes.LANG_TEXT,    false);
		put(ViewTypes.LESSON,       false);
		put(ViewTypes.DIR_PATH,     false);
		}};

	public static boolean validateLevel(int level) {
		if (level < minLevel || level > maxLevel) {
			return false;
		}

		return true;
	}

	/**
	 * Checks if the given string is inside the established parameters.
	 * @param str
	 *      The string to be verified.
	 * @param type
	 *      The type of field view where the string will be.
	 * @return
	 *      TRUE if the string is valid.
	 *      FALSE otherwise.
	 */
	public static boolean validateStringLength(String str, ViewTypes type) {
		if (str == null) {
			return false;
		}

		int size = str.length();

		if(size < 0 || size > STRING_LENGTHS.get(type)) {
			return false;
		}

		return true;
	}

	/**
	 * Checks if the given string is null and if it is allowed to be null.
	 * @param str
	 *      The string to be verified.
	 * @param type
	 *      The type of field view where the string will be used.
	 * @return
	 *      TRUE if the string is not null OR is null and allowed to be it.
	 *      FALSE otherwise.
	 */
	public static boolean validateNullableValue(String str, ViewTypes type) {
		if (str == null && !NULLABLE_VALUES.get(type)) {
			return false;
		}

		return true;
	}

	/**
	 * Checks if the given string is a correct path.
	 * @param str
	 *      String to be analysed.
	 * @return
	 *      True if it is a valid path.
	 *      False otherwise.
	 */
	public static boolean validatePath(String str) {
		if (str == null) {
			return false;
		}

		return Pattern.matches(Configurations.REGEX, str);
	}

	/**
	 *  Checks if the given value meets the specified conditions.
	 * @param str
	 *      The string to be verified.
	 * @param type
	 *      The type of field view where the string will be used.
	 * @return
	 *      TRUE if the string passes both tests.
	 *      FALSE otherwise.
	 */
	public static boolean validateValue(String str, ViewTypes type) {
		boolean check = false;

		check = validateNullableValue(str, type);
		//  The previous validation may be return TRUE if the variable is nullable.
		//  For that case, and to avoid conflicts, this condition is needed.
		if (str != null) {
			check &= validateStringLength(str, type);

			if (type == ViewTypes.DIR_PATH && validatePath(str)) {
				check &= validateFile(str);
			}
		}

		return check;
	}

	/**
	 * Checks if the given path mentions an existent file.
	 * @param path
	 *      The path to be analyzed.
	 * @return
	 *      True if it is a file.
	 *      False otherwise.
	 */
	public static boolean validateFile(String path) {
		boolean check = false;

		check = FileValidator.fileExists(path);

		return check;
	}

	/**
	 * Checks if the given path mentions an existent directory.
	 * @param path
	 *      The path to be analyzed.
	 * @return
	 *      True if it is a directory.
	 *      False otherwise.
	 */
	public static boolean validateDirectory(String path) {
		boolean check = false;

		check = FileValidator.dirExists(path);

		return check;
	}
}
