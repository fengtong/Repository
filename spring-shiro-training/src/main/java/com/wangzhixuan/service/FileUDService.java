package com.wangzhixuan.service;

import java.util.List;

import com.wangzhixuan.model.FileUD;


public interface FileUDService {
	public Integer setFileUDService(FileUD fileUD);

	public Integer getFileUDCount(String str);
	
	public Integer getFileUDEntityCount(String fileName, String str, String countLine);

	public Integer updateFileUDPwd(FileUD fileUD);

	public FileUD getFileUDEntity(String string);

	public List<FileUD> getFileUDList(FileUD fileUD);

	public Integer getFileUDListCount(FileUD fileUD);

	public String getEntityOrderByDateDesc();

	public List<String> getFileUDEntityList(String timeFileName);

	public List<FileUD> getFileUDListByFileName(String fileName);

	public FileUD getFileUDByFileNameAndUserName(String fileName,
			String userName, String countLine);

	public List<FileUD> getFileUDSearchList(String userName, int pageNo, int pageSize);

	public int getFileUDSearchListCount(String userName);

	public FileUD getEntityByIptvAccountAndStatus(String iptvAccount,
			String status);

	public List<FileUD> getTwoFileUDSearchList(String userName, int pageNo, int pageSize);

	public int getTwoFileUDSearchListCount(String userName);

}
