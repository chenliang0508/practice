<%@page import="cn.com.upcard.mgateway.util.systemparam.SysPara"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String basePath = SysPara.MGATEWAY_HOST + "/";
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
<title>付款失败</title>
</head>
<body style="background-color:#fff;">
<div class="pay-ok">
     <div class="pay-ok01"><img src="<%=basePath%>images/qr/weixin-qr/e-wx.png"></div>
     <div class="pay-ok02">支付失败</div>
     <div class="pay-ok04">您支付的金额将原路退回</div>
     <div class="pay-ok05"><input  type="button" value="关闭窗口" onclick = "WeixinJSBridge.call('closeWindow');" ></div>
</div>
</body>
</html>