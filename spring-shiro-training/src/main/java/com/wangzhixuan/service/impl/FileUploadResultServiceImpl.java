package com.wangzhixuan.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wangzhixuan.mapper.FileUploadResultMapper;
import com.wangzhixuan.model.FileUploadResultEntity;
import com.wangzhixuan.service.FileUploadResultService;

@Service
@Transactional
public class FileUploadResultServiceImpl implements FileUploadResultService{
	@Autowired
    private FileUploadResultMapper fileUploadResultMapper;

	@Override
	public Integer getFileUploadResultCount(String formatDate) {
		Integer count = fileUploadResultMapper.getFileUploadResultCount(formatDate);
		return count;
	}

	@Override
	public Integer addFileUploadResult(FileUploadResultEntity fileUpResult) {
		Integer count = fileUploadResultMapper.addFileUploadResult(fileUpResult);
		return count;
	}

	@Override
	public FileUploadResultEntity getFileUploadResult(String formatDate) {
		return fileUploadResultMapper.getFileUploadResult(formatDate);
	}

	@Override
	public Integer getUpdateFileUploadResult(FileUploadResultEntity fileUpResult) {
		Integer count = fileUploadResultMapper.getUpdateFileUploadResult(fileUpResult);
		return count;
	}

	@Override
	public List<FileUploadResultEntity> getFileUploadResultList(
			FileUploadResultEntity entity) {
		return fileUploadResultMapper.getFileUploadResultList(entity);
	}

	@Override
	public int getFileUploadResultListCount(FileUploadResultEntity entity) {
		
		return fileUploadResultMapper.getFileUploadResultListCount(entity);
	}

	@Override
	public FileUploadResultEntity getFileUploadResultById(String id) {
		return fileUploadResultMapper.getFileUploadResultById(id);
	}

	@Override
	public List<String> getFileNameByStatus(String status) {
		List<String> lists = fileUploadResultMapper.getFileNameByStatus(status);
		return lists;
	}

	@Override
	public Integer updateFileUploadResultById(String id, String result, String status) {
		return fileUploadResultMapper.updateFileUploadResultById(id, result, status);
	}


}
