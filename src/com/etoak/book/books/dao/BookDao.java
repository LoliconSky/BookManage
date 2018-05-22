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

    // TODO 应该使用可变参或者 Map 多个拼接
    List<Book> getPage(int index, int max);

    // TODO 应该使用可变参或者 Map 多个拼接
    List<Book> getBookAndCategoryPage(int start, int pageSize, String name, String caid);

    int getCount(String name, String caid);

    int removeBookById(String id);

    int removeBookByName(String name);

    int removeBookByIds(String[] ids);

    int updateStatus(String[] ids, String status);
}
