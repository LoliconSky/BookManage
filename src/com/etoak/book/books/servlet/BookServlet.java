package com.etoak.book.books.servlet;

import com.etoak.book.books.po.Book;
import com.etoak.book.books.po.Bookpic;
import com.etoak.book.books.service.BookService;
import com.etoak.book.books.service.BookpicService;
import com.etoak.book.commons.page.Page;
import com.etoak.book.commons.util.CommonsUtils;
import com.jspsmart.upload.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by 冰封承諾Andy on 2018/5/19.
 */
@WebServlet(name = "BookServlet", urlPatterns = "/BookServlet")
public class BookServlet extends HttpServlet {
    private BookService bookService = new BookService();


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/plain; charset=UTF-8");

        String method = request.getParameter("method");
        if (null == method) {
            // 带文件的添加
            addBookAndPic(request, response);
            return;
        } else if ("sj".equals(method)) {
            changeStatus(request, response, "1");
            return;
        } else if ("xj".equals(method)) {
            changeStatus(request, response, "0");
            return;
        }

        // 获得当前类的 class 文件
        Class clazz = this.getClass();
        try {
            // 寻找指定名字和参数的方法
            Method m = clazz.getDeclaredMethod(method, HttpServletRequest.class, HttpServletResponse.class);
            // 执行方法
            m.invoke(this, request, response);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    private void queryBookById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        Book book = bookService.getBookById(id);

        BookpicService bookpicService = new BookpicService();
        book.setBps(bookpicService.getBookpicByBookId(id));

        JSONObject jsonObject = JSONObject.fromObject(book);
        response.getWriter().print(jsonObject);
    }

    private void changeStatus(HttpServletRequest request, HttpServletResponse response, String status) throws IOException {
        String[] ids = request.getParameterValues("id");
        int flag = bookService.updateStatus(ids, status);

        if (flag == 0) {
            response.getWriter().print("操作失败！");
        } else {
            response.getWriter().print("操作成功！");
        }
    }

    private void delSome(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] ids = request.getParameterValues("id");
        int flag = bookService.removeSomeBook(ids);

        if (flag == 0) {
            response.getWriter().print("删除失败！");
        } else {
            response.getWriter().print("删除成功！");
        }
    }

    @Deprecated
    private void delBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        if (CommonsUtils.isEmpty(id)) {
            response.getWriter().print("删除失败！");
            return;
        }
        int flag = bookService.removeBookById(id);
        if (flag == 0) {
            response.getWriter().print("删除失败！");
        } else {
            response.getWriter().print("删除成功！");
        }
    }

    private void addBookAndPic(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        SmartUpload su = new SmartUpload();
        su.initialize(this, request, response);
        try {
            su.upload();
            Request req = su.getRequest();
            String name = req.getParameter("name");
            String price = req.getParameter("price");
            String author = req.getParameter("author");
            String date = req.getParameter("date");
            String categoryId = req.getParameter("categoryid");
            String status = req.getParameter("status");
            Integer fm = -1;
            if (req.getParameter("fm") != null) {
                fm = Integer.valueOf(req.getParameter("fm"));
            }

            Book book = new Book();
            book.setName(name);
            book.setPrice(Double.valueOf(price));
            book.setAuthor(author);
            book.setPublishdate(CommonsUtils.strToDate(date));
            book.setCategoryid(categoryId);
            book.setStatus(status);

            BookpicService bookpicService = new BookpicService();
            String bookid = bookService.addBook(book);

            // 获得文件数据
            Files files = su.getFiles();
            int flag = 1;
            for (int i = 0; i < files.getCount(); i++) {
                File file = files.getFile(i);
                String fName = file.getFileName();
                String fileExt = file.getFileExt();
                String newName = CommonsUtils.getUUIDTo32() + "." + fileExt;
                file.saveAs("/myfiles/" + newName);

                // 保存到数据库
                Bookpic bookpic = new Bookpic();
                bookpic.setBookid(bookid);
                if (fm == i) {
                    bookpic.setFm("1");
                } else {
                    bookpic.setFm("0");
                }
                bookpic.setSavepath("/myfiles/" + newName);
                bookpic.setShowname(fName);
                flag = bookpicService.addBookPic(bookpic);
            }

            if (flag == 0) {
                response.getWriter().print("添加失败！");
            } else {
                response.getWriter().print("添加成功！");
            }
        } catch (SmartUploadException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取全部的列表，并且采用的是递归查询，效率极低，不建议使用
     */
    @Deprecated
    private void getAllBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // List<Book> bookList = bookService.getAllBook();
        List<Book> bookList = bookService.getAllBookAndCategory();

        JSONArray jsonArray = JSONArray.fromObject(bookList);
        response.getWriter().print(jsonArray);
    }

    private void getListPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Page<Book> page = new Page<>();
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        String paraName = request.getParameter("name");
        String caid = request.getParameter("caid");

        page.setPageNumber(pageNumber);

        int total = bookService.getBookCount(paraName, caid);
        page.setTotal(total);

        List<Book> bookList = bookService.getBookAndCategoryPage(page.getStart(), page.getPageSize(), paraName, caid);
        page.setRows(bookList);

        JSONObject jsonObject = JSONObject.fromObject(page);
        response.getWriter().print(jsonObject);
    }

    @Deprecated
    private void addBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String price = request.getParameter("price");
        String author = request.getParameter("author");
        String date = request.getParameter("date");
        String categoryId = request.getParameter("categoryid");
        String status = request.getParameter("status");

        Book book = new Book();
        book.setName(name);
        book.setPrice(Double.valueOf(price));
        book.setAuthor(author);
        book.setPublishdate(CommonsUtils.strToDate(date));
        book.setCategoryid(categoryId);
        book.setStatus(status);

        System.out.println(book);

        String flag = bookService.addBook(book);

        if (flag != null) {
            response.getWriter().print("添加成功！!");
        } else {
            response.getWriter().print("添加失败！!");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
