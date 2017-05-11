package com.wangzhixuan.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.net.telnet.TelnetClient;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.wangzhixuan.model.FileUD;
import com.wangzhixuan.model.FileUploadResultEntity;
import com.wangzhixuan.service.FileUDService;
import com.wangzhixuan.service.FileUploadResultService;
import com.wangzhixuan.service.RoleService;
import com.wangzhixuan.service.UserService;
import com.wangzhixuan.utils.FtpUtils;
import com.wangzhixuan.utils.PropertiesUtils;

/**
 * @author zgt 文件上传
 */
@Controller
@RequestMapping(value = "/fileUpLoad")
public class FileUDController {

	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

	private static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	private Properties prop = new PropertiesUtils().getProp();
	/**
	 * 服务器下载路径目录
	 */
	private String BASE_PATH = prop.getProperty("BASE_UPLOAD_PATH");
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
	/**
	 * 把用户上传的那个文本文件输出到该目录等待上传到ftp服务器
	 */
	private String UPLOAD_PATH = prop.getProperty("UPLOAD_PATH");
	/**
	 * 不同操作系统的换行符号
	 */
	private String SERVICE_NEWLINE = prop.getProperty("SERVICE_NEWLINE");
	/**
	 * 处理数据时候的分隔符
	 */
	private String SAPERATE = prop.getProperty("SAPERATE");
	/**
	 * 修改密码的上传文件路径
	 */
	private String EDIT_PASSWORD_PATH = prop.getProperty("EDIT_PASSWORD_PATH");
	/**
	 * 修改密码的IP
	 */
	private String EDIT_PW_SOCKET_IP = prop.getProperty("EDIT_PW_SOCKET_IP");
	/**
	 * 修改密码的PORT
	 */
	private String EDIT_PW_SOCKET_PORT = prop.getProperty("EDIT_PW_SOCKET_PORT");
	 /**
	  * 修改密码的结果
	  */
	private String EDIT_PASSWORD_RESULT_PATH = prop.getProperty("EDIT_PASSWORD_RESULT_PATH");
	/**
	 * 删除IPOE的上传文件路径
	 */
	private String DEL_PATH = prop.getProperty("DEL_PATH");
	/**
	 * 删除IPOE的结果文件路径
	 */
	private String DEL_RESULT_PATH = prop.getProperty("DEL_RESULT_PATH");
	
	private final static String BLANK = " ";
	private String WIDE_BAND_PROP = prop.getProperty("WIDE_BAND_PROP");
	private String SAVE_FIELD = prop.getProperty("SAVE_FIELD");
	private String CONSUMER_NAME = prop.getProperty("CONSUMER_NAME");
	private String ADDRESS = prop.getProperty("ADDRESS");
	private String PHONE = prop.getProperty("PHONE");

	@Autowired
	private FileUDService fileUDService;
	@Autowired
	private FileUploadResultService fileUploadResultService;
	@Autowired
	private UserService userService;
	@Autowired
    private RoleService roleService;
	
