package files;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import data.LessonData;

public class LessonLoader {
	private JsonReader reader;
	private LessonData data;

	/**
	 * Loads the data needed for the lesson.
	 * @param reader
	 *      Reader stream to access the file where the information is.
	 */
	public LessonLoader(JsonReader reader) {
		this.reader = reader;
		this.data = new LessonData();
	}

	/**
	 * Loads data from the given Reader file.
	 * @return
	 *      An instance with all the data read included.
	 */
	public LessonData load() {
		Gson gson = new Gson();
		data = gson.fromJson(reader, LessonData.class);

		return data;
	}
}
