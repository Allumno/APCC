package items;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;

import alpha.apcc.R;
import variables.Actions;


public class ImgBt extends OptBt {
	protected ImageButton button = null;                 //  View of the image button (default)

	/**
	 * Standard image button flyweight.
	 * @param button
	 *      View of the button
	 * @param handle
	 *      Handle to process instructions by other threads
	 */
	public ImgBt(ImageButton button, Handler handle) {
		super(button, handle);
		this.button = button;
	}

	/**
	 * Standard image button flyweight.
	 * @param button
	 *      View of the button
	 * @param handle
	 *      Handle to process instructions by other threads
	 * @param action
	 *      Flag of the action to be used by the handler
	 */
	public ImgBt(ImageButton button, Handler handle, Actions action) {
		super(button, handle, action);
		this.button = button;
	}

	/**
	 * Sets the image to appear in the button
	 * @param bitmap
	 *      Bitmap of the image to appear in the button
	 */
	public void setImage(Bitmap bitmap) {
		if (button != null) {
			button.setImageBitmap(bitmap);
		}
	}

	/**
	 * Gets the image to appear in the button
	 * @return
	 *      Bitmap of the image to appear in the button
	 */
	public Bitmap getImage() {
		if (button != null) {
			return button.getDrawingCache();
		}

		return null;
	}

	/**
	 * Makes the button visible or invisible
	 * @param change
	 *      True to make the button visible
	 *      False to make it invisible
	 */
	@Override
	public void setVisible(boolean change) {
		if (change) {
			button.setVisibility(View.VISIBLE);
		}
		else {
			button.setVisibility(View.GONE);
		}
		refresh();
	}

	/**
	 * Changes the background of the button.
	 * Also applies a filter to the button so the image also becomes tinted.
	 * @param sk
	 *      Flag to indicate to which button it will update.
	 */
	@Override
	public void changeBG(Button.SKIN sk) {
		int id;

		if(button != null) {
			switch (sk) {
				case FOCUSED:
					button.setColorFilter(Color.argb(0x64, 0x8F, 0xB0, 0xFF));
					id = R.drawable.focus_button;
					break;
				default:
					button.clearColorFilter();
					id = R.drawable.default_button;
					break;
			}

			button.setBackgroundResource(id);
		}

		refresh();
	}
}
