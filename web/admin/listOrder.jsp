<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../include/admin/adminHeader.jsp" %>
<%@include file="../include/admin/adminNavigator.jsp" %>

<script>
  $(function () {
    $("button.orderPageCheckOrderItems").click(function () {
      var oid = $(this).attr("oid");
      $("tr.orderPageOrderItemTR[oid=" + oid + "]").toggle();
    });
  });
  $(function () {
    $("button.delivery").click(function () {
      var confirmDelivery = confirm("确认发货?");
      if (confirmDelivery)
        return true;
      return false;
    });
  });
</script>

<title>订单管理</title>
<div class="container">
  <h3>订单管理</h3>
  <table class="table table-responsive table-bordered">
    <thead>
    <tr class="text-success">
      <th width="70px">订单ID</th>
      <th>订单号</th>
      <th width="70px">状态</th>
      <th>金额</th>
      <th width="75px">商品数量</th>
      <th width="100px">买家名称</th>
      <th width="90px">创建时间</th>
      <th width="90px">支付时间</th>
      <th width="90px">发货时间</th>
      <th width="120px">确认收货时间</th>
      <th width="120px">操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${order_list}" var="o">
      <tr class="OrderTitleTR">
        <td>${o.id}</td>
        <td>${o.orderCode}</td>
        <td>${o.statusDesc}</td>
        <td>￥<fmt:formatNumber type="number" value="${o.total}" minFractionDigits="2"/></td>
        <td>${o.totalNumber}</td>
        <td>${o.user.name}</td>

        <td><fmt:formatDate value="${o.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        <td><fmt:formatDate value="${o.payDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        <td><fmt:formatDate value="${o.deliveryDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        <td><fmt:formatDate value="${o.confirmDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>

        <td>
          <button oid=${o.id} class="orderPageCheckOrderItems btn btn-primary btn-xs
          ">查看详情</button>

          <c:if test="${o.status=='waitDelivery'}">
            <a href="admin_order_delivery?id=${o.id}">
              <button class="delivery btn btn-primary btn-xs">发货</button>
            </a>
          </c:if>
        </td>
      </tr>
      <tr class="orderPageOrderItemTR" oid=${o.id}>
        <td colspan="11">

          <table class="table table-condensed">
            <c:forEach items="${o.orderItemList}" var="oi">
              <tr>
                <td width="8%">
                  <a href="img/productSingle/${oi.product.firstProductImage.id}.jpg" target="_blank"><img width="40px"
                                                                                                          height="40px"
                                                                                                          src="img/productSingle/${oi.product.firstProductImage.id}.jpg"></a>
                </td>

                <td width="42%">
                  <a href="foreproduct?pid=${oi.product.id}"><span>${oi.product.name}</span></a>
                </td>

                <td width="10%">
                  <span class="text-muted">数量：${oi.number}</span>
                </td>
                <td>
                  <span class="text-muted">单价：￥${oi.product.promotePrice}</span>
                </td>
                <td>
                  <span class="text-muted">共：￥<fmt:formatNumber type="number"
                                                                value="${oi.product.promotePrice * oi.number}"
                                                                minFractionDigits="2"/></span>
                </td>
              </tr>
            </c:forEach>
          </table>

        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</div>
<%@ include file="../include/admin/adminPage.jsp" %>
<%@ include file="../include/admin/adminFooter.jsp" %>