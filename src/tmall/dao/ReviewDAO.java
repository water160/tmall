package tmall.dao;

import tmall.bean.Product;
import tmall.bean.Review;
import tmall.bean.User;
import tmall.util.DBUtil;
import tmall.util.DateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReviewDAO {

    public int getTotal() {
        int total = 0;
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            String sql = "select count(*) from review";

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    /**
     * 根据产品id获取该产品的所有评论数
     *
     * @param pid
     * @return
     */
    public int getTotal(int pid) {
        int total = 0;
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {

            String sql = "select count(*) from review where pid = " + pid;

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public void add(Review bean) {
        String sql = "insert into review values(null,?,?,?,?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            pstmt.setString(1, bean.getContent());
            pstmt.setInt(2, bean.getUser().getId());
            pstmt.setInt(3, bean.getProduct().getId());
            pstmt.setTimestamp(4, DateUtil.date2time((Date) bean.getCreateDate()));
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

    public void update(Review bean) {
        String sql = "update review set content = ?, uid = ?, pid = ? , createDate = ? where id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            pstmt.setString(1, bean.getContent());
            pstmt.setInt(2, bean.getUser().getId());
            pstmt.setInt(3, bean.getProduct().getId());
            pstmt.setTimestamp(4, DateUtil.date2time((Date) bean.getCreateDate()));
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
            String sql = "delete from review where id = " + id;
            stmt.execute(sql);
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Review getReviewById(int id) {
        Review bean = null;

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {

            String sql = "select * from review where id = " + id;
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                int pid = rs.getInt("pid");
                int uid = rs.getInt("uid");
                String content = rs.getString("content");
                Product product = new ProductDAO().getProductById(pid);
                User user = new UserDAO().getUserById(uid);
                Date createDate = DateUtil.time2date(rs.getTimestamp("createDate"));

                bean = new Review();
                bean.setId(id);
                bean.setContent(content);
                bean.setUser(user);
                bean.setProduct(product);
                bean.setCreateDate(createDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public List<Review> list(int pid) {
        return list(pid, 0, Short.MAX_VALUE);
    }

    public List<Review> list(int pid, int start, int count) {
        List<Review> r_list = new ArrayList<Review>();

        String sql = "select * from review where pid = ? order by id desc limit ?, ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, pid);
            pstmt.setInt(2, start);
            pstmt.setInt(3, count);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Review bean = new Review();
                int id = rs.getInt(1);
                int uid = rs.getInt("uid");
                Date createDate = DateUtil.time2date(rs.getTimestamp("createDate"));
                String content = rs.getString("content");
                Product product = new ProductDAO().getProductById(pid);
                User user = new UserDAO().getUserById(uid);

                bean.setContent(content);
                bean.setCreateDate(createDate);
                bean.setId(id);
                bean.setProduct(product);
                bean.setUser(user);

                r_list.add(bean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return r_list;
    }

    /**
     * 防止网络延迟导致多次提交
     *
     * @param content
     * @param pid
     * @return
     */
    public boolean isExist(String content, int pid) {

        String sql = "select * from review where content = ? and pid = ?";

        try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, content);
            pstmt.setInt(2, pid);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
