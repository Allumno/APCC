package data;

import java.util.ArrayList;

public class MetaDataCategory {
	private String category;
	private ArrayList<MetaDataLevel> levels;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public ArrayList<MetaDataLevel> getLevels() {
		return levels;
	}

	public void setLevels(ArrayList<MetaDataLevel> levels) {
		this.levels = levels;
	}

	public ArrayList<String> getLevelsStringList() {
		if (getLevels() == null) {
			return null;
		}

		ArrayList<String> names = new ArrayList<String>();

		for (MetaDataLevel level : levels) {
			names.add(level.toString());
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
		if (category == null) {
			return "";
		}

		return category;
	}
}
