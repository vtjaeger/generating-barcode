package com.example.barcode.service;

import com.example.barcode.dtos.ProductResponse;
import com.example.barcode.model.BarcodeCounter;
import com.example.barcode.model.Product;
import com.example.barcode.repository.BarcodeCounterRepository;
import com.example.barcode.repository.ProductRepository;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public ResponseEntity createProduct(String name) throws IOException, WriterException {
        String barcode = getNextBarcode();
        byte[] barcodeImage = barcodeService.generateBarcodeImage(barcode);

        var product = new Product();
        product.setName(name);
        product.setBarcode(barcode);
        product.setBarcodeImage(barcodeImage);
        productRepository.save(product);

        var response = new ProductResponse(product.getName(), product.getBarcode(), product.getBarcodeImage());

        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity getProducts(){
        List<Product> products = productRepository.findAll();
        List<ProductResponse> response = products.stream()
                .map(product -> new ProductResponse(
                        product.getName(),
                        product.getBarcode(),
                        product.getBarcodeImage()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity getOneProduct(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("product not found");
        }
        var product = productOptional.get();
        var reponse = new ProductResponse(
                product.getName(),
                product.getBarcode(),
                product.getBarcodeImage()
                );

        return ResponseEntity.ok().body(reponse);
    }
}
