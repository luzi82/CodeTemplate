package guri.codetemplate.standardmodule;

import java.util.TreeMap;

import org.w3c.dom.Element;

import guri.codetemplate.CodeTemplateModule;

public class DataTableModule implements CodeTemplateModule {

	static private TreeMap<String, DataTableView> tableView = new TreeMap<String, DataTableView>();

	public String parse(Element element) {
		// TODO Auto-generated method stub
		return null;
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
