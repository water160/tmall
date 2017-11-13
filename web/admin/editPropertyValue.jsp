<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../include/admin/adminHeader.jsp" %>
<%@ include file="../include/admin/adminNavigator.jsp" %>

<title>编辑产品属性值</title>

<script>
  $(document).ready(function () {
    $("input.pvValue").keyup(function () {
      var value = $(this).val();
      var page = "admin_product_updatePropertyValue";
      var pvid = $(this).attr("pvid");
      var parentSpan = $(this);
      $.post(
          page,
          {"value": value, "pvid": pvid},
          function (result) {
            if ("success" == result) {
              parentSpan.css("border", "1px solid green");
              parentSpan.css("input::after", "content:'success'");
            }
            else {
              parentSpan.css("border", "1px solid red");
            }
          }
      );
    });
  });

</script>

<div class="container">
  <ol class="breadcrumb">
    <li><a href="admin_category_list">所有分类</a></li>
    <li><a href="admin_product_list?cid=${product.category.id}">分类名(${product.category.name})</a></li>
    <li class="active">产品名(${product.name})</li>
    <li class="active">编辑产品属性</li>
  </ol>

  <div class="editPVDiv">
    <c:forEach items="${propertyValue_list}" var="pvl" varStatus="status">
      <span class="pvName">${pvl.property.name}</span>
      <span class="pvValue"><input class="pvValue" type="text" pvid="${pvl.id}" value="${pvl.value}"></span>
    </c:forEach>
  </div>
</div>