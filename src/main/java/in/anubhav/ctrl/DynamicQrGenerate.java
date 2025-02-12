package in.anubhav.ctrl;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import jakarta.servlet.http.HttpServletResponse;

import com.google.zxing.client.j2se.MatrixToImageWriter;

import java.nio.file.Files;

@Controller
public class DynamicQrGenerate {
    
    private static final String QRcode_Path = "D:\\jitendra\\generateQr\\";

    static {
        File qrDirectory = new File(QRcode_Path);
        if (!qrDirectory.exists()) {
            qrDirectory.mkdirs(); // Create directory if not exists
        }
    }

    @GetMapping("/qr-page")
    public String qrPage() {
        return "qr"; // Return JSP page
    }

    @PostMapping("/generate-qr")
    public String generateQR(@RequestParam("inputData") String inputData, Model model) throws Exception {
        String qrFileName = inputData + "QRCODE.png";
        String qrFilePath = QRcode_Path + qrFileName;

        // Generate QR Code
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix = writer.encode(inputData, BarcodeFormat.QR_CODE, 250, 250);
        Path path = FileSystems.getDefault().getPath(qrFilePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

        // Add QR image name to model to display on JSP
        model.addAttribute("qrImage", qrFileName);
        
        return "qr"; // Returning JSP page with generated QR image
    }

    // Endpoint to serve QR images
    @GetMapping("/qr/{imageName}")
    @ResponseBody
    public void getQrImage(@PathVariable String imageName, HttpServletResponse response) throws Exception {
        File qrFile = new File(QRcode_Path + imageName);
        if (qrFile.exists()) {
            response.setContentType("image/png");
            Files.copy(qrFile.toPath(), response.getOutputStream());
            response.getOutputStream().flush();
        }
    }
}
