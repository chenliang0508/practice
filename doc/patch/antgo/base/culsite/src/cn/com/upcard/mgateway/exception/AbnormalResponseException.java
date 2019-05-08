package cn.com.upcard.mgateway.exception;

public class AbnormalResponseException extends GatewayBusinessException {

	private static final long serialVersionUID = -4377133507457977174L;

	public AbnormalResponseException(String message) {
		super(message);
	}

}
