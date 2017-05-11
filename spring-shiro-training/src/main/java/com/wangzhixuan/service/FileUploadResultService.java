package com.wangzhixuan.service;

import java.util.List;

import com.wangzhixuan.model.FileUploadResultEntity;



public interface FileUploadResultService {

	Integer getFileUploadResultCount(String formatDate);

	FileUploadResultEntity getFileUploadResult(String formatDate);

	Integer addFileUploadResult(FileUploadResultEntity fileUpResult);

	Integer getUpdateFileUploadResult(FileUploadResultEntity fileUpResult);

	List<FileUploadResultEntity> getFileUploadResultList(
			FileUploadResultEntity entity);

	int getFileUploadResultListCount(FileUploadResultEntity entity);

	FileUploadResultEntity getFileUploadResultById(String id);

	List<String> getFileNameByStatus(String status);

	Integer updateFileUploadResultById(String id, String result, String status);

	
}
