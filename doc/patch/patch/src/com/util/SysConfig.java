package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;

public final class SysConfig {

	private static Properties configs = new Properties();
	public static String BASE_PATH = null;
	public static String SVN_NAME = null;
	public static String SVN_PWD = null;
	public static String JDBC_DRIVER = null;
	public static String JDBC_URL = null;
	public static String JDBC_USER = null;
	public static String JDBC_PASSWORD = null;
	public static String MAVEN_JAR = null;
	public static String TARGET_VER = null;
	public static String SOURCE_VER = null;

	static {
		init();
	}

	private static void init() {
		File config = new File("D:\\tools\\patch2.0\\patch\\patch\\src\\config.properties");
		try {
			configs.load(new InputStreamReader(new FileInputStream(config), "UTF-8"));
			BASE_PATH = configs.getProperty("BASE_PATH");
			SVN_NAME = configs.getProperty("SVN_NAME");
			SVN_PWD = configs.getProperty("SVN_PWD");
			JDBC_DRIVER = configs.getProperty("JDBC_DRIVER");
			JDBC_URL = configs.getProperty("JDBC_URL");
			JDBC_USER = configs.getProperty("JDBC_USER");
			JDBC_PASSWORD = configs.getProperty("JDBC_PASSWORD");
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public static void refresh() {
		 init();
	}
	
	@SuppressWarnings("resource")
	public static void setMavenJar() {
		try {
			File file = new File(System.getenv().get("MAVEN_HOME")+"\\conf\\settings.xml");
			InputStreamReader inBuff = new InputStreamReader(new FileInputStream(file),"UTF-8");
			String text = "";
			char[] b = new char[1024*15]; 
			while (inBuff.read(b) != -1) {  
				text += String.valueOf(b);  
			} 
			Document document = DocumentHelper.parseText(text.trim());
			MAVEN_JAR = document.getRootElement().element("localRepository").getTextTrim();
		}  catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public static void setVersion(String target, String source) {
		TARGET_VER = target;
		SOURCE_VER = source;
	}
}