package com.etoak.book.books.dao;

import com.etoak.book.books.po.Book;
import com.etoak.book.commons.factory.CF;
import com.etoak.book.commons.util.CommonsUtils;
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
public class BookDaoImpl implements BookDao {
    private QueryRunner qr = new QueryRunner(CF.getDataSource());

    @Override
    public String addBook(Book book) {
        String sql = "insert into book(id,name,price,author,publishdate,categoryid,status) values (?,?,?,?,?,?,?)";
        // String sql = "insert into book values (replace(uuid(),'-',''),?,?,?,?,?,?)";
        String id = CommonsUtils.getUUIDTo32();
        try {
            qr.update(sql, id, book.getName(),
                    book.getPrice(), book.getAuthor(), book.getPublishdate(),
                    book.getCategoryid(), book.getStatus());
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Book> getAll() {
        String sql = "select * from book";
        try {
            return qr.query(sql, new BeanListHandler<>(Book.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Book getBookById(String id) {
        String sql = "select * from book where id=?";
        try {
            return qr.query(sql, new BeanHandler<>(Book.class), id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public int removeBookById(String id) {
        String sql = "delete from book where id=?";
        try {
            return qr.update(sql, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int removeBookByName(String name) {
        String sql = "delete from book where name=?";
        try {
            return qr.update(sql, name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Book> getPage(int index, int max) {
        String sql = "select * from book order by publishdate limit ?,?";
        try {
            return qr.query(sql, new BeanListHandler<>(Book.class),index, max);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getCount() {
        String sql = "select count(*) count from book";
        try {
            Map<String, Object> map = qr.query(sql, new MapHandler());
            return Integer.parseInt(map.get("count").toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int removeBookByIds(String[] ids) {
        StringBuilder sb = new StringBuilder("delete from book where id in (");
        for (String id : ids) {
            sb.append("?,");
        }

        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
        try {
            return qr.update(sb.toString(), (Object[]) ids);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int updateStatus(String[] ids, String status) {
        StringBuilder sql = new StringBuilder("update book set status='" + status + "' where id in (");
        for (String id : ids) {
            sql.append("?,");
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(")");

        try {
            return qr.update(sql.toString(), (Object[]) ids);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
