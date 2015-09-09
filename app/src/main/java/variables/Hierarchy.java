package variables;

import java.util.ArrayList;

import data.MetaData;
import data.MetaDataAssignment;
import data.MetaDataCategory;
import data.MetaDataLevel;

public class Hierarchy {
	private final static Hierarchy INSTANCE = new Hierarchy();

	private MetaData metadata = null;
	private MetaDataCategory category = null;
	private MetaDataLevel level = null;
	private ArrayList<String> currentList = null;

	public final int MIN_DEPTH = 0;
	public final int MAX_DEPTH = 2;
	public final int INCREMENT = 1;
	private int depth = 0;
	private String path = Configurations.CATEGORY_PATH;

	public static Hierarchy getInstance() {
		return INSTANCE;
	}

	public MetaData getMetadata() {
		return metadata;
	}

	public void setMetadata(MetaData metadata) {
		this.metadata = metadata;
	}

	public ArrayList<String> getCurrentList() {
		return currentList;
	}

	private void setCurrentList(ArrayList<String> currentList) {
		this.currentList = currentList;
	}

	public String getPath() {
		return path;
	}

	private void setPath(String path) {
		this.path = path;
	}

	public int getDepth() {
		return depth;
	}

	private void setDepth(int depth) {
		this.depth = depth;
	}

	private MetaDataCategory getCategory() {
		return category;
	}

	private void setCategory(MetaDataCategory category) {
		this.category = category;
	}

	public MetaDataLevel getLevel() {
		return level;
	}

	private void setLevel(MetaDataLevel level) {
		this.level = level;
	}

	public void incrementDepth(int selection) {
		depth = validateDepth(depth + INCREMENT);

		if (depth <= MIN_DEPTH) {
			setCurrentList(metadata.getCategoriesStringList());
			setPath(Configurations.CATEGORY_PATH);
		}
		else if (depth == 1 &&
				metadata != null &&
				metadata.getCategories() != null &&
				selection < metadata.getCategories().size()) {
			category = metadata.getCategories().get(selection);
			setCurrentList(category.getLevelsStringList());
			setPath((path + category.toString()));
		}
		else if (depth >= MAX_DEPTH &&
				category != null &&
				category.getLevels() != null &&
				selection < category.getLevels().size()) {
			level = category.getLevels().get(selection);
			setCurrentList(level.getAssignmentsStringList());
			setPath((path + "/" + level.toString()));
		}
	}

	public void decrementDepth() {
		depth = validateDepth(depth - INCREMENT);

		if (depth <= MIN_DEPTH) {
			setCurrentList(metadata.getCategoriesStringList());
			setPath(Configurations.CATEGORY_PATH);
		}
		else if (depth == 1 &&
				category != null) {
			setCurrentList(category.getLevelsStringList());
			setPath(path.substring(0, path.lastIndexOf('/')));
		}
		else if (depth >= MAX_DEPTH &&
				level != null) {
			setCurrentList(level.getAssignmentsStringList());
			setPath(path.substring(0, path.lastIndexOf('/')));
		}
	}

	public MetaDataAssignment getAssignment(int selection) {
		if (depth == MAX_DEPTH &&
				level != null &&
				level.getAssignments() != null) {
			return level.getAssignments().get(selection);
		}

		return null;
	}

	private int validateDepth(int depth) {
		if (depth < MIN_DEPTH ) {
			return MIN_DEPTH;
		}
		else if (depth > MAX_DEPTH) {
			return MAX_DEPTH;
		}
		else {
			return depth;
		}
	}

	public void reset() {
		setDepth(MIN_DEPTH);
		setPath(Configurations.CATEGORY_PATH);
		setCategory(null);
		setLevel(null);
		setCurrentList(metadata.getCategoriesStringList());
	}
}
