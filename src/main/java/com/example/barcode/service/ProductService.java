package com.example.barcode.service;

import com.example.barcode.model.Product;
import com.example.barcode.repository.ProductRepository;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Random;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BarcodeService barcodeService;

    private String generateRandomBarcode(){
        Random random = new Random();
        StringBuilder barcode = new StringBuilder();
        for(int i = 0; i < 12; i++) {
            barcode.append(random.nextInt(10));
        }
        return barcode.toString();
    }

    public Product createProduct(String name) throws IOException, WriterException {
        String barcode = generateRandomBarcode();
        byte[] barcodeImage = barcodeService.generateBarcodeImage(barcode);
        var product = new Product();
        product.setName(name);
        product.setBarcode(barcode);
        product.setBarcodeImage(barcodeImage);
        productRepository.save(product);
        return product;
    }

    public List<Product> getProducts(){
        return productRepository.findAll();
    }
}
