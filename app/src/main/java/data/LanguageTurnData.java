package data;

import java.util.ArrayList;

public class LanguageTurnData {
	private String correct;
	private ArrayList<String> incorrect;

	public LanguageTurnData() {
		incorrect = new ArrayList<String>();
	}

	public String getCorrect() {
		return correct;
	}

	public void setCorrect(String correct) {
		this.correct = correct;
	}

	public ArrayList<String> getIncorrect() {
		return incorrect;
	}

	public void setIncorrect(ArrayList<String> incorrect) {
		this.incorrect = incorrect;
	}

	public String getIncorrect(int index) {
		if (index < 0 && index >= this.incorrect.size()) {
			return null;
		}

		return this.incorrect.get(index);
	}

	public void addIncorrect(String incorrect) {
		this.incorrect.add(incorrect);
	}

	public void delIncorrect(String incorrect) {
		this.incorrect.remove(incorrect);
	}

	/**
	 * Correct option goes on the first space followed by the remaining incorrect options.
	 * @return
	 *      Ordered list of all options for this turn.
	 */
	public ArrayList<String> getAllOptions() {
		ArrayList<String> all = new ArrayList<String>();

		all.add(correct);
		all.addAll(incorrect);

		return all;
	}

	@Override
	public String toString() {
		return "\nCorrect: " + correct + "\nIncorrect: " + incorrect + "\n";
	}
}
