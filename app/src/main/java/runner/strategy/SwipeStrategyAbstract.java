package runner.strategy;

import items.GroupItems;
import items.GroupManager;

public abstract class SwipeStrategyAbstract {

	GroupItems main = null;
	GroupItems footer = null;

	/**
	 * Setups the strategy to be used by the swipe thread
	 * @param fact
	 *      Instance of GroupManager with the items already setup
	 */
	public abstract void setup(GroupManager fact);

	/**
	 * Returns the main group of the defined strategy
	 * @return
	 *      An instance of the group to be used by the swipe strategy
	 */
	public GroupItems getMainGroup() {
        return main;
    }

	/**
	 * Returns the footer group of the defined strategy
	 * @return
	 *      An instance of the footer group to be used by the swipe strategy
	 */
	public GroupItems getFooterGroup() {
        return footer;
    }
}
