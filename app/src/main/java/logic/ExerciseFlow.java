package logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import constants.ExercisePersistence;
import constants.Layouts;

public class ExerciseFlow {
	private boolean set = false;
	private ExercisePersistence header = null;
	private ExercisePersistence file_dialog = new ExercisePersistence(Layouts.FILE_DIALOG);
	private ArrayList<ExercisePersistence> lessons =  new ArrayList<ExercisePersistence>();
	private ArrayList<ExercisePersistence> exercises =  new ArrayList<ExercisePersistence>();
	private ExercisePersistence current = null;
	private ExercisePersistence previous = null;
	private boolean lessonFocus = true;
	private int indexLesson = 0;
	private int indexExercise = 0;

	public boolean isSet() {
		if (lessons.size() > 0 || exercises.size() > 0) {
			setSet(true);
		}
		else {
			setSet(false);
		}

		return set;
	}

	private void setSet(boolean set) {
		this.set = set;
	}

	public ExercisePersistence getHeader() {
		return header;
	}

	public void setHeader(ExercisePersistence header) {
		this.header = header;
	}

	public ArrayList<ExercisePersistence> getLessons() {
		return lessons;
	}

	public void setLessons(ArrayList<ExercisePersistence> lessons) {
		this.lessons = lessons;
	}

	public ArrayList<ExercisePersistence> getExercises() {
		return exercises;
	}

	public void setExercises(ArrayList<ExercisePersistence> exercises) {
		this.exercises = exercises;
	}

	public ExercisePersistence getCurrent() {
		if (current == null) {
			if (lessons.size() > 0) {
				current = lessons.get(indexLesson);
			}
			else if (exercises.size() > 0) {
				current = lessons.get(indexExercise);
			}
		}

		return current;
	}

	public void setCurrent(ExercisePersistence current) {
		this.current = current;
	}

	public ExercisePersistence getPrevious() {
		return previous;
	}

	public void setPrevious(ExercisePersistence previous) {
		this.previous = previous;
	}

	public boolean isLessonFocus() {
		return lessonFocus;
	}

	public void setLessonFocus(boolean lessonFocus) {
		this.lessonFocus = lessonFocus;
	}

	public void swapFocus() {
		this.lessonFocus = !this.lessonFocus;
	}

	public int getIndexLesson() {
		return indexLesson;
	}

	public void setIndexLesson(int indexLesson) {
		this.indexLesson = indexLesson;
	}

	public void resetIndexLesson() {
		this.indexLesson = 0;
	}

	public void incrementLesson() {
		this.indexLesson++;
	}

	public void decrementLesson() {
		this.indexLesson--;
	}

	public int getIndexExercise() {
		return indexExercise;
	}

	public void setIndexExercise(int indexExercise) {
		this.indexExercise = indexExercise;
	}

	public void resetIndexExercise() {
		this.indexExercise = 0;
	}

	public void incrementExercise() {
		this.indexExercise++;
	}

	public void decrementExercise() {
		this.indexExercise--;
	}

	public ExercisePersistence next() {
		if (lessonFocus) {
			incrementLesson();

			if (indexLesson >= lessons.size()) {
				resetIndexLesson();
				swapFocus();
			}
		}
		else {
			incrementExercise();

			if (indexExercise >= exercises.size()) {
				resetIndexExercise();
				swapFocus();
			}
		}

		if (lessonFocus) {
			current = lessons.get(indexLesson);
		}
		else {
			current = exercises.get(indexExercise);
		}

		return current;
	}

	public ExercisePersistence back() {
		if (lessonFocus) {
			decrementLesson();
		}
		else {
			swapFocus();
		}

		if (indexLesson >= 0) {
			current = lessons.get(indexLesson);
		}
		else {
			current = file_dialog;
		}

		return current;
	}

	public void clear() {
		header = null;
		lessons.clear();
		exercises.clear();
		indexLesson = 0;
		indexExercise = 0;
		lessonFocus = true;
		current = null;
	}

	public boolean atTheEnd() {
		if (lessonFocus) {
			if (exercises.size() > 0) {
				return false;
			}
			else {
				return (indexLesson > lessons.size());
			}
		}
		else {
			return (indexExercise >= exercises.size());
		}
	}
}
