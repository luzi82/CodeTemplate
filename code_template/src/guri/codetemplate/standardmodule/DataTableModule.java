package guri.codetemplate.standardmodule;

import java.util.TreeMap;

import org.w3c.dom.Element;

import guri.codetemplate.CodeTemplateModule;

public class DataTableModule implements CodeTemplateModule {

	static private TreeMap<String, DataTableView> tableView = new TreeMap<String, DataTableView>();

	public String parse(Element element) {
		try {
			// switch element name
			String nodeName = element.getNodeName();
			if (nodeName.equals("table:create-view")) {
				return null;
			} else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
			throw new Error(e);
		}
	}

	static public void addView(String key, DataTableView view) {
		if (key == null || view == null)
			throw new NullPointerException();
		if (tableView.containsKey(key))
			throw new IllegalArgumentException();
		tableView.put(key, view);
	}

	static public void removeView(String key) {
		if (key == null)
			throw new NullPointerException();
		if (!tableView.containsKey(key))
			throw new IllegalArgumentException();
		tableView.remove(key);
	}

}
