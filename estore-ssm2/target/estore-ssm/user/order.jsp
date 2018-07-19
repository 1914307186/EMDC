<%--
  Created by IntelliJ IDEA.
  Author: yuyaying
  Project: java_web
  Date: 2018/7/10
  Time: 13:40
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<jsp:include page="header.jsp" />
<table cellspacing=1 cellpadding=3 align=center class=tableBorder2>
    <tr>
        <td height=25 valign=middle>
            <img src="../images/Forum_nav.gif" align="middle">
            <a href=index.jsp>杰普电子商务门户</a> →
            <img border="0" src="../images/dog.gif" width="19" height="18">
            订单列表
        </td>
    </tr>
</table>
<br>

<table cellpadding=3 cellspacing=1 align=center class=tableborder1>
    <tr>
        <td valign=middle align=center height=25 background="../images/bg2.gif" width=""><font color="#ffffff"><b>序号</b></font></td>
        <td valign=middle align=center height=25 background="../images/bg2.gif" width=""><font color="#ffffff"><b>订单编号</b></font></td>
        <td valign=middle align=center height=25 background="../images/bg2.gif" width=""><font color="#ffffff"><b>订单金额</b></font></td>
        <td valign=middle align=center height=25 background="../images/bg2.gif" width=""><font color="#ffffff"><b>付款方式</b></font></td>
        <td valign=middle align=center height=25 background="../images/bg2.gif" width=""><font color="#ffffff"><b>下单时间</b></font></td>
        <td valign=middle align=center height=25 background="../images/bg2.gif" width=""><font color="#ffffff"><b>操作</b></font></td>
    </tr>
    <%
        int index = 0;
    %>
    <c:forEach items="${orders }" var="order">
        <%index++; %>
        <tr>
            <td class=tablebody2 valign=middle align=center width=""><%=index %></td>
            <td class=tablebody1 valign=middle width="">&nbsp;&nbsp;<a href="orderinfo?orderid=${order.getId() }">${order.getId() }</a></td>
            <td class=tablebody2 valign=middle align=center width=""><font color="#ff0000"><b>￥ ${order.getCost() }</b></font></td>
            <td class=tablebody1 valign=middle align=center width=""><select><option> 微信支付 </option><option> 支付宝 </option><option> 银联在线支付 </option></select></td>
            <td class=tablebody2 valign=middle align=center width="">${order.getOrderDate().toString() }</td>
            <td class=tablebody1 valign=middle align=center width="">
                <input type="button" value="删除" onclick="javascript:window.location='removeOrder?orderid=${order.getId() }';">&nbsp;<input type="button" value="明细" onclick="javascript:window.location='orderinfo?orderid=${order.getId() }';"><!--&nbsp;<input type="button" value="修改"/>--> </td>
        </tr>
    </c:forEach>
</table><br>
<jsp:include page="footer.jsp" />