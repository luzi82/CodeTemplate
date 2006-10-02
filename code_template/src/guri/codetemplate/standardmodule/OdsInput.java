package guri.codetemplate.standardmodule;

import guri.codetemplate.CodeTemplateModule;
import guri.codetemplate.CodeTemplateModuleContainer;

import java.io.InputStream;
import java.util.zip.ZipFile;

import org.w3c.dom.Element;

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
			Element document = Util.getElement(is);
			is.close();
			file.close();

			buffer.append(document.getNodeName());

			// return
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
			throw new Error(e);
		}
	}

}
