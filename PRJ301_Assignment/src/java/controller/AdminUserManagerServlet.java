package controller;

import dal.UserDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import model.User;
import model.Department;
import model.Role;

@WebServlet("/adminUserManager")
public class AdminUserManagerServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "add":
                showAddForm(request, response);
                break;

            case "edit":
                showEditForm(request, response);
                break;

            case "delete":
                deleteUser(request, response);
                break;

            default:
                listUsers(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        switch (action) {
            case "add":
                addUser(request, response);
                break;

            case "edit":
                updateUser(request, response);
                break;

            default:
                listUsers(request, response);
                break;
        }
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<User> list;

        if (keyword != null && !keyword.trim().isEmpty()) {
            list = userDAO.searchUsers(keyword.trim());
        } else {
            list = userDAO.getAllUsers();
        }

        request.setAttribute("users", list);
        request.setAttribute("keyword", keyword);
        request.getRequestDispatcher("adminUserManager.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("user", null);
        request.getRequestDispatcher("adminUserForm.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User user = userDAO.getUserById(id);
        request.setAttribute("user", user);
        request.getRequestDispatcher("adminUserForm.jsp").forward(request, response);
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String display = request.getParameter("display");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        int depId = Integer.parseInt(request.getParameter("department"));
        int roleId = Integer.parseInt(request.getParameter("role"));
        boolean active = request.getParameter("active") != null;

        // ✅ Tạo đối tượng Role và Department đúng kiểu
        Department dep = new Department();
        dep.setDepID(depId);

        Role role = new Role();
        role.setRoleID(roleId);

        // ✅ Gán đầy đủ vào User
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setDisplay(display);
        user.setEmail(email);
        user.setPhone(phone);
        user.setDepartment(dep);
        user.setRole(role);
        user.setActive(active);

        // ✅ Thêm user
        userDAO.addUser(user);

        response.sendRedirect("adminUserManager");
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String display = request.getParameter("display");
        int depId = Integer.parseInt(request.getParameter("department"));
        int roleId = Integer.parseInt(request.getParameter("role"));
        boolean active = request.getParameter("active") != null;

        Department dep = new Department();
        dep.setDepID(depId);

        Role role = new Role();
        role.setRoleID(roleId);

        User user = new User();
        user.setUserID(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setDisplay(display);
        user.setDepartment(dep);
        user.setRole(role);
        user.setActive(active);

        userDAO.updateUser(user);
        response.sendRedirect("adminUserManager");
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        userDAO.deleteUser(id);
        response.sendRedirect("adminUserManager");
    }
}
