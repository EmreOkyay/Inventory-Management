package com.emre.inventory_management.service.product;

import com.emre.inventory_management.dto.ProductDTO;
import com.emre.inventory_management.dto.ProductRequest;
import com.emre.inventory_management.model.Product;
import com.emre.inventory_management.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
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

        // TODO: Kafka integration for emailing the dealer(user) maybe
        // producer.sendUserEvent("product_created:" + savedProduct.getName());

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
        return productRepository.findAll().stream().map(this::convertToDTO).toList();
    }

    @Override
    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public Optional<ProductDTO> getProductByName(String name) {
        return productRepository.getProductByName(name).map(this::convertToDTO);
    }

    @Override
    public List<ProductDTO> getProductsByCategory(String category) {
        return productRepository.getProductsByCategory(category).stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<ProductDTO> getProductsWithAvailableStock() {
        return productRepository.getProductsWithAvailableStock().stream().map(this::convertToDTO).toList();
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO productDto = new ProductDTO();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setCategory(product.getCategory());
        productDto.setStock(product.getStock());
        productDto.setIsAvailable(product.getIsAvailable());
        return productDto;
    }
}
