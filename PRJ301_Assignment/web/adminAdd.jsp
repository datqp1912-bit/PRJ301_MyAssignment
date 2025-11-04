<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Thêm người dùng mới</title>
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
                <h3 class="mb-4 text-center">Thêm người dùng mới</h3>

                <div class="card p-4 shadow-sm mx-auto" style="max-width: 600px;">
                    <!-- Gửi form về servlet AdminEditServlet -->
                    <form action="adminEdit?action=add" method="post">

                        <div class="mb-3">
                            <label class="form-label">Tên đầy đủ</label>
                            <input type="text" name="name" class="form-control" required>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Tên đăng nhập</label>
                            <input type="text" name="username" class="form-control" required>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Mật khẩu</label>
                            <input type="password" name="password" class="form-control" required>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Email</label>
                            <input type="email" name="email" class="form-control">
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Số điện thoại</label>
                            <input type="text" name="phone" class="form-control">
                        </div>

                        <div class="mb-3">
                            <label class="control-label">Phòng ban</label>
                            <select name="depID" class="form-select" required>
                                <c:forEach var="d" items="${departments}">
                                    <option value="${d.depID}">${d.depName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label class="control-label">Chức vụ</label>
                            <select name="roleID" class="form-select" required>
                                <c:forEach var="r" items="${roles}">
                                    <option value="${r.roleID}">${r.roleName}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Trạng thái</label>
                            <select name="active" class="form-select">
                                <option value="true" selected>Hoạt động</option>
                                <option value="false">Ngưng hoạt động</option>
                            </select>
                        </div>

                        <div class="d-flex justify-content-between">
                            <button type="submit" class="btn btn-success px-4">Lưu lại</button>
                            <a href="adminUserManager" class="btn btn-secondary px-4">Hủy bỏ</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
