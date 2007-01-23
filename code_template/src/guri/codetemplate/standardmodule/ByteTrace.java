package guri.codetemplate.standardmodule;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;

import org.w3c.dom.Element;

import guri.codetemplate.CodeTemplateModule;
import guri.codetemplate.CodeTemplateModuleContainer;

public class ByteTrace implements CodeTemplateModule {

	public String parse(Element element) {
		try {
			if (!element.getNodeName().equals("bytetrace"))
				return null;
			StringBuffer ret = new StringBuffer();
			String src = CodeTemplateModuleContainer.instance().parseChilds(
					element);
			String charset = element.getAttribute("charset");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(baos, charset);
			osw.write(src);
			osw.flush();
			osw.close();
			byte[] output = baos.toByteArray();
			for (byte outputI : output) {
				String outPutIhex = Integer.toHexString(outputI);
				while (outPutIhex.length() < 2) {
					outPutIhex = "0" + outPutIhex;
				}
				ret.append(outPutIhex);
			}
			return ret.toString();
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(-1);
			return null;
		}
	}

}
