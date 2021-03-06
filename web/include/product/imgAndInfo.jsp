<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<script>
  $(function () {
    var stock = ${product.stock};

    //可以输入数值，范围在1-stock之间
    $(".productNumberSetting").keyup(function () {
      var num = $(".productNumberSetting").val();
      num = parseInt(num);
      if (isNaN(num))
        num = 1;
      if (num <= 0)
        num = 1;
      if (num > stock)
        num = stock;
      $(".productNumberSetting").val(num);
    });

    //添加和减少购买物品数量
    $(".increaseNumber").click(function () {
      var num = $(".productNumberSetting").val();
      num++;
      if (num > stock)
        num = stock;
      $(".productNumberSetting").val(num);
    });
    $(".decreaseNumber").click(function () {
      var num = $(".productNumberSetting").val();
      --num;
      if (num <= 0)
        num = 1;
      $(".productNumberSetting").val(num);
    });

    $(".addCartButton").removeAttr("disabled");

    //监听“加入购物车”按钮
    $(".addCartLink").click(function () {
      var page = "/forecheckLogin";
      $.get(
          page,
          function (result) {
            if ("success" == result) {
              var pid = ${product.id};
              var num = $(".productNumberSetting").val();
              var addCartpage = "/foreaddCart";
              $.get(
                  addCartpage,
                  {"pid": pid, "num": num},
                  function (result) {
                    if ("success" == result) {
                      $(".addCartButton").html("已加入购物车");
                      $(".addCartButton").animate({
                        background: 'lightgray',
                        border: '1px solid lightgray',
                        color: 'black'
                      }, 500);
                      $(".addCartButton").html("加入购物车");
                      $(".addCartButton").animate({
                        background: '#C40000',
                        border: '1px solid #C40000',
                        color: 'white'
                      }, 500);
                    }
                    else {
                    }
                  }
              );
            }
            else {
              $("#loginModal").modal('show');
            }
          }
      );
      return false;
    });

    //监听“立即购买”按钮
    $(".buyLink").click(function () {
      var page = "/forecheckLogin";
      $.get(
          page,
          function (result) {
            if ("success" == result) {
              var num = $(".productNumberSetting").val();
              location.href = $(".buyLink").attr("href") + "&num=" + num;
            }
            else {
              $("#loginModal").modal('show');
            }
          }
      );
      return false;
    });

    //模态框中的提交按钮，对用户名和密码进行ajax登录验证
    $("button.loginSubmitButton").click(function () {
      var name = $("#name").val();
      var password = $("#password").val();

      if (0 == name.length || 0 == password.length) {
        $("span.errorMessage").html("请输入账号密码");
        $("div.loginErrorMessageDiv").show();
        return false;
      }

      var page = "foreloginAjax";
      $.get(
          page,
          {"name": name, "password": password},
          function (result) {
            if ("success" == result) {
              location.reload();
            }
            else {
              $("span.errorMessage").html("账号密码错误");
              $("div.loginErrorMessageDiv").show();
            }
          }
      );
      return true;
    });

    $("img.smallImage").mouseenter(function () {
      var bigImageURL = $(this).attr("bigImageURL");
      $("img.bigImg").attr("src", bigImageURL);
    });

    $("img.bigImg").on(
        function () {
          $("img.smallImage").each(function () {
            var bigImageURL = $(this).attr("bigImageURL");
            img = new Image();
            img.src = bigImageURL;

            img.onload = function () {
              $("div.img4load").append($(img));
            };
          });
        }
    );
  });

</script>

<div class="imgAndInfo">

  <div class="imgInimgAndInfo">
    <c:if test="${!empty product.firstProductImage}">
      <img src="../../img/productSingle/${product.firstProductImage.id}.jpg" class="bigImg">
    </c:if>
    <c:if test="${empty product.firstProductImage}">
      <img src="../../img/site/imgNotFound.jpg" class="bigImg">
    </c:if>

    <div class="smallImageDiv">
      <c:if test="${empty product.productSingleImages}">
        <img src="../../img/site/imgNotFound.jpg" bigImageURL="../../img/site/imgNotFound.jpg"
             class="smallImage">
      </c:if>
      <c:if test="${!empty product.productSingleImages}">
        <c:forEach items="${product.productSingleImages}" var="pi">
          <img src="../../img/productSingle_small/${pi.id}.jpg" bigImageURL="../../img/productSingle/${pi.id}.jpg"
               class="smallImage">
        </c:forEach>
      </c:if>
    </div>

    <div class="img4load hidden"></div>
  </div>

  <div class="infoInimgAndInfo">
    <div class="productTitle">
      ${product.name}
    </div>

    <div class="productSubTitle">
      ${product.subTitle}
    </div>

    <div class="productPrice">
      <div class="juhuasuan">
        <span class="juhuasuanBig">聚划算</span>
        <span>此商品即将参加聚划算，<span class="juhuasuanTime">1天19小时</span>后开始，</span>
      </div>

      <div class="productPriceDiv">
        <div class="gouwujuanDiv"><img height="16px" src="../../img/site/gouwujuan.png">
          <span> 全天猫实物商品通用</span>
        </div>

        <div class="originalDiv">
          <span class="originalPriceDesc">价格</span>
          <span class="originalPriceYuan">¥</span>
          <span class="originalPrice">
            <fmt:formatNumber type="number" value="${product.originalPrice}" minFractionDigits="2"/>
          </span>
        </div>

        <div class="promotionDiv">
          <span class="promotionPriceDesc">促销价 </span>
          <span class="promotionPriceYuan">¥</span>
          <span class="promotionPrice">
            <fmt:formatNumber type="number" value="${product.promotePrice}" minFractionDigits="2"/>
          </span>
        </div>
      </div>
    </div>

    <div class="productSaleAndReviewNumber">
      <div>销量 <span class="redColor boldWord"> ${product.saleCount }</span></div>
      <div>累计评价 <span class="redColor boldWord"> ${product.reviewCount}</span></div>
    </div>
    <div class="productNumber">
      <span>数量</span>
      <span>
        <span class="productNumberSettingSpan">
          <input class="productNumberSetting" type="text" value="1">
        </span>
        <span class="arrow">
          <a href="#nowhere" class="increaseNumber">
            <span class="updown">
              <img src="../../img/site/increase.png">
            </span>
          </a>
          <span class="updownMiddle"> </span>
          <a href="#nowhere" class="decreaseNumber">
            <span class="updown">
              <img src="../../img/site/decrease.png">
            </span>
          </a>
        </span>
        件</span>
      <span>库存${product.stock}件</span>
    </div>
    <div class="serviceCommitment">
      <span class="serviceCommitmentDesc">服务承诺</span>
      <span class="serviceCommitmentLink">
        <a href="#nowhere">正品保证</a>
        <a href="#nowhere">极速退款</a>
        <a href="#nowhere">赠运费险</a>
        <a href="#nowhere">七天无理由退换</a>
      </span>
    </div>

    <div class="buyDiv">
      <a class="buyLink" href="/forebuyone?pid=${product.id}">
        <button class="buyButton">立即购买</button>
      </a>
      <a href="#nowhere" class="addCartLink">
        <button class="addCartButton"><span class="glyphicon glyphicon-shopping-cart"></span>加入购物车</button>
      </a>
    </div>
  </div>
  <div style="clear:both"></div>
</div>