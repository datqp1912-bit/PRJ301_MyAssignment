<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Tạo đơn mới</title>
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
                        
                        <c:if test="${sessionScope.user.roleID == 2}">
                        <li class="nav-item"><a href="agenda" class="nav-link active" style="font-weight:bold;">📅 Lịch làm việc</a></li>
                        </c:if>
                </ul>
            </div>
            <div class="logout-btn">
                <a href="logout" class="btn btn-danger w-100">Đăng xuất</a>
            </div>
        </div>

        <!-- Nội dung chính -->
        <div class="main-content">
            <div class="form-container">
                <h3>Tạo đơn mới</h3>

                <c:if test="${not empty error}">
                    <div class="alert alert-danger">${error}</div>
                </c:if>

                <!-- Form tạo đơn -->
                <form action="UserEditApplicationServlet" method="post" onsubmit="return validateDates()">
                    <input type="hidden" name="action" value="create">

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
                        <input type="date" class="form-control" name="from" id="from" required
                               onchange="document.getElementById('to').min = this.value;">
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Đến ngày</label>
                        <input type="date" class="form-control" name="to" id="to" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Lý do</label>
                        <textarea class="form-control" name="reason" rows="3" required></textarea>
                    </div>

                    <div class="text-center">
                        <button type="submit" class="btn btn-primary">Tạo đơn</button>
                        <a href="userApplication" class="btn btn-secondary ms-2">Hủy</a>
                    </div>
                </form>
            </div>
        </div>

        <!-- JS kiểm tra ngày -->
        <script>
            window.onload = function () {
                const fromInput = document.getElementById("from");
                const toInput = document.getElementById("to");

                const today = new Date();
                const tomorrow = new Date(today);
                tomorrow.setDate(today.getDate() + 1);

                const minDate = tomorrow.toISOString().split("T")[0];
                fromInput.setAttribute("min", minDate);
                toInput.setAttribute("min", minDate);

                // Validate ngày bắt đầu và kết thúc
                window.validateDates = function () {
                    const fromDate = new Date(fromInput.value);
                    const toDate = new Date(toInput.value);

                    if (toDate < fromDate) {
                        alert("❌ Ngày kết thúc không được nhỏ hơn ngày bắt đầu!");
                        return false;
                    }
                    return true;
                };
            };
        </script>



    </body>
</html>
