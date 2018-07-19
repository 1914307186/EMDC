<%--
  Created by IntelliJ IDEA.
  Author: yuyaying
  Project: java_web
  Date: 2018/7/10
  Time: 9:01
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="header.jsp" />
<table cellspacing=1 cellpadding=3 align=center class=tableBorder2>
    <tr>
        <td height=25 valign=middle>
            <img src="images/Forum_nav.gif" align="absmiddle">
            <a href=index.jsp>杰普电子商务门户</a> → 用户登录
        </td>
    </tr>
</table>
<br>

<form action="login?gotopage=${param.gotopage }" method="post" name="loginForm">
    <table cellpadding=3 cellspacing=1 align=center class=tableborder1>
        <tr>
            <td valign=middle colspan=2 align=center height=25 background="images/bg2.gif" ><font color="#ffffff"><b>输入您的用户名、密码登录</b></font></td>
        </tr>
        <tr>
            <td valign=middle class=tablebody1>请输入您的用户名</td>
            <td valign=middle class=tablebody1>
                <INPUT name=username type=text> &nbsp;
                <a href="register.jsp?gotopage=${param.gotopage }">没有注册？</a>
            </td>
        </tr>
        <tr>
            <td valign=middle class=tablebody1>请输入您的密码</td>
            <td valign=middle class=tablebody1>
                <INPUT name=password type=password> &nbsp; </td>
        </tr>
        <tr>
            <td class=tablebody2 valign=middle colspan=2 align=center><input type="submit" value="登 录" onclick="javascript:checkMe()"></td>
        </tr>
    </table>
</form>
<jsp:include page="footer.jsp" />