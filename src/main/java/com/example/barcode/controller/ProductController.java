package com.example.barcode.controller;

import com.example.barcode.dtos.CreateProductDTO;
import com.example.barcode.model.Product;
import com.example.barcode.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getProducts(){
        return productService.getProducts();
    }

    @PostMapping
    public Product createProduct(@RequestBody CreateProductDTO dto) throws Exception {
        return productService.createProduct(dto.name());
    }

    @GetMapping("/{id}")
    public Product getOneProduct(@PathVariable Long id){
        return productService.getOneProduct(id);
    }
}
