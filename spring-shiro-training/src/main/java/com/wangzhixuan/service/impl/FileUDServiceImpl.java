package com.wangzhixuan.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wangzhixuan.mapper.FileUDMapper;
import com.wangzhixuan.model.FileUD;
import com.wangzhixuan.service.FileUDService;

@Service
@Transactional
public class FileUDServiceImpl implements FileUDService{
	
	@Autowired
    private FileUDMapper fileUDMapper;

	@Override
	public Integer setFileUDService(FileUD fileUD) {
		int count = fileUDMapper.setFileUDService(fileUD);
		return count;
	}

	@Override
	public Integer getFileUDCount(String str) {
		return fileUDMapper.getFileUDCount(str);
	}

	@Override
	public Integer updateFileUDPwd(FileUD fileUD) {
		return fileUDMapper.updateFileUDPwd(fileUD);
	}

	@Override
	public FileUD getFileUDEntity(String str) {
		return fileUDMapper.getFileUDEntity(str);
	}

	@Override
	public List<FileUD> getFileUDList(FileUD fileUD) {
		List<FileUD> fileUDs = fileUDMapper.getFileUDList(fileUD);
		return fileUDs;
	}

	@Override
	public Integer getFileUDListCount(FileUD fileUD) {
		return fileUDMapper.getFileUDListCount(fileUD);
	}

	@Override
	public String getEntityOrderByDateDesc() {
		return fileUDMapper.getEntityOrderByDateDesc();
	}

	@Override
	public List<String> getFileUDEntityList(String timeFileName) {
		List<String> fileUDs = fileUDMapper.getFileUDEntityList(timeFileName);
		return fileUDs;
	}

	@Override
	public List<FileUD> getFileUDListByFileName(String fileName) {
		List<FileUD> fileUDs = fileUDMapper.getFileUDListByFileName(fileName);
		return fileUDs;
	}

	@Override
	public FileUD getFileUDByFileNameAndUserName(String fileName,
			String userName,String insertLine) {
		FileUD fileUD = fileUDMapper.getFileUDByFileNameAndUserName(fileName,userName,insertLine);
		return fileUD;

	}

	@Override
	public Integer getFileUDEntityCount(String fileName,String userName, String insertLine) {
		return fileUDMapper.getFileUDEntityCount(fileName,userName,insertLine);
	}

	@Override
	public List<FileUD> getFileUDSearchList(String userName, int pageNo, int pageSize) {
		return fileUDMapper.getFileUDSearchList(userName, pageNo, pageSize);
	}

	@Override
	public int getFileUDSearchListCount(String userName) {
		return fileUDMapper.getFileUDSearchListCount(userName);
	}

	@Override
	public FileUD getEntityByIptvAccountAndStatus(String iptvAccount,
			String status) {
		return fileUDMapper.getEntityByIptvAccountAndStatus(iptvAccount, status);
	}

	@Override
	public List<FileUD> getTwoFileUDSearchList(String userName, int pageNo,
			int pageSize) {
		return fileUDMapper.getTwoFileUDSearchList(userName, pageNo, pageSize);
	}

	@Override
	public int getTwoFileUDSearchListCount(String userName) {
		return fileUDMapper.getTwoFileUDSearchListCount(userName);
	}

}
