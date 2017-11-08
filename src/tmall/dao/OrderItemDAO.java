package tmall.dao;

import tmall.bean.Order;
import tmall.bean.OrderItem;
import tmall.bean.Product;
import tmall.bean.User;
import tmall.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAO {

    public int getTotal() {
        int total = 0;

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            String sql = "select count(*) from orderitem";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public void add(OrderItem bean) {
        String sql = "insert into orderitem values(null, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            pstmt.setInt(1, bean.getProduct().getId());
            //订单项在创建的时候，是没有订单信息的，设置为-1
            if (bean.getOrder() == null) {
                pstmt.setInt(2, -1);
            } else {
                pstmt.setInt(2, bean.getOrder().getId());
            }
            pstmt.setInt(3, bean.getUser().getId());
            pstmt.setInt(4, bean.getNumber());
            pstmt.execute();
            conn.commit();
            conn.setAutoCommit(true);

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                bean.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(OrderItem bean) {

        String sql = "update orderitem set pid= ?, oid=?, uid=?,number=?  where id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            pstmt.setInt(1, bean.getProduct().getId());
            if (bean.getOrder() == null) {
                pstmt.setInt(2, -1);
            } else {
                pstmt.setInt(2, bean.getOrder().getId());
            }
            pstmt.setInt(3, bean.getUser().getId());
            pstmt.setInt(4, bean.getNumber());
            pstmt.setInt(5, bean.getId());
            pstmt.execute();
            conn.commit();
            conn.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            conn.setAutoCommit(false);
            String sql = "delete from orderitem where id = " + id;
            stmt.execute(sql);
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public OrderItem getOrderItemById(int id) {
        OrderItem bean = null;

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {

            String sql = "select * from orderitem where id = " + id;
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                int pid = rs.getInt("pid");
                int oid = rs.getInt("oid");
                int uid = rs.getInt("uid");
                int number = rs.getInt("number");
                Product product = new ProductDAO().getProductById(pid);
                User user = new UserDAO().get(uid);

                bean = new OrderItem();
                bean.setId(id);
                bean.setProduct(product);
                if (oid != -1) {
                    Order order = new OrderDAO().getOrderById(oid);
                    bean.setOrder(order);
                }
                bean.setUser(user);
                bean.setNumber(number);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public List<OrderItem> listByUser(int uid) {
        return listByUser(uid, 0, Short.MAX_VALUE);
    }

    public List<OrderItem> listByUser(int uid, int start, int count) {
        List<OrderItem> beans = new ArrayList<OrderItem>();
        String sql = "select * from orderitem where uid = ? and oid=-1 order by id desc limit ?,? ";

        try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, uid);
            pstmt.setInt(2, start);
            pstmt.setInt(3, count);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                OrderItem bean = new OrderItem();
                int id = rs.getInt(1);
                int pid = rs.getInt("pid");
                int oid = rs.getInt("oid");
                int number = rs.getInt("number");

                Product product = new ProductDAO().getProductById(pid);
                if (-1 != oid) {
                    Order order = new OrderDAO().getOrderById(oid);
                    bean.setOrder(order);
                }

                User user = new UserDAO().get(uid);
                bean.setProduct(product);
                bean.setUser(user);
                bean.setNumber(number);
                bean.setId(id);
                beans.add(bean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return beans;
    }

    public List<OrderItem> listByOrder(int oid) {
        return listByOrder(oid, 0, Short.MAX_VALUE);
    }

    public List<OrderItem> listByOrder(int oid, int start, int count) {
        List<OrderItem> beans = new ArrayList<OrderItem>();

        String sql = "select * from orderitem where oid = ? order by id desc limit ?,? ";

        try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, oid);
            pstmt.setInt(2, start);
            pstmt.setInt(3, count);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                OrderItem bean = new OrderItem();
                int id = rs.getInt(1);
                int pid = rs.getInt("pid");
                int uid = rs.getInt("uid");
                int number = rs.getInt("number");

                Product product = new ProductDAO().getProductById(pid);
                if (-1 != oid) {
                    Order order = new OrderDAO().getOrderById(oid);
                    bean.setOrder(order);
                }

                User user = new UserDAO().get(uid);
                bean.setProduct(product);

                bean.setUser(user);
                bean.setNumber(number);
                bean.setId(id);
                beans.add(bean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return beans;
    }

    public void fill(List<Order> orderList) {
        for (Order order : orderList) {
            List<OrderItem> oiList = listByOrder(order.getId());
            float total = 0;
            int totalNumber = 0;
            for (OrderItem oi : oiList) {
                total += oi.getNumber() * oi.getProduct().getPromotePrice();
                totalNumber += oi.getNumber();
            }
            order.setTotal(total);
            order.setOrderItemList(oiList);
            order.setTotalNumber(totalNumber);
        }

    }

    public void fill(Order order) {
        List<OrderItem> oiList = listByOrder(order.getId());
        float total = 0;
        for (OrderItem oi : oiList) {
            total += oi.getNumber() * oi.getProduct().getPromotePrice();
        }
        order.setTotal(total);
        order.setOrderItemList(oiList);
    }

    public List<OrderItem> listByProduct(int pid) {
        return listByProduct(pid, 0, Short.MAX_VALUE);
    }

    public List<OrderItem> listByProduct(int pid, int start, int count) {
        List<OrderItem> beans = new ArrayList<OrderItem>();

        String sql = "select * from orderitem where pid = ? order by id desc limit ?,? ";

        try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, pid);
            pstmt.setInt(2, start);
            pstmt.setInt(3, count);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                OrderItem bean = new OrderItem();
                int id = rs.getInt(1);

                int uid = rs.getInt("uid");
                int oid = rs.getInt("oid");
                int number = rs.getInt("number");

                Product product = new ProductDAO().getProductById(pid);
                if (-1 != oid) {
                    Order order = new OrderDAO().getOrderById(oid);
                    bean.setOrder(order);
                }

                User user = new UserDAO().get(uid);
                bean.setProduct(product);

                bean.setUser(user);
                bean.setNumber(number);
                bean.setId(id);
                beans.add(bean);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return beans;
    }

    public int getSaleCount(int pid) {
        int total = 0;
        try (Connection conn = DBUtil.getConnection(); Statement stmt = conn.createStatement()) {

            String sql = "select sum(number) from orderitem where pid = " + pid;
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }
}
