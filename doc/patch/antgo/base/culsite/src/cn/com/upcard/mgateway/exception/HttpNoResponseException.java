package cn.com.upcard.mgateway.exception;

/**
 * <pre>
 * http请求无响应异常，与具体的业务逻辑无关
 * 
 * </pre>
 * @author huatingzhou
 * @version 1.0
 */
public class HttpNoResponseException extends GatewayBusinessException {

	private static final long serialVersionUID = 5719005803883306461L;
	
	public HttpNoResponseException(String message) {
		super(message);
	}
}
