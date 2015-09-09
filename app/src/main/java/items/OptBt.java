package items;

import android.os.Handler;
import android.os.Message;
import android.view.View;

import alpha.apcc.R;
import variables.Actions;


public abstract class OptBt extends items.Button {
	private boolean option = false;     //  Option of the button
	private boolean selected = false;
	private enum SKIN {DEFAULT, INCORRECT, CORRECT}

	/**
	 * Standard button flyweight (option = false by default)
	 * @param button
	 *      View of the button
	 * @param handle
	 *      Handle to process instructions by other threads
	 */
	public OptBt(View button, Handler handle) {
		super(button, handle);
		option = false;
		selected = false;
	}

	/**
	 * Standard button flyweight (option = false by default)
	 * @param button
	 *      View of the button
	 * @param handle
	 *      Handle to process instructions by other threads
	 */
	public OptBt(View button, Handler handle, Actions action) {
		super (button, handle, action);
		option = false;
		selected = false;
	}

	/**
	 * Handles operations when the button is selected
	 * @return
	 *      Return True if operation was successful
	 *      Return False otherwise.
	 */
	@Override
	public boolean select() {
		if(button == null) {
			return false;
		}

		//  The option will become unfocusable and selected
		setFocusable(false);
		setSelected(true);

		if(getOption()) {
			//  Changes the button to show that the option is correct
			changeBG(SKIN.CORRECT);

			//  Sends instruction to change turn
			Message msg = handler.obtainMessage(0, Actions.MISC_NEXT_TURN);
			handler.sendMessage(msg);

			return true;
		}
		else {
			//  Changes the button to show that the option is incorrect
			changeBG(SKIN.INCORRECT);

			return false;
		}
	}

	/**
	 * Resets the state of the button.
	 * Sets the option to false, makes it visible, focusable and resets the background.
	 */
	@Override
	public void reset() {
		setOption(false);
		setSelected(false);
		super.reset();
	}

	/**
	 * Changes the background of the button.
	 * @param sk
	 *      Flag to indicate to which button it will update.
	 */
	public void changeBG(SKIN sk) {
		int id;

		switch(sk) {
			case CORRECT:
				id = R.drawable.correct_button;
				break;
			case INCORRECT:
				id = R.drawable.incorrect_button;
				break;
			default:
				id = R.drawable.default_button;
				break;
		}

		button.setBackgroundResource(id);
		refresh();
	}

	/**
	 * Sets the option of the button
	 * @param option
	 *      True if the button is the correct option
	 *      False if the button is the incorrect option
	 */
	public void setOption(boolean option) {
		this.option = option;
	}

	/**
	 * Gets the current option of the button
	 * @return
	 *      True if the button is the correct option
	 *      False if the button is the incorrect option
	 */
	public boolean getOption() {
		return option;
	}

	/**
	 * Checks if the current option is already selected or not.
	 * @return
	 *      True if it is selected.
	 *      False otherwise.
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * Sets the current option has already selected or not.
	 * @return
	 *      True if it is selected.
	 *      False otherwise.
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
