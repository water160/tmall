<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<script>
    $(function () {
        $("ul.pagination li.disabled a").click(function () {
            return false;
        });
    });
</script>
<div class="container">
    <ul class="pagination pull-right">
        <li <c:if test="${!page.hasPrevious()}">class="disabled"</c:if>>
            <a href="?page.start=0" aria-label="FirstPage"><span aria-hidden="true">«</span></a>
        </li>
        <li <c:if test="${!page.hasPrevious()}">class="disabled"</c:if>>
            <a href="?page.start=${page.start-page.count}" aria-label="PreviousPage"><span
                    aria-hidden="true">‹</span></a>
        </li>
        <c:forEach begin="0" end="${page.totalPage-1}" varStatus="status">
            <li <c:if test="${status.index * page.count == page.start}">class="disabled"</c:if>>
                <a href="?page.start=${status.index * page.count}">${status.count}</a>
            </li>
        </c:forEach>
        <li <c:if test="${!page.hasNext()}">class="disabled"</c:if>>
            <a href="?page.start=${page.start+page.count}" aria-label="NextPage"><span aria-hidden="true">›</span></a>
        </li>
        <li <c:if test="${!page.hasNext()}">class="disabled"</c:if>>
            <a href="?page.start=${page.last}" aria-label="LastPage"><span aria-hidden="true">»</span></a>
        </li>
    </ul>
</div>
