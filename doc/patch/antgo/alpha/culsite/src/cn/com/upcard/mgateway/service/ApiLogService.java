package cn.com.upcard.mgateway.service;

import cn.com.upcard.mgateway.common.enums.OrderOperType;

public interface ApiLogService {
	/**
	 * <pre>
	 * 保存收单机构api返回结果，返回结果超出12000个字节的部分直接丢弃。
	 * 
	 * api的返回结果保存在三个字段中，每个字段最长4000个字节，截取时已经处理最后一个字符的乱码问题，因此
	 * 每个字段实际存储的字节数量是小于4000的。
	 * </pre>
	 * @param orderId 订单号
	 * @param apiType api类型，用于区分接口类型
	 * @param log api返回结果
	 */
	void saveApiLog(String orderId, OrderOperType apiType, String log);
}
