package items;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;

import constants.Actions;
import constants.MediaType;

public class MixBt implements ItemInterface {
	protected String id;
	protected TextBt textButton;
	protected ImgBt imageButton;
	protected MediaType type;                             //  Flags the type of the button that is being used (text by default)
	protected Handler mainHandler;

	/**
	 * Public constructor of a mixed element view (containing a TEXT button and an IMAGE button).
	 * Only one of the buttons can be accessible at a given time.
	 * @param id
	 *      String ID of the view that contains the buttons.
	 * @param txt
	 *      The view of the text button.
	 * @param img
	 *      The view of the image button.
	 * @param handler
	 *      Handler responsible to interact with the UI thread.
	 * @param action
	 *      Action flag with the action of the button
	 */
	public MixBt(String id, Button txt, ImageButton img, Handler handler, Actions action) {
		this.id = id;
		this.mainHandler = handler;
		this.textButton = new TextBt(txt, handler, action);
		this.imageButton = new ImgBt(img, handler, action);
		this.type = MediaType.UNDEFINED;
	}

	/**
	 * Returns the string ID of the view.
	 * @return
	 *      String that represents this view.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the string ID of the view.
	 * @param id
	 *      String that represents this view.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns the instance of TextBt contained in this view.
	 * @return
	 *      An instance of TextBt.
	 */
	public TextBt getTextButton() {
		return textButton;
	}

	/**
	 * Sets the instance of the TextBt in this view.
	 * Text Button becomes accessible and Image Button becomes inaccessible.
	 * @param textButton
	 *       An instance of TextBt.
	 */
	public void setTextButton(TextBt textButton) {
		this.textButton = textButton;
		setType(MediaType.TEXT);
	}

	/**
	 * Returns the instance of ImgBt contained in this view.
	 * @return
	 *      An instance of ImgBt.
	 */
	public ImgBt getImageButton() {
		return imageButton;
	}

	/**
	 * Sets the instance of the ImgBt in this view.
	 * Image Button becomes accessible and Text Button becomes inaccessible.
	 * @param imageButton
	 *       An instance of ImgBt.
	 */
	public void setImageButton(ImgBt imageButton) {
		this.imageButton = imageButton;
		setType(MediaType.IMAGE);
	}

	/**
	 * Returns which type of button is currently accessible.
	 * @return
	 *      MediaType.TEXT if it is the text button.
	 *      MediaType.IMAGE if it is the image button.
	 *      MediaType.UNDEFINED if it is still not defined.
	 */
	public MediaType getType() {
		return type;
	}

	/**
	 * Sets which type of button is to be made accessible.
	 * Default value is UNDEFINED.
	 * @param type
	 *      MediaType.TEXT if it is the text button.
	 *      MediaType.IMAGE if it is the image button.
	 *      MediaType.UNDEFINED if it is still not defined.
	 */
	public void setType(MediaType type) {
		this.type = type;

		switch (this.type) {
			case TEXT:
				textButton.setAccessible(true);
				imageButton.setAccessible(false);
				break;
			case IMAGE:
				textButton.setAccessible(false);
				imageButton.setAccessible(true);
				break;
			default:
			case UNDEFINED:
				textButton.setAccessible(false);
				imageButton.setAccessible(false);
				break;
		}
	}

	/**
	 * Gets the button currently defined or NULL otherwise.
	 * @return
	 *      A Items.Button instance with the currently defined button.
	 *      Null otherwise.
	 */
	public items.Button getButton() {
		switch (getType()) {
			case TEXT:
				return getTextButton();
			case IMAGE:
				return getImageButton();
			default:
			case UNDEFINED:
				return null;
		}
	}

	/**
	 * Requests and handles operations when the button is focused.
	 */
	@Override
	public void focus(boolean change) {
		items.Button button = getButton();

		if(button == null || !isFocusable()) {
			return;
		}

		button.setFocused(change);
	}

	/**
	 * Handles operations when the button is selected
	 * @return
	 *      Return True if operation was successful
	 *      Return False otherwise (ex: when view is null)
	 */
	@Override
	public boolean select() {
		items.Button button = getButton();

		if(button == null) {
			return false;
		}

		return button.select();
	}

	/**
	 * Checks if this button is currently focused.
	 * @return
	 *      True if it is focused.
	 *      False otherwise.
	 */
	@Override
	public boolean isFocused() {
		items.Button button = getButton();

		if(button == null) {
			return false;
		}

		return button.isFocused();
	}

	/**
	 * Changes the handler of the buttons contained in this view.
	 * @param handle
	 *      New handler to allow communication
	 */
	@Override
	public void setHandler(Handler handle) {
		this.mainHandler = handle;
		items.Button button = getTextButton();

		if(button != null) {
			button.setHandler(handle);
		}

		button = getImageButton();

		if(button != null) {
			button.setHandler(handle);
		}
	}

	/**
	 * Gets the handler for the buttons contained in this view.
	 * @return
	 *      The current handler
	 */
	@Override
	public Handler getHandler() {
		return mainHandler;
	}

	/**
	 * Makes the button visible or invisible
	 * @param change
	 *      True to make the button visible
	 *      False to make it invisible
	 */
	@Override
	public void setVisible(boolean change) {
		items.Button button = getButton();

		if(button == null) {
			return;
		}

		button.setVisible(change);
	}

	/**
	 * Gets the current state of visibility
	 * @return
	 *      True if the button is visible
	 *      False if not visible
	 */
	@Override
	public boolean isVisible() {
		items.Button button = getButton();

		if(button == null) {
			return false;
		}

		return button.isVisible();
	}

	/**
	 * Makes the button focusable or not focusable
	 * @param change
	 *      True to make the button focusable
	 *      False to make it not focusable
	 */
	@Override
	public void setFocusable(boolean change) {
		items.Button button = getButton();

		if(button == null) {
			return;
		}

		button.setFocusable(change);
	}

	/**
	 * Gets the current state of focusability
	 * @return
	 *      True if the button is focusable
	 *      False if not focusable
	 */
	@Override
	public boolean isFocusable() {
		items.Button button = getButton();

		if(button == null) {
			return false;
		}

		return button.isFocusable();
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
		items.Button button = getButton();

		if(button == null) {
			return;
		}

		focus(false);
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
		items.Button button = getButton();

		if(button == null) {
			return false;
		}

		return button.isAccessible();
	}

	/**
	 * Resets the state by changing the type to UNDEFINED.
	 */
	@Override
	public void reset() {
		setType(MediaType.UNDEFINED);
		textButton.reset();
		imageButton.reset();
	}

	public boolean isSelected() {
		items.OptBt button = (OptBt) getButton();

		if(button == null) {
			return false;
		}

		return button.isSelected();
	}

	public void setImage(Bitmap bit) {
		reset();
		if(getImageButton() != null) {
			getImageButton().setImage(bit);
			setType(MediaType.IMAGE);
		}
	}

	public void setText(String txt) {
		reset();
		if(getTextButton() != null) {
			getTextButton().setText(txt);
			setType(MediaType.TEXT);
		}
	}

	public void setOption(boolean change) {
		items.OptBt button = (OptBt)getButton();

		if(button == null) {
			return;
		}

		button.setOption(change);
	}
}
