package com.example.service;

import java.util.List;
import com.example.DTO.BillDTO;
import com.example.entity.Bill;

public interface BillService {
    BillDTO createBill(Bill bill);
    BillDTO updateBill(Long id, Bill bill);
    void deleteBill(Long id);
    BillDTO getBillById(Long id);
    List<BillDTO> getAllBills();
//    List<BillDTO> getBillsByVendor(Long vendorId);
//    List<BillDTO> getBillsByCustomer(Long customerId);
    BillDTO markAsPaid(Long id, String paymentReference);
}
