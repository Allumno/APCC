package files;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import data.GenericData;

public class GenericDataLoader {
	private JsonReader reader;
	private GenericData data;

	/**
	 * Loads the data needed for the exercise.
	 * @param reader
	 *      Reader stream to access the file where the information is.
	 */
	public GenericDataLoader(JsonReader reader) {
		this.reader = reader;
		this.data = new GenericData();
	}

	/**
	 * Loads data from the given Reader file.
	 * @return
	 *      An instance of GenericData with all the data read included.
	 */
	public GenericData load() {
		Gson gson = new Gson();
		data = gson.fromJson(reader, GenericData.class);

		return data;
	}
}
