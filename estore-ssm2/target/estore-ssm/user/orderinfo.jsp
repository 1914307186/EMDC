<%--
  Created by IntelliJ IDEA.
  Author: yuyaying
  Project: java_web
  Date: 2018/7/10
  Time: 13:41
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<jsp:include page="header.jsp" />
<table cellspacing=1 cellpadding=3 align=center class=tableBorder2>
    <tr>
        <td height=25 valign=middle>
            <img src="../images/Forum_nav.gif" align="middle">
            <a href=../index.html>杰普电子商务门户</a> →
            <a href="order.html">定单列表</a> →
            定单明细
        </td>
    </tr>
</table>
<br>

<form name="order" method="post" action="../saveOrder" >
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
    <%--<tr>
        <td class="tablebody2" align="right"><b>家庭电话</b>：</td>
        <td class="tablebody1">sdfadsf</td>
    </tr>
    <tr>
        <td class="tablebody2" align="right"><b>办公室电话</b>：</td>
        <td class="tablebody1">sdfadsfa</td>
    </tr>--%>
    <tr>
        <td class="tablebody2" align="right"><b>手机</b>：</td>
        <td class="tablebody1">${customer.getTelephone() }</td>
    </tr>
    <tr>
        <td class="tablebody2" align="right"><b>Email地址</b>：</td>
        <td class="tablebody1">${customer.getEmail() }</td>
    </tr>
</table>
<br>
    <table cellpadding="3" cellspacing="1" align="center" class="tableborder1" id="table2">
        <tr>
            <td valign="middle" colspan="2" align="center" height="25" background="../images/bg2.gif">
                <font color="#FFFFFF"><b>付款方式</b></font></td>
        </tr>
        <tr>
            <td width="40%" class="tablebody2" align="right">　</td>
            <td width="60%" class="tablebody1"><select><option> 微信支付 </option><option> 支付宝 </option><option> 银联在线支付 </option></select></td>
        </tr>
    </table>


    <br>

    <table cellpadding=3 cellspacing=1 align=center class=tableborder1 id="table3">
        <tr>
            <td valign=middle align=center height=25 background="../images/bg2.gif" colspan="5">
                <font color="#ffffff"><b>商品购物清单</b></font></td>
        </tr>

        <% int index = 0; %>
        <c:forEach items="${lines }" var="line">
            <%index++; %>
            <tr>
                <td class=tablebody2 valign=middle align=center width=""><%=index %></td>
                <td class=tablebody1 valign=middle width="">&nbsp;&nbsp;<a href="../productDetail.jsp?productid=2" target="_blank">${line.getBook().getName() }</a></td>
                <td class=tablebody2 valign=middle align=center width="">价格：${line.getBook().getPrice() }</td>
                <td class=tablebody1 valign=middle align=center width="">数量：${line.getNum() }</td>
                <td class=tablebody2 valign=middle align=left width="">小计：￥${line.getNum() * line.getBook().getPrice()}</td>
            </tr>
        </c:forEach>

        <tr>
            <td class=tablebody1 valign=middle align=center colspan="4">　</td>
            <td class=tablebody1 valign=middle align=left width="">合计：<font color="#ff0000"><b>￥${order.getCost() }</b></font></td>
        </tr>
    </table>
</form>
<br>
<jsp:include page="footer.jsp" />