<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="../js/jquery/3.2.1/jquery.js"></script>
    <link href="../css/bootstrap/3.3.7/bootstrap.min.css" rel="stylesheet">
    <script src="../js/bootstrap/3.3.7/bootstrap.min.js"></script>
    <link href="../css/back/style.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-offset-3 col-md-6">
            <form class="form-horizontal" action="" method="post">
                <span class="heading">用户登录</span>
                <input type="hidden" name="action" value="post">
                <%
                    String action = request.getParameter("action");
                    if (action != null && action.equals("post")) {
                        String username = request.getParameter("username");
                        String password = request.getParameter("password");
                        if (username == null || !username.equals("admin")) {
                %>
                <p style="color: #ac2925">username not correct.</p>
                <%
                        } else if (password == null || !password.equals("admin")) {
                            out.println("<p style=\"color: #ac2925\">password not correct.</p>");
                        } else {
                            request.getSession().setAttribute("admin", "true");
                            response.sendRedirect("/admin_category_list");
                        }
                    }
                %>
                <div class="form-group">
                    <input type="text" class="form-control" name="username" placeholder="用户名或电子邮件">
                    <i class="fa fa-user"></i>
                </div>
                <div class="form-group help">
                    <input type="password" class="form-control" name="password" placeholder="密　码">
                    <i class="fa fa-lock"></i>
                    <a href="#" class="fa fa-question-circle"></a>
                </div>
                <div class="form-group">
                    <div class="main-checkbox">
                        <input type="checkbox" value="None" id="checkbox1" name="check"/>
                        <label for="checkbox1"></label>
                    </div>
                    <span class="text">Remember me</span>
                    <button type="submit" class="btn btn-default">登录</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
