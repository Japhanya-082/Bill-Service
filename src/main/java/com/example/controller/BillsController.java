package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.DTO.BillDTO;
import com.example.entity.Bill;
import com.example.serviceImpl.BillServiceImpl;


@RestController
@RequestMapping("/bills")
public class BillsController {
                 
	@Autowired
	private BillServiceImpl billServiceImpl;
		
	@PostMapping("/create")
	public BillDTO createBill(@RequestBody Bill bill) {
		return billServiceImpl.createBill(bill);
	}
	
	@PostMapping("/{id}/pay")
	public BillDTO markAsPaid(@PathVariable Long id, 
			                                             @RequestParam String paymentReference) {
		 return billServiceImpl.markAsPaid(id, paymentReference);
	}
	
	@GetMapping("/{id}")
	public BillDTO getBillById(@RequestBody Bill bill, @PathVariable Long id) {
		return billServiceImpl.getBillById(id);
	}
	
	@GetMapping("/getAllBill")
	public List<BillDTO> getAllBills() {
		return billServiceImpl.getAllBills();
	}
	
	@GetMapping("/vendor/{vendorId}")
	public List<BillDTO>  getBillsByVendor(@PathVariable Long vendorId) {
		return billServiceImpl.getBillsByVendor(vendorId);
	}
	
	@GetMapping("/customer/{customerId}")
	public List<BillDTO> getBillsByCustomer(@PathVariable Long customerId){
		return billServiceImpl.getBillsByCustomer(customerId);
	}
	
	@PutMapping("/{id}")
	public BillDTO updateBill(@PathVariable Long id, @RequestBody Bill bill) {
		return billServiceImpl.updateBill(id, bill);
	}
	
	@DeleteMapping("/{id}")
	public String deleteBill(@PathVariable Long id) {
		 billServiceImpl.deleteBill(id);
		 return "Bill with ID" +id+ "DeletedSuccessfully";
	}
	
}



