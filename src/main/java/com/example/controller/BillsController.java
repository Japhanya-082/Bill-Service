package com.example.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.DTO.BillDTO;
import com.example.entity.Bill;
import com.example.service.BillService;

@RestController
@RequestMapping("/bills")
//@CrossOrigin(origins = "*")
public class BillsController {

    @Autowired
    private BillService billService;

    @PostMapping("/create")
    public ResponseEntity<BillDTO> createBill(@RequestBody Bill bill) {
        return ResponseEntity.ok(billService.createBill(bill));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BillDTO> updateBill(@PathVariable Long id, @RequestBody Bill bill) {
        return ResponseEntity.ok(billService.updateBill(id, bill));
    }

    @PostMapping("/{id}/pay")
    public ResponseEntity<BillDTO> markAsPaid(@PathVariable Long id,
                                              @RequestParam String paymentReference) {
        return ResponseEntity.ok(billService.markAsPaid(id, paymentReference));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillDTO> getBillById(@PathVariable Long id) {
        return ResponseEntity.ok(billService.getBillById(id));
    }

    @GetMapping("/getAllBill")
    public ResponseEntity<List<BillDTO>> getAllBills() {
        return ResponseEntity.ok(billService.getAllBills());
    }

//    @GetMapping("/vendor/{vendorId}")
//    public ResponseEntity<List<BillDTO>> getBillsByVendor(@PathVariable Long vendorId) {
//        return ResponseEntity.ok(billService.getBillsByVendor(vendorId));
//    }
//
//    @GetMapping("/customer/{customerId}")
//    public ResponseEntity<List<BillDTO>> getBillsByCustomer(@PathVariable Long customerId) {
//        return ResponseEntity.ok(billService.getBillsByCustomer(customerId));
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBill(@PathVariable Long id) {
        billService.deleteBill(id);
        return ResponseEntity.ok("Bill with ID " + id + " deleted successfully");
    }
}
