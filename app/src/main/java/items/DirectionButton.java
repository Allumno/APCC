package items;

import android.os.Handler;
import android.widget.Button;

import alpha.apcc.R;
import variables.Actions;
import variables.Directions;

public class DirectionButton extends NormalButton {
	Directions dir;
	int skin_default;
	int skin_focused;

	/**
	 * Standard button flyweight.
	 *
	 * @param button View of the button
	 * @param handle Handle to process instructions by other threads
	 */
	public DirectionButton(Button button, Handler handle) {
		super(button, handle);
	}

	/**
	 * Standard button flyweight.
	 *
	 * @param button View of the button
	 * @param handle Handle to process instructions by other threads
	 * @param action Flag to indicate the action to be executed.
	 */
	public DirectionButton(Button button, Handler handle, Actions action) {
		super(button, handle, action);
	}

	/**
	 * Standard button flyweight.
	 *
	 * @param button View of the button
	 * @param handle Handle to process instructions by other threads
	 * @param action Flag to indicate the action to be executed.
	 * @param dir Flag to indicate the direction of this button.
	 */
	public DirectionButton(Button button, Handler handle, Actions action, Directions dir) {
		super(button, handle, action);
		setDir(dir);
	}

	/**
	 * Gets the direction associated with this button.
	 * @return
	 *      An enumeration value that indicates the direction.
	 */
	public Directions getDir() {
		return dir;
	}

	/**
	 * Sets the direction associated with this button.
	 * @param dir
	 *      An enumeration value that indicates the direction.
	 */
	public void setDir(Directions dir) {
		this.dir = dir;

		switch (dir) {
			case UP:
				setAction(Actions.BT_UP);
				skin_default = R.drawable.vertical_button;
				skin_focused = R.drawable.focus_vertical_button;
				break;
			case DOWN:
				setAction(Actions.BT_DOWN);
				skin_default = R.drawable.vertical_button;
				skin_focused = R.drawable.focus_vertical_button;
				break;
			case LEFT:
				setAction(Actions.BT_LEFT);
				skin_default = R.drawable.horizontal_button;
				skin_focused = R.drawable.focus_horizontal_button;
				break;
			case RIGHT:
				setAction(Actions.BT_RIGHT);
				skin_default = R.drawable.horizontal_button;
				skin_focused = R.drawable.focus_horizontal_button;
				break;
		}

		changeBG(SKIN.UNFOCUSED);
	}

	/**
	 * Changes the background of the button.
	 *
	 * @param sk Flag to indicate to which button it will update.
	 */
	@Override
	public void changeBG(SKIN sk) {
		switch(sk) {
			case FOCUSED:
				id = skin_focused;
				break;
			case UNFOCUSED:
			default:
				id = skin_default;
				break;
		}

		button.setBackgroundResource(id);

		//  Apply rotation in case it's not the default directions of the template (LEFT or UP)
		if (getDir() == Directions.RIGHT || getDir() == Directions.DOWN) {
			button.setRotation(180);
		}

		refresh();
	}
}
