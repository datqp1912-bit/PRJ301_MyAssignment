package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.*;
import java.time.temporal.IsoFields;
import java.util.*;
import dal.RequestDAO;
import model.User;

@WebServlet("/agenda")
public class AgendaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy user đang đăng nhập
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            response.sendRedirect("login");
            return;
        }

        // Lấy offset tuần (0: tuần hiện tại, +1: tuần sau, -1: tuần trước)
        int weekOffset = 0;
        String weekParam = request.getParameter("weekOffset");
        if (weekParam != null && !weekParam.isEmpty()) {
            try {
                weekOffset = Integer.parseInt(weekParam);
            } catch (NumberFormatException e) {
                weekOffset = 0;
            }
        }

        // Lấy Thứ 2 và CN của tuần tương ứng
        LocalDate today = LocalDate.now();
        LocalDate monday = today.with(DayOfWeek.MONDAY).plusWeeks(weekOffset);
        LocalDate sunday = monday.plusDays(6);

        // Danh sách ngày trong tuần
        List<LocalDate> dates = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            dates.add(monday.plusDays(i));
        }

        // Dùng RequestDAO để lấy nhân viên trong cùng phòng ban
        RequestDAO rdao = new RequestDAO();
        List<Map<String, Object>> users = rdao.getUsersInDepartment(currentUser.getDepID());

        // Tạo map lưu trạng thái làm việc theo từng ngày
        Map<Integer, Map<LocalDate, String>> attendanceMap = new HashMap<>();

        for (Map<String, Object> u : users) {
            int userId = (int) u.get("UserID");
            Map<LocalDate, String> dayStatus = new HashMap<>();

            for (LocalDate d : dates) {
                if (d.getDayOfWeek() == DayOfWeek.SUNDAY) {
                    dayStatus.put(d, "sunday");
                } else {
                    boolean onLeave = rdao.isOnLeave(userId, d);
                    dayStatus.put(d, onLeave ? "off" : "work");
                }
            }
            attendanceMap.put(userId, dayStatus);
        }

        // Gửi dữ liệu sang JSP
        request.setAttribute("weekOffset", weekOffset);
        request.setAttribute("dates", dates);
        request.setAttribute("users", users);
        request.setAttribute("attendanceMap", attendanceMap);
        request.setAttribute("startDate", monday);
        request.setAttribute("endDate", sunday);
        request.setAttribute("currentWeek", monday.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR));

        request.getRequestDispatcher("agenda.jsp").forward(request, response);
    }
}
