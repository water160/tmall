package tmall.dao;

import tmall.bean.Category;
import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.util.DBUtil;
import tmall.util.DateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public int getTotal(int cid) {
        int total = 0;
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            String sql = "select count(*) from product where cid = " + cid;
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public void add(Product bean) {

        String sql = "insert into product values(null, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            pstmt.setString(1, bean.getName());
            pstmt.setString(2, bean.getSubTitle());
            pstmt.setFloat(3, bean.getOriginalPrice());
            pstmt.setFloat(4, bean.getPromotePrice());
            pstmt.setInt(5, bean.getStock());
            pstmt.setInt(6, bean.getCategory().getId());
            pstmt.setTimestamp(7, DateUtil.date2time((Date) bean.getCreateDate()));
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

    public void update(Product bean) {

        String sql = "update product set name = ?, subTitle = ?, originalPrice = ?, " +
                "promotePrice = ?, stock = ?, cid = ?, createDate = ? where id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            pstmt.setString(1, bean.getName());
            pstmt.setString(2, bean.getSubTitle());
            pstmt.setFloat(3, bean.getOriginalPrice());
            pstmt.setFloat(4, bean.getPromotePrice());
            pstmt.setInt(5, bean.getStock());
            pstmt.setInt(6, bean.getCategory().getId());
            pstmt.setTimestamp(7, DateUtil.date2time((Date) bean.getCreateDate()));
            pstmt.setInt(8, bean.getId());
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
            String sql = "delete from product where id = " + id;
            stmt.execute(sql);
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Product getProductById(int id) {
        Product bean = null;

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            String sql = "select * from product where id = " + id;
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                String name = rs.getString("name");
                String subTitle = rs.getString("subTitle");
                float originalPrice = rs.getFloat("originalPrice");
                float promotePrice = rs.getFloat("promotePrice");
                int stock = rs.getInt("stock");
                int cid = rs.getInt("cid");
                Date createDate = DateUtil.time2date(rs.getTimestamp("createDate"));

                bean = new Product();
                bean.setName(name);
                bean.setSubTitle(subTitle);
                bean.setOriginalPrice(originalPrice);
                bean.setPromotePrice(promotePrice);
                bean.setStock(stock);
                Category category = new CategoryDAO().getCategoryById(cid);
                bean.setCategory(category);
                bean.setCreateDate(createDate);
                bean.setId(id);

                setMainProductImage(bean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
    }

    /**
     * 获取某类别下所有的产品
     *
     * @param cid
     * @return
     */
    public List<Product> list(int cid) {
        return list(cid, 0, Short.MAX_VALUE);
    }

    public List<Product> list(int cid, int start, int count) {
        List<Product> p_list_cid = new ArrayList<Product>();
        Category category = new CategoryDAO().getCategoryById(cid);
        String sql = "select * from product where cid = ? order by id desc limit ?, ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, cid);
            pstmt.setInt(2, start);
            pstmt.setInt(3, count);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Product bean = new Product();
                int id = rs.getInt(1);
                String name = rs.getString("name");
                String subTitle = rs.getString("subTitle");
                float originalPrice = rs.getFloat("originalPrice");
                float promotePrice = rs.getFloat("promotePrice");
                int stock = rs.getInt("stock");
                Date createDate = DateUtil.time2date(rs.getTimestamp("createDate"));

                bean.setName(name);
                bean.setSubTitle(subTitle);
                bean.setOriginalPrice(originalPrice);
                bean.setPromotePrice(promotePrice);
                bean.setStock(stock);
                bean.setCreateDate(createDate);
                bean.setId(id);
                bean.setCategory(category);

                setMainProductImage(bean);
                p_list_cid.add(bean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p_list_cid;
    }

    public List<Product> list() {
        return list(0, Short.MAX_VALUE);
    }

    public List<Product> list(int start, int count) {
        List<Product> p_list = new ArrayList<Product>();

        String sql = "select * from product limit ?, ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, start);
            pstmt.setInt(2, count);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Product bean = new Product();
                int id = rs.getInt(1);
                int cid = rs.getInt("cid");
                String name = rs.getString("name");
                String subTitle = rs.getString("subTitle");
                float originalPrice = rs.getFloat("originalPrice");
                float promotePrice = rs.getFloat("promotePrice");
                int stock = rs.getInt("stock");
                Date createDate = DateUtil.time2date(rs.getTimestamp("createDate"));

                bean.setName(name);
                bean.setSubTitle(subTitle);
                bean.setOriginalPrice(originalPrice);
                bean.setPromotePrice(promotePrice);
                bean.setStock(stock);
                bean.setCreateDate(createDate);
                bean.setId(id);

                Category category = new CategoryDAO().getCategoryById(cid);
                bean.setCategory(category);
                p_list.add(bean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p_list;
    }

    /**
     * 为所有分类填充产品集合
     *
     * @param categoryList
     */
    public void fillAll(List<Category> categoryList) {
        for (Category category : categoryList)
            fill(category);
    }

    /**
     * 为某个分类填充产品集合
     *
     * @param category
     */
    public void fill(Category category) {
        List<Product> ps = this.list(category.getId());
        category.setProductList(ps);
    }

    /**
     * 便于首页显示，针对每个类别都加入一个List<List<Product>>，用于首页展示
     *
     * @param categoryList
     */
    public void fillByRow(List<Category> categoryList) {
        int productNumberEachRow = 8;
        for (Category category : categoryList) {
            List<Product> products = category.getProductList();
            List<List<Product>> productsByRow = new ArrayList<>();

            for (int i = 0; i < products.size(); i += productNumberEachRow) {
                int size = i + productNumberEachRow;
                size = size > products.size() ? products.size() : size;
                List<Product> productsOfEachRow = products.subList(i, size);
                productsByRow.add(productsOfEachRow);
            }
            category.setProductListByRow(productsByRow);
        }
    }

    /**
     * 一个产品有多个图片，但是只有一个主图片，把第一个图片设置为主图片
     *
     * @param product
     */
    public void setMainProductImage(Product product) {
        List<ProductImage> pis = new ProductImageDAO().list(product, ProductImageDAO.typeSingle);
        if (!pis.isEmpty())
            product.setFirstProductImage(pis.get(0));
    }

    /**
     * 为某个产品设置销量和评价数
     *
     * @param product
     */
    public void setSaleAndReviewNumber(Product product) {
        int saleCount = new OrderItemDAO().getSaleCount(product.getId());
        product.setSaleCount(saleCount);

        int reviewCount = new ReviewDAO().getTotal(product.getId());
        product.setReviewCount(reviewCount);

    }

    /**
     * 为一些产品设置销量和评价数
     *
     * @param products
     */
    public void setSaleAndReviewNumber(List<Product> products) {
        for (Product p : products) {
            setSaleAndReviewNumber(p);
        }
    }

    public List<Product> search(String keyword, int start, int count) {
        List<Product> beans = new ArrayList<Product>();

        if (null == keyword || 0 == keyword.trim().length())
            return beans;
        String sql = "select * from product where name like ? limit ?, ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + keyword.trim() + "%");
            pstmt.setInt(2, start);
            pstmt.setInt(3, count);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Product bean = new Product();
                int id = rs.getInt(1);
                int cid = rs.getInt("cid");
                String name = rs.getString("name");
                String subTitle = rs.getString("subTitle");
                float originalPrice = rs.getFloat("originalPrice");
                float promotePrice = rs.getFloat("promotePrice");
                int stock = rs.getInt("stock");
                Date createDate = DateUtil.time2date(rs.getTimestamp("createDate"));

                bean.setName(name);
                bean.setSubTitle(subTitle);
                bean.setOriginalPrice(originalPrice);
                bean.setPromotePrice(promotePrice);
                bean.setStock(stock);
                bean.setCreateDate(createDate);
                bean.setId(id);

                Category category = new CategoryDAO().getCategoryById(cid);
                bean.setCategory(category);
                setMainProductImage(bean);
                beans.add(bean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return beans;
    }

}
