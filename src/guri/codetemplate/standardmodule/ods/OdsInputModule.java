package guri.codetemplate.standardmodule.ods;

import guri.codetemplate.CodeTemplateModule;
import guri.codetemplate.CodeTemplateModuleContainer;
import guri.codetemplate.standardmodule.Util;
import guri.codetemplate.standardmodule.datatable.DataTableBase;
import guri.codetemplate.standardmodule.datatable.DataTableCoordinate;
import guri.codetemplate.standardmodule.datatable.DataTableModule;
import guri.codetemplate.standardmodule.datatable.DataTableView;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.zip.ZipFile;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class OdsInputModule implements CodeTemplateModule {

	public String parse(Element element) {
		try {
			// check note name
			if (!element.getNodeName().equals("ods"))
				return null;

			// obtain table id
			String tableId = element.getAttribute("id");
			if (tableId.length() <= 0)
				throw new IllegalArgumentException();

			// get file name
			Element fileNameElement = Util.getUniqueChildElementInNodeName(
					element, "file");
			if (fileNameElement == null)
				throw new IllegalArgumentException();

			// get scope
			Element scopeElement = Util.getUniqueChildElementInNodeName(
					element, "scope");
			if (scopeElement == null)
				throw new IllegalArgumentException();

			String fileName = CodeTemplateModuleContainer.instance()
					.parseChilds(fileNameElement);

			// create buffer
			String ret = null;
			{ // scope for full inner operation

				System.gc();

				DataTableBase base = createDataTableBase(fileName);

				DataTableView view = new DataTableView(base);
				DataTableModule.addView(tableId, view);

				ret = CodeTemplateModuleContainer.instance().parseChilds(
						scopeElement);

				DataTableModule.removeView(tableId);
			}
			System.gc();

			// return
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
			throw new Error(e);
		}
	}

	static boolean emptyString(String txt) {
		return (txt == null || txt.length() == 0);
	}

	static public DataTableBase createDataTableBase(String fileName)
			throws IOException {
		// create table
		DataTableBase base = new DataTableBase(3);

		{ // scope of file reading temp obj
			// obtain document from osd file
			ZipFile file = new ZipFile(fileName);
			InputStream is = file.getInputStream(file.getEntry("content.xml"));
			Element odsContent = Util.getElement(is);
			is.close();
			file.close();

			Element bodyElement = Util.getUniqueChildElementInNodeName(
					odsContent, "office:body");
			Element spreadSheetElement = Util.getUniqueChildElementInNodeName(
					bodyElement, "office:spreadsheet");
			LinkedList<Element> tableElementL = Util
					.getAllChildElementInNodeName(spreadSheetElement,
							"table:table");

			for (Element tableElement : tableElementL) {

				// fill ods table
				OdsTable odsTable = new OdsTable();

				int intCoorY, intCoorZ;// coordinate
				intCoorZ = 0;
				LinkedList<Element> rowElementL = Util
						.getAllChildElementInNodeName(tableElement,
								"table:table-row");
				for (Element rowElement : rowElementL) {
					int rowRepeat = Util.getIntAttr(rowElement,
							"table:number-rows-repeated", 1);

					intCoorY = 0;
					LinkedList<Element> colElementL = Util
							.getAllChildElement(rowElement);
					for (Element colElement : colElementL) {
						String colNodeName = colElement.getNodeName();
						if ((!colNodeName.equals("table:table-cell"))
								&& (!colNodeName
										.equals("table:covered-table-cell")))
							continue;

						int colRepeat = Util.getIntAttr(colElement,
								"table:number-columns-repeated", 1);
						int rowSpan = Util.getIntAttr(colElement,
								"table:number-rows-spanned", 1);
						int colSpan = Util.getIntAttr(colElement,
								"table:number-columns-spanned", 1);

						// Element txtElement = Util
						// .getUniqueChildElementInNodeName(colElement,
						// "text:p");
						NodeList textPList = colElement
								.getElementsByTagName("text:p");
						int textPListLength = textPList.getLength();
						// if (txtElement != null) {
						if (textPListLength > 0) {
							StringBuffer valueBuffer = new StringBuffer();
							for (int j = 0; j < textPListLength; ++j) {
								if(j>0){
									valueBuffer.append("\n");
								}
								Node txtElement = textPList.item(j);
								Node txtElementChild = txtElement
										.getFirstChild();
								while (txtElementChild != null) {
									if (txtElementChild instanceof Text) {
										Text txtElementChildT = (Text) txtElementChild;
										String txt = txtElementChildT
												.getNodeValue();
										valueBuffer.append(txt);
									} else if (txtElementChild instanceof Element) {
										Element txtElementChildE = (Element) txtElementChild;
										if (txtElementChildE.getNodeName()
												.equals("text:s")) {
											int length = 1;
											String textC = txtElementChildE
													.getAttribute("text:c");
											if (textC != null
													&& textC.length() > 0) {
												length = Integer
														.parseInt(textC);
											}
											for (int i = 0; i < length; ++i) {
												valueBuffer.append(" ");
											}
										} else if (txtElementChildE
												.getNodeName().equals(
														"text:span")) {
											valueBuffer.append(txtElementChildE
													.getFirstChild()
													.getNodeValue());
										}
									}
									txtElementChild = txtElementChild
											.getNextSibling();
								}
							}
							for (int cw1 = 0; cw1 < rowRepeat + rowSpan - 1; ++cw1) {
								for (int cw2 = 0; cw2 < colRepeat + colSpan
										- 1; ++cw2) {
									int cc1 = cw1 + intCoorZ;
									int cc2 = cw2 + intCoorY;
									// int[] cc = { cc1, cc2 };
									// base
									// .setData(
									// new DataTableCoordinate(
									// cc),
									// txt);
									odsTable.put(cc2, cc1, valueBuffer
											.toString());
								}
							}
						}
						intCoorY += colRepeat;
					}
					intCoorZ += rowRepeat;
				}

				// fill base
				String txtCoorX, txtCoorY, txtCoorZ, v;
				txtCoorX = tableElement.getAttribute("table:name");
				int i, j;
				for (i = 1; i < odsTable.yMax(); ++i) {
					txtCoorY = odsTable.get(i, 0);
					if (emptyString(txtCoorY))
						continue;
					for (j = 1; j < odsTable.zMax(); ++j) {
						txtCoorZ = odsTable.get(0, j);
						v = odsTable.get(i, j);
						if (emptyString(txtCoorZ))
							continue;
						if (emptyString(v))
							continue;
						String[] coorData = { txtCoorX, txtCoorY, txtCoorZ };
						DataTableCoordinate coor = new DataTableCoordinate(
								coorData);
						base.setData(coor, v);
					}
				}
			}
		}
		return base;
	}

}
