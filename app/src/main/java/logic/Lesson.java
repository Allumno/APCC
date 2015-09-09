package logic;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import alpha.apcc.QuestionAct;
import alpha.apcc.R;
import constants.Actions;
import constants.Fonts;
import data.LessonData;
import files.LessonLoader;
import files.Reader;
import items.GroupManager;
import runner.LessonUnitStrat;

public class Lesson extends ScreenAbstract {
	private TextView title_view;
	private TextView theme_view;
	private TextView text_view;
	private ImageView image_view;
	private LessonData data;


	/**
	 * Setups the environment
	 * @param activity
	 *      Activity instance to allow UI access
	 */
	@Override
	public void setup(Activity activity) {
		act = activity;

		//  Data loading
		loadData();

		//  Setup handler responsible for UI events and interactions
		handlerSetup();

		//  Setups the views in the activity
		viewSetup();

		//  Swipe strategy setup
		strat = new LessonUnitStrat();
		super.swipeSetup();
	}

	/**
	 * Responsible to setup the handler behaviour and actions.
	 */
	private void handlerSetup() {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Actions action = (Actions)msg.obj;
				switch (action) {
					case SW_NEXT:
						//  Skips to the next step in the swipe.
						//  Swipe runner responsible for this.
						swipe.nextStep();
						break;
					case SW_SELECT:
						//  Selects the current item.
						//  Item listeners responsible for this.
						swipe.select();
						break;
					case BT_NEXT:
						changeActivity();
						break;
				}
			}
		};
	}

	/**
	 * Responsible for the setup of the views in the activity.
	 */
	private void viewSetup() {
		//  Buttons and item views setup
		items = new GroupManager(handler);
		TextView vi;

		//  Activity window is added to allow clicks in any part of the screen
		//  It's not to be added to the swipe list
		items.addBackgroundBt("bg", act.findViewById(R.id.activity_window));

		//  Items to be added to the swipe list
		vi = (TextView)act.findViewById(R.id.next_button);
		vi.setTypeface(Fonts.KOMIKA);
		items.addNormalBt("next", vi, Actions.BT_NEXT);


		//  Labels, titles, etc. setup
		title_view = (TextView) act.findViewById(R.id.titleView);
		theme_view = (TextView) act.findViewById(R.id.themeView);
		image_view = (ImageView) act.findViewById(R.id.imageView);
		text_view = (TextView) act.findViewById(R.id.textView);

		//  Change the font and size used by the views
		title_view.setTypeface(Fonts.CARL);
		theme_view.setTypeface(Fonts.KOMIKA);
		text_view.setTypeface(Fonts.KOMIKA);

		//  Insert the initial data to the views
//		title_view.setText(data.getTitle());
//		theme_view.setText(data.getAssignment());
		text_view.setText(data.getText());

		File file = Environment.getExternalStorageDirectory();
		file = new File(file, data.getImgPath());
		Bitmap bit = BitmapFactory.decodeFile(file.getAbsolutePath());
		bit = scaleDown(bit);
		image_view.setImageBitmap(bit);
	}

	/**
	 * Responsible for loading the necessary data.
	 */
	private void loadData() {
		Reader reader = new Reader("lesson_test.json");
		reader.open();
		LessonLoader loader = new LessonLoader(reader.getReader());

		data = loader.load();
		reader.close();
	}

	/**
	 * Changes to the next activity.
	 */
	private void changeActivity() {
		Intent intent = new Intent(act, QuestionAct.class);

		if (getTurn() > -1) {
			intent.putExtra("TURN", turn);
		}

		act.startActivity(intent);
	}

	/**
	 * Scales down a given image (to 1/4 the screen height).
	 * @param image
	 *      The image to be resized.
	 * @return
	 *      The scaled down image.
	 */
	private Bitmap scaleDown(Bitmap image) {
		Display display = act.getWindowManager().getDefaultDisplay();
		Point point = new Point();
		display.getSize(point);

		float maxImageSize = point.y/4;

		float ratio = Math.min(
				(float) maxImageSize / image.getWidth(),
				(float) maxImageSize / image.getHeight());
		int width = Math.round((float) ratio * image.getWidth());
		int height = Math.round((float) ratio * image.getHeight());

		Bitmap newBitmap = Bitmap.createScaledBitmap(image, width,
				height, true);
		return newBitmap;
	}
}
