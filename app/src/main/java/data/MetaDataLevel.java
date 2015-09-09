package data;

import java.util.ArrayList;

public class MetaDataLevel {
	private String level;
	private ArrayList<MetaDataAssignment> assignments;

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public ArrayList<MetaDataAssignment> getAssignments() {
		return assignments;
	}

	public void setAssignments(ArrayList<MetaDataAssignment> assignments) {
		this.assignments = assignments;
	}

	public ArrayList<String> getAssignmentsStringList() {
		if (getAssignments() == null) {
			return null;
		}

		ArrayList<String> names = new ArrayList<String>();

		for (MetaDataAssignment assignment : assignments) {
			names.add(assignment.toString());
		}

		return names;
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
		if (level == null) {
			return "";
		}

		return level;
	}
}
