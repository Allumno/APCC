package runner;

import items.GroupItems;
import items.GroupManager;

public class QuestionLineStrat implements SwipeStrategyInterface {
	private GroupItems main = null;
	private GroupItems footer = null;

	/**
	 * Setups the strategy to be used by the swipe thread
	 * @param fact
	 *      Instance of GroupManager with the items already setup
	 */
	@Override
	public void setup(GroupManager fact) {
		GroupItems temp = fact.getGroup();

		GroupItems g1 = new GroupItems(temp.getHandler());
		g1.addItem("opt0", temp.getItem("opt0"));
		g1.addItem("opt1", temp.getItem("opt1"));
		g1.addItem("opt2", temp.getItem("opt2"));
		g1.addItem("opt3", temp.getItem("opt3"));

		footer = new GroupItems(temp.getHandler());
		footer.addItem("back", temp.getItem("back"));

		main = new GroupItems(temp.getHandler());
		main.addItem("g0", g1);
		main.addItem("footer", footer);
	}

	/**
	 * Returns the main group of the defined strategy
	 * @return
	 *      An instance of the group to be used by the swipe strategy. Null if it was not setup
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