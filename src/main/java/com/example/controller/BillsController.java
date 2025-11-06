package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.example.common.RestAPIResponse;
import com.example.entity.Bill;
import com.example.service.BillService;

@RestController
@RequestMapping("/bills")
//@CrossOrigin(origins = "*")
public class BillsController {

    @Autowired
    private BillService billService;

    //  Create a new Bill
    @PostMapping("/create")
    public ResponseEntity<RestAPIResponse> createBill(@RequestBody Bill bill) {
        try {
            BillDTO createdBill = billService.createBill(bill);
            return new ResponseEntity<>(new RestAPIResponse("Success", "Bill Created Successfully", createdBill),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RestAPIResponse("Error", "Bill Creation Failed: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //  Update existing Bill
    @PutMapping("/{id}")
    public ResponseEntity<RestAPIResponse> updateBill(@PathVariable Long id, @RequestBody Bill bill) {
        try {
            BillDTO updatedBill = billService.updateBill(id, bill);
            return new ResponseEntity<>(new RestAPIResponse("Success", "Bill Updated Successfully", updatedBill),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>( new RestAPIResponse("Error", "Bill Update Failed: " + e.getMessage()),HttpStatus.BAD_REQUEST );
        }
    }

    //  Mark Bill as Paid
    @PostMapping("/{id}/pay")
    public ResponseEntity<RestAPIResponse> markAsPaid(@PathVariable Long id, @RequestParam String paymentReference) {
        try {
            BillDTO updatedBill = billService.markAsPaid(id, paymentReference);
            return new ResponseEntity<>(new RestAPIResponse("Success", "Bill Marked as Paid Successfully", updatedBill),  HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RestAPIResponse("Error", "Failed to Mark Bill as Paid: " + e.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }
    
       // @GetMapping("/vendor/{vendorId}")
      // public ResponseEntity<List<BillDTO>> getBillsByVendor(@PathVariable Long vendorId) { 
     // return ResponseEntity.ok(billService.getBillsByVendor(vendorId)); 
    // } 
    
    // // @GetMapping("/customer/{customerId}") 
    // public ResponseEntity<List<BillDTO>> getBillsByCustomer(@PathVariable Long customerId) {
    // return ResponseEntity.ok(billService.getBillsByCustomer(customerId));
   // }

    //  Get Bill by ID
    @GetMapping("/{id}")
    public ResponseEntity<RestAPIResponse> getBillById(@PathVariable Long id) {
        try {
            BillDTO bill = billService.getBillById(id);
            return new ResponseEntity<>( new RestAPIResponse("Success", "Bill Retrieved Successfully", bill),HttpStatus.OK );
        } catch (Exception e) {
            return new ResponseEntity<>(new RestAPIResponse("Error", "Bill Not Found: " + e.getMessage(), null),HttpStatus.NOT_FOUND);
        }
    }

    //  Get All Bills
    @GetMapping("/getAllBill")
    public ResponseEntity<RestAPIResponse> getAllBills() {
        try {
            List<BillDTO> bills = billService.getAllBills();
            return new ResponseEntity<>(new RestAPIResponse("Success", "All Bills Retrieved Successfully", bills),HttpStatus.OK );
        } catch (Exception e) {
            return new ResponseEntity<>( new RestAPIResponse("Error", "Failed to Retrieve Bills: " + e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Search, Sort & Pagination
    @GetMapping("/searchAndSort") 
    public ResponseEntity<RestAPIResponse> getBillsWithPaginationAndSearch(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String keyword) {

        try {
            Page<BillDTO> billPage = billService.getAllBillsWithPaginationAndSearch(page, size, sortField, sortDir, keyword);

            Map<String, Object> response = new HashMap<>();
            response.put("bills", billPage.getContent());
            response.put("currentPage", billPage.getNumber());
            response.put("totalItems", billPage.getTotalElements());
            response.put("totalPages", billPage.getTotalPages());
            response.put("sortField", sortField);
            response.put("sortDir", sortDir);
            response.put("keyword", keyword);

            return new ResponseEntity<>( new RestAPIResponse("Success", "Bills Retrieved Successfully (Search + Sort + Pagination)", response),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>( new RestAPIResponse("Error", "Failed to Retrieve Bills: " + e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //  Delete Bill by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<RestAPIResponse> deleteBill(@PathVariable Long id) {
        try {
            billService.deleteBill(id);
            return new ResponseEntity<>(
                    new RestAPIResponse("Success", "Bill Deleted Successfully"),HttpStatus.OK );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new RestAPIResponse("Error", "Bill Deletion Failed: " + e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
