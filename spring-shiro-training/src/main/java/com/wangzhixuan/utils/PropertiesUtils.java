package com.wangzhixuan.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {

	private String url = "/conf.properties";
	private Properties prop = null;
	
	public PropertiesUtils() {
		init();
	}

	
	private void init() {
		try {
			InputStream is = PropertiesUtils.class.getResourceAsStream(url);
			this.prop = new Properties();
			prop.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Properties getProp() {
		return prop;
	}
	public void setProp(Properties prop) {
		this.prop = prop;
	}
	
	
}
