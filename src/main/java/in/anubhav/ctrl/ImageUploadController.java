package in.anubhav.ctrl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class ImageUploadController {

    private static final String UPLOAD_DIR = "D:\\jitendra\\images\\"; // Adjust as needed
    private static final String QR_CODE_DIR = "src/main/resources/static/qrcodes/";

    @GetMapping("/")
    public String showUploadForm() {
        return "upload";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("image") MultipartFile file, Model model, HttpServletRequest request) {
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a file to upload.");
            return "upload";
        }

        try {
            // Ensure directories exist
            new File(UPLOAD_DIR).mkdirs();
            new File(QR_CODE_DIR).mkdirs();

            // Save the uploaded image
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File imageFile = new File(UPLOAD_DIR + fileName);
            file.transferTo(imageFile);

            // Generate a QR code for the image URL
            String imageUrl = request.getRequestURL().toString().replace(request.getRequestURI(), "") + "/images/" + fileName;
            String qrCodeFileName = UUID.randomUUID() + "_qr.png";
            File qrCodeFile = new File(QR_CODE_DIR + qrCodeFileName);
            generateQRCodeImage(imageUrl, 300, 300, qrCodeFile.getAbsolutePath());

            // Pass the QR code URL to the JSP
            String qrCodeUrl = "/qrcodes/" + qrCodeFileName;
            model.addAttribute("qrCodeUrl", qrCodeUrl);
            model.addAttribute("message", "File uploaded successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Failed to upload file.");
        }

        return "upload";
    }

    private void generateQRCodeImage(String text, int width, int height, String filePath) throws Exception {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);
        Path path = new File(filePath).toPath();
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }
}
