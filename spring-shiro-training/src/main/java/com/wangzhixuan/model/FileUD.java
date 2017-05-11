package com.wangzhixuan.model;

import java.io.Serializable;

public class FileUD implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5081199337857091238L;
	private Integer id;
	private String createNodeMark;
	private String userName;
	private String passWord;
	private String wideBandProp;
	private String saveField;
	private String consumerName;
	private String address;
	private String phone;
	private String fileName;
	private String status;
	private String result;
	private String uploadTime;
	private String repeatCount;
	private String insertLine;
	private Integer pageNo;//页数
	private Integer pageSize;//条数
	
	
	public String getInsertLine() {
		return insertLine;
	}
	public void setInsertLine(String insertLine) {
		this.insertLine = insertLine;
	}
	public String getRepeatCount() {
		return repeatCount;
	}
	public void setRepeatCount(String repeatCount) {
		this.repeatCount = repeatCount;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	public String getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCreateNodeMark() {
		return createNodeMark;
	}
	public void setCreateNodeMark(String createNodeMark) {
		this.createNodeMark = createNodeMark;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getWideBandProp() {
		return wideBandProp;
	}
	public void setWideBandProp(String wideBandProp) {
		this.wideBandProp = wideBandProp;
	}
	public String getSaveField() {
		return saveField;
	}
	public void setSaveField(String saveField) {
		this.saveField = saveField;
	}
	public String getConsumerName() {
		return consumerName;
	}
	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
