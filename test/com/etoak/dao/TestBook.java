package com.etoak.dao;

import com.etoak.book.books.po.Book;
import com.etoak.book.books.service.BookService;
import org.junit.Test;

import java.util.Date;

/**
 * Created by 冰封承諾Andy on 2018/5/19.
 */
public class TestBook {
    public static void main(String[] args) {
        BookService bookService = new BookService();
        bookService.addBook(new Book(null, "计算机书名",125.5,"我",new Date(),"02ee8172df3047b1989b46401055d49a","0"));

        bookService.getAllBook().forEach(System.out::println);
    }

    @Test
    public void getOne(){
        BookService bookService = new BookService();

        bookService.getAllBookAndCategory();
    }

    @Test
    public void removeBook() {
        BookService bookService = new BookService();
        System.out.println(bookService.removeBookByName("书名"));
    }
}
