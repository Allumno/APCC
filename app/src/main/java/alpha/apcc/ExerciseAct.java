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
	private MenuItem swipeBt = null;

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

		float time = (float) Configurations.time_step / 1000;
		swipeBt = menu.findItem(R.id.swipeToggle);
		swipeBt.setTitle("On " + time + " s");
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
			case R.id.swipeToggle:
				if (item.isChecked()) {
					item.setChecked(false);
					Configurations.swipeToggle = false;
					logic.reset();
					logic.stop();
				}
				else {
					item.setChecked(true);
					Configurations.swipeToggle = true;
					logic.start();
					logic.reset();
				}
				break;
			case R.id.decSwipe:
				if (swipeBt.isChecked() &&
						Configurations.time_step > Configurations.MIN_TIME_STEP) {
					Configurations.time_step -= Configurations.GAP_TIME_STEP;
				}
				break;
			case R.id.incSwipe:
				if (swipeBt.isChecked() &&
						Configurations.time_step < Configurations.MAX_TIME_STEP) {
					Configurations.time_step += Configurations.GAP_TIME_STEP;
				}
				break;
			default:
				return super.onMenuItemSelected(featureId, item);
		}

		if (swipeBt.isChecked()) {
			float time = (float) Configurations.time_step / 1000;
			swipeBt.setTitle("On " + time + " s");
		}
		else {
			swipeBt.setTitle("Off");
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
