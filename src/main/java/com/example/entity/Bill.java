package com.example.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.status.BillStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Bill_Info")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String invoiceNumber;  // maps to invoiceNumber in Invoice Service

    private String poNumber;
    private String paymentTerm;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate invoiceDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate dueDate;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate paymentDate;
    
    private String transactionId; 

    @Column(nullable = false)
    private Double totalAmount; // maps from totalAmount in Invoice

    private String billDescription;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BillStatus status;

//    private Long customerId;
//    private Long vendorId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime updatedAt;

    // Optional additional fields
    private String consultantName;
    private String approvalStatus;
    private Integer hoursWorked;
    private Double rate;
    private String projectName;
    private String paymentMethod;
    private String paymentReference; // maps to transactionId
}
