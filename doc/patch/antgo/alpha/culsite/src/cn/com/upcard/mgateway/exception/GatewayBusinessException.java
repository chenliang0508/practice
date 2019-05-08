package cn.com.upcard.mgateway.exception;
/**
 * <pre>
 * 作为业务相关的异常的基类
 * 
 * </pre>
 * @author huatingzhou
 * @version 1.0
 */
public class GatewayBusinessException extends RuntimeException {

	private static final long serialVersionUID = -8024147563781743259L;

	public GatewayBusinessException(String message) {
		super(message);
	}
}
