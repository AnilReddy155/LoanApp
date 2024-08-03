package com.cg.controller;

import java.util.Optional;
import java.util.UUID;

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

import com.cg.entity.CustomerApproval;
import com.cg.service.CustomerApprovalService;

@RestController
@RequestMapping("/customer-approvals")
public class CustomerApprovalController {

    @Autowired
    private CustomerApprovalService customerApprovalService;

    @PostMapping
    public ResponseEntity<CustomerApproval> createCustomerApproval(@RequestBody CustomerApproval customerApproval) {
        CustomerApproval createdCustomerApproval = customerApprovalService.createCustomerApproval(customerApproval);
        return new ResponseEntity<>(createdCustomerApproval, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerApproval> getCustomerApprovalById(@PathVariable Long id) {
        Optional<CustomerApproval> customerApproval = customerApprovalService.getCustomerApprovalById(id);
        return customerApproval.map(ResponseEntity::ok)
                               .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerApproval> updateCustomerApproval(@PathVariable Long id, @RequestBody CustomerApproval customerApproval) {
        customerApproval.setId(id);
        try {
            CustomerApproval updatedCustomerApproval = customerApprovalService.updateCustomerApproval(customerApproval);
            return ResponseEntity.ok(updatedCustomerApproval);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomerApproval(@PathVariable Long id) {
        try {
            customerApprovalService.deleteCustomerApproval(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
