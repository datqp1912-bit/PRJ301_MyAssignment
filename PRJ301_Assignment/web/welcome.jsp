<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Hệ thống quản lý đơn xin nghỉ phép</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: "Segoe UI", Arial, sans-serif;
            background: linear-gradient(135deg, #2980b9, #6dd5fa, #ffffff);
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .container {
            background: white;
            width: 480px;
            padding: 40px 50px;
            border-radius: 16px;
            text-align: center;
            box-shadow: 0 0 25px rgba(0,0,0,0.15);
            animation: fadeIn 0.8s ease-in-out;
        }

        h1 {
            font-size: 22px;
            color: #2c3e50;
            margin-bottom: 15px;
        }

        p {
            color: #555;
            margin-bottom: 30px;
            font-size: 15px;
        }

        a {
            display: inline-block;
            padding: 10px 25px;
            background: #3498db;
            color: white;
            font-weight: bold;
            text-decoration: none;
            border-radius: 8px;
            transition: all 0.3s ease;
        }

        a:hover {
            background: #2980b9;
            transform: scale(1.05);
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(10px); }
            to { opacity: 1; transform: translateY(0); }
        }

        footer {
            margin-top: 25px;
            font-size: 13px;
            color: #888;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Hệ thống quản lý đơn xin nghỉ phép</h1>
        <a href="login.jsp">Đăng nhập</a>
    </div>
</body>
</html>
