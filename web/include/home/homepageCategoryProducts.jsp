<!DOCTYPE html>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>

<div class="homepageCategoryProducts">
  <c:forEach items="${c_list}" var="c">
    <div class="eachHomepageCategoryProducts">
      <div class="left-mark"></div>
      <span class="categoryTitle">${c.name}</span>
      <br>
      <c:forEach items="${c.productList}" var="p" varStatus="st">
        <c:if test="${st.count<=5}">
          <div class="productItem" >
            <c:if test="${!empty p.firstProductImage.id}">
              <a href="/foreproduct?pid=${p.id}"><img width="100px" src="../../img/productSingle_middle/${p.firstProductImage.id}.jpg"></a>
            </c:if>
            <c:if test="${empty p.firstProductImage.id}">
              <a href="/foreproduct?pid=${p.id}"><img width="100px" src="../../img/site/imgNotFound.jpg"></a>
            </c:if>
            <a class="productItemDescLink" href="/foreproduct?pid=${p.id}">
              <span class="productItemDesc">[热销]
              ${fn:substring(p.name, 0, 20)}
              </span>
            </a>
            <span class="productPrice">
              <fmt:formatNumber type="number" value="${p.promotePrice}" minFractionDigits="2"/>
            </span>
          </div>
        </c:if>
      </c:forEach>
      <div style="clear:both"></div>
    </div>
  </c:forEach>

  <img id="endpng" class="endpng" src="../../img/site/end.png">
</div>