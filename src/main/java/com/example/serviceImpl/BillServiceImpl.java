package com.example.serviceImpl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
            System.err.println(" Failed to sync invoice status for " 
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

        //  Update all editable fields
        existing.setInvoiceNumber(bill.getInvoiceNumber());
        existing.setPoNumber(bill.getPoNumber());
        existing.setPaymentTerm(bill.getPaymentTerm());
        existing.setTotalAmount(bill.getTotalAmount());
        existing.setInvoiceDate(bill.getInvoiceDate());
        existing.setDueDate(bill.getDueDate());
        existing.setPaymentDate(bill.getPaymentDate());
        existing.setStatus(bill.getStatus());
        existing.setBillDescription(bill.getBillDescription());
        existing.setConsultantName(bill.getConsultantName());
        existing.setApprovalStatus(bill.getApprovalStatus());
        existing.setHoursWorked(bill.getHoursWorked());
        existing.setTransactionId(bill.getTransactionId());
        existing.setRate(bill.getRate());
        existing.setProjectName(bill.getProjectName());
        existing.setPaymentMethod(bill.getPaymentMethod());
        existing.setPaymentReference(bill.getPaymentReference());
        existing.setUpdatedAt(LocalDateTime.now());

        Bill updated = billRepository.save(existing);
        syncInvoiceStatus(updated);

        //  Return the fully updated DTO
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
    
    @Override
    public Page<BillDTO> getAllBillsWithPaginationAndSearch(int page, int size, String sortField, String sortDir,String keyword) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Bill> billPage;

        if (keyword != null && !keyword.isBlank()) {
            billPage = billRepository.search(keyword, pageable);
        } else {
            billPage = billRepository.findAll(pageable);
        }

        List<BillDTO> dtos = billPage.getContent()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(dtos, pageable, billPage.getTotalElements());
    }

}
