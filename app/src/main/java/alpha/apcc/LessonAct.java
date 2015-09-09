package alpha.apcc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import logic.Lesson;
import logic.ScreenAbstract;

public class LessonAct extends Activity {
	private ScreenAbstract screen = null;
	private int previous_level = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lay_lesson);

		Intent intent = getIntent();
		previous_level = intent.getIntExtra("TURN", -1);

		//  Setups the screen
		screen = new Lesson();

		screen.setTurn(previous_level);

		if (screen != null) {
			screen.setup(this);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		//  Starts the screen
		if (screen != null) {
			screen.start();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();

		//  Stops the screen
		if (screen != null) {
			screen.stop();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		//  Clears memory
		if (screen != null) {
			screen.clear();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.language, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
