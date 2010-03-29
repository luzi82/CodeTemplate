package guri.codetemplate.standardmodule;

import guri.codetemplate.CodeTemplateModule;
import guri.codetemplate.CodeTemplateModuleContainer;

import java.util.regex.Pattern;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Replace implements CodeTemplateModule {

	public String parse(Element element) {
		try {
			if (!element.getNodeName().equals("replace"))
				return null;

			NodeList pairList = element.getElementsByTagName("pair");
			Element scopeNode = Util.getUniqueChildElementInNodeName(element,
					"scope");
			if (pairList == null || scopeNode == null)
				throw new IllegalArgumentException();

			String scope = CodeTemplateModuleContainer.instance().parseChilds(
					scopeNode);
			for (int i = 0; i < pairList.getLength(); ++i) {
				Node n = pairList.item(i);
				String from = CodeTemplateModuleContainer
						.instance()
						.parseChilds(
								Util.getUniqueChildElementInNodeName(n, "from"));
				String to = CodeTemplateModuleContainer.instance().parseChilds(
						Util.getUniqueChildElementInNodeName(n, "to"));
				to=to.replaceAll(Pattern.quote("\\"), "\\\\\\\\");
				to=to.replaceAll(Pattern.quote("$"), "\\\\$");
				scope = scope.replaceAll(Pattern.quote(from), to);
			}
			return scope;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
			throw new Error(e);
		}
	}

}
