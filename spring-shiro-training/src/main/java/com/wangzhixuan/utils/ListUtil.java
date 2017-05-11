package com.wangzhixuan.utils;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;

public class ListUtil {

	/**
	 * 判空
	 * @param list
	 * @return
	 */
	public static boolean listIsEmpty(List<T> list) {
		if(list != null && list.size() > 0) {
			return true;
		}else {
			return false;
		}
	}
	
}
