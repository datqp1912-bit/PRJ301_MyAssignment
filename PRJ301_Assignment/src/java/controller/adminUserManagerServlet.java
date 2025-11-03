package controller;

import dal.UserDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import model.User;

@WebServlet("/adminUserManager")
public class adminUserManagerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserDAO dao = new UserDAO();

        // Lấy từ khóa tìm kiếm từ JSP
        String keyword = request.getParameter("keyword");

        List<User> list;
        if (keyword != null && !keyword.trim().isEmpty()) {
            // Nếu có từ khóa, tìm kiếm theo Name
            list = dao.searchByName(keyword.trim());
        } else {
            // Nếu không có từ khóa, lấy tất cả user
            list = dao.getAllUsers();
        }

        // Lưu danh sách vào request attribute
        request.setAttribute("users", list);
        request.setAttribute("keyword", keyword); // giữ giá trị tìm kiếm trên form

        // Forward sang JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("adminUserManager.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Gọi doGet để xử lý tìm kiếm
        doGet(request, response);
    }
}
