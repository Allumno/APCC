package variables;

public class Configurations {
	public final static Layouts INITIAL_SCREEN = Layouts.FILE_DIALOG;
	public final static String[] VALID_DATA_EXTENSIONS =
			{
					".json",
					".JSON"
			};
	public final static String[] VALID_RESOURCE_EXTENSIONS =
			{
					".jpg",
					".JPG",
					".png",
					".PNG"
			};


	public final static long START_DELAY = 200;

	public static final long TURN_WAIT_TIME = 500;
	public static boolean swipeToggle = false;

	public final static long MIN_TIME_STEP = 750;
	public final static long MAX_TIME_STEP = 3000;
	public final static long GAP_TIME_STEP = 250;
	public static long time_step = 1500;

	public static final String ASSIGNMENT_TAG = "ASSIGNMENT";
	public static final String PATH_TAG = "ASSIGNMENT";

	public final static String INIT_PATH = "APCC/";
	public final static String CATEGORY_PATH = "Categories/";
	public final static String LEVEL_PATH = "Level_";
	public final static String ASSIGNMENT_PATH = "Assignment_";
	public final static String HEADERS_PATH = "/Headers/";
	public final static String LESSONS_PATH = "/Lessons/";
	public final static String EXERCISES_PATH = "/Exercises/";
	public final static String RESOURCES_PATH = "/Resources/";

	public final static String LEVEL_PREFIX = "Nível ";
	public final static String ASSIGNMENT_PREFIX = "Ficha ";

	public final static String METADATA_NAME = "metadata.json";
	public final static String METADATA_PATH = INIT_PATH + "/" + METADATA_NAME;
	public static String current_path = INIT_PATH;

	public final static int MAX_DEPTH = 2;
	public static int current_depth = 0;
	public static int categories_index = 0;
	public static int levels_index = 0;

	public static final String PATH_REGEX = "([a-zA-Z]:)?(\\\\[a-zA-Z0-9_.-]+)+\\\\?";
}
