package com.example.DTO;

import java.time.LocalDate;

import com.example.status.BillStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillDTO {
	private Long id;
    private String invoiceNumber;
    private String poNumber;
    private Double amount;
    private LocalDate invoiceDate;
    private LocalDate dueDate;
    private BillStatus status;

    private CustomerDTO customer;
    private VendorDTO vendor;
}
