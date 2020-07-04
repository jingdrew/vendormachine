package com.vm.xyz.app.api.v1;

import com.vm.xyz.app.entity.Product;
import com.vm.xyz.app.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "${api.v1}/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping(path = "")
    public Product getProduct(@RequestParam("id") Long productId) {
        return productService.getProductById(productId);
    }

    @PostMapping(path = "")
    public Product saveProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @GetMapping(path = "/list")
    public List<Product> getProducts() {
        return productService.getAllProducts();
    }

    @PostMapping(path = "/list")
    public List<Product> saveProducts(List<Product> products) {
        return productService.saveProducts(products);
    }

    @GetMapping(path = "/que")
    public String test(){
        return "Fuck yeah always any ways whatttttdddddd";
    }

}
