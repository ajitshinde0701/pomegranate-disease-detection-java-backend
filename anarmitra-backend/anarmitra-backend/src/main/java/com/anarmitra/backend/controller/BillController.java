package com.anarmitra.backend.controller;

import com.anarmitra.backend.entity.Bill;
import com.anarmitra.backend.entity.BillItem;
import com.anarmitra.backend.repository.BillRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    private final BillRepository billRepository;

    public BillController(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    @PostMapping
    public Bill createBill(@RequestBody Bill bill) {

        double total = 0;

        if (bill.getItems() != null) {
            for (BillItem item : bill.getItems()) {
                double itemTotal = item.getPrice() * item.getQuantity();
                item.setTotal(itemTotal);
                total += itemTotal;
            }
        }

        bill.setTotalAmount(total);

        return billRepository.save(bill);
    }

    @GetMapping("/farmer/{farmerId}")
    public List<Bill> getBillsByFarmer(@PathVariable Long farmerId) {
        return billRepository.findByFarmerId(farmerId);
    }

    @GetMapping("/seller/{sellerId}")
    public List<Bill> getBillsBySeller(@PathVariable Long sellerId) {
        return billRepository.findBySellerId(sellerId);
    }
}