<%@ page import="tmall.bean.Order" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<script>
  var deleteOrder = false;
  var deleteOrderid = 0;

  $(function () {
    $("a[orderStatus]").click(function () {
      var orderStatus = $(this).attr("orderStatus");
      if (orderStatus == 'all') {
        $("table[orderStatus]").show();
      }
      else {
        $("table[orderStatus]").hide();
        $("table[orderStatus=" + orderStatus + "]").show();
      }

      $("div.orderType div").removeClass("selectedOrderType");
      $(this).parent("div").addClass("selectedOrderType");
    });

    //删除某订单，触发模态框
    $("a.deleteOrderLink").click(function () {
      deleteOrderid = $(this).attr("oid");
      deleteOrder = false;
      $("#deleteConfirmModal").modal("show");
    });

    //模态框删除确认按钮
    $("button.deleteConfirmButton").click(function () {
      deleteOrder = true;
      $("#deleteConfirmModal").modal('hide');
    });

    //如果点击确认按钮，设置deleteOrder为true，触发离开状态时会利用ajax提交判断
    $('#deleteConfirmModal').on('hidden.bs.modal', function (e) {
      if (deleteOrder) {
        var page = "/foredeleteOrder";
        $.post(
            page,
            {"oid": deleteOrderid},
            function (result) {
              if (result == "success") {
                $("table.orderListItemTable[oid=" + deleteOrderid + "]").hide();
              }
              else {
                location.href = "/front/login.jsp";
              }
            }
        );
      }
    });

    //催卖家发货
    $(".ask2delivery").click(function () {
      var link = $(this).attr("link");
      $(this).hide();
      page = link;
      $.ajax({
        url: page,
        success: function (result) {
          alert("卖家已秒发，刷新当前页面，即可进行确认收货")
        }
      });

    });
  });

</script>
<%
  List<Order> o_list = (List<Order>) request.getAttribute("o_list");
  int waitPayNum = 0, waitDeliveryNum = 0, waitConfirm = 0, waitReview = 0;
  for (Order order : o_list) {
    if (order.getStatus().equals("waitPay")) {
      waitPayNum++;
    } else if (order.getStatus().equals("waitDelivery")) {
      waitDeliveryNum++;
    } else if (order.getStatus().equals("waitConfirm")) {
      waitConfirm++;
    } else if (order.getStatus().equals("waitReview")) {
      waitReview++;
    } else {
      System.out.println(order.getStatus() + " no status fit!");
    }
  }
