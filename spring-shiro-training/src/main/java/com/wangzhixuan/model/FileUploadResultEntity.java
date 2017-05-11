package com.wangzhixuan.model;

import java.sql.Timestamp;

public class FileUploadResultEntity {

	private Integer id;
	private String fileName;//文件名
	private String cityArea;//地区
	private String fileListCount;//总数
	private String repeatCount;//数据库是否有重复的
	private String fileRepeatCount;//文件上传去重的条数
	private String status;
	private String result;
	private Timestamp uploadTime;
	private Timestamp beginDate;
	private Timestamp endDate;
	private Integer pageNo;//页数
	private Integer pageSize;//条数
	
	
	public Timestamp getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Timestamp uploadTime) {
		this.uploadTime = uploadTime;
	}
	public Timestamp getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Timestamp beginDate) {
		this.beginDate = beginDate;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	public String getFileRepeatCount() {
		return fileRepeatCount;
	}
	public void setFileRepeatCount(String fileRepeatCount) {
		this.fileRepeatCount = fileRepeatCount;
	}
	public String getRepeatCount() {
		return repeatCount;
	}
	public void setRepeatCount(String repeatCount) {
		this.repeatCount = repeatCount;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getCityArea() {
		return cityArea;
	}
	public void setCityArea(String cityArea) {
		this.cityArea = cityArea;
	}
	public String getFileListCount() {
		return fileListCount;
	}
	public void setFileListCount(String fileListCount) {
		this.fileListCount = fileListCount;
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
