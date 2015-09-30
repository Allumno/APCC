package logic;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import alpha.apcc.R;
import variables.Actions;
import variables.Configurations;
import variables.Directions;
import variables.ExerciseResources;
import variables.ExercisePersistence;
import variables.Layouts;
import data.MetaDataAssignment;
import fragments.FileDialogFragment;
import fragments.FooterFragment;
import fragments.FragmentTemplate;
import fragments.HeaderFragment;
import fragments.ImageQuestionFragment;
import fragments.LanguageFragment;
import fragments.LessonFragment;
import fragments.QuestionFragment;
import items.BackgroundView;
import items.GroupManager;
import runner.FileDialogGroupStrat;
import runner.ImageQuestionGroupStrat;
import runner.LangUnitStrat;
import runner.LessonUnitStrat;
import runner.QuestionLineStrat;
import runner.SwipeRunner;
import runner.SwipeStrategyInterface;

public class ExerciseLogic {
	protected SwipeStrategyInterface strat;
	protected SwipeRunner runner;
	protected Handler handler;

	protected FragmentTemplate header;
	protected FragmentTemplate body;
	protected FragmentTemplate footer;

	protected GroupManager group;

	protected Layouts lay;
	protected Activity act;

	protected MetaDataAssignment assignment;
	protected String path;
	protected ExerciseResources exerciseResources;
	protected ExerciseFlow list;

	protected Bundle headerBundle;
	protected Bundle bodyBundle;
	protected Bundle footerBundle;

	public Bundle savedBundle;


	public ExerciseLogic(Activity act) {
		this.act = act;
		this.lay = Configurations.INITIAL_SCREEN;

		this.group = new GroupManager(null);

		this.header = null;
		this.body = null;
		this.footer = null;

		this.runner = null;
		this.strat = null;

		this.bodyBundle    = new Bundle();
		this.footerBundle  = new Bundle();
		this.headerBundle  = new Bundle();
		this.savedBundle   = new Bundle();

		Configurations.swipeToggle = true;
	}

	public void setup() {
		//  Handler
		handlerSetup();

		//  Set up a onClick listener in the background
		backgroundListener();

		//  ----    Set body fragment
			bodyCreate();

		//  ----    Set header fragment
			headerCreate();

		//  ----    Set footer fragment
			footerCreate();
	}

