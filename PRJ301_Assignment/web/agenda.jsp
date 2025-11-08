<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Lịch làm việc phòng ban</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
        <style>
            body {
                margin: 0;
                padding: 0;
                font-family: 'Segoe UI', sans-serif;
                display: flex;
                height: 100vh;
                overflow: hidden;
            }
            .sidebar {
                width: 250px;
                background-color: #1e293b;
                color: white;
                display: flex;
                flex-direction: column;
                justify-content: space-between;
                padding: 20px;
                position: fixed;
                top: 0;
                left: 0;
                bottom: 0;
            }
            .sidebar h5 {
                margin-bottom: 20px;
                color: #f8fafc;
            }
            .sidebar .nav-link {
                color: white;
                padding: 10px 0;
                transition: background 0.3s;
                text-decoration: none;
                display: block;
            }
            .sidebar .nav-link:hover {
                background-color: rgba(255, 255, 255, 0.2);
                border-radius: 5px;
            }
            .main-content {
                margin-left: 250px;
                flex: 1;
                background-color: #f8f9fa;
                padding: 30px;
                height: 100vh;
                overflow-y: auto;
            }
            table {
                background-color: white;
                border-radius: 10px;
                overflow: hidden;
                width: 100%;
                text-align: center;
                border-collapse: collapse;
            }
            th {
                background-color: #1e293b;
                color: white;
                text-align: center;
                padding: 10px;
            }
            td {
                padding: 10px;
                border: 1px solid #dee2e6;
                height: 40px;
            }

            /* Màu trạng thái */
            .work {
                background-color: #16a34a !important;
            }
            .off {
                background-color: #dc2626 !important;
            }
            .sunday {
                background-color: #6b7280 !important;
            }

            /* Legend */
            .legend span {
                display: inline-block;
                padding: 6px 12px;
                border-radius: 4px;
                margin-right: 10px;
                color: white;
                font-size: 14px;
            }
            .legend .work {
                background-color: #16a34a;
            }
            .legend .off {
                background-color: #dc2626;
            }
            .legend .sunday {
                background-color: #6b7280;
            }
        </style>
    </head>
    <body>

        <!-- Sidebar -->
        <div class="sidebar">
            <div>
                <h5>${sessionScope.user.name}</h5>
                <ul class="nav flex-column">
                    <li class="nav-item"><a href="userInformation" class="nav-link">Thông tin cá nhân</a></li>
                        <c:if test="${sessionScope.user.roleID == 3 || sessionScope.user.roleID == 4}">
                        <li class="nav-item"><a href="userApplication" class="nav-link">Đơn của tôi</a></li>
                        </c:if>
                        <c:if test="${sessionScope.user.roleID == 2 || sessionScope.user.roleID == 3}">
                        <li class="nav-item"><a href="managerRequest" class="nav-link">Quản lý đơn</a></li>
                        </c:if>
                        <c:if test="${sessionScope.user.roleID == 2}">
                        <li class="nav-item"><a href="agenda" class="nav-link active fw-bold">📅 Lịch làm việc</a></li>
                        </c:if>
                </ul>
            </div>
            <div class="logout-btn">
                <a href="logout" class="btn btn-danger w-100">Đăng xuất</a>
            </div>
        </div>

        <!-- Main content -->
        <div class="main-content">
            <div class="container">

                <h4 class="fw-bold mb-3">📅 Lịch làm việc của nhân viên trong phòng</h4>

                <div class="legend mb-3">
                    <span class="work">Đi làm</span>
                    <span class="off">Nghỉ phép</span>
                    <span class="sunday">Chủ nhật</span>
                </div>

                <div class="d-flex justify-content-between align-items-center mb-3">
                    <form action="agenda" method="get" class="m-0">
                        <input type="hidden" name="weekOffset" value="${weekOffset - 1}">
                        <button class="btn btn-outline-primary">&laquo; Tuần trước</button>
                    </form>

                    <h5 class="fw-bold mb-0">
                        Tuần ${currentWeek} (${startDate.dayOfMonth}/${startDate.monthValue} - ${endDate.dayOfMonth}/${endDate.monthValue})
                    </h5>

                    <form action="agenda" method="get" class="m-0">
                        <input type="hidden" name="weekOffset" value="${weekOffset + 1}">
                        <button class="btn btn-outline-primary">Tuần sau &raquo;</button>
                    </form>
                </div>

                <table class="table table-bordered align-middle text-center">
                    <thead>
                        <tr>
                            <th>Nhân viên</th>
                                <c:forEach var="d" items="${dates}">
                                <th>
                                    <c:choose>
                                        <c:when test="${d.dayOfWeek.value == 7}">CN</c:when>
                                        <c:otherwise>T${d.dayOfWeek.value}</c:otherwise>
                                    </c:choose>
                                    <br>${d.dayOfMonth}/${d.monthValue}
                                </th>
                            </c:forEach>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="u" items="${users}">
                            <tr>
                                <td>
                                    ${u.Name}
                                    <c:if test="${not empty u.RoleName}">
                                        <br><span class="text-muted small">(${u.RoleName})</span>
                                    </c:if>
                                </td>
                                <c:forEach var="d" items="${dates}">
                                    <c:set var="status" value="${attendanceMap[u.UserID][d]}" />
                                    <td class="${status}"></td>
                                </c:forEach>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

    </body>
</html>