%>
<div class="boughtDiv">
  <div class="orderType">
    <div class="selectedOrderType"><a orderStatus="all" href="#nowhere">所有订单(${o_list.size()})</a></div>
    <div><a orderStatus="waitPay" href="#nowhere">待付款(<%=waitPayNum%>)</a></div>
    <div><a orderStatus="waitDelivery" href="#nowhere">待发货(<%=waitDeliveryNum%>)</a></div>
    <div><a orderStatus="waitConfirm" href="#nowhere">待收货(<%=waitConfirm%>)</a></div>
    <div><a orderStatus="waitReview" href="#nowhere" class="noRightborder">待评价(<%=waitReview%>)</a></div>
    <div class="orderTypeLastOne"><a class="noRightborder"> </a></div>
  </div>
  <div style="clear:both"></div>
  <div class="orderListTitle">
    <table class="orderListTitleTable">
      <tr>
        <td>宝贝</td>
        <td width="100px">单价</td>
        <td width="100px">数量</td>
        <td width="120px">实付款</td>
        <td width="100px">交易操作</td>
      </tr>
    </table>
  </div>

  <div class="orderListItem">
    <c:forEach items="${o_list}" var="o">
      <table class="orderListItemTable" orderStatus="${o.status}" oid="${o.id}">
        <tr class="orderListItemFirstTR">
          <td colspan="2">
            <b>[创建时间]</b> <fmt:formatDate value="${o.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>&emsp;
            <b>[订单号]</b> ${o.orderCode}<br/>
            <b>[电话]</b> ${o.mobile}&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
            <b>[收件人]</b> ${o.receiver}
          </td>

          <td colspan="2"><img width="13px" src="../../img/site/orderItemTmall.png">天猫商场</td>

          <td colspan="1"><a class="wangwanglink" href="#nowhere">
            <div class="orderItemWangWangGif"></div>
          </a></td>

          <td class="orderItemDeleteTD">
            <a class="deleteOrderLink" oid="${o.id}" href="#nowhere">
              <span class="orderListItemDelete glyphicon glyphicon-trash"></span>
            </a>
          </td>
        </tr>
        <c:forEach items="${o.orderItemList}" var="oi" varStatus="st">
          <tr class="orderItemProductInfoPartTR">
            <td class="orderItemProductInfoPartTD">
              <c:if test="${empty oi.product.firstProductImage}">
                <img width="80" height="80" src="../../img/site/imgNotFound.jpg">
              </c:if>
              <c:if test="${!empty oi.product.firstProductImage}">
                <img width="80" height="80" src="../../img/productSingle_middle/${oi.product.firstProductImage.id}.jpg">
              </c:if>
            </td>

            <td class="orderItemProductInfoPartTD">
              <div class="orderListItemProductLinkOutDiv">
                <a href="/foreproduct?pid=${oi.product.id}">${oi.product.name}</a>
                <div class="orderListItemProductLinkInnerDiv">
                  <img src="../../img/site/creditcard.png" title="支持信用卡支付">
                  <img src="../../img/site/7day.png" title="消费者保障服务,承诺7天退货">
                  <img src="../../img/site/promise.png" title="消费者保障服务,承诺如实描述">
                  <c:if test="${o.status=='waitReview' and oi.isReviewed == 0}">
                    <span><a href="/forereview?pid=${oi.product.id}&oid=${o.id}&oiid=${oi.id}">
                      <button class="orderListItemReview">评价</button></a></span>
                  </c:if>
                  <c:if test="${o.status=='waitReview' and oi.isReviewed == 1}">
                    <p>已评价</p>
                  </c:if>
                  <c:if test="${o.status=='finish'}">
                    <p>该产品已评价</p>
                  </c:if>
                </div>
              </div>
            </td>

            <td class="orderItemProductInfoPartTD" width="145px">
              <p>原价:<span class="orderListItemProductOriginalPrice">￥<fmt:formatNumber type="number"
                                                                                       value="${oi.product.originalPrice}"
                                                                                       minFractionDigits="2"/></span>
              </p>
              <p>折扣价:<span class="orderListItemProductPrice">￥<fmt:formatNumber type="number"
                                                                                value="${oi.product.promotePrice}"
                                                                                minFractionDigits="2"/></span></p>
              <p>数量: <span class="orderListItemProductPrice">${oi.number}</span></p>
            </td>

            <c:if test="${st.count==1}">
              <td valign="top" rowspan="${fn:length(o.orderItemList)}"
                  class="orderListItemNumberTD orderItemOrderInfoPartTD" width="100px">
                <span class="orderListItemNumber">${o.totalNumber}</span>
              </td>

              <td valign="top" rowspan="${fn:length(o.orderItemList)}" width="120px"
                  class="orderListItemProductRealPriceTD orderItemOrderInfoPartTD">
                <div class="orderListItemProductRealPrice">
                  ￥<fmt:formatNumber minFractionDigits="2" maxFractionDigits="2" type="number"
                                     value="${o.total}"/></div>
                <div class="orderListItemPriceWithTransport">(含运费：￥0.00)</div>
              </td>

              <td valign="top" rowspan="${fn:length(o.orderItemList)}"
                  class="orderListItemButtonTD orderItemOrderInfoPartTD" width="100px">
                <c:if test="${o.status=='waitConfirm'}">
                  <a href="/foreconfirmPay?oid=${o.id}">
                    <button class="orderListItemConfirm">确认收货</button>
                  </a>
                </c:if>
                <c:if test="${o.status=='waitPay'}">
                  <a href="/front/alipay.jsp?oid=${o.id}&total=${o.total}">
                    <button class="orderListItemConfirm">付款</button>
                  </a>
                </c:if>
                <c:if test="${o.status=='waitDelivery'}">
                  <span>待发货</span>
                  <%--<button class="btn btn-info btn-sm ask2delivery" link="admin_order_delivery?id=${o.id}">催卖家发货</button>--%>
                </c:if>
                <c:if test="${o.status=='waitReview'}">
                  <p>产品待评价</p>
                </c:if>
                <c:if test="${o.status=='finish'}">
                  <p>产品都已评价</p>
                </c:if>
              </td>
            </c:if>
          </tr>
        </c:forEach>
      </table>
    </c:forEach>
  </div>
</div>