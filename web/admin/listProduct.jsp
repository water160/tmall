<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../include/admin/adminHeader.jsp" %>
<%@include file="../include/admin/adminNavigator.jsp" %>

<script>
  $(function () {
    $("#addForm").submit(function () {
      if (!checkEmpty("name", "产品名称"))
        return false;
      if (!checkNumber("originalPrice", "原价格"))
        return false;
      if (!checkNumber("promotePrice", "折扣价"))
        return false;
      if (!checkInt("stock", "库存量"))
        return false;
      return true;
    });
  });
</script>

<title>产品管理</title>
<div class="container">
  <ol class="breadcrumb">
    <li><a href="admin_category_list">所有分类</a></li>
    <li><a href="admin_product_list?cid=${c.id}">分类名(${c.name})</a></li>
    <li class="active">产品管理</li>
  </ol>
  <div class="container">
    <h4>分类产品管理 - (${c.name})
      <!--模态框按钮-->
      <button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#addProduct">添加产品</button>
    </h4>
  </div>

  <table class="table table-hover table-responsive table-bordered">
    <thead class="text-success">
    <tr>
      <th width="4%">ID</th>
      <th width="22%">产品名称</th>
      <th width="14%">产品小标题</th>
      <th width="10%">产品图片</th>
      <th width="6%">原价格</th>
      <th width="6%">折扣价</th>
      <th width="6%">库存量</th>
      <th width="10%">产品图片管理</th>
      <th width="9%">属性值设置</th>
      <th width="9%">创建时间</th>
      <th colspan="2" class="center">操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${product_list}" var="p">
      <tr>
        <td>${p.id}</td>
        <td>${p.name}</td>
        <td>${p.subTitle}</td>
        <td>
          <c:if test="${!empty p.firstProductImage}">
            <img src="img/productSingle/${p.firstProductImage.id}.jpg" width="40px">
          </c:if>
        </td>
        <td>${p.originalPrice}</td>
        <td>${p.promotePrice}</td>
        <td>${p.stock}</td>
        <td>
          <a href="admin_productImage_list?pid=${p.id}">
            <span class="glyphicon glyphicon-picture"></span> 图片设置</a>
        </td>
        <td>
          <a href="admin_product_editPropertyValue?id=${p.id}">
            <span class="glyphicon glyphicon-th-list"></span> 属性设置</a>
        </td>
        <td>${p.createDate}</td>
        <td>
          <a class href="admin_product_edit?id=${p.id}">
            <span class="glyphicon glyphicon-edit"></span></a>
        </td>
        <td>
          <a deleteLink="true" href="admin_product_delete?id=${p.id}">
            <span class="glyphicon glyphicon-trash"></span></a>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</div>

<!-- 模态框 -->
<form id="addForm" action="admin_product_add" method="post">
  <div class="modal fade" id="addProduct" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
       aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title" id="myModalLabel">添加产品信息</h4>
        </div>
        <div class="modal-body">
          <table class="table">
            <tr>
              <td>产品名称</td>
              <td><input id="name" name="name" type="text" class="form-control"></td>
            </tr>
            <tr>
              <td>小标题</td>
              <td><input id="subTitle" name="subTitle" type="text" class="form-control"></td>
            </tr>
            <tr>
              <td>原价格</td>
              <td><input id="originalPrice" name="originalPrice" type="text" class="form-control"
                         placeholder="(浮点数，3.402823e+38 ~ 1.401298e-45)"></td>
            </tr>
            <tr>
              <td>折扣价</td>
              <td><input id="promotePrice" name="promotePrice" type="text" class="form-control"
                         placeholder="(浮点数，3.402823e+38 ~ 1.401298e-45)"></td>
            </tr>
            <tr>
              <td>库存量</td>
              <td><input id="stock" name="stock" type="text" class="form-control"
                         placeholder="(整数，-2147483648 ~ 2147483647)"></td>
            </tr>
          </table>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">取 消</button>
          <button type="submit" class="btn btn-primary">确 定</button>
          <input type="hidden" name="cid" value="${c.id}"><!--别忘记这里的传值-->
        </div>
      </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
  </div>
</form>
<%@ include file="../include/admin/adminPage.jsp" %>
<%@ include file="../include/admin/adminFooter.jsp" %>
