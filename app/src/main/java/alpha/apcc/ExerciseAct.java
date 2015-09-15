package alpha.apcc;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import variables.Actions;
import variables.Configurations;
import variables.ExerciseResources;
import variables.ExercisePersistence;
import variables.Layouts;
import logic.ExerciseLogic;

public class ExerciseAct extends Activity {

	public ExerciseLogic logic = null;
	public Bundle savedBundle = null;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//  Set layout for the activity
		setContentView(R.layout.lay_exercise);

		logic = new ExerciseLogic(this);
		logic.setup();
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		if (savedInstanceState != null) {
			savedBundle = savedInstanceState;
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (logic != null) {
			logic.stop();
			logic.handlerSetup();
			logic.swipeSetup();
			logic.setSavedBundle(savedBundle);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (logic != null) {
			logic.stop();
			this.onSaveInstanceState(logic.getSavedBundle());
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (logic != null) {
			logic.start();
		}
	}


	@Override
	public void onBackPressed() {
		//  Loads the layout on top of the list
		ExerciseResources exerciseResources = ExerciseResources.getInstance();
		ExercisePersistence content = exerciseResources.getList().back();

		//TODO currently content is always != null. Check on this later.

		if (content != null) {
			changeLay(content.getLayout());
		}
		//  Finishes the activity if there is nowhere more to go back
		else {
			finish();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.configuration, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (logic == null) {
			return super.onMenuItemSelected(featureId, item);
		}

		if (item.isChecked()) {
			item.setChecked(false);
			Configurations.swipeToggle = false;
			logic.stop();
			logic.reset();
		}
		else {
			item.setChecked(true);
			Configurations.swipeToggle = true;
			logic.start();
			logic.reset();
		}

		return true;
	}

	public void changeLay(Layouts lay) {
		if (logic != null) {
			logic.stop();
			logic.changeLay(lay);
			logic.handlerSetup();
			logic.swipeSetup();
			logic.start();
		}
	}

	public void onClick(View vi) {
		if (Configurations.swipeToggle && logic.getHandler() != null) {
			Message msg = logic.getHandler().obtainMessage(0, Actions.SW_SELECT);
			logic.getHandler().sendMessage(msg);
		}
	}
}
