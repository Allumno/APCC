package data;


import java.util.ArrayList;

public class ImageQuestionTurnData {
	private String imgPath;
	private String question;
	private ImageTextData correct;
	private ArrayList<ImageTextData> incorrect;

	public ImageQuestionTurnData() {
		incorrect = new ArrayList<ImageTextData>();
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public ImageTextData getCorrect() {
		return correct;
	}

	public void setCorrect(ImageTextData correct) {
		this.correct = correct;
	}

	public ArrayList<ImageTextData> getIncorrect() {
		return incorrect;
	}

	public void setIncorrect(ArrayList<ImageTextData> incorrect) {
		this.incorrect = incorrect;
	}

	public ImageTextData getIncorrect(int index) {
		if (index < 0 && index >= this.incorrect.size()) {
			return null;
		}

		return this.incorrect.get(index);
	}

	public void addIncorrect(ImageTextData incorrect) {
		this.incorrect.add(incorrect);
	}

	public void delIncorrect(ImageTextData incorrect) {
		this.incorrect.remove(incorrect);
	}

	/**
	 * Correct option goes on the first space followed by the remaining incorrect options.
	 * @return
	 *      Ordered list of all options for this turn.
	 */
	public ArrayList<ImageTextData> getAllOptions() {
		ArrayList<ImageTextData> all = new ArrayList<ImageTextData>();

		all.add(correct);
		all.addAll(incorrect);

		return all;
	}
}
