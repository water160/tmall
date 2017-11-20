package tmall.servlet;

import tmall.bean.Category;
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
        return "home.jsp";
    }
}
