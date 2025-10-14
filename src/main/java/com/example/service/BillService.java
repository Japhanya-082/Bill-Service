package com.example.service;

import java.util.List;

import com.example.DTO.BillDTO;
import com.example.entity.Bill;

public interface BillService {
         public BillDTO createBill(Bill bill);
         public BillDTO mapToDTO(Bill bill); 
         BillDTO updateBill(Long id, Bill bill);
         void deleteBill(Long id);
         BillDTO getBillById(Long id);
         public List<BillDTO> getAllBills();
         public List<BillDTO> getBillsByVendor(Long vendorId);
         public List<BillDTO> getBillsByCustomer(Long customerId);
         public BillDTO markAsPaid(Long id, String paymentReference);
}
