<%--
  Created by IntelliJ IDEA.
  Author: yuyaying
  Project: java_web
  Date: 2018/7/10
  Time: 13:33
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="header.jsp" />
<table cellspacing=1 cellpadding=3 align=center class=tableBorder2>
    <tr>
        <td height=25 valign=middle>
            <img src="images/Forum_nav.gif" align="middle">
            <a href=index.jsp>杰普电子商务门户</a> →
            <img border="0" src="images/dog.gif" width="19" height="18">
            购物清单
        </td>
    </tr>
</table>
<br>

<table cellpadding=3 cellspacing=1 align=center class=tableborder1>
    <tr>
        <td valign=middle align=center height=25 background="images/bg2.gif" width=""><font color="#ffffff"><b>序号</b></font></td>
        <td valign=middle align=center height=25 background="images/bg2.gif" width=""><font color="#ffffff"><b>产品名称</b></font></td>
        <td valign=middle align=center height=25 background="images/bg2.gif" width=""><font color="#ffffff"><b>价格</b></font></td>
        <td valign=middle align=center height=25 background="images/bg2.gif" width=""><font color="#ffffff"><b>数量</b></font></td>
        <td valign=middle align=center height=25 background="images/bg2.gif" width=""><font color="#ffffff"><b>合计</b></font></td>
        <td valign=middle align=center height=25 background="images/bg2.gif" width=""><font color="#ffffff"><b>操作</b></font></td>
    </tr>
    <c:forEach items="${shopping_car.getLines() }" var="item" varStatus="status">
    <tr>
        <form method="post" action="updateProduct" name="f1">
            <input type="hidden" name="productid" value="${item.value.getBook().getId() }">
            <input type="hidden" name="number" value="${item.value.getNum() }">
            <td class=tablebody2 valign=middle align=center width="">${status.count }</td>
            <td class=tablebody1 valign=middle width="">&nbsp;&nbsp;<a href="productDetail.jsp?productid=${item.value.getBook().getId() }">${item.value.getBook().getName() }</a></td>
            <td class=tablebody2 valign=middle align=center width="">￥${item.value.getBook().getPrice() }</td>
            <td class=tablebody1 valign=middle align=center width=""><input type="text" name="num" value="${item.value.getNum() }" size="4" onblur="javascript:if(this.value<1){alert('对不起，产品数量不能小于 1 ');this.value=${item.value.getNum() };}else{number.value=this.value;}"/></td>
            <td class=tablebody2 valign=middle align=left width="">&nbsp;&nbsp;￥${item.value.getNum() * item.value.getBook().getPrice() }</td>
            <td class=tablebody1 valign=middle align=center width="">
                <input type="button" value="删除" onclick="javascript:window.location='removeProduct?productid=${item.value.getBook().getId() }';"> <input type="submit" value="保存修改">
            </td>
        </form>
    </tr>
    </c:forEach>
    <tr>
        <td class=tablebody1 valign=middle align=center colspan="4">　</td>
        <td class=tablebody1 valign=middle align=left width="">&nbsp;&nbsp;<font color="#ff0000"><b>￥${shopping_car.getCost() }</b></font></td>
        <td class=tablebody1 valign=middle align=center width="">　</td>
    </tr>
    <tr>
        <td class=tablebody2 valign=middle align=center colspan="6">
            <input type="button" value="提交订单" onclick="javascript:window.location='confirmOrder';">
            <input type="button" value="继续购物" onclick="javascript:window.location='index.jsp';">
            <input type="button" value="清空购物车" onclick="javascript:window.location='removeAllProduct';">
        </td>
    </tr>
</table><br>
<jsp:include page="footer.jsp" />