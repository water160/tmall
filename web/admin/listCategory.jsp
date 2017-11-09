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
    <h2>分类管理</h2>
    <table class="table table-hover table-responsive table-bordered">
        <thead class="text-success">
        <tr>
            <th>分类ID</th>
            <th>分类名称</th>
            <th>图片</th>
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
                <td><a href="admin_category_edit?id=${c.id}"><span class="glyphicon glyphicon-edit"></span> 编辑</a></td>
                <td><a href="admin_category_delete?id=${c.id}"><span class="glyphicon glyphicon-trash"></span> 删除</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<%@ include file="../include/admin/adminPage.jsp" %>
<%@ include file="../include/admin/adminFooter.jsp" %>