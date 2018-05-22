package com.etoak.book.books.servlet;

import com.etoak.book.books.po.Category;
import com.etoak.book.books.service.CategoryService;
import com.etoak.book.commons.util.CommonsUtils;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by 冰封承諾Andy on 2018/5/19.
 */
@WebServlet(name = "CategoryServlet", urlPatterns = "/CategoryServlet")
public class CategoryServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/plain; charset=UTF-8");

        String method = request.getParameter("method");
        if ("add".equals(method)) {
            addCategory(request,response);
        } else if ("getAll2Json".equals(method)) {
            getAll2Json(request, response);
        }
    }

    private void getAll2Json(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CategoryService categoryService = new CategoryService();
        List<Category> categoryList = categoryService.getAllCategory();

        JSONArray jsonArray = JSONArray.fromObject(categoryList);

        response.setContentType("text/plain; charset=UTF-8");
        response.getWriter().print(jsonArray);
    }

    private void addCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        if (CommonsUtils.isEmpty(name)){
            return;
        }
        CategoryService categoryService = new CategoryService();
        String flag = categoryService.addCategory(new Category(null, name));
        if (flag != null) {
            response.getWriter().print("操作成功！");
        }else {
            response.getWriter().print("操作失败！");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
