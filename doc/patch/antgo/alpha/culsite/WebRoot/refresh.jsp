<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="cn.com.upcard.mgateway.util.systemparam.SystemParamLoader"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>refresh properties</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

</head>
<%
	boolean flag = true;
	try {
		SystemParamLoader.init();
	} catch (Exception e) {
		flag = false;
		e.printStackTrace();
	}
	String msg = flag ? "refresh success" : "refresh fail";
	out.print("<div>" + msg + "</div>");
	System.out.println(msg);
%>
<body>
	<br>
</body>
</html>
