package com.cg.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.entity.LoanRequest;
import com.cg.service.LoanRequestService;

@RestController
@RequestMapping("/loan-requests")
public class LoanRequestController {

    @Autowired
    private LoanRequestService loanRequestService;

    @PostMapping
    public ResponseEntity<LoanRequest> createLoanRequest(@RequestBody LoanRequest loanRequest) {
        LoanRequest createdLoanRequest = loanRequestService.createLoanRequest(loanRequest);
        return new ResponseEntity<>(createdLoanRequest, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanRequest> getLoanRequestById(@PathVariable Long id) {
        Optional<LoanRequest> loanRequest = loanRequestService.getLoanRequestById(id);
        return loanRequest.map(ResponseEntity::ok)
                          .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanRequest> updateLoanRequest(@PathVariable Long id, @RequestBody LoanRequest loanRequest) {
        loanRequest.setId(id);
        try {
            LoanRequest updatedLoanRequest = loanRequestService.updateLoanRequest(loanRequest);
            return ResponseEntity.ok(updatedLoanRequest);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoanRequest(@PathVariable Long id) {
        try {
            loanRequestService.deleteLoanRequest(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
