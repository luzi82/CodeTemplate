package guri.codetemplate;

import java.util.LinkedList;

public class CodeTemplateModuleContainer {

	private LinkedList<CodeTemplateModule> moduleList = new LinkedList<CodeTemplateModule>();

	private CodeTemplateModuleContainer() {
	}

	public void init() {
		try {
			LinkedList<String> moduleClassList = CodeTemplateGlobalConfig
					.instance().getModuleList();
			for (String i : moduleClassList) {
				Class moduleClass = Class.forName(i);
				CodeTemplateModule module = (CodeTemplateModule) moduleClass
						.newInstance();
				moduleList.add(module);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	// /////////////////////////////
	// SINGLETON

	private static CodeTemplateModuleContainer instance;

	public static CodeTemplateModuleContainer instance() {
		if (instance == null)
			instance = new CodeTemplateModuleContainer();
		return instance;
	}

}
