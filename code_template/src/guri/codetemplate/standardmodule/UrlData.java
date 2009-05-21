package guri.codetemplate.standardmodule;

import guri.codetemplate.CodeTemplateModule;
import guri.codetemplate.CodeTemplateModuleContainer;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;

import org.w3c.dom.Element;

public class UrlData implements CodeTemplateModule {

	public String parse(Element element) {
		try {
			if (!element.getNodeName().equals("url"))
				return null;
			URL url = new URL(CodeTemplateModuleContainer.instance()
					.parseChilds(element));
			InputStream is = url.openStream();
			InputStreamReader isr = new InputStreamReader(is);
			StringWriter sw = new StringWriter();
			char[] buf = new char[1000];
			while (true) {
				int len = isr.read(buf);
				if (len < 0)
					break;
				sw.write(buf, 0, len);
			}
			isr.close();
			sw.flush();
			String out = sw.toString();
			sw.close();
			return out;
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(-1);
			return null;
		}
	}
}
