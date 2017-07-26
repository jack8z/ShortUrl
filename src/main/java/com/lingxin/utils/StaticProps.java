package com.lingxin.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StaticProps {
	private static final Logger log = LoggerFactory.getLogger(StaticProps.class);

	private static StaticProps instance = null;

	private Properties prop = null;

	private StaticProps() {
		prop = new Properties();
		try {
			prop.load(StaticProps.class.getResourceAsStream("/static.properties"));
		} catch (IOException e) {
			log.error("加载数据库的配置信息时出错啦", e);
		}
	}

	public static StaticProps getInstance() {
		if (instance == null) {
			instance = new StaticProps();
		}
		return instance;
	}

	public String getProperty(String key) {
		String val = prop.getProperty(key);
		val = (null == val ? "" : val.trim());
		return val;
	}
}
