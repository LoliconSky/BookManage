package com.etoak.book.books.service;

import com.etoak.book.books.dao.CategoryDao;
import com.etoak.book.books.dao.CategoryDaoImpl;
import com.etoak.book.books.po.Category;

import java.util.List;

/**
 * Created by 冰封承諾Andy on 2018/5/19.
 */
public class CategoryService {
    private CategoryDao dao = new CategoryDaoImpl();

    public String addCategory(Category category){
        Category c = dao.getCategoryByName(category.getName());
        if (c == null){
            return dao.addCategory(category);
        }
        // 已经存在
        return null;
    }

    public List<Category> getAllCategory() {
        return dao.getAll();
    }

    public Category getCategoryById(String id) {
        return dao.getCategoryById(id);
    }
}
