<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>支付宝扫码支付</title>
<script type="text/javascript">
function callpay() {
	AlipayJSBridge.call("tradePay", 
			{tradeNO:"${tradeNO}"},
			function(result) {
				console.log(result);
				if ("9000" == result.resultCode) {
					window.location.href = "${url}";
					//订单成功
				} else if ("8000" == result.resultCode) {
					//正在处理中
				} else if ("4000" == result.resultCode) {
					//支付失败
				} else if ("6001" == result.resultCode) {
					//用户中途取消
				} else if ("6002" == result.resultCode) {
					//网络出错
					alert("网络异常");
				} else if ("99" == result.resultCode) {
					//用户忘记密码，退出支付界面
				} else {
					//支付失败
				}
			});
}
</script>
</head>
<body onload="javascript:callpay()">
</body>
</html>