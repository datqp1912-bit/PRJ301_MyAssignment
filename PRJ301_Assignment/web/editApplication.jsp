<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Chỉnh sửa đơn</title>
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
            .form-container {
                max-width: 650px;
                margin: auto;
                background: white;
                padding: 25px 30px;
                border-radius: 10px;
                box-shadow: 0 0 8px rgba(0,0,0,0.1);
            }
            .form-container h3 {
                text-align: center;
                margin-bottom: 25px;
            }
            input[readonly] {
                background-color: #e9ecef;
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
                        <li class="nav-item"><a href="userApplication" class="nav-link">Đơn của tôi</a></li>
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

        <!-- Main Content -->
        <div class="main-content">
            <div class="form-container">
                <h3>Chỉnh sửa đơn</h3>

                <c:if test="${not empty error}">
                    <div class="alert alert-danger">${error}</div>
                </c:if>

                <form action="UserEditApplicationServlet" method="post" onsubmit="return validateDates()">
                    <input type="hidden" name="action" value="edit">
                    <input type="hidden" name="id" value="${req.reqID}">

                    <div class="mb-3">
                        <label class="form-label">Tên người tạo</label>
                        <input type="text" class="form-control" value="${sessionScope.user.name}" readonly>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Phòng ban</label>
                        <input type="text" class="form-control" value="${sessionScope.user.depName}" readonly>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Chức vụ</label>
                        <input type="text" class="form-control" value="${sessionScope.user.roleName}" readonly>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Từ ngày</label>
                        <input type="date" class="form-control" name="from" id="from"
                               value="${req.from}" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Đến ngày</label>
                        <input type="date" class="form-control" name="to" id="to"
                               value="${req.to}" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Lý do</label>
                        <textarea class="form-control" name="reason" rows="3" required>${req.reason}</textarea>
                    </div>

                    <div class="text-center">
                        <button type="submit" class="btn btn-success">Cập nhật</button>
                        <a href="userApplication" class="btn btn-secondary ms-2">Hủy</a>
                    </div>
                </form>
            </div>
        </div>

        <script>
            function validateDates() {
                const fromDate = new Date(document.getElementById("from").value);
                const toDate = new Date(document.getElementById("to").value);
                if (toDate < fromDate) {
                    alert("❌ Ngày kết thúc không được nhỏ hơn ngày bắt đầu!");
                    return false;
                }
                return true;
            }
        </script>

    </body>
</html>
