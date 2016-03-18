package runner;

import items.GroupItems;
import items.GroupManager;

public class LessonUnitStrat extends SwipeStrategyAbstract {

	/**
	 * Setups the strategy to be used by the swipe thread.
	 * @param fact
	 *      Instance of GroupManager with the items already setup.
	 */
	@Override
	public void setup(GroupManager fact) {
		GroupItems temp = fact.getGroup();

		main = new GroupItems(temp.getHandler());
		main.addItem("up", temp.getItem("up"));
		main.addItem("down", temp.getItem("down"));
		main.addItem("back", temp.getItem("back"));
		main.addItem("next", temp.getItem("next"));

		footer = new GroupItems(temp.getHandler());
		footer.addItem("back", temp.getItem("back"));
		footer.addItem("next", temp.getItem("next"));
	}
}
