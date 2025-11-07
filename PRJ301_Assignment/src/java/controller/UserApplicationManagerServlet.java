package controller;

import dal.RequestDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import model.Request;
import model.User;

@WebServlet("/managerRequest")
public class UserApplicationManagerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // Kiểm tra đăng nhập
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        RequestDAO dao = new RequestDAO();

        // Lấy danh sách đơn theo phòng ban
        List<Request> list = dao.getRequestsByDepartment(user.getDepID());
        request.setAttribute("listRequest", list);

        // Hiển thị danh sách
        request.getRequestDispatcher("managerRequest.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        int reqID = Integer.parseInt(request.getParameter("reqID"));
        RequestDAO dao = new RequestDAO();

        if ("approve".equals(action)) {
            dao.updateRequestStatus(reqID, 2, user.getUserID()); // 2 = Accepted
        } else if ("reject".equals(action)) {
            dao.updateRequestStatus(reqID, 3, user.getUserID()); // 3 = Rejected
        }

        response.sendRedirect("managerRequest");
    }
}
