package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Bill;
import com.example.status.BillStatus;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long>{
                          
	List<Bill> findByStatus(BillStatus billStatus);
	List<Bill> findByCustomerId(Long customerId);
	List<Bill> findByVendorId(Long vendorId);
}
