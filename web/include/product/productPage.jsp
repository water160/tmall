<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<title>模仿天猫官网 ${product.name}</title>
<ol class="breadcrumb">
  <li><a href="/forehome">首页</a></li>
  <li><a href="/forecategory?cid=${product.category.getId()}">分类（${product.category.name}）</a></li>
  <li class="active">${product.name}</li>
</ol>
<div class="categoryPictureInProductPageDiv">
  <img class="categoryPictureInProductPage" src="../../img/category/${product.category.id}.jpg">
</div>

<div class="productPageDiv">
  <%@include file="imgAndInfo.jsp" %>

  <%@include file="productReview.jsp" %>

  <%@include file="productDetail.jsp" %>
</div>