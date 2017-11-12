<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../include/admin/adminHeader.jsp" %>
<%@include file="../include/admin/adminNavigator.jsp" %>

<script>
    $(function() {
        $("#editForm").submit(function() {
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
<div class="container">
    <ol class="breadcrumb">
        <li><a href="admin_category_list">所有分类</a></li>
        <li><a href="admin_product_list?cid=${product.category.id}">分类名(${product.category.name})</a></li>
        <li class="active">产品名(${product.name})</li>
        <li class="active">编辑产品</li>
    </ol>
</div>
<div class="container" style="width: 50%;">
    <div class="panel panel-primary" style="min-width: 360px;">
        <div class="panel-heading">编辑产品</div>
        <div class="panel-body">
            <form method="post" id="editForm" action="admin_product_update">
                <table class="table">
                    <tr>
                        <td>产品名称</td>
                        <td><input id="name" name="name" class="form-control" value="${product.name}" type="text"></td>
                    </tr>
                    <tr>
                        <td>小标题</td>
                        <td><input id="subTitle" name="subTitle" type="text" class="form-control" value="${product.subTitle}"></td>
                    </tr>
                    <tr>
                        <td>原价格</td>
                        <td><input id="originalPrice" name="originalPrice" type="text" class="form-control" value="${product.originalPrice}"></td>
                    </tr>
                    <tr>
                        <td>折扣价</td>
                        <td><input id="promotePrice" name="promotePrice" type="text" class="form-control" value="${product.promotePrice}"></td>
                    </tr>
                    <tr>
                        <td>库存量</td>
                        <td><input id="stock" name="stock" type="text" class="form-control" value="${product.stock}"></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <button class="btn btn-default" onclick="window.location.href='admin_product_list?cid=${product.category.id}'">取 消</button>
                            <button type="submit" class="btn btn-success">确定更改</button>
                        </td>
                    </tr>
                </table>
                <input type="hidden" name="id" value="${product.id}">
                <input type="hidden" name="cid" value="${product.category.id}">
            </form>
        </div>
    </div>
</div>