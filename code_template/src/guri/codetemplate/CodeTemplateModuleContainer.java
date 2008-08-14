package guri.codetemplate;

import java.util.LinkedList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public class CodeTemplateModuleContainer {

	private LinkedList<CodeTemplateModule> moduleList = new LinkedList<CodeTemplateModule>();

	private CodeTemplateModuleContainer() {
	}

	@SuppressWarnings("unchecked")
	public void init() {
		try {
			LinkedList<String> moduleClassList = CodeTemplateGlobalConfig
					.instance().getModuleList();
			for (String i : moduleClassList) {
				Class moduleClass = Class.forName(i);
				CodeTemplateModule module = (CodeTemplateModule) moduleClass
						.newInstance();
				moduleList.add(module);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public String parse(Element element) {
		for (CodeTemplateModule module : moduleList) {
			String out = module.parse(element);
			if (out != null)
				return out;
		}
		return PARSE_ELEMENT_FAIL;
	}

	public String parseChilds(Element element) {
		StringBuffer outBuffer = new StringBuffer();
		Node child = element.getFirstChild();
		while (child != null) {
			if (child instanceof Text) {
				outBuffer.append(child.getTextContent());
			} else if (child instanceof Element) {
				outBuffer.append(parse((Element) child));
			}
			child = child.getNextSibling();
		}
		return outBuffer.toString();
	}

	// /////////////////////////////
	// SINGLETON

	private static CodeTemplateModuleContainer instance;

	public static CodeTemplateModuleContainer instance() {
		if (instance == null)
			instance = new CodeTemplateModuleContainer();
		return instance;
	}

	// /////////////////////////////////
	// CONST

	private static final String PARSE_ELEMENT_FAIL = "!!!UNPARSABLE!!!";

}
