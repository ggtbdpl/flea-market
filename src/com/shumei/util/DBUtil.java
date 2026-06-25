package com.shumei.util;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DBUtil {
    private static String url = "jdbc:mysql://127.0.0.1:3306/fleamarket?useSSL=false&useUnicode=true&characterEncoding=utf8";
    private static String user="root";
    private static String password="Jxl5201314..";
    private static String driver="com.mysql.jdbc.Driver";  // ← 改这里，去掉 .cj

    static {
        try {
            // 读取 db.properties 配置文件
            InputStream is = DBUtil.class.getClassLoader().getResourceAsStream("db.properties");
            Properties props = new Properties();
            props.load(is);

            url= props.getProperty("url");
            user = props.getProperty("username");
            password = props.getProperty("password");
            driver = props.getProperty("driver");

            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public static void close(Connection conn, Statement ps, ResultSet rs) {
        if (rs != null) {
            try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        if (ps != null) {
            try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        if (conn != null) {
            try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}
