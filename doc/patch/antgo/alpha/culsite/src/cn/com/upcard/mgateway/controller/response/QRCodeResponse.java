package cn.com.upcard.mgateway.controller.response;

import cn.com.upcard.mgateway.common.enums.ResponseResult;

public class QRCodeResponse extends BaseResponse{

	private static final long serialVersionUID = 1515747617046719143L;
	private String qrCodeUrl;
	
	public QRCodeResponse() {
	}
	
	public QRCodeResponse(ResponseResult result) {
		super(result);
	}
	public String getQrCodeUrl() {
		return qrCodeUrl;
	}
	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}
}
