<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Thông tin cá nhân</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
        <style>
            body {
                margin: 0;
                padding: 0;
                font-family: 'Segoe UI', sans-serif;
                display: flex;
                height: 100vh;
                overflow: hidden; /* Ngăn cuộn toàn trang */
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
                position: fixed; /* Giữ cố định khi cuộn */
                top: 0;
                left: 0;
                bottom: 0;
            }

            .sidebar h5 {
                margin-bottom: 20px;
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

            /* Phần nội dung chính */
            .main-content {
                margin-left: 250px; /* chừa chỗ cho sidebar */
                flex: 1;
                background-color: #f8f9fa;
                padding: 30px;
                height: 100vh;
                overflow-y: auto; /* chỉ phần này cuộn */
            }
        </style>
    </head>
    <body>
        <!-- Sidebar cố định -->
        <div class="sidebar">
            <div>
                <h5>User: ${sessionScope.account.name}</h5>
                <ul class="nav flex-column">
                    <li class="nav-item"><a href="userInformation" class="nav-link">Thông tin cá nhân</a></li>

                    <c:if test="${sessionScope.user.roleID == 3 || sessionScope.user.roleID == 4}">
                        <li class="nav-item"><a href="userApplication" class="nav-link">Đơn của tôi</a></li>
                        </c:if>

                    <c:if test="${sessionScope.user.roleID == 2 || sessionScope.user.roleID == 3}">
                        <li class="nav-item"><a href="managerRequest" class="nav-link">Quản lý đơn</a></li>
                        </c:if>

                </ul>
            </div>
            <form action="logout" method="post" class="logout-btn">
                <button class="btn btn-danger w-100">Đăng xuất</button>
            </form>
        </div>

        <!-- Nội dung chính -->
        <div class="main-content">
            <h3 class="mb-4">Thông tin của tôi</h3>

            <div class="card p-4 shadow-sm mx-auto" style="max-width: 700px;">
                <form action="userInformation" method="post">
                    <input type="hidden" name="action" value="edit">

                    <div class="mb-3">
                        <label class="form-label">Tên người dùng</label>
                        <input type="text" name="name" value="${sessionScope.account.name}" class="form-control">
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Tên đăng nhập</label>
                        <input type="text" name="username" value="${sessionScope.account.username}" class="form-control" readonly>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Mật khẩu</label>
                        <input type="password" name="password" value="${sessionScope.account.password}" class="form-control">
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Email</label>
                        <input type="email" name="email" value="${sessionScope.account.email}" class="form-control">
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Số điện thoại</label>
                        <input type="text" name="phone" value="${sessionScope.account.phone}" class="form-control">
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Phòng ban</label>
                        <input type="text" value="${sessionScope.account.depName}" class="form-control bg-light" readonly>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Chức vụ</label>
                        <input type="text" value="${sessionScope.account.roleName}" class="form-control bg-light" readonly>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Trạng thái</label>
                        <input type="text" value="${sessionScope.account.active ? 'Đang hoạt động' : 'Ngưng hoạt động'}" class="form-control bg-light" readonly>
                    </div>

                    <c:if test="${not empty message}">
                        <div class="alert alert-success">${message}</div>
                    </c:if>

                    <div class="d-flex justify-content-between">
                        <button type="submit" class="btn btn-success px-4">Lưu lại</button>
                        <a href="userInformation" class="btn btn-secondary px-4">Hủy bỏ</a>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
