package logic;

import android.app.Activity;
import android.os.Handler;

import items.GroupManager;
import runner.SwipeRunner;
import runner.SwipeStrategyInterface;

public abstract class ScreenAbstract {
	protected Activity act;

	protected Handler handler;
	protected GroupManager items;

	protected SwipeStrategyInterface strat;
	protected SwipeRunner swipe;
	protected long start_delay = 1000;
	protected long time_step = 750;

	protected int turn = 0;

	/**
	 * Setups the layout, screen, etc.
	 * @param act
	 *      Instance of the activity in which it is inserted.
	 */
	public abstract void setup(Activity act);

	/**
	 * Starts the swipe.
	 */
	public void start() {
		swipe.start();
	}

	/**
	 * Ends the swipe (only goes through the footer elements).
	 */
	public void end() {
		swipe.end(true);
	}

	/**
	 * Stops the swipe.
	 */
	public void stop() {
		swipe.stop();
	}

	/**
	 * Pauses the swhipe.
	 */
	public void pause() {
		swipe.pause(true);
	}

	/**
	 * Resumes the swipe.
	 */
	public void resume() {
		swipe.pause(false);
	}

	/**
	 * Clears things.
	 */
	public void clear() {
		stop();
	}

	/**
	 * Initiates the swipe strategy to be used.
	 */
	protected void swipeSetup() {
		strat.setup(items);
		swipe = new SwipeRunner(strat, handler, start_delay, time_step);
	}

	/**
	 * Gets the current turn for this activity.
	 * @return
	 *      The current turn.
	 */
	public int getTurn() {
		return turn;
	}

	/**
	 * Sets the turn to begin with.
	 * @param turn
	 *      The beginning turn.
	 */
	public void setTurn(int turn) {
		this.turn = turn;
	}
}
