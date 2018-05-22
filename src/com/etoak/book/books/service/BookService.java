package com.etoak.book.books.service;

import com.etoak.book.books.dao.BookDao;
import com.etoak.book.books.dao.BookDaoImpl;
import com.etoak.book.books.dao.BookpicDao;
import com.etoak.book.books.dao.BookpicDaoImpl;
import com.etoak.book.books.po.Book;
import com.etoak.book.books.po.Category;

import java.util.List;

/**
 * Created by 冰封承諾Andy on 2018/5/19.
 */
public class BookService {
    private BookDao dao = new BookDaoImpl();

    public String addBook(Book book) {
        return dao.addBook(book);
    }

    public List<Book> getAllBook() {
        return dao.getAll();
    }

    @Deprecated
    public List<Book> getAllBookAndCategory() {
        CategoryService categoryService = new CategoryService();
        List<Book> bookList = dao.getAll();

        bookList.forEach(book -> {
            Category category = categoryService.getCategoryById(book.getCategoryid());
            book.setCategory(category);
        });

        return bookList;
    }

    public List<Book> getBookAndCategoryPage(int start, int pageSize, String name, String caid) {
        return dao.getBookAndCategoryPage(start, pageSize, name, caid);
    }

    public List<Book> getPage(int index, int max) {
        return dao.getPage(index, max);
    }


    public Book getBookById(String id) {
        return dao.getBookById(id);
    }

    public int getBookCount(String name, String caid) {
        return dao.getCount(name,caid);
    }

    public int removeBook(Book book) {
        return removeBookById(book.getId());
    }

    public int removeBookById(String id) {
        return dao.removeBookById(id);
    }

    public int removeBookByName(String name) {
        return dao.removeBookByName(name);
    }

    public int removeBookAndPic(Book book) {
        BookpicDao dao = new BookpicDaoImpl();
        dao.removeBookpicByBookId(book.getId());

        return removeBookById(book.getId());
    }

    public int removeSomeBook(String[] ids) {
        return dao.removeBookByIds(ids);
    }

    public int updateStatus(String[] ids, String status) {
        return dao.updateStatus(ids, status);
    }

}
