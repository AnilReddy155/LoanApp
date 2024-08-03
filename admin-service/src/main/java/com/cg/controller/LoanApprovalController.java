package com.cg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.service.LoanApprovalService;

@RestController
@RequestMapping("/loan-approvals")
public class LoanApprovalController {

    @Autowired
    private LoanApprovalService loanApprovalService;

    // Define endpoints
}

