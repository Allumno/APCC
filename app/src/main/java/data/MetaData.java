package data;

import java.util.ArrayList;

public class MetaData {

	private String ID;
	private String type;
	private ArrayList<MetaDataCategory> categories;

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public ArrayList<MetaDataCategory> getCategories() {
		return categories;
	}

	public void setCategories(ArrayList<MetaDataCategory> categories) {
		this.categories = categories;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ArrayList<String> getCategoriesStringList() {
		if (getCategories() == null) {
			return null;
		}

		ArrayList<String> names = new ArrayList<String>();

		for (MetaDataCategory category : categories) {
			names.add(category.toString());
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
		if (ID == null) {
			return "";
		}

		return ID;
	}
}
