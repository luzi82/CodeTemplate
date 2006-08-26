package guri.codetemplate;

import org.w3c.dom.Element;

public interface CodeTemplateModule {

	/**
	 * Parse Element.
	 * 
	 * @param element
	 *            the element to be parse.
	 * @return string from the element, null if nothing to be parsed.
	 */
	public String parse(Element element);

}
