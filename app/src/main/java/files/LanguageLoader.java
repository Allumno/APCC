package files;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import data.LanguageData;

public class LanguageLoader {
	private JsonReader reader;
	private LanguageData data;

	/**
	 * Loads the data needed for the exercise.
	 * @param reader
	 *      Reader stream to access the file where the information is.
	 */
	public LanguageLoader(JsonReader reader) {
		this.reader = reader;
		this.data = new LanguageData();
	}

	/**
	 * Loads data from the given Reader file.
	 * @return
	 *      An instance of LanguageData with all the data read included.
	 */
	public LanguageData load() {
		if (reader != null) {
			Gson gson = new Gson();
			data = gson.fromJson(reader, LanguageData.class);

			return data;
		}

		return null;
	}
}
