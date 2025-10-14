package com.example.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Client.CustomerClient;
import com.example.DTO.BillDTO;
import com.example.DTO.CustomerDTO;
import com.example.DTO.VendorDTO;
import com.example.entity.Bill;
import com.example.repository.BillRepository;
import com.example.service.BillService;
import com.example.status.BillStatus;

@Service
public class BillServiceImpl implements BillService{
               
	@Autowired
	private BillRepository billRepository;
	@Autowired
	private CustomerClient customerClient;

	public BillDTO mapToDTO(Bill bill) {
        CustomerDTO customer = customerClient.getCustomerById(bill.getCustomerId());
        VendorDTO vendor = customerClient.getVendorById(bill.getVendorId());

        BillDTO dto = new BillDTO();
        dto.setId(bill.getId());
        dto.setInvoiceNumber(bill.getInvoiceNumber());
        dto.setPoNumber(bill.getPoNumber());
        dto.setAmount(bill.getAmount());
        dto.setInvoiceDate(bill.getInvoiceDate());
        dto.setDueDate(bill.getDueDate());
        dto.setStatus(bill.getStatus());
        dto.setCustomer(customer);
        dto.setVendor(vendor);
        return dto;
    }
	@Override
	public BillDTO createBill(Bill bill) {
		bill.setCreatedAt(LocalDateTime.now());
		bill.setStatus(BillStatus.UNPAID);
		Bill saved = billRepository.save(bill);
		return mapToDTO(saved);
	}
	@Override
	public BillDTO updateBill(Long id, Bill bill) {
		Bill existing = billRepository.findById(id).orElseThrow(() -> new RuntimeException("Bill Not Found"));
		existing.setAmount(bill.getAmount());
        existing.setDueDate(bill.getDueDate());
        existing.setInvoiceDate(bill.getInvoiceDate());
        existing.setPoNumber(bill.getPoNumber());
        existing.setPaymentTerm(bill.getPaymentTerm());
        existing.setBillDescription(bill.getBillDescription());
        Bill updated = billRepository.save(existing);
		return 	mapToDTO(updated);
	}
	@Override
	public void deleteBill(Long id) {
		billRepository.deleteById(id);
	}
	@Override
	public BillDTO getBillById(Long id) {
		Bill bill = billRepository.findById(id).orElseThrow(()-> new RuntimeException("Bill Not Found"));
		return 	mapToDTO(bill);
	}
	@Override
	public List<BillDTO> getAllBills() {
		return billRepository.findAll().stream()
				       .map(this :: mapToDTO)
				       .collect(Collectors.toList());
	}
	@Override
	public List<BillDTO> getBillsByVendor(Long vendorId) {
		return billRepository.findByVendorId(vendorId).stream()
				       .map(this :: mapToDTO)
				       .collect(Collectors.toList());
	}
	@Override
	public List<BillDTO> getBillsByCustomer(Long customerId) {
		return billRepository.findById(customerId).stream()
				       .map(this :: mapToDTO)
				       .collect(Collectors.toList());
	}
	@Override
	public BillDTO markAsPaid(Long id, String paymentReference) {
		Bill bill =billRepository.findById(id).orElseThrow(() -> new RuntimeException("Bill Not Found"));
		bill.setStatus(BillStatus.PAID);
		bill.setPaymentReference(paymentReference);
		Bill updatedBill = billRepository.save(bill);
		return mapToDTO(updatedBill);
	}
	
	
	
}
