package runner;

import items.GroupItems;
import items.GroupManager;

public class LangUnitStrat implements SwipeStrategyInterface {

	private GroupItems main = null;
	private GroupItems footer = null;

	/**
	 * Setups the strategy to be used by the swipe thread.
	 * @param fact
	 *      Instance of GroupManager with the items already setup.
	 */
	@Override
	public void setup(GroupManager fact) {
		main = fact.getGroup();

		footer = new GroupItems(main.getHandler());
		footer.addItem("back", main.getItem("back"));
	}

	/**
	 * Returns the main group of the defined strategy.
	 * @return
	 *      An instance of the group to be used by the swipe strategy. Null if it was not setup.
	 */
	@Override
	public GroupItems getMainGroup() {
		return main;
	}

	/**
	 * Returns the footer group of the defined strategy
	 *
	 * @return An instance of the footer group to be used by the swipe strategy
	 */
	@Override
	public GroupItems getFooterGroup() {
		return footer;
	}
}
