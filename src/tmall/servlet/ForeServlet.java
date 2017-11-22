package tmall.servlet;

import org.springframework.web.util.HtmlUtils;
import tmall.bean.Category;
import tmall.bean.User;
import tmall.dao.CategoryDAO;
import tmall.dao.ProductDAO;
import tmall.util.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ForeServlet extends BaseForeServlet {
    public String home(HttpServletRequest request, HttpServletResponse response, Page page) {
        List<Category> c_list = new CategoryDAO().list();//列举所有分类
        new ProductDAO().fill(c_list);//为所有分类填充产品集合
        new ProductDAO().fillByRow(c_list);//Category中的productListByRow【List<List<Product>>】被填充，一行8个
        request.setAttribute("c_list", c_list);
        return "/front/home.jsp";
    }

    public String register(HttpServletRequest request, HttpServletResponse response, Page page) {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        name = HtmlUtils.htmlEscape(name);//防止恶意注册，如：<script>alert('papapa')</script>弹出一个对话框

        boolean exist = userDAO.isExist(name);
        if(exist) {
            request.setAttribute("msg", "用户名已经被使用，请重新输入用户名");
            return "/front/register.jsp";
        }
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        userDAO.add(user);

        return "@/front/registerSuccess.jsp";
    }

    public String login(HttpServletRequest request, HttpServletResponse response, Page page) {
        String name = request.getParameter("name");
        name = HtmlUtils.htmlEscape(name);
        String password = request.getParameter("password");

        User user = userDAO.userLogin(name, password);
        if(user == null) {
            request.setAttribute("msg", "账号密码错误");
            return "/front/login.jsp";
        }
        request.getSession().setAttribute("user", user);
        return "@/forehome";
    }

    public String logout(HttpServletRequest request, HttpServletResponse response, Page page) {
        request.getSession().removeAttribute("user");
        return "@/forehome";
    }
}
