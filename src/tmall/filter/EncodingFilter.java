package tmall.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EncodingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        //1. contentType="text/html;charset=UTF-8"用来告知浏览器请求提交数据使用UTF-8编码
        //2. form表单中method使用post
        //这里是页面显示时使用UTF-8编码
        request.setCharacterEncoding("UTF-8");
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
