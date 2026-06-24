package com.shumei.DAO;

import com.shumei.pojo.Category;
import java.util.List;

public interface CategoryDAO {
    List<Category> getAll();
    Category getById(Integer id);
    void add(Category category);
    void update(Category category);
    void delete(Integer id);
}
