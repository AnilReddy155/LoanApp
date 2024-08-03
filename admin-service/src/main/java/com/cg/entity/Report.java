package com.cg.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "report", schema = "admin_db")
public class Report {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "report_seq")
	@SequenceGenerator(name = "report_seq", sequenceName = "report_sequence", allocationSize = 1, schema = "admin_db")
    private Long id;

    @Column(name = "report_type", nullable = false)
    private String reportType;

    @Column(name = "generated_at", nullable = false)
    private Timestamp generatedAt;

    @Column(name = "report_data", columnDefinition = "json")
    private String reportData;

}
