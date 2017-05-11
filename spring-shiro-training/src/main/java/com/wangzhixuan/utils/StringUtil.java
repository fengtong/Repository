package com.wangzhixuan.utils;

public class StringUtil {

	public static boolean isEmpty(String str){
		if(str==null || "".equals(str)){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isNotEmpty(String str){
		if(str!=null && !"".equals(str)){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean existStrArr(String str,String []strArr){
		for(int i=0;i<strArr.length;i++){
			if(strArr[i].equals(str)){
				return true;
			}
		}
		return false;
	}
}
