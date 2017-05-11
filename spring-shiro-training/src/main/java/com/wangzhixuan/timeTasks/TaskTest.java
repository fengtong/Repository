package com.wangzhixuan.timeTasks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.wangzhixuan.model.FileUD;
import com.wangzhixuan.service.FileUDService;
import com.wangzhixuan.service.FileUploadResultService;
import com.wangzhixuan.utils.BeanUtils;
import com.wangzhixuan.utils.FtpUtils;
import com.wangzhixuan.utils.PropertiesUtils;

public class TaskTest {

	protected final Logger logger = Logger.getLogger(TaskTest.class);
	private Properties prop = new PropertiesUtils().getProp();
	/**
	 * 上传到ftp服务器ip地址
	 */
	private String STRING_IP = prop.getProperty("STRING_IP");
	/**
	 * 上传到ftp服务器端口
	 */
	private int INT_PORT = Integer.valueOf((prop.getProperty("INT_PORT")));
	/**
	 * 上传到ftp服务器用户名
	 */
	private String USERNAME = prop.getProperty("USERNAME");
	/**
	 * 上传到ftp服务器密码
	 */
	private String PASSWORD = prop.getProperty("PASSWORD");
	/**
	 * 上传到ftp服务器目录路径
	 */
	private String FTP_UPLOAD_PATH = prop.getProperty("FTP_UPLOAD_PATH");
	
	private String DOWNLOAD_FROM_FTP = prop.getProperty("DOWNLOAD_FROM_FTP");

	@SuppressWarnings("resource")
	public void taskWork() {
		String encoding = "gb2312";
		FileUDService fileUDService = BeanUtils.getBean("fileUDService",FileUDService.class);
		FileUploadResultService fileUploadResultService = BeanUtils.getBean("fileUploadResultService", FileUploadResultService.class);
		List<String> timeFileNames = fileUploadResultService.getFileNameByStatus("1"); //取得文件状态是1的文件
		if(timeFileNames != null && timeFileNames.size() > 0) {
			for(String timeFileName : timeFileNames) {
				String fileName = "Kaihu." + timeFileName + ".result";
				String remotePath = FTP_UPLOAD_PATH + "result/";// 往ftp下载东西的目录
//				String remotePath = FTP_UPLOAD_PATH + "downloadPath\\";// 往ftp下载东西的目录
				String localPath = DOWNLOAD_FROM_FTP;
				boolean bFlag = FtpUtils.downFile(STRING_IP, INT_PORT, USERNAME,
						PASSWORD, remotePath, fileName, localPath);
				if (bFlag) {
					try {
//						File file = new File(remotePath + fileName);
						File file = new File(localPath + fileName);
						logger.info(localPath + fileName + "------------------------------------------------");
						if (file.isFile() && file.exists()) {
							InputStreamReader read = new InputStreamReader(
									new FileInputStream(file), encoding);
							BufferedReader bfReader = new BufferedReader(read);
							String lineTxt = null;
							FileUD fileUD = new FileUD();
							String status = "";
							int countLine = 0;
							while ((lineTxt = bfReader.readLine()) != null) {
								countLine++;
								String[] strings = lineTxt.split(":");
								Integer count = fileUDService.getFileUDEntityCount(
										timeFileName, strings[1],
										String.valueOf(countLine));
								if (count != null && count > 0) {
									if (strings[8].equals("OK")) {
										status = "2";
										fileUD.setStatus(status);// 导入成功
									} else {
										status = "1";
										fileUD.setStatus(status);// 导入发生错误
									}
									fileUD.setCreateNodeMark(strings[0]);
									fileUD.setPassWord(strings[2]);
									fileUD.setWideBandProp(strings[3]);
									fileUD.setSaveField(strings[4]);
									fileUD.setConsumerName(strings[5]);
									fileUD.setAddress(strings[6]);
									fileUD.setPhone(strings[7]);
									fileUD.setResult(strings[8]);
									logger.info(strings[8]);
									FileUD fileUDObj = fileUDService
											.getFileUDByFileNameAndUserName(
													timeFileName, strings[1],
													String.valueOf(countLine));
									fileUD.setId(fileUDObj.getId());
									Integer updateCount = fileUDService
											.updateFileUDPwd(fileUD);
									if (updateCount != null && updateCount > 0) {
										logger.info(strings[1] + "更新成功");
									} else {
										logger.info(strings[1] + "更新失败");
									}
								}
							}
							List<String> strsResults = fileUDService.getFileUDEntityList(timeFileName);
							String id = String.valueOf(fileUploadResultService.getFileUploadResult(timeFileName).getId());
							String resultStatus = "";
							String result = "";
//							fileUpResult.setId(Integer.valueOf(id));
//							fileUpResult.setStatus("3");
//							fileUpResult.setResult("OK");
							if (strsResults != null && strsResults.size() > 0) {
								resultStatus = "3";
								result = "OK";
								for (String strsResult : strsResults) {
									if (strsResult.equals("OK")) {
										continue;
									} else {
										resultStatus = "2";
										result = "ERROR";
//										fileUpResult.setStatus("2");
//										fileUpResult.setResult("ERROR");
									}
								}
							}else {
								logger.info("please ensure this file has elements.");
							}
							Integer uploadResultCount = fileUploadResultService.updateFileUploadResultById(id,result,resultStatus);
							if (uploadResultCount != null && uploadResultCount > 0) {
								logger.info("update result successfully");
							} else {
								logger.info("update result failed");
							}
						} else {
							logger.info("can not file local file");
						}
					} catch (FileNotFoundException e) {
						logger.info("FileNotFoundException" + e);
						e.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						logger.info("UnsupportedEncodingException" + e);
						e.printStackTrace();
					} catch (IOException e) {
						logger.info("IOException" + e);
						e.printStackTrace();
					}
				} else {
					logger.info("ftp can not connect successfully...");
				}
			}
		}else {
			logger.info("have no new file.");
		}
	}
}
