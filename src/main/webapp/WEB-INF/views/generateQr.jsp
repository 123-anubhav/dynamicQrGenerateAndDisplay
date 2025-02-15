<%@ page import="java.io.*, javax.imageio.ImageIO, java.awt.image.BufferedImage" %>
<%@ page import="com.google.zxing.*, com.google.zxing.common.BitMatrix, com.google.zxing.qrcode.QRCodeWriter" %>
<%@ page import="com.google.zxing.client.j2se.MatrixToImageWriter" %>

<%
    String qrText = request.getParameter("inputData");

    if (qrText != null && !qrText.trim().isEmpty()) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(qrText, BarcodeFormat.QR_CODE, 250, 250);

        response.setContentType("image/png");
        OutputStream outputStream = response.getOutputStream();
        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        ImageIO.write(qrImage, "png", outputStream);
        outputStream.flush();
        outputStream.close();
    }
%>
