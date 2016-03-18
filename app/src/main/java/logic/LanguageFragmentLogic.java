package logic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Handler;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import alpha.apcc.ExerciseAct;
import alpha.apcc.R;
import variables.Actions;
import variables.Colors;
import variables.Configurations;
import variables.Fonts;
import variables.Layouts;
import variables.Messages;
import variables.ExerciseResources;
import variables.ToastMessage;
import data.LanguageData;
import files.LanguageLoader;
import files.Reader;
import fragments.common.FragmentTemplate;
import items.GroupManager;
import items.OptionButton;

public class LanguageFragmentLogic {
	private File file;
	private ExerciseAct act;
	private FragmentTemplate frag;
	private View view;

	private String path;
	private LanguageData data;

	private final int MAXOPTIONS = 8;
	private int turn = 0;

	private Handler mainHandler;
	private GroupManager group;

	private TextView text_view;
	private ImageView image_view;
	private Bitmap bit;
	private int ratio;


	/**
	 * Public constructor.
	 * @param frag
	 *      Fragment instance to allow UI access.
	 */
	public LanguageFragmentLogic(FragmentTemplate frag) {
		this.frag = frag;
		this.path = "language_test.json";
	}

	/**
	 * Public constructor.
	 * @param frag
	 *      Fragment instance to allow UI access.
	 * @param path
	 *      Path string where the data is stored.
	 */
	public LanguageFragmentLogic(FragmentTemplate frag, String path) {
		this.frag = frag;
		this.path = path;
	}

	/**
	 * Public constructor.
	 * @param frag
	 *      Fragment instance to allow UI access.
	 * @param file
	 *      File where the data is stored.
	 */
	public LanguageFragmentLogic(FragmentTemplate frag, File file) {
		this.frag = frag;
		this.file = file;
	}

	/**
	 * Setups the environment
	 */
	public void setup() {
		this.view = frag.getView();
		this.act  = (ExerciseAct) frag.getActivity();
		this.ratio = 1;

		//  Setups the views in the activity
		viewSetup();

		//  Setups and formats the text
		textSetup();

		//  Sets up the screen
		turnSetup();
	}

	public void turnSetup() {
		//  Encrypt and highlight the keywords in the text
		encrypt();

		//  If last turn ends the exercise
		if (turn >= data.getMaxTurns() && act.logic != null) {
			end();
			return;
		}

		//  Reset buttons state
		group.reset();

		//  Retrieves the options for the current turn
		ArrayList<String> options = data.getOptions(turn);

		for (int i = 0; i < MAXOPTIONS; i++) {
			String id = "opt" + i;
			OptionButton it = (OptionButton) group.getItem(id);

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
	}

	/**
	 * Skips to the next turn.
	 */
	public void nextTurn() {
		try {
			Thread.sleep(Configurations.TURN_WAIT_TIME);
		} catch (InterruptedException e) {
		}

		//  act.logic is put on pause
		act.logic.pause();

		//  Sets up the turn
		turnSetup();

		//  Resets the act.logic
		act.logic.reset();

		//  Turn off pause
		act.logic.resume();
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
	 * Using the original text:
	 *  Encrypts the keywords from the current turn forward.
	 *  Highlights the already found keywords.
	 */
	public void encrypt() {
		String txt = data.getText();
		String encrypt;

		for (int i = turn; i < data.getMaxTurns(); i++) {
			encrypt = "** " + (i+1) +" **";
			txt = txt.replace(data.getCorrect(i), encrypt);
		}

		txt = Html.toHtml(new SpannableStringBuilder(txt));

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
	 * Responsible for loading the necessary data.
	 */
	public void loadData() {
		if (file == null) {
			return;
		}

		Reader reader;
		reader = new Reader(file);
		reader.open();

		LanguageLoader loader = new LanguageLoader(reader.getReader());

		data = loader.load();
		reader.close();

		//  Loads and scales down the image of the lesson
		if (data.getImgPath() != null) {
			File file = ExerciseResources.getResourceFiles().get(data.getImgPath());
			bit = BitmapFactory.decodeFile(file.getAbsolutePath());

			if (bit.getHeight() > bit.getWidth()) {
				ExerciseResources.getList().getCurrent().setLayout(Layouts.LANGUAGE_Y);
				ExerciseResources.getList().getCurrent().setChanged(true);
			}
		}
	}

	/**
	 * Responsible for the setup of the views in the activity.
	 */
	private void viewSetup() {
		//  Buttons and item views setup
		image_view = (ImageView) view.findViewById(R.id.imageView);

		group = new GroupManager(mainHandler);
		TextView vi;

		//  Group to be added to the act.logic list
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

		vi = (TextView)view.findViewById(R.id.opt4);
		vi.setTypeface(Fonts.KOMIKA);
		group.addOptionBt("opt4", vi);

		vi = (TextView)view.findViewById(R.id.opt5);
		vi.setTypeface(Fonts.KOMIKA);
		group.addOptionBt("opt5", vi);

		vi = (TextView)view.findViewById(R.id.opt6);
		vi.setTypeface(Fonts.KOMIKA);
		group.addOptionBt("opt6", vi);

		vi = (TextView)view.findViewById(R.id.opt7);
		vi.setTypeface(Fonts.KOMIKA);
		group.addOptionBt("opt7", vi);

		//  Insert the initial data to the views
		text_view = (TextView) view.findViewById(R.id.textView);
		text_view.setTypeface(Fonts.KOMIKA);
		text_view.setText(data.getText());

		//  Set the image
		if (bit != null) {
			bit = scaleDown(bit, ratio);
			image_view.setImageBitmap(bit);
		}
		else {
			image_view.setVisibility(View.GONE);
		}
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
	 * Scales down a given image (by 1/x of the screen height).
	 * @param image
	 *      The image to be resized.
	 * @return
	 *      The scaled down image.
	 */
	private Bitmap scaleDown(Bitmap image, int x) {
		Display display = frag.getActivity().getWindowManager().getDefaultDisplay();
		Point point = new Point();
		display.getSize(point);

		float maxImageSize = point.y/x;

		float ratio = Math.min(
				maxImageSize / image.getWidth(),
				maxImageSize / image.getHeight());
		int width = Math.round(ratio * image.getWidth());
		int height = Math.round(ratio * image.getHeight());

		Bitmap newBitmap = Bitmap.createScaledBitmap(image, width,
				height, true);
		return newBitmap;
	}
}
