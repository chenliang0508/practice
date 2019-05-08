package cn.com.upcard.mgateway.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.upcard.mgateway.common.Constants;
import cn.com.upcard.mgateway.common.enums.OrderOperType;
import cn.com.upcard.mgateway.dao.ApiLogDAO;
import cn.com.upcard.mgateway.entity.ApiLog;
import cn.com.upcard.mgateway.service.ApiLogService;
import cn.com.upcard.mgateway.util.DateUtil;
import cn.com.upcard.mgateway.util.StringTool;

@Service
public class ApiLogServiceImpl implements ApiLogService {
	
	@Autowired
	private ApiLogDAO apiLogDAO;
	private static Logger logger = LoggerFactory.getLogger(ApiLogServiceImpl.class);
	
	private static final int MAX_LOG_LENGTH = 12000;
	private static final int EACH_FIELD_LENGTH = 4000;
	
	public void saveApiLog(String orderId, OrderOperType apiType, String log) {
		logger.info("orderId:" + orderId);
		ApiLog apiLog = new ApiLog();
		apiLog.setOrderId(orderId);
		apiLog.setApiType(apiType.name());
		apiLog.setCreateTime(DateUtil.now());
		
		String utfLog = null;
		try {
		   utfLog = new String(log.getBytes(), Constants.DEFAULT_CHATSET);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		byte[] logBytes = utfLog.getBytes();
		if (logBytes.length > 8000) {
			String infoI = StringTool.subStrByByteSize(utfLog, EACH_FIELD_LENGTH);
			String infoIi = StringTool.subStrByByteSize(utfLog, infoI.length(), EACH_FIELD_LENGTH);
			String infoIii = StringTool.subStrByByteSize(utfLog, infoI.length() + infoIi.length(), EACH_FIELD_LENGTH);
			apiLog.setResInfoI(infoI);
			apiLog.setResInfoIi(infoIi);
			apiLog.setResInfoIii(infoIii);
		} else if (logBytes.length > 4000 && logBytes.length <= 8000) {
			String infoI = StringTool.subStrByByteSize(utfLog, EACH_FIELD_LENGTH);
			String infoIi = StringTool.subStrByByteSize(utfLog, infoI.length(), EACH_FIELD_LENGTH);
			apiLog.setResInfoI(infoI);
			apiLog.setResInfoIi(infoIi);
		} else {
			apiLog.setResInfoI(log);
		}
		apiLogDAO.save(apiLog);
	}
}
