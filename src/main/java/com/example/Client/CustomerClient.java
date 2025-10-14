package com.example.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.DTO.CustomerDTO;
import com.example.DTO.VendorDTO;

@FeignClient(name = "CUSTOMER-SERVICE")
public interface CustomerClient {
	 @GetMapping("/customer/{customerId}")
	CustomerDTO getCustomerById(@PathVariable("customerId") Long customerId);
	 
	 @GetMapping("/vendor/{vendorId}")
		VendorDTO getVendorById(@PathVariable("vendorId") Long vendorId);
}
