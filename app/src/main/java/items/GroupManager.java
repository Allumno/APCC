package items;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import variables.Actions;
import variables.Directions;

public class GroupManager {
	private GroupItems group;
	private Handler handler;

	/**
	 * Creates a new factory of items.
	 * @param handle
	 *      Handler responsible for the messages sent from and to the items.
	 */
	public GroupManager(Handler handle) {
		group = new GroupItems();
		handler = handle;
	}

	/**
	 * Adds every item, individually, contained in the given group manager.
	 * @param manager
	 *      Group manager containing the items to be added.
	 */
	public void addGroupManager(GroupManager manager) {
		//  If the the given manager is null.
		if (manager == null) {
			return;
		}

		GroupItems temp = manager.getGroup();

		//  If there are no views in the group.
		if (temp == null) {
			return;
		}

		for(String key : temp.getList().keySet()) {
			group.addItem(key, temp.getItem(key));
		}
	}

	/**
	 * Creates a standard button.
	 * Replaces existent item if the ID is already present.
	 * @param id
	 *      ID of the button to be able to fetch it later.
	 * @param vi
	 *      View of the button to be created.
	 */
	public void addNormalBt(String id, View vi) {
		if(vi != null) {
			group.addItem(id, new NormalButton((Button) vi, handler));
		}
	}

	/**
	 * Creates a standard button.
	 * Replaces existent item if the ID is already present.
	 * @param id
	 *      ID of the button to be able to fetch it later.
	 * @param vi
	 *      View of the button to be created.
	 * @param action
	 *      Flag of the action to be handled.
	 */
	public void addNormalBt(String id, View vi, Actions action) {
		if(vi != null) {
			group.addItem(id, new NormalButton((Button) vi, handler, action));
		}
	}

	/**
	 * Creates an option button.
	 * Replaces existent item if the ID is already present.
	 * @param id
	 *      ID of the button to be able to fetch it later
	 * @param vi
	 *      View of the button to be created
	 */
	public void addOptionBt(String id, View vi) {
		if(vi != null) {
			group.addItem(id, new OptionButton((Button) vi, handler));
		}
	}

	/**
	 * Creates a background button.
	 * Replaces existent item if the ID is already present.
	 * @param id
	 *      ID of the button to be able to fetch it later.
	 * @param vi
	 *      View of the button to be created.
	 */
	public void addBackgroundBt(String id, View vi) {
		if(vi != null) {
			group.addItem(id, new BackgroundView(vi, handler));
		}
	}

	/**
	 * Creates a text option button.
	 * Replaces existent item if the ID is already present.
	 * @param id
	 *      ID of the button to be able to fetch it later.
	 * @param vi
	 *      View of the button to be created.
	 */
	public void addTextBt (String id, View vi) {
		if(vi != null) {
			group.addItem(id, new TextBt((Button) vi, handler));
		}
	}

	/**
	 * Creates an image option button.
	 * Replaces existent item if the ID is already present.
	 * @param id
	 *      ID of the button to be able to fetch it later.
	 * @param vi
	 *      View of the button to be created.
	 */
	public void addImageBt (String id, View vi) {
		if(vi != null) {
			group.addItem(id, new ImgBt((ImageButton) vi, handler));
		}
	}

	/**
	 * Adds a view/object that implements ItemInterface.
	 * @param id
	 *      ID of the view to be able to fetch it later.
	 * @param vi
	 *      Item to be added to the group.
	 */
	public void addItemInterface(String id, ItemInterface vi) {
		if(vi != null) {
			group.addItem(id, vi);
		}
	}

	/**
	 * Creates a direction button.
	 * @param id
	 *      ID of the button to be able to fetch it later.
	 * @param vi
	 *      View of the button to be created.
	 * @param action
	 *      Flag of the action to be handled.
	 * @param dir
	 *      Direction of the button.
	 */
	public void addDirectionBt(String id, View vi, Actions action, Directions dir) {
		if(vi != null) {
			group.addItem(id, new DirectionButton((Button) vi, handler, action, dir));
		}
	}

	/**
	 * Gets a specific item from the list.
	 * @param id
	 *      ID of the item to be retrieved.
	 * @return
	 *      The item desired associated to the respective interface.
	 */
	public ItemInterface getItem(String id) {
		return group.getItem(id);
	}

	/**
	 * Removes a specific item from the list.
	 * @param id
	 *      ID of the item to be deleted.
	 */
	public void removeItem(String id) {
		group.removeItem(id);
	}

	/**
	 * Returns the list of items.
	 * @return
	 *      Returns the group created with the list of items.
	 */
	public GroupItems getGroup() {
		return group;
	}

	/**
	 * Clears the all list of items in memory.
	 */
	public void clear() {
		group.clear();
	}

	/**
	 * Resets the state of all items created by this factory.
	 */
	public void reset() {
		group.reset();
	}

	/**
	 * Sets an handler to all the items, and future items, included in this factory.
	 * @param handler
	 *      Handler destined to achieve greatness!
	 */
	public void setHandler(Handler handler) {
		this.handler = handler;
		group.setHandler(handler);
	}
}
