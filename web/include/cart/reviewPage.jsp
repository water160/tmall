<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<div class="reviewDiv">
  <div class="reviewProductInfoDiv">
    <div class="reviewProductInfoImg">
      <c:if test="${!empty product.firstProductImage}">
        <img width="400px" height="400px" src="../../img/productSingle/${product.firstProductImage.id}.jpg">
      </c:if>
      <c:if test="${empty product.firstProductImage}">
        <img width="400px" height="400px" src="../../img/site/imgNotFound.jpg">
      </c:if>
    </div>

    <div class="reviewProductInfoRightDiv">
      <div class="reviewProductInfoRightText">
        ${product.name}
      </div>

      <table class="reviewProductInfoTable">
        <tr>
          <td width="75px">折扣价</td>
          <td><span class="reviewProductInfoTablePrice">￥<fmt:formatNumber type="number" value="${product.originalPrice}" minFractionDigits="2"/></span> 元
          </td>
        </tr>
        <tr>
          <td>配送方式</td>
          <td>普通快递</td>
        </tr>
        <tr>
          <td>月销量</td>
          <td><span class="reviewProductInfoTableSellNumber">${product.saleCount}</span> 件</td>
        </tr>
      </table>

      <div class="reviewProductInfoRightBelowDiv">
        <span class="reviewProductInfoRightBelowImg"><img src="../../img/site/reviewLight.png"/></span>
        <span class="reviewProductInfoRightBelowText">现在查看的是 您所购买商品的信息于
          <fmt:formatDate value="${order.createDate}" pattern="yyyy年MM月dd"/>下单购买了此商品 </span>
      </div>
    </div>
    <div style="clear:both"></div>
  </div>
  <div class="reviewStatisticsDiv">
    <div class="reviewStatisticsLeft">
      <div class="reviewStatisticsLeftTop"></div>
      <div class="reviewStatisticsLeftContent">累计评价 <span class="reviewStatisticsNumber"> ${product.reviewCount}</span>
      </div>
      <div class="reviewStatisticsLeftFoot"></div>
    </div>
    <div class="reviewStatisticsRight">
      <div class="reviewStatisticsRightEmpty"></div>
      <div class="reviewStatisticsFoot"></div>
    </div>
  </div>

  <c:if test="${param.showonly==true}">
    <div class="reviewDivlistReviews">
      <c:forEach items="${reviews}" var="r">
        <div class="reviewDivlistReviewsEach">
          <div class="reviewDate"><fmt:formatDate value="${r.createDate}" pattern="yyyy-MM-dd"/></div>
          <div class="reviewContent">${r.content}</div>
          <div class="reviewUserInfo pull-right">${r.user.anonymousName}<span class="reviewUserInfoAnonymous">(匿名)</span></div>
        </div>
      </c:forEach>
    </div>
  </c:if>

  <c:if test="${param.showonly!=true}">
    <div class="makeReviewDiv">
      <form method="post" action="/foredoreview">
        <div class="makeReviewText">其他买家，需要你的建议哦！</div>
        <table class="makeReviewTable">
          <tr>
            <td class="makeReviewTableFirstTD">评价商品</td>
            <td><textarea name="content"></textarea></td>
          </tr>
        </table>
        <div class="makeReviewButtonDiv">
          <input type="hidden" name="oiid" value="${orderItem.id}">
          <input type="hidden" name="oid" value="${order.id}">
          <input type="hidden" name="pid" value="${product.id}">
          <button type="submit">提交评价</button>
        </div>
      </form>
    </div>
  </c:if>

</div>