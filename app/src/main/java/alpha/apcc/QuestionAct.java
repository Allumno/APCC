package alpha.apcc;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import constants.Layouts;
import logic.Lesson;
import logic.ScreenAbstract;
import logic.Language;
import logic.Question;

public class QuestionAct extends Activity {
	private ScreenAbstract screen = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//  Setups the screen
		Layouts lay = Layouts.QUESTION;
		changeTask(lay);
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

	public void changeTask(Layouts lay) {
		int content;

		//  If there is an screen running stop it and clear things up
		if (screen != null) {
			screen.stop();
			screen.clear();
		}

		switch (lay) {
			case LANGUAGE:
				screen = new Language();
				content = R.layout.lay_language;
				break;
			case QUESTION:
				screen = new Question();
				content = R.layout.lay_question;
				break;
			default:
				screen = new Lesson();
				content = R.layout.lay_lesson;
				break;
		}

		setContentView(content);

		//  Setups the screen
		if (screen != null) {
			int turn = getIntent().getIntExtra("TURN", -1);

			if (turn > -1) {
				screen.setTurn(turn);
			}

			screen.setup(this);
		}
	}
}
