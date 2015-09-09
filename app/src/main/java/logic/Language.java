package logic;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import alpha.apcc.LessonAct;
import alpha.apcc.R;
import constants.Actions;
import constants.Colors;
import constants.Fonts;
import data.LanguageData;
import files.LanguageLoader;
import files.Reader;
import items.GroupManager;
import items.OptionButton;
import runner.*;

public class Language extends ScreenAbstract {
	private TextView title_view;
	private TextView level_view;
	private TextView text_view;

	private LanguageData data;
	private final int MAXOPTIONS = 8;

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
//		strat = new LangUnitStrat();
		strat = new LangUnitStrat();
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

		//  Encrypt and highlight the keywords in the text
		encrypt();

		//  If last turn ends the exercise
		if (turn >= data.getMaxTurns()) {
				end();
			swipe.end(true);
			return;
		}

		//  Reset buttons state
		items.reset();

		//  Retrieves the options for the current turn
		ArrayList<String> options = data.getOptions(turn);

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
	 * Using the original text:
	 *  Encrypts the keywords from the current turn forward.
	 *  Highlights the already found keywords.
	 */
	public void encrypt() {
		String txt = Html.toHtml(new SpannableStringBuilder(data.getText()));
		String encrypt;

		for (int i = turn; i < data.getMaxTurns(); i++) {
			encrypt = "** " + (i+1) +" **";
			txt = txt.replace(data.getCorrect(i), encrypt);
		}

		txt = highlight(txt);

		text_view.setText(Html.fromHtml(txt));
	}

	/**
	 * Highlights the keywords already found and yet to find.
	 * @param txt
	 *      Encrypted text.
	 * @return
	 *      Text with keywords and encrypted keywords highlighted.
	 */
	private String highlight(String txt) {
		String html;
		String html1 = "<b><font color =\"";
		String html2 = "\">";
		String html3 = "</font></b>";
		String enc;
		String enc1 = "\\*\\* ";
		String enc2 = " \\*\\*";
		int color;

		for (int i = 0; i < data.getMaxTurns(); i++) {
			if (i == turn - 1) {
				color = Colors.GREEN;
			} else if (i == turn) {
				color = Colors.RED;
			} else {
				color = Colors.BLACK;
			}

			if (i < turn) {
				html = html1 + color + html2 + data.getCorrect(i) + html3;
				txt = txt.replaceAll(data.getCorrect(i), html);
			} else {
				enc = enc1 + (i + 1) + enc2;
				html = html1 + color + html2 + enc + html3;
				txt = txt.replaceAll(enc, html);
			}
		}

		return txt;
	}

	/**
	 * Formats special characters in the text displayed to be recognized by HTML converter
	 */
	private void formatText() {
		//  Replace line breaks '\n' -> <\br>
		String txt = text_view.getText().toString();
		txt = txt.replaceAll("\n", "<\\br>");

		text_view.setText(Html.fromHtml(txt));
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
				Actions act = (Actions)msg.obj;
				switch (act) {
					case SW_NEXT:
						//  Skips to the next step in the swipe.
						//  Swipe runner responsible for this.
						swipe.nextStep();
						break;
					case SW_SELECT:
						//  Selects the current item.
						//  Item listeners responsible for this.
						swipe.select();
						break;
					case MISC_NEXT_TURN:
						//  Skips to the next turn.
						//  Event successful responsible for this.
						nextTurn();
						break;
					case BT_BACK:
						changeActivity();
						break;
				}
			}
		};
	}

	/**
	 * Responsible for loading the neccessary data.
	 */
	private void loadData() {
		Reader reader = new Reader("test.json") ;
		reader.open();
		LanguageLoader loader = new LanguageLoader(reader.getReader());

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

		vi = (TextView)act.findViewById(R.id.opt4);
		vi.setTypeface(Fonts.KOMIKA);
		items.addOptionBt("opt4", vi);

		vi = (TextView)act.findViewById(R.id.opt5);
		vi.setTypeface(Fonts.KOMIKA);
		items.addOptionBt("opt5", vi);

		vi = (TextView)act.findViewById(R.id.opt6);
		vi.setTypeface(Fonts.KOMIKA);
		items.addOptionBt("opt6", vi);

		vi = (TextView)act.findViewById(R.id.opt7);
		vi.setTypeface(Fonts.KOMIKA);
		items.addOptionBt("opt7", vi);

		vi = (TextView)act.findViewById(R.id.back_button);
		vi.setTypeface(Fonts.KOMIKA);
		items.addNormalBt("back", vi, Actions.BT_BACK);


		//  Labels, titles, etc. setup
		title_view = (TextView) act.findViewById(R.id.titleView);
		level_view = (TextView) act.findViewById(R.id.levelView);
		text_view = (TextView) act.findViewById(R.id.textView);

		//  Change the font and size used by the views
		title_view.setTypeface(Fonts.CARL);
		level_view.setTypeface(Fonts.KOMIKA);
		text_view.setTypeface(Fonts.KOMIKA);

		//  Insert the initial data to the views
//		title_view.setText(data.getTitle());
//		level_view.setText(data.getAssignment());
		text_view.setText(data.getText());
	}

	/**
	 * Changes the activity back to the lesson.
	 */
	private void changeActivity() {
		Intent intent = new Intent(act, LessonAct.class);
		intent.putExtra("TURN", turn-1);
		act.startActivity(intent);
	}
}
