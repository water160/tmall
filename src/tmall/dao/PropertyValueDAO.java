package tmall.dao;

import tmall.bean.Product;
import tmall.bean.Property;
import tmall.bean.PropertyValue;
import tmall.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PropertyValueDAO {

    public int getTotal() {
        int total = 0;

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            String sql = "select count(*) from PropertyValue";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public void add(PropertyValue bean) {

        String sql = "insert into propertyvalue values(null, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            pstmt.setInt(1, bean.getProduct().getId());
            pstmt.setInt(2, bean.getProperty().getId());
            pstmt.setString(3, bean.getValue());
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

    public void update(PropertyValue bean) {
        String sql = "update propertyvalue set pid= ?, ptid= ?, value= ? where id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            pstmt.setInt(1, bean.getProduct().getId());
            pstmt.setInt(2, bean.getProperty().getId());
            pstmt.setString(3, bean.getValue());
            pstmt.setInt(4, bean.getId());
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
            String sql = "delete from propertyvalue where id = " + id;
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PropertyValue getPropertyValueById(int id) {
        PropertyValue bean = null;

        try (Connection conn = DBUtil.getConnection(); Statement stmt = conn.createStatement()) {
            String sql = "select * from propertyvalue where id = " + id;
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                bean = new PropertyValue();
                int pid = rs.getInt("pid");
                int ptid = rs.getInt("ptid");
                String value = rs.getString("value");
                Product product = new ProductDAO().getProductById(pid);
                Property property = new PropertyDAO().getPropertyById(ptid);

                bean.setProduct(product);
                bean.setProperty(property);
                bean.setValue(value);
                bean.setId(id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
    }

    /**
     * 用于查找某产品某属性的具体值，属性与属性值是1对多的关系
     *
     * @param ptid
     * @param pid
     * @return
     */
    public PropertyValue getPropertyValueByBothId(int pid, int ptid) {
        PropertyValue bean = null;

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            String sql = "select * from propertyvalue where pid = " + pid + " and ptid = " + ptid;
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                bean = new PropertyValue();
                int id = rs.getInt("id");
                String value = rs.getString("value");
                Product product = new ProductDAO().getProductById(pid);
                Property property = new PropertyDAO().getPropertyById(ptid);
                bean.setId(id);
                bean.setProduct(product);
                bean.setProperty(property);
                bean.setValue(value);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public List<PropertyValue> listAllPropertyValue() {
        return listPropertyValue(0, Short.MAX_VALUE);
    }

    public List<PropertyValue> listPropertyValue(int start, int count) {
        List<PropertyValue> pv_list = new ArrayList<PropertyValue>();

        String sql = "select * from propertyvalue order by id desc limit ?, ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, start);
            pstmt.setInt(2, count);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                PropertyValue bean = new PropertyValue();
                int id = rs.getInt(1);
                int pid = rs.getInt("pid");
                int ptid = rs.getInt("ptid");
                String value = rs.getString("value");
                Product product = new ProductDAO().getProductById(pid);
                Property property = new PropertyDAO().getPropertyById(ptid);

                bean.setId(id);
                bean.setProduct(product);
                bean.setProperty(property);
                bean.setValue(value);

                pv_list.add(bean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pv_list;
    }

    /**
     * 初始化某产品所有的属性值，根据分类获取所有属性，遍历每个属性值，根据产品id和属性id获取属性值，若属性值为空，就创建一个属性值对象
     *
     * @param product
     */
    public void init(Product product) {
        //找到该产品所属类别下的所有属性
        List<Property> pt_list = new PropertyDAO().listAllProperty(product.getCategory().getId());

        for (Property property : pt_list) {
            //根据该产品id和属性值id查询到唯一的属性值，若属性值为空说明未设置也需要在前台展示，因此也要添加到PropertyValue对象中维系
            PropertyValue pv = getPropertyValueByBothId(product.getId(), property.getId());
            if (pv == null) {
                pv = new PropertyValue();
                pv.setProduct(product);
                pv.setProperty(property);
                this.add(pv);
            }
        }
    }

    /**
     * 查找某产品的所有属性值
     *
     * @param pid
     * @return
     */
    public List<PropertyValue> listPropertyValueByPid(int pid) {
        List<PropertyValue> pv_list = new ArrayList<PropertyValue>();

        String sql = "select * from propertyvalue where pid = ? order by ptid desc";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, pid);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                PropertyValue bean = new PropertyValue();
                int id = rs.getInt(1);
                int ptid = rs.getInt("ptid");
                String value = rs.getString("value");
                Product product = new ProductDAO().getProductById(pid);
                Property property = new PropertyDAO().getPropertyById(ptid);

                bean.setProduct(product);
                bean.setProperty(property);
                bean.setValue(value);
                bean.setId(id);

                pv_list.add(bean);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return pv_list;
    }
}
