package files;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import data.HeaderData;

public class HeaderLoader {
	private JsonReader reader;
	private HeaderData data;

	/**
	 * Loads the data needed for the header.
	 * @param reader
	 *      Reader stream to access the file where the information is.
	 */
	public HeaderLoader(JsonReader reader) {
		this.reader = reader;
		this.data = new HeaderData();
	}

	/**
	 * Loads data from the given Reader file.
	 * @return
	 *      An instance with all the data read included.
	 */
	public HeaderData load() {
		if (reader != null) {
			Gson gson = new Gson();
			data = gson.fromJson(reader, HeaderData.class);

			return data;
		}

		return null;
	}
}
