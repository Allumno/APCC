package runner;

import items.GroupItems;
import items.GroupManager;

public class LangUnitStrat extends SwipeStrategyAbstract {

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
}
