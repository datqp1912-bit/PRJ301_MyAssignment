<%-- 
    Document   : register
    Created on : Oct 29, 2025, 7:09:33 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Đăng ký</title>
        <style>
            body {
                font-family: 'Poppins', sans-serif;
                background: url('img/Asm.jpg') no-repeat center center/cover;
                height: 100vh;
                display: flex;
                justify-content: center;
                align-items: center;
                margin: 0;
            }

            .container {
                background-color: white;
                width: 380px;
                border-radius: 12px;
                box-shadow: 0 6px 20px rgba(0, 0, 0, 0.15);
                padding: 40px 30px;
                text-align: center;
            }

            h2 {
                margin-bottom: 25px;
                color: #000;
            }

            .input-group {
                margin-bottom: 15px;
                text-align: left;
            }

            label {
                display: block;
                margin-bottom: 6px;
                font-weight: 500;
            }

            input, select {
                width: 100%;
                padding: 10px;
                border-radius: 6px;
                border: 1px solid #ccc;
                outline: none;
                transition: 0.3s;
                font-size: 14px;
            }

            input:focus, select:focus {
                border-color: #6a0dad;
                box-shadow: 0 0 4px rgba(106, 13, 173, 0.5);
            }

            .btn {
                background-color: #6a0dad;
                color: white;
                border: none;
                padding: 10px;
                width: 100%;
                border-radius: 6px;
                cursor: pointer;
                font-weight: bold;
                transition: 0.3s;
                margin-top: 10px;
            }

            .btn:hover {
                background-color: #550a8a;
            }

            .link {
                margin-top: 15px;
                display: block;
            }

            .link a {
                color: #6a0dad;
                text-decoration: none;
                font-weight: 500;
            }

            .link a:hover {
                text-decoration: underline;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h2>Đăng ký tài khoản</h2>
            <form>
                <div class="input-group">
                    <label for="username">Tên đăng nhập</label>
                    <input type="text" id="username" placeholder="Nhập tên đăng nhập" required>
                </div>

                <div class="input-group">
                    <label for="password">Mật khẩu</label>
                    <input type="password" id="password" placeholder="Nhập mật khẩu" required>
                </div>

                <div class="input-group">
                    <label for="displayName">Tên hiển thị</label>
                    <input type="text" id="displayName" placeholder="Tên bạn muốn hiển thị">
                </div>

                <div class="input-group">
                    <label for="department">Phòng ban</label>
                    <select id="department" required>
                        <option value="">-- Chọn phòng ban --</option>
                        <option>IT</option>
                        <option>Sale</option>
                        <option>QA</option>
                    </select>
                </div>

                <div class="input-group">
                    <label for="role">Vai trò</label>
                    <select id="role" required>
                        <option value="">-- Chọn vai trò --</option>
                        <option>Employee</option>
                        <option>Group Leader</option>
                        <option>Department Manager</option>
                    </select>
                </div>

                <button class="btn" type="submit">Đăng ký</button>

                <div class="link">
                    <span>Đã có tài khoản? <a href="login">Đăng nhập</a></span>
                </div>
            </form>
        </div>
    </body>
</html>
