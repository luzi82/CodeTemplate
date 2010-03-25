package guri.codetemplate.standardmodule;

import org.w3c.dom.Element;

import guri.codetemplate.CodeTemplateModule;

public class HelloWorld implements CodeTemplateModule {

	public String parse(Element element) {
		if (element.getNodeName().equals("helloworld"))
			return "HelloWorld from Module!!!";
		else
			return null;
	}

}
