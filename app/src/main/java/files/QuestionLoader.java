package files;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import data.QuestionData;

public class QuestionLoader {
	private JsonReader reader;
	private QuestionData data;

	/**
	 * Loads the data needed for the exercise.
	 * @param reader
	 *      Reader stream to access the file where the information is.
	 */
	public QuestionLoader(JsonReader reader) {
		this.reader = reader;
		this.data = new QuestionData();
	}

	/**
	 * Loads data from the given Reader file.
	 * @return
	 *      An instance of LanguageData with all the data read included.
	 */
	public QuestionData load() {
		Gson gson = new Gson();
		data = gson.fromJson(reader, QuestionData.class);

		return data;
	}
}
