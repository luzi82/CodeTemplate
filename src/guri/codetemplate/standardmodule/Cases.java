package guri.codetemplate.standardmodule;

import org.w3c.dom.Element;

import guri.codetemplate.CodeTemplateModule;
import guri.codetemplate.CodeTemplateModuleContainer;

public class Cases implements CodeTemplateModule {

	public String parse(Element element) {
		if (element.getNodeName().equals("uppercase"))
			return CodeTemplateModuleContainer.instance().parseChilds(element)
					.toUpperCase();
		else if (element.getNodeName().equals("lowercase"))
			return CodeTemplateModuleContainer.instance().parseChilds(element)
					.toLowerCase();
		return null;
	}

}
