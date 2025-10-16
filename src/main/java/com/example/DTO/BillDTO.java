package com.example.DTO;

import java.time.LocalDate;
import com.example.status.BillStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillDTO {

    private Long id;
    private String invoiceNumber;
    private String poNumber;
    private String paymentTerm;
    private Double totalAmount;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate invoiceDate;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate paymentDate;
    
    private BillStatus status;
    private String billDescription;
    
    private String transactionId; 

    // Additional optional details
    private String consultantName;
    private String approvalStatus;
    private Integer hoursWorked;
    private Double rate;
    private String projectName;
    private String paymentMethod;

    private String paymentReference;

    // IDs are optional, no need to fetch full customer/vendor data
//    private Long vendorId;
//    private Long customerId;
}
