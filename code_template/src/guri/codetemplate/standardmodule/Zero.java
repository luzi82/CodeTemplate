package guri.codetemplate.standardmodule;

import guri.codetemplate.CodeTemplateModule;
import guri.codetemplate.CodeTemplateModuleContainer;

import org.w3c.dom.Element;

public class Zero implements CodeTemplateModule {

	public String parse(Element element) {
		if (!element.getNodeName().equals("zero"))
			return null;
		CodeTemplateModuleContainer.instance().parseChilds(element);
		return "";
	}

}
