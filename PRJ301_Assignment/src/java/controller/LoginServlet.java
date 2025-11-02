package controller;

import dal.UserDAO;
import model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Nếu đã đăng nhập, redirect thẳng theo role
        if (session != null && session.getAttribute("account") != null) {
            String role = (String) session.getAttribute("role");

            switch (role.toLowerCase()) {
                case "admin":
                    response.sendRedirect("adminUserManager");
                    break;
                case "department manager":
                    response.sendRedirect("dep/home");
                    break;
                case "group leader":
                    response.sendRedirect("leader/home");
                    break;
                case "employee":
                    response.sendRedirect("employee/home");
                    break;
                default:
                    response.sendRedirect("login.jsp"); // Role không xác định → quay lại login
                    break;
            }
            return;
        }

        // Nếu chưa đăng nhập, hiển thị trang login
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDAO dao = new UserDAO();
        User u = dao.login(username, password);

        if (u != null) {
            // Đăng nhập thành công → lưu account và role vào session
            HttpSession session = request.getSession();
            session.setAttribute("account", u);
            session.setAttribute("role", u.getRole().getRoleName().toLowerCase());

            // Redirect trực tiếp theo role
            switch (u.getRole().getRoleName().toLowerCase()) {
                case "admin":
                    response.sendRedirect("adminUserManager");
                    break;
                case "department manager":
                    response.sendRedirect("dep/home");
                    break;
                case "group leader":
                    response.sendRedirect("leader/home");
                    break;
                case "employee":
                    response.sendRedirect("employee/home");
                    break;
                default:
                    response.sendRedirect("login.jsp"); // Role không xác định
                    break;
            }

        } else {
            // Sai tài khoản hoặc mật khẩu
            request.setAttribute("ms", "Sai tài khoản hoặc mật khẩu!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet xử lý đăng nhập và phân quyền người dùng";
    }
}
