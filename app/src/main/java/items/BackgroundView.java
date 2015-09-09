package items;

import android.os.Handler;
import android.os.Message;
import android.view.View;

import java.io.Serializable;

import constants.Actions;
import constants.Configurations;

public class BackgroundView implements ItemInterface, Serializable {
	protected int id = -1;                  //  ID of the stored button
	protected View view = null;             //  View of the button
	protected Handler handler = null;       //  Handle to allow instruction sending to other threads
	protected boolean focused = false;      //  Signals if the button is focused or not
	protected boolean focusable = false;    //  Signals if the button can be focused or not

	/**
	 * Background view flyweight.
	 * @param vi
	 *      View of the background.
	 * @param handle
	 *      Handle to process instructions by other threads.
	 */
	public BackgroundView(View vi, Handler handle) {
		view = vi;
		id = vi.getId();
		handler = handle;
		focused = false;
		focusable = false;

		if (Configurations.swipeToggle) {
			view.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Message msg = handler.obtainMessage(0, Actions.SW_SELECT);
					handler.sendMessage(msg);
				}
			});
		}
	}

	/**
	 * Nothing should happen if the background is focused.
	 */
	@Override
	public void focus(boolean change) {
		return;
	}

	/**
	 * Returns false by default since background can't be focused.
	 * @return
	 *      False by default.
	 */
	@Override
	public boolean isFocused() {
		return false;
	}

	/**
	 * If the background is selected it returns true (selects the currently focused button).
	 * @return
	 *      False if the view defined is null.
	 *      True otherwise.
	 */
	@Override
	public boolean select() {
		if (view == null) {
			return false;
		}

		return true;
	}

	/**
	 * Changes the handler of this button.
	 * @param handle
	 *      New handler to allow communication.
	 */
	@Override
	public void setHandler(Handler handle) {
		handler = handle;
	}

	/**
	 * Gets the current handler associated with this button.
	 * @return
	 *      The current handler.
	 */
	@Override
	public Handler getHandler() {
		return handler;
	}

	/**
	 * Makes the button visible or invisible.
	 * @param change
	 *      True to make the button visible.
	 *      False to make it invisible.
	 */
	@Override
	public void setVisible(boolean change) {
		if (change) {
			view.setVisibility(View.VISIBLE);
		}
		else {
			view.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * Gets the current state of visibility.
	 * @return
	 *      True if the button is visible.
	 *      False if not visible.
	 */
	@Override
	public boolean isVisible() {
		if (view.getVisibility() == View.VISIBLE) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Makes the button focusable or not focusable.
	 * @param change
	 *      True to make the button focusable.
	 *      False to make it not focusable.
	 */
	@Override
	public void setFocusable(boolean change) {
		focusable = change;
	}

	/**
	 * Gets the current state of focusability.
	 * @return
	 *      True if the button is focusable.
	 *      False if not focusable.
	 */
	@Override
	public boolean isFocusable() {
		return focusable;
	}

	/**
	 * Changes the accessibility (visibility + focusability).
	 * @param change
	 *      True to make it visible AND focusable.
	 *      False to make it invisible AND not focusable.
	 */
	@Override
	public void setAccessible(boolean change) {
		setFocusable(change);
		setVisible(change);
	}

	/**
	 * Gets the current state of accessibility.
	 * @return
	 *      True if visible AND focusable.
	 *      False if invisible AND not focusable.
	 */
	@Override
	public boolean isAccessible() {
		return (isFocusable() && isVisible());
	}

	/**
	 * Resets the state of the background.
	 * Makes it visible and not focusable.
	 */
	@Override
	public void reset() {
		if ( view == null) {
			return;
		}

		setFocusable(false);
		setVisible(true);
	}
}
