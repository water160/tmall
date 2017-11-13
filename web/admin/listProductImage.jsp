<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../include/admin/adminHeader.jsp" %>
<%@ include file="../include/admin/adminNavigator.jsp" %>

<script>
  $(function () {
    $(".addFormSingle").submit(function () {
      if (checkEmpty("filepathSingle", "单个图片文件")) {
        $("#filepathSingle").value("");
        return true;
      }
      return false;
    });
    $(".addFormDetail").submit(function () {
      if (checkEmpty("filepathDetail", "详情图片文件"))
        return true;
      return false;
    });
  });
</script>
<title>产品图片管理</title>

<div class="container">
  <ol class="breadcrumb">
    <li><a href="admin_category_list">所有分类</a></li>
    <li><a href="admin_product_list?cid=${product.category.id}">分类名(${product.category.name})</a></li>
    <li class="active">产品名(${product.name})</li>
    <li class="active">产品图片管理</li>
  </ol>

  <div class="row">
    <div class="col-md-6">
      <div class="panel panel-info">
        <div class="panel-heading">
          <h3 class="panel-title">
            新增产品 <b class="text-danger">单个</b> 图片
          </h3>
        </div>
        <div class="panel-body">
          <form method="post" class="addFormSingle" action="admin_productImage_add" enctype="multipart/form-data">
            <table class="table">
              <tr>
                <td>
                  <p>请选择本地图片(尺寸 <b class="text-primary">400X400</b> 为佳)</p>
                  <p><input id="filepathSingle" type="file" name="filepath"/></p>
                </td>
              </tr>
              <tr>
                <input type="hidden" name="type" value="type_single"/>
                <input type="hidden" name="pid" value="${product.id}"/>
              </tr>
            </table>
            <button type="submit" class="btn btn-success">提 交</button>
          </form>
        </div>
      </div>
      <table class="table table-bordered table-hover">
        <thead>
        <tr class="success">
          <th>单个图片ID</th>
          <th>产品单个图片缩略图</th>
          <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${productImageList_single}" var="pi">
          <tr>
            <td>${pi.id}</td>
            <td>
              <a title="点击查看原图" href="img/productSingle/${pi.id}.jpg"><img height="50px"
                                                                           src="img/productSingle/${pi.id}.jpg"></a>
            </td>
            <td>
              <a deleteLink="true" href="admin_productImage_delete?id=${pi.id}">
                <span class="glyphicon glyphicon-trash"></span> 删除</a>
            </td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
    </div>

    <div class="col-md-6">
      <div class="panel panel-info">
        <div class="panel-heading">
          <h3 class="panel-title">
            新增产品 <b class="text-danger">详情</b> 图片
          </h3>
        </div>
        <div class="panel-body">
          <form method="post" class="addFormDetail" action="admin_productImage_add" enctype="multipart/form-data">
            <table class="table">
              <tr>
                <td>
                  <p>请选择本地图片(宽度 <b class="text-primary">790</b> 为佳)</p>
                  <p><input id="filepathDetail" type="file" name="filepath"/></p>
                </td>
              </tr>
              <tr>
                <input type="hidden" name="type" value="type_detail"/>
                <input type="hidden" name="pid" value="${product.id}"/>
              </tr>
            </table>
            <button type="submit" class="btn btn-success">提 交</button>
          </form>
        </div>
      </div>

      <table class="table table-bordered table-hover">
        <thead>
        <tr class="success">
          <th>详情图片ID</th>
          <th>产品详情图片缩略图</th>
          <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${productImageList_detail}" var="pi">
          <tr>
            <td>${pi.id}</td>
            <td>
              <a title="点击查看原图" href="img/productDetail/${pi.id}.jpg"><img height="50px"
                                                                           src="img/productDetail/${pi.id}.jpg"></a>
            </td>
            <td>
              <a deleteLink="true" href="admin_productImage_delete?id=${pi.id}">
                <span class="glyphicon glyphicon-trash"></span> 删除</a>
            </td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
    </div>
  </div>
</div>