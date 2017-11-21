<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>

<script>
  $(function(){//随机数控制
    $("div.productsAsideCategorys div.row a").each(function(){
      var v = Math.round(Math.random() *6);
      if(v == 1)
        $(this).css("color","#87CEFA");
    });
  });
</script>
<!-- 产品分类链接 -->
<c:forEach items="${c_list}" var="c">
  <div cid="${c.id}" class="productsAsideCategorys">
    <c:forEach items="${c.productListByRow}" var="ps">
      <div class="row show1">
        <c:forEach items="${ps}" var="p">
          <c:if test="${!empty p.subTitle}">
            <a href="foreproduct?pid=${p.id}">
              <c:forEach items="${fn:split(p.subTitle, ' ')}" var="title" varStatus="st">
                <c:if test="${st.index==0}">
                  ${title}
                </c:if>
              </c:forEach>
            </a>
          </c:if>
        </c:forEach>
        <div class="seperator"></div>
      </div>
    </c:forEach>
  </div>
</c:forEach>