package com.cg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.repo.ReportRepository;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    // Implement service methods
}
