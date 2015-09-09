package data;

import java.io.Serializable;
import java.util.ArrayList;

public class MetaDataAssignment implements Serializable {
	private String assignment;
	private String headerPath;
	private ArrayList<String> lessonsPath;
	private ArrayList<String> exercisesPath;

	public String getAssignment() {
		return assignment;
	}

	public void setAssignment(String assignment) {
		this.assignment = assignment;
	}

	public String getHeaderPath() {
		return headerPath;
	}

	public void setHeaderPath(String headerPath) {
		this.headerPath = headerPath;
	}

	public ArrayList<String> getLessonsPath() {
		return lessonsPath;
	}

	public void setLessonsPath(ArrayList<String> lessonsPath) {
		this.lessonsPath = lessonsPath;
	}

	public ArrayList<String> getExercisesPath() {
		return exercisesPath;
	}

	public void setExercisesPath(ArrayList<String> exercisesPath) {
		this.exercisesPath = exercisesPath;
	}

	/**
	 * Returns a string containing a concise, human-readable description of this
	 * object. Subclasses are encouraged to override this method and provide an
	 * implementation that takes into account the object's type and data. The
	 * default implementation is equivalent to the following expression:
	 * <pre>
	 *   getClass().getName() + '@' + Integer.toHexString(hashCode())</pre>
	 * <p>See <a href="{@docRoot}reference/java/lang/Object.html#writing_toString">Writing a useful
	 * {@code toString} method</a>
	 * if you intend implementing your own {@code toString} method.
	 *
	 * @return a printable representation of this object.
	 */
	@Override
	public String toString() {
		if (assignment == null) {
			return "";
		}

		return assignment;
	}
}
