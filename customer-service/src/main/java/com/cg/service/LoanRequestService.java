package com.cg.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.entity.LoanRequest;
import com.cg.repo.LoanRequestRepository;

@Service
public class LoanRequestService {

    @Autowired
    private LoanRequestRepository loanRequestRepository;

    public LoanRequest createLoanRequest(LoanRequest loanRequest) {
        return loanRequestRepository.save(loanRequest);
    }

    public Optional<LoanRequest> getLoanRequestById(Long id) {
        return loanRequestRepository.findById(id);
    }

    public LoanRequest updateLoanRequest(LoanRequest loanRequest) {
        if (loanRequestRepository.existsById(loanRequest.getId())) {
            return loanRequestRepository.save(loanRequest);
        }
        throw new RuntimeException("Loan Request not found");
    }

    public void deleteLoanRequest(Long id) {
        if (loanRequestRepository.existsById(id)) {
            loanRequestRepository.deleteById(id);
        } else {
            throw new RuntimeException("Loan Request not found");
        }
    }

}
