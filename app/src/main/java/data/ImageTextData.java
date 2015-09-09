package data;

import java.io.Serializable;
import java.util.ArrayList;

import variables.MediaType;

public class ImageTextData implements Serializable {

	private String text = null;
	private String imgPath = null;
	private MediaType type = MediaType.UNDEFINED;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		this.type = MediaType.TEXT;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
		this.type = MediaType.IMAGE;
	}

	public MediaType getType() {
		return type;
	}

	public void setType(MediaType type) {
		this.type = type;
	}

	public ArrayList<String> toArrayList() {
		ArrayList<String> array = new ArrayList<String>();

		if (getText() != null) {
			array.add(getText());
		}

		if (getImgPath() != null) {
			array.add(getImgPath());
		}

		return array;
	}
}
