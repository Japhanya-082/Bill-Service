package com.example.serviceImpl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Client.InvoiceClient;
import com.example.DTO.BillDTO;
import com.example.entity.Bill;
import com.example.repository.BillRepository;
import com.example.service.BillService;
import com.example.status.BillStatus;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private InvoiceClient invoiceClient;

    // Convert Bill → BillDTO
    private BillDTO mapToDTO(Bill bill) {
        BillDTO dto = new BillDTO();
        dto.setId(bill.getId());
        dto.setInvoiceNumber(bill.getInvoiceNumber());
        dto.setPoNumber(bill.getPoNumber());
        dto.setPaymentTerm(bill.getPaymentTerm());
        dto.setTotalAmount(bill.getTotalAmount());
        dto.setInvoiceDate(bill.getInvoiceDate());
        dto.setDueDate(bill.getDueDate());
        dto.setStatus(bill.getStatus());
        dto.setBillDescription(bill.getBillDescription());
        dto.setConsultantName(bill.getConsultantName());
        dto.setApprovalStatus(bill.getApprovalStatus());
        dto.setHoursWorked(bill.getHoursWorked());
        dto.setTransactionId(bill.getTransactionId());
        dto.setRate(bill.getRate());
        dto.setProjectName(bill.getProjectName());
        dto.setPaymentMethod(bill.getPaymentMethod());
        dto.setPaymentDate(bill.getPaymentDate());
        dto.setPaymentReference(bill.getPaymentReference());
        return dto;
    }

    // Sync Bill status with Invoice Service
    private void syncInvoiceStatus(Bill bill) {
        try {
            Map<String, String> payload = new HashMap<>();
            payload.put("status", bill.getStatus().name());
            invoiceClient.updateInvoiceStatus(bill.getInvoiceNumber(), payload);
        } catch (Exception e) {
            System.err.println("⚠️ Failed to sync invoice status for " 
                + bill.getInvoiceNumber() + ": " + e.getMessage());
        }
    }

    @Override
    public BillDTO createBill(Bill bill) {
        bill.setCreatedAt(LocalDateTime.now());
        if (bill.getStatus() == null)
            bill.setStatus(BillStatus.PENDING);

        Bill saved = billRepository.save(bill);
        syncInvoiceStatus(saved);
        return mapToDTO(saved);
    }

    @Override
    public BillDTO updateBill(Long id, Bill bill) {
        Bill existing = billRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bill not found with id: " + id));

        existing.setTotalAmount(bill.getTotalAmount());
        existing.setDueDate(bill.getDueDate());
        existing.setInvoiceDate(bill.getInvoiceDate());
        existing.setPoNumber(bill.getPoNumber());
        existing.setPaymentTerm(bill.getPaymentTerm());
        existing.setTransactionId(bill.getTransactionId());
        existing.setBillDescription(bill.getBillDescription());
        existing.setStatus(bill.getStatus());
        existing.setUpdatedAt(LocalDateTime.now());

        Bill updated = billRepository.save(existing);
        syncInvoiceStatus(updated);
        return mapToDTO(updated);
    }

    @Override
    public BillDTO markAsPaid(Long id, String paymentReference) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        bill.setStatus(BillStatus.PAID);
        bill.setPaymentReference(paymentReference);
        bill.setPaymentDate(LocalDateTime.now().toLocalDate());
        bill.setUpdatedAt(LocalDateTime.now());
        bill.setTransactionId(paymentReference);

        Bill updated = billRepository.save(bill);
        syncInvoiceStatus(updated);
        return mapToDTO(updated);
    }

    @Override
    public void deleteBill(Long id) {
        billRepository.deleteById(id);
    }

    @Override
    public BillDTO getBillById(Long id) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bill not found"));
        return mapToDTO(bill);
    }

    @Override
    public List<BillDTO> getAllBills() {
        return billRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}
