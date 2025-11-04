package controller;

import dal.UserDAO;
import dal.DepartmentDAO;
import dal.RoleDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import model.User;
import model.Department;
import model.Role;

@WebServlet("/adminEdit")
public class adminEditServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        UserDAO userDao = new UserDAO();
        DepartmentDAO depDao = new DepartmentDAO();
        RoleDAO roleDao = new RoleDAO();

        try {
            if ("delete".equalsIgnoreCase(action)) {
                // Xóa người dùng theo ID
                int id = Integer.parseInt(request.getParameter("id"));
                userDao.deleteUser(id);
                response.sendRedirect("adminUserManager");

            } else if ("edit".equalsIgnoreCase(action)) {
                // Lấy thông tin user theo ID để hiển thị lên form chỉnh sửa
                int id = Integer.parseInt(request.getParameter("id"));
                User u = userDao.getUserByID(id);

                if (u != null) {
                    // Lấy danh sách phòng ban & vai trò
                    List<Department> departments = depDao.getAll();
                    List<Role> roles = roleDao.getAll();

                    request.setAttribute("departments", departments);
                    request.setAttribute("roles", roles);
                    request.setAttribute("user", u);

                    RequestDispatcher rd = request.getRequestDispatcher("adminEdit.jsp");
                    rd.forward(request, response);
                } else {
                    response.sendRedirect("adminUserManager");
                }

            } else if ("add".equalsIgnoreCase(action)) {
                // Lấy danh sách phòng ban & chức vụ để hiển thị trong form thêm
                List<Department> departments = depDao.getAll();
                List<Role> roles = roleDao.getAll();

                request.setAttribute("departments", departments);
                request.setAttribute("roles", roles);

                RequestDispatcher rd = request.getRequestDispatcher("adminAdd.jsp");
                rd.forward(request, response);
            } else {
                response.sendRedirect("adminUserManager");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("adminUserManager");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        UserDAO dao = new UserDAO();

        try {
            if ("add".equalsIgnoreCase(action)) {
                // Nhận dữ liệu từ form thêm
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                String name = request.getParameter("name");
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");
                int depID = Integer.parseInt(request.getParameter("depID"));
                int roleID = Integer.parseInt(request.getParameter("roleID"));
                boolean active = Boolean.parseBoolean(request.getParameter("active"));

                User u = new User();
                u.setUsername(username);
                u.setPassword(password);
                u.setName(name);
                u.setEmail(email);
                u.setPhone(phone);
                u.setDepID(depID);
                u.setRoleID(roleID);
                u.setActive(active);

                dao.addUser(u);
                response.sendRedirect("adminUserManager");

            } else if ("edit".equalsIgnoreCase(action) || "update".equalsIgnoreCase(action)) {
                // Cập nhật người dùng (xử lý khi bấm nút "Lưu thay đổi")
                int id = Integer.parseInt(request.getParameter("id"));
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                String name = request.getParameter("name");
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");
                int depID = Integer.parseInt(request.getParameter("depID"));
                int roleID = Integer.parseInt(request.getParameter("roleID"));
                boolean active = Boolean.parseBoolean(request.getParameter("active"));

                User u = new User();
                u.setUserID(id);
                u.setUsername(username);
                u.setPassword(password);
                u.setName(name);
                u.setEmail(email);
                u.setPhone(phone);
                u.setDepID(depID);
                u.setRoleID(roleID);
                u.setActive(active);

                dao.updateUser(u);
                response.sendRedirect("adminUserManager");

            } else {
                response.sendRedirect("adminUserManager");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("adminUserManager");
        }
    }
}
