package guri.codetemplate.standardmodule;

import org.w3c.dom.Element;

import guri.codetemplate.CodeTemplateModule;
import guri.codetemplate.CodeTemplateModuleContainer;

public class Utf16Hex implements CodeTemplateModule {

	@Override
	public String parse(Element element) {
		try {
			if (!element.getNodeName().equals("u16hex"))
				return null;
			String child = CodeTemplateModuleContainer.instance().parseChilds(
					element);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < child.length(); ++i) {
				char c = child.charAt(i);
				if (c < 128) {
					sb.append(c);
				} else {
					sb.append("\\u");
					String u = Integer.toHexString(c);
					while (u.length() < 4) {
						u = "0" + u;
					}
					sb.append(u);
				}
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
			throw new Error(e);
		}
	}

}
