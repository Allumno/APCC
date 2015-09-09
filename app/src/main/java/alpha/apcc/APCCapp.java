package alpha.apcc;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;

public class APCCapp extends Application {
	private static Context context;
	private static AssetManager assets;

	@Override
	public void onCreate() {
		super.onCreate();
		context = this;
		assets = this.getAssets();
	}

	public static Context getContext() {
		return context;
	}

	public static AssetManager getAssetManager() {
		return assets;
	}
}
