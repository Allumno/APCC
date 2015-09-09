package logic;

import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import alpha.apcc.ExerciseAct;
import alpha.apcc.R;
import variables.Actions;
import variables.Configurations;
import variables.Fonts;
import variables.Messages;
import variables.ExerciseResources;
import variables.ToastMessage;
import data.QuestionData;
import files.QuestionLoader;
import files.Reader;
import fragments.FragmentTemplate;
import items.GroupManager;
import items.OptionButton;

public class QuestionFragmentLogic {

	private File file;
	private TextView question_view;
	private QuestionData data;
	private final int MAXOPTIONS = 4;
	private ExerciseAct act;
	private FragmentTemplate frag;
	private View view;
	private String path;
	private Handler mainHandler;
	private GroupManager group;
	private int turn = 0;
	private ArrayList<String> incorrect = null;
	private ArrayList<String> selected = null;

	/**
	 * Public constructor.
	 * @param frag
	 *      Fragment instance to allow UI access.
	 */
	public QuestionFragmentLogic(FragmentTemplate frag) {
		this.frag = frag;
		this.path = "question_test.json";

		//  Data loading
		loadData();
	}

	/**
	 * Public constructor.
	 * @param frag
	 *      Fragment instance to allow UI access.
	 * @param path
	 *      Path string where data is stored.
	 */
	public QuestionFragmentLogic(FragmentTemplate frag, String path) {
		this.frag = frag;
		this.path = path;

		//  Data loading
		loadData();
	}

	/**
	 * Public constructor.
	 * @param frag
	 *      Fragment instance to allow UI access.
	 * @param file
	 *      Path string where data is stored.
	 */
	public QuestionFragmentLogic(FragmentTemplate frag, File file) {
		this.frag = frag;
		this.file = file;

		//  Data loading
		loadData();
	}

	/**
	 * Setups the environment.
	 */
	public void setup() {
		this.view = frag.getView();
		this.act  = (ExerciseAct) frag.getActivity();

		//  Setups the views in the activity
		viewSetup();

		//  Setups and formats the text
		textSetup();
	}

	/**
	 * Responsible for preparing the necessary data.
	 */
	private void loadData() {
		//  If the file is not defined returns immediately.
		if (file == null) {
			return;
		}

		//  Loads miscelaneous data
		Reader reader = new Reader(file);
		reader.open();
		QuestionLoader loader = new QuestionLoader(reader.getReader());

		data = loader.load();
		reader.close();
	}

	/**
	 * Responsible for the setup of the views in the activity.
	 */
	private void viewSetup() {
		//  Buttons and item views setup
		group = new GroupManager(mainHandler);
		TextView vi;

		//  Items to be added to the swipe list
		vi = (TextView)view.findViewById(R.id.opt0);
		vi.setTypeface(Fonts.KOMIKA);
		group.addOptionBt("opt0", vi);

		vi = (TextView)view.findViewById(R.id.opt1);
		vi.setTypeface(Fonts.KOMIKA);
		group.addOptionBt("opt1", vi);

		vi = (TextView)view.findViewById(R.id.opt2);
		vi.setTypeface(Fonts.KOMIKA);
		group.addOptionBt("opt2", vi);

		vi = (TextView)view.findViewById(R.id.opt3);
		vi.setTypeface(Fonts.KOMIKA);
		group.addOptionBt("opt3", vi);

		//  Labels, titles, etc. setup
		question_view = (TextView) view.findViewById(R.id.question);
		//  Change the font and size used by the views
		question_view.setTypeface(Fonts.KOMIKA);
	}

	/**
	 * Acts according to the given action.
	 * @param action
	 *      The action to be executed.
	 */
	public void actionHandler(Actions action, int arg1, int arg2) {
		switch (action) {
			case MISC_NEXT_TURN:
				//  Skips to the next turn.
				//  Event successful responsible for this.
				nextTurn();
				break;
		}
	}

