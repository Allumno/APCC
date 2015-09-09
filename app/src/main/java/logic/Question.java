package logic;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import alpha.apcc.LessonAct;
import alpha.apcc.R;
import constants.Actions;
import constants.Fonts;
import data.QuestionData;
import files.QuestionLoader;
import files.Reader;
import items.GroupManager;
import items.OptionButton;
import runner.QuestionLineStrat;

public class Question extends ScreenAbstract {
	private TextView title_view;
	private TextView level_view;
	private TextView question_view;
	private QuestionData data;
	private final int MAXOPTIONS = 4;

	/**
	 * Setups the environment
	 * @param activity
	 *      Activity instance to allow UI access
	 */
	@Override
	public void setup(Activity activity) {
		act = activity;

		//  Data loading
		loadData();

		//  Setup handler responsible for UI events and interactions
		handlerSetup();

		//  Setups the views in the activity
		viewSetup();

		//  Setups and formats the text
		textSetup();

		//  Swipe strategy setup
		strat = new QuestionLineStrat();
		super.swipeSetup();

		//  Setups the first turn
		nextTurn();
	}

	/**
	 * Skips to the next turn.
	 */
	public void nextTurn() {
		//  Swipe is put on pause
		swipe.pause(true);

		//  If last turn ends the exercise
		if (turn >= data.getMaxTurns()) {
			end();
			swipe.end(true);
			return;
		}

		//  Reset buttons state
		items.reset();

		//  Sets the question for the current turn
		question_view.setText(data.getQuestion(turn));

		//  Retrieves the options for the current turn
		ArrayList<String> options = data.getTurns(turn);

		for (int i = 0; i < MAXOPTIONS; i++) {
			String id = "opt" + i;
			OptionButton it = (OptionButton) items.getItem(id);

			if (i < options.size()) {
				//  Sets the option text to the correspondent buttons
				String label = options.get(i);
				it.setText(label);

				//  In case the current option is TRUE, set the flag in the button to TRUE
				if (label.equals(data.getCorrect(turn))) {
					it.setOption(true);
				}
			}
			//  Set all unused buttons accessibility to FALSE
			else {
				it.setAccessible(false);
			}
		}

		//  Increase the turn value
		++turn;

		//  Resets the swipe
		swipe.reset();

		//  Turn off pause
		swipe.pause(false);
	}

	/**
	 * End of the game actions.
	 */
	public void end() {
		//  Creates a simple message to alert the end of the current game was reached
		Toast.makeText(act, "Parabéns! Acabaste o jogo!", Toast.LENGTH_LONG).show();
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
	 * Responsible to setup the handler behaviour and actions.
	 */
	private void handlerSetup() {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Actions action = (Actions)msg.obj;
				switch (action) {
					case SW_NEXT:
						//  Skips to the next step in the swipe.
						//  Swipe runner responsible for this.
						swipe.nextStep();
						break;
					case MISC_NEXT_TURN:
						//  Skips to the next turn.
						//  Event successful responsible for this.
						nextTurn();
						break;
					case SW_SELECT:
						//  Selects the current item.
						//  Item listeners responsible for this.
						swipe.select();
						break;
					case BT_BACK:
						changeActivity();
						break;
				}
			}
		};
	}

	/**
	 * Responsible for loading the necessary data.
	 */
	private void loadData() {
		Reader reader = new Reader("question_test.json") ;
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
		items = new GroupManager(handler);
		TextView vi;

		//  Activity window is added to allow clicks in any part of the screen
		//  It's not to be added to the swipe list
		items.addBackgroundBt("bg", act.findViewById(R.id.activity_window));
		//  Items to be added to the swipe list

		vi = (TextView)act.findViewById(R.id.opt0);
		vi.setTypeface(Fonts.KOMIKA);
		items.addOptionBt("opt0", vi);

		vi = (TextView)act.findViewById(R.id.opt1);
		vi.setTypeface(Fonts.KOMIKA);
		items.addOptionBt("opt1", vi);

		vi = (TextView)act.findViewById(R.id.opt2);
		vi.setTypeface(Fonts.KOMIKA);
		items.addOptionBt("opt2", vi);

		vi = (TextView)act.findViewById(R.id.opt3);
		vi.setTypeface(Fonts.KOMIKA);
		items.addOptionBt("opt3", vi);

		vi = (TextView)act.findViewById(R.id.back_button);
		vi.setTypeface(Fonts.KOMIKA);
		items.addNormalBt("back", vi, Actions.BT_BACK);


		//  Labels, titles, etc. setup
		title_view = (TextView) act.findViewById(R.id.titleView);
		level_view = (TextView) act.findViewById(R.id.levelView);
		question_view = (TextView) act.findViewById(R.id.question);

		//  Change the font and size used by the views
		title_view.setTypeface(Fonts.CARL);
		level_view.setTypeface(Fonts.KOMIKA);
		question_view.setTypeface(Fonts.KOMIKA);

//		//  Insert the initial data to the views
//		title_view.setText(data.getTitle());
//		level_view.setText(data.getAssignment());
	}

	/**
	 * Changes the activity.
	 */
	private void changeActivity() {
		Intent intent = new Intent(act, LessonAct.class);
		intent.putExtra("TURN", turn-1);
		act.startActivity(intent);
	}
}
