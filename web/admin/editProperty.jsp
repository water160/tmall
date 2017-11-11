<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../include/admin/adminHeader.jsp" %>
<%@include file="../include/admin/adminNavigator.jsp" %>

<script>
    $(function () {
        $("#editForm").submit(function () {
            if (!checkEmpty("name", "分类名称"))
                return false;
            return true;
        });
    });

</script>
<div class="container">
    <ol class="breadcrumb">
        <li><a href="admin_category_list">所有分类</a></li>
        <li><a href="admin_property_list?cid=${property.category.id}">${property.category.name}</a></li>
        <li class="active">编辑属性(${property.name})</li>
    </ol>
</div>
<div class="container" style="width: 50%;">
    <div class="panel panel-primary" style="min-width: 360px;">
        <div class="panel-heading">编辑属性</div>
        <div class="panel-body">
            <form method="post" id="editForm" action="admin_property_update">
                <table class="table">
                    <tr>
                        <td>属性名称</td>
                        <td><input id="name" name="name" value="${property.name}" type="text"></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <button class="btn btn-default" onclick="window.location.href='admin_property_list?cid=${property.category.id}'">放弃更改</button>
                            <button type="submit" class="btn btn-success">确定更改</button>
                        </td>
                    </tr>
                </table>
                <input type="hidden" name="id" value="${property.id}">
                <input type="hidden" name="cid" value="${property.category.id}">
            </form>
        </div>
    </div>
</div>