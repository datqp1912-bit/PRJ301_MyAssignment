package controller;

import dal.UserDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import model.User;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDAO dao = new UserDAO();
        User user = dao.checkLogin(username, password);
        
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("account", user);
            
            //Phân quyền dựa trên RoleID
            switch (user.getRoleID()) {
                case 1: // Admin
                    response.sendRedirect("adminUserManager");
                    break;
                case 2: // Department
                case 3: // Group Leader
                case 4: // Employee
                    response.sendRedirect("userInformation");
                    break;
                default:
                    request.setAttribute("error", "Tài khoản không có quyền truy cập!");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    break;
            }

        } else {
            request.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
