package com.etoak.book.books.dao;

import com.etoak.book.books.po.Book;
import com.etoak.book.books.po.Category;
import com.etoak.book.commons.factory.CF;
import com.etoak.book.commons.util.CommonsUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.sql.SQLException;
import java.util.ArrayList;
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
    public List<Book> getBookAndCategoryPage(int start, int pageSize, String name, String caid) {
        String sql = "select b.id bid,b.name bname, b.price bprice," +
                " b.author bauthor, b.publishdate bpublishdate," +
                " b.categoryid bcaid, b.status bstatus, c.id cid," +
                " c.name cname from book b join category c on b.categoryid = c.id" +
                " where 1=1";
        if (!CommonsUtils.isEmpty(name)) {
            sql += " and b.name like '%" + name + "%'";
        }
        if (!CommonsUtils.isEmpty(caid)) {
            sql += " and b.categoryid like '%" + caid + "%'";
        }
        sql += " order by bpublishdate limit ?,?";
        try {
            List<Map<String, Object>> maps = qr.query(sql, new MapListHandler(), start, pageSize);
            if (maps == null || maps.size() == 0) {
                return null;
            }
            List<Book> list = new ArrayList<>();
            for (Map<String, Object> map : maps) {
                Book book = new Book();
                book.setId(map.get("bid").toString());
                book.setName(map.get("bname").toString());
                book.setPrice(Double.valueOf(map.get("bprice").toString()));
                book.setAuthor(map.get("bauthor").toString());
                book.setPublishdate(CommonsUtils.strToDate(map.get("bpublishdate").toString()));
                book.setCategoryid(map.get("bcaid").toString());
                book.setStatus(map.get("bstatus").toString());
                Category category = new Category();
                category.setId(map.get("cid").toString());
                category.setName(map.get("cname").toString());
                book.setCategory(category);

                list.add(book);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getCount(String name, String caid) {
        String sql = "select count(*) count from book where 1=1";
        if (!CommonsUtils.isEmpty(name)) {
            sql += " and name like '%" + name + "%'";
        }
        if (!CommonsUtils.isEmpty(caid)) {
            sql += " and categoryid like '%" + caid + "%'";
        }
        try {
            Map<String, Object> map = qr.query(sql, new MapHandler());
            return Integer.parseInt(map.get("count").toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
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
