package cn.com.upcard.mgateway.exception;

public class UndefinedTradeTypeException extends GatewayBusinessException {
	
	private static final long serialVersionUID = 3101610293480713556L;

	public UndefinedTradeTypeException(String message) {
		super(message);
	}

}
