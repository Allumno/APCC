package items;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.Serializable;

import alpha.apcc.R;
import constants.Actions;
import constants.Configurations;


public class TextBt extends OptBt {
	protected Button button = null;                 //  View of the text button (default)

	/**
	 * Standard button flyweight.
	 * @param button
	 *      View of the button
	 * @param handle
	 *      Handle to process instructions by other threads
	 */
	public TextBt(Button button, Handler handle) {
		super(button, handle);
		this.button = button;
	}

	/**
	 * Standard button flyweight.
	 * @param button
	 *      View of the button
	 * @param handle
	 *      Handle to process instructions by other threads
	 * @param action
	 *      Flag of the action to be used by the handler
	 */
	public TextBt(Button button, Handler handle, Actions action) {
		super(button, handle, action);
		this.button = button;
	}

	/**
	 * Sets the text to appear in the button
	 * @param txt
	 *      String to appear in the button
	 */
	public void setText(String txt) {
		if (button != null) {
			button.setText(txt);
		}
	}

	/**
	 * Gets the current text set in the button
	 * @return
	 *      String representing the text in the button
	 */
	public CharSequence getText() {
		if (button != null) {
			return button.getText();
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
}
