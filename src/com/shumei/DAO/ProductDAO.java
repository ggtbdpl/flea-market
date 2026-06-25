package com.shumei.DAO;

import com.shumei.pojo.Product;

import java.util.ArrayList;

public interface ProductDAO {
    ArrayList<Product> getProductList();
    ArrayList<Product> getAllProducts();
    Product getProductById(int id);
    ArrayList<Product> getProductByKeyword(String keyword);
    boolean addProduct(Product product);
    boolean updateProduct(Product product);
    boolean delProduct(int id);
    int updateStatus(Integer id, Integer status);
}
