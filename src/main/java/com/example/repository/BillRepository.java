package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Bill;
import com.example.status.BillStatus;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long>{
                          
	List<Bill> findByStatus(BillStatus billStatus);
//	List<Bill> findByCustomerId(Long customerId);
//	List<Bill> findByVendorId(Long vendorId);
	
	@Query("SELECT b FROM Bill b WHERE " +
	           "LOWER(b.invoiceNumber) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
	           "LOWER(b.poNumber) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
	           "LOWER(b.paymentTerm) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
	           "LOWER(b.status) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
	           "LOWER(b.billDescription) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
	           "LOWER(b.consultantName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
	           "LOWER(b.approvalStatus) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
	           "LOWER(b.projectName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
	           "LOWER(b.paymentMethod) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
	           "LOWER(b.paymentReference) LIKE LOWER(CONCAT('%', :keyword, '%'))")
	    Page<Bill> search(@Param("keyword") String keyword, Pageable pageable);
	}

