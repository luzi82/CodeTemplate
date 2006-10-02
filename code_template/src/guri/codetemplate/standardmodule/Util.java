package guri.codetemplate.standardmodule;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Util {

	private Util() {
	};

	// /////////////////////////////

	public static String[] TRUE_STRING = { "true", "yes", "1", "on" };

	public static String[] FALSE_STRING = { "false", "no", "0", "off" };

	public static boolean isTrue(String input, boolean defaultValue) {
		if (input == null || input.length() == 0)
			return defaultValue;
		String i = input.toLowerCase();
		for (String s : TRUE_STRING) {
			if (i.equals(s))
				return true;
		}
		for (String s : FALSE_STRING) {
			if (i.equals(s))
				return false;
		}
		return defaultValue;
	}

	public static LinkedList<Element> getAllChildElementInNodeName(Node parent,
			String nodeName) {
		LinkedList<Element> out = new LinkedList<Element>();
		Node child = parent.getFirstChild();
		while (child != null) {
			if (child instanceof Element) {
				Element childE = (Element) child;
				if (childE.getNodeName().equals(nodeName)) {
					out.add(childE);
				}
			}
			child = child.getNextSibling();
		}
		return out;
	}

	public static Element getUniqueChildElementInNodeName(Node parent,
			String nodeName) {
		LinkedList<Element> list = getAllChildElementInNodeName(parent,
				nodeName);
		return (list.size() == 1) ? list.getFirst() : null;
	}

	public static Element getElement(String fileName) {
		try {
			FileInputStream fis = new FileInputStream(fileName);
			Element out = getElement(fis);
			fis.close();
			return out;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Error(e);
		}
	}

	public static Element getElement(InputStream is) {
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory
					.newDocumentBuilder();
			return documentBuilder.parse(is).getDocumentElement();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Error(e);
		}
	}

}
