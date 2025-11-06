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

@WebServlet("/UserEditApplicationServlet")
public class UserEditApplicationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        RequestDAO dao = new RequestDAO();

        try {
            // ===================== SỬA ĐƠN =====================
            if ("edit".equals(action)) {
                int reqID = Integer.parseInt(request.getParameter("id"));
                Request req = dao.getRequestById(reqID);

                if (req == null || req.getCreateBy() != user.getUserID()) {
                    response.sendRedirect("userApplication");
                    return;
                }

                request.setAttribute("req", req);
                request.getRequestDispatcher("editApplication.jsp").forward(request, response);
            }

            // ===================== XOÁ ĐƠN =====================
            else if ("delete".equals(action)) {
                int reqID = Integer.parseInt(request.getParameter("id"));
                boolean success = dao.deleteRequest(reqID);

                if (success) {
                    response.sendRedirect("userApplication");
                } else {
                    request.setAttribute("error", "Không thể xoá đơn hoặc đơn không tồn tại!");
                    request.getRequestDispatcher("userApplication").forward(request, response);
                }
            }

            // ===================== TẠO ĐƠN =====================
            else if ("create".equals(action)) {
                request.getRequestDispatcher("createApplication.jsp").forward(request, response);
            }

            // ===================== MẶC ĐỊNH =====================
            else {
                response.sendRedirect("userApplication");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("userApplication");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        RequestDAO dao = new RequestDAO();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fromStr = request.getParameter("from");
            String toStr = request.getParameter("to");
            String reason = request.getParameter("reason");

            Date fromDate = sdf.parse(fromStr);
            Date toDate = sdf.parse(toStr);

            // Kiểm tra hợp lệ: ngày bắt đầu không được sau ngày kết thúc
            if (fromDate.after(toDate)) {
                request.setAttribute("error", "Ngày bắt đầu không được lớn hơn ngày kết thúc!");
                if ("edit".equals(action)) {
                    int reqID = Integer.parseInt(request.getParameter("id"));
                    request.setAttribute("req", dao.getRequestById(reqID));
                    request.getRequestDispatcher("editApplication.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("createApplication.jsp").forward(request, response);
                }
                return;
            }

            // ===================== CẬP NHẬT ĐƠN =====================
            if ("edit".equals(action)) {
                int reqID = Integer.parseInt(request.getParameter("id"));
                Request updateReq = new Request();
                updateReq.setReqID(reqID);
                updateReq.setFrom(fromDate);
                updateReq.setTo(toDate);
                updateReq.setReason(reason);

                boolean success = dao.updateRequest(updateReq);
                if (success) {
                    response.sendRedirect("userApplication");
                } else {
                    request.setAttribute("error", "Không thể cập nhật đơn, vui lòng thử lại!");
                    request.setAttribute("req", dao.getRequestById(reqID));
                    request.getRequestDispatcher("editApplication.jsp").forward(request, response);
                }
            }

            // ===================== TẠO MỚI ĐƠN =====================
            else if ("create".equals(action)) {
                Request newReq = new Request();
                newReq.setCreateBy(user.getUserID());
                newReq.setCreateTime(new Date());
                newReq.setFrom(fromDate);
                newReq.setTo(toDate);
                newReq.setReason(reason);
                newReq.setStatusID(1); // 1 = Pending
                newReq.setApprovedBy(null);
                newReq.setApprovedTime(null);

                dao.insertRequest(newReq);
                response.sendRedirect("userApplication");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra khi xử lý!");
            if ("edit".equals(action)) {
                request.getRequestDispatcher("editApplication.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("createApplication.jsp").forward(request, response);
            }
        }
    }
}
