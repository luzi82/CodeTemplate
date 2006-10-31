package guri.codetemplate.standardmodule;

import guri.codetemplate.CodeTemplateModule;
import guri.codetemplate.CodeTemplateModuleContainer;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.zip.ZipFile;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public class OdsInput implements CodeTemplateModule {

	public String parse(Element element) {
		try {
			// check note name
			if (!element.getNodeName().equals("ods_input"))
				return null;

			// obtain table id
			String tableId = element.getAttribute("table:view-id");
			if (tableId.length() <= 0)
				throw new IllegalArgumentException();

			// get file name
			Element fileNameElement = Util.getUniqueChildElementInNodeName(
					element, "filename");
			if (fileNameElement == null)
				throw new IllegalArgumentException();

			// get scope
			Element scopeElement = Util.getUniqueChildElementInNodeName(
					element, "scope");
			if (scopeElement == null)
				throw new IllegalArgumentException();

			// create buffer
			String ret = null;
			{ // scope for full inner operation

				// create table
				DataTableBase base = new DataTableBase(3);

				String fileName = CodeTemplateModuleContainer.instance()
						.parseChilds(fileNameElement);

				{ // scope of file reading temp obj
					// obtain document from osd file
					ZipFile file = new ZipFile(fileName);
					InputStream is = file.getInputStream(file
							.getEntry("content.xml"));
					Element odsContent = Util.getElement(is);
					is.close();
					file.close();

					Element bodyElement = Util.getUniqueChildElementInNodeName(
							odsContent, "office:body");
					Element spreadSheetElement = Util
							.getUniqueChildElementInNodeName(bodyElement,
									"office:spreadsheet");
					LinkedList<Element> tableElementL = Util
							.getAllChildElementInNodeName(spreadSheetElement,
									"table:table");

					// fill table
					int c0, c1, c2;// coordinate
					c0 = 0;
					for (Element tableElement : tableElementL) {
						c1 = 0;
						LinkedList<Element> rowElementL = Util
								.getAllChildElementInNodeName(tableElement,
										"table:table-row");
						for (Element rowElement : rowElementL) {
							int rowRepeat = Util.getIntAttr(rowElement,
									"table:number-rows-repeated", 1);

							c2 = 0;
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

								Element txtElement = Util
										.getUniqueChildElementInNodeName(
												colElement, "text:p");
								if (txtElement != null) {
									Node txtElementChild = txtElement
											.getFirstChild();
									if (txtElementChild instanceof Text) {
										Text txtElementChildT = (Text) txtElementChild;
										String txt = txtElementChildT
												.getNodeValue();
										for (int cw1 = 0; cw1 < rowRepeat
												+ rowSpan - 1; ++cw1) {
											for (int cw2 = 0; cw2 < colRepeat
													+ colSpan - 1; ++cw2) {
												int cc1 = cw1 + c1;
												int cc2 = cw2 + c2;
												int[] cc = { c0, cc1, cc2 };
												base
														.setData(
																new DataTableCoordinate(
																		cc),
																txt);
											}
										}
									}
								}
								c2 += colRepeat;
							}
							c1 += rowRepeat;
						}
						++c0;
					}
				}
				System.gc();

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

}
