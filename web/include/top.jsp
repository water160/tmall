<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<nav class="top ">
  <a href="/forehome">
    <span style="color:#C40000;margin:0px" class=" glyphicon glyphicon-home redColor"></span>
    天猫首页
  </a>

  <span>喵，欢迎来天猫</span>
  <!-- 通过/front/login.jsp传的name和password在ForeServlet中判断，若符合，则request.getSession.setAttribute("user", user)，保留user信息 -->
  <c:if test="${!empty user}">
    <a href="/front/login.jsp">${user.name}</a>
    <a href="/forelogout">退出</a>
  </c:if>

  <c:if test="${empty user}">
    <a href="../front/login.jsp">请登录</a>
    <a href="../front/register.jsp">免费注册</a>
  </c:if>

  <span class="pull-right">
    <a href="/forebought">我的订单</a>
    <a href="/forecart"><span style="color:#C40000;margin:0px" class=" glyphicon glyphicon-shopping-cart redColor"></span>
    <c:if test="${empty cartTotalItemNumber}">
      购物车<strong>0</strong>件</a>
    </c:if>
    <c:if test="${!empty cartTotalItemNumber}">
      购物车<strong>${cartTotalItemNumber}</strong>件</a>
    </c:if>
  </span>

</nav>