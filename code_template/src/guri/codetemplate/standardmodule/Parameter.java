package guri.codetemplate.standardmodule;

import org.w3c.dom.Element;

import guri.codetemplate.CodeTemplate;
import guri.codetemplate.CodeTemplateModule;

public class Parameter implements CodeTemplateModule {

	public String parse(Element element) {
		if (!element.getNodeName().equals("parameter"))
			return null;
		int index = Integer.parseInt(element.getAttribute("id"));
		return CodeTemplate.instance().parameter(index);
	}

}
