package tmall.servlet;

import tmall.bean.Category;
import tmall.bean.Property;
import tmall.util.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class PropertyServlet extends BaseBackServlet {

    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        int cid = Integer.parseInt(request.getParameter("cid"));
        Category c = categoryDAO.getCategoryById(cid);

        String name = request.getParameter("name");
        Property property = new Property();
        property.setCategory(c);
        property.setName(name);
        propertyDAO.add(property);

        return "@admin_property_list?cid=" + cid;
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        Property property = propertyDAO.getPropertyById(id);
        propertyDAO.delete(id);

        return "@admin_property_list?cid=" + property.getCategory().getId();
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        Property property = propertyDAO.getPropertyById(id);
        request.setAttribute("property", property);

        return "admin/editProperty.jsp";
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
        int cid = Integer.parseInt(request.getParameter("cid"));
        Category category = categoryDAO.getCategoryById(cid);

        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        Property property = new Property();
        property.setId(id);
        property.setName(name);
        property.setCategory(category);
        propertyDAO.update(property);

        return "@admin_property_list?cid=" + cid;
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
        int cid = Integer.parseInt(request.getParameter("cid"));
        Category c = categoryDAO.getCategoryById(cid);
        List<Property> property_list = propertyDAO.list(cid, page.getStart(), page.getCount());
        int total = propertyDAO.getTotal(cid);

        page.setTotal(total);
        page.setParam("&cid=" + cid);

        request.setAttribute("property_list", property_list);
        request.setAttribute("c", c);
        request.setAttribute("page", page);

        return "admin/listProperty.jsp";
    }
}
