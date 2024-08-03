package com.cg.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "loan_approval", schema = "admin_db")
@Data
public class LoanApproval {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loan_approval_seq")
	@SequenceGenerator(name = "loan_approval_seq", sequenceName = "loan_approval_sequence", allocationSize = 1, schema = "admin_db")
	private Long id; // Primary Key

	@Column(name = "loan_request_id", nullable = false)
	private Long loanRequestId; // Foreign Key referencing LoanRequest(id)

	@Column(name = "admin_id", nullable = false)
	private Long adminId;

	@Column(name = "status", nullable = false)
	private String status; // e.g., PENDING, APPROVED, REJECTED

	@Column(name = "validation_status", nullable = false)
	private String validationStatus; // e.g., SUCCESS, FAILURE

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "created_at", nullable = false)
	private Timestamp createdAt;

	@Column(name = "updated_at", nullable = false)
	private Timestamp updatedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getLoanRequestId() {
		return loanRequestId;
	}

	public void setLoanRequestId(Long loanRequestId) {
		this.loanRequestId = loanRequestId;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getValidationStatus() {
		return validationStatus;
	}

	public void setValidationStatus(String validationStatus) {
		this.validationStatus = validationStatus;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

}
