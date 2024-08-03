package com.cg.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.entity.LoanApproval;
import com.cg.repo.LoanApprovalRepository;

@Service
public class LoanApprovalService {

    @Autowired
    private LoanApprovalRepository loanApprovalRepository;

    public LoanApproval createLoanApproval(LoanApproval loanApproval) {
        return loanApprovalRepository.save(loanApproval);
    }

    public Optional<LoanApproval> getLoanApprovalById(Long id) {
        return loanApprovalRepository.findById(id);
    }

    public LoanApproval updateLoanApproval(LoanApproval loanApproval) {
        if (loanApprovalRepository.existsById(loanApproval.getId())) {
            return loanApprovalRepository.save(loanApproval);
        }
        throw new RuntimeException("Loan Approval not found");
    }

    public void deleteLoanApproval(Long id) {
        if (loanApprovalRepository.existsById(id)) {
            loanApprovalRepository.deleteById(id);
        } else {
            throw new RuntimeException("Loan Approval not found");
        }
    }

}

