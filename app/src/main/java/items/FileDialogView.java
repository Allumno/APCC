package items;

import android.widget.ImageView;
import android.widget.TextView;

public class FileDialogView {
	private static ImageView directory;
	private static ImageView file;
	private static TextView path;

	public ImageView getDirectory() {
		return directory;
	}

	public void setDirectory(ImageView directory) {
		this.directory = directory;
	}

	public ImageView getFile() {
		return file;
	}

	public void setFile(ImageView file) {
		this.file = file;
	}

	public TextView getPath() {
		return path;
	}

	public void setPath(TextView path) {
		this.path = path;
	}
}
