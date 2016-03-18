package logic;

import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import alpha.apcc.ExerciseAct;
import alpha.apcc.R;
import variables.Actions;
import variables.Configurations;
import variables.Directions;
import variables.ExerciseResources;
import data.MetaData;
import files.MetaDataLoader;
import fragments.common.FragmentTemplate;
import items.GroupManager;
import items.ListItem;

public class FileDialogFragmentLogic {
	private FragmentTemplate frag;
	private View view;

	private ListView list_view;
	private Handler mainHandler;
	private GroupManager group;

	private MetaData data;

	private TextView dir_view;
	private Button up_button;
	private Button down_button;
	private Button left_button;
	private FileDialogAdapter adapter;
	private ExerciseAct act;


	public FileDialogFragmentLogic(FragmentTemplate frag) {
		this.frag = frag;
		loadData();
	}

	/**
	 * Setup of the view, data, etc.
	 */
	public void setup() {
		this.view = frag.getView();
		this.act  = (ExerciseAct) frag.getActivity();
		viewSetup();
	}

	/**
	 * Loads the initial data.
	 */
	public void loadData(){
		MetaDataLoader loader = new MetaDataLoader(Configurations.METADATA_PATH);
		loader.open();
		data = loader.load();
		loader.close();
	}

	/**
	 * Views setup.
	 */
	public void viewSetup() {
		left_button     = (Button)      view.findViewById(R.id.left_button);
		list_view       = (ListView)    view.findViewById(R.id.pathListView);


		adapter = new FileDialogAdapter(data);
		list_view.setAdapter(adapter);

		list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (!Configurations.swipeToggle) {
					System.out.println("entrei!!!");
				}
			}
		});

		refreshViews();
	}

	/**
	 * Sets the handler responsible for processing action requests.
	 * @param mainHandler
	 *      Handler from the main activity/exercise.
	 */
	public void setMainHandler(Handler mainHandler) {
		this.mainHandler = mainHandler;
		group.setHandler(this.mainHandler);
	}

	/**
	 * Gets the handler responsible for processing action requests.
	 * @return
	 *      Handler from the main activity/exercise.
	 */
	public Handler getMainHandler() {
		return mainHandler;
	}

	public String getCurrentPath() {
		return Configurations.current_path;
	}

	public void setCurrentPath(String path) {
		Configurations.current_path = path;
	}

	/**
	 * Acts according to the given action.
	 * @param action
	 *      The action to be executed.
	 */
	public void actionHandler(Actions action, int arg1, int arg2) {
		switch (action) {
			case BT_UP:
				scroll(Directions.UP);
				break;
			case BT_DOWN:
				scroll(Directions.DOWN);
				break;
			case BT_LEFT:
				act.logic.stop();
				adapter.decrementDepth();
				act.logic.swipeSetup();
				act.logic.start();
				break;
			case LIST_ITEM_SELECT:
				act.logic.stop();
				if(adapter.incrementDepth(arg1)) {
					act.changeLay(ExerciseResources.getList().getCurrent().getLayout());
					return;
				}
				act.logic.swipeSetup();
				act.logic.start();
				break;
		}
	}

	/**
	 * Converts a String array to an ArrayList of Strings.
	 * @param array
	 *      Array filled with Strings.
	 * @return
	 *      An ArrayList with the same strings.
	 *      Null if the given array is NULL.
	 */
	public ArrayList<String> arrayToArrayList(File[] array) {
		if (array != null) {
			ArrayList<String> list = new ArrayList<String>(array.length);
			for (File file : array) {
				list.add(file.getName());
			}
			return list;
		}

		return null;
	}

	/**
	 * Scrolls the view in the given direction.
	 * @param dir
	 *      Direction in which the view is to be scrolled.
	 */
	public void scroll(Directions dir) {
		int offset = 1;
		int yTop = list_view.getFirstVisiblePosition();

		switch (dir) {
			case UP:
				list_view.setSelection(yTop - offset);
				break;
			case DOWN:
				list_view.setSelection(yTop + offset);
				break;
		}

		list_view.computeScroll();
	}

	/**
	 * Refreshes the group with the views used by swipe.
	 */
	public void refreshViews() {
		group = new GroupManager(mainHandler);

//		group.addDirectionBt("upBt", up_button, Actions.BT_UP, Directions.UP);
//		group.addDirectionBt("downBt", down_button, Actions.BT_DOWN, Directions.DOWN);
		group.addDirectionBt("leftBt", left_button, Actions.BT_LEFT, Directions.LEFT);

		for(int i = 0; i < adapter.getCount(); i++) {
			group.addItemInterface("li"+i, new ListItem(list_view, i, mainHandler));
		}
	}

	public GroupManager getViews() {
		refreshViews();
		return group;
	}
}
