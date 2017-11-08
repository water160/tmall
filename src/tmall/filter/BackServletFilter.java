package tmall.filter;

import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BackServletFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filter) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        /**
         * 路径：resin/webapps/my_proj/test/request.jsp
         http://localhost:8080/my_proj/test/request.jsp?***

         request.getRequestURI() /my_proj/test/request.jsp
         request.getRequestURL() http://localhost:8080/my_proj/test/request.jsp
         request.getContextPath() /my_proj 带/WEB-INF的目录地址
         request.getServletPath()  /test/request.jsp
         */
//        String contextPath = request.getServletContext().getContextPath();//返回站点的根路径
//        String uri = request.getRequestURI();
//        uri = StringUtils.remove(uri, contextPath);
        String uri = request.getServletPath();//获取浏览器请求的路径（除根目录外）
        if (uri.startsWith("/admin")) {
            String servletPath = StringUtils.substringBetween(uri, "_", "_") + "Servlet";// 取出admin_category_list
            String method = StringUtils.substringAfterLast(uri, "_");
            request.setAttribute("method", method);
            req.getRequestDispatcher("/" + servletPath).forward(request, response);
            return;
        }
        filter.doFilter(request, response);
    }

    public void destroy() {

    }

}
