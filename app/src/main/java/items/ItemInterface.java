package items;

import android.os.Handler;

public interface ItemInterface {
	public void focus(boolean change);
	public boolean select();

	public boolean isFocused();

	public void setHandler(Handler handle);
	public Handler getHandler();

	public void setVisible(boolean change);
	public boolean isVisible();

	public void setFocusable(boolean change);
	public boolean isFocusable();

	public void setAccessible(boolean change);
	public boolean isAccessible();

	public void reset();
}
