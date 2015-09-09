package runner;

import items.GroupItems;
import items.GroupManager;

public interface SwipeStrategyInterface {
	/**
	 * Setups the strategy to be used by the swipe thread
	 * @param fact
	 *      Instance of GroupManager with the items already setup
	 */
	public void setup(GroupManager fact);

	/**
	 * Returns the main group of the defined strategy
	 * @return
	 *      An instance of the group to be used by the swipe strategy
	 */
	public GroupItems getMainGroup();

	/**
	 * Returns the footer group of the defined strategy
	 * @return
	 *      An instance of the footer group to be used by the swipe strategy
	 */
	public GroupItems getFooterGroup();
}
