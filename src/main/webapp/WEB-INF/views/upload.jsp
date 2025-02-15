<!-- src/main/webapp/WEB-INF/views/upload.jsp -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Image Upload with QR Code</title>
</head>
<body>

<h2>Upload Image and Generate QR Code</h2>

<form action="/upload" method="post" enctype="multipart/form-data">
    <input type="file" name="image" accept="image/*" required>
    <button type="submit">Upload and Generate QR Code</button>
</form>

<c:if test="${not empty qrCodeUrl}">
    <h3>Generated QR Code:</h3>
    <img src="${qrCodeUrl}" alt="QR Code" />
    <p>QR Code URL: <a href="${qrCodeUrl}" target="_blank">${qrCodeUrl}</a></p>
</c:if>

</body>
</html>
