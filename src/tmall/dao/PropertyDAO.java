package tmall.dao;

import tmall.bean.Category;
import tmall.bean.Property;
import tmall.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PropertyDAO {

    public int getTotal(int cid) {
        int total = 0;
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            String sql = "SELECT count(*) from property WHERE cid = " + cid;
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public void add(Property bean) {
        String sql = "INSERT INTO property values(null, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            pstmt.setInt(1, bean.getCategory().getId());
            pstmt.setString(2, bean.getName());
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

    public void update(Property bean) {
        String sql = "UPDATE property SET cid = ?, name = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            pstmt.setInt(1, bean.getCategory().getId());
            pstmt.setString(2, bean.getName());
            pstmt.setInt(3, bean.getId());
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
            String sql = "DELETE FROM property WHERE id = " + id;
            stmt.execute(sql);
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Property getPropertyById(int id) {
        Property bean = null;
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            String sql = "SELECT * FROM property WHERE id = " + id;
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                bean = new Property();
                int cid = rs.getInt("cid");
                String name = rs.getString("name");
                bean.setId(id);
                bean.setName(name);
                Category category = new CategoryDAO().getCategoryById(cid);
                bean.setCategory(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public List<Property> list(int cid) {
        return list(cid, 0, Short.MAX_VALUE);
    }

    public List<Property> list(int cid, int start, int count) {
        List<Property> pt_list = new ArrayList<Property>();
        String sql = "SELECT * FROM property WHERE cid = ? order by id desc limit ?, ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, cid);
            pstmt.setInt(2, start);
            pstmt.setInt(3, count);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Property bean = new Property();
                int id = rs.getInt(1);
                String name = rs.getString("name");
                Category category = new CategoryDAO().getCategoryById(cid);
                bean.setId(id);
                bean.setName(name);
                bean.setCategory(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pt_list;
    }

}
