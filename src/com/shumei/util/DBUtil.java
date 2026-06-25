package com.shumei.util;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DBUtil {
    private static String url = "jdbc:mysql://127.0.0.1:3306/fleamarket?useSSL=false&useUnicode=true&characterEncoding=utf8";
    private static String user = "root";
    private static String password = "Jxl5201314..";
    private static String driver = "com.mysql.jdbc.Driver";

    static {
        try {
            InputStream is = DBUtil.class.getClassLoader().getResourceAsStream("db.properties");
            if (is != null) {
                Properties props = new Properties();
                props.load(is);
                String cfgUrl = props.getProperty("url");
                String cfgUser = props.getProperty("username");
                String cfgPwd = props.getProperty("password");
                String cfgDriver = props.getProperty("driver");
                if (cfgUrl != null) url = cfgUrl;
                if (cfgUser != null) user = cfgUser;
                if (cfgPwd != null) password = cfgPwd;
                if (cfgDriver != null) driver = cfgDriver;
            }
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
