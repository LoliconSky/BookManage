package com.etoak.book.books.servlet;

import com.etoak.book.books.po.Bookpic;
import com.etoak.book.books.service.BookpicService;
import com.etoak.book.commons.util.CommonsUtils;
import com.jspsmart.upload.Request;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;
import net.sf.json.JSONArray;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
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
        } else if ("del".equals(method)) {
            delPic(request, response);
        } else if (method == null) {
            // 图片异步上传
            addPic(request, response);
        }
    }

    private void addPic(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        SmartUpload su = new SmartUpload();
        su.initialize(this, request, response);
        try {
            su.upload();
            Request req = su.getRequest();
            String bookid = req.getParameter("bookid");


            // 获得文件数据
            com.jspsmart.upload.File file = su.getFiles().getFile(0);
            String fName = file.getFileName();
            String fileExt = file.getFileExt();
            String newName = CommonsUtils.getUUIDTo32() + "." + fileExt;
            file.saveAs("/myfiles/" + newName);

            // 保存到数据库
            Bookpic bookpic = new Bookpic();
            bookpic.setBookid(bookid);
            bookpic.setFm("0");
            bookpic.setSavepath("/myfiles/" + newName);
            bookpic.setShowname(fName);
            int flag = bookpicService.addBookPic(bookpic);


            if (flag == 0) {
                response.getWriter().print("{\"flag\":\"err\"}");
            } else {
                response.getWriter().print("{\"flag\":\"suc\"}");
            }
        } catch (SmartUploadException | IOException e) {
            e.printStackTrace();
        }
    }

    private void delPic(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        // 删除文件
        ServletContext context = this.getServletContext();
        Bookpic bookpic = bookpicService.getBookpicById(id);
        String path = context.getRealPath(bookpic.getSavepath());
        File file = new File(path);
        if (file.exists()) {
            if (!file.delete()) {
                response.getWriter().print("删除失败！");
                return;
            }
        }

        // 删除数据库
        int flag = bookpicService.removeBookpicById(id);

        if (flag == 0) {
            response.getWriter().print("删除失败！");
        } else {
            response.getWriter().print("suc");
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
