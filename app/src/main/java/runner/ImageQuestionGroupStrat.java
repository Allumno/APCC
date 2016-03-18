package runner;

import items.GroupItems;
import items.GroupManager;

public class ImageQuestionGroupStrat extends SwipeStrategyAbstract {

	/**
	 * Setups the strategy to be used by the swipe thread
	 *
	 * @param fact Instance of GroupManager with the items already setup
	 */
	@Override
	public void setup(GroupManager fact) {
		GroupItems temp = fact.getGroup();

		GroupItems g0 = new GroupItems(temp.getHandler());
		g0.addItem("opt0", temp.getItem("opt0"));
		g0.addItem("opt1", temp.getItem("opt1"));
		g0.addItem("opt2", temp.getItem("opt2"));
		g0.addItem("opt3", temp.getItem("opt3"));

		footer = new GroupItems(temp.getHandler());
		footer.addItem("back", temp.getItem("back"));

		main = new GroupItems(temp.getHandler());
		main.addItem("g0",g0);
		main.addItem("footer", footer);
	}
}
