package com.vm.xyz.app.service;

import com.vm.xyz.app.entity.Product;

import java.util.List;

public interface ProductService {

    Product getProductById(Long productId);

    List<Product> getAllProducts();

    List<Product> saveProducts(List<Product> products);

}
