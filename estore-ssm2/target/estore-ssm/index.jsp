<%--
  Created by IntelliJ IDEA.
  Author: yuyaying
  Project: java_web
  Date: 2018/7/9
  Time: 22:16
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="header.jsp" />
  <table cellspacing=1 cellpadding=3 align=center class=tableBorder2>
    <tr>
      <td height=25 valign=middle>
        <img src="images/Forum_nav.gif" align="middle">
        <a href=index.jsp>杰普电子商务门户</a> →
        <img border="0" src="images/dog.gif" width="19" height="18">
        欢迎<font color="red"></font>光临！
      </td>
    </tr>
  </table>
  <br>

  <table cellpadding=3 cellspacing=1 align=center class=tableborder1>
    <tr>
      <td valign=middle align=center height=25 background="images/bg2.gif" width=""><font color="#ffffff"><b>序号</b></font></td>
      <td valign=middle align=center height=25 background="images/bg2.gif" width=""><font color="#ffffff"><b>产品名称</b></font></td>
      <td valign=middle align=center height=25 background="images/bg2.gif" width=""><font color="#ffffff"><b>价格</b></font></td>
      <td valign=middle align=center height=25 background="images/bg2.gif" width=""><font color="#ffffff"><b>操作</b></font></td>
    </tr>

    <tr>
      <td class=tablebody2 valign=middle align=center width="">1</td>
      <td class=tablebody1 valign=middle width="">&nbsp;&nbsp;<a href="productDetail.jsp?productid=1">精通Hibernate：Java对象持久化技术详解</a></td>
      <td class=tablebody2 valign=middle align=center width="">59.0</td>
      <td class=tablebody1 valign=middle align=center width=""><a href="addProduct?productid=1">
        <img border="0" src="images/car_new.gif" width="97" height="18"></a> </td>
    </tr>

    <tr>
      <td class=tablebody2 valign=middle align=center width="">2</td>
      <td class=tablebody1 valign=middle width="">&nbsp;&nbsp;<a href="productDetail.jsp?productid=2">Effective Java中文版</a></td>
      <td class=tablebody2 valign=middle align=center width="">39.0</td>
      <td class=tablebody1 valign=middle align=center width=""><a href="addProduct?productid=2">
        <img border="0" src="images/car_new.gif" width="97" height="18"></a> </td>
    </tr>

    <tr>
      <td class=tablebody2 valign=middle align=center width="">3</td>
      <td class=tablebody1 valign=middle width="">&nbsp;&nbsp;<a href="productDetail.jsp?productid=3">精通Spring</a></td>
      <td class=tablebody2 valign=middle align=center width="">39.0</td>
      <td class=tablebody1 valign=middle align=center width=""><a href="addProduct?productid=3">
        <img border="0" src="images/car_new.gif" width="97" height="18"></a> </td>
    </tr>

    <tr>
      <td class=tablebody2 valign=middle align=center width="">4</td>
      <td class=tablebody1 valign=middle width="">&nbsp;&nbsp;<a href="productDetail.jsp?productid=4">深入浅出Hibernate</a></td>
      <td class=tablebody2 valign=middle align=center width="">59.0</td>
      <td class=tablebody1 valign=middle align=center width=""><a href="addProduct?productid=4">
        <img border="0" src="images/car_new.gif" width="97" height="18"></a> </td>
    </tr>

    <tr>
      <td class=tablebody2 valign=middle align=center width="">5</td>
      <td class=tablebody1 valign=middle width="">&nbsp;&nbsp;<a href="productDetail.jsp?productid=5">JAVA编程思想：第3版</a></td>
      <td class=tablebody2 valign=middle align=center width="">95.0</td>
      <td class=tablebody1 valign=middle align=center width=""><a href="addProduct?productid=5">
        <img border="0" src="images/car_new.gif" width="97" height="18"></a> </td>
    </tr>

    <tr>
      <td class=tablebody2 valign=middle align=center width="">6</td>
      <td class=tablebody1 valign=middle width="">&nbsp;&nbsp;<a href="productDetail.jsp?productid=6">Java 2核心技术（第6版） 卷I：基础知识 </a></td>
      <td class=tablebody2 valign=middle align=center width="">75.0</td>
      <td class=tablebody1 valign=middle align=center width=""><a href="addProduct?productid=6">
        <img border="0" src="images/car_new.gif" width="97" height="18"></a> </td>
    </tr>

    <tr>
      <td class=tablebody2 valign=middle align=center width="">7</td>
      <td class=tablebody1 valign=middle width="">&nbsp;&nbsp;<a href="productDetail.jsp?productid=7">Tomcat与Java Web开发技术详解</a></td>
      <td class=tablebody2 valign=middle align=center width="">45.0</td>
      <td class=tablebody1 valign=middle align=center width=""><a href="addProduct?productid=7">
        <img border="0" src="images/car_new.gif" width="97" height="18"></a> </td>
    </tr>

    <tr>
      <td class=tablebody2 valign=middle align=center width="">8</td>
      <td class=tablebody1 valign=middle width="">&nbsp;&nbsp;<a href="productDetail.jsp?productid=8">Java与模式</a></td>
      <td class=tablebody2 valign=middle align=center width="">88.0</td>
      <td class=tablebody1 valign=middle align=center width=""><a href="addProduct?productid=8">
        <img border="0" src="images/car_new.gif" width="97" height="18"></a> </td>
    </tr>

  </table>
<jsp:include page="footer.jsp" />