    /**
     * 点击OBS上传
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/obsFileUpload")
    public String obsFileUpload(Model model) {
        return "/admin/obsFileUpload";
    }
    
    /**
     * 点击obs 二级测试账户IPOE账号查询
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/obsTwoIPOEAccountQuery")
    public String obsTwoIPOEAccountQuery(Model model) {
        return "/admin/obsTwoIPOEAccountQuery";
    }
    
    /**
     * 点击obs IPOE账号查询
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/obsIPOEAccountQuery")
    public String obsIPOEAccountQuery(Model model) {
        return "/admin/obsIPOEAccountQuery";
    }
    
    /**
     * 点击OBS结果查询
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/obsResultQuery")
    public String obsResultQuery(Model model) {
        return "/admin/obsResultQuery";
    }
	
    /**
     * 点击OBS密码修改
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/obsEditPassword")
    public String obsEditPassword(Model model) {
        return "/admin/obsEditPassword";
    }
    
    /**
     * 点击OBS 删除IPOE账号
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/obsDelIPOE")
    public String obsDelIPOE(Model model) {
        return "/admin/obsDelIPOE";
    }
	
	/**
	 * 
	 * @param file
	 *            用户上传的文件主体
	 * @param req
	 * @param resp
	 */
	@ResponseBody
	@RequestMapping(value = "/getUpLoadFile", method = RequestMethod.POST)
	public Map<String, Object> uploadOBSFile(
			@RequestParam("file") CommonsMultipartFile file,
			@Param("areaText") String areaText, HttpServletRequest req,
			HttpServletResponse resp) {
		boolean flag = false;
		String result = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			DataInputStream dataIs = null;
			DataOutputStream dataOs = null;
			resp.setContentType(CONTENT_TYPE);
			Date date = new Date();
			String formatDate = new SimpleDateFormat("yyyyMMddHHmmss").format(date);
			String uploadDateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
			String fileName = "Kaihu." + formatDate + ".txt";
			String filePath = BASE_PATH + fileName;
			dataIs = new DataInputStream(new BufferedInputStream(file.getInputStream()));
			dataOs = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)));
			byte[] bytes = new byte[1024];
			while (true) {
				int len = 0;
				if (dataIs != null) {
					len = dataIs.read(bytes);
				}
				if (len == -1) {
					break;
				}
				dataOs.write(bytes, 0, len);
			}
			dataOs.flush();
			dataIs.close();
			dataOs.close();

			List<String> fileList = readTxt(filePath);
			// 输出文件重复的条数并删除，输出总条数还有去重复之后的条数
			Integer fileCount = 0;
			List<String> noRepeatList = new ArrayList<String>();
			List<String> copyFileList = new ArrayList<String>();
			for (String str : fileList) {
				copyFileList.add(str);
			}
			List<String> repeatList = new ArrayList<String>();
			for (int i = 0; i < copyFileList.size(); i++) {
				fileCount++;
				String[] strings = copyFileList.get(i).split("\\|");
				if (!noRepeatList.contains(strings[0])) {
					noRepeatList.add(strings[0]);//把账号不重复的集合起来放在noRepeatList
				} else {
					repeatList.add(strings[0]);//把账号重复的集合起来放在repeatList
					copyFileList.remove(i);
					i--;
				}
			}
			//把上面的repeatList集合相同账号的个数统计出来
			Map<String, Integer> strMap = new HashMap<String, Integer>();
			for (int i = 0; i < repeatList.size(); i++) {
				String strObj = repeatList.get(i);
				if(strMap.containsKey(repeatList.get(i))){
					strMap.put(strObj, strMap.get(strObj) + 1);
				}else{
					strMap.put(strObj, 1);
				}
			}
			
			
			
			String ftpFilePath = dealReadTxt(fileList, areaText, formatDate);

			// 上传到ftp
			File ftpFile = new File(ftpFilePath);
			FileInputStream in = null;
			String ftpFileName = "Kaihu." + formatDate;
			String dealDataFlag = dealData(fileList, areaText, formatDate,
					uploadDateStr, String.valueOf(fileCount),
					String.valueOf(fileCount - copyFileList.size()),strMap);
			if (dealDataFlag.equals("success")) {
				in = new FileInputStream(ftpFile);
				flag = FtpUtils.uploadFile(STRING_IP, INT_PORT, USERNAME,PASSWORD, FTP_UPLOAD_PATH, ftpFileName, in);
				if (flag) {
					String id = String.valueOf(fileUploadResultService.getFileUploadResult(formatDate).getId());
					Integer uploadResultCount = fileUploadResultService.updateFileUploadResultById(id, null, "1");
					if (uploadResultCount != null && uploadResultCount > 0) {
						LOGGER.info("update successfully");
					} else {
						LOGGER.info("update failed");
					}
					result = "ftpSuccess";
				} else {
					result = "ftpFailed";
				}
			} else {
				result = "fail";
			}
			map.put("result", result);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 把用户上传上来的文件进行处理并保存到数据库
	 * 
	 * @param fileList
	 */
	private String dealData(List<String> fileList, String areaText,String formatDate, String uploadDateStr, String fileCount,String fileRepeatCount,Map<String, Integer> strMap) {
		String b = "fail";
		int count = 0;
		FileUD fileUD = new FileUD();
		if (fileList != null && fileList.size() > 0) {
			for (String listString : fileList) {
				count ++;
				String[] strings = listString.split("\\|");
				fileUD.setUserName(strings[0]);
				fileUD.setPassWord(strings[1]);
				fileUD.setFileName(formatDate);
				fileUD.setStatus("0");
				fileUD.setUploadTime(uploadDateStr);
				fileUD.setInsertLine(String.valueOf(count));
				if(strMap.containsKey(strings[0])) {
					fileUD.setRepeatCount(String.valueOf(strMap.get(strings[0])));
				}else {
					fileUD.setRepeatCount("0");
				}
				fileUD.setCreateNodeMark(areaText);
				fileUD.setWideBandProp(WIDE_BAND_PROP);
				Integer addCount = fileUDService.setFileUDService(fileUD);
				if (addCount != null && addCount > 0) {
					LOGGER.info("suceessfully insert into table");
				} else {
					LOGGER.info("failed insert into table");
				}
			}

			FileUploadResultEntity fileUpResult = new FileUploadResultEntity();
			fileUpResult.setFileName(formatDate);
			fileUpResult.setCityArea(areaText);
			fileUpResult.setFileListCount(fileCount);
			fileUpResult.setFileRepeatCount(fileRepeatCount);
			fileUpResult.setStatus("0");
			fileUpResult.setResult(null);
			fileUpResult.setUploadTime(Timestamp.valueOf(uploadDateStr));
			LOGGER.info("执行插入");
			Integer fileAddCount = fileUploadResultService.addFileUploadResult(fileUpResult);
			if (fileAddCount != null && fileAddCount > 0) {
				LOGGER.info("suceessfully insert into result table...");
			} else {
				LOGGER.info("failed insert into result table...");
			}
			b = "success";
		}
		return b;
	}

	private String dealReadTxt(List<String> fileList, String areaText,
			String formatDate) {
		String fileOutPutString = "";
		if (fileList != null && fileList.size() > 0) {
			for (String listString : fileList) {
				String[] strings = listString.split("\\|");
				fileOutPutString += areaText + SAPERATE + strings[0] + SAPERATE
						+ strings[1] + SAPERATE + WIDE_BAND_PROP + SAPERATE
						+ SAVE_FIELD + SAPERATE + CONSUMER_NAME + SAPERATE
						+ ADDRESS + SAPERATE + PHONE + SERVICE_NEWLINE;
			}
		}
		return outputStringToTxt(fileOutPutString, formatDate);
	}

	/**
	 * 返回待上传到ftp服务器的文件目录路径
	 * 
	 * @param fileOutPutString
	 * @return
	 */
	private String outputStringToTxt(String fileOutPutString, String formatDate) {
		String ftpFilePath = UPLOAD_PATH + formatDate + ".txt";
		try {
			OutputStreamWriter osr = new OutputStreamWriter(
					new FileOutputStream(ftpFilePath), "UTF-8");
			BufferedWriter bw = new BufferedWriter(osr);
			bw.write(fileOutPutString);
			bw.flush();
			bw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ftpFilePath;
	}

	/**
	 * @param filePath
	 *            读取用户上传的txt文件
	 */
	private List<String> readTxt(String filePath) {
		List<String> fileList = new ArrayList<String>();
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(
					filePath), "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String fileLine = null;
			while ((fileLine = br.readLine()) != null) {
				if (fileLine.equals("")) {
					continue;
				} else {
					fileList.add(fileLine);
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileList;
	}

	/**
	 * 进行查询搜索
	 * 
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/searchQuery", method = RequestMethod.POST)
	public String queryResult(@RequestParam("rows") String rows,
			@RequestParam("page") String page,
			@RequestParam("createNodeMark") String createNodeMark,
			@RequestParam("bgDate") String bgDate,
			@RequestParam("edDate") String edDate) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
		String bgStringDate = null;
		String edStringDate = null;
		Timestamp bgTimetamp = null;
		Timestamp edTimetamp = null;
		FileUploadResultEntity entity = new FileUploadResultEntity();
		try {
			if(bgDate != null && !"".equals(bgDate)){
				bgStringDate = sdf.format(new SimpleDateFormat("yyyy-MM-dd").parse(bgDate));
				entity.setBeginDate(Timestamp.valueOf(bgStringDate));
			}else {
				entity.setBeginDate(bgTimetamp);
			}
			if(edDate != null && !"".equals(edDate)){
				edStringDate = sdf.format(new SimpleDateFormat("yyyy-MM-dd").parse(edDate));
				entity.setEndDate(Timestamp.valueOf(edStringDate));
			}else {
				entity.setEndDate(edTimetamp);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		entity.setCityArea(createNodeMark);
		entity.setPageNo((Integer.parseInt(page) - 1) * Integer.parseInt(rows));
		entity.setPageSize(Integer.parseInt(page) * Integer.parseInt(rows));
		List<FileUploadResultEntity> fileUploads = fileUploadResultService
				.getFileUploadResultList(entity);
		int count = fileUploadResultService
				.getFileUploadResultListCount(entity);
		if (fileUploads != null) {
			jsonMap.put("total", count);
			jsonMap.put("rows", fileUploads);
		}
		return JSONObject.fromObject(jsonMap).toString();
	}

	/**
	 * 进行查询搜索
	 * 
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
	public void downloadFile(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("inputStr");
		FileUploadResultEntity entity = fileUploadResultService
				.getFileUploadResultById(id);
		String fileName = null;
		if (entity != null) {
			fileName = entity.getFileName();
		}
//		String[] headers = { "IPTV账号", "密码", "导入结果", "obs返回结果" };
		String[] headers = { "IPTV账号", "导入结果", "obs返回结果" };
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet s = wb.createSheet();
		wb.setSheetName(0, fileName + "上传返回结果详情");
		XSSFCellStyle style0 = wb.createCellStyle();
		style0.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		style0.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		XSSFFont front0 = wb.createFont();
		front0.setFontName("Arial");// 字体名称
		front0.setFontHeightInPoints((short) 10); // 字体大小
		front0.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体变粗
		style0.setFont(front0);

		XSSFRow row0 = s.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			this.insertExcel(row0, i, headers[i]);
		}
		Iterator<Cell> it0 = row0.cellIterator();
		while (it0.hasNext()) {
			XSSFCell cell = (XSSFCell) it0.next();
			cell.setCellStyle(style0);
		}
		List<FileUD> fileUDs = fileUDService.getFileUDListByFileName(fileName);
		if (fileUDs != null && fileUDs.size() > 0) {
			XSSFRow row = null;
			String status = "";
			for (int i = 0; i < fileUDs.size(); i++) {
				status = fileUDs.get(i).getStatus();
				if (status.equals("2")) {
					status = "完成";
				} else if (status.equals("1")) {
					status = "失败";
				} else if (status.equals("0")) {
					status = "还未导入";
				}
				row = s.createRow(i + 1);
				this.insertExcel(row, 0, fileUDs.get(i).getUserName());
//				this.insertExcel(row, 1, fileUDs.get(i).getPassWord());
				this.insertExcel(row, 1, status);
				this.insertExcel(row, 2, fileUDs.get(i).getResult());
			}
		}

		// 设置列宽
		for (int i = 0; i < 10; i++) {
			s.setColumnWidth((short) i, (short) (10 * 256));
		}

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		ByteArrayOutputStream os = null;
		try {
			os = new ByteArrayOutputStream();
			wb.write(os);
			byte[] content = os.toByteArray();
			InputStream is = new ByteArrayInputStream(content);
			response.reset();
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String((fileName + ".xls").getBytes(), "utf-8"));
			ServletOutputStream out = response.getOutputStream();
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
				bos.flush();
			}
			bis.close();
			bos.close();
			is.close();
			os.close();
		} catch (UnsupportedEncodingException e) {
			LOGGER.info("UnsupportedEncodingException: " + e);
			e.printStackTrace();
		} catch (IOException e) {
			LOGGER.info("IOException: " + e);
			e.printStackTrace();
		}

	}

	private void insertExcel(XSSFRow row, int cols, String value) {
		XSSFCell cell = row.createCell((short) cols);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		if (value != null && !"null".equals(value)) {
			cell.setCellValue(value);
		} else {
			cell.setCellValue("");
		}
	}
//`1Fenghj
	@ResponseBody
	@RequestMapping(value = "/searchUserName", method = RequestMethod.POST)
	public String queryUserName(@RequestParam("rows") String rows,
			@RequestParam("page") String page,
			@RequestParam("userName") String userName) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		List<FileUD> fileUDs = fileUDService.getFileUDSearchList(userName,(Integer.valueOf(page) - 1) * 10, Integer.valueOf(page) * 10);
		int count = fileUDService.getFileUDSearchListCount(userName);
		if (fileUDs != null) {
			jsonMap.put("total", count);
			jsonMap.put("rows", fileUDs);
		}
		return JSONObject.fromObject(jsonMap).toString();
	}
	
	@ResponseBody
	@RequestMapping(value = "/twoSearchUserName", method = RequestMethod.POST)
	public String twoSearchUserName(@RequestParam("rows") String rows,
			@RequestParam("page") String page,
			@RequestParam("userName") String userName) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		List<FileUD> fileUDs = fileUDService.getTwoFileUDSearchList(userName,(Integer.valueOf(page) - 1) * 10, Integer.valueOf(page) * 10);
		int count = fileUDService.getTwoFileUDSearchListCount(userName);
		if (fileUDs != null) {
			jsonMap.put("total", count);
			jsonMap.put("rows", fileUDs);
		}
		return JSONObject.fromObject(jsonMap).toString();
	}
	
	/**
	 * 
	 * @param file
	 *            用户上传的文件主体,修改IPOE账号密码处理主体
	 * @param req
	 * @param resp
	 */
	@ResponseBody
	@RequestMapping(value = "/getEditPwUpLoadFile", method = RequestMethod.POST)
	public Map<String, Object> getEditPwUpLoadFile(
			@RequestParam("editFile") CommonsMultipartFile file,
			@RequestParam("areaEditText") String areaText, HttpServletRequest req,
			HttpServletResponse resp) {
		Map<String,Object> map = null;
		LOGGER.info("-------------------------");
		String host = EDIT_PW_SOCKET_IP;
		int port = Integer.valueOf(EDIT_PW_SOCKET_PORT);
		DataInputStream dataIs = null;
		DataOutputStream dataOs = null;
		try {
			map = new HashMap<String, Object>();
			resp.setContentType(CONTENT_TYPE);
			Date date = new Date();
			String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(date);
			String filePath = EDIT_PASSWORD_PATH + fileName;
			dataIs = new DataInputStream(new BufferedInputStream(file.getInputStream()));
			dataOs = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)));
			byte[] bytes = new byte[1024];
			while (true) {
				int len = 0;
				if (dataIs != null) {
					len = dataIs.read(bytes);
				}
				if (len == -1) {
					break;
				}
				dataOs.write(bytes, 0, len);
			}
			dataOs.flush();
			dataIs.close();
			dataOs.close();
			
			//上传的文件，转成list
			List<String> fileList = readTxt(filePath);
			
			List<String> strDataList = dealEditReadTxt(fileList, fileName, host, port);
//			String strData = "";
//			if(strDataList != null && strDataList.size() > 0) {
//				for (int i = 0; i < strDataList.size(); i++) {
//					strData = strDataList.get(i);
//				}
//			}
			FileWriter output = new FileWriter(EDIT_PASSWORD_RESULT_PATH + fileName + ".result");
			BufferedWriter bf = new BufferedWriter(output);
			if(strDataList != null && strDataList.size() > 0) {
				for(String str : strDataList){
					bf.write(str + SERVICE_NEWLINE);
				}
			}
			bf.flush();
			bf.close();
			map.put("result", "success");
		} catch (FileNotFoundException e) {
			LOGGER.info("FileNotFoundException: " + e);
			e.printStackTrace();
			map.put("result", "failed");
		} catch (IOException e) {
			LOGGER.info("IOException: " + e);
			e.printStackTrace();
			map.put("result", "failed");
		}
		
		
		return map;
	}
	
	/**
	 * 处理文件里的数据，拼成一个结果字符串，再把结果字符串放到list里边
	 * @param fileList
	 * @param fileName
	 * @param host
	 * @param port
	 * @return
	 */
	private List<String> dealEditReadTxt(List<String> fileList, String fileName, String host, int port) {
		String fileOutPutString = "";
		//流水号
		String seriesNum = "";
		//账号
		String iptvAccount = "";
		//密码
		String iptvPassword = "";
		//节点
		String createNodeMark = "";
		
		List<String> resultList = new ArrayList<String>();
		for (int i = 0; i < fileList.size(); i++) {
			seriesNum = fileName + String.valueOf(i);
			String[] strings = fileList.get(i).split("\\|");
			iptvAccount = strings[0];
			iptvPassword = strings[1];
			FileUD fileUD = fileUDService.getEntityByIptvAccountAndStatus(iptvAccount, "2");
			if(fileUD != null) {
				if(fileUD.getCreateNodeMark() != null) {
					createNodeMark = "{" + fileUD.getCreateNodeMark() + "}";
				}else {
					createNodeMark = "{}";
				}
			}else {
				createNodeMark = "{}";
			}
			fileOutPutString += "set_iptv" + BLANK
							 + "{" + seriesNum +"}" + BLANK
							 + "{" + iptvAccount +"}" + BLANK
							 + createNodeMark + BLANK
							 + "{" + iptvAccount +"}" + BLANK
							 + "{" + iptvPassword +"}" + BLANK + SERVICE_NEWLINE;
			
			String result = "{" + iptvAccount +"}" + BLANK
					+ "{" + iptvPassword +"}" + BLANK
					+ sendData(fileOutPutString, host, port, iptvPassword, fileUD);
			resultList.add(result);
		}
		return resultList;
	}
	
	/**
	 * 取到OBS的修改密码的结果，是否成功，然后把结果返回~
	 * @param strData
	 * @param host
	 * @param port
	 * @param iptvPassword
	 * @param fileUD
	 * @return
	 */
	private String sendData(String strData,String host, int port, String iptvPassword, FileUD fileUD) {
		TelnetClient telnetClient = new TelnetClient("vt200");  //指明Telnet终端类型，否则会返回来的数据中文会乱码
//      telnetClient.setDefaultTimeout(5000); //socket延迟时间：5000ms
		StringBuilder sb = new StringBuilder();
		try {
			telnetClient.connect(host,port);  //建立一个连接,默认端口是23
			Writer writer = new OutputStreamWriter(telnetClient.getOutputStream());
			writer.write(strData);
			writer.flush();
			
			
			Reader reader = new InputStreamReader(telnetClient.getInputStream());
			char[] chars = new char[64];
			int len;
			String temp;
			int index;
			while((len = reader.read(chars)) != -1) {
				temp = new String(chars, 0, len);
				if((index = temp.indexOf(SERVICE_NEWLINE)) != -1) {
					sb.append(temp.substring(0, index));
					break;
				} 
				sb.append(temp); 
			}
			LOGGER.info("from server: " + sb);
			writer.close();
			reader.close();
			telnetClient.disconnect();
			String[] strs = sb.toString().split(" ");
			if(strs[1].equals("{OK}")) {
				fileUD.setPassWord(iptvPassword);
				Integer count = fileUDService.updateFileUDPwd(fileUD);
				if(count != 0) {
					LOGGER.info("update password success...");
				}else {
					LOGGER.info("update password failed...");
				}
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	
	/**
	 * 
	 * @param file
	 *            用户上传的文件主体，删除IPOE账号的处理主体
	 * @param req
	 * @param resp
	 */
	@ResponseBody
	@RequestMapping(value = "/getDelPwUpLoadFile", method = RequestMethod.POST)
	public Map<String, Object> getDelPwUpLoadFile(
			@RequestParam("delFile") CommonsMultipartFile file,
			@RequestParam("areaDelText") String areaText, HttpServletRequest req,
			HttpServletResponse resp) {
		Map<String,Object> map = null;
		LOGGER.info("-------------------------");
		String host = EDIT_PW_SOCKET_IP;
		int port = Integer.valueOf(EDIT_PW_SOCKET_PORT);
		DataInputStream dataIs = null;
		DataOutputStream dataOs = null;
		try {
			map = new HashMap<String, Object>();
			resp.setContentType(CONTENT_TYPE);
			Date date = new Date();
			String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(date);
			String filePath = DEL_PATH + fileName;
			dataIs = new DataInputStream(new BufferedInputStream(file.getInputStream()));
			dataOs = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)));
			byte[] bytes = new byte[1024];
			while (true) {
				int len = 0;
				if (dataIs != null) {
					len = dataIs.read(bytes);
				}
				if (len == -1) {
					break;
				}
				dataOs.write(bytes, 0, len);
			}
			dataOs.flush();
			dataIs.close();
			dataOs.close();
			
			//上传的文件，转成list
			List<String> fileList = readTxt(filePath);
			
			List<String> strDataList = dealDelReadTxt(fileList, fileName, host, port);
			FileWriter output = new FileWriter(DEL_RESULT_PATH + fileName + ".result");
			BufferedWriter bf = new BufferedWriter(output);
			if(strDataList != null && strDataList.size() > 0) {
				for(String str : strDataList){
					bf.write(str + SERVICE_NEWLINE);
				}
			}
			bf.flush();
			bf.close();
			map.put("result", "success");
		} catch (FileNotFoundException e) {
			LOGGER.info("FileNotFoundException: " + e);
			e.printStackTrace();
			map.put("result", "failed");
		} catch (IOException e) {
			LOGGER.info("IOException: " + e);
			e.printStackTrace();
			map.put("result", "failed");
		}
		
		
		return map;
	}
	
	/**
	 * 将删除结果返回到一个list里边
	 * @param fileList
	 * @param fileName
	 * @param host
	 * @param port
	 * @return
	 */
	private List<String> dealDelReadTxt(List<String> fileList, String fileName,
			String host, int port) {
		String fileOutPutString = "";
		//流水号
		String seriesNum = "";
		//账号
		String iptvAccount = "";
		//节点
		String createNodeMark = "";
		
		List<String> resultList = new ArrayList<String>();
		for (int i = 0; i < fileList.size(); i++) {
			seriesNum = fileName + String.valueOf(i);
			String[] strings = fileList.get(i).split("\\|");
			iptvAccount = strings[0];
			FileUD fileUD = fileUDService.getEntityByIptvAccountAndStatus(iptvAccount, "2");
			if(fileUD != null) {
				if(fileUD.getCreateNodeMark() != null) {
					createNodeMark = "{" + fileUD.getCreateNodeMark() + "}";
				}else {
					createNodeMark = "{}";
				}
			}else {
				createNodeMark = "{}";
			}
			fileOutPutString += "del_iptv" + BLANK
							 + "{" + seriesNum +"}" + BLANK
							 + "{" + iptvAccount +"}" + BLANK
							 + createNodeMark + BLANK
							 + "{" + iptvAccount +"}" + BLANK + SERVICE_NEWLINE;
			
			String result = "{" + iptvAccount +"}" + BLANK
					+ sendDelData(fileOutPutString, host, port, fileUD);
			resultList.add(result);
		}
		return resultList;
	}
	
	/**
	 * 把OBS返回的结果返回到一个文件里边~让用户去查询
	 * @param strData
	 * @param host
	 * @param port
	 * @param fileUD
	 * @return
	 */
	private String sendDelData(String strData,String host, int port, FileUD fileUD) {
		TelnetClient telnetClient = new TelnetClient("vt200");  //指明Telnet终端类型，否则会返回来的数据中文会乱码
//      telnetClient.setDefaultTimeout(5000); //socket延迟时间：5000ms
		StringBuilder sb = new StringBuilder();
		try {
			telnetClient.connect(host,port);  //建立一个连接,默认端口是23
			Writer writer = new OutputStreamWriter(telnetClient.getOutputStream());
			writer.write(strData);
			writer.flush();
			
			
			Reader reader = new InputStreamReader(telnetClient.getInputStream());
			char[] chars = new char[64];
			int len;
			String temp;
			int index;
			while((len = reader.read(chars)) != -1) {
				temp = new String(chars, 0, len);
				if((index = temp.indexOf(SERVICE_NEWLINE)) != -1) {
					sb.append(temp.substring(0, index));
					break;
				} 
				sb.append(temp); 
			}
			LOGGER.info("from server: " + sb);
			writer.close();
			reader.close();
			telnetClient.disconnect();
			String[] strs = sb.toString().split(" ");
			if(strs[1].equals("{OK}")) {
				fileUD.setStatus("3");//删除掉的
//				fileUD.setResult(strs[2].replace("{", "") + strs[3].replace("}", ""));
				fileUD.setResult("删除成功");
				Integer count = fileUDService.updateFileUDPwd(fileUD);
				if(count != 0) {
					LOGGER.info("delete IPOE " + fileUD.getUserName() + "success...");
				}else {
					LOGGER.info("delete IPOE " + fileUD.getUserName() + "failed...");
				}
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
}
