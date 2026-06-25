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
            String sql = "SELECT id, title, category_id, price, original_price, `condition`, description, image, images, contact, user_id, status, tags, create_time, update_time FROM product WHERE status = 1";
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
                p.setTags(rs.getString("tags"));
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
    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT id, title, category_id, price, original_price, `condition`, description, image, images, contact, user_id, status, tags, create_time, update_time FROM product";
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
                p.setTags(rs.getString("tags"));
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
            String sql = "SELECT id, title, category_id, price, original_price, `condition`, description, image, images, contact, user_id, status, tags, create_time, update_time FROM product WHERE id = ?";
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
                p.setTags(rs.getString("tags"));
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
            String sql = "SELECT id, title, category_id, price, original_price, `condition`, description, image, images, contact, user_id, status, tags, create_time, update_time FROM product WHERE title LIKE ? AND status = 1";
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
                p.setTags(rs.getString("tags"));
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
    public ArrayList<Product> getProductByKeywordAndTag(String keyword, String tag, String sort) {
        ArrayList<Product> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            StringBuilder sql = new StringBuilder(
                    "SELECT id, title, category_id, price, original_price, `condition`, description, image, images, contact, user_id, status, tags, create_time, update_time FROM product WHERE status = 1"
            );
            if (keyword != null && !keyword.trim().isEmpty()) {
                sql.append(" AND title LIKE ?");
            }
            if (tag != null && !tag.trim().isEmpty()) {
                sql.append(" AND tags LIKE ?");
            }
            if ("price_asc".equals(sort)) {
                sql.append(" ORDER BY price ASC");
            } else if ("price_desc".equals(sort)) {
                sql.append(" ORDER BY price DESC");
            } else if ("newest".equals(sort)) {
                sql.append(" ORDER BY create_time DESC");
            } else {
                sql.append(" ORDER BY id DESC");
            }
            ps = conn.prepareStatement(sql.toString());
            int idx = 1;
            if (keyword != null && !keyword.trim().isEmpty()) {
                ps.setString(idx++, "%" + keyword.trim() + "%");
            }
            if (tag != null && !tag.trim().isEmpty()) {
                ps.setString(idx++, "%" + tag.trim() + "%");
            }
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
                p.setTags(rs.getString("tags"));
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
    public ArrayList<Product> getProductByFilter(String keyword, Integer categoryId, String condition, String sort) {
        ArrayList<Product> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            StringBuilder sql = new StringBuilder(
                    "SELECT id, title, category_id, price, original_price, `condition`, description, image, images, contact, user_id, status, tags, create_time, update_time FROM product WHERE status = 1"
            );
            if (keyword != null && !keyword.trim().isEmpty()) {
                sql.append(" AND title LIKE ?");
            }
            if (categoryId != null && categoryId > 0) {
                sql.append(" AND category_id = ?");
            }
            if (condition != null && !condition.trim().isEmpty()) {
                sql.append(" AND `condition` = ?");
            }
            if ("price_asc".equals(sort)) {
                sql.append(" ORDER BY price ASC");
            } else if ("price_desc".equals(sort)) {
                sql.append(" ORDER BY price DESC");
            } else if ("newest".equals(sort)) {
                sql.append(" ORDER BY create_time DESC");
            } else {
                sql.append(" ORDER BY id DESC");
            }
            ps = conn.prepareStatement(sql.toString());
            int idx = 1;
            if (keyword != null && !keyword.trim().isEmpty()) {
                ps.setString(idx++, "%" + keyword.trim() + "%");
            }
            if (categoryId != null && categoryId > 0) {
                ps.setInt(idx++, categoryId);
            }
            if (condition != null && !condition.trim().isEmpty()) {
                ps.setString(idx++, condition.trim());
            }
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
                p.setTags(rs.getString("tags"));
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
    public ArrayList<String> getAllTags() {
        ArrayList<String> allTags = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT tags FROM product WHERE status = 1 AND tags IS NOT NULL AND tags != ''";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String tags = rs.getString("tags");
                if (tags != null && !tags.isEmpty()) {
                    String[] split = tags.split("[,，]");
                    for (String t : split) {
                        String trim = t.trim();
                        if (!trim.isEmpty() && !allTags.contains(trim)) {
                            allTags.add(trim);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return allTags;
    }

    @Override
    public ArrayList<String> getAllConditions() {
        ArrayList<String> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT DISTINCT `condition` FROM product WHERE status = 1 AND `condition` IS NOT NULL AND `condition` != ''";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String c = rs.getString("condition");
                if (c != null && !c.trim().isEmpty()) {
                    list.add(c.trim());
                }
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
            String sql = "INSERT INTO product (title, category_id, price, original_price, `condition`, description, image, images, contact, user_id, status, tags) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            ps.setString(12, product.getTags());
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
            String sql = "UPDATE product SET title = ?, category_id = ?, price = ?, original_price = ?, `condition` = ?, description = ?, image = ?, images = ?, contact = ?, user_id = ?, status = ?, tags = ? WHERE id = ?";
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
            ps.setString(12, product.getTags());
            ps.setInt(13, product.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, ps, null);
        }
    }

    @Override
    public int updateStatus(Integer id, Integer status) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE product SET status = ? WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, status);
            ps.setInt(2, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
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
