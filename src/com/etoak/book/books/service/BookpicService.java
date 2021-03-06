package com.etoak.book.books.service;

import com.etoak.book.books.dao.BookpicDao;
import com.etoak.book.books.dao.BookpicDaoImpl;
import com.etoak.book.books.po.Bookpic;

import java.util.List;

/**
 * Created by 冰封承諾Andy on 2018/5/19.
 */
public class BookpicService {
    private BookpicDao dao = new BookpicDaoImpl();

    public int addBookPic(Bookpic bookpic) {
        return dao.addBookPic(bookpic);
    }

    @Deprecated
    public List<Bookpic> getAllBookPic() {
        return dao.getAll();
    }

    public Bookpic getBookpicById(String id) {
        return dao.getBookpicById(id);
    }

    public List<Bookpic> getBookpicByBookId(String bid) {
        return dao.getBookpicByBookId(bid);
    }

    public int removeBookpic(Bookpic bookpic) {
        return removeBookpicById(bookpic.getId());
    }

    public int removeBookpicById(String id) {
        return dao.removeBookpicById(id);
    }

    public int removeBookpicByBookId(String bid) {
        return dao.removeBookpicByBookId(bid);
    }

    public void updateResetFM(String bookid) {
        dao.updateResetFM(bookid);
    }

    public void updateFM(String id, String status) {
        dao.updateFM(id, status);
    }
}
