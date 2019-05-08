<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	if ("/".equals(path)) {
		path = "";
	}
	String port = String.valueOf(request.getServerPort());
	if ("80".equals(port)) {
		port = "";
	} else {
		port = ":" + port;
	}
	String basePath = "https://www.culdata.com:1443/mgateway/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/qr/qr-weixin.css"/>

<title>付款成功</title>
</head>
<body  style="background-color:#fff;">
	<div class="pay-ok">
     <div class="pay-ok01"><img src="<%=basePath%>images/qr/weixin-qr/ok-wx.png"></div>
     <div class="pay-ok02">支付成功</div>
     <div class="pay-ok03"><span>&yen;</span><fmt:formatNumber value="${totalAmount}" pattern = "0.00"/></div>
     <c:if test="${orderNo != null}">
     <div class="pay-ok04">订单后四位 ${orderNo}</div>
     </c:if>
     <div class="pay-ok05"><input type="button" value="完成" onclick = "WeixinJSBridge.call('closeWindow');"></div>
</div>


</body>
</html>