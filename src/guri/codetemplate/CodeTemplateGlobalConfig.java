package guri.codetemplate;

import java.io.File;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class CodeTemplateGlobalConfig {

	private LinkedList<String> moduleNameList = new LinkedList<String>();

	private CodeTemplateGlobalConfig() {
	}

	public void init(String filename) {
		try {
			// get xml document
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory
					.newDocumentBuilder();
			Document xmlDocument = documentBuilder.parse(new File(filename));

			// scan all module node
			Element root = xmlDocument.getDocumentElement();
			Node child = root.getFirstChild();
			while (child != null) {
				if (child instanceof Element
						&& child.getNodeName().equals(MODULE_NODE_NAME)) {
					String moduleClass = child.getTextContent();
					if (moduleClass == null)
						throw new IllegalArgumentException();
					moduleNameList.add(moduleClass);
				}
				child = child.getNextSibling();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public LinkedList<String> getModuleList() {
		return new LinkedList<String>(moduleNameList);
	}

	// /////////////////////////////
	// SINGLETON

	private static CodeTemplateGlobalConfig instance;

	public static CodeTemplateGlobalConfig instance() {
		if (instance == null)
			instance = new CodeTemplateGlobalConfig();
		return instance;
	}

	// ///////////////////////////////
	// CONST

	public static final String MODULE_NODE_NAME = "module";

}
