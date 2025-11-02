package controller;

import dal.UserDAO;
import dal.DepartmentDAO;
import dal.RoleDAO;
import model.User;
import model.Department;
import model.Role;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/adminUserEdit")
public class AdminEditServlet extends HttpServlet {

    private UserDAO userDAO;
    private DepartmentDAO depDAO;
    private RoleDAO roleDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
        depDAO = new DepartmentDAO();
        roleDAO = new RoleDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            action = "";
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
                // Nếu không có action, quay lại danh sách chính
                response.sendRedirect("adminUserManager");
                break;
        }
    }

    // ===================== HIỂN THỊ FORM THÊM =====================
    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("roles", roleDAO.getAllRoles());
        request.setAttribute("departments", depDAO.getAllDepartments());
        request.getRequestDispatcher("adminEdit.jsp").forward(request, response);
    }

    // ===================== HIỂN THỊ FORM SỬA =====================
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User user = userDAO.getUserById(id);
        request.setAttribute("user", user);
        request.setAttribute("roles", roleDAO.getAllRoles());
        request.setAttribute("departments", depDAO.getAllDepartments());
        request.getRequestDispatcher("adminEdit.jsp").forward(request, response);
    }

    // ===================== XOÁ NGƯỜI DÙNG =====================
    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        userDAO.deleteUser(id);
        response.sendRedirect("adminUserManager"); // quay lại danh sách sau khi xóa
    }

    // ===================== XỬ LÝ POST =====================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("add".equals(action)) {
            addUser(request, response);
        } else if ("edit".equals(action)) {
            updateUser(request, response);
        } else {
            response.sendRedirect("adminUserManager");
        }
    }

    // ===================== THÊM NGƯỜI DÙNG =====================
    private void addUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String display = request.getParameter("display");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        int depID = Integer.parseInt(request.getParameter("department"));
        int roleID = Integer.parseInt(request.getParameter("role"));
        boolean active = request.getParameter("active") != null;

        Department dep = new Department();
        dep.setDepID(depID);
        Role role = new Role();
        role.setRoleID(roleID);

        User u = new User();
        u.setUsername(username);
        u.setPassword(password);
        u.setDisplay(display);
        u.setEmail(email);
        u.setPhone(phone);
        u.setDepartment(dep);
        u.setRole(role);
        u.setActive(active);

        userDAO.addUser(u);
        response.sendRedirect("adminUserManager");
    }

    // ===================== CẬP NHẬT NGƯỜI DÙNG =====================
    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String display = request.getParameter("display");
        boolean active = request.getParameter("active") != null;

        User u = userDAO.getUserById(id);
        u.setUsername(username);
        u.setPassword(password);
        u.setDisplay(display);
        u.setActive(active);

        userDAO.updateUser(u);
        response.sendRedirect("adminUserManager");
    }
}
