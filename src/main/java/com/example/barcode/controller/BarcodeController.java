package com.example.barcode.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.OutputStream;

@RestController
public class BarcodeController {
    @GetMapping("/generate-barcode")
    public void generateBarcode(@RequestParam String text, HttpServletResponse response) throws Exception {
        BarcodeFormat barcodeFormat = BarcodeFormat.CODE_128;
        int width = 300;
        int height = 100;

        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, barcodeFormat, width, height);
        response.setContentType("image/png");

        try (OutputStream outputStream = response.getOutputStream()) {
            MatrixToImageWriter.writeToStream(bitMatrix, "png", outputStream);
        }
    }
}
