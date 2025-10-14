 package com.example.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.status.BillStatus;

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

    @Column(nullable = false)
    private String invoiceNumber;

    private String poNumber;
    private String paymentTerm;

    @Column(nullable = false)
    private LocalDate invoiceDate;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Column(nullable = false)
    private Double amount;

    private String billDescription;

    @Enumerated(EnumType.STRING)
    private BillStatus status = BillStatus.UNPAID;

    // Reference only IDs
    @Column(nullable = false)
    private Long vendorId;

    @Column(nullable = false)
    private Long customerId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String paymentReference;
}
