package items;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.Serializable;

import alpha.apcc.R;
import variables.Actions;
import variables.Configurations;


public class NormalButton implements ItemInterface, Serializable {
	protected int id = -1;                          //  ID of the stored button
	protected Button button = null;                 //  View of the text button (default)
	protected ImageButton imageButton = null;       //  View of the image button
	protected Handler handler = null;               //  Handle to allow instruction sending to other threads
	protected boolean focused = false;              //  Signals if the button is focused or not
	protected boolean focusable = false;            //  Signals if the button can be focused or not
	protected enum BT_TYPE {TEXT, IMAGE}                    //  Flags the type of the button that is being used (text by default)
	protected BT_TYPE type;                         //  Flags the type of the button that is being used (text by default)
	protected enum SKIN {UNFOCUSED, FOCUSED}                       //  Flags the skin to use
	private Actions action = Actions.BT_DEFAULT;    //  Flag to be used by the handler on selection

	/**
	 * Standard button flyweight.
	 * @param button
	 *      View of the button
	 * @param handle
	 *      Handle to process instructions by other threads
	 */
	public NormalButton(Button button, Handler handle) {
		setType(BT_TYPE.TEXT);
		setButton(button);
		setHandler(handle);
		setAction(Actions.BT_DEFAULT);
		setFocused(false);
		setFocusable(true);
		setListener();
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
	public NormalButton(Button button, Handler handle, Actions action) {
		setType(BT_TYPE.TEXT);
		setButton(button);
		setHandler(handle);
		setAction(action);
		setFocused(false);
		setFocusable(true);
		setListener();
	}

	/**
	 * Standard button flyweight.
	 * @param imageBt
	 *      View of the button
	 * @param handle
	 *      Handle to process instructions by other threads
	 */
	public NormalButton(ImageButton imageBt, Handler handle) {
		setType(BT_TYPE.IMAGE);
		setImageButton(imageBt);
		setHandler(handle);
		setAction(Actions.BT_DEFAULT);
		setFocused(false);
		setFocusable(true);
		setListener();
	}

	/**
	 * Standard button flyweight.
	 * @param imageBt
	 *      View of the button
	 * @param handle
	 *      Handle to process instructions by other threads
	 * @param action
	 *      Flag of the action to be used by the handler
	 */
	public NormalButton(ImageButton imageBt, Handler handle, Actions action) {
		setType(BT_TYPE.IMAGE);
		setImageButton(imageBt);
		setHandler(handle);
		setAction(action);
		setFocused(false);
		setFocusable(true);
		setListener();
	}

	/**
	 * OnClick listener setup (default action).
	 */
	public void setListener() {
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (handler != null) {
					if (Configurations.swipeToggle) {
						Message msg = handler.obtainMessage(0, Actions.SW_SELECT);
						handler.sendMessage(msg);
					} else {
						select();
					}
				}
			}
		});
	}

	/**
	 * Requests and handles operations when the button is focused.
	 */
	@Override
	public void focus(boolean change) {
		if(button == null || !isFocusable()) {
			return;
		}

		setFocused(change);
	}

	/**
	 * Handles operations when the button is selected
	 * @return
	 *      Return True if operation was successful
	 *      Return False otherwise (ex: when view is null)
	 */
	@Override
	public boolean select() {
		if(button == null) {
			return false;
		}

		//  Sends instruction to change turn
		Message msg = handler.obtainMessage(0, getAction());
		handler.sendMessage(msg);

		return true;
	}

	/**
	 * Changes the handler of this button
	 * @param handle
	 *      New handler to allow communication
	 */
	@Override
	public void setHandler(Handler handle) {
		handler = handle;
	}

	/**
	 * Gets the current handler associated with this button
	 * @return
	 *      The current handler
	 */
	@Override
	public Handler getHandler() {
		return handler;
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
			button.setVisibility(View.INVISIBLE);
		}
		refresh();
	}

	/**
	 * Gets the current state of visibility
	 * @return
	 *      True if the button is visible
	 *      False if not visible
	 */
	@Override
	public boolean isVisible() {
		if (button.getVisibility() == View.VISIBLE) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Makes the button focusable or not focusable
	 * @param change
	 *      True to make the button focusable
	 *      False to make it not focusable
	 */
	@Override
	public void setFocusable(boolean change) {
		focusable = change;
	}

	/**
	 * Gets the current state of focusability
	 * @return
	 *      True if the button is focusable
	 *      False if not focusable
	 */
	@Override
	public boolean isFocusable() {
		return focusable;
	}

	/**
	 * Changes the accessibility (visibility + focusability).
	 * Resets focused state to false.
	 * @param change
	 *      True to make it visible AND focusable
	 *      False to make it invisible AND not focusable
	 */
	@Override
	public void setAccessible(boolean change) {
		setFocused(false);
		setFocusable(change);
		setVisible(change);
	}

	/**
	 * Gets the current state of accessibility
	 * @return
	 *      True if visible AND focusable
	 *      False if invisible AND not focusable
	 */
	@Override
	public boolean isAccessible() {
		return (isFocusable() && isVisible());
	}

	/**
	 * Resets the state of the button.
	 * Makes it visible, focusable and resets the background.
	 */
	@Override
	public void reset() {
		if ( button == null) {
			return;
		}
		setAccessible(true);
	}

	/**
	 * Flags the button as being focused or not.
	 * @param focused
	 *      True if it is now focused.
	 *      False otherwise.
	 */
	public void setFocused(boolean focused) {
		this.focused = focused;

		SKIN sk;

		if (!focused) {
			sk = SKIN.UNFOCUSED;
		}
		else {
			sk = SKIN.FOCUSED;
		}

		changeBG(sk);
	}

	/**
	 * Checks if this button is currently focused.
	 * @return
	 *      True if it is focused.
	 *      False otherwise.
	 */
	public boolean isFocused() {
		return focused;
	}

	/**
	 * Changes the background of the button.
	 * @param sk
	 *      Flag to indicate to which button it will update.
	 */
	public void changeBG(SKIN sk) {
		int id;

		switch(sk) {
			case FOCUSED:
				id = R.drawable.focus_button;
				break;
			default:
				id = R.drawable.default_button;
				break;
		}

		button.setBackgroundResource(id);
		refresh();
	}

	/**
	 * Sets the text to appear in the button
	 * @param txt
	 *      String to appear in the button
	 */
	public void setText(String txt) {
		if (this.getType() == BT_TYPE.TEXT) {
			button.setText(txt);
		}
	}

	/**
	 * Gets the current text set in the button
	 * @return
	 *      String representing the text in the button
	 */
	public CharSequence getText() {
		if (this.getType() == BT_TYPE.TEXT) {
			return button.getText();
		}

		return null;
	}

	/**
	 * Sets the image to appear in the button
	 * @param bitmap
	 *      Bitmap of the image to appear in the button
	 */
	public void setImage(Bitmap bitmap) {
		if (this.getType() == BT_TYPE.IMAGE) {
			imageButton.setImageBitmap(bitmap);
		}
	}

	/**
	 * Gets the image to appear in the button
	 * @return
	 *      Bitmap of the image to appear in the button
	 */
	public Bitmap getImage() {
		if (this.getType() == BT_TYPE.IMAGE) {
			return imageButton.getDrawingCache();
		}

		return null;
	}

	/**
	 * Changes the text button associated with this instance (option setting remains the same)
	 * @param button
	 *      Button to be associated with this instance
	 */
	public void setButton(Button button) {
		this.button = button;
		this.id = this.button.getId();
		setType(BT_TYPE.TEXT);

		this.imageButton = null;
	}

	/**
	 * Gets the button in this instance
	 * @return
	 *      The button associated with this instance or null if not set.
	 */
	public Button getButton() {
		return button;
	}

	/**
	 * Changes the image button associated with this instance (option setting remains the same)
	 * @param imageButton
	 *      Button to be associated with this instance
	 */
	public void setImageButton(ImageButton imageButton) {
		this.imageButton = imageButton;
		this.id = this.button.getId();
		setType(BT_TYPE.IMAGE);

		this.button = null;
	}

	/**
	 * Gets the image button in this instance
	 * @return
	 *      The button associated with this instance or null if not set.
	 */
	public ImageButton getImageButton() {
		return imageButton;
	}

	/**
	 * Gets the type of this button.
	 * @return
	 *      BT_TYPE.TEXT if it is a text button.
	 *      BT_TYPE.IMAGE if it is an image button.
	 */
	public BT_TYPE getType() {
		return type;
	}

	/**
	 * Sets the type of this button.
	 * @param type
	 *      BT_TYPE.TEXT if it is a text button.
	 *      BT_TYPE.IMAGE if it is an image button.
	 */
	private void setType(BT_TYPE type) {
		this.type = type;
	}

	/**
	 *  Gets the flag of the action to be used by the handler.
	 * @return
	 *       Flag of the action to be used by the handler.
	 */
	public Actions getAction() {
		return action;
	}

	/**
	 *  Sets the flag of the action to be used by the handler.
	 * @param action
	 *      Action flag to be used by the handler.
	 */
	public void setAction(Actions action) {
		this.action = action;
	}

	/**
	 * Refresh the state of the button
	 */
	public void refresh() {
		button.refreshDrawableState();
	}
}
