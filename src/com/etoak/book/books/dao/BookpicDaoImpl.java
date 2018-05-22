package com.etoak.book.books.dao;

import com.etoak.book.books.po.Bookpic;
import com.etoak.book.commons.factory.CF;
import com.etoak.book.commons.util.CommonsUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 冰封承諾Andy on 2018/5/19.
 */
public class BookpicDaoImpl implements BookpicDao {
    private QueryRunner qr = new QueryRunner(CF.getDataSource());

    @Override
    public int addBookPic(Bookpic bookpic) {
        String sql = "insert into bookpic values (?,?,?,?,?)";
        try {
            return qr.update(sql, CommonsUtils.getUUIDTo32(), bookpic.getSavepath(),
                    bookpic.getShowname(), bookpic.getBookid(), bookpic.getFm());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Bookpic> getAll() {
        String sql = "select * from bookpic";
        try {
            return qr.query(sql, new BeanListHandler<>(Bookpic.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Bookpic getBookpicById(String id) {
        String sql = "select * from bookpic where id=?";
        try {
            return qr.query(sql, new BeanHandler<>(Bookpic.class), id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Bookpic> getBookpicByBookId(String bid) {
        String sql = "select * from bookpic where bookid=?";
        try {
            return qr.query(sql, new BeanListHandler<>(Bookpic.class), bid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int removeBookpicById(String id) {
        String sql = "delete from bookpic where id=?";
        try {
            return qr.update(sql, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int removeBookpicByBookId(String bid) {
        String sql = "delete from bookpic where bookid=?";
        try {
            return qr.update(sql, bid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void updateResetFM(String bookid) {
        String sql = "update bookpic set fm='0' where bookid=?";
        try {
            qr.update(sql, bookid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateFM(String id, String status) {
        String sql = "update bookpic set fm=? where id=?";
        try {
            qr.update(sql, status,id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
