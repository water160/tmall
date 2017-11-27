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
        try(Connection conn = DBUtil.getConnection(); Statement stmt = conn.createStatement();) {
            String sql = "select count(*) from user";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Can't get Total(UserDAO)");
            e.printStackTrace();
        }
        return total;
    }

    /**
     * 增加一个User对象
     *
     * @param user
     */
    public void add(User user) {
        String sql = "insert into user values(null ,? ,?)";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
            conn.setAutoCommit(false);
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getPassword());
            pstmt.execute();
            conn.commit();
            conn.setAutoCommit(true);

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                user.setId(id);
            }
        } catch (SQLException e) {
            System.out.println("Can't add User(UserDAO)");
            e.printStackTrace();
        }
    }

    /**
     * 修改一个User对象
     *
     * @param user
     */
    public void update(User user) {
        String sql = "update user set name= ? , password = ? where id = ? ";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
            conn.setAutoCommit(false);
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getPassword());
            pstmt.setInt(3, user.getId());
            pstmt.execute();
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("Can't update User(UserDAO)");
            e.printStackTrace();
        }
    }

    /**
     * 根据id删除一个User对象
     *
     * @param id
     */
    public void delete(int id) {
        try (Connection conn = DBUtil.getConnection(); Statement stmt = conn.createStatement();) {
            String sql = "delete from user where id = " + id;
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Can't delete User(UserDAO)");
            e.printStackTrace();
        }
    }

    /**
     * 根据id获取一个User对象
     *
     * @param id
     * @return User user
     */
    public User getUserById(int id) {
        User user = null;
        try (Connection conn = DBUtil.getConnection(); Statement stmt = conn.createStatement();) {
            String sql = "select * from user where id = " + id;
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                user = new User();
                String name = rs.getString("name");
                user.setName(name);
                String password = rs.getString("password");
                user.setPassword(password);
                user.setId(id);
            }
        } catch (SQLException e) {
            System.out.println("Can't get User By ID(UserDAO)");
            e.printStackTrace();
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

        String sql = "select * from user order by id desc limit ?,? ";

        try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, start);
            pstmt.setInt(2, count);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                User bean = new User();
                int id = rs.getInt(1);

                String name = rs.getString("name");
                bean.setName(name);
                String password = rs.getString("password");
                bean.setPassword(password);

                bean.setId(id);
                user_list.add(bean);
            }
        } catch (SQLException e) {
            System.out.println("Can't list User start and count(UserDAO)");
            e.printStackTrace();
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

        String sql = "select * from user where name = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User();
                int id = rs.getInt("id");
                user.setName(name);
                String password = rs.getString("password");
                user.setPassword(password);
                user.setId(id);
            }

        } catch (SQLException e) {
            System.out.println("Can't get User By name(UserDAO)");
            e.printStackTrace();
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
        String sql = "select * from user where name = ? and password = ?";

        try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User();
                int id = rs.getInt("id");
                user.setName(name);
                user.setPassword(password);
                user.setId(id);
            }

        } catch (SQLException e) {
            System.out.println("Can't check userLogin(UserDAO)");
            e.printStackTrace();
        }
        return user;
    }
}
