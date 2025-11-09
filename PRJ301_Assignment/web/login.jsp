<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>  
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Đăng nhập</title>

    </head>
    <body>
        <div class="login-box">
            <h2>Đăng nhập</h2>
            <!-- Gửi dữ liệu đến servlet LoginServlet qua phương thức POST -->
            <form action="login" method="post">
                <label for="username">Tên đăng nhập</label>
                <input type="text" id="username" name="username" placeholder="Username" required>

                <label for="password">Mật khẩu</label>
                <input type="password" id="password" name="password" placeholder="Password" required>

                <div class="remember">
                    <input type="checkbox" id="remember">
                    <label for="remember">Remember me</label>
                </div>

                <button type="submit">Login</button>
            </form>

            <c:if test="${not empty error}">
                <center><h2 style="color: red">${error}</h2></center>
            </c:if>
        </div>
    </body>

</html>
<style>
    body {
        margin: 0;
        padding: 0;
        height: 100vh;
        display: flex;
        justify-content: center;
        align-items: center;
        background: url('img/Asm.jpg') no-repeat center center/cover;
        font-family: Arial, sans-serif;
    }

    .login-box {
        background: white;
        padding: 40px;
        border-radius: 10px;
        box-shadow: 0 5px 20px rgba(0,0,0,0.2);
        text-align: center; /* căn chữ Login vào giữa */
    }

    .login-box h2 {
        margin-bottom: 20px;
    }

    .login-box input[type="text"],
    .login-box input[type="password"] {
        width: 100%;
        padding: 10px;
        margin: 10px 0;
        border-radius: 5px;
        border: 1px solid #ccc;
    }

    .login-box button {
        width: 100%;
        padding: 10px;
        background-color: #5a0de0;
        color: white;
        border: none;
        border-radius: 5px;
        cursor: pointer;
    }

    .login-box a {
        display: block;
        margin-top: 15px;
        color: #5a0de0;
        text-decoration: none;
    }

</style>

