package cn.com.upcard.mgateway.controller.response;

import cn.com.upcard.mgateway.common.enums.ResponseResult;
import cn.com.upcard.mgateway.model.CodeMsg;

public class QRCodePreResponse extends BaseResponse {

	private static final long serialVersionUID = 4406333532707798539L;
	
	private Object payInfo;
	private String payUrl;
	private String mchId;
	
	public QRCodePreResponse() {
	}
	public QRCodePreResponse(ResponseResult result) {
		super(result);
	}
	public QRCodePreResponse(String code, String msg) {
		super(code, msg);
	}
	
	public QRCodePreResponse(CodeMsg<?> codeMsg) {
		super(codeMsg);
	}
	
	public Object getPayInfo() {
		return payInfo;
	}
	public void setPayInfo(Object payInfo) {
		this.payInfo = payInfo;
	}
	public String getPayUrl() {
		return payUrl;
	}
	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}
	public String getMchId() {
		return mchId;
	}
	public void setMchId(String mchId) {
		this.mchId = mchId;
	}
}