	/**
	 * Skips to the next turn.
	 */
	public void nextTurn() {
		try {
			Thread.sleep(Configurations.TURN_WAIT_TIME);
		} catch (InterruptedException e) {
		}

		changeContent(turn);

		//  Increase the turn value
		++turn;
	}

	public void changeContent(int newTurn) {
		//  Swipe is put on pause
		act.logic.pause();

		//  If last turn ends the exercise
		if (newTurn >= data.getMaxTurns()) {
			end();
			return;
		}

		//  Reset buttons state
		group.reset();

		//  Sets the question for the current turn
		question_view.setText(data.getQuestion(newTurn));

		//  Retrieves the options for the current turn
		ArrayList<String> options = data.getTurns(newTurn);

		for (int i = 0; i < MAXOPTIONS; i++) {
			String id = "opt" + i;
			OptionButton it = (OptionButton) group.getItem(id);

			if (i < options.size()) {
				//  Sets the option text to the correspondent buttons
				String label = options.get(i);
				it.setText(label);

				//  In case the current option is TRUE, set the flag in the button to TRUE
				if (label.equals(data.getCorrect(newTurn))) {
					it.setOption(true);
				}
				//  If the current option was already selected in a previous session its state is restored
				else if (selected != null && selected.contains(label)) {
					it.select();
				}
			}
			//  Set all unused buttons accessibility to FALSE
			else {
				it.setAccessible(false);
			}
		}

		//  Resets the swipe
		act.logic.reset();

		//  Turn off pause
		act.logic.resume();
	}

	/**
	 * Gets the items in this view to be swiped.
	 * @return
	 *      A group with the items to be swiped.
	 */
	public GroupManager getViews() {
		return group;
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

	/**
	 * Formats special characters in the text displayed to be recognized by HTML converter
	 */
	private void formatText() {
		//  Replace line breaks '\n' -> <\br>
		String txt = question_view.getText().toString();
		txt = txt.replaceAll("\n", "<\\br>");

		question_view.setText(Html.fromHtml(txt));
	}

	/**
	 * Formats the text to HTML style.
	 */
	private void textSetup() {
		formatText();
	}

	/**
	 * End of the game actions.
	 */
	public void end() {
		//  Creates a simple message to alert the end of the current game was reached
		ToastMessage.message(act, Messages.CONGRATULATIONS);
		act.logic.end();

		ExerciseResources.getList().next();

		//  Loads the next layout
		((ExerciseAct)this.frag.getActivity()).changeLay(ExerciseResources.getList().getCurrent().getLayout());
	}

	/**
	 * Returns the current turn of the exercise.
	 * @return
	 *      The current turn.
	 */
	public int getTurn() {
		return turn-1;
	}

	/**
	 * Changes the current turn to the given.
	 * NOTE: if the turn is invalid, by being negative or exceeding the
	 * maximum turns for the exercise it will reset to 0.
	 * @param turn
	 *      The turn to be changed.
	 */
	public void setTurn(int turn) {
		if (turn >= 0 && turn < data.getMaxTurns()) {
			this.turn = turn;
		}
		else {
			this.turn = 0;
		}

		nextTurn();
	}

	/**
	 * Returns a string arraylist with the current selected options.
	 * @return
	 *      A string arraylist with the options already selected.
	 */
	public ArrayList<String> getSelected() {
		ArrayList<String> incorrect = new ArrayList<String>();

		for (int i = 0; i < MAXOPTIONS; i++) {
			String id = "opt" + i;
			OptionButton it = (OptionButton) group.getItem(id);

			if (it.isSelected()) {
				incorrect.add(it.getText().toString());
			}
		}

		return incorrect;
	}

	/**
	 * Sets the an arraylist of options that are already selected.
	 * @param selected
	 *      ArrayList with options that are already selected.
	 */
	public void setSelected(ArrayList<String> selected) {
		this.selected = selected;
	}
}
