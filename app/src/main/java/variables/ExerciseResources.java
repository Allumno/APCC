package variables;

import java.io.File;
import java.util.HashMap;

import data.FileLayout;
import data.GenericData;
import data.MetaDataAssignment;
import files.FileValidator;
import files.GenericDataLoader;
import files.Reader;
import logic.ExerciseFlow;

public class ExerciseResources {
	public static HashMap<String, FileLayout> dataFiles = new HashMap<String, FileLayout>();
	public static HashMap<String, File> resourceFiles = new HashMap<String, File>();

	private static ExerciseResources instance;
	private static ExerciseFlow list;
	private MetaDataAssignment assignment;
	private String path;

	public static ExerciseResources getInstance() {
		if (instance == null) {
			instance = new ExerciseResources();
			list = new ExerciseFlow();
		}

		return instance;
	}

	public static HashMap<String, FileLayout> getDataFiles() {
		return dataFiles;
	}

	public static void setDataFiles(HashMap<String, FileLayout> dataFiles) {
		ExerciseResources.dataFiles = dataFiles;
	}

	public static HashMap<String, File> getResourceFiles() {
		return resourceFiles;
	}

	public static void setResourceFiles(HashMap<String, File> resourceFiles) {
		ExerciseResources.resourceFiles = resourceFiles;
	}

	public MetaDataAssignment getAssignment() {
		return assignment;
	}

	public void setAssignment(MetaDataAssignment assignment) {
		this.assignment = assignment;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public static ExerciseFlow getList() {
		return list;
	}

	public static void setList(ExerciseFlow list) {
		ExerciseResources.list = list;
	}

	public static boolean isNull() {
		return (instance == null);
	}

	public boolean setup(){
		if (assignment == null) {
			return false;
		}

		listIndex(null);

		list.clear();

		String str;
		ExercisePersistence header = new ExercisePersistence();
		header.setLayout(Layouts.HEADER);
		path = Configurations.INIT_PATH + path + "/" + assignment.getAssignment();
		str = path + Configurations.HEADERS_PATH + assignment.getHeaderPath();
		header.setPath(str);
		list.setHeader(header);

		ExercisePersistence content;

		for (String lessonPath : assignment.getLessonsPath()) {
			str = path + Configurations.LESSONS_PATH + lessonPath;

			if (dataFiles.get(str) != null) {
				content = new ExercisePersistence();
				content.setLayout(Layouts.LESSON);
				content.setFile(dataFiles.get(str).getFile());
				content.setPath(str);

				list.getLessons().add(content);
			}
		}

		for (String exPath : assignment.getExercisesPath()) {
			str = path + Configurations.EXERCISES_PATH + exPath;

			if (dataFiles.get(str) != null) {
				content = new ExercisePersistence();
				content.setLayout(dataFiles.get(str).getLayout());
				content.setFile(dataFiles.get(str).getFile());
				content.setPath(str);

				list.getExercises().add(content);
			}
		}

		return true;
	}

	private static void listIndex(File root) {
		if (root == null) {
			root = FileValidator.getFile(Configurations.INIT_PATH);
		}

		for (File file : root.listFiles()) {
			if (file == null) {
				return;
			}

			if (file.isDirectory()) {
				listIndex(file);
			}
			else if (file.isFile()){
				String name = file.getName().toLowerCase();

				//  If the file are resources the name will be used as it's ID
				for (String ext : Configurations.VALID_RESOURCE_EXTENSIONS) {
					if (name.endsWith(ext)) {
						resourceFiles.put(name, file);
						continue;
					}
				}

				//  If it is a JSON the ID will be the one contained in it
				for (String ext : Configurations.VALID_DATA_EXTENSIONS) {
					if (name.endsWith(ext)) {
						Reader reader = new Reader(file);
						reader.open();
						GenericData data = new GenericDataLoader(reader.getReader()).load();
						data.setPath(file.getPath());
						reader.close();

						if (data != null) {
							dataFiles.put(data.getPath().substring(
											data.getPath().
													lastIndexOf(Configurations.INIT_PATH)),
									new FileLayout(data.getID(), file, getType(data.getType())));
						}
						continue;
					}
				}
			}
		}
	}

	public static Layouts getType(String type) {
		if (type.equals("metadata")) {
			return Layouts.FILE_DIALOG;
		}
		else if (type.equals("header")) {
			return Layouts.HEADER;
		}
		else if (type.equals("lesson")) {
			return Layouts.LESSON;
		}
		else if (type.equals("language")) {
			return Layouts.LANGUAGE;
		}
		else if (type.equals("question_text")) {
			return Layouts.QUESTION;
		}
		else if (type.equals("question_text_image")) {
			return Layouts.IMAGE_QUESTION;
		}
		else {
			return Layouts.UNDEFINED;
		}
	}
}
