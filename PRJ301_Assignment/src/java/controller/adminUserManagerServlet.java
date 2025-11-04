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

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        UserDAO dao = new UserDAO();
        String action = request.getParameter("action");

        if ("add".equalsIgnoreCase(action)) {
            // Khi bấm nút "Thêm người dùng mới" → chuyển sang servlet adminEdit
            response.sendRedirect("adminEdit?action=add");
            return;
        }

        // Xử lý tìm kiếm và hiển thị danh sách người dùng
        String keyword = request.getParameter("keyword");
        List<User> list;

        if (keyword != null && !keyword.trim().isEmpty()) {
            list = dao.searchByName(keyword.trim());
        } else {
            list = dao.getAllUsers();
        }

        request.setAttribute("users", list);
        request.setAttribute("keyword", keyword);

        RequestDispatcher dispatcher = request.getRequestDispatcher("adminUserManager.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Servlet này chỉ hiển thị danh sách, không xử lý thêm/sửa/xóa
        doGet(request, response);
    }
}
