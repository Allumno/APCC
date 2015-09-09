package files;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import data.ImageQuestionData;

public class ImageQuestionLoader {

	private JsonReader reader;
	private ImageQuestionData data;

	/**
	 * Loads the data needed for the exercise.
	 * @param reader
	 *      Reader stream to access the file where the information is.
	 */
	public ImageQuestionLoader(JsonReader reader) {
		this.reader = reader;
		this.data = new ImageQuestionData();
	}

	/**
	 * Loads data from the given Reader file.
	 * @return
	 *      An instance of LanguageData with all the data read included.
	 */
	public ImageQuestionData load() {
		Gson gson = new Gson();
		data = gson.fromJson(reader, ImageQuestionData.class);

		return data;
	}

}
