<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Quản lý người dùng</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
</head>
<body>
<div class="d-flex" style="height:100vh;">
    <!-- Sidebar -->
    <div class="bg-dark text-white p-3" style="width:250px;">
        <h5>Admin: ${sessionScope.account.username}</h5>
        <hr>
        <ul class="nav flex-column">
            <li class="nav-item">
                <a href="adminUserManager" class="nav-link text-white">Quản lý người dùng</a>
            </li>
        </ul>
        <form action="logout" method="post">
            <button class="btn btn-danger w-100 mt-3">Đăng xuất</button>
        </form>
    </div>

    <!-- Main content -->
    <div class="p-4 flex-grow-1">
        <h3>Danh Sách Người Dùng</h3>
        <a href="addUser.jsp" class="btn btn-success mb-3">Thêm người dùng mới</a>

        <table class="table table-bordered table-striped align-middle">
            <thead class="table-dark">
                <tr>
                    <th>Mã</th>
                    <th>Tài khoản</th>
                    <th>Mật khẩu</th>
                    <th>Hiển thị</th>
                    <th>Phòng ban</th>
                    <th>Chức vụ</th>
                    <th>Trạng thái</th>
                    <th>Chỉnh sửa</th>
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
                            <a href="editUser?id=${u.userID}" class="btn btn-warning btn-sm">Sửa</a>
                            <a href="deleteUser?id=${u.userID}" class="btn btn-danger btn-sm">Xóa</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
