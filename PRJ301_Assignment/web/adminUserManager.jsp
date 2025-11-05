<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Quản lý người dùng</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: 'Segoe UI', sans-serif;
            display: flex;
            height: 100vh;
            overflow: hidden; /* ngăn cuộn toàn trang */
        }

        /* Sidebar cố định bên trái */
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
            margin-bottom: 10px;
        }

        .sidebar .nav-link {
            color: white;
            transition: background 0.3s;
            text-decoration: none;
            display: block;
            padding: 8px 0;
        }

        .sidebar .nav-link:hover {
            background-color: rgba(255, 255, 255, 0.2);
            border-radius: 5px;
        }

        .logout-btn {
            margin-top: auto;
        }

        /* Phần nội dung chính cuộn riêng */
        .main-content {
            margin-left: 250px; /* chừa chỗ cho sidebar */
            flex: 1;
            background-color: #f8f9fa;
            padding: 30px;
            height: 100vh;
            overflow-y: auto; /* chỉ phần này cuộn */
        }

        .table th, .table td {
            vertical-align: middle;
            text-align: center;
        }
    </style>
</head>
<body>
    <!-- Sidebar -->
    <div class="sidebar">
        <div>
            <h5>User: ${sessionScope.user.getName()}</h5>
            <hr style="border-color: rgba(255,255,255,0.3);">
            <ul class="nav flex-column">
                <li class="nav-item">
                    <a href="adminUserManager" class="nav-link">Quản lý người dùng</a>
                </li>
            </ul>
        </div>
        <form action="logout" method="post" class="logout-btn">
            <button class="btn btn-danger w-100">Đăng xuất</button>
        </form>
    </div>

    <!-- Main content -->
    <div class="main-content">
        <h3 class="mb-4">Danh Sách Người Dùng</h3>

        <!-- Nút thêm người dùng + form tìm kiếm -->
        <div class="d-flex justify-content-between align-items-center mb-3">
            <a href="adminEdit?action=add" class="btn btn-primary">Thêm người dùng mới</a>

            <form action="adminUserManager" method="get" class="d-flex" style="width: 50%;">
                <input type="text"
                       name="keyword"
                       class="form-control me-2"
                       placeholder="Nhập tên người dùng..."
                       value="${keyword != null ? keyword : ''}">
                <button class="btn btn-primary">Tìm kiếm</button>
            </form>
        </div>

        <!-- Bảng người dùng -->
        <table class="table table-bordered table-striped align-middle">
            <thead class="table-primary text-center">
                <tr>
                    <th>Mã</th>
                    <th>Tài khoản</th>
                    <th>Tên người dùng</th>
                    <th>Email</th>
                    <th>Số điện thoại</th>
                    <th>Phòng ban</th>
                    <th>Chức vụ</th>
                    <th>Trạng thái</th>
                    <th>Thao tác</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="u" items="${users}">
                    <tr>
                        <td>${u.userID}</td>
                        <td>${u.username}</td>
                        <td>${u.name}</td>
                        <td>${u.email}</td>
                        <td>${u.phone}</td>
                        <td>${u.depName}</td>
                        <td>${u.roleName}</td>
                        <td>
                            <c:choose>
                                <c:when test="${u.active}">
                                    <span class="badge bg-success">Hoạt động</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-secondary">Ngưng</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <a href="adminEdit?action=edit&id=${u.userID}" class="btn btn-warning btn-sm me-1">Sửa</a>
                            <a href="adminEdit?action=delete&id=${u.userID}"
                               class="btn btn-danger btn-sm"
                               onclick="return confirm('Bạn có chắc muốn xóa người dùng này không?');">
                                Xóa
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
