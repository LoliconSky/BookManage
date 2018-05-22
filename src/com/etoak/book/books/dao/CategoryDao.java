package com.etoak.book.books.dao;

import com.etoak.book.books.po.Category;

import java.util.List;

/**
 * Created by 冰封承諾Andy on 2018/5/19.
 */
public interface CategoryDao {
    String addCategory(Category category);

    List<Category> getAll();

    Category getCategoryById(String id);

    Category getCategoryByName(String name);
}
