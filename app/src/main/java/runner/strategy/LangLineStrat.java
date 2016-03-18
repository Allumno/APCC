package runner.strategy;

import items.GroupItems;
import items.GroupManager;

/**
 * Created by JLA on 15/01/2015.
 */
public class LangLineStrat extends SwipeStrategyAbstract {

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

		GroupItems g2 = new GroupItems(temp.getHandler());
		g2.addItem("opt4", temp.getItem("opt4"));
		g2.addItem("opt5", temp.getItem("opt5"));
		g2.addItem("opt6", temp.getItem("opt6"));
		g2.addItem("opt7", temp.getItem("opt7"));

		footer = new GroupItems(temp.getHandler());
		footer.addItem("back", temp.getItem("back"));

		main = new GroupItems(temp.getHandler());
		main.addItem("g0", g1);
		main.addItem("g1", g2);
		main.addItem("footer", footer);
	}
}