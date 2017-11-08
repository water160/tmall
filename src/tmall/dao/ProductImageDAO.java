package tmall.dao;

import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductImageDAO {

    public static final String typeSingle = "type_single";
    public static final String typeDetail = "type_detail";

    public int getTotal() {
        int total = 0;
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            String sql = "SELECT count(*) from productimage";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public void add(ProductImage bean) {
        String sql = "INSERT INTO productimage VALUES(null, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bean.getProduct().getId());
            pstmt.setString(2, bean.getType());
            pstmt.execute();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                bean.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(ProductImage bean) {

    }

    public void delete(int id) {

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            String sql = "delete from productimage where id = " + id;
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ProductImage getProductImageById(int id) {
        ProductImage bean = new ProductImage();

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {

            String sql = "select * from ProductImage where id = " + id;
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                int pid = rs.getInt("pid");
                String type = rs.getString("type");
                Product product = new ProductDAO().getProductById(pid);
                bean.setProduct(product);
                bean.setType(type);
                bean.setId(id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public List<ProductImage> list(Product product, String type) {
        return list(product, type, 0, Short.MAX_VALUE);
    }

    public List<ProductImage> list(Product product, String type, int start, int count) {
        List<ProductImage> pi_list = new ArrayList<ProductImage>();

        String sql = "select * from ProductImage where pid = ? and type = ? order by id desc limit ?, ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, product.getId());
            pstmt.setString(2, type);
            pstmt.setInt(3, start);
            pstmt.setInt(4, count);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ProductImage bean = new ProductImage();
                int id = rs.getInt(1);
                bean.setProduct(product);
                bean.setType(type);
                bean.setId(id);
                pi_list.add(bean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pi_list;
    }
}
