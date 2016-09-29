package com.openthinks.easyweb.utils.export.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.openthinks.easyweb.utils.export.bean.OptionsBean;

public class SettingupProjectBean extends SettingupProject {
	OptionsBean projectSourceOptions, reviewResult;
	DateFormat dateFormat;
	{
		dateFormat = new SimpleDateFormat("yyyy/MM/dd");

		projectSourceOptions = new OptionsBean();
		projectSourceOptions.addDefaultItem("上级指令项目");
		projectSourceOptions.addDefaultItem("业主委托项目");
		projectSourceOptions.addDefaultItem("合同商谈项目");
		projectSourceOptions.addDefaultItem("投标项目");
		reviewResult = new OptionsBean();
		reviewResult.addDefaultItem("合同或委托内容及业主要求符合国家法律、法规。");
		reviewResult.addDefaultItem("我院的技术力量能保证完成设计任务。");
		reviewResult.addDefaultItem("我院的技术力量能满足业主的质量及进度要求。");
		reviewResult.addDefaultItem("需要组织外出考察收集资料。");

	}

	public OptionsBean getProjectSource1() {
		projectSourceOptions.setCheckedOption(getProjectSource(), true);
		return projectSourceOptions;
	}

	public OptionsBean getReviewResult() {
		List<String> resultList = getReviewResultCheck();
		if (resultList != null) {
			for (String label : resultList) {
				reviewResult.setCheckedOption(label, true);
			}
		}
		return reviewResult;
	}

	public String getRequestDate_() {
		Long requetDate = getRequestDate();
		return dateFormat.format(new Date(requetDate));
	}

	public String getReviewDate_() {
		Long date = getReviewDate();
		return dateFormat.format(new Date(date));
	}

	public String getStartDate_() {
		Long date = getStartDate();
		return dateFormat.format(new Date(date));
	}

	public String getEndDate_() {
		Long date = getEndDate();
		return dateFormat.format(new Date(date));
	}

	public static void main(String[] args) {
		SettingupProjectBean tester = new SettingupProjectBean();
		System.out.println(tester.getRequestDate_());
	}

}
