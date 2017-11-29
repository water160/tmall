<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<div class="productDetailDiv">
  <div class="productDetailTopPart">
    <a href="#nowhere" class="productDetailTopPartSelectedLink selected">商品详情</a>
    <a href="#nowhere" class="productDetailTopReviewLink">累计评价 <span
            class="productDetailTopReviewLinkNumber">${product.reviewCount}</span> </a>
  </div>

  <div class="productParamterPart">
    <div class="productParamter">产品参数：</div>

    <div class="productParamterList">
      <c:forEach items="${pv_list}" var="pv">
        <c:if test="${!empty pv.value}">
          <span><b>${pv.property.name}:</b>  ${pv.value} </span>
        </c:if>
        <c:if test="${empty pv.value}">
          <span><b>${pv.property.name}:</b>  (暂无数据) </span>
        </c:if>
      </c:forEach>
    </div>
    <div style="clear:both"></div>
  </div>

  <div class="productDetailImagesPart">
    <c:forEach items="${product.productDetailImages}" var="pi">
      <img src="../../img/productDetail/${pi.id}.jpg">
    </c:forEach>
  </div>
</div>