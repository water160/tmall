package tmall.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库工具类，提供一个getConnection用于DAO层获取数据库连接，包含数据库url，编码，用户名，密码
 */
public class DBUtil {

    public static String ip = "127.0.0.1";
    public static int port = 3306;
    public static String database = "tmall";
    public static String encoding = "UTF-8";
    public static String loginName = "root";
    public static String password = "123456";

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Can't com.mysql.jdbc.Driver");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        String url = String.format("jdbc:mysql://%s:%d/%s?characterEncoding=%s", ip, port, database, encoding);
        try {
            return DriverManager.getConnection(url, loginName, password);
        } catch (SQLException e) {
            System.out.println("Can't get Connection");
            e.printStackTrace();
        }
        return null;
    }

//    public static void main(String[] args) {
//        System.out.println(getConnection());
//    }

}
