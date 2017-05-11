package com.wangzhixuan.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wangzhixuan.model.FileUD;


public interface FileUDMapper {
	public Integer setFileUDService(FileUD fileUD);

	public Integer getFileUDCount(String str);

	public Integer updateFileUDPwd(FileUD fileUD);

	public FileUD getFileUDEntity(String str);

	public List<FileUD> getFileUDList(FileUD fileUD);

	public Integer getFileUDListCount(FileUD fileUD);

	public String getEntityOrderByDateDesc();

	public List<String> getFileUDEntityList(String timeFileName);

	public List<FileUD> getFileUDListByFileName(String fileName);

	public FileUD getFileUDByFileNameAndUserName(@Param("fileName") String fileName,@Param("userName") String userName,@Param("insertLine") String insertLine);

	public Integer getFileUDEntityCount(@Param("fileName") String fileName,@Param("userName")String userName, @Param("insertLine")String insertLine);

	public List<FileUD> getFileUDSearchList(@Param("userName") String userName, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize);

	public int getFileUDSearchListCount(@Param("userName") String userName);

	public FileUD getEntityByIptvAccountAndStatus(@Param("userName")String userName,
			@Param("status") String status);

	public List<FileUD> getTwoFileUDSearchList(@Param("userName") String userName, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize);

	public int getTwoFileUDSearchListCount(@Param("userName") String userName);
}
