package items;

import android.os.Handler;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class GroupItems implements ItemInterface, Serializable {
	private LinkedHashMap<String, ItemInterface> list = null;   //  List of items
	private Handler handler = null;                             //  Handle to allow instruction sending to other threads
	private boolean focused = false;

	/**
	 * Standard group of flyweights
	 */
	public GroupItems() {
		list = new LinkedHashMap<String, ItemInterface>();
	}

	/**
	 * Standard group of flyweights.
	 * @param handle
 *          Handler to communicate with other threads.
	 */
	public GroupItems(Handler handle) {
		list = new LinkedHashMap<String, ItemInterface>();
		handler = handle;
		focused = false;
	}

	/**
	 * Requests and handles operations when the group of items is focused.
	 */
	@Override
	public void focus(boolean change) {
		this.focused = change;

		Iterator<Map.Entry<String, ItemInterface>> iter = getSetIterator();
		ItemInterface it;

		if (iter == null) {
			return;
		}

		while(iter.hasNext()) {
			it = iter.next().getValue();
			it.focus(change);
		}

		this.focused = change;
	}

	/**
	 * Returns if the group is currently focused.
	 * @return
	 *      True if the group is focused.
	 *      False otherwise.
	 */
	@Override
	public boolean isFocused() {
		return this.focused;
	}

	/**
	 * Selects the current group.
	 * Does nothing by default leaving the item group access task for the swipe algorithm.
	 * @return
	 *      True if list is not null.
	 *      False if list is null.
	 */
	@Override
	public boolean select() {
		return list != null;
	}

	/**
	 * Changes the handler of this item list and all the items included in it.
	 * @param handle
	 *      New handler to allow communication.
	 */
	@Override
	public void setHandler(Handler handle) {
		handler = handle;

		for(ItemInterface it : list.values()) {
			it.setHandler(handler);
		}
	}

	/**
	 * Gets the current handler associated with this item list.
	 * @return
	 *      The current handler.
	 */
	@Override
	public Handler getHandler() {
		return handler;
	}

	/**
	 * Change visibility of all the items in this group.
	 * @param change
	 *      True to make the items visible.
	 *      False to make the items invisible.
	 */
	@Override
	public void setVisible(boolean change) {
		Iterator<Map.Entry<String, ItemInterface>> iter = getSetIterator();
		ItemInterface it;

		if (iter == null) {
			return;
		}

		while(iter.hasNext()) {
			it = iter.next().getValue();
			it.setVisible(change);
		}
	}

	/**
	 * Returns if the items in this group are visible or not.
	 * @return
	 *      True if items are visible.
	 *      False items are invisible.
	 */
	@Override
	public boolean isVisible() {
		return (getNumVisible() > 0);
	}

	/**
	 * Change focusability of all the items in this group.
	 * @param change
	 *      True to make the items focusable.
	 *      False to make the items not focusable.
	 */
	@Override
	public void setFocusable(boolean change) {
		Iterator<Map.Entry<String, ItemInterface>> iter = getSetIterator();
		ItemInterface it;

		if (iter == null) {
			return;
		}

		while(iter.hasNext()) {
			it = iter.next().getValue();
			it.setFocusable(change);
		}
	}

	/**
	 * Returns if the items in this group are focusable or not.
	 * @return
	 *      True if items are focusable.
	 *      False items are not focusable.
	 */
	@Override
	public boolean isFocusable() {
		return (getNumFocusable() > 0);
	}

	/**
	 * Changes the accessibility (visibility + focusability).
	 * @param change
	 *      True to make all items visible AND focusable.
	 *      False to make all items invisible AND not focusable.
	 */
	@Override
	public void setAccessible(boolean change) {
		setFocusable(change);
		setVisible(change);
	}

	/**
	 * Gets the current state of accessibility.
	 * @return
	 *      True if items are visible AND focusable.
	 *      False if items are invisible AND not focusable.
	 */
	@Override
	public boolean isAccessible() {
		return (isFocusable() && isVisible());
	}

	/**
	 * Resets the state of the items in the list.
	 * Makes them visible, focusable and other default operations they might have.
	 */
	@Override
	public void reset() {
		Iterator<Map.Entry<String, ItemInterface>> iter = getSetIterator();
		ItemInterface it;

		if (iter == null) {
			return;
		}

		while(iter.hasNext()) {
			it = iter.next().getValue();
			it.reset();
		}
	}

	/**
	 * Gets the number of focusable items contained in this group.
	 * @return
	 *      The number of elements that are focusable.
	 */
	public int getNumFocusable() {
		Iterator<Map.Entry<String, ItemInterface>> item = getSetIterator();
		int numFocusable = 0;

		while(item != null && item.hasNext()) {
			if (item.next().getValue().isFocusable()) {
				numFocusable++;
			}
		}

		return numFocusable;
	}

	/**
	 * Gets the number of visible items contained in this group.
	 * @return
	 *      The number of elements that are visible.
	 */
	public int getNumVisible() {
		Iterator<Map.Entry<String, ItemInterface>> item = getSetIterator();
		int numVisible = 0;

		while(item != null && item.hasNext()) {
			if (item.next().getValue().isVisible()) {
				numVisible++;
			}
		}

		return numVisible;
	}

	/**
	 * Adds an item to the group replacing the previous, if it exists, with the same ID tag.
	 * @param id
	 *      ID of the item to be inserted in the group.
	 * @param item
	 *      Item flyweight to be added to the group.
	 * @return
	 *      True if item was successfully added.
	 *      False if list is null or item is null.
	 */
	public boolean addItem(String id, ItemInterface item) {
		if (list != null && item != null) {
			list.put(id, item);
			return true;
		}
		return false;
	}

	/**
	 * Returns the list of items.
	 * @return
	 *      Returns a linked HashMap with the items or null if list was not initialized.
	 */
	public LinkedHashMap<String, ItemInterface> getList() {
		return list;
	}

	/**
	 * Returns the item with the respective ID.
	 * @param id
	 *      ID of the item to be returned.
	 * @return
	 */
	public ItemInterface getItem(String id) {
		if (list != null) {
			return list.get(id);
		}
		return null;
	}

	/**
	 * Deletes an item with the given ID from the group.
	 * @param id
	 *      ID of the item to be deleted.
	 */
	public void removeItem(String id) {
		if (list != null && !list.isEmpty()) {
			list.remove(id);
		}
	}

	/**
	 * Clears all the items from the group.
	 */
	public void clear() {
		if (list != null) {
			list.clear();
		}
	}

	/**
	 * Returns an iterator of the set of Map entries with the IDs and items contained in this group.
	 * @return
	 *      An iterator of the set of Map.Entry containing the IDs and items.
	 */
	public Iterator<Map.Entry<String, ItemInterface>> getSetIterator() {
		if (list != null) {
			return list.entrySet().iterator();
		}
		return null;
	}
}
