package cn.com.upcard.mgateway.controller.response;

public class WxJsResponse extends BaseResponse {

	private static final long serialVersionUID = 8316704955426096129L;

	private String mchId;
	private String deviceInfo;
	private String payInfo;

	public WxJsResponse() {
		super();
	}

	public String getPayInfo() {
		return payInfo;
	}

	public void setPayInfo(String payInfo) {
		this.payInfo = payInfo;
	}

	public WxJsResponse(String code, String msg) {
		super(code, msg);
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "WxJsResponse [deviceInfo=" + deviceInfo + ", payInfo=" + payInfo + "]";
	}

}
