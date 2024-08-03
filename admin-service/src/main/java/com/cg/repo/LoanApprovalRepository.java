package com.cg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.entity.LoanApproval;

@Repository
public interface LoanApprovalRepository extends JpaRepository<LoanApproval, Long> {
}

