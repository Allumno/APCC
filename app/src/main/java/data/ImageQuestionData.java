package data;

import java.util.ArrayList;
import java.util.Collections;

public class ImageQuestionData {

	private String ID;
	private String headerID;
	private String author;

	private String type;

	protected boolean completed;
	private ArrayList<ImageQuestionTurnData> turns;

	/**
	 * Empty public constructor.
	 */
	public ImageQuestionData() {
		turns = new ArrayList<ImageQuestionTurnData>();
	}

	/**
	 * Indicates if the exercise has already been solved or not.
	 * @return
	 *      True if it has been successfully been solved.
	 *      False otherwise.
	 */
	public boolean isCompleted() {
		return completed;
	}

	/**
	 * Flag to indicate if the exercise has already been solved or not.
	 * @param completed
	 *      True if it has been successfully been solved.
	 *      False otherwise.
	 */

	/**
	 * Gets a list of all the turns contained in this exercise.
	 * @return
	 *      A list with all the information of all the turns in this exercise
	 *      (question, correct and incorrect options).
	 */
	public ArrayList<ImageQuestionTurnData> getTurns() {
		return turns;
	}

	/**
	 * Returns all the turns, already MIXED, available for the given theme/turn.
	 * @param level
	 *      Level from which the turns should be retrieved.
	 * @return
	 *      A string array with the given turns.
	 *      Null if the theme is not defined or the turns list is null.
	 */
	public ArrayList<ImageTextData> getTurns(int level) {
		if (level >= 0 && level < getMaxTurns()) {
			ImageQuestionTurnData turn = turns.get(level);

			ArrayList<ImageTextData> mixedOptions = turn.getAllOptions();
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
	public void setTurns(ImageQuestionTurnData turn) {
		this.turns.add(turn);
	}

	/**
	 * Sets all the turns.
	 * @param turns
	 *      A list with the information pertaining all the turns to be included in this exercise.
	 */
	public void setTurns(ArrayList<ImageQuestionTurnData> turns) {
		this.turns = turns;
	}

	/**
	 * Gets the correct option for the given turn.
	 * @return
	 *      Returns a String with the correct option.
	 *      Null if the given turn is off limits or there is no correct option defined.
	 */
	public ImageTextData getCorrect(int level) {
		if (level >= 0 && level < getMaxTurns()) {
			ImageQuestionTurnData turn = turns.get(level);
			return turn.getCorrect();
		}

		return null;
	}

	/**
	 * Gets the incorrect list of turns for the given turn.
	 * @return
	 *      Returns an Arraylist of Strings with the incorrect turns.
	 *      Null if the given turn is off limits or there is no incorrect turns defined.
	 */
	public ArrayList<ImageTextData> getIncorrect(int level) {
		if (level >= 0 && level < getMaxTurns()) {
			ImageQuestionTurnData turn = turns.get(level);
			return turn.getIncorrect();
		}

		return null;
	}

	public String getQuestion(int level) {
		if (level >= 0 && level < getMaxTurns()) {
			ImageQuestionTurnData turn = turns.get(level);
			return turn.getQuestion();
		}

		return null;
	}

	public String getImagePath(int level) {
		if (level >= 0 && level < getMaxTurns()) {
			ImageQuestionTurnData turn = turns.get(level);
			return turn.getImgPath();
		}

		return null;
	}

	/**
	 * Gets the total number of turns in this exercise.
	 * @return
	 *      The total number of turns.
	 */
	public long getMaxTurns() {
		return turns.size();
	}

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getHeaderID() {
		return headerID;
	}

	public void setHeaderID(String headerID) {
		this.headerID = headerID;
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
}
