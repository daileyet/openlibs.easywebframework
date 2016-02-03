package com.openthinks.easyweb.utils.export.bean;

import java.util.List;

public class SettingupProject {

	private String projectName = "EasywebFramework";

	private String projectSource = "";

	private String projectLocation = "Oracle";

	private String customer = "openthinks";

	private String customerContactor = "苏州";

	private String customerPhone = "15995768656";

	private String customerFax;

	private String customerEmail = "dailey.dai@oracle.com";

	private String designContent = "测试内容,无关紧要";

	private String qualityRequest = "要求一般般";

	private String designStage;

	private Long startDate = new java.util.Date().getTime();

	private Long endDate = new java.util.Date().getTime();

	private String requestPerson = "dailey";

	private Long requestDate = new java.util.Date().getTime();

	private Long reviewDate = new java.util.Date().getTime();

	private String reviewCompere = "dailey";

	private String reviewResultCheck = "";

	private String reviewResultOther;

	private String reviewLocation = "suzhou";

	private String designDepartOpinion = "";

	private String designDepartPersion = "同意";

	private String leader = "同意";

	public String getProjectSource() {
		return projectSource;
	}

	public void setProjectSource(String projectSource) {
		this.projectSource = projectSource;
	}

	public String getProjectLocation() {
		return projectLocation;
	}

	public void setProjectLocation(String projectLocation) {
		this.projectLocation = projectLocation;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getCustomerContactor() {
		return customerContactor;
	}

	public void setCustomerContactor(String customerContactor) {
		this.customerContactor = customerContactor;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getCustomerFax() {
		return customerFax;
	}

	public void setCustomerFax(String customerFax) {
		this.customerFax = customerFax;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getDesignContent() {
		return designContent;
	}

	public void setDesignContent(String designContent) {
		this.designContent = designContent;
	}

	public String getQualityRequest() {
		return qualityRequest;
	}

	public void setQualityRequest(String qualityRequest) {
		this.qualityRequest = qualityRequest;
	}

	public String getDesignStage() {
		return designStage;
	}

	public void setDesignStage(String designStage) {
		this.designStage = designStage;
	}

	public String getReviewCompere() {
		return reviewCompere;
	}

	public void setReviewCompere(String reviewCompere) {
		this.reviewCompere = reviewCompere;
	}

	public List<String> getReviewResultCheck() {
		return EntityUtil.toArrayFromString(reviewResultCheck);
	}

	public void setReviewResultCheck(List<String> reviewResultCheck) {
		this.reviewResultCheck = EntityUtil.toStringFromArray(reviewResultCheck);
	}

	public Long getStartDate() {
		return startDate;
	}

	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}

	public Long getEndDate() {
		return endDate;
	}

	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}

	public Long getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Long requestDate) {
		this.requestDate = requestDate;
	}

	public Long getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Long reviewDate) {
		this.reviewDate = reviewDate;
	}

	public void setReviewResultCheck(String reviewResultCheck) {
		this.reviewResultCheck = reviewResultCheck;
	}

	public String getReviewResultOther() {
		return reviewResultOther;
	}

	public void setReviewResultOther(String reviewResultOther) {
		this.reviewResultOther = reviewResultOther;
	}

	public String getDesignDepartOpinion() {
		return designDepartOpinion;
	}

	public void setDesignDepartOpinion(String designDepartOpinion) {
		this.designDepartOpinion = designDepartOpinion;
	}

	public String getDesignDepartPersion() {
		return designDepartPersion;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public void setDesignDepartPersion(String designDepartPersion) {
		this.designDepartPersion = designDepartPersion;
	}

	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	public String getRequestPerson() {
		return requestPerson;
	}

	public void setRequestPerson(String requestPerson) {
		this.requestPerson = requestPerson;
	}

	public String getReviewLocation() {
		return reviewLocation;
	}

	public void setReviewLocation(String reviewLocation) {
		this.reviewLocation = reviewLocation;
	}

}
