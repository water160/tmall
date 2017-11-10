package tmall.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import tmall.dao.*;
import tmall.util.Page;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 服务器通过BackServletFilter类处理过后会跳转到CategoryServlet中
 * 1. CategoryServlet extends BaseBackServlet, BaseBackServlet extends HttpServlet
 * 2. 服务器跳转到CategoryServlet后会访问其doGet()或doPost()方法（在访问过service()方法之后）
 * 3. 通过BaseBackServlet重写了service方法{获取分页，根据反射访问对应方法，根据对应方法的返回值进行服务端跳转，客户端跳转，或者直接输出字符串}
 * 4. 如何根据反射获取方法？
 * 取到从BackServletFilter中request.setAttribute()传过来的值listAllCategory，根据此list，借助反射机制调用CategoryServlet类中的list()方法
 * 即就调用了CategoryServlet.listAllCategory()方法
 */
public abstract class BaseBackServlet extends HttpServlet {
    protected CategoryDAO categoryDAO = new CategoryDAO();
    protected OrderDAO orderDAO = new OrderDAO();
    protected OrderItemDAO orderItemDAO = new OrderItemDAO();
    protected ProductDAO productDAO = new ProductDAO();
    protected ProductImageDAO productImageDAO = new ProductImageDAO();
    protected PropertyDAO propertyDAO = new PropertyDAO();
    protected PropertyValueDAO propertyValueDAO = new PropertyValueDAO();
    protected ReviewDAO reviewDAO = new ReviewDAO();
    protected UserDAO userDAO = new UserDAO();

    public abstract String add(HttpServletRequest request, HttpServletResponse response, Page page);

    public abstract String delete(HttpServletRequest request, HttpServletResponse response, Page page);

    public abstract String edit(HttpServletRequest request, HttpServletResponse response, Page page);

    public abstract String update(HttpServletRequest request, HttpServletResponse response, Page page);

    public abstract String list(HttpServletRequest request, HttpServletResponse response, Page page);

    public void service(HttpServletRequest request, HttpServletResponse response) {
        try {
            /*获取分页信息*/
            int start = 0;
            int count = 7;
            try {
                start = Integer.parseInt(request.getParameter("page.start"));
                if (start < 0) {
                    start = 0;
                }
            } catch (Exception e) {

            }
            try {
                count = Integer.parseInt(request.getParameter("page.count"));
            } catch (Exception e) {
            }
            Page page = new Page(start, count);

            /*借助反射，调用对应的方法*/
            String method = (String) request.getAttribute("method");
            Method m = this.getClass().getMethod(method, HttpServletRequest.class, HttpServletResponse.class, Page.class);
            String redirect = m.invoke(this, request, response, page).toString();

            /*根据方法的返回值，进行相应的客户端跳转，服务端跳转，或者仅仅是输出字符串*/

            if (redirect.startsWith("@"))
                response.sendRedirect(redirect.substring(1));
            else if (redirect.startsWith("%"))
                response.getWriter().print(redirect.substring(1));
            else
                request.getRequestDispatcher(redirect).forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public InputStream parseUpload(HttpServletRequest request, Map<String, String> params) {
        InputStream is = null;
        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            //设置上传文件大小为10M
            factory.setSizeThreshold(1024 * 10240);

            List items = upload.parseRequest(request);
            Iterator iter = items.iterator();
            while (iter.hasNext()) {
                FileItem fileItem = (FileItem) iter.next();
                if (!fileItem.isFormField()) {
                    //此处浏览器指定了以二进制的形式提交数据，不能通过常规的手段获取非File字段，在遍历item时通过item.isFormField，若为true则为常规字段
                    //如果此处提交的为文件
                    is = fileItem.getInputStream();//获取上传文件的输入流
                } else {
                    String paramName = fileItem.getFieldName();
                    String paramValue = fileItem.getString();
                    paramValue = new String(paramValue.getBytes("ISO-8859-1"), "UTF-8");
                    System.out.println(paramName + " " + paramValue);
                    params.put(paramName, paramValue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return is;
    }
}
