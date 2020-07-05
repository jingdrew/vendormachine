package com.vm.xyz.app.configuration;

import com.vm.xyz.app.entity.*;
import com.vm.xyz.app.model.CurrencyType;
import com.vm.xyz.app.model.PaymentMethod;
import com.vm.xyz.app.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@AllArgsConstructor
public class DBDataInitializer implements ApplicationRunner {

    private final ProductRepository productRepository;
    private final MachineRepository machineRepository;
    private final MachineProductSlotRepository machineProductSlotRepository;
    private final MachineMoneySlotRepository machineMoneySlotRepository;
    private final CurrencyRepository currencyRepository;

    public void createData() {

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



        Currency twoUSD = this.currencyRepository.save(new Currency(CurrencyType.BILL, "USD", "$2 Dollars", new BigDecimal(2)));
        Currency oneUSD = this.currencyRepository.save(new Currency(CurrencyType.BILL, "USD", "$1 Dollar", new BigDecimal(1)));
        Currency c50 = this.currencyRepository.save(new Currency(CurrencyType.COIN, "USD", "50 Cents", new BigDecimal("0.50")));
        Currency c25 = this.currencyRepository.save(new Currency(CurrencyType.COIN, "USD", "25 Cents", new BigDecimal("0.25")));
        Currency c10 = this.currencyRepository.save(new Currency(CurrencyType.COIN, "USD", "10 Cents", new BigDecimal("0.10")));
        Currency c5 = this.currencyRepository.save(new Currency(CurrencyType.COIN, "USD", "5 Cents", new BigDecimal("0.05")));


        Machine xyz1 = this.machineRepository.save(new Machine("XYZ-1",
                "This is a brand new vendor machine, only accepts $1, $2 bills, 5 cents, 10 cents, 25 cents, 50 cents coins and all kinds of debit/credit cards",
                PaymentMethod.ALL));
        Machine xyz2 = this.machineRepository.save(new Machine("XYZ-2",
                "This is a older machine, only accepts $1, $2 bills and 5 cents, 10 cents, 25 cents, 50 cents coins. Does not accept credit or debit cards.",
                PaymentMethod.CASH));


        saveMoneySlots(xyz1, 100, twoUSD, oneUSD, c50, c25, c10, c5);
        saveMoneySlots(xyz2, 150,  twoUSD, oneUSD, c50, c25, c10, c5);
        saveProductSlots(xyz1, 20, sneakers, water, coke, hersheys, brownie, frappuccino, lays, doritos);
        saveProductSlots(xyz2, 15, sneakers, water, coke, hersheys, brownie, frappuccino, lays, doritos);

    }

    private void saveMoneySlots(Machine machine, int qty, Currency... currencies) {
        for (Currency currency: currencies) {
            machineMoneySlotRepository.save(new MachineMoneySlot(machine, currency, qty));
        }
    }

    private void saveProductSlots(Machine machine, int qty, Product... products) {
        for (Product product: products) {
            machineProductSlotRepository.save(new MachineProductSlot(machine, product, qty));
        }
    }

    @Override
    public void run(ApplicationArguments args) {
        createData();
    }
}
