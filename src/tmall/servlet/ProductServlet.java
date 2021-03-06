package tmall.servlet;

import tmall.bean.Category;
import tmall.bean.Product;
import tmall.bean.PropertyValue;
import tmall.util.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.List;

public class ProductServlet extends BaseBackServlet {
    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        int cid = Integer.parseInt(request.getParameter("cid"));
        Category c = categoryDAO.getCategoryById(cid);

        String name = request.getParameter("name");
        String subTitle = request.getParameter("subTitle");
        float originalPrice = Float.parseFloat(request.getParameter("originalPrice"));
        float promotePrice = Float.parseFloat(request.getParameter("promotePrice"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        Date createDate = new Date(System.currentTimeMillis());

        Product product = new Product();
        product.setCategory(c);
        product.setName(name);
        product.setSubTitle(subTitle);
        product.setOriginalPrice(originalPrice);
        product.setPromotePrice(promotePrice);
        product.setStock(stock);
        product.setCreateDate(createDate);
        productDAO.add(product);

        return "@admin_product_list?cid=" + cid;
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = productDAO.getProductById(id);
        productDAO.delete(id);
        return "@admin_product_list?cid=" + product.getCategory().getId();
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = productDAO.getProductById(id);
        request.setAttribute("product", product);

        return "admin/editProduct.jsp";
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
        int cid = Integer.parseInt(request.getParameter("cid"));
        Category c = categoryDAO.getCategoryById(cid);

        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String subTitle = request.getParameter("subTitle");
        float originalPrice = Float.parseFloat(request.getParameter("originalPrice"));
        float promotePrice = Float.parseFloat(request.getParameter("promotePrice"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        Date createDate = new Date(System.currentTimeMillis());

        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setSubTitle(subTitle);
        product.setCategory(c);
        product.setOriginalPrice(originalPrice);
        product.setPromotePrice(promotePrice);
        product.setStock(stock);
        product.setCreateDate(createDate);
        productDAO.update(product);

        return "@admin_product_list?cid=" + cid;
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
        int cid = Integer.parseInt(request.getParameter("cid"));
        Category c = categoryDAO.getCategoryById(cid);
        List<Product> product_list = productDAO.list(cid, page.getStart(), page.getCount());

        int total = productDAO.getTotal(cid);
        page.setTotal(total);
        page.setParam("&cid=" + cid);

        request.setAttribute("product_list", product_list);
        request.setAttribute("c", c);
        request.setAttribute("page", page);

        return "admin/listProduct.jsp";
    }

    public String editPropertyValue(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = productDAO.getProductById(id);
        request.setAttribute("product", product);

        //初始化propertyValueList，由某产品ID和某产品属性ID查询出来，如果数据库中没有对应的的属性值就添加进去
        propertyValueDAO.init(product);

        List<PropertyValue> propertyValue_list = propertyValueDAO.list(product.getId());

        request.setAttribute("propertyValue_list", propertyValue_list);
        return "admin/editPropertyValue.jsp";
    }

    public String updatePropertyValue(HttpServletRequest request, HttpServletResponse response, Page page) {
        int pvid = Integer.parseInt(request.getParameter("pvid"));
        String value = request.getParameter("value");

        PropertyValue propertyValue = propertyValueDAO.getPropertyValueById(pvid);
        propertyValue.setValue(value);
        propertyValueDAO.update(propertyValue);
        return "%success";
    }
}
