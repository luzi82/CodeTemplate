package guri.codetemplate.standardmodule.datatable;

import java.util.Set;
import java.util.TreeMap;

import org.w3c.dom.Element;

import guri.codetemplate.CodeTemplateModule;
import guri.codetemplate.CodeTemplateModuleContainer;
import guri.codetemplate.standardmodule.Util;

public class DataTableModule implements CodeTemplateModule {

	static private TreeMap<String, DataTableView> tableView = new TreeMap<String, DataTableView>();

	public String parse(Element element) {
		try {
			// switch element name
			String nodeName = element.getNodeName();
			if (nodeName.equals("for_view")) {
				String srcId = element.getAttribute("src");
				String tarId = element.getAttribute("id");
				int dimension = Util.getIntAttr(element, "did", -1);
				if (srcId == null)
					throw new IllegalArgumentException();
				if (tarId == null)
					throw new IllegalArgumentException();
				if (dimension < 0)
					throw new IllegalArgumentException();
				DataTableView oriView = tableView.get(srcId);
				if (oriView == null)
					throw new IllegalArgumentException();
				if (dimension >= oriView.dimension())
					throw new IllegalArgumentException();
				Set<DataTableCoordinate> subViewCoor = oriView
						.getSubViewCoorSet(dimension);
				StringBuffer ret = new StringBuffer();
				for (DataTableCoordinate i : subViewCoor) {
					DataTableView subView = new DataTableView(oriView, i);
					addView(tarId, subView);
					ret.append(CodeTemplateModuleContainer.instance()
							.parseChilds(element));
					removeView(tarId);
				}
				return ret.toString();
			} else if (nodeName.equals("view_coor")) {
				String srcId = element.getAttribute("src");
				if (srcId == null)
					throw new IllegalArgumentException();
				int dimension = Util.getIntAttr(element, "did", -1);
				if (dimension < 0)
					throw new IllegalArgumentException();
				DataTableView oriView = tableView.get(srcId);
				if (oriView == null)
					throw new IllegalArgumentException();
				if (dimension >= oriView.dimension())
					throw new IllegalArgumentException();
				String ret = oriView.coordinate().coordinate(dimension);
				if (ret == null)
					throw new IllegalArgumentException();
				return ret;
			} else if (nodeName.equals("coor_id")) {
				String srcId = element.getAttribute("src");
				int dimension = Util.getIntAttr(element, "did", -1);
				if (srcId == null)
					throw new IllegalArgumentException();
				if (dimension < 0)
					throw new IllegalArgumentException();
				
				String coor=CodeTemplateModuleContainer.instance().parseChilds(element);
				
				DataTableView oriView = tableView.get(srcId);
				if (oriView == null)
					throw new IllegalArgumentException();
				if (dimension >= oriView.dimension())
					throw new IllegalArgumentException();
				Set<DataTableCoordinate> subViewCoor = oriView
						.getSubViewCoorSet(dimension);
				String ret=null;
				int j=0;
				for (DataTableCoordinate i : subViewCoor) {
					if(i.coordinate(dimension).equals(coor))
					{
						ret=""+j;
					}
					++j;
				}
				return ret;
			} else if (nodeName.equals("view_value")) {
				String srcId = element.getAttribute("src");
				if (srcId == null)
					throw new IllegalArgumentException();
				DataTableView oriView = tableView.get(srcId);
				if (oriView == null)
					throw new IllegalArgumentException();
				if (!oriView.coordinate().unique())
					throw new IllegalArgumentException();
				return oriView.value();
			} else if (nodeName.equals("subview")) {
				String srcId = element.getAttribute("src");
				String tarId = element.getAttribute("id");
				if (srcId == null)
					throw new IllegalArgumentException();
				if (tarId == null)
					throw new IllegalArgumentException();
				int dimension = Util.getIntAttr(element, "did", -1);
				if (dimension < 0)
					throw new IllegalArgumentException();
				DataTableView oriView = tableView.get(srcId);
				if (oriView == null)
					throw new IllegalArgumentException();
				if (dimension >= oriView.dimension())
					throw new IllegalArgumentException();
				Element dimensionValueElement = Util
						.getUniqueChildElementInNodeName(element, "coor");
				if (dimensionValueElement == null)
					throw new IllegalArgumentException();
				String dimensionValue = CodeTemplateModuleContainer.instance()
						.parseChilds(dimensionValueElement);
				String[] coor = new String[oriView.dimension()];
				coor[dimension] = dimensionValue;
				DataTableView newView = new DataTableView(oriView,
						new DataTableCoordinate(coor));
				addView(tarId, newView);
				Element scope = Util.getUniqueChildElementInNodeName(element,
						"scope");
				if (scope == null)
					throw new IllegalArgumentException();
				String ret = CodeTemplateModuleContainer.instance()
						.parseChilds(scope);
				removeView(tarId);
				return ret;
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
