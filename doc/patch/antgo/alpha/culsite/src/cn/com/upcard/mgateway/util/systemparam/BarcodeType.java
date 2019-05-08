package cn.com.upcard.mgateway.util.systemparam;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.upcard.mgateway.common.enums.TradeType;

public class BarcodeType {
	private static final Map<String, Pattern> barcodePattern = new HashMap<String, Pattern>();
	private static final Logger logger = LoggerFactory.getLogger(BarcodeType.class);
	public static void init() {
		Properties prop = new Properties();
		InputStream in = SysPara.class.getResourceAsStream("/barcodeType.properties");
		try {
			logger.info("----------------开始加载条码配置文件----------------------");
			prop.load(in);
			Set<String> propretyNames = prop.stringPropertyNames();
			for (String propertyName : propretyNames) {
				logger.info("{}--:{}", propertyName, prop.getProperty(propertyName));
				Pattern p = Pattern.compile(prop.getProperty(propertyName));
				barcodePattern.put(propertyName, p);
			}
			logger.info("----------------结束加载条码配置文件----------------------");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static TradeType route(String barcode) {
		if (StringUtils.isEmpty(barcode)) {
			return null;
		}
		for (Map.Entry<String, Pattern> barcodeP : barcodePattern.entrySet()) {
			if (barcode.matches(barcodeP.getValue().toString())) {
				return TradeType.toTradeType(barcodeP.getKey());
			}
		}
		return null;
	}
	
	@Test
	public void mssain() {
		init();
	}
}
