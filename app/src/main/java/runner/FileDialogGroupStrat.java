package runner;

import java.util.Iterator;
import java.util.Map;

import items.GroupItems;
import items.GroupManager;
import items.ItemInterface;

public class FileDialogGroupStrat extends SwipeStrategyAbstract {

	/**
	 * Setups the strategy to be used by the swipe thread
	 *
	 * @param fact Instance of GroupManager with the items already setup
	 */
	@Override
	public void setup(GroupManager fact) {
		GroupItems temp = fact.getGroup();

		GroupItems g0 = new GroupItems(temp.getHandler());
//		g0.addItem("upBt", temp.getItem("upBt"));
//		g0.addItem("downBt", temp.getItem("downBt"));

		Iterator it = temp.getSetIterator();
		Map.Entry<String, ItemInterface> entry;
		String key;

		while (it.hasNext()) {
			entry = (Map.Entry<String, ItemInterface>) it.next();
			key = entry.getKey().toLowerCase();
			if (key.matches("li\\d+")) {
				g0.addItem(key, entry.getValue());
			}
		}

		GroupItems g1 = new GroupItems(temp.getHandler());
		g1.addItem("leftBt", temp.getItem("leftBt"));

		footer = new GroupItems(temp.getHandler());
		footer.addItem("back", temp.getItem("back"));

		main = new GroupItems(temp.getHandler());
		main.addItem("g0", g0);
		main.addItem("g1", g1);
		main.addItem("footer", footer);
	}
}
