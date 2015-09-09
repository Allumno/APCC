package validators;

import constants.DataConstraints;
import constants.Layouts;
import constants.ViewTypes;
import data.HeaderData;
import data.ImageQuestionData;
import data.ImageQuestionTurnData;
import data.ImageTextData;
import data.LanguageData;
import data.LanguageTurnData;
import data.LessonData;
import data.MetaData;
import data.MetaDataAssignment;
import data.MetaDataCategory;
import data.MetaDataLevel;
import data.QuestionData;
import data.QuestionTurnData;

public class LayoutValidator {

	public static boolean validateDataIntegrity(Object data, Layouts type) {
		if (data == null) {
			return false;
		}

		System.out.println("Type: " + type);
		System.out.println("Data: " + data.toString());

		switch (type) {
			case FILE_DIALOG:
				return validateFileDialog((MetaData) data);
			case HEADER:
				return validateHeader((HeaderData) data);
			case LESSON:
				return validateLesson((LessonData) data);
			case LANGUAGE:
				return validateLanguage((LanguageData) data);
			case QUESTION:
				return validateQuestion((QuestionData) data);
			case IMAGE_QUESTION:
				return validateImageQuestion((ImageQuestionData) data);
			default:
				return false;
		}
	}

	//  ####### MAIN VALIDATORS
	private static boolean validateFileDialog(MetaData data) {
		if (data == null) {
			return false;
		}

		boolean check = true;

		check = DataConstraints.validateValue(data.getID(), ViewTypes.ID);

		for(MetaDataCategory cat : data.getCategories()) {
			check &= validateCategory(cat);
		}

		return check;
	}

	private static boolean validateHeader(HeaderData data) {
		if (data == null) {
			return false;
		}

		boolean check = true;

		check =     DataConstraints.
				validateValue(data.getID(), ViewTypes.ID);
		check &=    DataConstraints.
				validateValue(data.getCategory(), ViewTypes.CATEGORY);
		check &=    DataConstraints.
				validateValue(data.getTheme(), ViewTypes.THEME);

		return check;
	}

	private static boolean validateLesson(LessonData data) {
		if (data == null) {
			return false;
		}

		boolean check = true;

		check =     DataConstraints.
				validateValue(data.getID(), ViewTypes.ID);
		check &=    DataConstraints.
				validateValue(data.getAuthor(), ViewTypes.AUTHOR);
		check &=    DataConstraints.
				validateValue(data.getText(), ViewTypes.LESSON);
		check &=    DataConstraints.
				validateValue(data.getImgPath(), ViewTypes.DIR_PATH);

		return check;
	}

	private static boolean validateLanguage(LanguageData data) {
		if (data == null) {
			return false;
		}

		boolean check = true;

		check =     DataConstraints.
				validateValue(data.getID(), ViewTypes.ID);
		check &=    DataConstraints.
				validateValue(data.getAuthor(), ViewTypes.AUTHOR);
		check &=    DataConstraints.
				validateValue(data.getText(), ViewTypes.LANG_TEXT);

		for (LanguageTurnData turn : data.getOptions()) {
			check &= validateLanguageTurnData(turn);
		}

		return check;
	}

	private static boolean validateQuestion(QuestionData data) {
		if (data == null) {
			return false;
		}

		boolean check = true;

		check =     DataConstraints.
				validateValue(data.getID(), ViewTypes.ID);
		check &=    DataConstraints.
				validateValue(data.getAuthor(), ViewTypes.AUTHOR);

		for (QuestionTurnData turn : data.getTurns()) {
			check &= validateQuestionTurnData(turn);
		}

		return check;
	}

	private static boolean validateImageQuestion(ImageQuestionData data) {
		if (data == null) {
			return false;
		}

		boolean check = true;

		check =     DataConstraints.
				validateValue(data.getID(), ViewTypes.ID);
		check &=    DataConstraints.
				validateValue(data.getAuthor(), ViewTypes.AUTHOR);

		for (ImageQuestionTurnData turn : data.getTurns()) {
			check &= validateImageQuestionTurnData(turn);
		}

		return check;
	}



	//  ####### AUXILIARY VALIDATORS
	private static boolean validateCategory(MetaDataCategory data) {
		boolean check = true;

		check = DataConstraints.
				validateValue(data.getCategory(), ViewTypes.CATEGORY);

		for(MetaDataLevel level : data.getLevels()) {
			check &= validateLevel(level);
		}

		return check;
	}

	private static boolean validateLevel(MetaDataLevel data) {
		boolean check = true;

		check = DataConstraints.
				validateValue(data.getLevel(), ViewTypes.LEVEL);

		for(MetaDataAssignment assignment : data.getAssignments()) {
				check &= validateAssignment(assignment);
		}

		return check;
	}

	private static boolean validateAssignment(MetaDataAssignment data) {
		boolean check = true;

		check = DataConstraints.
				validateValue(data.getAssignment(), ViewTypes.ASSIGNMENT);

		check &= DataConstraints.
				validateValue(data.getHeaderPath(), ViewTypes.DIR_PATH);

		for (String str : data.getLessonsPath()) {
			check &= DataConstraints.validateValue(str, ViewTypes.DIR_PATH);
		}

		for (String str : data.getExercisesPath()) {
			check &= DataConstraints.validateValue(str, ViewTypes.DIR_PATH);
		}

		return check;
	}

	private static boolean validateLanguageTurnData(LanguageTurnData data) {
		boolean check = true;

		for (String option : data.getAllOptions()) {
			check &= DataConstraints.
					validateValue(option, ViewTypes.OPTION);
		}

		return check;
	}

	private static boolean validateQuestionTurnData(QuestionTurnData data) {
		boolean check = true;

//		check = DataConstraints.
//				validateValue(data.getImgPath(), ViewTypes.DIR_PATH);

		check = DataConstraints.
				validateValue(data.getQuestion(), ViewTypes.QUESTION);

		for (String option : data.getAllOptions()) {
			check &= DataConstraints.
					validateValue(option, ViewTypes.OPTION);
		}

		return check;
	}

	private static boolean validateImageQuestionTurnData(ImageQuestionTurnData data) {
		boolean check = true;

		check = DataConstraints.
				validateValue(data.getImgPath(), ViewTypes.DIR_PATH);

		check &= DataConstraints.
				validateValue(data.getQuestion(), ViewTypes.QUESTION);

		for (ImageTextData option : data.getAllOptions()) {
			switch (option.getType()) {
				case TEXT:
					check &= DataConstraints.
							validateValue(option.getText(), ViewTypes.OPTION);
					break;
				case IMAGE:
					check &= DataConstraints.
							validateValue(option.getImgPath(), ViewTypes.DIR_PATH);
			}
		}

		return check;
	}
}
