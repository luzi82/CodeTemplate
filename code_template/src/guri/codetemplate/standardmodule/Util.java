package guri.codetemplate.standardmodule;

public class Util {

	private Util() {
	};

	// /////////////////////////////

	public static String[] TRUE_STRING = { "true", "yes", "1" };

	public static String[] FALSE_STRING = { "false", "no", "0" };

	public static boolean isTrue(String input, boolean defaultValue) {
		if (input == null || input.length() == 0)
			return defaultValue;
		String i = input.toLowerCase();
		for (String s : TRUE_STRING) {
			if (i.equals(s))
				return true;
		}
		for (String s : FALSE_STRING) {
			if (i.equals(s))
				return false;
		}
		return defaultValue;
	}

}
