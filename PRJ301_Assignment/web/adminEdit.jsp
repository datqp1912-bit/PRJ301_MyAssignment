<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Chỉnh sửa người dùng</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    </head>
    <body>
        <div class="d-flex" style="height:100vh;">
            <!-- Sidebar -->
            <div class="sidebar p-3 bg-dark text-white" style="width:250px;">
                <h5>Admin: ${sessionScope.account.username}</h5>
                <hr>
                <a href="adminUserManager" class="nav-link text-white">Quản lý người dùng</a>
                <form action="logout" method="post" class="mt-auto">
                    <button class="btn btn-danger w-100 mt-3">Đăng xuất</button>
                </form>
            </div>

            <!-- Main content -->
            <div class="flex-grow-1 bg-light p-4">
                <h3 class="text-center mb-4">Chỉnh sửa thông tin người dùng</h3>

                <div class="card shadow-sm p-4 mx-auto" style="max-width: 600px;">
                    <form action="adminEdit" method="post">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="id" value="${user.userID}">

                        <div class="mb-3">
                            <label class="form-label">Tên đầy đủ</label>
                            <input type="text" name="name" class="form-control" value="${user.name}" required>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Tên đăng nhập</label>
                            <input type="text" name="username" class="form-control" value="${user.username}" readonly>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Mật khẩu</label>
                            <input type="password" name="password" class="form-control" value="${user.password}" required>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Email</label>
                            <input type="email" name="email" class="form-control" value="${user.email}">
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Số điện thoại</label>
                            <input type="text" name="phone" class="form-control" value="${user.phone}">
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Phòng ban</label>
                            <select name="depID" class="form-select">
                                <c:forEach var="d" items="${departments}">
                                    <option value="${d.depID}" ${user.depID == d.depID ? 'selected' : ''}>${d.depName}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Chức vụ</label>
                            <select name="roleID" class="form-select">
                                <c:forEach var="r" items="${roles}">
                                    <option value="${r.roleID}" ${user.roleID == r.roleID ? 'selected' : ''}>${r.roleName}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Trạng thái</label>
                            <select name="active" class="form-select">
                                <option value="true" ${user.active ? 'selected' : ''}>Hoạt động</option>
                                <option value="false" ${!user.active ? 'selected' : ''}>Ngưng hoạt động</option>
                            </select>
                        </div>

                        <div class="d-flex justify-content-between">
                            <button type="submit" class="btn btn-success px-4">Lưu thay đổi</button>
                            <a href="adminUserManager" class="btn btn-secondary px-4">Hủy bỏ</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
