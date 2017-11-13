<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<script>
  $(function () {
    $("ul.pagination li.disabled a").click(function () {
      return false;
    });
  });
</script>
<div class="container">
  <div class="row">
    <div class="pull-left">
      共<b class="blue"> ${page.total} </b>条数据, 每页显示 <b class="blue">${page.count} </b>条
    </div>
    <div class="pull-right">
      <ul class="pagination" style="padding: 0; margin: 0;">
        <li <c:if test="${!page.hasPrevious()}">class="disabled"</c:if>>
          <a href="?page.start=0${page.param}" aria-label="FirstPage"><span aria-hidden="true">«</span></a>
        </li>
        <li <c:if test="${!page.hasPrevious()}">class="disabled"</c:if>>
          <a href="?page.start=${page.start-page.count}${page.param}" aria-label="PreviousPage"><span
                  aria-hidden="true">‹</span></a>
        </li>
        <c:forEach begin="0" end="${page.totalPage-1}" varStatus="status">
          <li <c:if test="${status.index * page.count == page.start}">class="disabled"</c:if>>
            <a href="?page.start=${status.index * page.count}${page.param}">${status.count}</a>
          </li>
        </c:forEach>
        <li <c:if test="${!page.hasNext()}">class="disabled"</c:if>>
          <a href="?page.start=${page.start+page.count}${page.param}" aria-label="NextPage"><span
                  aria-hidden="true">›</span></a>
        </li>
        <li <c:if test="${!page.hasNext()}">class="disabled"</c:if>>
          <a href="?page.start=${page.last}${page.param}" aria-label="LastPage"><span aria-hidden="true">»</span></a>
        </li>
      </ul>
    </div>
  </div>
</div>
