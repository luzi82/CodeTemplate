package guri.codetemplate.standardmodule;

import java.util.regex.Pattern;

import org.w3c.dom.Element;

import guri.codetemplate.CodeTemplateModule;
import guri.codetemplate.CodeTemplateModuleContainer;

public class UnXml implements CodeTemplateModule {

	public String parse(Element element) {
		if (element.getNodeName().equals("unxml")) {
			String ret = CodeTemplateModuleContainer.instance().parseChilds(
					element);
			ret = ret.replaceAll(Pattern.quote("&"), "&amp;");
			ret = ret.replaceAll(Pattern.quote("<"), "&lt;");
			ret = ret.replaceAll(Pattern.quote(">"), "&gt;");
			return ret;
		}
		return null;
	}

}
