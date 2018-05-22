package com.etoak.book.books.dao;

import com.etoak.book.books.po.Book;

import java.util.List;

/**
 * Created by 冰封承諾Andy on 2018/5/19.
 */
public interface BookDao {
    String addBook(Book book);

    List<Book> getAll();

    Book getBookById(String id);

    int removeBookById(String id);

    int removeBookByName(String name);

    List<Book> getPage(int index, int max);

    int getCount();

    int removeBookByIds(String[] ids);

    int updateStatus(String[] ids, String status);
}
