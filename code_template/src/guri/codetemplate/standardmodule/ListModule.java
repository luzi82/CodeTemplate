package guri.codetemplate.standardmodule;

import guri.codetemplate.CodeTemplateModule;
import guri.codetemplate.CodeTemplateModuleContainer;
import guri.codetemplate.standardmodule.datatable.DataTableBase;
import guri.codetemplate.standardmodule.datatable.DataTableCoordinate;
import guri.codetemplate.standardmodule.datatable.DataTableModule;
import guri.codetemplate.standardmodule.datatable.DataTableView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

import org.w3c.dom.Element;

public class ListModule implements CodeTemplateModule {

	public String parse(Element element) {
		try {
			if (!element.getNodeName().equals("list"))
				return null;

			// table id
			String tableId = element.getAttribute("id");
			if (tableId.length() <= 0)
				throw new IllegalArgumentException();

			// get file name
			Element fileNameElement = Util.getUniqueChildElementInNodeName(
					element, "file");
			if (fileNameElement == null)
				throw new IllegalArgumentException();
			String fileName = CodeTemplateModuleContainer.instance()
					.parseChilds(fileNameElement);

			// get scope
			Element scopeElement = Util.getUniqueChildElementInNodeName(
					element, "scope");
			if (scopeElement == null)
				throw new IllegalArgumentException();

			int index = 0;
			FileInputStream fis = new FileInputStream(fileName);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			DataTableBase dtb = new DataTableBase(1);
			DecimalFormat df = new DecimalFormat("00000000");
			while (true) {
				String line = br.readLine();
				if (line == null)
					break;
				String[] coor = { df.format(index) };
				DataTableCoordinate dtc = new DataTableCoordinate(coor);
				dtb.setData(dtc, line);
				++index;
			}

			DataTableView dtv = new DataTableView(dtb);
			DataTableModule.addView(tableId, dtv);

			String ret = CodeTemplateModuleContainer.instance().parseChilds(
					scopeElement);

			DataTableModule.removeView(tableId);

			return ret;
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(-1);
			return null;
		}
	}

}
