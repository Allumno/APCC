package logic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.File;

import alpha.apcc.R;
import variables.Actions;
import variables.Directions;
import variables.ExerciseResources;
import variables.Fonts;
import variables.Layouts;
import data.LessonData;
import files.LessonLoader;
import files.Reader;
import fragments.FragmentTemplate;
import items.GroupManager;

public class LessonFragmentLogic {
	private File file;
	private TextView text_view;
	private ImageView image_view;
	private ScrollView scroll_view;
	private Button up_button;
	private Button down_button;
	private Bitmap bit;
	private LessonData data;
	private FragmentTemplate frag;
	private View view;
	private String path;
	private Handler mainHandler;
	private int ratio;

	/**
	 * Public constructor.
	 * @param frag
	 *      Fragment instance to allow UI access.
	 */
	public LessonFragmentLogic(FragmentTemplate frag) {
		this.frag = frag;
		this.path = "lesson_test.json";
	}

	/**
	 * Public constructor.
	 * @param frag
	 *      Fragment instance to allow UI access.
	 * @param path
	 *      Path string where data is stored.
	 */
	public LessonFragmentLogic(FragmentTemplate frag, String path) {
		this.frag = frag;
		this.path = path;
	}

		/**
	 * Public constructor.
	 * @param frag
	 *      Fragment instance to allow UI access.
	 * @param file
	 *      File where the data is stored.
	 */
	public LessonFragmentLogic(FragmentTemplate frag, File file) {
		this.frag = frag;
		this.file = file;
		loadData();
	}

	/**
	 * Setups the environment.
	 */
	public void setup() {
		this.view = frag.getView();
		this.ratio = 1;

//		//  Data loading
//		loadData();

		//  Setups the views in the activity
		viewSetup();
	}

	/**
	 * Responsible for preparing the necessary data.
	 */
	public void loadData() {
		//  If the file is not defined returns immediately.
		if (file == null) {
			return;
		}

		//  Loads miscelaneous data
		Reader reader = new Reader(file);
		reader.open();
		LessonLoader loader = new LessonLoader(reader.getReader());

		data = loader.load();
		reader.close();

		//  Loads and scales down the image of the lesson
		if (data.getImgPath() != null) {
			File file = ExerciseResources.getResourceFiles().get(data.getImgPath());
			bit = BitmapFactory.decodeFile(file.getAbsolutePath());

			if (bit.getHeight() > bit.getWidth()) {
				ExerciseResources.getList().getCurrent().setLayout(Layouts.LESSON_Y);
				ExerciseResources.getList().getCurrent().setChanged(true);
			}
		}
	}

	/**
	 * Responsible for the setup of the views in the activity.
	 */
	private void viewSetup() {
		//  Labels, titles, etc. setup
		image_view  =   (ImageView) view.findViewById(R.id.imageView);
		text_view   =   (TextView) view.findViewById(R.id.textView);
		scroll_view =   (ScrollView) view.findViewById(R.id.scrollView);
		up_button   =   (Button) view.findViewById(R.id.up_button);
		down_button =   (Button) view.findViewById(R.id.down_button);

		//  Set the image
		if (bit != null) {
			bit = scaleDown(bit, ratio);
			image_view.setImageBitmap(bit);
		}

		//  Change the font and size used by the views
		text_view.setTypeface(Fonts.KOMIKA);
		//  Insert the initial data to the views
		text_view.setText(data.getText());
	}

	/**
	 * Acts according to the given action.
	 * @param action
	 *      The action to be executed.
	 */
	public void actionHandler(Actions action, int arg1, int arg2) {
		switch (action) {
			case BT_UP:
				scroll(Directions.UP);
				break;
			case BT_DOWN:
				scroll(Directions.DOWN);
				break;
		}
	}

	/**
	 * Scales down a given image (by 1/x of the screen height).
	 * @param image
	 *      The image to be resized.
	 * @return
	 *      The scaled down image.
	 */
	private Bitmap scaleDown(Bitmap image, int x) {
		Display display = frag.getActivity().getWindowManager().getDefaultDisplay();
		Point point = new Point();
		display.getSize(point);

		float maxImageSize = point.y/x;

		float ratio = Math.min(
				maxImageSize / image.getWidth(),
				maxImageSize / image.getHeight());
		int width = Math.round(ratio * image.getWidth());
		int height = Math.round(ratio * image.getHeight());

		Bitmap newBitmap = Bitmap.createScaledBitmap(image, width,
				height, true);
		return newBitmap;
	}

	/**
	 * Scrolls the view in the given direction.
	 * @param dir
	 *      Direction in which the view is to be scrolled.
	 */
	public void scroll(Directions dir) {
		int y = scroll_view.getHeight()-100;

		switch (dir) {
			case UP:
				scroll_view.smoothScrollBy(0, -y);
				break;
			case DOWN:
				scroll_view.smoothScrollBy(0, y);
				break;
		}

		scroll_view.computeScroll();
		scroll_view.refreshDrawableState();
	}

	/**
	 * Gets the items in this view to be swiped.
	 * @return
	 *      A group with the items to be swiped.
	 */
	public GroupManager getViews() {
		GroupManager group = new GroupManager(mainHandler);

		group.addDirectionBt("up", up_button, Actions.BT_UP, Directions.UP);
		group.addDirectionBt("down", down_button, Actions.BT_DOWN, Directions.DOWN);

		return group;
	}

	public void setMainHandler(Handler mainHandler) {
		this.mainHandler = mainHandler;
	}

	public Handler getMainHandler() {
		return mainHandler;
	}
}
