package com.shumei.DAO;

import com.shumei.pojo.Product;

import java.util.ArrayList;

public interface ProductDAO {
    ArrayList<Product> getProductList();
    ArrayList<Product> getAllProducts();
    Product getProductById(int id);
    ArrayList<Product> getProductByKeyword(String keyword);
    ArrayList<Product> getProductByKeywordAndTag(String keyword, String tag, String sort);
    ArrayList<String> getAllTags();
    boolean addProduct(Product product);
    boolean updateProduct(Product product);
    int updateStatus(Integer id, Integer status);
    boolean delProduct(int id);
    ArrayList<Product> getProductByFilter(String keyword, Integer categoryId, String condition, String sort);

    ArrayList<String> getAllConditions();
}
