<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../include/admin/adminHeader.jsp" %>
<%@include file="../include/admin/adminNavigator.jsp" %>

<script>
    $(function () {
        $("#addForm").submit(function () {
            if (!checkEmpty("name", "分类名称"))
                return false;
            if (!checkEmpty("categoryPic", "分类图片"))
                return false;
            return true;
        });
    });
</script>

<title>分类管理</title>
<div class="container">
    <div class="container">
        <h3>分类管理
            <!--模态框按钮-->
            <button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#addCategory">添加分类</button>
        </h3>
    </div>

    <table class="table table-hover table-responsive table-bordered">
        <thead class="text-success">
        <tr>
            <th width="8%">分类ID</th>
            <th width="20%">分类名称</th>
            <th width="50%">图片</th>
            <%--<th>属性管理</th>--%>
            <%--<th>产品管理</th>--%>
            <th colspan="2" class="center">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${c_list}" var="c">
            <tr>
                <td>${c.id}</td>
                <td>${c.name}</td>
                <td><img height="40px" src="img/category/${c.id}.jpg" alt="${c.id}.jpg"></td>
                    <%--<td><a href="admin_property_list?cid=${c.id}"><span class="glyphicon glyphicon-th-list"></span></a></td>--%>
                    <%--<td><a href="admin_product_list?cid=${c.id}"><span class="glyphicon glyphicon-shopping-cart"></span></a></td>--%>
                <td>
                    <a class href="admin_category_edit?id=${c.id}">
                        <span class="glyphicon glyphicon-edit"></span> 编辑</a>
                </td>
                <td>
                    <a deleteLink="true" href="admin_category_delete?id=${c.id}">
                        <span class="glyphicon glyphicon-trash"></span> 删除</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<!-- 模态框 -->
<form id="addForm" action="admin_category_add" method="post" enctype="multipart/form-data">
    <div class="modal fade" id="addCategory" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">添加分类信息</h4>
                </div>
                <div class="modal-body">
                    <table class="table">
                        <tr>
                            <td>分类名称</td>
                            <td><input id="name" name="name" type="text" class="form-control"></td>
                        </tr>
                        <tr>
                            <td>分类图片</td>
                            <td>
                                <input id="categoryPic" accept="image/*" type="file" name="filepath"/>
                                <div class="label label-default">height=40px, width<=1000px, size<=10M</div>
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="submit" class="btn btn-primary">提交</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div>
</form>
<%@ include file="../include/admin/adminPage.jsp" %>
<%@ include file="../include/admin/adminFooter.jsp" %>