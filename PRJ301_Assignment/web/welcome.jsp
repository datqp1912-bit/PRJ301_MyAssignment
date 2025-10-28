<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Hệ thống quản lý đơn xin nghỉ phép</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: "Segoe UI", Arial, sans-serif;
        }

        body {
            background-image: url('img/Asm.jpg'); 
            background-size: cover;
            background-position: center;
            background-repeat: no-repeat;
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .welcome-box {
            background-color: white; /* Container màu trắng */
            padding: 80px 70px; /* tăng padding để box to hơn */
            border-radius: 20px;
            box-shadow: 0 0 25px rgba(0,0,0,0.3);
            text-align: center;
            max-width: 600px; /* tăng chiều rộng */
            animation: fadeIn 1s ease-in-out;
        }

        .welcome-box h1 {
            font-size: 32px; /* tăng font chữ */
            color: #2c3e50;
            margin-bottom: 25px;
        }

        .welcome-box p {
            font-size: 18px; /* tăng font chữ */
            color: #34495e;
            margin-bottom: 35px;
        }

        .welcome-box a {
            display: inline-block;
            padding: 14px 30px; /* tăng kích thước button */
            margin: 0 12px;
            background: #3498db;
            color: white;
            text-decoration: none;
            border-radius: 12px;
            font-weight: bold;
            font-size: 16px;
            transition: 0.3s;
        }

        .welcome-box a:hover {
            background: #2980b9;
            transform: scale(1.05);
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(15px); }
            to { opacity: 1; transform: translateY(0); }
        }

        @media screen and (max-width: 500px) {
            .welcome-box {
                padding: 60px 30px; /* tăng padding để box vẫn to trên mobile */
            }
            .welcome-box h1 {
                font-size: 26px;
            }
            .welcome-box p {
                font-size: 16px;
            }
        }
    </style>
</head>
<body>
    <div class="welcome-box">
        <h1>Chào mừng</h1>
        <p>Hệ thống quản lý đơn xin nghỉ phép</p>
        <a href="login.jsp">Đăng nhập</a>
        <a href="register.jsp">Đăng ký</a>
    </div>
</body>
</html>
