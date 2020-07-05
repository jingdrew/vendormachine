package com.vm.xyz.app.service.impl;

import com.vm.xyz.app.entity.Product;
import com.vm.xyz.app.exception.NoDataFoundException;
import com.vm.xyz.app.repository.ProductRepository;
import com.vm.xyz.app.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow(
                () -> new NoDataFoundException("Product with id of " + productId + " not found or does not exist"));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> saveProducts(List<Product> products) {
        return productRepository.saveAll(products);
    }
}
