<%--
  Created by IntelliJ IDEA.
  Author: yuyaying
  Project: java_web
  Date: 2018/7/10
  Time: 0:13
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Language" content="zh-cn">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="description" content="杰普电子商务门户">
    <title>杰普电子商务门户</title>
    <LINK href="css/main.css" rel="stylesheet" type="text/css" charset="UTF-8">
    <script language = "JavaScript" src = "js/main.js" charset="UTF-8" type="javascript"></script>
</head>
<body onLoad="MM_preloadImages('images/index_on.gif','images/reg_on.gif','images/order_on.gif','../images/top/topxmas/jp_on.gif','../images/top/topxmas/download_on.gif','../images/top/topxmas/bbs_on.gif','../images/top/topxmas/designwz_on.gif')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0" id="table2">
    <tr>
        <td align="left" width="7%" background="images/top_bg.gif"><img src="images/logo.gif" width="165" height="60"></td>
        <td width="62%" background="images/top_bg.gif">　</td>
        <td width="31%" background="images/top_bg.gif" align="right">
            <img src="images/top_r.gif" width="352" height="58" border="0"></td>
    </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td background="images/dh_bg.gif" align="left" height="12">
            <table width="100" border="0" cellspacing="0" cellpadding="0" align="center">
                <tr>
                    <td width="5%">　</td>
                    <td width="10%"><a href="index.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1','','images/index_on.gif',1)">
                        <img name="Image1" border="0" src="images/index.gif" width="90" height="36"></a></td>
                    <td width="10%"><a href="user/userinfo.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image2','','images/reg_on.gif',1)">
                        <img name="Image2" border="0" src="images/reg.gif" width="92" height="36"></a></td>
                    <td width="10%"><a href="shopcart.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image4','','images/carts_on.gif',1)">
                        <img name="Image4" border="0" src="images/cart.gif" width="92" height="36"></a></td>
                    <td width="10%"><a href="user/order" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image5','','images/order_on.gif',1)">
                        <img name="Image5" border="0" src="images/order.gif" width="92" height="36"></a></td>
                    <td width="10%"><a href="exit" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image6','','images/exit_on.gif',1)">
                        <img name="Image6" border="0" src="images/exit.gif" width="92" height="36"></a></td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<table cellspacing="1" cellpadding="3" align="center" border="0" width="98%">
    <tr>
        <td width="65%"><BR>
            >> 欢迎访问 <b>杰普电子商务门户</b>
        </td>
        <td width="35%" align="right">

        </td></tr></table>
