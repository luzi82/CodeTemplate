package guri.codetemplate.standardmodule;

import org.w3c.dom.Element;

import guri.codetemplate.CodeTemplateModule;
import guri.codetemplate.CodeTemplateModuleContainer;

public class CompareSwitchModule implements CodeTemplateModule {

	public String parse(Element element) {
		if (!element.getNodeName().equals("if"))
			return null;
		Element lhsElement = Util.getUniqueChildElementInNodeName(element,
				"lhs");
		Element rhsElement = Util.getUniqueChildElementInNodeName(element,
				"rhs");
		String lhsString = CodeTemplateModuleContainer.instance().parseChilds(
				lhsElement);
		String rhsString = CodeTemplateModuleContainer.instance().parseChilds(
				rhsElement);
		int diff = lhsString.compareTo(rhsString);
		if (diff == 0) {
			Element ele = Util.getUniqueChildElementInNodeName(element,
					"eq");
			if (ele != null)
				return CodeTemplateModuleContainer.instance().parseChilds(ele);
		}
		if (diff > 0) {
			Element ele = Util.getUniqueChildElementInNodeName(element,
					"lt");
			if (ele != null)
				return CodeTemplateModuleContainer.instance().parseChilds(ele);
		}
		if (diff < 0) {
			Element ele = Util.getUniqueChildElementInNodeName(element,
					"gt");
			if (ele != null)
				return CodeTemplateModuleContainer.instance().parseChilds(ele);
		}
		if (diff <= 0) {
			Element ele = Util.getUniqueChildElementInNodeName(element,
					"lte");
			if (ele != null)
				return CodeTemplateModuleContainer.instance().parseChilds(ele);
		}
		if (diff >= 0) {
			Element ele = Util.getUniqueChildElementInNodeName(element,
					"gte");
			if (ele != null)
				return CodeTemplateModuleContainer.instance().parseChilds(ele);
		}
		if (diff != 0) {
			Element ele = Util.getUniqueChildElementInNodeName(element,
					"neq");
			if (ele != null)
				return CodeTemplateModuleContainer.instance().parseChilds(ele);
		}
		return "";
	}

}
