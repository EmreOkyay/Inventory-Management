package com.emre.inventory_management.service.product;

import com.emre.inventory_management.dto.ProductDTO;
import com.emre.inventory_management.dto.request.ProductRequest;
import com.emre.inventory_management.mapper.ProductMapper;
import com.emre.inventory_management.model.product.Product;
import com.emre.inventory_management.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public ProductDTO createProduct(ProductRequest request) {

        Product product = new Product();

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setCategory(request.getCategory());
        product.setStock(request.getStock());
        product.setIsAvailable(request.getIsAvailable());

        Product savedProduct = productRepository.save(product);

        ProductDTO productDTO = new ProductDTO();

        productDTO.setName(savedProduct.getName());
        productDTO.setDescription(savedProduct.getDescription());
        productDTO.setPrice(savedProduct.getPrice());
        productDTO.setCategory(savedProduct.getCategory());
        productDTO.setStock(savedProduct.getStock());
        productDTO.setIsAvailable(savedProduct.getIsAvailable());

        return productDTO;
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream().map(productMapper::toDTO).toList();
    }

    @Override
    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id).map(productMapper::toDTO);
    }

    @Override
    public Optional<ProductDTO> getProductByName(String name) {
        return productRepository.getProductByName(name).map(productMapper::toDTO);
    }

    @Override
    public List<ProductDTO> getProductsByCategory(String category) {
        return productRepository.getProductsByCategory(category).stream().map(productMapper::toDTO).toList();
    }

    @Override
    public List<ProductDTO> getProductsWithAvailableStock() {
        return productRepository.getProductsWithAvailableStock().stream().map(productMapper::toDTO).toList();
    }
}