	/**
	 * Responsible to setup the handler behaviour and actions.
	 */
	public void handlerSetup() {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Actions action = (Actions)msg.obj;
				switch (action) {
					case SW_NEXT:
						//  Skips to the next step in the swipe.
						//  Swipe runner responsible for this.
						runner.nextStep();
						break;
					case SW_SELECT:
						//  Selects the current item.
						//  Item listeners responsible for this.
						runner.select();
						break;
					default:
						//  Remaining actions are processed accordingly by each fragment.
						body.actionHandler(action, msg.arg1, msg.arg2);
						header.actionHandler(action, msg.arg1, msg.arg2);
						footer.actionHandler(action, msg.arg1, msg.arg2);
						break;
				}
			}
		};
	}

	/**
	 * Set up the background to receive clicks.
	 */
	private void backgroundListener() {
		BackgroundView background = new BackgroundView(act.findViewById(R.id.layout_exercise), handler);
	}

	/**
	 * Initiates the swipe strategy to be used.
	 */
	public void swipeSetup() {
		header.setMainHandler(handler);
		body.setMainHandler(handler);
		footer.setMainHandler(handler);

		group.clear();
		group.addGroupManager(header.getViews());
		group.addGroupManager(body.getViews());
		group.addGroupManager(footer.getViews());

		strat.setup(group);

		if (runner != null) {
			runner.stop();
		}

		runner = new SwipeRunner(strat, handler, Configurations.START_DELAY, Configurations.time_step);
	}

	private void headerSetup() {
		header = new HeaderFragment();
		header.setArguments(headerBundle);
	}

	/**
	 * Creates the header fragment.
	 */
	public void headerCreate() {
		if (header == null) {
			headerSetup();

			//  Add the header Fragment
			act.getFragmentManager().beginTransaction().add(R.id.fragment_header, header).commit();
		}
	}

	/**
	 * Changes the content of the header fragment container to another header fragment.
	 * @param transaction
	 *      The fragment transaction in which this operation will occur.
	 */
	public FragmentTransaction headerChange(FragmentTransaction transaction) {
		headerSetup();

		transaction.replace(R.id.fragment_header, header);
		return transaction;
	}

	/**
	 * Setup of the body.
	 */
	private void bodySetup() {
		bodyBundle.clear();
		headerBundle.clear();

		ExercisePersistence header = null;
		ExercisePersistence content = null;

		if(list != null && list.isSet()) {
			header = list.getHeader();
			content = list.getCurrent();

			this.lay = content.getLayout();
			this.path = exerciseResources.getPath();

			switch (lay) {
				case LESSON:
				case LESSON_Y:
					bodyBundle.putString("LESSON_PATH",
							content.getPath());
					this.body = new LessonFragment();
					this.strat = new LessonUnitStrat();
					break;
				case QUESTION:
					bodyBundle.putString("QUESTION_PATH",
							content.getPath());
					this.body = new QuestionFragment();
					this.strat = new QuestionLineStrat();
					break;
				case IMAGE_QUESTION:
				case IMAGE_QUESTION_Y:
					bodyBundle.putString("IMAGE_QUESTION_PATH",
							content.getPath());
					this.body = new ImageQuestionFragment();
					this.strat = new ImageQuestionGroupStrat();
					break;
				case LANGUAGE:
				case LANGUAGE_Y:
					bodyBundle.putString("LANGUAGE_PATH",
							content.getPath());
					this.body = new LanguageFragment();
					this.strat = new LangUnitStrat();
					break;
				default:
					this.body = new FileDialogFragment();
					this.strat = new FileDialogGroupStrat();
					break;
			}
		}
		else {
			this.body = new FileDialogFragment();
			this.strat = new FileDialogGroupStrat();
		}


		if (header != null && lay != Layouts.FILE_DIALOG) {
			headerBundle.putString("HEADER_PATH", header.getPath());
		}

		//  Sets the arguments
		this.body.setArguments(bodyBundle);
	}

	/**
	 * Creates the body fragment.
	 */
	public void bodyCreate() {
		if (body == null) {
			bodySetup();

			//  Add the body Fragment
			act.getFragmentManager().beginTransaction().add(R.id.fragment_body, body).commit();
		}
	}

	/**
	 * Changes the content of the body fragment container to another header fragment.
	 * @param transaction
	 *      The fragment transaction in which this operation will occur.
	 */
	public FragmentTransaction bodyChange(FragmentTransaction transaction) {
		exerciseResources = ExerciseResources.getInstance();
		list = ExerciseResources.getList();
		bodySetup();

		transaction.replace(R.id.fragment_body, body);
		return transaction;
	}

	/**
	 * Setup of the footer.
	 */
	private void footerSetup() {
		footer = new FooterFragment();

		switch (lay) {
			case LESSON:
			case LESSON_Y:
				footerBundle.putSerializable("FOOTER_DIRECTION", Directions.HORIZONTAL);
				break;
			default:
				footerBundle.putSerializable("FOOTER_DIRECTION", Directions.LEFT);
				break;
		}

		footer.setArguments(footerBundle);
	}

	/**
	 * Creates the footer fragment.
	 */
	public void footerCreate() {
		if (footer == null) {
			footerSetup();

			//  Add the footer Fragment
			act.getFragmentManager().beginTransaction().add(R.id.fragment_footer, footer).commit();
		}
	}

	/**
	 * Changes the content of the footer fragment container to another header fragment.
	 * @param transaction
	 *      The fragment transaction in which this operation will occur.
	 */
	public FragmentTransaction footerChange(FragmentTransaction transaction) {
		footerSetup();

		transaction.replace(R.id.fragment_footer, footer);
		return transaction;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	/**
	 * Returns the instance of header fragment.
	 * @return
	 *      An instance of FragmentTemplate containing the header Fragment.
	 */
	public FragmentTemplate getHeader() {
		return header;
	}

	/**
	 * Returns the instance of body fragment.
	 * @return
	 *      An instance of FragmentTemplate containing the body Fragment.
	 */
	public FragmentTemplate getBody() {
		return body;
	}

	/**
	 * Returns the instance of footer fragment.
	 * @return
	 *      An instance of FragmentTemplate containing the footer Fragment.
	 */
	public FragmentTemplate getFooter() {
		return footer;
	}

	/**
	 * Returns the current body layout.
	 * @return
	 *      The current layout flag.
	 */
	public Layouts getLay() {
		return lay;
	}

	/**
	 * Manually sets the flag the current layout.
	 * @param lay
	 *      The flag of the layout.
	 */
	public void setLay(Layouts lay) {
		this.lay = lay;
	}

	/**
	 * Sets the layout to be used.
	 * @param lay
	 *      The flag of the layout to be used.
	 */
	public void changeLay(Layouts lay) {
		setLay(lay);

		FragmentTransaction transaction;
		transaction = act.getFragmentManager().beginTransaction();
		transaction = bodyChange(transaction);
		transaction = headerChange(transaction);
		transaction = footerChange(transaction);
		transaction = transaction.addToBackStack(null);

		transaction.commit();
		act.getFragmentManager().executePendingTransactions();
	}

	/**
	 * Gets the saved bundle.
	 * @return
	 *      A bundle instance containing the saved information.
	 */
	public Bundle getSavedBundle() {
		return savedBundle;
	}

	/**
	 * Sets the bundle with the relevant information.
	 * @param savedBundle
	 *      The bundle with the information.
	 */
	public void setSavedBundle(Bundle savedBundle) {
		if (savedBundle != null) {
			this.savedBundle = savedBundle;
		}
	}

	/**
	 * Starts the swipe.
	 */
	public void start() {
		if (Configurations.swipeToggle && runner != null) {
			runner.start();
		}
	}

	/**
	 * Ends the game.
	 */
	public void end() {
		if (runner != null) {
			runner.end(true);
		}
	}

	/**
	 * Stops the swipe.
	 */
	public void stop() {
		if (runner != null) {
			runner.stop();
		}
	}

	/**
	 * Pauses the swipe.
	 */
	public void pause() {
		if (runner != null) {
			runner.pause(true);
		}
	}

	/**
	 * Resumes the swipe.
	 */
	public void resume() {
		if (runner != null) {
			runner.pause(false);
		}
	}

	/**
	 * Resets the swipe.
	 */
	public void reset() {
		runner.reset();
	}
}
