package files;

import android.os.Environment;

import java.io.File;

public class FileValidator {

	public static boolean fileExists(String path) {
		File file = getFile(path);

		if (file == null || !file.exists()) {
			return false;
		}

		if (file.isFile()) {
			return true;
		}

		return false;
	}

	public static boolean dirExists(String path) {
		File file = getFile(path);

		if (file == null || !file.exists()) {
			return false;
		}

		if (file.isDirectory()) {
			return true;
		}

		return false;
	}

	public static File getFile(String path) {
		if (path != null) {
			File file = Environment.getExternalStorageDirectory();
			return new File(file, path);
		}

		return null;
	}
}
