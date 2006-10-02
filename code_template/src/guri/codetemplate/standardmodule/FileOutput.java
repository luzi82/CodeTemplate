package guri.codetemplate.standardmodule;

import guri.codetemplate.CodeTemplateModule;
import guri.codetemplate.CodeTemplateModuleContainer;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import org.w3c.dom.Element;

public class FileOutput implements CodeTemplateModule {

	public String parse(Element element) {
		try {
			if (!element.getNodeName().equals("file_output"))
				return null;

			Element fileNameNode = Util.getUniqueChildElementInNodeName(element, "filename");
			Element scopeNode = Util.getUniqueChildElementInNodeName(element, "scope");
			if (fileNameNode == null || scopeNode == null)
				throw new IllegalArgumentException();

			boolean bubble = Util.isTrue(element.getAttribute("bubble"), true);
			String fileName = CodeTemplateModuleContainer.instance()
					.parseChilds(fileNameNode);
			String scope = CodeTemplateModuleContainer.instance().parseChilds(
					scopeNode);

			FileOutputStream fos = new FileOutputStream(fileName);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
			BufferedWriter bw = new BufferedWriter(osw);

			bw.write(scope);
			bw.flush();

			bw.close();
			osw.close();
			fos.close();

			if (bubble)
				return scope;
			else
				return "";
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
			throw new Error(e);
		}
	}

}
