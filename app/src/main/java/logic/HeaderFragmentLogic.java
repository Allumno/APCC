package logic;

import android.app.Fragment;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import alpha.apcc.R;
import variables.Actions;
import variables.Fonts;
import data.HeaderData;
import files.HeaderLoader;
import files.Reader;

public class HeaderFragmentLogic {
	private final Fragment frag;
	private String path;
	private View view;
	private TextView title_view;
	private TextView theme_view;
	private TextView theme_label;
	private HeaderData data;
	private Handler mainHandler;

	/**
	 * Public constructor.
	 * @param frag
	 *      Fragment instance to allow UI access.
	 */
	public HeaderFragmentLogic(Fragment frag) {
		this.frag = frag;
		this.view = frag.getView();
		this.path = null;
	}

	/**
	 * Public constructor.
	 * @param frag
	 *      Fragment instance to allow UI access.
	 */
	public HeaderFragmentLogic(Fragment frag, String path) {
		this.frag = frag;
		this.view = frag.getView();
		this.path = path;
	}

	/**
	 * Setups the environment.
	 */
	public void setup() {
		this.view = frag.getView();

		//  Data loading
		loadData();

		//  Setups the views in the activity
		viewSetup();
	}

	/**
	 * Responsible for preparing the necessary data.
	 */
	private void loadData() {
		if (path == null) {
			return;
		}

		Reader reader = new Reader(path);
		reader.open();
		HeaderLoader loader = new HeaderLoader(reader.getReader());

		data = loader.load();
		reader.close();
	}

	/**
	 * Responsible for the setup of the views in the activity.
	 */
	private void viewSetup() {
		//  Labels, titles, etc. setup
		title_view  =   (TextView) view.findViewById(R.id.titleView);
		theme_view  =   (TextView) view.findViewById(R.id.themeView);
		theme_label =   (TextView) view.findViewById(R.id.theme_label);

		//  Change the font and size used by the views
		theme_view.setTypeface(Fonts.KOMIKA);

		//  Makes the views visible
		title_view.setVisibility(View.VISIBLE);
		theme_view.setVisibility(View.VISIBLE);
		theme_label.setVisibility(View.VISIBLE);

		//  Insert the initial data to the views
		if (data != null) {
			title_view.setText(data.getCategory());
			theme_view.setText(data.getTheme());
		}
		//  Makes the fields invisible
		else {
			title_view.setVisibility(View.INVISIBLE);
			theme_view.setVisibility(View.INVISIBLE);
			theme_label.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * Handler to allow communication with the main exercise/layout
	 * @param mainHandler
	 *      The handler from that exercise.
	 */
	public void setMainHandler(Handler mainHandler) {
		this.mainHandler = mainHandler;
	}

	/**
	 * Acts according to the given action.
	 * @param action
	 *      The action to be executed.
	 */
	public void actionHandler(Actions action, int arg1, int arg2) {
		//  No actions to process for this fragment (at the moment)
		switch (action) {
			default:
				break;
		}
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
		loadData();
	}
}
