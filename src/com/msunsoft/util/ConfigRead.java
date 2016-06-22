package com.msunsoft.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>公司名称 :山东众阳软件有限公司 </p>
 * <p>项目名称 : IMDataServer</p>
 * <p>创建时间 : 2016年5月19日 下午4:36:30</p>
 * <p>类描述 :     读取配置文件    </p>
 *
 *
 * @version 1.0.0
 * @author <a href=" ">chenyanjun</a>
 */
public class ConfigRead {
	public static String is_encrypt = "false";

	static {
		is_encrypt = ConfigRead.getValue("im.properties", "IS_ENCRYPT");
	}

	public static String getValue(String configfile, String key) {
		String value = null;
		if (!StringUtils.isBlank(configfile)) {
			InputStream inputStream = ConfigRead.class.getClassLoader().getResourceAsStream(configfile);
			Properties p = new Properties();
			try {
				p.load(inputStream);
				value = p.getProperty(key, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return value;
	}

	public static Properties getProperties(String configfile) {

		Properties p = null;
		if (!StringUtils.isEmpty(configfile)) {
			InputStream inputStream = ConfigRead.class.getClassLoader().getResourceAsStream(configfile);
			p = new Properties();
			try {
				p.load(inputStream);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return p;
	}

	public static void main(String[] args) {
		System.out.println(ConfigRead.getValue("im.properties", "IP"));
	}
}
