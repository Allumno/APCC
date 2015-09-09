package logic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import alpha.apcc.ExerciseAct;
import alpha.apcc.R;
import variables.Actions;
import variables.Configurations;
import variables.Fonts;
import variables.Layouts;
import variables.MediaType;
import variables.Messages;
import variables.ExerciseResources;
import variables.ToastMessage;
import data.ImageQuestionData;
import data.ImageTextData;
import files.ImageQuestionLoader;
import files.Reader;
import fragments.FragmentTemplate;
import items.GroupManager;
import items.MixBt;

public class ImageQuestionFragmentLogic {

	private File file;
	private ImageView image_view;
	private TextView question_view;
	private ArrayList<ImageTextData> options;
	private ImageQuestionData data;
	private final int MAXOPTIONS = 4;
	private ExerciseAct act;
	private FragmentTemplate frag;
	private View view;
	private String path;
	private Handler mainHandler;
	private GroupManager group;
	private int turn = 0;
	private ArrayList<String> selected = null;
	private int ratioQuestion;
	private int ratioOption;

	/**
	 * Public constructor.
	 * @param frag
	 *      Fragment instance to allow UI access.
	 */
	public ImageQuestionFragmentLogic(FragmentTemplate frag) {
		this.frag = frag;
		this.path = "image_question_test.json";
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
	public ImageQuestionFragmentLogic(FragmentTemplate frag, String path) {
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
	public ImageQuestionFragmentLogic(FragmentTemplate frag, File file) {
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
		this.ratioQuestion = 1;
		this.ratioOption = 7;

//		//  Data loading
//		loadData();

		//  Setups the views in the activity
		viewSetup();

		//  Setups and formats the text
		textSetup();
	}

	/**
	 * Responsible for preparing the necessary data.
	 */
	public void loadData() {
		Reader reader = new Reader(file);
		reader.open();
		ImageQuestionLoader loader = new ImageQuestionLoader(reader.getReader());

		data = loader.load();
		reader.close();

		//  Loads and scales down the image of the lesson
		if (data.getImagePath(0) != null) {
			File file = ExerciseResources.getResourceFiles().get(data.getImagePath(0));
			Bitmap bit = BitmapFactory.decodeFile(file.getAbsolutePath());

			if (bit.getHeight() > bit.getWidth()) {
				ExerciseResources.getList().getCurrent().setLayout(Layouts.IMAGE_QUESTION_Y);
				ExerciseResources.getList().getCurrent().setChanged(true);
			}
		}
	}

	/**
	 * Loads images from a given path.
	 * @param path
	 *      The path of the image.
	 * @param ratio
	 *      The vertical ratio that the image should be scaled down (in relation to the view).
	 */
	private Bitmap loadImage(String path, int ratio) {
		if (path != null) {
			Bitmap bit;
			File file = ExerciseResources.getResourceFiles().get(path.toLowerCase());
			bit = BitmapFactory.decodeFile(file.getAbsolutePath());
			if (bit != null) {
				bit = frag.scaleDown(bit, ratio);
			}

			return bit;
		}

		return null;
	}

	/**
	 * Responsible for the setup of the views in the activity.
	 */
	private void viewSetup() {
		//  Buttons and item views setup
		group = new GroupManager(mainHandler);

		MixBt vi;
		Button txtBt;
		ImageButton imgBt;

		//  Items to be added to the swipe list
		txtBt = (Button) view.findViewById(R.id.opt0_text);
		txtBt.setTypeface(Fonts.KOMIKA);
		imgBt = (ImageButton) view.findViewById(R.id.opt0_image);
		vi = new MixBt("opt0", txtBt, imgBt, mainHandler, null);
		group.addItemInterface("opt0", vi);

		txtBt = (Button) view.findViewById(R.id.opt1_text);
		txtBt.setTypeface(Fonts.KOMIKA);
		imgBt = (ImageButton) view.findViewById(R.id.opt1_image);
		vi = new MixBt("opt1", txtBt, imgBt, mainHandler, null);
		group.addItemInterface("opt1", vi);

		txtBt = (Button) view.findViewById(R.id.opt2_text);
		txtBt.setTypeface(Fonts.KOMIKA);
		imgBt = (ImageButton) view.findViewById(R.id.opt2_image);
		vi = new MixBt("opt2", txtBt, imgBt, mainHandler, null);
		group.addItemInterface("opt2", vi);

		txtBt = (Button) view.findViewById(R.id.opt3_text);
		txtBt.setTypeface(Fonts.KOMIKA);
		imgBt = (ImageButton) view.findViewById(R.id.opt3_image);
		vi = new MixBt("opt3", txtBt, imgBt, mainHandler, null);
		group.addItemInterface("opt3", vi);

		//  Labels, titles, etc. setup
		question_view = (TextView) view.findViewById(R.id.question_view);
		question_view.setTypeface(Fonts.KOMIKA);
		image_view = (ImageView) view.findViewById(R.id.image_view);
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


//####################### CHANGE ZONE #######################

		//  Sets the question, and respective image, for the current turn
		question_view.setText(data.getQuestion(newTurn));
		String path = data.getImagePath(newTurn);
		if (path != null) {
			image_view.setVisibility(View.VISIBLE);
			image_view.setImageBitmap(loadImage(path, ratioQuestion));
		}
		//  In case there isn't an image defined it disables the view
		else {
			image_view.setVisibility(View.GONE);
		}

		//  Retrieves the options for the current turn already mixed
		options = data.getTurns(newTurn);

		for (int i = 0; i < MAXOPTIONS; i++) {
			String tag = "opt"+i;
			MixBt it = (MixBt) group.getItem(tag);

			if (i < options.size()) {
				//  Sets the option text to the correspondent buttons
				ImageTextData label = options.get(i);

				switch (label.getType()) {
					case IMAGE:
						path = label.getImgPath();
						Bitmap bit = loadImage(path, ratioOption);

						if (bit != null) {
							it.setImage(bit);
						}
						break;
					case TEXT:
						String txt = label.getText();
						it.setText(txt);
						break;
				}

				//  In case the current option is TRUE, set the flag in the button to TRUE
				if (label.equals(data.getCorrect(newTurn))) {
					it.setOption(true);
				}
				//  If the current option was already selected in a previous session its state is restored
				else if (selected != null &&
						(selected.contains(label.getText())
								|| selected.contains(label.getImgPath()))) {
					it.select();
				}
			}
			//  Set all unused buttons accessibility to FALSE by stating the type as UNDEFINED
			else {
				it.setType(MediaType.UNDEFINED);
			}
		}

//####################### CHANGE ZONE #######################

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
	 * Returns a String arraylist with the current selected options.
	 * @return
	 *      A String arraylist with the options already selected.
	 */
	public ArrayList<String> getSelected() {
		ArrayList<String> incorrect = new ArrayList();

		for (int i = 0; i < MAXOPTIONS; i++) {
			String tag = "opt"+i;
			MixBt mix = (MixBt) group.getItem(tag);

			if (mix.isSelected()) {
				incorrect.addAll(options.get(i).toArrayList());
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
