<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Đơn của tôi</title>
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

            .logout-btn {
                margin-top: 20px;
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
            }

            th {
                background-color: #1e293b;
                color: white;
            }

            .status-pending {
                color: #f59e0b;
                font-weight: bold;
            }

            .status-accepted {
                color: #16a34a;
                font-weight: bold;
            }

            .status-rejected {
                color: #dc2626;
                font-weight: bold;
            }
        </style>
    </head>
    <body>

        <!-- Sidebar -->
        <div class="sidebar">
            <div>
                <h5>User: ${sessionScope.user.name}</h5>
                <ul class="nav flex-column">
                    <li class="nav-item"><a href="userInformation" class="nav-link">Thông tin cá nhân</a></li>

                    <c:if test="${sessionScope.user.roleID == 3 || sessionScope.user.roleID == 4}">
                        <li class="nav-item"><a href="userApplication" class="nav-link active">Đơn của tôi</a></li>
                        </c:if>

                    <c:if test="${sessionScope.user.roleID == 2 || sessionScope.user.roleID == 3}">
                        <li class="nav-item"><a href="managerRequest" class="nav-link">Quản lý đơn</a></li>
                        </c:if>
                </ul>
            </div>
            <div class="logout-btn">
                <a href="logout" class="btn btn-danger w-100">Đăng xuất</a>
            </div>
        </div>

        <!-- Nội dung chính -->
        <div class="main-content">
            <div class="container">
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h3>Danh sách đơn của tôi</h3>
                    <a href="createApplication.jsp" class="btn btn-primary">+ Tạo đơn mới</a>
                </div>

                <!-- Form tìm kiếm -->
                <form action="userApplication" method="get" class="d-flex mb-4" style="max-width: 500px;">
                    <input type="text" name="keyword" class="form-control me-2" placeholder="Nhập mã đơn hoặc lý do..." value="${param.keyword}">
                    <button type="submit" class="btn btn-secondary">Tìm kiếm</button>
                </form>

                <!-- Nếu không có đơn -->
                <c:if test="${empty listRequest}">
                    <div class="alert alert-info">Bạn chưa có đơn nào.</div>
                </c:if>

                <!-- Bảng danh sách đơn -->
                <c:if test="${not empty listRequest}">
                    <table class="table table-bordered align-middle text-center">
                        <thead>
                            <tr>
                                <th>Mã đơn</th>
                                <th>Người tạo</th>
                                <th>Phòng ban</th>
                                <th>Vai trò</th>
                                <th>Từ ngày</th>
                                <th>Đến ngày</th>
                                <th>Lý do</th>
                                <th>Trạng thái</th>
                                <th>Người duyệt</th>
                                <th>Ngày duyệt</th>
                                <th>Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="r" items="${listRequest}">
                                <tr>
                                    <td>${r.reqID}</td>
                                    <td>${r.name}</td>
                                    <td>${r.depName}</td>
                                    <td>${r.roleName}</td>
                                    <td>${r.from}</td>
                                    <td>${r.to}</td>
                                    <td>${r.reason}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${r.statusName == 'Pending'}">
                                                <span class="status-pending">Đang chờ</span>
                                            </c:when>
                                            <c:when test="${r.statusName == 'Accepted'}">
                                                <span class="status-accepted">Đã duyệt</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="status-rejected">Từ chối</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:if test="${r.approvedName != null}">
                                            ${r.approvedName}
                                        </c:if>
                                        <c:if test="${r.approvedName == null}">
                                            -
                                        </c:if>
                                    </td>

                                    <td>
                                        <c:if test="${r.approvedTime != null}">
                                            ${r.approvedTime}
                                        </c:if>
                                        <c:if test="${r.approvedTime == null}">
                                            -
                                        </c:if>
                                    </td>
                                    <td>
                                        <a href="UserEditApplicationServlet?action=edit&id=${r.reqID}" class="btn btn-warning btn-sm">Sửa</a>
                                        <a href="UserEditApplicationServlet?action=delete&id=${r.reqID}" 
                                           class="btn btn-danger btn-sm"
                                           onclick="return confirm('Bạn có chắc muốn xoá đơn này không?');">
                                            Xoá
                                        </a>

                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </div>
        </div>
    </body>
</html>
