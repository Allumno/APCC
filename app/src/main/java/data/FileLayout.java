package data;

import java.io.File;

import variables.Layouts;

public class FileLayout {

	public String ID;
	public File file;
	public Layouts layout;

	public FileLayout(String ID, File file, Layouts layout) {
		this.ID = ID;
		this.file = file;
		this.layout = layout;
	}

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Layouts getLayout() {
		return layout;
	}

	public void setLayout(Layouts layout) {
		this.layout = layout;
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
		return ID + " [" + layout + "]: " + file.toString();
	}
}
