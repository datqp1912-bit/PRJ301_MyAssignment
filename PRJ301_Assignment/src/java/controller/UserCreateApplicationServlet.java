package controller;

import dal.RequestDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.Request;
import model.User;

@WebServlet("/UserCreateApplicationServlet")
public class UserCreateApplicationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String fromStr = request.getParameter("from");
        String toStr = request.getParameter("to");
        String reason = request.getParameter("reason");

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fromDate = sdf.parse(fromStr);
            Date toDate = sdf.parse(toStr);

            Request req = new Request();
            req.setCreateBy(user.getUserID());
            req.setFrom(fromDate);
            req.setTo(toDate);
            req.setReason(reason);
            req.setStatusID(1); // Pending
            req.setApprovedBy(null);
            req.setCreateTime(new Date());

            RequestDAO dao = new RequestDAO();
            dao.insertRequest(req);

            // Sau khi insert xong quay về danh sách
            response.sendRedirect("userApplication");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra khi tạo đơn");
            request.getRequestDispatcher("createApplication.jsp").forward(request, response);
        }
    }
}
