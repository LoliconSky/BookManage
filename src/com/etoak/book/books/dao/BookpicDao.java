package com.etoak.book.books.dao;

import com.etoak.book.books.po.Bookpic;

import java.util.List;

/**
 * Created by 冰封承諾Andy on 2018/5/19.
 */
public interface BookpicDao {

    int addBookPic(Bookpic bookpic);

    List<Bookpic> getAll();

    Bookpic getBookpicById(String id);

    List<Bookpic> getBookpicByBookId(String bid);

    int removeBookpicById(String id);

    int removeBookpicByBookId(String bid);

    void updateResetFM(String bookid);

    void updateFM(String id, String status);
}
