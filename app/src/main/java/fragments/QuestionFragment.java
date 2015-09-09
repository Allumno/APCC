package fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import alpha.apcc.R;
import variables.Actions;
import variables.ExercisePersistence;
import variables.ExerciseResources;
import variables.Layouts;
import items.GroupManager;
import logic.QuestionFragmentLogic;

public class QuestionFragment extends FragmentTemplate {
	private int turn = 0;
	private ArrayList<String> selected = null;
	private QuestionFragmentLogic logic;

	public QuestionFragment() {
		ExercisePersistence current = ExerciseResources.getList().getCurrent();

		if (current.getFile().exists()) {
			logic = new QuestionFragmentLogic(this, current.getFile());
		}

		setLay(Layouts.QUESTION);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_question, container, false);

		return view;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putSerializable("LAYOUT", getLay());
		outState.putInt("QUESTION_TURN", logic.getTurn());
		outState.putStringArrayList("QUESTION_SELECTED", logic.getSelected());
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		logic.setup();

		//  Loads saved data
		if (savedInstanceState == null) {
			turn        = getArguments().getInt("QUESTION_TURN", 0);
			selected    = getArguments().getStringArrayList("QUESTION_SELECTED");
		}
		else {
			turn        = savedInstanceState.getInt("QUESTION_TURN", 0);
			selected    = savedInstanceState.getStringArrayList("QUESTION_SELECTED");
		}
	}

	@Override
	public void onPause() {
		super.onPause();

		getArguments().putSerializable("LAYOUT", getLay());

		if (logic != null) {
			getArguments().putInt("QUESTION_TURN", logic.getTurn());
			getArguments().putStringArrayList("QUESTION_SELECTED", logic.getSelected());
		}
	}

	@Override
	public void onStart() {
		super.onStart();

		if (selected != null) {
			logic.setSelected(selected);
		}

		logic.setTurn(turn);
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
