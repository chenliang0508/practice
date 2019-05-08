package cn.com.upcard.mgateway.channel.bank.lkl;

import org.apache.commons.lang3.StringUtils;

import cn.com.upcard.mgateway.channel.enums.ChannelResponseResult;
/**
 * <pre>
 * 拉卡拉收单机构的错误码
 * 
 * 只包含几种特殊的错误信息，没有包含拉卡拉的所有错误码类型
 * 
 * </pre>
 * @author huatingzhou
 *
 */
public enum LklError {
//	<Item POSCOD="00" MSG="交易成功"/>     
//	<Item POSCOD="01" MSG="查询发卡方"/>     
//	<Item POSCOD="02" MSG="CALL BANK 查询"/>     
//	<Item POSCOD="03" MSG="无效商户"/>     
//	<Item POSCOD="05" MSG="不承兑"/>     
//	<Item POSCOD="10" MSG="承兑部分金额"/>     
//	<Item POSCOD="12" MSG="无效交易"/>     
//	<Item POSCOD="13" MSG="无效金额"/>     
//	<Item POSCOD="14" MSG="无此卡号"/>     
//	<Item POSCOD="19" MSG="稍候重做交易"/>     
//	<Item POSCOD="23" MSG="不能接受的交易费"/> 
//	<Item POSCOD="24" MSG="接收者不支持"/> 
//	<Item POSCOD="25" MSG="记录不存在"/> 
//	<Item POSCOD="26" MSG="重复的文件更新记录"/> 
//	<Item POSCOD="27" MSG="文件更新域错"/> 
//	<Item POSCOD="28" MSG="文件锁定"/>     
//	<Item POSCOD="29" MSG="文件更新不成功"/> 
//	<Item POSCOD="30" MSG="格式错误"/> 
//	<Item POSCOD="31" MSG="交换站不支持代理方"/> 
//	<Item POSCOD="33" MSG="到期卡, 请没收"/> 
//	<Item POSCOD="34" MSG="舞弊嫌疑, 请没收"/> 
//	<Item POSCOD="35" MSG="与受卡行联系"/> 
//	<Item POSCOD="36" MSG="黑名单卡, 没收"/> 
//	<Item POSCOD="40" MSG="请求的功能尚不支持"/> 
//	<Item POSCOD="41" MSG="遗失卡, 请没收"/> 
//	<Item POSCOD="43" MSG="被盗卡,请没收"/> 
//	<Item POSCOD="51" MSG="余额不足"/> 
//	<Item POSCOD="53" MSG="帐户不存在"/> 
//	<Item POSCOD="54" MSG="过期卡"/> 
//	<Item POSCOD="55" MSG="密码不符"/> 
//	<Item POSCOD="56" MSG="无卡记录"/> 
//	<Item POSCOD="57" MSG="持卡人无效交易"/> 
//	<Item POSCOD="58" MSG="终端无效交易"/> 
//	<Item POSCOD="59" MSG="舞蔽嫌疑"/> 
//	<Item POSCOD="61" MSG="超限额"/> 
//	<Item POSCOD="65" MSG="交易次数超限"/> 
//	<Item POSCOD="68" MSG="接收超时"/> 
//	<Item POSCOD="75" MSG="超过密码次数"/> 
//	<Item POSCOD="76" MSG="不允许手输卡号"/> 
//	<Item POSCOD="78" MSG="有效期错"/> 
//	<Item POSCOD="79" MSG="帐务处理超时"/> 
//	<Item POSCOD="80" MSG="MAC不正确"/> 
//	<Item POSCOD="81" MSG="网间MAC不正确"/> 
//	<Item POSCOD="82" MSG="返回码未定义"/> 
//	<Item POSCOD="83" MSG="无效终端"/> 
//	<Item POSCOD="84" MSG="限本地卡"/> 
//	<Item POSCOD="85" MSG="限异地信用卡"/> 
//	<Item POSCOD="86" MSG="单笔核对有误"/> 
//	<Item POSCOD="88" MSG="网络连接失败"/> 
//	<Item POSCOD="89" MSG="操作员密码错"/> 
//	<Item POSCOD="90" MSG="系统暂停"/> 
//	<Item POSCOD="91" MSG="交换站未操作"/> 
//	<Item POSCOD="92" MSG="找不到交易终点"/> 
//	<Item POSCOD="93" MSG="交易违法"/> 
//	<Item POSCOD="95" MSG="对帐不平"/> 
//	<Item POSCOD="96" MSG="系统故障"/>
//	<Item POSCOD="C0" MSG="支付等待中"/> 
//	<Item POSCOD="C1" MSG="无效二维码"/> 

	SUCCESS("000000", ChannelResponseResult.SUCCESS),
	INVALID_MCHID("010003", ChannelResponseResult.SHOP_ID_INVALID),
	INVALID_TRADE("010012", ChannelResponseResult.SHOP_ID_INVALID),
	INVALID_AMOUNT("010013", ChannelResponseResult.RESULT_FAIL),
	CARD_NOT_EXIST("010014", ChannelResponseResult.CARD_NOT_EXISTS),
	RECOARD_NOT_EXIST("010025", ChannelResponseResult.OGR_TRADE_NOT_EXISTS),
	BALANCE_NOT_ENOUGH("010051", ChannelResponseResult.BALANCE_NOT_ENOUGH),
	PASSWORD_ERROR("010055", ChannelResponseResult.PASSWORD_ERROR),
	BUYER_OVER_LIMIT_ERROR("001061", ChannelResponseResult.BUYER_OVER_LIMIT_ERROR),
	HANDLE_TIME_EXPIRE("001079", ChannelResponseResult.HANDLE_TIME_EXPIRE),
	INVALID_TIEM("001083", ChannelResponseResult.INVALID_TIEM),
	SYSTEM_ERROR("001096", ChannelResponseResult.UNKNOWN_SYSTEM_ERROR),
	PAYING("0010C0", ChannelResponseResult.PAYING),
	INVALID_AUTH_CODE("0010C1", ChannelResponseResult.AUTO_CODE_INVALID),
	PAY_FAIL("NULL", ChannelResponseResult.SYSTEM_ERROR);
	
	
	private LklError(String lklErrorCode, ChannelResponseResult channelResponseResult) {
		this.lklErrorCode = lklErrorCode;
		this.channelResponseResult = channelResponseResult;
	}
	private String lklErrorCode;
	private ChannelResponseResult channelResponseResult;

	public ChannelResponseResult getChannelResponseResult() {
		return channelResponseResult;
	}

	public void setChannelResponseResult(ChannelResponseResult channelResponseResult) {
		this.channelResponseResult = channelResponseResult;
	}
	
	public ChannelResponseResult toChannelResponseResult() {
		return this.channelResponseResult;
	}
	
	public static LklError toLklError(String lklErrorCode) {
		if (StringUtils.isEmpty(lklErrorCode)) {
			return null;
		}
		
		for (LklError lklError : LklError.values()) {
			if (lklError.lklErrorCode.equals(lklErrorCode)) {
				return lklError;
			}
		}
		return PAY_FAIL;
	}
}
