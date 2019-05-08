<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>微信扫码支付</title>
<script type="text/javascript">
function callpay() {
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
		WeixinJSBridge.invoke('getBrandWCPayRequest', {
			"appId":     "${appId}", //公众号名称，由商户传入
			"timeStamp": "${timeStamp}", //时间戳，自1970 年以来的秒数
			"nonceStr":  "${nonceStr}", //随机串
			"package":   "${package1}",
			"signType":  "${signType}", //微信签名方式:
			"paySign":   "${paySign}" //微信签名
		}, function (res) {
			window.location.href = "${url}";
				
			if (res.err_msg == 'get_brand_wcpay_request:ok') {
				//微信前端的支付成功结果是不一定可靠的，需要调用后台进行查询
			} else if (res.err_msg == 'get_brand_wcpay_request:cancel') {
				//用户撤销
			} else {
				//支付失败
			}
		});
	});
}
</script>
</head>
<body onload="javascript:callpay()">
</body>
</html>