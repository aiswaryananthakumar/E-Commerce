package com.ecommerce.control;

import com.ecommerce.dao.CategoryDaoDemo;

import com.ecommerce.entity.CategoryDemo;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/categoryDemo")
@MultipartConfig(maxFileSize = 16177215) // ~16MB
public class CategoryManagementControl extends HttpServlet {

    private CategoryDaoDemo categoryDao = new CategoryDaoDemo();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "LIST";

        switch (action) {
        case "EDIT":
            try {
				showEditForm(request, response);
			} catch (ServletException | IOException | SQLException e) {
				e.printStackTrace();
			}
            break;
        case "DELETE":
            try {
				deleteCategory(request, response);
			} catch (IOException | SQLException e) {
				e.printStackTrace();
			}
            break;
        default:
            listCategories(request, response);
            break;
    }
    }

    private void listCategories(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<CategoryDemo> list = categoryDao.getAllCategories();
        request.setAttribute("categoryList", list);
        request.getRequestDispatcher("category-list.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        CategoryDemo category = categoryDao.getCategoryById(id);
        request.setAttribute("categoryDemo", category);
        request.getRequestDispatcher("category-form.jsp").forward(request, response);
    }

    private void deleteCategory(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        categoryDao.deleteCategory(id);
        response.sendRedirect("categoryDemo");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        Part filePart = request.getPart("image");
        InputStream inputStream = filePart.getInputStream();
        byte[] imageBytes = inputStream.readAllBytes();

        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            // Insert
            CategoryDemo newCategory = new CategoryDemo();
            newCategory.setName(name);
            newCategory.setImage(imageBytes);
            categoryDao.insertCategory(newCategory);
        } else {
            // Update
            int id = Integer.parseInt(idStr);
            CategoryDemo existing = new CategoryDemo();
            existing.setId(id);
            existing.setName(name);
            existing.setImage(imageBytes);
            categoryDao.updateCategory(existing);
        }
        response.sendRedirect("categoryDemo");
    }
}
