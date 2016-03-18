package runner;

import android.os.Handler;
import android.os.Message;

import java.util.Iterator;
import java.util.Map;

import variables.Actions;
import items.GroupItems;
import items.ItemInterface;
import variables.Configurations;


public class SwipeRunner implements Runnable {
	private GroupItems main;                                                //  Stores the main group of items
	private GroupItems footerGroup;                                         //  Stores the footerGroup group of items
	private GroupItems currentGroup;                                        //  Current group of items
	private ItemInterface currentItem;                                      //  Current item selected inside the group
	private ItemInterface previousItem;                                     //  Item previously focused

	private Iterator<Map.Entry<String, ItemInterface>> elements;            //  Iterator of elements in the current group

	private long start_delay;                                               //  Sets the time delay to start the swiping
	private Handler handler;                                                //  Reference to the main handler to treat UI events

	private boolean pause;                                                  //  Alternates the swipe between pause and play
	private boolean end;                                                    //  Marks when the thread should end execution

	/**
	 * Public constructor.
	 * @param strat
	 *      Strategy instance to be used by the swipe thread.
	 * @param handle
	 *      Handler used to communicate with other threads.
	 */
	public SwipeRunner(SwipeStrategyAbstract strat, Handler handle, long start, long step) {
		main = currentGroup = strat.getMainGroup();
		footerGroup = strat.getFooterGroup();
		elements = main.getSetIterator();
		previousItem = null;
		currentItem = null;
		handler = handle;
		start_delay = start;
		pause = false;
		end = false;
	}

	/**
	 * Main thread.
	 */
	public void run() {
		if(!pause) {
			//  Advance the focused button between all groups
			nextElement();

			//  Asks the UI thread to unfocus previous button and focus new one
			Message msg = handler.obtainMessage(0, Actions.SW_NEXT);
			handler.sendMessage(msg);
		}

		//  Repeats the process while end flag is not activated
		handler.postDelayed(this, Configurations.time_step);
	}

	/**
	 * Starts the swipe thread.
	 */
	public void start() {
		handler.removeCallbacks(this);
		pause = false;
		end = false;
		handler.postDelayed(this, start_delay);
	}

	/**
	 * Pauses/Unpauses the swipe thread.
	 * @param trigger
	 *      True if swipe is to be paused.
	 *      False if it is to unpause.
	 */
	public void pause(boolean trigger) {
		pause = trigger;
	}

	/**
	 * Ends/De-Ends(?) the game.
	 * @param trigger
	 *      True if the game has ended.
	 *      False otherwise.
	 */
	public void end(boolean trigger) {
		end = trigger;
		reset();
	}

	/**
	 * Marks the swipe to stop
	 */
	public void stop() {
		pause = true;
		end = true;
		handler.removeCallbacks(this);
	}

	/**
	 * Action of selecting an item or group.
	 * If it's a group, then it will swipe through it.
	 * Else it will behave has defined in the item.
	 */
	public void select() {
		pause(true);

		ItemInterface item = getCurrentItem();

		//  Ignores selection as protection for times where the item is not yet loaded
		if (item == null) {
			pause(false);
			return;
		}

		//  If the current item is a sub-group it swipes through it
		if (item instanceof GroupItems) {
			nextSubGroup();

			//  In case there is only one item in the group select it
			if (getCurrentGroup().getNumFocusable() == 1) {
				item = nextElement();
			}
		}

		//  If the current item is not a group select it
		if (!(item instanceof GroupItems)) {
			item.select();
			reset();
		}

		pause(false);
	}
	
	/**
	 * Advances one step in the swipe.
	 */
	public void nextStep() {
		ItemInterface item;

		//  Unfocus the previous item
		item = getPreviousItem();

		if (item != null) {
			item.focus(false);
		}

		//  Focus the next item
		item = getCurrentItem();

		if (item != null){
			item.focus(true);
		}
	}

	/**
	 * Cycles through the current group with each turn.
	 * @return
	 *      An object that implements the ItemInterface.
	 */
	public ItemInterface nextElement() {
		previousItem = currentItem;

		//  If the current group as no remaining focusable items the swipe is reset
		if (!currentGroup.isFocusable()) {
			reset();
		}

		//  IF there is more than 1 item that is focusable
		//  OR there is only one last item but it is not the the current focused item
		//  THEN searches for the item
		if (currentGroup.isFocusable()) {
			do {
				if (!elements.hasNext()) {
					cycleReset();
				}

				currentItem = elements.next().getValue();
			} while (!currentItem.isFocusable());
		}

		//  Returns the current focusable item
		return currentItem;
	}

	/**
	 * Selection of the current item
	 */
	public void nextSubGroup() {
		currentGroup = (GroupItems) currentItem;
		cycleReset();
	}

	/**
	 * Gets the previous focused item.
	 * @return
	 *      The previous focused item.
	 *      Null if none or previous item was selected.
	 */
	public ItemInterface getPreviousItem() {
		return previousItem;
	}

	/**
	 * Gets the current item being focused.
 	 * @return
	 *      The currently focused item.
	 */
	public ItemInterface getCurrentItem() {
		return currentItem;
	}

	/**
	 * Returns the current group being swiped.
	 * @return
	 *      The current group being swiped.
	 */
	public GroupItems getCurrentGroup() {
		return currentGroup;
	}

	/**
	 * Resets the swipe in the current selected group.
	 * Puts the focus on the first item in the current selected group.
	 */
	public void cycleReset() {
		Iterator<Map.Entry<String, ItemInterface>> iter = currentGroup.getSetIterator();

		if (iter != null) {
			elements = iter;
		}
	}

	/**
	 * Resets the swipe.
	 * Puts the focus on the first item on the main group.
	 */
	public void reset() {
		//  To avoid the current item being let focused after the reset
		if (currentItem != null && currentItem.isFocusable()) {
			currentItem.focus(false);
		}

		if (!end) {
			currentGroup = main;
		}
		else {
			currentGroup = footerGroup;
		}

		currentItem = null;
		previousItem = null;
		cycleReset();
	}
}
