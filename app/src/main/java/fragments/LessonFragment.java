package fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import alpha.apcc.R;
import constants.Actions;
import constants.ExercisePersistence;
import constants.ExerciseResources;
import constants.Layouts;
import items.GroupManager;
import logic.LessonFragmentLogic;

public class LessonFragment extends FragmentTemplate {
	private int index = -1;
	private LessonFragmentLogic logic;

	public LessonFragment() {
		ExercisePersistence current = ExerciseResources.getList().getCurrent();

		if (current.getFile().exists()) {
			logic = new LessonFragmentLogic(this, current.getFile());
			logic.loadData();
		}

		setLay(current.getLayout());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(getLayoutResource(), container, false);

		if (savedInstanceState != null) {
			index = savedInstanceState.getInt("LESSON_INDEX", -1);
		}

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		logic.setup();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putInt("LESSON_INDEX", index);
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
	 * Handler to allow communication with the main exercise/layout
	 * @param mainHandler
	 *      The handler from that exercise.
	 */
	public void setMainHandler(Handler mainHandler) {
		this.mainHandler = mainHandler;
		logic.setMainHandler(mainHandler);
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
}
