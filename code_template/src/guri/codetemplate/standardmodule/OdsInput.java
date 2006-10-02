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

			// create buffer
			StringBuffer buffer = new StringBuffer();

			// get file name
			Element fileNameElement = Util.getUniqueChildElementInNodeName(
					element, "filename");
			if (fileNameElement == null)
				throw new IllegalArgumentException();
			String fileName = CodeTemplateModuleContainer.instance()
					.parseChilds(fileNameElement);

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

			int c1, c2, c3;// coordinate
			c1 = 0;
			for (Element tableElement : tableElementL) {
				c2 = 0;
				LinkedList<Element> rowElementL = Util
						.getAllChildElementInNodeName(tableElement,
								"table:table-row");
				for (Element rowElement : rowElementL) {
					int rowRepeat = Util.getIntAttr(rowElement,
							"table:number-rows-repeated", 1);

					c3 = 0;
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
								.getUniqueChildElementInNodeName(colElement,
										"text:p");
						if (txtElement != null) {
							Node txtElementChild = txtElement.getFirstChild();
							if (txtElementChild instanceof Text) {
								Text txtElementChildT = (Text) txtElementChild;
								String txt = txtElementChildT.getNodeValue();
								// TODO fill
							}
						}

						c3 += colRepeat;
					}

					c2 += rowRepeat;
				}
				++c1;
			}

			// return
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
			throw new Error(e);
		}
	}

}
