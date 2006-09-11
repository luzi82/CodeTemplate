package guri.codetemplate;

import java.util.LinkedList;

public class CodeTemplate {

	private enum ParseState {
		KEY(null), GLOBAL_SETTING_FILE_NAME("g"), TEMPLATE_FILE_NAME("t"), ;
		String key;

		ParseState(String key) {
			this.key = key;
		}
	}

	private String globalSettingFileName;

	private String templateFileName;

	private LinkedList<String> parameterList = new LinkedList<String>();

	private CodeTemplate() {
	}

	private void exe(String[] args) {
		ParseState parseState = ParseState.KEY;
		for (String i : args) {
			switch (parseState) {
			case KEY: {
				// read param?
				if (i.length() >= 2 && i.charAt(0) == '-') {
					String key = i.substring(1);
					for (ParseState ps : ParseState.values()) {
						if (key.equals(ps.key)) {
							parseState = ps;
							break;
						}
					}
					if (parseState == ParseState.KEY) {
						throw new IllegalArgumentException(String.format(
								UNKNOW_ARG_ERROR_FORMAT, i));
					}
				} else {
					parameterList.add(i);
				}
			}
				break;
			case GLOBAL_SETTING_FILE_NAME: {
				if (globalSettingFileName == null) {
					globalSettingFileName = i;
					parseState = ParseState.KEY;
				} else
					throw new IllegalArgumentException(
							TOO_MUCH_GLOBAL_ERROR_TEXT);
			}
				break;
			case TEMPLATE_FILE_NAME: {
				templateFileName = i;
				parseState = ParseState.KEY;
			}
				break;
			}
		}
		if (parseState != ParseState.KEY)
			throw new IllegalArgumentException(ARG_NOT_END_ERROR_TEXT);
		if (templateFileName == null) {
			throw new IllegalArgumentException(NO_TEMPLATE_ERROR_TEXT);
		}
		CodeTemplateGlobalConfig.instance().init(globalSettingFileName);
		CodeTemplateModuleContainer.instance().init();
	}

	// /////////////////////////////
	// SINGLETON

	private static CodeTemplate instance;

	public static CodeTemplate instance() {
		if (instance == null)
			instance = new CodeTemplate();
		return instance;
	}

	// ////////////////////////////////////
	// MAIN

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		instance().exe(args);
	}

	// //////////////////////////////
	// warning hardcode

	public static final String TOO_MUCH_GLOBAL_ERROR_TEXT = "more than 1 -"
			+ ParseState.GLOBAL_SETTING_FILE_NAME.key + " detected";

	public static final String UNKNOW_ARG_ERROR_FORMAT = "unknown arg: %s.";

	public static final String ARG_NOT_END_ERROR_TEXT = "args not end.";

	public static final String NO_TEMPLATE_ERROR_TEXT = "no template found.";
}
