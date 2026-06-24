package com.shumei.DAO.Impl;

import com.shumei.DAO.ProductDAO;
import com.shumei.pojo.Product;
import com.shumei.util.DBUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDAOImpl implements ProductDAO {

    @Override
    public ArrayList<Product> getProductList() {
        ArrayList<Product> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT id, title, category_id, price, original_price, `condition`, description, image, images, contact, user_id, status, create_time, update_time FROM product WHERE status = 1";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setTitle(rs.getString("title"));
                p.setCategoryId(rs.getInt("category_id"));
                p.setPrice(rs.getBigDecimal("price"));
                p.setOriginalPrice(rs.getBigDecimal("original_price"));
                p.setCondition(rs.getString("condition"));
                p.setDescription(rs.getString("description"));
                p.setImage(rs.getString("image"));
                p.setImages(rs.getString("images"));
                p.setContact(rs.getString("contact"));
                p.setUserId(rs.getInt("user_id"));
                p.setStatus(rs.getInt("status"));
                p.setCreateTime(rs.getString("create_time"));
                p.setUpdateTime(rs.getString("update_time"));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return list;
    }

    @Override
    public Product getProductById(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Product p = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT id, title, category_id, price, original_price, `condition`, description, image, images, contact, user_id, status, create_time, update_time FROM product WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                p = new Product();
                p.setId(rs.getInt("id"));
                p.setTitle(rs.getString("title"));
                p.setCategoryId(rs.getInt("category_id"));
                p.setPrice(rs.getBigDecimal("price"));
                p.setOriginalPrice(rs.getBigDecimal("original_price"));
                p.setCondition(rs.getString("condition"));
                p.setDescription(rs.getString("description"));
                p.setImage(rs.getString("image"));
                p.setImages(rs.getString("images"));
                p.setContact(rs.getString("contact"));
                p.setUserId(rs.getInt("user_id"));
                p.setStatus(rs.getInt("status"));
                p.setCreateTime(rs.getString("create_time"));
                p.setUpdateTime(rs.getString("update_time"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return p;
    }

    @Override
    public ArrayList<Product> getProductByKeyword(String keyword) {
        ArrayList<Product> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT id, title, category_id, price, original_price, `condition`, description, image, images, contact, user_id, status, create_time, update_time FROM product WHERE title LIKE ? AND status = 1";
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setTitle(rs.getString("title"));
                p.setCategoryId(rs.getInt("category_id"));
                p.setPrice(rs.getBigDecimal("price"));
                p.setOriginalPrice(rs.getBigDecimal("original_price"));
                p.setCondition(rs.getString("condition"));
                p.setDescription(rs.getString("description"));
                p.setImage(rs.getString("image"));
                p.setImages(rs.getString("images"));
                p.setContact(rs.getString("contact"));
                p.setUserId(rs.getInt("user_id"));
                p.setStatus(rs.getInt("status"));
                p.setCreateTime(rs.getString("create_time"));
                p.setUpdateTime(rs.getString("update_time"));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return list;
    }

    @Override
    public boolean addProduct(Product product) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO product (title, category_id, price, original_price, `condition`, description, image, images, contact, user_id, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, product.getTitle());
            ps.setInt(2, product.getCategoryId() != null ? product.getCategoryId() : 0);
            ps.setBigDecimal(3, product.getPrice());
            ps.setBigDecimal(4, product.getOriginalPrice());
            ps.setString(5, product.getCondition());
            ps.setString(6, product.getDescription());
            ps.setString(7, product.getImage());
            ps.setString(8, product.getImages());
            ps.setString(9, product.getContact());
            ps.setInt(10, product.getUserId() != null ? product.getUserId() : 0);
            ps.setInt(11, product.getStatus() != null ? product.getStatus() : 0);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, ps, null);
        }
    }

    @Override
    public boolean updateProduct(Product product) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE product SET title = ?, category_id = ?, price = ?, original_price = ?, `condition` = ?, description = ?, image = ?, images = ?, contact = ?, user_id = ?, status = ? WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, product.getTitle());
            ps.setInt(2, product.getCategoryId() != null ? product.getCategoryId() : 0);
            ps.setBigDecimal(3, product.getPrice());
            ps.setBigDecimal(4, product.getOriginalPrice());
            ps.setString(5, product.getCondition());
            ps.setString(6, product.getDescription());
            ps.setString(7, product.getImage());
            ps.setString(8, product.getImages());
            ps.setString(9, product.getContact());
            ps.setInt(10, product.getUserId() != null ? product.getUserId() : 0);
            ps.setInt(11, product.getStatus() != null ? product.getStatus() : 0);
            ps.setInt(12, product.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, ps, null);
        }
    }

    @Override
    public boolean delProduct(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "DELETE FROM product WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, ps, null);
        }
    }
}
