package com.example.barcode.service;

import com.example.barcode.model.Product;
import com.example.barcode.repository.ProductRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

@Service
public class BarcodeService {
    @Autowired
    private ProductRepository productRepository;
    public byte[] generateBarcodeImage(String barcode) throws WriterException, IOException {
        Code128Writer barcodeWriter = new Code128Writer();
        BitMatrix bitMatrix = barcodeWriter.encode(barcode, BarcodeFormat.CODE_128, 300, 150);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", baos);
            return baos.toByteArray();
        }
    }

    public Product getByBarcode(String barcode) {
        Optional<Product> productOptional = productRepository.findByBarcode(barcode);
        return productOptional.orElse(null);
    }
}
