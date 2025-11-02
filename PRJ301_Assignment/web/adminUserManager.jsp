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
            }
            .sidebar {
                width: 250px;
                background-color: #1e293b;
                color: white;
                display: flex;
                flex-direction: column;
                justify-content: space-between;
                padding: 20px;
            }
            .sidebar h5 {
                margin-bottom: 10px;
            }
            .sidebar .nav-link {
                color: white;
                transition: background 0.3s;
            }
            .sidebar .nav-link:hover {
                background-color: rgba(255, 255, 255, 0.2);
                border-radius: 5px;
            }
            .logout-btn {
                margin-top: auto;
            }
            .table th, .table td {
                vertical-align: middle;
                text-align: center;
            }
        </style>
    </head>

    <body>
        <div class="d-flex" style="height:100vh;">
            <!-- Sidebar -->
            <div class="sidebar">
                <div>
                    <h5>Admin: ${sessionScope.account.username}</h5>
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
            <div class="p-4 flex-grow-1 bg-light">
                <h3 class="mb-4">Danh Sách Người Dùng</h3>

                <!-- THÊM NGƯỜI DÙNG + TÌM KIẾM -->
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <!-- ✅ Gọi servlet adminUserEdit?action=add -->
                    <a href="adminUserEdit?action=add" class="btn btn-success">
                        Thêm người dùng mới
                    </a>

                    <form action="adminUserManager" method="get" class="d-flex" style="width: 50%;">
                        <input type="text" 
                               name="keyword" 
                               class="form-control me-2" 
                               placeholder="Nhập tên tài khoản hoặc hiển thị..." 
                               value="${param.keyword}">
                        <button class="btn btn-primary">Tìm kiếm</button>
                    </form>
                </div>

                <!-- Bảng người dùng -->
                <table class="table table-bordered table-striped align-middle">
                    <thead class="table-primary text-center">
                        <tr>
                            <th>Mã</th>
                            <th>Tài khoản</th>
                            <th>Mật khẩu</th>
                            <th>Hiển thị</th>
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
                                <td>${u.password}</td>
                                <td>${u.display}</td>
                                <td>${u.department.depName}</td>
                                <td>${u.role.roleName}</td>
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
                                    <!-- ✅ Nút Sửa -->
                                    <a href="adminUserEdit?action=edit&id=${u.userID}" class="btn btn-warning btn-sm me-1">Sửa</a>
                                    
                                    <!-- ✅ Nút Xóa -->
                                    <a href="adminUserEdit?action=delete&id=${u.userID}" 
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
        </div>
    </body>
</html>
