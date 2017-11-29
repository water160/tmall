package tmall.dao;

import tmall.bean.Order;
import tmall.bean.User;
import tmall.util.DBUtil;
import tmall.util.DateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDAO {

    public static final String waitPay = "waitPay";// 待付款
    public static final String waitDelivery = "waitDelivery";// 待发货
    public static final String waitConfirm = "waitConfirm";// 待收货
    public static final String waitReview = "waitReview";// 等待评价
    public static final String finish = "finish";// 订单完成
    public static final String delete = "delete";// 订单删除(都是一个状态)

    public int getTotal() {
        int total = 0;
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {

            String sql = "select count(*) from order_";

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public void add(Order bean) {

        String sql = "insert into order_ values(null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            pstmt.setString(1, bean.getOrderCode());
            pstmt.setString(2, bean.getAddress());
            pstmt.setString(3, bean.getPost());
            pstmt.setString(4, bean.getReceiver());
            pstmt.setString(5, bean.getMobile());
            pstmt.setString(6, bean.getUserMessage());
            pstmt.setTimestamp(7, DateUtil.date2time(bean.getCreateDate()));
            pstmt.setTimestamp(8, DateUtil.date2time(bean.getPayDate()));
            pstmt.setTimestamp(9, DateUtil.date2time(bean.getDeliveryDate()));
            pstmt.setTimestamp(10, DateUtil.date2time(bean.getConfirmDate()));
            pstmt.setInt(11, bean.getUser().getId());
            pstmt.setString(12, bean.getStatus());
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

    public void update(Order bean) {

        String sql = "update order_ set address=?, post=?, receiver=?, mobile=?, userMessage=? , createDate=?, payDate=?, deliveryDate=?, confirmDate=? , orderCode=?, uid=?, status=? where id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            pstmt.setString(1, bean.getAddress());
            pstmt.setString(2, bean.getPost());
            pstmt.setString(3, bean.getReceiver());
            pstmt.setString(4, bean.getMobile());
            pstmt.setString(5, bean.getUserMessage());
            pstmt.setTimestamp(6, DateUtil.date2time(bean.getCreateDate()));
            pstmt.setTimestamp(7, DateUtil.date2time(bean.getPayDate()));
            pstmt.setTimestamp(8, DateUtil.date2time(bean.getDeliveryDate()));
            pstmt.setTimestamp(9, DateUtil.date2time(bean.getConfirmDate()));
            pstmt.setString(10, bean.getOrderCode());
            pstmt.setInt(11, bean.getUser().getId());
            pstmt.setString(12, bean.getStatus());
            pstmt.setInt(13, bean.getId());
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
            String sql = "delete from order_ where id = " + id;
            stmt.execute(sql);
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Order getOrderById(int id) {
        Order bean = null;

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {

            String sql = "select * from order_ where id = " + id;
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                String orderCode = rs.getString("orderCode");
                String address = rs.getString("address");
                String post = rs.getString("post");
                String receiver = rs.getString("receiver");
                String mobile = rs.getString("mobile");
                String userMessage = rs.getString("userMessage");
                Date createDate = DateUtil.time2date(rs.getTimestamp("createDate"));
                Date payDate = DateUtil.time2date(rs.getTimestamp("payDate"));
                Date deliveryDate = DateUtil.time2date(rs.getTimestamp("deliveryDate"));
                Date confirmDate = DateUtil.time2date(rs.getTimestamp("confirmDate"));
                int uid = rs.getInt("uid");
                String status = rs.getString("status");

                bean = new Order();
                bean.setId(id);
                bean.setOrderCode(orderCode);
                bean.setAddress(address);
                bean.setPost(post);
                bean.setReceiver(receiver);
                bean.setMobile(mobile);
                bean.setUserMessage(userMessage);
                bean.setCreateDate(createDate);
                bean.setPayDate(payDate);
                bean.setDeliveryDate(deliveryDate);
                bean.setConfirmDate(confirmDate);
                User user = new UserDAO().getUserById(uid);
                bean.setUser(user);
                bean.setStatus(status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public List<Order> list() {
        return list(0, Short.MAX_VALUE);
    }

    public List<Order> list(int start, int count) {
        List<Order> o_list = new ArrayList<Order>();

        String sql = "select * from order_ order by id desc limit ?, ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, start);
            pstmt.setInt(2, count);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Order bean = new Order();
                int id = rs.getInt("id");
                String orderCode = rs.getString("orderCode");
                String address = rs.getString("address");
                String post = rs.getString("post");
                String receiver = rs.getString("receiver");
                String mobile = rs.getString("mobile");
                String userMessage = rs.getString("userMessage");
                Date createDate = DateUtil.time2date(rs.getTimestamp("createDate"));
                Date payDate = DateUtil.time2date(rs.getTimestamp("payDate"));
                Date deliveryDate = DateUtil.time2date(rs.getTimestamp("deliveryDate"));
                Date confirmDate = DateUtil.time2date(rs.getTimestamp("confirmDate"));
                int uid = rs.getInt("uid");
                String status = rs.getString("status");

                bean.setId(id);
                bean.setOrderCode(orderCode);
                bean.setAddress(address);
                bean.setPost(post);
                bean.setReceiver(receiver);
                bean.setMobile(mobile);
                bean.setUserMessage(userMessage);
                bean.setCreateDate(createDate);
                bean.setPayDate(payDate);
                bean.setDeliveryDate(deliveryDate);
                bean.setConfirmDate(confirmDate);
                User user = new UserDAO().getUserById(uid);
                bean.setUser(user);
                bean.setStatus(status);

                o_list.add(bean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return o_list;
    }

    public List<Order> list(int uid, String excludedStatus) {
        return list(uid, excludedStatus, 0, Short.MAX_VALUE);
    }

    /**
     * 查询指定用户的订单(去掉某种订单状态，通常是"delete")
     *
     * @param uid
     * @param excludedStatus
     * @param start
     * @param count
     * @return
     */
    public List<Order> list(int uid, String excludedStatus, int start, int count) {
        List<Order> ou_list = new ArrayList<Order>();

        String sql = "select * from order_ where uid = ? and status != ? order by id desc limit ?, ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, uid);
            pstmt.setString(2, excludedStatus);
            pstmt.setInt(3, start);
            pstmt.setInt(4, count);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Order bean = new Order();
                int id = rs.getInt("id");
                String orderCode = rs.getString("orderCode");
                String address = rs.getString("address");
                String post = rs.getString("post");
                String receiver = rs.getString("receiver");
                String mobile = rs.getString("mobile");
                String userMessage = rs.getString("userMessage");
                String status = rs.getString("status");
                Date createDate = DateUtil.time2date(rs.getTimestamp("createDate"));
                Date payDate = DateUtil.time2date(rs.getTimestamp("payDate"));
                Date deliveryDate = DateUtil.time2date(rs.getTimestamp("deliveryDate"));
                Date confirmDate = DateUtil.time2date(rs.getTimestamp("confirmDate"));

                bean.setId(id);
                bean.setOrderCode(orderCode);
                bean.setAddress(address);
                bean.setPost(post);
                bean.setReceiver(receiver);
                bean.setMobile(mobile);
                bean.setUserMessage(userMessage);
                bean.setCreateDate(createDate);
                bean.setPayDate(payDate);
                bean.setDeliveryDate(deliveryDate);
                bean.setConfirmDate(confirmDate);
                User user = new UserDAO().getUserById(uid);
                bean.setStatus(status);
                bean.setUser(user);

                ou_list.add(bean);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return ou_list;
    }
}
