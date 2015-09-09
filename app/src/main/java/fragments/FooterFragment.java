package fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import alpha.apcc.ExerciseAct;
import alpha.apcc.R;
import constants.Actions;
import constants.Directions;
import items.GroupManager;
import logic.FooterFragmentLogic;

public class FooterFragment extends FragmentTemplate {

	private FooterFragmentLogic logic;

	public FooterFragment() {
		logic = new FooterFragmentLogic(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Directions dir = (Directions)getArguments().get("FOOTER_DIRECTION");
		View view;

		if (dir == null) {
			dir = Directions.HORIZONTAL;
		}

		switch (dir) {
			case LEFT:
				view = inflater.inflate(R.layout.fragment_footer_back, container, false);
				break;
			case RIGHT:
				view = inflater.inflate(R.layout.fragment_footer_next, container, false);
				break;
			case HORIZONTAL:
			default:
				view = inflater.inflate(R.layout.fragment_footer_both, container, false);
				break;
		}

		setDirection(dir);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		logic.setup();
	}

	/**
	 * Handler to allow communication with the main exercise/layout
	 * @param mainHandler
	 *      The handler from that exercise.
	 */
	@Override
	public void setMainHandler(Handler mainHandler) {
		this.mainHandler = mainHandler;
		logic.setMainHandler(mainHandler);
	}

	/**
	 * Gets the items in this view to be swiped.
	 * @return
	 *      A group with the items to be swiped.
	 */
	public GroupManager getViews() {
		return logic.getViews();
	}

	/**
	 * Acts according to the given action.
	 * @param action
	 *      The action to be executed.
	 */
	@Override
	public void actionHandler(Actions action, int arg1, int arg2) {
		logic.actionHandler(action, arg1, arg2);
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
		logic.setDirection(direction);
	}
}
