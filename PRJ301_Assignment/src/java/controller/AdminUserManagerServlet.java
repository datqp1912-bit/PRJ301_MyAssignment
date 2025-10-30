package controller;

import dal.UserDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import model.User;

@WebServlet("/adminUserManager")
public class AdminUserManagerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserDAO dao = new UserDAO();
        List<User> list = dao.getAllUsers(); // bạn sẽ tạo hàm này trong UserDAO

        request.setAttribute("users", list);
        request.getRequestDispatcher("adminUserManager.jsp").forward(request, response);
    }
}
