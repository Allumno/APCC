package logic;

import android.os.Handler;
import android.view.View;
import android.widget.Button;

import alpha.apcc.ExerciseAct;
import alpha.apcc.R;
import constants.Actions;
import constants.Directions;
import constants.ExerciseResources;
import fragments.FragmentTemplate;
import items.GroupManager;

public class FooterFragmentLogic {
	private final FragmentTemplate frag;
	private View view;
	private Button next;
	private Button back;
	private Handler mainHandler;
	private Directions direction;

	/**
	 * Public constructor.
	 * @param frag
	 *      Fragment instance to allow UI access.
	 */
	public FooterFragmentLogic(FragmentTemplate frag) {
		this.frag = frag;
		this.direction = Directions.HORIZONTAL;
		//  Use getInstance to initialize the data in case it hasn't been.
		ExerciseResources.getInstance();
	}

	/**
	 * Setups the environment.
	 */
	public void setup() {
		this.view = frag.getView();

		//  Setups the views in the activity
		viewSetup();
	}

	/**
	 * Responsible for the setup of the views in the activity.
	 */
	private void viewSetup() {
		//  Labels, titles, etc. setup
		if (getDirection() == Directions.LEFT ||
				getDirection() == Directions.HORIZONTAL) {
			back = (Button) view.findViewById(R.id.back_button);
		}

		if (getDirection() == Directions.RIGHT ||
				getDirection() == Directions.HORIZONTAL) {
			next = (Button) view.findViewById(R.id.next_button);
		}
	}

	/**
	 * Gets the items in this view to be swiped.
	 * @return
	 *      A group with the items to be swiped.
	 */
	public GroupManager getViews() {
		GroupManager group = new GroupManager(mainHandler);

		if (getDirection() == Directions.LEFT ||
				getDirection() == Directions.HORIZONTAL) {
			group.addNormalBt("back", back, Actions.BT_BACK);
		}

		if (getDirection() == Directions.RIGHT ||
				getDirection() == Directions.HORIZONTAL) {
			group.addNormalBt("next", next, Actions.BT_NEXT);
		}

		return group;
	}

	/**
	 * Handler to allow communication with the main exercise/layout
	 * @param mainHandler
	 *      The handler from that exercise.
	 */
	public void setMainHandler(Handler mainHandler) {
		this.mainHandler = mainHandler;
	}

	/**
	 * Sets the navigation directions that the footer will provide.
	 * @return
	 *      Directions.LEFT to have the back function.
	 *      Directions.RIGHT to have the next function.
	 *      Directions.HORIZONTAL to have both.
	 */
	public Directions getDirection() {
		return direction;
	}

	/**
	 * Sets the navigation directions that the footer will provide.
	 * Default is horizontal.
	 * @param direction
	 *      Directions.LEFT to have the back function.
	 *      Directions.RIGHT to have the next function.
	 *      Directions.HORIZONTAL to have both.
	 */
	public void setDirection(Directions direction) {
		if (direction != Directions.LEFT && direction != Directions.RIGHT &&
			direction != Directions.HORIZONTAL) {
			direction = Directions.HORIZONTAL;
		}

		this.direction = direction;
	}

	/**
	 * Acts according to the given action.
	 * @param action
	 *      The action to be executed.
	 */
	public void actionHandler(Actions action, int arg1, int arg2) {
		switch (action) {
			case BT_NEXT:
				//  Saves the current layout to the list
				ExerciseResources.getList().next();

				//  Loads the next layout
				((ExerciseAct)this.frag.getActivity()).changeLay(ExerciseResources.getList().getCurrent().getLayout());
				break;
			case BT_BACK:
				ExerciseResources.getList().back();

				//  Loads the next layout
				((ExerciseAct)this.frag.getActivity()).changeLay(ExerciseResources.getList().getCurrent().getLayout());

//				((ExerciseAct)frag.getActivity()).onBackPressed();
				break;
		}
	}
}
