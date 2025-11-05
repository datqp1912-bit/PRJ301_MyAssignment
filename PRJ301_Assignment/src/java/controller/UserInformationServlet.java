package controller;

import dal.UserDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import model.User;

@WebServlet("/userInformation")
public class UserInformationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User sessionUser = (User) session.getAttribute("account");
        UserDAO dao = new UserDAO();
        User currentUser = dao.getUserByID(sessionUser.getUserID());

        // Cập nhật lại session và request
        session.setAttribute("account", currentUser);
        request.setAttribute("user", currentUser);

        request.getRequestDispatcher("userInformation.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserDAO dao = new UserDAO();
        User sessionUser = (User) session.getAttribute("account");

        if (sessionUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        if (action != null && action.equalsIgnoreCase("edit")) {

            // Lấy dữ liệu từ form
            String name = request.getParameter("name");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");

            // Giữ nguyên các thông tin không cho phép chỉnh
            int userID = sessionUser.getUserID();
            String username = sessionUser.getUsername();
            int depID = sessionUser.getDepID();
            int roleID = sessionUser.getRoleID();
            boolean active = sessionUser.isActive();

            // Tạo user mới với thông tin cập nhật
            User updatedUser = new User(userID, username, password, name, email, phone, depID, roleID, active);

            // Lưu vào DB
            dao.updateUser(updatedUser);

            // Cập nhật session và request
            session.setAttribute("account", updatedUser);
            request.setAttribute("user", updatedUser);
            request.setAttribute("message", "✅ Cập nhật thông tin thành công!");

            // Quay lại trang userInformation.jsp (hiển thị dữ liệu mới)
            request.getRequestDispatcher("userInformation.jsp").forward(request, response);

        } else {
            response.sendRedirect("userInformation");
        }
    }
}
