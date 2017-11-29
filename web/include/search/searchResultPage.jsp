<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<ol class="breadcrumb">
  <li><a href="/forehome">首页</a></li>
  <li class="active">搜索（${param.keyword}）</li>
</ol>
<div id="searchResult">
  <div class="searchResultDiv">
    <%@include file="productsBySearch.jsp" %>
  </div>
</div>