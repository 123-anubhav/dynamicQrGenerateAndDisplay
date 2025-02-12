<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>QR Code Generator</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .container {
            display: flex;
            justify-content: space-between;
            width: 60%;
            padding: 20px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
        }

        .left {
            width: 50%;
            padding-right: 20px;
        }

        .right {
            width: 50%;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        img {
            width: 250px;
            height: 250px;
            border: 2px solid #333;
            padding: 5px;
        }

        button {
            margin-top: 10px;
            padding: 8px 15px;
            font-size: 16px;
            background-color: #28a745;
            color: white;
            border: none;
            cursor: pointer;
        }

        button:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>

    <div class="container">
        <!-- Left: Input Form -->
        <div class="left">
            <h1>QR Code Generator</h1>
            <form action="<%= request.getContextPath() %>/generate-qr" method="post">
                <label>Enter Text for QR Code:</label>
                <input type="text" name="inputData" required>
                <button type="submit">Generate QR</button>
            </form>
        </div>

        <!-- Right: Display QR Code -->
        <div class="right">
            <% String qrImage = (String) request.getAttribute("qrImage"); %>
            <% if (qrImage != null && !qrImage.isEmpty()) { %>
                <img src="<%= request.getContextPath() %>/qr/<%= qrImage %>" alt="QR Code Image">
            <% } %>
        </div>
    </div>

</body>
</html>
