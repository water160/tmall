package tmall.filter;

import org.apache.commons.lang.StringUtils;
import tmall.bean.Category;
import tmall.bean.OrderItem;
import tmall.bean.User;
import tmall.dao.CategoryDAO;
import tmall.dao.OrderItemDAO;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ForeServletFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String contextPath = request.getServletContext().getContextPath();
        request.getServletContext().setAttribute("contextPath", contextPath);

        User user = (User) request.getSession().getAttribute("user");
        int cartTotalItemNumber = 0;//购物车中商品数量
        if(user != null) {
            List<OrderItem> oi_list = new OrderItemDAO().listByUser(user.getId());
            for(OrderItem oi : oi_list) {
                cartTotalItemNumber += oi.getNumber();
            }
        }

        List<Category> c_list = (List<Category>) request.getAttribute("c_list");
        if(c_list == null) {
            c_list = new CategoryDAO().list();
            request.setAttribute("c_list", c_list);
        }
        /*
        * 路径：resin/webapps/my_proj/test/request.jsp
            http://localhost:8080/my_proj/test/request.jsp?***

             request.getRequestURI() /my_proj/test/request.jsp
             request.getRequestURL() http://localhost:8080/my_proj/test/request.jsp
             request.getContextPath() /my_proj 带/WEB-INF的目录地址
             request.getServletPath()  /test/request.jsp
        */
        String uri = request.getRequestURI();
        uri = StringUtils.remove(uri, contextPath);

        if(uri.startsWith("/fore") && !uri.startsWith("/foreServlet")) {//这里不用按照BaseServletFilter一样需要找到相应的类似categoryServlet进行跳转
            String method = StringUtils.substringAfterLast(uri, "/fore");
            request.setAttribute("method", method);
            req.getRequestDispatcher("/foreServlet").forward(request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
