<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../include/admin/adminHeader.jsp" %>
<%@include file="../include/admin/adminNavigator.jsp" %>

<script>
</script>

<title>用户管理</title>
<div class="container">
  <h3>用户管理</h3>
  <table class="table table-hover table-responsive table-bordered">
    <thead class="text-success">
    <tr>
      <th>用户ID</th>
      <th>用户名称</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${user_list}" var="u">
      <tr>
        <td>${u.id}</td>
        <td>${u.name}</td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</div>
<%@ include file="../include/admin/adminPage.jsp" %>
<%@ include file="../include/admin/adminFooter.jsp" %>