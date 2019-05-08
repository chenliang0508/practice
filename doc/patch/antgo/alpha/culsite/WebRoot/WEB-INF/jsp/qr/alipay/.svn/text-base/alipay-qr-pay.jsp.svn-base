<%@page import="cn.com.upcard.mgateway.util.systemparam.SysPara"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String basePath = SysPara.MGATEWAY_HOST + "/";
%>
<%--
* File:      wx-qr-pay.jsp
* Version:   1.0.0
* Author:    huatingzhou (www.upcard.com.cn)
* Date:      2017-09-07
* Copyright: www.Upcard.com.cn,all rights reserved.
*
* Remark:    支付宝二位码支付页面
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/qr/qr-alipay.css" />
<title>支付宝付款</title>
<script>
	function recharge() {
		var money = document.getElementById("moneyinputId").innerHTML;
		var a = /^[0-9]*(\.[0-9]{1,2})?$/;
		if (!a.test(money)) {
			alert("金额格式不正确!");
			return;
		}
		document.getElementById("keybosure").style.backgroundColor = "#badde0";
		document.getElementById("keybosure").onclick = '';
		document.getElementById("keybosure").innerHTML = "<br><br>正在<br>付款";
		document.getElementById("totalAmount").value = money;
		var form = document.getElementById("myform");
		form.submit();
	}

	function inputMoney(number) {
		var moneyNum = document.getElementById("moneyinputId").innerHTML;
		var length = moneyNum.length;
		if (number == 10) {
			document.getElementById("moneyinputId").innerHTML = moneyNum
					.substring(0, length - 1);
			var temp = document.getElementById("moneyinputId").innerHTML;

			if (temp == "" || temp == "0" || temp == "0." || temp == "0.0"
					|| temp == "0.00") {
				document.getElementById("keybosure").style.backgroundColor = "#badde0";
				document.getElementById("keybosure").onclick = '';

			}

		} else if (number == 12) { //小数点
			if (length == 0)
				return;
			else if (moneyNum.indexOf('.') > 0)
				return;
			document.getElementById("moneyinputId").innerHTML = moneyNum + ".";

		} else {
			var pointPlace = moneyNum.indexOf('.');
			if (pointPlace > 0) {
				if (moneyNum.substring(0, pointPlace).length + 1 > 6)
					return;
				else if (moneyNum.substring(pointPlace, length + 1).length > 2)
					return;
			} else {
				if (length + 1 > 6)
					return;
				else if (moneyNum == "0") {
					if (number == 0)
						return;
					moneyNum = "";
				}
			}
			document.getElementById("moneyinputId").innerHTML = moneyNum
					+ number;
			if (number == 0)
				return;
			document.getElementById("keybosure").style.backgroundColor = "#108ee9";
			document.getElementById("keybosure").onclick = recharge;
		}
	}

	function getKeyNum(object) {
		object && object.addEventListener("click", function() {
			var _this = this;
			this.className = "touched";
			setTimeout(function() {
				_this.className = "";
			}, 100);

			var num = this.dataset.num;
			inputMoney(num);
		});
	}

	window.onload = function() {
		var newtken = document.getElementById("newtken");
		var cover = document.getElementById("cover");
		newtken.style.display = 'none';
		cover.style.display = 'none';

		var keyBoard = document.getElementsByClassName("key-board");
		for ( var i in keyBoard) {
			getKeyNum(keyBoard[i]);
		}
	}
</script>
</head>
<body>
	<div class="newtken" id="newtken">加载中...</div>
	<div class="cover" id="cover"></div>
	<div class="pay-box">
		<div class="pay-top">
			<span class="pay-top01"><img
				src="<%=basePath%>images/qr/alipay-qr/logo-zfb.png" /></span>
			<div class="pay-top02">${mchName}</div>
		</div>
		<div class="pay-bot">
			<div class="pay-bot01">支付金额</div>
			<div class="moneybox">
				<span class="pay-bot02">&yen;</span> <span id="moneyinputId"
					class="pay-bot03"></span> <span class="pay-bot04" id="inimg"></span>
			</div>

		</div>
	</div>
	<div class="pmess">由银商资讯提供技术支持</div>
	<div class="keyboardbox">
		<div>
			<span class="key-board " data-num="1"
				style="border-bottom: 1px solid #E0E0E0; border-right: 1px solid #E0E0E0;">1</span>
			<span class="key-board " data-num="2"
				style="border-bottom: 1px solid #E0E0E0; border-right: 1px solid #E0E0E0; left: 24%;">2</span>
			<span class="key-board " data-num="3"
				style="border-bottom: 1px solid #E0E0E0; border-right: 1px solid #E0E0E0; left: 48%;">3</span>
			<span class="key-board" data-num="10"
				style="border-bottom: 1px solid #E0E0E0; right: 0; width: 28%;"><img
				src="<%=basePath%>images/qr/alipay-qr/del.png"
				style="height: 1.375rem; width: 1.875rem; margin: auto; margin-top: 1.0625rem; display: block;" /></span>
			<span class="key-board " data-num="4"
				style="border-bottom: 1px solid #E0E0E0; border-right: 1px solid #E0E0E0; top: 3.5rem;">4</span>
			<span class="key-board " data-num="5"
				style="border-bottom: 1px solid #E0E0E0; border-right: 1px solid #E0E0E0; top: 3.5rem; left: 24%;">5</span>
			<span class="key-board " data-num="6"
				style="border-bottom: 1px solid #E0E0E0; border-right: 1px solid #E0E0E0; top: 3.5rem; left: 48%;">6</span>
			<span id="keybosure"
				style="color: white; background-color: #badde0; height: 10.5rem; bottom: 0; right: 0; width: 28%; line-height: 1.75rem; font-size: 20px;"><a
				style="display: block; margin-top: 3.5rem;">确认<br>支付</a></span> <span
				class="key-board " data-num="7"
				style="border-bottom: 1px solid #E0E0E0; border-right: 1px solid #E0E0E0; top: 7rem;">7</span>
			<span class="key-board " data-num="8"
				style="border-bottom: 1px solid #E0E0E0; border-right: 1px solid #E0E0E0; top: 7rem; left: 24%;">8</span>
			<span class="key-board " data-num="9"
				style="border-bottom: 1px solid #E0E0E0; border-right: 1px solid #E0E0E0; top: 7rem; left: 48%;">9</span>
			<span class="key-board " data-num="0"
				style="border-right: 1px solid #E0E0E0; top: 10.5rem; width: 48%;">0</span>
			<span class="key-board " data-num="12"
				style="border-right: 1px solid #E0E0E0; top: 10.5rem; left: 48%;">.</span>
		</div>
	</div>
	<form id="myform"
		action="<%=basePath%>gateway?service=unified.qrcode.pre" method="post">
		<input type="hidden" name="timestamp" value="${timestamp}" /> <input
			type="hidden" name="qrToken" value="${qrToken}" /> <input
			type="hidden" name="mchId" value="${mchId}" /> <input type="hidden"
			name="sign" value="${sign}" /> <input type="hidden" id="totalAmount"
			name="totalAmount" value="" /> <input type="hidden" name="reqIp"
			value="127.0.0.1" /> <input type="hidden" name="body"
			value="银商静态二维码" /> <input type="hidden" name="thirdBuyerId"
			value="${thirdBuyerId}" />
	</form>
</body>
</html>