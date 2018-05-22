package com.etoak.book.books.po;

/**
 * Created by 冰封承諾Andy on 2018/5/19.
 */
public class Bookpic {
    private String id;
    private String savepath;
    private String showname;
    private Book book;
    private String bookid;
    private String fm;

    public Bookpic() {
    }

    public Bookpic(String id, String savepath, String showname, String bookid, String fm) {
        this.id = id;
        this.savepath = savepath;
        this.showname = showname;
        this.bookid = bookid;
        this.fm = fm;
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSavepath() {
        return savepath;
    }

    public void setSavepath(String savepath) {
        this.savepath = savepath;
    }

    public String getShowname() {
        return showname;
    }

    public void setShowname(String showname) {
        this.showname = showname;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getFm() {
        return fm;
    }

    public void setFm(String fm) {
        this.fm = fm;
    }

    @Override
    public String toString() {
        return "Bookpic{" +
                "id='" + id + '\'' +
                ", savepath='" + savepath + '\'' +
                ", showname='" + showname + '\'' +
                ", book=" + book +
                ", bookid='" + bookid + '\'' +
                ", fm='" + fm + '\'' +
                '}';
    }
}
