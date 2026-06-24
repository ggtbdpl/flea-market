package com.shumei.util;

import java.sql.*;

public class DBUtil {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/fleamarket?useSSL=false&useUnicode=true&characterEncoding=utf8";
    private static final String user="root";
    private static final String password="Jxl5201314..";
    private static final String driver="com.mysql.jdbc.Driver";  // ← 改这里，去掉 .cj

    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
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
