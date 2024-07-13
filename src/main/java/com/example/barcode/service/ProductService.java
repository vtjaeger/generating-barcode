package com.example.barcode.service;

import com.example.barcode.model.BarcodeCounter;
import com.example.barcode.model.Product;
import com.example.barcode.repository.BarcodeCounterRepository;
import com.example.barcode.repository.ProductRepository;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BarcodeService barcodeService;
    @Autowired
    private BarcodeCounterRepository barcodeCounterRepository;

    private String getNextBarcode(){
        BarcodeCounter barcodeCounter = barcodeCounterRepository.findById(1L).orElseThrow(
                () -> new RuntimeException("barcode not found")
        );
        int nextBarcode = barcodeCounter.getNextBarcodeNumber();
        barcodeCounter.setNextBarcodeNumber(nextBarcode + 1);
        barcodeCounterRepository.save(barcodeCounter);

        return String.format("%05d", nextBarcode);
    }

    public Product createProduct(String name) throws IOException, WriterException {
        String barcode = getNextBarcode();
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

    public Product getOneProduct(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        return productOptional.orElse(null);
    }
}
