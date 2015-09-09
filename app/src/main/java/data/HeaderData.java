package data;

public class HeaderData {

	protected String ID;
	protected String type;
	protected String category;
	protected String theme;
	protected boolean completed;


	/**
	 * Empty public constructor.
	 */
	public HeaderData() {
		this.completed = false;
	}

	/**
	 * Public constructor.
	 * @param category
	 *      String with the category of the exercise.
	 * @param level
	 *      String with the theme of difficulty of the exercise.
	 */
	public HeaderData(String category, String level) {
		this.category = category;
		this.theme = level;
		this.completed = false;
	}

	/**
	 * Retrieves the category of the exercise.
	 * @return
	 *      A string with the category of the exercise.
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Sets the category of the exercise.
	 * @return
	 *      A string with the category of the exercise.
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * Retrieves the theme of the exercise.
	 * @return
	 *      A string with the theme of the exercise.
	 */
	public String getTheme() {
		return theme;
	}

	/**
	 * Sets the theme of the exercise.
	 * @return
	 *      A string with the theme of the exercise.
	 */
	public void setTheme(String theme) {
		this.theme = theme;
	}

	/**
	 * Indicates if the exercise has already been solved or not.
	 * @return
	 *      True if it has been successfully been solved.
	 *      False otherwise.
	 */
	public boolean getCompleted() {
		return completed;
	}

	/**
	 * Flag to indicate if the exercise has already been solved or not.
	 * @param completed
	 *      True if it has been successfully been solved.
	 *      False otherwise.
	 */
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
