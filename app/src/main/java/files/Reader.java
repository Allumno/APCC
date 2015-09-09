package files;

import android.os.Environment;
import android.util.Log;

import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class Reader {
	private String path;
	private File file;
	private JsonReader reader;

	public Reader(String filepath) {
		path = filepath;
		file = null;
		reader = null;
	}

	public Reader(File file) {
		this.file = file;
	}

	public void open() {
		try {
			if (file == null && path != null) {
				file = Environment.getExternalStorageDirectory();
				file = new File(file, path);
			}

			if (file != null && file.exists()) {
				FileReader in = new FileReader(file);
				reader = new JsonReader(in);
			}
		} catch(Exception err) {
			Log.wtf("[Reader]", "Failed to open read stream at: " + file.getAbsolutePath());
			Log.wtf("[ERROR]", err.toString());
		}
	}

	public JsonReader getReader() {
		return reader;
	}

	public void close(){
		if (reader != null) {
			try {
				reader.close();
			} catch (Exception err) {
				Log.wtf("[Reader]", "Failed to close read stream at: " + file.getAbsolutePath());
				Log.wtf("[ERROR]", err.toString());
			}
		}
	}
}
