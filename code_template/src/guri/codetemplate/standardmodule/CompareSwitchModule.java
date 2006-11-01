package guri.codetemplate.standardmodule;

import org.w3c.dom.Element;

import guri.codetemplate.CodeTemplateModule;
import guri.codetemplate.CodeTemplateModuleContainer;

public class CompareSwitchModule implements CodeTemplateModule {

	public String parse(Element element) {
		if (!element.getNodeName().equals("compare-switch"))
			return null;
		Element lhsElement = Util.getUniqueChildElementInNodeName(element,
				"compare-switch:lhs");
		Element rhsElement = Util.getUniqueChildElementInNodeName(element,
				"compare-switch:rhs");
		String lhsString = CodeTemplateModuleContainer.instance().parseChilds(
				lhsElement);
		String rhsString = CodeTemplateModuleContainer.instance().parseChilds(
				rhsElement);
		int diff = lhsString.compareTo(rhsString);
		if (diff == 0) {
			Element ele = Util.getUniqueChildElementInNodeName(element,
					"compare-switch:eq");
			if (ele != null)
				return CodeTemplateModuleContainer.instance().parseChilds(ele);
		}
		if (diff > 0) {
			Element ele = Util.getUniqueChildElementInNodeName(element,
					"compare-switch:lt");
			if (ele != null)
				return CodeTemplateModuleContainer.instance().parseChilds(ele);
		}
		if (diff < 0) {
			Element ele = Util.getUniqueChildElementInNodeName(element,
					"compare-switch:gt");
			if (ele != null)
				return CodeTemplateModuleContainer.instance().parseChilds(ele);
		}
		if (diff <= 0) {
			Element ele = Util.getUniqueChildElementInNodeName(element,
					"compare-switch:lte");
			if (ele != null)
				return CodeTemplateModuleContainer.instance().parseChilds(ele);
		}
		if (diff >= 0) {
			Element ele = Util.getUniqueChildElementInNodeName(element,
					"compare-switch:gte");
			if (ele != null)
				return CodeTemplateModuleContainer.instance().parseChilds(ele);
		}
		if (diff != 0) {
			Element ele = Util.getUniqueChildElementInNodeName(element,
					"compare-switch:neq");
			if (ele != null)
				return CodeTemplateModuleContainer.instance().parseChilds(ele);
		}
		return "";
	}

}
