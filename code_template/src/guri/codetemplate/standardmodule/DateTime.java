package guri.codetemplate.standardmodule;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.w3c.dom.Element;

import guri.codetemplate.CodeTemplateModule;

public class DateTime implements CodeTemplateModule {

	@Override
	public String parse(Element element) {
		try {
			if (!element.getNodeName().equals("datetime"))
				return null;

			String formatAttr = element.getAttribute("format");
			SimpleDateFormat format = new SimpleDateFormat(formatAttr);
			Calendar now = Calendar.getInstance();
			return format.format(now.getTime());
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(-1);
			return null;
		}
	}

}
