package com.vm.xyz.app.configuration;

import com.vm.xyz.app.entity.Product;
import com.vm.xyz.app.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DBDataInitializer implements ApplicationRunner {

    @Autowired
    private ProductRepository productRepository;

    public void createData() {

        productRepository.deleteAll();
        Product sneakers = this.productRepository.save(
                new Product("Sneakers", new BigDecimal(1), new BigDecimal("1.75"),
                        "https://www.meijer.com/content/dam/meijer/product/0004/00/0042/43/0004000042431_3_A1C1_1200.png"));

        Product water = this.productRepository.save(
                new Product("Water", new BigDecimal("0.50"), new BigDecimal("1.25"),
                        "https://www.kroger.com/product/images/xlarge/front/0001200000159"));

        Product coke = this.productRepository.save(
                new Product("Coca Cola", new BigDecimal("0.75"), new BigDecimal("1.50"),
                        "https://i5.walmartimages.ca/images/Large/192/721/6000200192721.jpg"));

        Product doritos = this.productRepository.save(
                new Product("Doritos", new BigDecimal("0.75"), new BigDecimal("1.50"),
                        "https://www.bigw.com.au/medias/sys_master/images/images/h48/h43/11305899819038.jpg"));

        Product lays = this.productRepository.save(
                new Product("Lays", new BigDecimal("0.75"), new BigDecimal("1.50"),
                        "https://images-na.ssl-images-amazon.com/images/I/81TLFU5Yj6L._SY445_.jpg"));

        Product hersheys = this.productRepository.save(
                new Product("Hershey's", new BigDecimal(3), new BigDecimal("6.50"),
                        "https://images-na.ssl-images-amazon.com/images/I/81e4EW-R9SL._SL1500_.jpg"));

        Product frappuccino = this.productRepository.save(
                new Product("Frappuccino", new BigDecimal("1.50"),
                        new BigDecimal("2.75"), "https://images.heb.com/is/image/HEBGrocery/000836123"));

        Product brownie = this.productRepository.save(
                new Product("Brownie", new BigDecimal("1.50"), new BigDecimal("2.75"),
                        "https://cf.shopee.ph/file/35bff282d2697a1593b78f2b756c79eb"));
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createData();
    }
}
