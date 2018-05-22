package com.etoak.dao;

import com.etoak.book.books.po.Bookpic;
import com.etoak.book.books.service.BookpicService;

/**
 * Created by 冰封承諾Andy on 2018/5/19.
 */
public class TestBookpic {
    public static void main(String[] args) {
        BookpicService bookpicService = new BookpicService();
        bookpicService.addBookPic(new Bookpic(null,"/path/name.jpg",
                "name.jpg","63ebb402ea9a444aa1561032ab02f97b","0"));

        bookpicService.getAllBookPic().forEach(System.out::println);
    }
}
