package com.lingxin.dbtools;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbConfig {
	private static final Logger log = LoggerFactory.getLogger(DbConfig.class);

	private static DbConfig instance = null;

	private Properties prop = null;

	private DbConfig() {
		prop = new Properties();
		try {
			prop.load(DbConfig.class.getResourceAsStream("/dbconfig.properties"));
		} catch (IOException e) {
			log.error("加载数据库的配置信息时出错啦", e);
		}
	}

	public static DbConfig getInstance() {
		if (instance == null) {
			instance = new DbConfig();
		}
		return instance;
	}

	public String getDriverClassName() {
		return prop.getProperty("driver");
	}

	public String getUrl() {
		String url = prop.getProperty("url");
		url = (null == url ? "" : url.trim());
		try {
			url = new String(url.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error("加载配置文件[config.properties]里的url数据时出错啦 ", e);
		}
		return url;
	}

	public String getUsername() {
		return prop.getProperty("user");
	}

	public String getPassword() {
		return prop.getProperty("password");
	}
}
