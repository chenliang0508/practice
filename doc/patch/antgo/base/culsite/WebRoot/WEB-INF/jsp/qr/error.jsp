<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<link rel="stylesheet" type="text/css" href="css/qr/qr-alipay.css"/>
<title>扫码支付失败</title>
<script>
	function closeWin() {
		//判断浏览器类型，选择不同的关闭方式
		var userAgent = navigator.userAgent.toLowerCase();
		console.log(userAgent);
		if (userAgent.indexOf("alipay") > -1) {
		    AlipayJSBridge.call('closeWebview');  //支付宝 
		} else if (userAgent.indexOf("microMessenger") > -1) {
		    WeixinJSBridge.call('closeWindow');//微信
		} else if (userAgent.indexOf("baidu") > -1) {
			BLightApp.closeWindow();//百度钱包 
		} else if (userAgent.indexOf("qq") > -1) {
			mqq.ui.popBack();
		} else {
			window.close();
		}		
	}

</script>        
</head>
<body style="background-color:#fff;">
<div class="pay-ok">
     <div class="pay-ok01"><img src="images/qr/alipay-qr/e-zfb.png"></div>
     <div class="pay-ok02">${msg }</div>
     <div class="pay-ok05"><input name="" type="button" value="关闭" onclick = "closeWin();"></div>
</div>
</body>
</html>