package com.vm.xyz.app.api.v1;

import com.vm.xyz.app.entity.*;
import com.vm.xyz.app.service.MachineService;
import com.vm.xyz.app.service.ProductService;
import com.vm.xyz.app.service.TransactionHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("${api.v1}/admin")
public class AdminController {

    private final MachineService machineService;
    private final ProductService productService;
    private final TransactionHistoryService transactionHistoryService;

    @GetMapping(value = "/transaction/list")
    public List<TransactionHistory> getTransactionHistories(@RequestParam("machine") Long machineId,
                                                            @RequestParam("date") String dateString) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = df.parse(dateString);
        return transactionHistoryService.getTransactionHistories(machineId, date);
    }

    @GetMapping("/product/list")
    public List<Product> getProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("/product")
    public List<Product> saveProducts(@RequestBody List<Product> products) {
        return productService.saveProducts(products);
    }

    @GetMapping("/machine/{id}")
    public Machine getMachine(@PathVariable("id") Long machineId) {
        return machineService.getMachineById(machineId);
    }

    @PostMapping("/machine/pslot")
    public Machine saveMachineProductSlots(@RequestParam("machine") Long machineId, @RequestBody List<MachineProductSlot> productSlotList) {
        return machineService.saveMachineProductSlots(machineId, productSlotList);
    }

    @PostMapping("/machine/mslot")
    public Machine saveMachineMoneySlots(@RequestParam("machine") Long machineId, @RequestBody List<MachineMoneySlot> machineMoneySlots) {
        return machineService.saveMachineMoneySlots(machineId, machineMoneySlots);
    }

}
