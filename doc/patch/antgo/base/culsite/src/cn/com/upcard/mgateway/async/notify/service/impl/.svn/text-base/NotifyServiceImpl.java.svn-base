package cn.com.upcard.mgateway.async.notify.service.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

import cn.com.upcard.mgateway.async.notify.model.NotifyPara;
import cn.com.upcard.mgateway.async.notify.service.NotifyService;
import cn.com.upcard.mgateway.async.notify.thread.NotifyThread;
import cn.com.upcard.mgateway.async.notify.thread.pool.NotifyExcecutor;
import cn.com.upcard.mgateway.async.notify.thread.pool.NotifyTaskExcecutor;
import cn.com.upcard.mgateway.dao.PushInfoDAO;
import cn.com.upcard.mgateway.entity.PushInfo;
import cn.com.upcard.mgateway.util.DateUtil;
import cn.com.upcard.mgateway.util.HttpClient;

@Service
public class NotifyServiceImpl implements NotifyService {

	private static Logger logger = LoggerFactory.getLogger(NotifyServiceImpl.class);

	@Autowired
	private PushInfoDAO pushInfoDAO;

	@Override
	public void executeNotifyThread(NotifyPara notifyPara) {
		NotifyThread notifyThread = new NotifyThread(notifyPara);
		NotifyExcecutor.getNotifythreadpool().execute(notifyThread);
	}

	@Override
	public void executeNotifyThreadForTask(NotifyPara notifyPara) {
		NotifyThread notifyThread = new NotifyThread(notifyPara);
		NotifyTaskExcecutor.getNotifythreadpool().execute(notifyThread);
	}

	@Transactional(timeout = 30)
	@Override
	public void sendNotice(NotifyPara notifyPara) {

		logger.info("开始向商户推送支付结果");

		// 定时任务多次推送时，对记录进行加锁，第一次推送的消息不受影响
		PushInfo oldInfo = null;
		if (0 != notifyPara.getPushedNumber()) {
			oldInfo = pushInfoDAO.findByOrderIdForUpdate(notifyPara.getOrderId());
		}

		Map<String, String> contentMap = notifyPara.getContentMap();
		logger.info("notify_url={}", notifyPara.getUrl());
		logger.info("contentMap={}", contentMap);

		HttpClient httpClient = new HttpClient(notifyPara.getUrl(), 2000, 3000);
		String result = null;
		try {
			httpClient.send(contentMap, "UTF-8");
			result = httpClient.getResult();
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		logger.info("result={}", result);

		// 本次推送完后已经推送的次数
		int alreadyPushNumber = notifyPara.getPushedNumber() + 1;

		PushInfo pushInfo = new PushInfo();
		pushInfo.setPushNumber(String.valueOf(alreadyPushNumber));

		if (result != null && "success".equals(result)) {
			// 状态设置成功
			pushInfo.setStatus("Y");
		} else {
			// 推送失败，记录再次推送时间
			String nextPushTime = null; // 下次将要推送的时间
			try {
				nextPushTime = getNextPushTime(alreadyPushNumber, notifyPara.getPushTime());
				logger.info("prePushTime={} , nextPushTime={}", notifyPara.getPushTime(), nextPushTime);
			} catch (ParseException e) {
				logger.error("ParseException", e);
				logger.error("alreadyPushNumber={} , pushTime={}", alreadyPushNumber, notifyPara.getPushTime());
			}

			// 11次后不再推送
			if (11 == alreadyPushNumber) {
				pushInfo.setStatus("F");
			} else {
				pushInfo.setStatus("W");
			}

			pushInfo.setPushTime(nextPushTime);
		}

		if (0 == notifyPara.getPushedNumber()) {
			// 首次推送，插入数据到推送表
			pushInfo.setOrderId(notifyPara.getOrderId());
			pushInfo.setContent(JSONObject.toJSONString(contentMap));
			pushInfo.setCreateTime(new Date());
			pushInfo.setNotifyUrl(notifyPara.getUrl());
			pushInfoDAO.save(pushInfo);
		} else {
			// 再次推送后，更新表数据
			oldInfo.setPushNumber(pushInfo.getPushNumber());
			oldInfo.setStatus(pushInfo.getStatus());
			if (null != pushInfo.getPushTime()) {
				oldInfo.setPushTime(pushInfo.getPushTime());
			}
			oldInfo.setUpdateTime(new Date());
			pushInfoDAO.save(oldInfo);
		}

		logger.info("推送结束");
	}

	/**
	 * 根据当前推送的次数和当前推送的时间，获取下次推送的时间
	 * 
	 * @param pushedNumber:已推送的次数
	 * @param currentTime:当前推送时间
	 * @return
	 * @throws ParseException
	 */
	private String getNextPushTime(int pushedNumber, String currentTime) throws ParseException {

		String nextPushTime = null;

		switch (pushedNumber) {
		case 1:
			// 第二次增加10秒
			nextPushTime = DateUtil.addSeconds(currentTime, DateUtil.yyyyMMddHHmmss, 10);
			break;

		case 2:
			// 第三次增加30秒
			nextPushTime = DateUtil.addSeconds(currentTime, DateUtil.yyyyMMddHHmmss, 30);
			break;

		case 3:
			// 第四次增加30秒
			nextPushTime = DateUtil.addSeconds(currentTime, DateUtil.yyyyMMddHHmmss, 30);
			break;

		case 4:
			// 第五次增加180秒
			nextPushTime = DateUtil.addSeconds(currentTime, DateUtil.yyyyMMddHHmmss, 180);
			break;

		case 5:
			// 第六次增加1800秒
			nextPushTime = DateUtil.addSeconds(currentTime, DateUtil.yyyyMMddHHmmss, 1800);
			break;

		case 6:
			// 第七次增加1800秒
			nextPushTime = DateUtil.addSeconds(currentTime, DateUtil.yyyyMMddHHmmss, 1800);
			break;

		case 7:
			// 第八次增加1小时
			nextPushTime = DateUtil.addHours(currentTime, DateUtil.yyyyMMddHHmmss, 1);
			break;

		case 8:
			// 第九次增加2小时
			nextPushTime = DateUtil.addHours(currentTime, DateUtil.yyyyMMddHHmmss, 2);
			break;

		case 9:
			// 第十次增加5小时
			nextPushTime = DateUtil.addHours(currentTime, DateUtil.yyyyMMddHHmmss, 5);
			break;

		case 10:
			// 第十一次增加5小时
			nextPushTime = DateUtil.addHours(currentTime, DateUtil.yyyyMMddHHmmss, 5);
			break;

		default:
			// 十一次后不再推送
			nextPushTime = currentTime;
			break;
		}

		return nextPushTime;
	}

}
