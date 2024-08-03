package com.cg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.entity.CustomerApproval;

@Repository
public interface CustomerApprovalRepository extends JpaRepository<CustomerApproval, Long> {
	
	List<CustomerApproval> findAllByStatus(String status);
}
