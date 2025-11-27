package com.emre.inventory_management.controller;

import com.emre.inventory_management.dto.ProductDTO;
import com.emre.inventory_management.dto.request.ProductRequest;
import com.emre.inventory_management.service.product.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductServiceImpl productServiceImpl;

    @Autowired
    public ProductController(ProductServiceImpl productServiceImpl) {
        this.productServiceImpl = productServiceImpl;
    }

    @PostMapping("/add")
    public ProductDTO addProduct(@RequestBody ProductRequest request) {
        return productServiceImpl.createProduct(request);
    }

    @RequestMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> productList = productServiceImpl.getAllProducts();
        return ResponseEntity.ok(productList);
    }

    @RequestMapping("/id/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return productServiceImpl.getProductById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping("/name/{name}")
    public ResponseEntity<ProductDTO> getProductByName(@PathVariable String name) {
        return productServiceImpl.getProductByName(name).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping("/category/{category}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable String category) {
        List<ProductDTO> productList = productServiceImpl.getProductsByCategory(category);
        return ResponseEntity.ok(productList);
    }

    @RequestMapping("/available")
    public ResponseEntity<List<ProductDTO>> getProductsWithAvailableStock() {
        List<ProductDTO> productList = productServiceImpl.getProductsWithAvailableStock();
        return ResponseEntity.ok(productList);
    }
}
