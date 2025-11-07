<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Quản lý đơn phòng ban</title>
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
            top: 0; left: 0; bottom: 0;
        }
        .sidebar .nav-link { color: white; display: block; padding: 10px 0; text-decoration: none; }
        .sidebar .nav-link:hover { background-color: rgba(255,255,255,0.2); border-radius: 5px; }
        .main-content { margin-left: 250px; flex: 1; background-color: #f8f9fa; padding: 30px; overflow-y: auto; }
        table { background-color: white; border-radius: 10px; overflow: hidden; }
        th { background-color: #1e293b; color: white; }
        .status-pending { color: #f59e0b; font-weight: bold; }
        .status-accepted { color: #16a34a; font-weight: bold; }
        .status-rejected { color: #dc2626; font-weight: bold; }
        .modal-content { border-radius: 12px; }
    </style>
</head>
<body>

    <!-- Sidebar -->
    <div class="sidebar">
            <div>
                <h5>User: ${sessionScope.account.name}</h5>
                <ul class="nav flex-column">
                    <li class="nav-item"><a href="userInformation" class="nav-link">Thông tin cá nhân</a></li>

                    <c:if test="${sessionScope.user.roleID == 3 || sessionScope.user.roleID == 4}">
                        <li class="nav-item"><a href="userApplication" class="nav-link">Đơn của tôi</a></li>
                        </c:if>

                    <c:if test="${sessionScope.user.roleID == 2 || sessionScope.user.roleID == 3}">
                        <li class="nav-item"><a href="manageRequest" class="nav-link">Quản lý đơn</a></li>
                        </c:if>

                </ul>
            </div>
            <form action="logout" method="post" class="logout-btn">
                <button class="btn btn-danger w-100">Đăng xuất</button>
            </form>
        </div>

    <!-- Main -->
    <div class="main-content container">
        <h3 class="mb-4">Danh sách đơn trong phòng ban</h3>

        <c:if test="${empty listRequest}">
            <div class="alert alert-info">Không có đơn nào trong phòng ban của bạn.</div>
        </c:if>

        <c:if test="${not empty listRequest}">
            <table class="table table-bordered text-center align-middle">
                <thead>
                    <tr>
                        <th>Mã đơn</th>
                        <th>Người tạo</th>
                        <th>Chức vụ</th>
                        <th>Từ ngày</th>
                        <th>Đến ngày</th>
                        <th>Lý do</th>
                        <th>Trạng thái</th>
                        <th>Người duyệt</th>
                        <th>Ngày duyệt</th>
                        <th>Chi tiết</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="r" items="${listRequest}">
                        <tr>
                            <td>${r.reqID}</td>
                            <td>${r.name}</td>
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
                            <td>${r.approvedName != null ? r.approvedName : '-'}</td>
                            <td>${r.approvedTime != null ? r.approvedTime : '-'}</td>
                            <td>
                                <button class="btn btn-primary btn-sm" 
                                        data-bs-toggle="modal" 
                                        data-bs-target="#detailModal${r.reqID}">
                                    Chi tiết
                                </button>

                                <!-- Modal Chi tiết -->
                                <div class="modal fade" id="detailModal${r.reqID}" tabindex="-1">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header bg-dark text-white">
                                                <h5 class="modal-title">Chi tiết đơn #${r.reqID}</h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                            </div>
                                            <div class="modal-body text-start">
                                                <p><strong>Người tạo:</strong> ${r.name}</p>
                                                <p><strong>Chức vụ:</strong> ${r.roleName}</p>
                                                <p><strong>Từ ngày:</strong> ${r.from}</p>
                                                <p><strong>Đến ngày:</strong> ${r.to}</p>
                                                <p><strong>Lý do:</strong> ${r.reason}</p>
                                                <p><strong>Trạng thái hiện tại:</strong> ${r.statusName}</p>

                                                <c:if test="${r.statusName == 'Pending' && sessionScope.user.userID != r.createBy}">
                                                    <form action="managerRequest" method="post" class="mt-3">
                                                        <input type="hidden" name="reqID" value="${r.reqID}">
                                                        <button type="submit" name="action" value="approve" class="btn btn-success me-2">✅ Đồng ý</button>
                                                        <button type="submit" name="action" value="reject" class="btn btn-danger">❌ Từ chối</button>
                                                    </form>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- End Modal -->
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
