package com.wangzhixuan.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wangzhixuan.model.FileUploadResultEntity;



public interface FileUploadResultMapper {

	Integer getFileUploadResultCount(String formatDate);

	FileUploadResultEntity getFileUploadResult(String formatDate);

	Integer addFileUploadResult(FileUploadResultEntity fileUpResult);

	Integer getUpdateFileUploadResult(FileUploadResultEntity fileUpResult);

	List<FileUploadResultEntity> getFileUploadResultList(
			FileUploadResultEntity entity);

	int getFileUploadResultListCount(FileUploadResultEntity entity);

	FileUploadResultEntity getFileUploadResultById(String id);

	List<String> getFileNameByStatus(String status);

	Integer updateFileUploadResultById(@Param("id") String id, @Param("result") String result, @Param("status") String status);
	
}
