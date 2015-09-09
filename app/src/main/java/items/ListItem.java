package items;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;

import variables.Actions;
import variables.Configurations;
import logic.FileDialogAdapter;

public class ListItem implements ItemInterface {
	protected int id = -1;                                  //  ID of the stored list
	protected ListView list = null;                         //  View of the text list (default)
	protected FileDialogAdapter adapter = null;             //  Adapter of the text list
	protected View view = null;                             //  Element view in the given position in the list
	protected int position = 0;                             //  Position of the item in the list
	protected Handler handler = null;                       //  Handle to allow instruction sending to other threads
	protected boolean focused = false;                      //  Signals if the list is focused or not
	protected boolean focusable = false;                    //  Signals if the list can be focused or not
	protected enum SKIN {UNFOCUSED, FOCUSED}                //  Flags the skin to use
	private Actions action = Actions.LIST_ITEM_SELECT;      //  Flag to be used by the handler on selection


	public ListItem(ListView list, int position, Handler handler) {
		setList(list);
		setAdapter(list.getAdapter());
		setView(getAdapter().getView(position, null, getList()));
		setPosition(position);
		setHandler(handler);
		setFocused(false);
		setFocusable(true);
		setListener();
	}
	
	/**
	 * Requests and handles operations when the list is focused.
	 */
	@Override
	public void focus(boolean change) {
		if(getView() == null || !isFocusable()) {
			return;
		}

		setFocused(change);
	}

	/**
	 * Handles operations when the list is selected
	 * @return
	 *      Return True if operation was successful
	 *      Return False otherwise (ex: when view is null)
	 */
	@Override
	public boolean select() {
		if(getView() == null) {
			return false;
		}

		//  Sends instruction of selection
		Message msg = handler.obtainMessage(0, getPosition(), 0, getAction());
		handler.sendMessage(msg);

		return true;
	}

	/**
	 * Changes the handler of this list
	 * @param handle
	 *      New handler to allow communication
	 */
	@Override
	public void setHandler(Handler handle) {
		handler = handle;
	}

	/**
	 * Gets the current handler associated with this list
	 * @return
	 *      The current handler
	 */
	@Override
	public Handler getHandler() {
		return handler;
	}

	/**
	 * Makes the list visible or invisible
	 * @param change
	 *      True to make the list visible
	 *      False to make it invisible
	 */
	@Override
	public void setVisible(boolean change) {
		if (change) {
			getView().setVisibility(View.VISIBLE);
		}
		else {
			getView().setVisibility(View.INVISIBLE);
		}
		refresh();
	}

	/**
	 * Gets the current state of visibility
	 * @return
	 *      True if the list is visible
	 *      False if not visible
	 */
	@Override
	public boolean isVisible() {
		if (getView().getVisibility() == View.VISIBLE) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Makes the list focusable or not focusable
	 * @param change
	 *      True to make the list focusable
	 *      False to make it not focusable
	 */
	@Override
	public void setFocusable(boolean change) {
		focusable = change;
	}

	/**
	 * Gets the current state of focusability
	 * @return
	 *      True if the list is focusable
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
	 * Resets the state of the list.
	 * Makes it visible, focusable and resets the background.
	 */
	@Override
	public void reset() {
		if ( getView() == null) {
			return;
		}
		setAccessible(true);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ListView getList() {
		return list;
	}

	public void setList(ListView list) {
		this.list = list;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public FileDialogAdapter getAdapter() {
		return adapter;
	}

	public void setAdapter(Adapter adapter) {
		this.adapter = (FileDialogAdapter) adapter;
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	/**
	 * Flags the item as being focused or not.
	 * @param focused
	 *      True if it is now focused.
	 *      False otherwise.
	 */
	public void setFocused(boolean focused) {
		this.focused = focused;

		getAdapter().focus(getPosition(), focused);
		int topVisible = getList().getFirstVisiblePosition();
		int bottomVisible = getList().getLastVisiblePosition();

		if(topVisible > getPosition() || bottomVisible < getPosition()) {
			getList().setSelection(getPosition());
		}
	}

	/**
	 * Checks if this item is currently focused.
	 * @return
	 *      True if it is focused.
	 *      False otherwise.
	 */
	public boolean isFocused() {
		return focused;
	}

	/**
	 * OnClick listener setup (default action).
	 */
	public void setListener() {
		getView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (handler != null) {
					if (Configurations.swipeToggle) {
						Message msg = handler.obtainMessage(0, position, 0, Actions.SW_SELECT);
						handler.sendMessage(msg);
					} else {
						System.out.println("#asdasdas###");
						select();
					}
				}
			}
		});
	}

	/**
	 * Refresh the state of the button
	 */
	public void refresh() {
		getView().refreshDrawableState();
		getList().refreshDrawableState();
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

}
