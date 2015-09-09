package constants;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import alpha.apcc.R;

public class ExercisePersistence {
	public Layouts layout = Layouts.UNDEFINED;
	public String path = "";
	public File file = null;
	public HashMap<String, Object> arguments = new HashMap<String, Object>();
	private boolean changed;

	public ExercisePersistence() {}

	public ExercisePersistence(Layouts layout) {
		this.layout = layout;
	}

	public Layouts getLayout() {
		return layout;
	}

	public void setLayout(Layouts layout) {
		this.layout = layout;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public HashMap<String, Object> getArguments() {
		return arguments;
	}

	public void setArguments(HashMap<String, Object> arguments) {
		this.arguments = arguments;
	}

	public Object getArgument(String key) {
		return this.arguments.get(key);
	}

	public void setArgument(String key, Object value) {
		this.arguments.put(key, value);
	}

	public void clearArgument(String key) {
		this.arguments.remove(key);
	}

	public void clearArguments() {
		this.arguments.clear();
	}

	public void reset() {
		this.layout = Layouts.UNDEFINED;
		this.path = null;
		clearArguments();
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	public boolean isChanged() {
		return changed;
	}
}
