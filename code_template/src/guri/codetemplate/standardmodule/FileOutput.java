package guri.codetemplate.standardmodule;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import guri.codetemplate.CodeTemplateModule;
import guri.codetemplate.CodeTemplateModuleContainer;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class FileOutput implements CodeTemplateModule {

	public String parse(Element element) {
		try {
			if (!element.getNodeName().equals("file_output"))
				return null;
			Element fileNameNode = null;
			Element scopeNode = null;
			Node child = element.getFirstChild();
			while (child != null) {
				if (child instanceof Element) {
					Element childE = (Element) child;
					if (childE.getNodeName().equals("filename")) {
						if (fileNameNode == null)
							fileNameNode = childE;
						else
							throw new IllegalArgumentException();
					} else if (childE.getNodeName().equals("scope")) {
						if (scopeNode == null)
							scopeNode = childE;
						else
							throw new IllegalArgumentException();
					}
				}
				child = child.getNextSibling();
			}
			if (scopeNode == null || fileNameNode == null)
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
			throw new Error();
		}
	}

}
