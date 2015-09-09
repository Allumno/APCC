package files;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.IOException;

import data.MetaData;

public class MetaDataLoader {
	private Reader reader;
	private JsonReader jsonReader;
	private MetaData data;

	/**
	 * Loads the meta data.
	 * @param path
	 *      Path of the file where the information is.
	 */
	public MetaDataLoader(String path) {
		this.reader = new Reader(path);
		this.jsonReader = null;
		this.data = null;
	}

	/**
	 * Loads the meta data.
	 * @param file
	 *      File instance where the information is.
	 */
	public MetaDataLoader(File file) {
		this.reader = new Reader(file);
		this.jsonReader = null;
		this.data = null;
	}

	/**
	 * Opens the stream for reading.
	 */
	public void open() {
		if(this.reader != null) {
			this.reader.open();
			this.jsonReader = this.reader.getReader();
		}
	}

	/**
	 * Closes the reading stream.
	 */
	public void close() {
		if(this.jsonReader != null) {
			try {
				this.jsonReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Loads data from the given meta data file.
	 * @return
	 *      An instance with all the data read included.
	 */
	public MetaData load() {
		try {
			if (jsonReader != null) {
				Gson gson = new Gson();
				data = gson.fromJson(jsonReader, MetaData.class);
			}
		} catch (Exception err) {
			Log.wtf("[MetaDataLoader]", "Failed to parse the metadata file!");
			Log.wtf("[MetaDataLoader]", err.getMessage());
		}

		return data;
	}
}
