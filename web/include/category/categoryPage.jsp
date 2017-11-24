<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<title>模仿天猫官网-${category.name}</title>
<ol class="breadcrumb">
  <li><a href="/forehome">首页</a></li>
  <li class="active">分类（${category.name}）</li>
</ol>
<div id="category">
  <div class="categoryPageDiv">
    <img src="img/category/${category.id}.jpg">
    <%@include file="sortBar.jsp"%>
    <%@include file="productsByCategory.jsp"%>
  </div>
</div>