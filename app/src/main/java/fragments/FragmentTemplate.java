package fragments;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Handler;
import android.view.Display;

import alpha.apcc.R;
import variables.Actions;
import variables.Layouts;
import items.GroupManager;

public abstract class FragmentTemplate extends Fragment {

	protected Handler mainHandler = null;
	protected static Layouts lay = null;


	/**
	 * Gets the items in this view to be swiped.
	 * @return
	 *      A group with the items to be swiped.
	 */
	public abstract GroupManager getViews();

	public Handler getMainHandler() {
		return mainHandler;
	}

	public abstract void setMainHandler(Handler mainHandler);

	public abstract void actionHandler(Actions action, int arg1, int arg2);

	public int getLayoutResource() {
		switch (lay) {
			case HEADER:
				return R.layout.fragment_header;
			case FILE_DIALOG:
				return R.layout.fragment_file_dialog;
			case LESSON:
				return R.layout.fragment_lesson;
			case LESSON_Y:
				return R.layout.fragment_lesson_y;
			case IMAGE_QUESTION:
				return R.layout.fragment_math_v2;
			case IMAGE_QUESTION_Y:
				return R.layout.fragment_math_v2_y;
			case QUESTION:
				return R.layout.fragment_question;
			case LANGUAGE:
				return R.layout.fragment_language;
			default:
				return -1;
		}
	}

	public Layouts getLay() {
		return lay;
	}

	public void setLay(Layouts lay) {
		this.lay = lay;
	}

	/**
	 * Scales down a given image (by 1/x of the screen height).
	 * @param image
	 *      The image to be resized.
	 * @return
	 *      The scaled down image.
	 */
	public Bitmap scaleDown(Bitmap image, int x) {
		if (image == null || x <= 0) {
			return null;
		}

		Display display = this.getActivity().getWindowManager().getDefaultDisplay();
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
}
