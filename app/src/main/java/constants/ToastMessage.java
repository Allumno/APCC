package constants;

import android.content.Context;
import android.widget.Toast;

public class ToastMessage {

	public static void message(Context act, String str) {
		Toast.makeText(act, str, Toast.LENGTH_SHORT).show();
	}
}
