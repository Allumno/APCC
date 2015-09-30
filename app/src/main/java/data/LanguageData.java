package data;

import java.util.ArrayList;
import java.util.Collections;

public class LanguageData {

	private String ID;
	private String author;

	private String type;

	private String text;
	private ArrayList<LanguageTurnData> options;
	private String imgPath;

	/**
	 * Empty public constructor.
	 */
	public LanguageData() {
		super();
		options = new ArrayList<LanguageTurnData>();
	}

	/**
	 * Retrieves the text of the exercise.
	 * @return
	 *      A string with the text of the exercise.
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the text of the exercise.
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Gets a list of all the turns contained in this exercise.
	 * @return
	 *      A list with all the information of all the turns in this exercise
	 *      (correct and incorrect options).
	 */
	public ArrayList<LanguageTurnData> getOptions() {
		return options;
	}

	/**
	 * Returns all the options, already MIXED, available for the given theme/turn.
	 * @param level
	 *      Level from which the options should be retrieved.
	 * @return
	 *      A string array with the given options.
	 *      Null if the theme is not defined or the options list is null.
	 */
	public ArrayList<String> getOptions(int level) {
		if (level >= 0 && level < getMaxTurns()) {
			LanguageTurnData turn = options.get(level);

			ArrayList<String> mixedOptions = turn.getAllOptions();
			Collections.shuffle(mixedOptions);

			return mixedOptions;
		}

		return null;
	}

	/**
	 * Adds a turn to the exercise.
	 * @param turn
	 *      An instance of LanguageTurnData.
	 */
	public void setOptions(LanguageTurnData turn) {
		this.options.add(turn);
	}

	/**
	 * Sets all the turns.
	 * @param options
	 *      A list with the information pertaining all the turns to be included in this exercise.
	 */
	public void setOptions(ArrayList<LanguageTurnData> options) {
		this.options = options;
	}

	/**
	 * Gets the correct option for the given turn.
	 * @return
	 *      Returns a String with the correct option.
	 *      Null if the given turn is off limits or there is no correct option defined.
	 */
	public String getCorrect(int level) {
		if (level >= 0 && level < getMaxTurns()) {
			LanguageTurnData turn = options.get(level);
			return turn.getCorrect();
		}

		return null;
	}

	/**
	 * Gets the incorrect list of options for the given turn.
	 * @return
	 *      Returns an Arraylist of Strings with the incorrect options.
	 *      Null if the given turn is off limits or there is no incorrect options defined.
	 */
	public ArrayList<String> getIncorrect(int level) {
		if (level >= 0 && level < getMaxTurns()) {
			LanguageTurnData turn = options.get(level);
			return turn.getIncorrect();
		}

		return null;
	}

	/**
	 * Gets the total number of turns in this exercise.
	 * @return
	 *      The total number of turns.
	 */
	public long getMaxTurns() {
		return options.size();
	}

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
}
