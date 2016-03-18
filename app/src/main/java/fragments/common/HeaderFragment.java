package fragments.common;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import alpha.apcc.R;
import variables.Actions;
import items.GroupManager;
import logic.HeaderFragmentLogic;

public class HeaderFragment extends FragmentTemplate {

	private HeaderFragmentLogic logic;
	private String path;

	public HeaderFragment() {
		path = null;

		if (getArguments() != null){
			path = getArguments().getString("HEADER_PATH");
		}

		if(path != null) {
			logic = new HeaderFragmentLogic(this, path);
		}
		else {
			logic = new HeaderFragmentLogic(this);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_header, container, false);

		if (getArguments() != null && getArguments().getString("HEADER_PATH") != null){
			logic.setPath(getArguments().getString("HEADER_PATH"));
		}

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstaceState) {
		super.onActivityCreated(savedInstaceState);
		logic.setup();
	}

	/**
	 * Gets the items in this view to be swiped.
	 * @return
	 *      A group with the items to be swiped.
	 */
	public GroupManager getViews() {
		//  There are currently no items to be swiped in the the header.
		return null;
	}

	@Override
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
