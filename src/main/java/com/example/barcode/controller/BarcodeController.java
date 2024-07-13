package com.example.barcode.controller;

import com.example.barcode.model.Product;
import com.example.barcode.repository.ProductRepository;
import com.example.barcode.service.BarcodeService;
import com.example.barcode.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.OutputStream;
import java.util.Optional;

@RestController
@RequestMapping("/barcode")
public class BarcodeController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private BarcodeService barcodeService;

    @Deprecated
    @GetMapping("/{id}")
    public void getBarcodeImage(@PathVariable Long id, HttpServletResponse response) throws Exception {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            if (product.getBarcodeImage() != null) {
                response.setContentType("image/png");
                OutputStream os = response.getOutputStream();
                os.write(product.getBarcodeImage());
                os.flush();
                os.close();
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @GetMapping("/code/{barcode}")
    public Product getByBarcode(@PathVariable String barcode){
        return barcodeService.getByBarcode(barcode);
    }
}
