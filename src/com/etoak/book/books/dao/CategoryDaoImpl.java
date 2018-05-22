package com.etoak.book.books.dao;

import com.etoak.book.books.po.Category;
import com.etoak.book.commons.factory.CF;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by 冰封承諾Andy on 2018/5/19.
 */
public class CategoryDaoImpl implements CategoryDao {
    private QueryRunner qr = new QueryRunner(CF.getDataSource());

    @Override
    public String addCategory(Category category) {
        String sql = "insert into category(id,name) values (?,?)";
        String getId = "select replace(uuid(),'-','') id";
        try {
            Map<String, Object> map = qr.query(getId, new MapHandler());
            category.setId(map.get("id").toString());
            qr.update(sql, category.getId(), category.getName());
            return category.getId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Category> getAll() {
        String sql = "select * from category";
        try {
            return qr.query(sql, new BeanListHandler<>(Category.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Category getCategoryById(String id) {
        String sql = "select * from category where id=?";
        try {
            return qr.query(sql, new BeanHandler<>(Category.class),id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Category getCategoryByName(String name) {
        String sql = "select * from category where name=?";
        try {
            return qr.query(sql, new BeanHandler<>(Category.class),name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
