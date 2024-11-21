package com.project.assesment.product.service;

import com.project.assesment.product.entity.Product;
import com.project.assesment.product.model.ProductRequest;
import com.project.assesment.product.model.ProductResponse;
import com.project.assesment.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setProduct(productRequest.getProduct());
        product.setPrice(productRequest.getPrice());
        product.setStock(productRequest.getStock());
        productRepository.create(product);
    }

    public void updateProduct(Integer id, ProductRequest productRequest) {
        Product product = new Product();
        product.setProduct(productRequest.getProduct());
        product.setPrice(productRequest.getPrice());
        product.setStock(productRequest.getStock());
        productRepository.update(id, product);
    }

    public List<ProductResponse> findAll() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> {
            ProductResponse productResponse = new ProductResponse();
            productResponse.setId(product.getId());
            productResponse.setProduct(product.getProduct());
            productResponse.setPrice(product.getPrice());
            productResponse.setStock(product.getStock());
            return productResponse;
        }).collect(Collectors.toList());
    }

    public ProductResponse findById(Integer id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with ID " + id + " not found")
        );
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setProduct(product.getProduct());
        productResponse.setPrice(product.getPrice());
        productResponse.setStock(product.getStock());
        return productResponse;
    }

    public void deleteProduct(Integer id) {
        productRepository.delete(id);
    }
}
