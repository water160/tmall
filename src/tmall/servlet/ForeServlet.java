package tmall.servlet;

import org.springframework.web.util.HtmlUtils;
import tmall.bean.*;
import tmall.comparator.*;
import tmall.dao.CategoryDAO;
import tmall.dao.ProductDAO;
import tmall.dao.ProductImageDAO;
import tmall.util.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
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

    public String category(HttpServletRequest request, HttpServletResponse response, Page page) {
        int cid = Integer.parseInt(request.getParameter("cid"));

        Category category = new CategoryDAO().getCategoryById(cid);
        new ProductDAO().fill(category);//category中有一个productList
        new ProductDAO().setSaleAndReviewNumber(category.getProductList());

        String sort = request.getParameter("sort");
        if(sort != null) {
            switch (sort) {
                case "review":
                    Collections.sort(category.getProductList(), new ProductReviewComparator());
                    break;
                case "date":
                    Collections.sort(category.getProductList(), new ProductDateComparator());
                    break;
                case "saleCount":
                    Collections.sort(category.getProductList(), new ProductSaleCountComparator());
                    break;
                case "price":
                    Collections.sort(category.getProductList(), new ProductPriceComparator());
                    break;
                case "all":
                    Collections.sort(category.getProductList(), new ProductAllComparator());
                    break;
            }
        }

        request.setAttribute("category", category);
        return "/front/category.jsp";
    }

    public String search(HttpServletRequest request, HttpServletResponse response, Page page) {
        String keyword = request.getParameter("keyword");
        List<Product> p_list = new ProductDAO().search(keyword, 0, 32767);
        request.setAttribute("p_list", p_list);
        return "/front/searchResult.jsp";
    }

    /**
     * 立即购买按钮，页面跳转至forebuy?pid=XXX&num=XXX，不保存状态
     */
    public String buyone(HttpServletRequest request, HttpServletResponse response, Page page) {
        int pid = Integer.parseInt(request.getParameter("pid"));
        int num = Integer.parseInt(request.getParameter("num"));

        return "@/forebuy?pid=" + pid + "&num=" + num;
    }

    /**
     * 立即购买的页面处理，完成后跳转至buy.jsp
     */
    public String buy(HttpServletRequest request, HttpServletResponse response, Page page) {
        int pid = Integer.parseInt(request.getParameter("pid"));
        int num = Integer.parseInt(request.getParameter("num"));
        Product product = productDAO.getProductById(pid);
        float totalPrice = 0.0F;

        totalPrice = product.getPromotePrice() * num;//获取购买这种商品的总价格
        request.setAttribute("product", product);
        request.setAttribute("number", num);
        request.setAttribute("totalPrice", totalPrice);
        return "/front/buy.jsp";
    }

    /**
     * 加入购物车，页面跳转至buyAll.jsp，保存状态
     */
    //TODO:修改此处的增加销量策略，改为完成订单后才计算
    public String addCart(HttpServletRequest request, HttpServletResponse response, Page page) {
        int pid = Integer.parseInt(request.getParameter("pid"));
        Product product = productDAO.getProductById(pid);
        int num = Integer.parseInt(request.getParameter("num"));

        User user = (User) request.getSession().getAttribute("user");
        boolean found = false;

        List<OrderItem> oi_list = orderItemDAO.listByUser(user.getId());
        for(OrderItem oi : oi_list) {
            if(oi.getProduct().getId() == product.getId()) {
                if(oi.getNumber() + num <= oi.getProduct().getStock()) {
                    oi.setNumber(oi.getNumber() + num);
                } else {
                    oi.setNumber(oi.getProduct().getStock());
                }
                orderItemDAO.update(oi);
                found = true;
                break;
            }
        }

        if(!found) {
            OrderItem oi = new OrderItem();
            oi.setProduct(product);
            oi.setUser(user);
            oi.setNumber(num);
            orderItemDAO.add(oi);
        }
        return "%success";
    }

    public String cart(HttpServletRequest request, HttpServletResponse response, Page page) {
        User user = (User) request.getSession().getAttribute("user");
        List<OrderItem> oi_list = orderItemDAO.listByUser(user.getId());
        request.setAttribute("oi_list", oi_list);
        return "/front/cart.jsp";
    }

    public String buyAll(HttpServletRequest request, HttpServletResponse response, Page page) {
        String[] oi_ids = request.getParameterValues("oiid");
        List<OrderItem> oi_buyAll_list = new ArrayList<>();
        float totalPrice = 0.0F;

        for(String str : oi_ids) {
            int oi_id = Integer.parseInt(str);
            OrderItem oi = orderItemDAO.getOrderItemById(oi_id);
            totalPrice += oi.getProduct().getPromotePrice() * oi.getNumber();//获取所有选中的oiid的价格总数
            oi_buyAll_list.add(oi);
        }
        request.getSession().setAttribute("oi_buyAll_list", oi_buyAll_list);
        request.setAttribute("totalPrice", totalPrice);
        return "/front/buyAll.jsp";
    }

    public String changeOrderItem(HttpServletRequest request, HttpServletResponse response, Page page) {
        User user = (User) request.getSession().getAttribute("user");
        if(user == null) {
            return "%fail";
        }

        int pid = Integer.parseInt(request.getParameter("pid"));
        int number = Integer.parseInt(request.getParameter("number"));
        List<OrderItem> oi_list = orderItemDAO.listByUser(user.getId());
        for(OrderItem oi : oi_list) {
            if(oi.getProduct().getId() == pid) {
                orderItemDAO.update(oi);
                break;
            }
        }
        return "%success";
    }

    public String deleteOrderItem(HttpServletRequest request, HttpServletResponse response, Page page) {
        User user = (User) request.getSession().getAttribute("user");
        if(user == null) {
            return "%fail";
        }
        int oiid = Integer.parseInt(request.getParameter("oiid"));
        orderItemDAO.delete(oiid);
        return "%success";
    }

}
