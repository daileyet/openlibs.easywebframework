package com.openthinks.easyweb.utils.export.bean;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.openthinks.easyweb.utils.export.bean.DefaultOptionBean;
import com.openthinks.easyweb.utils.export.bean.OptionsBean;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateException;

public class ExportExampleBean {

	public String column2;
	public String column3;
	public OptionsBean options;

	public OptionsBean getOptions() {
		return options;
	}

	public void setOptions(OptionsBean options) {
		this.options = options;
	}

	public String getColumn2() {
		return column2;
	}

	public void setColumn2(String column2) {
		this.column2 = column2;
	}

	public String getColumn3() {
		return column3;
	}

	public void setColumn3(String column3) {
		this.column3 = column3;
	}

	public static void main(String[] args) throws IOException,
			TemplateException {
		Map<String, Object> root = new HashMap<String, Object>();
		ExportExampleBean bean = new ExportExampleBean();
		bean.column2 = "测试1";
		bean.column3 = "测试2";
		bean.options = new OptionsBean();
		bean.options.addDefaultItem("Label1", true);
		bean.options.addDefaultItem("Label2", false);
		root.put("bean", bean);

		Configuration cfg = new Configuration();
		// cfg.setDirectoryForTemplateLoading(new File(
		// "/com/openthinks/easyweb/utils/export/template/"));
		cfg.setClassForTemplateLoading(ExportExampleBean.class,
				"/com/openthinks/easyweb/utils/export/template/");
		cfg.setObjectWrapper(new DefaultObjectWrapper());

		// Template template = cfg.getTemplate("导出表格示例.ftl");
		// OutputStream out = new
		// FileOutputStream("/home/dailey/导出表格示例.doc");
		// Writer write = new OutputStreamWriter(out);
		// template.process(root, write);
		// write.flush();
		// write.close();

		System.out.println((int) DefaultOptionBean.CHECKEDS[0].charAt(0));
		System.out.println((int) DefaultOptionBean.CHECKEDS[1].charAt(0));
		System.out.println('\u9744');
		System.out.println('\u9745');
	}
}
