<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>
        <c:choose>
            <c:when test="${user != null}">Sửa người dùng</c:when>
            <c:otherwise>Thêm người dùng</c:otherwise>
        </c:choose>
    </title>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">

    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            margin: 30px;
            background-color: #f9fafb;
        }
        form {
            width: 520px;
            margin: auto;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 12px rgba(0,0,0,0.1);
        }
        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }
        label {
            font-weight: 600;
            margin-top: 12px;
        }
        input[type="text"], input[type="password"], input[type="email"], select {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            border-radius: 5px;
            border: 1px solid #ccc;
        }
        .btn {
            margin-top: 18px;
            padding: 10px 20px;
            border-radius: 6px;
            font-weight: 600;
        }
        .save {
            background-color: #28a745;
            color: white;
        }
        .cancel {
            background-color: #6c757d;
            color: white;
            margin-left: 10px;
        }
    </style>
</head>

<body>
    <h2>
        <c:choose>
            <c:when test="${user != null}">Chỉnh sửa người dùng</c:when>
            <c:otherwise>Thêm người dùng mới</c:otherwise>
        </c:choose>
    </h2>

    <!-- Trỏ đến servlet adminUserEdit -->
    <form action="adminUserEdit" method="post">
        <input type="hidden" name="action" value="${user != null ? 'edit' : 'add'}" />

        <c:if test="${user != null}">
            <input type="hidden" name="id" value="${user.userID}" />
        </c:if>

        <!-- Username -->
        <label>Tên đăng nhập:</label>
        <input type="text" name="username" required
               value="${user != null ? user.username : ''}" />

        <!-- Password -->
        <label>Mật khẩu:</label>
        <input type="password" name="password" required
               value="${user != null ? user.password : ''}" />

        <!-- Display Name -->
        <label>Tên hiển thị:</label>
        <input type="text" name="display" required
               value="${user != null ? user.display : ''}" />

        <!-- Email -->
        <label>Email:</label>
        <input type="email" name="email" required
               value="${user != null ? user.email : ''}" />

        <!-- Phone -->
        <label>Số điện thoại:</label>
        <input type="text" name="phone" required
               value="${user != null ? user.phone : ''}" />

        <!-- Department -->
        <label>Phòng ban:</label>
        <select name="department" required>
            <c:forEach var="d" items="${departments}">
                <option value="${d.depID}" 
                        <c:if test="${user != null && user.department.depID == d.depID}">selected</c:if>>
                    ${d.depName}
                </option>
            </c:forEach>
        </select>

        <!-- Role -->
        <label>Vai trò:</label>
        <select name="role" required>
            <c:forEach var="r" items="${roles}">
                <option value="${r.roleID}" 
                        <c:if test="${user != null && user.role.roleID == r.roleID}">selected</c:if>>
                    ${r.roleName}
                </option>
            </c:forEach>
        </select>

        <!-- Active -->
        <label class="mt-3">
            <input type="checkbox" name="active"
                   <c:if test="${user != null && user.active}">checked</c:if> />
            Đang hoạt động
        </label>

        <div class="text-center">
            <button type="submit" class="btn save">💾 Lưu</button>
            <a href="adminUserManager" class="btn cancel">Hủy</a>
        </div>
    </form>

</body>
</html>
