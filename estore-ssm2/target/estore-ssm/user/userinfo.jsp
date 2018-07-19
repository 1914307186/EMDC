<%--
  Created by IntelliJ IDEA.
  Author: yuyaying
  Project: java_web
  Date: 2018/7/10
  Time: 13:42
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<jsp:include page="header.jsp" />
<table cellspacing=1 cellpadding=3 align=center class=tableBorder2>
    <tr>
        <td height=25 valign=middle>
            <img src="../images/Forum_nav.gif" align="middle">
            <a href=../index.jsp>杰普电子商务门户</a> →
            <a href="order.jsp">用户信息</a>
        </td>
    </tr>
</table>
<br>

<form name="order" method="post" action="modifyuserinfo.jsp" >
    <table cellpadding="3" cellspacing="1" align="center" class="tableborder1" id="table1">
        <tr>
            <td valign="middle" colspan="2" align="center" height="25" background="../images/bg2.gif">
                <font color="#ffffff"><b>用户信息</b></font></td>
        </tr>
        <tr>
            <td width="40%" class="tablebody2" align="right"><b>用户名</b>：</td>
            <td width="60%" class="tablebody1">${customer.getName() }</td>
        </tr>
        <tr>
            <td class="tablebody2" align="right"><b>地址</b>：</td>
            <td class="tablebody1">${customer.getAddress() }</td>
        </tr>
        <tr>
            <td class="tablebody2" align="right"><b>邮编</b>：</td>
            <td class="tablebody1">${customer.getZip() }</td>
        </tr>
        <tr>
            <td class="tablebody2" align="right"><b>手机</b>：</td>
            <td class="tablebody1">${cellphone }</td>
        </tr>
        <tr>
            <td class="tablebody2" align="right"><b>家庭电话</b>：</td>
            <td class="tablebody1">${homephone }</td>
        </tr>
        <tr>
            <td class="tablebody2" align="right"><b>办公室电话</b>：</td>
            <td class="tablebody1">${officephone }</td>
        </tr>
        <tr>
            <td class="tablebody2" align="right"><b>Email地址</b>：</td>
            <td class="tablebody1">${customer.getEmail() }</td>
        </tr>
        <tr>
            <td class="tablebody2" valign="middle" colspan="2" align="center">
                <input type="submit" value="修 改 信 息"></td>
        </tr>
    </table>

</form>
<jsp:include page="footer.jsp" />