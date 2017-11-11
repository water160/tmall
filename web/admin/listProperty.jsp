<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../include/admin/adminHeader.jsp" %>
<%@include file="../include/admin/adminNavigator.jsp" %>

<script>
    $(function () {
        $("#addForm").submit(function () {
            if (!checkEmpty("name", "属性名称"))
                return false;
            return true;
        });
    });
</script>

<title>属性管理</title>
<div class="container">
    <ol class="breadcrumb">
        <li><a href="admin_category_list">所有分类</a></li>
        <li><a href="admin_property_list?cid=${c.id}">${c.name}</a></li>
        <li class="active">属性管理</li>
    </ol>
    <div class="container">
        <h4>分类属性管理
            <!--模态框按钮-->
            <button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#addProperty">添加属性</button>
        </h4>
    </div>

    <table class="table table-hover table-responsive table-bordered">
        <thead class="text-success">
        <tr>
            <th width="6%">属性ID</th>
            <th width="74%">属性名称</th>
            <th colspan="2" class="center">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${property_list}" var="p">
            <tr>
                <td>${p.id}</td>
                <td>${p.name}</td>
                <td>
                    <a class href="admin_property_edit?id=${p.id}">
                        <span class="glyphicon glyphicon-edit"></span> 编辑</a>
                </td>
                <td>
                    <a deleteLink="true" href="admin_property_delete?id=${p.id}">
                        <span class="glyphicon glyphicon-trash"></span> 删除</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<!-- 模态框 -->
<form id="addForm" action="admin_property_add" method="post">
    <div class="modal fade" id="addProperty" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">添加属性信息</h4>
                </div>
                <div class="modal-body">
                    <table class="table">
                        <tr>
                            <td>属性名称</td>
                            <td><input id="name" name="name" type="text" class="form-control"></td>
                        </tr>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">放弃添加</button>
                    <button type="submit" class="btn btn-primary">确定添加</button>
                    <input type="hidden" name="cid" value="${c.id}">
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div>
</form>
<%@ include file="../include/admin/adminPage.jsp" %>
<%@ include file="../include/admin/adminFooter.jsp" %>
