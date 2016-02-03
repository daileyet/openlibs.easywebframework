package com.openthinks.easyweb.utils.export.template;

import com.openthinks.easyweb.utils.export.ExportData;
import com.openthinks.easyweb.utils.export.ExportService;
import com.openthinks.easyweb.utils.export.SourceTemplate;
import com.openthinks.easyweb.utils.export.TargetDefine;
import com.openthinks.easyweb.utils.export.bean.ExportExampleBean;
import com.openthinks.easyweb.utils.export.bean.OptionsBean;
import com.openthinks.easyweb.utils.export.bean.SettingupProject;
import com.openthinks.easyweb.utils.export.bean.SettingupProjectBean;

public class ExportServiceTest {

	public void testSimplebena() {
		ExportService service = new ExportService();

		ExportExampleBean bean = new ExportExampleBean();
		bean.column2 = "测试1";
		bean.column3 = "测试2";
		bean.options = new OptionsBean();
		bean.options.addDefaultItem("Label1", true);
		bean.options.addDefaultItem("Label2", false);

		ExportData data = ExportData.create(bean);
		SourceTemplate sorceTemplate = SourceTemplate.create(
				"/com/openthinks/easyweb/utils/export/template/", "导出表格示例.xml");
		TargetDefine target = TargetDefine.create("/home/dailey/导出表格示例.doc");
		target = TargetDefine.create("D:/导出表格示例.doc");
		service.setExportData(data);
		service.setSourceTemplate(sorceTemplate);
		service.setTargetDefine(target);

		service.export();
	}

	public void testAdvance() {
		ExportService service = new ExportService();
		SettingupProject bean = new SettingupProject();
		bean.setProjectSource("投标项目");
		bean.setReviewResultCheck("合同或委托内容及业主要求符合国家法律、法规。~我院的技术力量能满足业主的质量及进度要求。");
		ExportData data = ExportData.createWrapper(bean,
				SettingupProjectBean.class);
		SourceTemplate sorceTemplate = SourceTemplate.create(
				"/com/openthinks/easyweb/utils/export/template/",
				"01项目立项评审表.xml");
		TargetDefine target = TargetDefine.create("/home/dailey/01项目立项评审表.doc");
		target = TargetDefine.create("D:/01项目立项评审表.doc");
		service.setExportData(data);
		service.setSourceTemplate(sorceTemplate);
		service.setTargetDefine(target);

		service.export();
	}

	public static void main(String[] args) {
		ExportServiceTest tester = new ExportServiceTest();
		// tester.testSimplebena();
		tester.testAdvance();

	}

}
