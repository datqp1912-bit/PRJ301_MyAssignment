package controller;

import dal.RequestDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import model.Request;
import model.User;

@WebServlet("/userApplication")
public class UserApplicationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        RequestDAO dao = new RequestDAO();
        List<Request> userRequests = dao.getRequestsByUser(currentUser.getUserID());

        request.setAttribute("listRequest", userRequests);
        request.getRequestDispatcher("userApplication.jsp").forward(request, response);
    }
}
