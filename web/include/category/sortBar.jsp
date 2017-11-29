<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<script>
  $(function () {
    $("input.sortBarPrice").keyup(function () {
      var num = $(this).val();
      if (num.length == 0) {
        $("div.productUnit").show();
        return;
      }

      num = parseInt(num);
      if (isNaN(num))
        num = 0;
      if (num <= 0)
        num = 0;
      $(this).val(num);

      var begin = $("input.beginPrice").val();
      var end = $("input.endPrice").val();
      if (begin == null) {
        begin = 0;
      }
      if (end == null) {
        end = Number.MAX_VALUE;
      }
      if (!isNaN(begin) && !isNaN(end)) {
        $("div.productUnit").hide();
        $("div.productUnit").each(function () {
          var price = $(this).attr("price");
          price = new Number(price);

          if (price >= begin && price <= end)
            $(this).show();
        });
      }
    });
  });
</script>

<div class="categorySortBar">

  <table class="categorySortBarTable categorySortTable">
    <tr>
      <td
              <c:if test="${param.sort=='all'||empty param.sort}">class="grayColumn"</c:if> >
        <a href="?cid=${category.id}&sort=all">综合<span class="glyphicon glyphicon-arrow-down"></span></a>
      </td>
      <td
              <c:if test="${param.sort=='review'}">class="grayColumn"</c:if> >
        <a href="?cid=${category.id}&sort=review">人气<span class="glyphicon glyphicon-arrow-down"></span></a>
      </td>
      <td <c:if test="${param.sort=='date'}">class="grayColumn"</c:if>>
        <a href="?cid=${category.id}&sort=date">新品<span class="glyphicon glyphicon-arrow-down"></span></a>
      </td>
      <td <c:if test="${param.sort=='saleCount'}">class="grayColumn"</c:if>>
        <a href="?cid=${category.id}&sort=saleCount">销量<span class="glyphicon glyphicon-arrow-down"></span></a>
      </td>
      <td <c:if test="${param.sort=='price'}">class="grayColumn"</c:if>>
        <a href="?cid=${category.id}&sort=price">价格<span class="glyphicon glyphicon-resize-vertical"></span></a>
      </td>
    </tr>
  </table>

  <table class="categorySortBarTable">
    <tr>
      <td><input class="sortBarPrice beginPrice" type="text" placeholder="请输入最低价格"></td>
      <td class="priceMiddleColumn">-</td>
      <td><input class="sortBarPrice endPrice" type="text" placeholder="请输入最高价格"></td>
    </tr>
  </table>
</div>