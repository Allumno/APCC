package fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import alpha.apcc.R;
import constants.Actions;
import constants.ExercisePersistence;
import constants.ExerciseResources;
import constants.Layouts;
import data.ImageTextData;
import items.GroupManager;
import logic.ImageQuestionFragmentLogic;

public class ImageQuestionFragment extends FragmentTemplate {
	private int turn = 0;
//	private ArrayList<ImageTextData> selected = null;
	private ArrayList<String> selected = null;
	private ImageQuestionFragmentLogic logic;
	private String path;

	public ImageQuestionFragment() {
		ExercisePersistence current = ExerciseResources.getList().getCurrent();

		if (current.getFile().exists()) {
			logic = new ImageQuestionFragmentLogic(this, current.getFile());
			logic.loadData();
		}

		setLay(current.getLayout());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(getLayoutResource(), container, false);

		return view;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putSerializable("LAYOUT", getLay());
		outState.putInt("IMAGE_QUESTION_TURN", logic.getTurn());
		outState.putStringArrayList("IMAGE_QUESTION_SELECTED", logic.getSelected());
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		logic.setup();

		//  Loads saved data
		if (savedInstanceState == null) {
			turn        = getArguments().getInt("IMAGE_QUESTION_TURN", 0);
			selected    = getArguments().getStringArrayList("IMAGE_QUESTION_SELECTED");
		}
		else {
			turn        = savedInstanceState.getInt("IMAGE_QUESTION_TURN", 0);
			selected    = savedInstanceState.getStringArrayList("IMAGE_QUESTION_SELECTED");
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		getArguments().putSerializable("LAYOUT", getLay());

		if (logic != null) {
			getArguments().putInt("IMAGE_QUESTION_TURN", logic.getTurn());
			getArguments().putStringArrayList("IMAGE_QUESTION_SELECTED", logic.getSelected());
		}
	}

	@Override
	public void onStart() {
		super.onStart();

		if (selected != null) {
			logic.setSelected(selected);
		}

		logic.setTurn(turn);

		if(selected != null) {
			selected.clear();
		}
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
