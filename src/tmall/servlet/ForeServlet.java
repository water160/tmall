package tmall.servlet;

import org.springframework.web.util.HtmlUtils;
import tmall.bean.*;
import tmall.dao.CategoryDAO;
import tmall.dao.ProductDAO;
import tmall.dao.ProductImageDAO;
import tmall.util.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
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

    public String product(HttpServletRequest request, HttpServletResponse response, Page page) {
        int pid = Integer.parseInt(request.getParameter("pid"));
        Product product = productDAO.getProductById(pid);
        //获取产品单个图片和详情图片
        List<ProductImage> productSingleImages = productImageDAO.list(product, ProductImageDAO.typeSingle);
        List<ProductImage> productDetailImages = productImageDAO.list(product, ProductImageDAO.typeDetail);
        product.setProductSingleImages(productSingleImages);
        product.setProductDetailImages(productDetailImages);

        List<PropertyValue> pv_list = propertyValueDAO.list(pid);//获取产品的属性值

        List<Review> reviews = reviewDAO.list(pid);//获取产品的评论

        productDAO.setSaleAndReviewNumber(product);//获取产品的销量和评论数，利用OrderItemDAO中此pid的number和review

        request.setAttribute("reviews", reviews);
        request.setAttribute("product", product);
        request.setAttribute("pv_list", pv_list);

        return "/front/product.jsp";
    }

    public String checkLogin(HttpServletRequest request, HttpServletResponse response, Page page) {
        User user = (User) request.getSession().getAttribute("user");
        if(user != null) {
            return "%success";
        }
        return "%fail";
    }

    public String loginAjax(HttpServletRequest request, HttpServletResponse response, Page page) {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        PrintWriter pw = null;
        User user = userDAO.userLogin(name, password);

        if(user == null) {
            request.setAttribute("msg", "账号密码错误");
            return "%fail";
        }
        request.getSession().setAttribute("user", user);
        return "%success";
    }
}
