package fragments;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;

import alpha.apcc.R;
import constants.Actions;
import constants.Layouts;
import items.GroupManager;
import logic.FileDialogFragmentLogic;

public class FileDialogFragment extends FragmentTemplate {
	private FileDialogFragmentLogic logic;
	private File file;

	public FileDialogFragment() {
		file = Environment.getExternalStorageDirectory();
		logic = new FileDialogFragmentLogic(this);
		setLay(Layouts.FILE_DIALOG);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_file_dialog, container, false);

		return view;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putSerializable("LAYOUT", getLay());
		outState.putSerializable("FILEDIALOG_FILE", file);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		logic.setup();

		//  Loads saved data
		if (savedInstanceState == null) {
			file        = (File) getArguments().getSerializable("FILEDIALOG_FILE");
		}
		else {
			file        = (File) savedInstanceState.getSerializable("FILEDIALOG_FILE");
		}
	}

	@Override
	public void onPause() {
		super.onPause();

		getArguments().putSerializable("LAYOUT", getLay());

		if (logic != null) {
			getArguments().putSerializable("FILEDIALOG_FILE", file);
		}
	}

	@Override
	public void onStart() {
		super.onStart();
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
