package tmall.servlet;

import tmall.bean.Order;
import tmall.util.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Date;

/**
 * Created by ZP on 2017/11/14.
 */
public class OrderServlet extends BaseBackServlet {
    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
        List<Order> order_list = orderDAO.list(page.getStart(), page.getCount());
        orderItemDAO.fill(order_list);
        int total = orderDAO.getTotal();
        page.setTotal(total);

        request.setAttribute("order_list", order_list);
        request.setAttribute("page", page);

        return "admin/listOrder.jsp";
    }

    public String delivery(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        Order order = orderDAO.getOrderById(id);
        order.setDeliveryDate(new Date());
        order.setStatus(orderDAO.waitConfirm);
        orderDAO.update(order);

        return "@admin_order_list";
    }
}
