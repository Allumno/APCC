package data;

import constants.Layouts;

public class GenericData {

	private String ID;
	private String type;
	private String path;
	private Layouts layout;
	private Object data;

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Layouts getLayout() {
		return checkLayout();
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	private Layouts checkLayout() {
		if (type == null) {
			return Layouts.UNDEFINED;
		}

		if (type.equals("metadata")) {
			layout = Layouts.FILE_DIALOG;
		}
		else if (type.equals("header")) {
			layout = Layouts.HEADER;
		}
		else if (type.equals("lesson")) {
			layout = Layouts.LESSON;
		}
		else if (type.equals("language")) {
			layout = Layouts.LANGUAGE;
		}
		else if (type.equals("question_text")) {
			layout = Layouts.QUESTION;
		}
		else if (type.equals("question_text_image")) {
			layout = Layouts.IMAGE_QUESTION;
		}
		else {
			layout = Layouts.UNDEFINED;
		}

		return layout;
	}
}
