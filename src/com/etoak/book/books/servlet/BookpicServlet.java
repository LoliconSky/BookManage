package com.etoak.book.books.servlet;

import com.etoak.book.books.po.Bookpic;
import com.etoak.book.books.service.BookpicService;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by 冰封承諾Andy on 2018/5/21.
 */
@WebServlet(name = "BookpicServlet", urlPatterns = "/BookpicServlet")
public class BookpicServlet extends HttpServlet {
    private BookpicService bookpicService = new BookpicService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/plain; charset=UTF-8");

        String method = request.getParameter("method");
        if ("get".equals(method)) {
            getPicByBookId(request, response);
        }
    }

    private void getPicByBookId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        List<Bookpic> list = bookpicService.getBookpicByBookId(id);

        JSONArray jsonArray = JSONArray.fromObject(list);
        response.getWriter().print(jsonArray);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
