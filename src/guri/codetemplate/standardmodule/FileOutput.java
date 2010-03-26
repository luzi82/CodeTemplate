package guri.codetemplate.standardmodule;

import guri.codetemplate.CodeTemplateModule;
import guri.codetemplate.CodeTemplateModuleContainer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Pattern;

import org.w3c.dom.Element;

public class FileOutput implements CodeTemplateModule {

	public String parse(Element element) {
		try {
			if (!element.getNodeName().equals("file_out"))
				return null;

			Element fileNameNode = Util.getUniqueChildElementInNodeName(
					element, "file");
			Element scopeNode = Util.getUniqueChildElementInNodeName(element,
					"scope");
			if (fileNameNode == null || scopeNode == null)
				throw new IllegalArgumentException();

			boolean bubble = Util.isTrue(element.getAttribute("bubble"), true);
			boolean mkdir = Util.isTrue(element.getAttribute("mkdir"), false);
			String fileName = CodeTemplateModuleContainer.instance()
					.parseChilds(fileNameNode);
			String scope = CodeTemplateModuleContainer.instance().parseChilds(
					scopeNode);

			// break string
			LinkedList<String> scopeDiv = new LinkedList<String>();
			scopeDiv.addLast(scope);
			scopeDiv = split(scopeDiv, "\r\n");
			scopeDiv = split(scopeDiv, "\r");
			scopeDiv = split(scopeDiv, "\n");

			if (mkdir) {
				String folderName = fileName.substring(0, fileName
						.lastIndexOf(File.separator));
				File folder = new File(folderName);
				folder.mkdirs();
			}

			FileOutputStream fos = new FileOutputStream(fileName);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
			BufferedWriter bw = new BufferedWriter(osw);

			Iterator<String> scopeDivItr = scopeDiv.iterator();
			while (scopeDivItr.hasNext()) {
				bw.write(scopeDivItr.next());
				if (scopeDivItr.hasNext())
					bw.newLine();
			}
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

	private static LinkedList<String> split(LinkedList<String> input,
			String text) {
		LinkedList<String> ret = new LinkedList<String>();
		String textPattern = Pattern.quote(text);
		for (String in : input) {
			String[] inV = in.split(textPattern, -1);
			for (String inVI : inV) {
				ret.addLast(inVI);
			}
		}
		return ret;
	}

}
