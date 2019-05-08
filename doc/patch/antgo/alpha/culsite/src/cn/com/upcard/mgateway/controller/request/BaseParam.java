package cn.com.upcard.mgateway.controller.request;

import java.io.Serializable;

import cn.com.upcard.mgateway.annotion.HttpParamName;
import cn.com.upcard.mgateway.common.RequestParameter;
import cn.com.upcard.mgateway.common.Restrict;
import cn.com.upcard.mgateway.common.enums.Required;

public class BaseParam implements Serializable {
	private static final long serialVersionUID = 5114322204212084024L;

	@HttpParamName(name = RequestParameter.SERVICE, param = Required.REQUIRE, maxLen = 32, minLen = 1)
	protected String service;
	@HttpParamName(name = RequestParameter.SIGN_TYPE, param = Required.REQUIRE, maxLen = 10, minLen = 1, restrict = Restrict.SIGN_TYPE)
	protected String signType;
	@HttpParamName(name = RequestParameter.RETURN_TYPE, param = Required.REQUIRE, maxLen = 10, minLen = 1, restrict = Restrict.RETURN_TYPE)
	protected String returnType;
	@HttpParamName(name = RequestParameter.SIGN, param = Required.REQUIRE, minLen = 1)
	protected String sign;
	@HttpParamName(name = RequestParameter.MCH_ID, param = Required.REQUIRE, maxLen = 30)
	protected String mchId;
	@HttpParamName(name = RequestParameter.TIMESTAMP, param = Required.REQUIRE, restrict = Restrict.TIMESTAMP, minLen = 10, maxLen = 10)
	private String timestamp;
	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "BaseParam [service=" + service + ", signType=" + signType + ", returnType=" + returnType + ", sign="
				+ sign + ", mchId=" + mchId + ", timestamp=" + timestamp + "]";
	}
}
