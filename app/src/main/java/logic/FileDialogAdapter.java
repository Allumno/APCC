package logic;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;

import alpha.apcc.APCCapp;
import alpha.apcc.R;
import variables.Hierarchy;
import variables.Messages;
import variables.ExerciseResources;
import variables.ToastMessage;
import data.MetaData;
import data.MetaDataAssignment;

public class FileDialogAdapter extends ArrayAdapter<String> {
	private Hierarchy hierarchy;
	private ArrayList<Boolean> focusedList;

	/**
	 * Main constructor.
	 * @param metadata
	 *      Metadata object with the info read from Metadata file.
	 */
	public FileDialogAdapter(MetaData metadata) {
		super(APCCapp.getContext(), R.layout.row_filedialog);
		hierarchy = Hierarchy.getInstance();
		hierarchy.setMetadata(metadata);
		hierarchy.reset();
		setData();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param position
	 * @param convertView
	 * @param parent
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView;

		if(convertView == null) {
			rowView = LayoutInflater.from(getContext())
					.inflate(R.layout.row_filedialog, parent, false);
		}
		else {
			rowView = convertView;
		}

		Button bt = (Button) rowView.findViewById(R.id.btPath);

		String path = getItem(position);
		bt.setText(path);

		//  Changes the icon according if it is a file or a directory.
		Drawable icon;
		if (hierarchy.getDepth() == 2) {
			icon= getContext().getResources().getDrawable( R.drawable.file_icon);
		}
		else {
			icon= getContext().getResources().getDrawable( R.drawable.folder_icon);
		}

		bt.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);

		//  Change the background according if the view is focused or not.
		if(position < focusedList.size() && focusedList.get(position)) {
			bt.setBackgroundResource(R.drawable.focus_button);
		}
		else {
			bt.setBackgroundResource(R.drawable.default_button);
		}

		rowView.setFocusable(false);
		rowView.setFocusableInTouchMode(false);

		return rowView;
	}

	/**
	 * Method to focus a row in a given position.
	 * @param position
	 *      Position in the ArrayList of the row to be focused/unfocused.
	 */
	public void focus(int position, Boolean change) {
		if (position < focusedList.size()) {
			focusedList.set(position, change);
		}

		notifyDataSetChanged();
	}

	/**
	 * Resets the focused list and sets it according to the current number of elements.
	 */
	public void initFocusedList() {
		this.focusedList = new ArrayList<Boolean>();
		Collections.addAll(this.focusedList, new Boolean[super.getCount()]);
		Collections.fill(this.focusedList, Boolean.FALSE);
	}

	/**
	 * Flags all rows as not focused (FALSE).
	 */
	public void clearFocused() {
		Collections.replaceAll(focusedList, Boolean.TRUE, Boolean.FALSE);
		notifyDataSetChanged();
	}

	/**
	 * Sets the data to be presented according to the current depth level.
	 */
	public void setData() {
		super.clear();

		if (hierarchy.getCurrentList() != null) {
			super.addAll(hierarchy.getCurrentList());
			initFocusedList();
		}

		notifyDataSetChanged();
	}

	/**
	 * Advances the current depth level.
	 * @param selection
	 *      The current selection to now what slot to expand.
	 */
	public boolean incrementDepth(int selection) {
		if (hierarchy.getDepth() < hierarchy.MAX_DEPTH) {
			hierarchy.incrementDepth(selection);
		}
		else {
			MetaDataAssignment assignment = hierarchy.getAssignment(selection);
			String path = hierarchy.getPath();
			select(assignment, path);
			return true;
		}

		setData();
		return false;
	}

	/**
	 * Goes back to the previous depth level.
	 */
	public void decrementDepth() {
		if (hierarchy.getDepth() > hierarchy.MIN_DEPTH) {
			hierarchy.decrementDepth();
		}

		setData();
	}

	/**
	 * Selects the exercise and loads the information.
	 * @param assignment
	 *      The selected assignment.
	 */
	public boolean select(MetaDataAssignment assignment, String path) {
		ExerciseResources exerciseResources = ExerciseResources.getInstance();
		exerciseResources.setAssignment(assignment);
		exerciseResources.setPath(path);

		if(!exerciseResources.setup()) {
			ToastMessage.message(APCCapp.getContext(), Messages.SCREEN_SETUP_ASSIGNMENT_ERROR);
			return false;
		}
		else {
			return true;
		}
	}
}
