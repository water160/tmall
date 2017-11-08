package tmall.dao;

import tmall.bean.Category;
import tmall.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    /**
     * 获取所有类别的数目
     *
     * @return int total
     */
    public int getTotal() {
        int total = 0;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT count(*) FROM category";
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Can't get Total(CategoryDAO)");
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return total;
    }

    /**
     * 增加一个Category对象
     *
     * @param category
     */
    public void add(Category category) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "INSERT INTO category values(null, ?)";
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, category.getName());
            pstmt.execute();
            conn.commit();
            conn.setAutoCommit(true);

            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                category.setId(id);
            }
        } catch (SQLException e) {
            System.out.println("Can't add(CategoryDAO)");
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (pstmt != null) {
                    pstmt.close();
                    pstmt = null;
                }
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 修改一个Category对象
     *
     * @param category
     */
    public void update(Category category) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "UPDATE category SET name = ? WHERE id = ?";
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, category.getName());
            pstmt.setInt(2, category.getId());
            pstmt.execute();
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("Can't update(CategoryDAO)");
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                    pstmt = null;
                }
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据id删除一个Category对象
     *
     * @param id
     */
    public void delete(int id) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            String sql = "DELETE FROM category WHERE id = " + id;
            stmt.execute(sql);
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("Can't delete(CategoryDAO)");
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据id获取一个Category对象
     *
     * @param id
     * @return Category category
     */
    public Category getCategoryById(int id) {
        Category category = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM category WHERE id = " + id;
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                category = new Category();
                String name = rs.getString(2);
                category.setName(name);
                category.setId(id);
            }
        } catch (SQLException e) {
            System.out.println("Can't get by id(CategoryDAO)");
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return category;
    }

    /**
     * 分页查询
     *
     * @param start
     * @param count
     * @return ArrayList<Category> c_list
     */
    public List<Category> list(int start, int count) {
        List<Category> c_list = new ArrayList<Category>();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM category order by id desc limit ?, ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, start);
            pstmt.setInt(2, count);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Category category = new Category();
                int id = rs.getInt(1);
                String name = rs.getString(2);
                category.setId(id);
                category.setName(name);
                c_list.add(category);
            }
        } catch (SQLException e) {
            System.out.println("Can't get list by (start, count)(CategoryDAO)");
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (pstmt != null) {
                    pstmt.close();
                    pstmt = null;
                }
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return c_list;
    }

    /**
     * 查询所有的类别
     *
     * @return ArrayList<Category> c_list
     */
    public List<Category> list() {
        return list(0, Short.MAX_VALUE);
    }

}
