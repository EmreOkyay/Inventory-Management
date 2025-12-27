package com.emre.inventory_management.service.product;

import com.emre.inventory_management.dto.ProductDTO;
import com.emre.inventory_management.dto.request.ProductRequest;
import com.emre.inventory_management.mapper.ProductMapper;
import com.emre.inventory_management.model.product.Product;
import com.emre.inventory_management.repository.ProductRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    void createProductData() {
        product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setDescription("example desc");
        product.setPrice(BigDecimal.valueOf(150));
        product.setCategory("PC");
        product.setStock(50L);
        product.setIsAvailable(true);

        productDTO = new ProductDTO();
        productDTO.setName("Laptop");
        productDTO.setDescription("example desc");
        productDTO.setPrice(BigDecimal.valueOf(150));
        productDTO.setCategory("PC");
        productDTO.setStock(50L);
        productDTO.setIsAvailable(true);
    }

    @Test
    void testCreateProduct_Successful() {
        // given
        ProductRequest request = new ProductRequest();
        request.setName("Laptop");
        request.setDescription("example desc");
        request.setPrice(BigDecimal.valueOf(150));
        request.setCategory("PC");
        request.setStock(50L);

        Product savedProduct = new Product();
        savedProduct.setName("Laptop");
        savedProduct.setDescription("example desc");
        savedProduct.setPrice(BigDecimal.valueOf(150));
        savedProduct.setCategory("PC");
        savedProduct.setStock(50L);
        savedProduct.setIsAvailable(true);

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        // when
        ProductDTO result = productService.createProduct(request);

        // then
        assertNotNull(result);
        assertEquals("Laptop", result.getName());
        assertEquals("PC", result.getCategory());
        assertEquals(BigDecimal.valueOf(150), result.getPrice());

        verify(productRepository).save(any(Product.class));
    }

    @Test
    void testGetAllProducts_Successful() {
        // given
        when(productRepository.findAll()).thenReturn(List.of(product));

        when(productMapper.toDTO(product)).thenReturn(productDTO);

        // when
        List<ProductDTO> productList = productService.getAllProducts();

        // then
        assertEquals(1, productList.size());
        assertEquals("Laptop", productList.get(0).getName());

        verify(productRepository).findAll();
        verify(productMapper).toDTO(product);
    }

    @Test
    void testGetProductById_Successful() {
        // given
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        when(productMapper.toDTO(product)).thenReturn(productDTO);

        // when
        Optional<ProductDTO> result = productService.getProductById(1L);

        // then
        assertTrue(result.isPresent());
        assertEquals("Laptop", result.get().getName());

        verify(productRepository).findById(1L);
        verify(productMapper).toDTO(product);
    }


    @Test
    void testGetProductByName_Successful() {
        // given
        when(productRepository.getProductByName("Laptop")).thenReturn(Optional.of(product));

        when(productMapper.toDTO(product)).thenReturn(productDTO);

        // when
        Optional<ProductDTO> result = productService.getProductByName("Laptop");

        // then
        assertTrue(result.isPresent());
        assertEquals("Laptop", result.get().getName());

        verify(productRepository).getProductByName("Laptop");
        verify(productMapper).toDTO(product);
    }

    @Test
    void testGetProductsByCategory_Successful() {
        // given
        when(productRepository.getProductsByCategory("PC")).thenReturn(List.of(product));

        when(productMapper.toDTO(product)).thenReturn(productDTO);

        // when
        List<ProductDTO> result = productService.getProductsByCategory("PC");

        // then
        assertEquals(1, result.size());
        assertEquals("Laptop", result.get(0).getName());
        assertEquals("PC", result.get(0).getCategory());

        verify(productRepository).getProductsByCategory("PC");
        verify(productMapper).toDTO(product);
    }

    @Test
    void testGetProductsWithAvailableStock_Successful() {
        // given
        when(productRepository.getProductsWithAvailableStock()).thenReturn(List.of(product));

        when(productMapper.toDTO(product)).thenReturn(productDTO);

        // when
        List<ProductDTO> result = productService.getProductsWithAvailableStock();

        // then
        assertEquals(1, result.size());
        assertEquals("Laptop", result.get(0).getName());

        verify(productRepository).getProductsWithAvailableStock();
        verify(productMapper).toDTO(product);
    }
}