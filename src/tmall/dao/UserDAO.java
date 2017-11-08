package tmall.dao;

import tmall.bean.User;
import tmall.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    /**
     * 获取所有用户的数目
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
            String sql = "SELECT count(*) FROM user";
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Can't get Total(UserDAO)");
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
     * 增加一个User对象
     *
     * @param user
     */
    public void add(User user) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "INSERT INTO user values(null, ?, ?)";
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getPassword());
            pstmt.execute();
            conn.commit();
            conn.setAutoCommit(true);

            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                user.setId(id);
            }

        } catch (SQLException e) {
            System.out.println("Can't add(UserDAO)");
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
     * 修改一个User对象
     *
     * @param user
     */
    public void update(User user) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "UPDATE user SET name = ?, password = ? WHERE id = ?";
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getPassword());
            pstmt.setInt(3, user.getId());
            pstmt.execute();
            conn.commit();
            conn.setAutoCommit(true);

        } catch (SQLException e) {
            System.out.println("Can't update(UserDAO)");
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
     * 根据id删除一个User对象
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
            String sql = "DELETE FROM user WHERE id = " + id;
            stmt.execute(sql);
            conn.commit();
            conn.setAutoCommit(true);

        } catch (SQLException e) {
            System.out.println("Can't delete(UserDAO)");
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
     * 根据id获取一个User对象
     *
     * @param id
     * @return User user
     */
    public User get(int id) {
        User user = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM user WHERE id = " + id;
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                user = new User();
                String name = rs.getString("name");
                String password = rs.getString("password");
                user.setName(name);
                user.setPassword(password);
                user.setId(id);
            }
        } catch (SQLException e) {
            System.out.println("Can't get by id(UserDAO)");
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
        return user;
    }

    /**
     * 分页查询
     *
     * @param start
     * @param count
     * @return ArrayList<User> user_list
     */
    public List<User> list(int start, int count) {
        List<User> user_list = new ArrayList<User>();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM user order by id desc limit ?, ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, start);
            pstmt.setInt(2, count);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                User user = new User();
                int id = rs.getInt(1);
                String name = rs.getString("name");
                String password = rs.getString("password");
                user.setId(id);
                user.setName(name);
                user.setPassword(password);
                user_list.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Can't get list by (start, count)(UserDAO)");
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
        return user_list;
    }

    /**
     * 查询所有的类别
     *
     * @return ArrayList<User> user_list
     */
    public List<User> list() {
        return list(0, Short.MAX_VALUE);
    }

    /**
     * 判断用户账户是否已存在
     *
     * @param name
     * @return true if exists
     */
    public boolean isExist(String name) {
        User user = getUserByName(name);
        return user != null;
    }

    /**
     * 根据name查询用户是否已存在
     *
     * @param name
     * @return User user
     */
    public User getUserByName(String name) {
        User user = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM user WHERE name = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = new User();
                int id = rs.getInt("id");
                user.setName(name);
                String password = rs.getString("password");
                user.setPassword(password);
                user.setId(id);
            }
        } catch (SQLException e) {
            System.out.println("Can't get user by name(UserDAO)");
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
        return user;
    }

    /**
     * 判断用户账号和密码是否一致
     *
     * @param name
     * @param password
     * @return User user
     */
    public User userLogin(String name, String password) {
        User user = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM user WHERE name = ? AND password = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = new User();
                int id = rs.getInt("id");
                user.setName(name);
                user.setPassword(password);
                user.setId(id);
            }
        } catch (SQLException e) {
            System.out.println("user Can't login(UserDAO)");
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
        return user;
    }
}
